import { combineReducers } from "redux";
import tasks from "./tasks";

const todoApp = combineReducers( {
    tasks
} );

export default todoApp;
