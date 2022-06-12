import './Info.css'

const Info = (props) => {

    return (
        <div className="App">
            <h1>Some Info</h1>
            <h2>{props.fileName}</h2>
            <h2>{props.line}</h2>
            <h2>{props.name}</h2>
            <h2>{props.structType}</h2>
        </div>
    );

}
export default Info;