import './Visual.css';
import ArrayList from './ArrayList';
import {useState} from "react";

const Visual = (props) => {
    const [data, setData] = useState([]);
    
    function DisplayVisual() {
        if (props.structType === "ArrayList") {
            setData(props.contents);
            return <ArrayList contents={data} redPositions={props.redPositions}/>
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

