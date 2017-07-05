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
                        onChange={( e ) => this.handleChange( e )}
                    />
                    <span className="input-group-btn">
                        <button onClick={() => this.handleClickAdd()} className="btn">Add task</button>
                    </span>
                </div>
            </li> );
    }

    handleChange( event ) {
        this.setState( { todo: event.target.value } );
    }

    handleClickAdd() {
        this.props.onAddTask( this.state.todo );
        this.setState( { todo: "" } );
    }

}

export default NewTask;
