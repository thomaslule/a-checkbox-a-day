const tasks = ( state = [], action ) => {
    switch ( action.type ) {
        case "FETCH_TASKS_SUCCESS":
            return action.tasks;
        case "ADD_TASK_SUCCESS":
            return [
                ...state,
                action.task
            ]
        case "COMPLETE_TASK":
            return state.map( task =>
                ( task.id === action.id )
                    ? { ...task, completed: true }
                    : task
            )
        default:
            return state
    }
}

export default tasks
