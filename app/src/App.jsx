import React, { Component } from 'react';
import './App.css';

class App extends Component {

    constructor( props ) {
        super( props );
        this.state = {
            newTaskTodo: "",
            tasks: []
        };
    }

    render() {
        var taskList = this.state.tasks.map( ( task ) => {
            return (
                <li key={task.id}>
                    <input
                        type="checkbox"
                        checked={task.completed}
                        disabled={task.completed}
                        onChange={() => this.handleTaskComplete( task )}
                    />
                    {task.todo}
                </li>
            );
        } );
        return (
            <div>
                <div>
                    <input
                        type="text"
                        value={this.state.newTaskTodo}
                        onChange={this.handleChange.bind( this )}
                    />
                    <button onClick={() => this.handleClick()}>Create task</button>
                </div>
                <ul>
                    {taskList}
                </ul>
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
