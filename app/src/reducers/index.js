import { combineReducers } from "redux";
import items from "./items";
import error from "./error";

const log = ( state = {}, action ) => { console.log( "action", action ); return state; };

const root = combineReducers( {
    log,
    items,
    error
} );

export default root;
