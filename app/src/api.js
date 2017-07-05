export default {

    fetchTasks: month => fetch( "/api/Tasks/" + month )
        .then( response => response.json() ),

    addTask: task => fetch( "/api/AddTask", {
        method: "POST",
        body: JSON.stringify( task )
    } )
        .then( response => response.json() ),

    completeTask: id => fetch( "/api/CompleteTask", {
        method: "POST",
        body: JSON.stringify( { id } )
    } )

}
