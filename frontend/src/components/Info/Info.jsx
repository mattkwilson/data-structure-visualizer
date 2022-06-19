import './Info.css'
import {useState} from "react";

const Info = (props) => {


    return (
        <div className="Info">
            <h2 className='text'>Information</h2>
            <h3 className='text'>File: {props.fileName}</h3>
            <h3 className='text'>Variable: {props.name}</h3>
            <h3 className='text'>Type: {props.structType}</h3>
        </div>
    );

}
export default Info;