import './Visual.css';

export default function ArrayList(props) {
    return (
        <table>
            <tr>
                {props.contents.map((element, i) => {
                    return(
                        <td key={i} className = {`td${props.bluePosition === i ? '-blue-left' :
                                              props.redPositions.includes(i) ? '-red' : props.bluePosition === i+1 ? '-blue-right' : ''}`}> {element}</td>
                    )
                })}
            </tr>
        </table>
    )
}