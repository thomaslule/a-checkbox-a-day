import React from "react";
import Task from "./Task";
import NewTask from "./NewTask";

class TaskList extends React.Component {

    constructor( props ) {
        super( props );
        this.state = {
            tasks: []
        };
    }

    render() {
        let taskList = this.state.tasks.map(( task ) => {
            return (
                <Task
                    task={task}
                    onCompleteTask={() => this.updateTaskList()}
                />
            );
        } );
        return (
            <div className="row">
                <div className="col-xs-offset-4 col-xs-4">
                    <ul className="list-unstyled">
                        {taskList}
                        <NewTask
                            onAddTask={() => this.updateTaskList()}
                        />
                    </ul>
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
        fetch( "api/Tasks/2017-05" )
            .then( this.toJson )
            .then( tasks => {
                this.setState( { tasks } );
            } )
            .catch( error => console.warn( error ) );
    }

}

export default TaskList;
