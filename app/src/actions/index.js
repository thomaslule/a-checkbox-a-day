export const fetchTasks = ( month ) => ( {
    type: "FETCH_TASKS",
    month
} );

export const receiveTasks = ( tasks ) => ({
    type: "RECEIVE_TASKS",
    tasks
});

let nextTodoId = 0
export const addTask = ( todo ) => ( {
    type: "ADD_TASK",
    id: nextTodoId++,
    todo
} );

export const completeTask = ( id ) => ( {
    type: "COMPLETE_TASK",
    id
} );
