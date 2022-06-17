import './Info.css'

const Info = (props) => {

    return (
        <div className="App">
            <h1>Info On Current Step</h1>
            <h2>File Name: {props.fileName}</h2>
            <h2>Line Number: {props.line}</h2>
            <h2>Variable Name: {props.name}</h2>
            <h2>Variable Type: {props.structType}</h2>
        </div>
    );

}
export default Info;