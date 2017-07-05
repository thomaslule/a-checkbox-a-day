let api = {};

api.fetchTasks = function( month ) {
    return fetch( "/api/Tasks/" + month )
        .then( response => response.json() );
}

api.addTask = function( task ) {
    return fetch( "/api/AddTask",
        {
            method: "POST",
            body: JSON.stringify( task )
        } )
        .then( response => response.json() );
}

api.completeTask = function( id ) {
    return fetch( "/api/CompleteTask",
        {
            method: "POST",
            body: JSON.stringify( { id } )
        } );
}

export default api;
