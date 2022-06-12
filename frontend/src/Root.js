import './Root.css';
import Info from './components/Info/Info'
import Visual from './components/Visual/Visual'
import {useState} from "react";

function Root() {
    const array = [];
    const [step, setStep] = useState(0);
    const [fileName, setFileName] = useState();
    const [line, setLine] = useState();
    const [name, setName] = useState();
    const [structType, setStructType] = useState();
    const [contents, setContents] = useState();

    function readJson() {
        const sample = JSON.parse('{"array": [{"fileName": "src/test.java","line": 26,"name": "arr","structType": "ArrayList","contents": [13,26,32,2]},{"fileName": "src/test2.java","line": 29,"name": "areeer","structType": "ArrayList","contents": [1,2,3,4]}]}');
        for (const step of sample.array) {
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
            const previousStep = step - 1
            setStep(previousStep)
            setStepData()
        }
    }

    function onClickNext() {
        if (step < (array.length - 1)) {
            const nextStep = step + 1
            setStep(nextStep)

        }
        setStepData()
    }

    function setStepData() {
        setFileName(array[step].fileName)
        setLine(array[step].line)
        setName(array[step].name)
        setStructType(array[step].structType)
        setContents(array[step].contents)
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
                    <Visual contents={contents}/>
                    <input type='button' value='Previous Step' onClick={onClickPrevious}/>
                    <input type='button' value='Next Step' onClick={onClickNext}/>
                </div>
            </div>
        </div>
    )
}

export default Root;