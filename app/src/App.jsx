import React, { Component } from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.css';

class App extends Component {

    constructor( props ) {
        super( props );
        this.state = {
            newTaskTodo: "",
            tasks: []
        };
    }

    render() {
        var taskList = this.state.tasks.map(( task ) => {
            return (
                <li key={task.id}>
                    <div className="checkbox">
                        <input
                            type="checkbox"
                            checked={task.completed}
                            disabled={task.completed}
                            onChange={() => this.handleTaskComplete( task )}
                        />
                    </div>
                    {task.todo}
                </li>
            );
        } );
        return (
            <div>
                <nav className="navbar navbar-default">
                    <div className="container">
                        <div className="row">
                            <div className="col-xs-offset-4 col-xs-5"><span className="navbar-brand">A checkbox a day</span></div>
                        </div>
                    </div>
                </nav>
                <div className="container">
                    <div className="col-xs-offset-4 col-xs-5">
                        <ul className="list-unstyled">
                            {taskList}
                            <li>
                                <div className="input-group">
                                    <input
                                        type="text"
                                        required="required"
                                        className="form-control"
                                        value={this.state.newTaskTodo}
                                        onChange={this.handleChange.bind( this )}
                                    />
                                    <span className="input-group-btn">
                                        <button onClick={() => this.handleClick()} className="btn">Create task</button>
                                    </span>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        );
    }

    toJson( response ) {
        return response.json();
    }

    componentDidMount() {
        this.updateTaskList();
    }

    updateTaskList() {
        fetch( "api/Tasks" )
            .then( this.toJson )
            .then( tasks => {
                this.setState( { tasks } );
            } )
            .catch( error => console.warn( error ) );
    }

    handleChange( event ) {
        this.setState( { newTaskTodo: event.target.value } );
    }

    handleTaskComplete( task ) {
        fetch( "api/CompleteTask",
            {
                method: "POST",
                body: JSON.stringify( { id: task.id } )
            } )
            .then(() => this.updateTaskList() )
            .catch( error => console.warn( error ) );
    }

    handleClick() {
        fetch( "api/AddTask",
            {
                method: "POST",
                body: JSON.stringify( { todo: this.state.newTaskTodo } )
            } )
            .then(() => {
                this.setState( { newTaskTodo: "" } );
                this.updateTaskList();
            } )
            .catch( error => console.warn( error ) );
    }
}

export default App;
