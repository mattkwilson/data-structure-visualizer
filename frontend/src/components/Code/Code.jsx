import './Code.css'

const Code = (props) => {


    return (
        <div className="Code">
            <h3>Code: </h3>
            <pre className='code-text'>{props.code0}</pre>
            <pre className='code-text'>{props.code1}</pre>
            <pre className='code-text code-highlighted'>{props.code2}</pre>
            <pre className='code-text'>{props.code3}</pre>
            <pre className='code-text'>{props.code4}</pre>
        </div>
    );

}
export default Code;