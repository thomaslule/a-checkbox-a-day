import React from "react";
import ContentEditable from "react-contenteditable";
import "./Item.css";

class Item extends React.Component {

    render() {
        const checkbox = this.props.item.itemType === "TASK" ? (
            <div className="checkbox">
                <input
                    type="checkbox"
                    checked={this.props.item.completed}
                    onChange={() => this.props.onChangeCompleteTask()}
                />
            </div> ) : null;
        const buttonCancel = this.props.item.cancelled ? null : ( <a href="" title="Cancel" onClick={( e ) => this.handleCancel( e )}><i className="glyphicon glyphicon-remove" /></a> );
        const buttonRestore = this.props.item.cancelled ? ( <a href="" title="Restore" onClick={( e ) => this.handleRestore( e )}><i className="glyphicon glyphicon-asterisk" /></a> ) : null;
        const buttonDelete = this.props.item.cancelled ? ( <a href="" title="Delete" onClick={( e ) => this.handleDelete( e )}><i className="glyphicon glyphicon-trash" /></a> ) : null;
        return (
            <li className={this.getItemClass()}>
                {checkbox}
                <ContentEditable tagName="span" className="item-name" html={this.props.item.text} onKeyDown={ (e) => this.handleTextKeyDown( e ) } onBlur={( e ) => this.handleChangeText( e )} />
                <span className="action-buttons">{buttonCancel}{buttonRestore}{buttonDelete}</span>
            </li> );
    }

    getItemClass() {
        let classes = ["item"];
        classes.push( this.props.item.itemType.toLowerCase() );
        if ( this.props.item.cancelled ) classes.push( "cancelled" );
        if ( this.props.item.completed ) classes.push( "completed" );
        return classes.join( " " );
    }

    handleCancel( e ) {
        this.props.onCancel();
        e.preventDefault();
    }

    handleRestore( e ) {
        this.props.onRestore();
        e.preventDefault();
    }

    handleDelete( e ) {
        this.props.onDelete();
        e.preventDefault();
    }

    handleChangeText( e ) {
        if (e.target.innerText !== this.props.item.text) {
            this.props.onChangeText(e.target.innerText);
        }
    }
    
    handleTextKeyDown( e ) {
        if (e.keyCode === 27) { // ESCAPE
            e.target.innerText = this.props.item.text;
            e.target.blur();
            e.preventDefault();
        } else if (e.keyCode === 13) { // ENTER
            e.target.blur();
            e.preventDefault();
        }
    }

}

export default Item;
