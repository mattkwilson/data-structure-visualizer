import './Root.css';
import Info from './components/Info/Info';
import Visual from './components/Visual/Visual';
import data from './json/sample.json';
import data2 from './json/sample2.json';
import { useState } from "react";

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
        // const sample = JSON.parse('{"array": [{"fileName": "src/test.java","line": 26,"name": "arr","structType": "ArrayList","contents": [13,26,32,2]},{"fileName": "src/test2.java","line": 29,"name": "areeer","structType": "ArrayList","contents": [1,2,3,4]}, {"fileName": "src/test2.java","line": 32,"name": "areeer","structType": "ArrayList","contents": [1,5,7,4]}]}');
        // const sample = JSON.parse(data);
        const samples = [data, data2]
        for (const json of samples) {
            const temp = [];
            for (const currStep of json.array) {
                temp.push({
                    "fileName": currStep["fileName"],
                    "line": currStep["line"],
                    "name": currStep["name"],
                    "structType": currStep["structType"],
                    "contents": currStep["contents"]
                })
            }
            array.push(temp);
        }
    }


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
        if (structType === "ArrayList" && step !== 0 && bluePosition === -1) {
            const newStepContents = array[currentData][step].contents;
            const previousStepContents = array[currentData][step - 1].contents
            const redPositions = [];
            for (let i = 0; i < newStepContents.length; i++) {
                if (previousStepContents[i] !== newStepContents[i]) {
                    redPositions.push(i);
                }
            }
            setRedPositions(redPositions)
        } else {
            setRedPositions([])
        }
    }

    function bluePositionHelper() {
        if (structType === "ArrayList" && step !== 0) {
            const newStepContents = array[currentData][step].contents;
            const previousStepContents = array[currentData][step - 1].contents
            if (newStepContents.length < previousStepContents.length) {
                for (let i = 0; i < newStepContents.length; i++) {
                    if (previousStepContents[i] !== newStepContents[i]) {
                        return i;
                    } else {
                        return newStepContents.length;
                    }
                }
            }
        }
        return -1;
    }

    readJson()
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
                    <input type='button' value='Previous Step' onClick={onClickPrevious}/>
                    <input type='button' value='Next Step' onClick={onClickNext}/>
                    <div className="data-buttons">
                        {array.map((dataStruct, index) => {
                            return (
                                <input key={index} type='button' value={dataStruct[0].name} onClick={() => onClickData(index)}/>
                            )
                        })}
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Root;