import React from "react";

class NewItem extends React.Component {

    constructor( props ) {
        super( props );
        this.state = {
            text: "",
            itemType: "TASK"
        };
    }

    render() {
        return (
            <li className="newItem">
                <div className="input-group">
                    <span className="input-group-btn">
                        <button type="button" className="btn btn-default dropdown-toggle" data-toggle="dropdown" id="choose-type-button">
                            {this.typeLabel[this.state.itemType]} <span className="caret"></span>
                        </button>
                        <ul className="dropdown-menu">
                            <li onClick={(e) => this.handleChangeType(e, "TASK" )}><a href="" id="choose-task-button">Task</a></li>
                            <li onClick={(e) => this.handleChangeType(e, "EVENT" )}><a href="" id="choose-event-button">Event</a></li>
                            <li onClick={(e) => this.handleChangeType(e, "NOTE" )}><a href="" id="choose-note-button">Note</a></li>
                        </ul>
                    </span>
                    <input
                        type="text"
                        required="required"
                        className="form-control"
                        value={this.state.text}
                        onChange={( e ) => this.handleChangeText( e )}
                    />
                    <span className="input-group-btn">
                        <button onClick={() => this.handleClickAdd()} className="btn">Add Item</button>
                    </span>
                </div>
            </li> );
    }

    handleChangeText( event ) {
        this.setState( Object.assign( {}, this.state, { text: event.target.value } ) );
    }

    handleChangeType(e, itemType ) {
        this.setState( Object.assign( {}, this.state, { itemType: itemType } ) );
        e.preventDefault();
    }

    handleClickAdd() {
        this.props.onAddItem( this.state.itemType, this.state.text );
        this.setState( { text: "" } );
    }

    typeLabel = {
        TASK: "Task",
        EVENT: "Event",
        NOTE: "Note"
    }

}

export default NewItem;
