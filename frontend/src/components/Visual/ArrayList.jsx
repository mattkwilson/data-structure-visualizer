import './Visual.css';

export default function ArrayList(props) {
    return (
        <table>
            <tr>
                {props.contents.map((element, i) => {
                    return(
                        <td key={i} className = {`td${props.redPosition === i ? '-red-left' :
                                              props.greenPositions.includes(i) ? '-green' : props.redPosition === i+1 ? '-red-right' : ''}`}> {element}</td>
                    )
                })}
            </tr>
        </table>
    )
}