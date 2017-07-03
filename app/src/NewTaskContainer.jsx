import React from "react";
import NewTask from "./NewTask";

class NewTaskContainer extends React.Component {

    render() {
        return (
            <NewTask
                onAddTask={( todo ) => this.handleAddTask( todo )}
            /> );
    }

    handleAddTask( todo ) {
        fetch( "/api/AddTask",
            {
                method: "POST",
                body: JSON.stringify( { todo: todo, month: this.props.month } )
            } )
            .then(() => {
                this.props.onAddTask();
            } )
            .catch( error => console.warn( error ) );
    }

}

export default NewTaskContainer;
