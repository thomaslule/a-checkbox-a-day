import React from "react";
import "./Item.css";

export default props => {
    let checkbox = props.item.itemType === "TASK" ? (
        <div className="checkbox">
            <input
                type="checkbox"
                checked={props.item.completed}
                onChange={() => props.onChangeCompleteTask( !props.item.completed, props.item.id )}
            />
        </div> ) : null;
    return (
        <li className={"item " + props.item.itemType.toLowerCase()}>
            {checkbox}
            {props.item.text}
        </li> );
}
