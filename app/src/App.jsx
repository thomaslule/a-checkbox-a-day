import React from "react";
import TaskList from "./TaskList";
import MonthPicker from "./MonthPicker";
import "./App.css";
import "bootstrap/dist/css/bootstrap.css";

class App extends React.Component {

    render() {
        return (
            <div>
                <nav className="navbar navbar-default navbar-static-top">
                    <div className="container">
                        <span className="navbar-brand">A checkbox a day</span>
                    </div>
                </nav>
                <div className="container">
                    <MonthPicker />
                    <TaskList />
                </div>
            </div>
        );
    }

}

export default App;
