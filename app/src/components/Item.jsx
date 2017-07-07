import React from "react";
import "./Item.css";

class Item extends React.Component {

    render() {
        const checkbox = this.props.item.itemType === "TASK" ? (
            <div className="checkbox">
                <input
                    type="checkbox"
                    checked={this.props.item.completed}
                    onChange={() => this.props.onChangeCompleteTask( !this.props.item.completed, this.props.item.id )}
                />
            </div> ) : null;
        const buttonCancel = this.props.item.cancelled ? null : ( <a href="" title="Cancel" onClick={(e) => this.handleCancel(e)}><i className="glyphicon glyphicon-remove" /></a> );
        return (
            <li className={this.getItemClass()}>
                {checkbox}
                <span className="item-name">{this.props.item.text}</span>
                <span className="action-buttons">{buttonCancel}</span>
            </li> );
    }
    
    getItemClass() {
        let classes = [ "item" ];
        classes.push(this.props.item.itemType.toLowerCase());
        if (this.props.item.cancelled) classes.push("cancelled");
        if (this.props.item.completed) classes.push("completed");
        return classes.join(" ");
    }
    
    handleCancel(e) {
        this.props.onCancel();
        e.preventDefault();
    }

}

export default Item;
