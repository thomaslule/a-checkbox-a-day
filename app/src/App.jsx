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
        console.log( this.state.tasks );
        var taskList = this.state.tasks.map( function( task ) {
            return (
                <li>{task.todo}</li>
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
            .catch( error => {
                console.warn( error );
            } );
    }

    handleChange( event ) {
        this.setState( { newTaskTodo: event.target.value } );
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
            .catch( error => {
                console.warn( error );
            } );
    }
}

export default App;
