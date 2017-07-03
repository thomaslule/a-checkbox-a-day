import React from "react";
import Task from "./Task";
import NewTask from "./NewTaskContainer";

export default ( props ) => {
    let taskList = props.tasks.map(( task ) =>
        <Task
            key={task.id}
            task={task}
            onCompleteTask={() => props.onCompleteTask()}
        /> );
    return (
        <div className="row">
            <div className="col-xs-offset-4 col-xs-4">
                <ul className="list-unstyled">
                    {taskList}
                    <NewTask
                        onAddTask={() => props.onAddTask()}
                        month={props.month}
                    />
                </ul>
            </div>

        </div>
    );
}
