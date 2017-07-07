let api = {};

function handleErrors(response) {
    if (!response.ok) {
        throw Error(response.statusText);
    }
    return response;
}

api.fetchTasks = function( month ) {
    return fetch( "/api/Tasks/" + month )
        .then( handleErrors )
        .then( response => response.json() );
}

api.addTask = function( task ) {
    return fetch( "/api/AddTask",
        {
            method: "POST",
            body: JSON.stringify( task )
        } )
        .then( handleErrors )
        .then( response => response.json() );
}

api.completeTask = function( id ) {
    return fetch( "/api/CompleteTask",
        {
            method: "POST",
            body: JSON.stringify( { id } )
        } )
        .then( handleErrors );
}

export default api;
