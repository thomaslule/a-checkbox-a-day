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
                    key={task.id}
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
                            month={this.props.match.params.month}
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
    
    componentWillReceiveProps(nextProps) {
        this.updateTaskList(nextProps.match.params.month);
    }

    updateTaskList(month) {
        month = month || this.props.match.params.month;
        fetch( "api/Tasks/" + month)
            .then( this.toJson )
            .then( tasks => {
                this.setState( { tasks } );
            } )
            .catch( error => console.warn( error ) );
    }

}

export default TaskList;
