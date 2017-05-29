import React from "react";
import TaskList from "./TaskList";
import "./App.css";
import "bootstrap/dist/css/bootstrap.css";

class App extends React.Component {

    render() {
        return (
            <div>
                <nav className="navbar navbar-default">
                    <div className="container">
                        <div className="row">
                            <div className="col-xs-offset-4 col-xs-5"><span className="navbar-brand">A checkbox a day</span></div>
                        </div>
                    </div>
                </nav>
                <TaskList />
            </div>
        );
    }

}

export default App;
