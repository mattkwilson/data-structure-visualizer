import './Visual.css';

const Visual = (props) => {
    return (
        <div className="App">
            <h1>Visuals?</h1>
            <h2>{props.contents}</h2>
        </div>
    );
}

export default Visual;