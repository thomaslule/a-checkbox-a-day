export default {
    
    fetchTasks: month => fetch( "/api/Tasks/" + month )
        .then( response => response.json() )
        .then(tasks => tasks)
}
