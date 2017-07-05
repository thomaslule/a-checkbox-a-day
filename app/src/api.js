export default {
    
    fetchTasks: month => fetch( "/api/Tasks/" + month )
        .then( response => response.json() )
        .then(tasks => tasks),
        
    addTask: task => fetch("/api/AddTask", {
        method: "POST",
        body: JSON.stringify(task)
    }),
    
    completeTask: id => fetch("/api/CompleteTask", {
        method: "POST",
        body: JSON.stringify({ id })
    })
    
}
