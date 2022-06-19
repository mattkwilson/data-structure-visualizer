import './StepList.css';

const StepList = (props) => {


    return (
        <div className="stepList">
                {Array.from(Array(props.length).keys()).map((i) => {
                    return (
                        <input key={i} className={`setStepButton${props.step === i ? '-selected' : ''}`} type='button' value={"Step " + [i+1]} onClick={() => props.handleClickStep(i)}/>
                    )
                })}
        </div>
    );
}

export default StepList;