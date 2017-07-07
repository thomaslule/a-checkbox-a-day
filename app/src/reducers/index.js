import { combineReducers } from "redux";
import tasks from "./tasks";
import error from "./error";

const log = ( state = {}, action ) => { console.log( "action", action ); return state; };

const todoApp = combineReducers( {
    log,
    tasks,
    error
} );

export default todoApp;
