import { combineReducers } from "redux"
import tasks from "./tasks"
import month from "./month"

const todoApp = combineReducers( {
    tasks,
    month
} )

export default todoApp
