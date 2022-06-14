import './Root.css';
import Info from './components/Info/Info';
import Visual from './components/Visual/Visual';
import data from './json/sample.json';
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

    function readJson() {
        // var request = new XMLHttpRequest();
        // request.open("GET", "./json/sample.json", false);
        // request.send(null)
        // var sample = JSON.parse(request.responseText);
        // alert(data.array);
        // const sample = JSON.parse('{"array": [{"fileName": "src/test.java","line": 26,"name": "arr","structType": "ArrayList","contents": [13,26,32,2]},{"fileName": "src/test2.java","line": 29,"name": "areeer","structType": "ArrayList","contents": [1,2,3,4]}, {"fileName": "src/test2.java","line": 32,"name": "areeer","structType": "ArrayList","contents": [1,5,7,4]}]}');
        // const sample = JSON.parse(data);
        for (const step of data.array) {
            array.push({
                "fileName": step["fileName"],
                "line": step["line"],
                "name": step["name"],
                "structType": step["structType"],
                "contents": step["contents"]
            })
        }
    }


    function onClickPrevious() {
        if (step > 0) {
            setStep(--step)
            setStepData()
        }

    }

    function onClickNext() {
        if (step < (array.length - 1)) {
            setStep(++step)
            setStepData()
        }

    }

    function setStepData() {
        setFileName(array[step].fileName)
        setLine(array[step].line)
        setName(array[step].name)
        setStructType(array[step].structType)
        redPositionHelper(step)
        setContents(array[step].contents)
    }

    function redPositionHelper(newContent) {
        if(structType === "ArrayList" && step !== 0) {
            const newStepContents = array[step].contents;
            const previousStepContents = array[step - 1].contents
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
                    <Visual name={name} contents={contents} structType={structType} redPositions={redPositions}/>
                    <input type='button' value='Previous Step' onClick={onClickPrevious}/>
                    <input type='button' value='Next Step' onClick={onClickNext}/>
                </div>
            </div>
        </div>
    )
}

export default Root;