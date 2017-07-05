const tasks = ( state = [], action ) => {
    console.log( "action", action );
    switch ( action.type ) {
        case "RECEIVE_TASKS":
            return action.tasks;
        case "TASK_ADDED":
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
