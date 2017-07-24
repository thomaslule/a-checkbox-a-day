let api = {};

function handleErrors(response) {
    if (!response.ok) {
        throw Error(response.statusText);
    }
    return response;
}

api.fetchItems = function( month ) {
    return fetch( "/api/Item/GetMonthItems/" + month )
        .then( handleErrors )
        .then( response => response.json() );
}

api.addItem = function( item ) {
    return fetch( "/api/Item/AddItem",
        {
            method: "POST",
            body: JSON.stringify( item )
        } )
        .then( handleErrors )
        .then( response => response.json() );
}

api.cancelItem = function( id ) {
    return fetch( "/api/Item/CancelItem",
        {
            method: "POST",
            body: JSON.stringify( { id } )
        } )
        .then( handleErrors );
}

api.restoreItem = function( id ) {
    return fetch( "/api/Item/RestoreItem",
        {
            method: "POST",
            body: JSON.stringify( { id } )
        } )
        .then( handleErrors );
}

api.deleteItem = function( id ) {
    return fetch( "/api/Item/DeleteItem",
        {
            method: "POST",
            body: JSON.stringify( { id } )
        } )
        .then( handleErrors );
}

api.completeTask = function( id ) {
    return fetch( "/api/Item/CompleteTask",
        {
            method: "POST",
            body: JSON.stringify( { id } )
        } )
        .then( handleErrors );
}

api.uncompleteTask = function( id ) {
    return fetch( "/api/Item/UncompleteTask",
        {
            method: "POST",
            body: JSON.stringify( { id } )
        } )
        .then( handleErrors );
}

api.changeItemText = function( id, newText ) {
    return fetch( "/api/Item/ChangeItemText",
        {
            method: "POST",
            body: JSON.stringify( { id, newText } )
        } )
        .then( handleErrors );
}

export default api;
