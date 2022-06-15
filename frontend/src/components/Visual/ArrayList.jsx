import './Visual.css';

export default function ArrayList(props) {
    return (
        <table>
            <tr>
                {props.contents.map((element, i) => {
                    return(
                        <td key={i} className = {`td${props.redPositions.includes(i) ? '-red' : ''}`}> {element}</td>
                    )
                })}
            </tr>
        </table>
    )
}

// <td className = {`td ${i === 1 ? '-red' : ''} `}> {element}</td>