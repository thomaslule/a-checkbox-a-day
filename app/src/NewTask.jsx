import React from "react";

class NewTask extends React.Component {

    constructor( props ) {
        super( props );
        this.state = {
            todo: ""
        };
    }
    
    render() {
        return (
            <li>
                <div className="input-group">
                    <input
                        type="text"
                        required="required"
                        className="form-control"
                        value={this.state.todo}
                        onChange={(e) => this.handleTodoChange(e) }
                    />
                    <span className="input-group-btn">
                        <button onClick={() => this.handleAddTask()} className="btn">Add task</button>
                    </span>
                </div>
            </li>
        );
    }

    handleTodoChange( event ) {
        this.setState( { todo: event.target.value } );
    }

    handleAddTask() {
        fetch( "api/AddTask",
            {
                method: "POST",
                body: JSON.stringify( { todo: this.state.todo, month: this.props.month } )
            } )
            .then(() => {
                this.setState( { todo: "" } );
                this.props.onAddTask();
            } )
            .catch( error => console.warn( error ) );
    }

}

export default NewTask;
