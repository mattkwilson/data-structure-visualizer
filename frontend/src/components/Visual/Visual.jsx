import './Visual.css';
import ArrayList from './ArrayList';
import HashMap from './HashMap';
import {useState} from "react";

const Visual = (props) => {
    const [data, setData] = useState([]);
    
    function DisplayVisual() {
        if (props.structType === "ArrayList") {
            setData(props.contents);
            return <ArrayList contents={data} redPositions={props.redPositions} bluePosition={props.bluePosition}/>
        } else if (props.structType === "HashMap") {
            setData(props.contents);
            return <HashMap contents={data}/>
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

