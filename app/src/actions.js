let actions = {};

actions.fetchTasks = ( month ) => ( {
    type: "FETCH_TASKS",
    month
} );

actions.fetchTasksSuccess = ( tasks ) => ( {
    type: "FETCH_TASKS_SUCCESS",
    tasks
} );

actions.addTask = ( task ) => ( {
    type: "ADD_TASK",
    task
} );

actions.addTaskSuccess = ( task ) => ( {
    type: "ADD_TASK_SUCCESS",
    task
} );

actions.completeTask = ( id ) => ( {
    type: "COMPLETE_TASK",
    id
} );

actions.error = (error) => ({
    type: "ERROR",
    error
});

export default actions;
