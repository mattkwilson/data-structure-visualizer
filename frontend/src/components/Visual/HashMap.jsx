import './Visual.css';
import arrow from '../../images/arrow.png';

export default function HashMap(props) {
    var params = props.contents.replace(/[{}]/g, "");
    var entries = params.split(", ");
    var array1 = [];
    var array2 = [];
    for (var i=0; i < entries.length; i++) {
        let index = entries[i].indexOf("=")
        var tokens = [entries[i].substring(0, index), entries[i].substring(index+1)]
        array1.push(tokens[0]);
        array2.push(tokens[1]);
    }
    console.log(array1);
    console.log(array2);

    return (
        <div className="HashMap">
            <table>
                {array1.map((element, i) => {
                    return(
                        <tr key={i}>
                            <td className="td">{element}</td>
                        </tr>
                    )
                })}
            </table>
            <img src={arrow} alt="->" />
            <table>
                {array2.map((element, i) => {
                    return(
                        <tr key={i}>
                            <td className="td">{element}</td>
                        </tr>
                    )
                })}
            </table>
        </div>
    )
}