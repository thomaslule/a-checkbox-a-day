let actions = {};

actions.fetchTasks = ( month ) => ( {
    type: "FETCH_TASKS",
    month
} );

actions.receiveTasks = ( tasks ) => ( {
    type: "RECEIVE_TASKS",
    tasks
} );

actions.addTask = ( task ) => ( {
    type: "ADD_TASK",
    task
} );

actions.taskAdded = ( task ) => ( {
    type: "TASK_ADDED",
    task
} );

actions.completeTask = ( id ) => ( {
    type: "COMPLETE_TASK",
    id
} );

export default actions;
