import React from "react";

export default props => (
    <li>
        <div className="checkbox">
            <input
                type="checkbox"
                checked={props.task.completed}
                disabled={props.task.completed}
                onChange={() => props.onCompleteTask( props.task.id )}
            />
        </div>
        {props.task.todo}
    </li> );
