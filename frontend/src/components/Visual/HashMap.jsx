import './Visual.css';
import arrow from '../../images/arrow.png';

export default function HashMap(props) {
    var array1 = props.contents[0]
    var array2 = props.contents[1]
    return (
        <div className="HashMap">
            <table>
                {array1.map((element, i) => {
                    return(
                        <tr key={i}>
                            <td className = {`td${props.bluePosition === i ? '-blue-top' :
                                              props.redPositions.includes(i) ? '-red' : props.bluePosition === i+1 ? '-blue-bottom' : ''}`}>{element}</td>
                        </tr>
                    )
                })}
            </table>
            <img src={arrow} alt="->" />
            <table>
                {array2.map((element, i) => {
                    return(
                        <tr key={i}>
                            <td className = {`td${props.bluePosition === i ? '-blue-top' :
                                              props.redPositions.includes(i) ? '-red' : props.bluePosition === i+1 ? '-blue-bottom' : ''}`}>{element}</td>
                        </tr>
                    )
                })}
            </table>
        </div>
    )
}