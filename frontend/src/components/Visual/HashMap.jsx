import './Visual.css';
import arrow from '../../images/arrow.png';
import {useEffect, useState} from "react";

export default function HashMap(props) {
    var array1 = props.contents[0]
    var array2 = props.contents[1]
    const [imageHeight, setImageHeight] = useState(0)

    function setHeight() {
        const height = document.getElementById('tableDiv').clientHeight
        setImageHeight(height)
    }

    useEffect(() => {
        setHeight()
    })

    return (
        <div className="center">
            <div className="HashMap">
                <table id='tableDiv'>
                    {array1.map((element, i) => {
                        return(
                            <tr key={i}>
                                <td className = {`td${props.redPosition === i ? '-red-top' :
                                    props.greenPositions.includes(i) ? '-green' : props.redPosition === i+1 ? '-red-bottom' : ''}`}>{element}</td>
                            </tr>
                        )
                    })}
                </table>
                <img src={arrow} alt="->" id='img' height={imageHeight}/>
                <table>
                    {array2.map((element, i) => {
                        return(
                            <tr key={i}>
                                <td className = {`td${props.redPosition === i ? '-red-top' :
                                    props.greenPositions.includes(i) ? '-green' : props.redPosition === i+1 ? '-red-bottom' : ''}`}>{element}</td>
                            </tr>
                        )
                    })}
                </table>
            </div>
        </div>

    )
}