import './Info.css'
import {useState} from "react";

const Info = (props) => {


    return (
        <div className="App">
            <h1>Info On Current Step</h1>
            <h2>File Name: {props.fileName}</h2>
            <h2>Line Number: {props.line}</h2>
            <h2>Variable Name: {props.name}</h2>
            <h2>Variable Type: {props.structType}</h2>
            <br/>
            <h3>Code: </h3>
            <h3>{props.code0}</h3>
            <h3>{props.code1}</h3>
            <h3>{props.code2}</h3>
            <h3>{props.code3}</h3>
            <h3>{props.code4}</h3>
        </div>
    );

}
export default Info;