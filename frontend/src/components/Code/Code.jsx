import './Code.css'
import {useState} from "react";

const Code = (props) => {


    return (
        <div className="Code">
            <h3>Code: </h3>
            <h3 className='code-text'>{props.code0}</h3>
            <h3 className='code-text'>{props.code1}</h3>
            <h3 className='code-text code-highlighted'>{props.code2}</h3>
            <h3 className='code-text'>{props.code3}</h3>
            <h3 className='code-text'>{props.code4}</h3>
        </div>
    );

}
export default Code;