const tasks = ( state = [], action ) => {
    console.log( "action", action );
    switch ( action.type ) {
        case "RECEIVE_TASKS":
            return action.tasks;
        case "ADD_TASK":
            return [
                ...state,
                {
                    id: action.id,
                    todo: action.todo,
                    completed: false
                }
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
