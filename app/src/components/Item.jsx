import React from "react";

export default props => (
    <li>
        <div className="checkbox">
            <input
                type="checkbox"
                checked={props.item.completed}
                disabled={props.item.completed}
                onChange={() => props.onCompleteTask( props.item.id )}
            />
        </div>
        {props.item.text}
    </li> );
