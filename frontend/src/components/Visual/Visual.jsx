import './Visual.css';
import ArrayList from './ArrayList';
import HashMap from './HashMap';
// import {useState} from "react";

const Visual = (props) => {
    // const [data, setData] = useState([]);
    
    function DisplayVisual() {
        if (props.structType === "ArrayList") {
            // setData(props.contents);
            return <ArrayList contents={props.contents} redPositions={props.redPositions} bluePosition={props.bluePosition}/>
        } else if (props.structType === "java.util.HashMap") {
            // setData(props.contents);
            return <HashMap contents={props.contents} redPositions={props.redPositions} bluePosition={props.bluePosition}/>
        }
    }
    
    return (
        <div className="App">
            <h1>{props.name}</h1>
            <DisplayVisual />
            
        </div>
    );
}

export default Visual;

