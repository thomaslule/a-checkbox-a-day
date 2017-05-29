import React from "react";

class Task extends React.Component {

    render() {
        let task = this.props.task;
        return (
            <li>
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
    }

    handleTaskComplete( task ) {
        fetch( "api/CompleteTask",
            {
                method: "POST",
                body: JSON.stringify( { id: task.id } )
            } )
            .then(() => this.props.onCompleteTask() )
            .catch( error => console.warn( error ) );
    }


}

export default Task;
