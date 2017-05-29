import React from "react";
import {
    BrowserRouter as Router,
    Route
} from "react-router-dom"
import TaskList from "./TaskList";
import MonthPicker from "./MonthPicker";
import "./App.css";
import "bootstrap/dist/css/bootstrap.css";

class App extends React.Component {

    render() {
        return (
            <Router>
                <div>
                    <nav className="navbar navbar-default navbar-static-top">
                        <div className="container">
                            <span className="navbar-brand">A checkbox a day</span>
                        </div>
                    </nav>
                    <div className="container">
                        <Route path="/:month" component={MonthPicker} />
                        <Route path="/:month" component={TaskList} />
                    </div>
                </div>
            </Router>
        );
    }

}

export default App;
