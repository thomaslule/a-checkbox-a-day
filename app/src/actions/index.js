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
