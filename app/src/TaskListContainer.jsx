import React from "react";
import TaskList from "./TaskList";

class TaskListContainer extends React.Component {

    constructor( props ) {
        super( props );
        this.state = {
            tasks: []
        };
    }

    render() {
        return (
            <TaskList tasks={this.state.tasks}
                onCompleteTask={() => this.updateTaskList()}
                onAddTask={() => this.updateTaskList()}
                month={this.props.month}
            /> );
    }

    toJson( response ) {
        return response.json();
    }

    componentDidMount() {
        this.updateTaskList();
    }

    componentWillReceiveProps( nextProps ) {
        this.updateTaskList( nextProps.month );
    }

    updateTaskList( month ) {
        month = month || this.props.month;
        fetch( "/api/Tasks/" + month )
            .then( this.toJson )
            .then( tasks => {
                this.setState( { tasks } );
            } )
            .catch( error => console.warn( error ) );
    }

}

export default TaskListContainer;
