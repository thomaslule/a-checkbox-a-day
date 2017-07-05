import React from "react";
import Task from "./Task";
import NewTask from "./NewTask";

class TaskList extends React.Component {

    componentDidMount() {
        this.props.onNewMonth( this.props.month );
    }

    componentWillReceiveProps( nextProps ) {
        if ( nextProps.month != this.props.month ) {
            this.props.onNewMonth( nextProps.month );
        }
    }

    render() {
        let taskList = this.props.tasks.map(( task ) =>
            <Task
                key={task.id}
                task={task}
                onCompleteTask={( id ) => this.props.onCompleteTask( id )}
            /> );
        return (
            <div className="row">
                <div className="col-xs-offset-4 col-xs-4">
                    <ul className="list-unstyled">
                        {taskList}
                        <NewTask
                            onAddTask={( todo ) => this.props.onAddTask( todo )}
                            month={this.props.month}
                        />
                    </ul>
                </div>

            </div>
        );
    }
}

export default TaskList;
