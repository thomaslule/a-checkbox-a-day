export const fetchTasks = ( month ) => ( {
    type: "FETCH_TASKS",
    month
} );

export const receiveTasks = ( tasks ) => ( {
    type: "RECEIVE_TASKS",
    tasks
} );

export const addTask = ( task ) => ( {
    type: "ADD_TASK",
    task
} );

export const taskAdded = ( task ) => ( {
    type: "TASK_ADDED",
    task
} );

export const completeTask = ( id ) => ( {
    type: "COMPLETE_TASK",
    id
} );
