import './Visual.css';
import ArrayList from './ArrayList';
import HashMap from './HashMap';

const Visual = (props) => {
    
    function DisplayVisual() {
        if (props.structType === "Array") {
            return <ArrayList contents={props.contents} greenPositions={props.greenPositions} redPosition={props.redPosition}/>
        } else if (props.structType === "Hashmap") {
            return <HashMap contents={props.contents} greenPositions={props.greenPositions} redPosition={props.redPosition}/>
        }
    }
    
    return (
        <div className="App">
            <br/>
            <DisplayVisual />
        </div>
    );
}

export default Visual;

