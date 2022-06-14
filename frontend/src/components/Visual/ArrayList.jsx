import './Visual.css';

export default function ArrayList(props) {
    return (
        <table>
            <tr>
                {props.contents.map((element, i) => {
                    return(
                        <td>{element}</td>
                    )
                })}
            </tr>
        </table>
    )
}

// <span className='td${props.color === 'red' ? '-red':''}'>