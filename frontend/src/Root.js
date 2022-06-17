import './Root.css';
import Info from './components/Info/Info';
import Visual from './components/Visual/Visual';
import data from './json/tracked.json';
import {useState} from "react";

function Root() {
    const array = [];
    let [step, setStep] = useState(-1);
    const [fileName, setFileName] = useState();
    const [line, setLine] = useState();
    const [name, setName] = useState();
    const [structType, setStructType] = useState();
    const [contents, setContents] = useState();
    const [redPositions, setRedPositions] = useState([]);
    const [bluePosition, setBluePosition] = useState(-1);
    let [currentData, setCurrentData] = useState(0);

    function readJson() {
        for (const json of data.jsonFiles) {
            const temp = [];
            for (const currStep of json.array) {
                var currContents;
                if (currStep["structType"] === "ArrayList") {
                    // if array is json array object
                    // currContents = JSON.parse(currStep["contents"])
                    let arrayString = currStep["contents"];
                    //get rid of brackets
                    arrayString = arrayString.substring(1,arrayString.length-1)
                    const arrayEntries = arrayString.split(",");
                    const arraylist = [];
                    for (let i=0; i < arrayEntries.length; i++) {
                        arraylist.push(arrayEntries[i]);
                    }
                    currContents = arraylist;
                } else if (currStep["structType"] === "java.util.HashMap") {
                    // Converts hashmap string into two arrays
                    var params = currStep["contents"].replace(/[{}]/g, "");
                    var entries = params.split(", ");
                    var array1 = [];
                    var array2 = [];
                    for (var i=0; i < entries.length; i++) {
                        let index = entries[i].indexOf("=")
                        var tokens = [entries[i].substring(0, index), entries[i].substring(index+1)]
                        array1.push(tokens[0]);
                        array2.push(tokens[1]);
                    }
                    currContents = [array1, array2]
                } else {
                    currContents = currStep["contents"]
                }
                temp.push({
                    "fileName": currStep["fileName"],
                    "line": currStep["line"],
                    "name": currStep["name"],
                    "structType": currStep["structType"],
                    "contents": currContents
                })
            }
            array.push(temp);
        }
    }

    readJson()

    function onClickPrevious() {
        if (step > 0) {
            setStep(--step)
            setStepData()
        }

    }

    function onClickNext() {
        if (step < (array[currentData].length - 1)) {
            setStep(++step)
            setStepData()
        }

    }

    function onClickData(index) {
        if (index !== currentData || step === -1) {
            setCurrentData(currentData = index)
            setStep(step = 0)
            setStepData()
        }
    };

    function setStepData() {
        setFileName(array[currentData][step].fileName)
        setLine(array[currentData][step].line)
        setName(array[currentData][step].name)
        setStructType(array[currentData][step].structType)
        let bluePosition = bluePositionHelper()
        setBluePosition(bluePosition)
        redPositionHelper(bluePosition)
        setContents(array[currentData][step].contents)
    }

    function redPositionHelper(bluePosition) {
        if ((structType === "ArrayList" || structType === "java.util.HashMap") && step !== 0 && bluePosition === -1) {
            var newStepContents;
            var previousStepContents;
            if (structType === "ArrayList") {
                newStepContents = array[currentData][step].contents;
            } else {
                newStepContents = array[currentData][step].contents[0];
            }
            if (structType === "ArrayList") {
                previousStepContents = array[currentData][step - 1].contents
            } else {
                previousStepContents = array[currentData][step - 1].contents[0]
            }
            const redPositions = [];
            for (let i = 0; i < newStepContents.length; i++) {
                if (previousStepContents[i] !== newStepContents[i]) {
                    redPositions.push(i);
                }
            }
            setRedPositions(redPositions)
        } else if (structType === "java.util.HashMap" && step !== 0 && bluePosition === -1){
            
        } else {
            setRedPositions([])
        }
    }

    function bluePositionHelper() {
        if ((structType === "ArrayList" || structType === "java.util.HashMap") && step !== 0) {
            var newStepContents;
            var previousStepContents;
            if (structType === "ArrayList") {
                newStepContents = array[currentData][step].contents;
            } else {
                newStepContents = array[currentData][step].contents[0];
            }
            if (structType === "ArrayList") {
                previousStepContents = array[currentData][step - 1].contents
            } else {
                previousStepContents = array[currentData][step - 1].contents[0]
            }
            if (newStepContents.length < previousStepContents.length) {
                for (let i = 0; i < newStepContents.length; i++) {
                    if (previousStepContents[i] !== newStepContents[i]) {
                        return i;
                    }
                }
                return newStepContents.length;
            }
        }
        return -1;
    }

    // readJson()
    return (
        <div>
            <div className="left">
                <div className="centered">
                    <Info fileName={fileName} line={line}
                          name={name} structType={structType}/>
                </div>

            </div>

            <div className="right">
                <div className="centered">
                    <Visual name={name} contents={contents} structType={structType} redPositions={redPositions}
                            bluePosition={bluePosition}/>
                    <input className="stepButton" type='button' value='Previous Step' onClick={onClickPrevious}/>
                    <input className="stepButton" type='button' value='Next Step' onClick={onClickNext}/>
                    <div className="dropdown">
                        <button>Variables</button>
                        <div className="dropdown-content">
                            {array.map((dataStruct, index) => {
                                return (
                                    <div className={'variable'} key={index}
                                           onClick={() => onClickData(index)}>
                                        {dataStruct[0].name}
                                    </div>
                                )
                            })}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Root;