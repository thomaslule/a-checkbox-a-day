let api = {};

function handleErrors(response) {
    if (!response.ok) {
        throw Error(response.statusText);
    }
    return response;
}

api.fetchItems = function( month ) {
    return fetch( "/api/Items/" + month )
        .then( handleErrors )
        .then( response => response.json() );
}

api.addItem = function( item ) {
    return fetch( "/api/AddItem",
        {
            method: "POST",
            body: JSON.stringify( item )
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

api.uncompleteTask = function( id ) {
    return fetch( "/api/UncompleteTask",
        {
            method: "POST",
            body: JSON.stringify( { id } )
        } )
        .then( handleErrors );
}

export default api;
