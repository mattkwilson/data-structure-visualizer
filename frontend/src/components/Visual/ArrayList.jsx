import './Visual.css';

export default function ArrayList(props) {
    return (
        <table>
            <tr>
                {props.contents.map((element, i) => {
                    return(
                        <td key={i} className = {`td${props.bluePosition === i ? '-blue' :
                                              props.redPositions.includes(i) ? '-red' : ''}`}> {element}</td>
                    )
                })}
            </tr>
        </table>
    )
}