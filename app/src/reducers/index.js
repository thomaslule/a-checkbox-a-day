import { combineReducers } from "redux";
import items from "./items";
import journal from "./journal";
import error from "./error";

const log = (state = {}, action) => { console.log("action", action); return state; };

const root = combineReducers({
    log,
    items,
    journal,
    error
});

export default root;
