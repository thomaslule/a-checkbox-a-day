import { call, put, takeLatest } from 'redux-saga/effects'
import { receiveTasks } from './actions'
import Api from "./api";

function* fetchTasks( action ) {
    try {
        const tasks = yield call( Api.fetchTasks, action.month );
        yield put( receiveTasks(tasks) );
    } catch ( e ) {
        yield put( { type: "FETCH_TASKS_FAILED", message: e.message } ); // TODO
    }
}

function* mySaga() {
    yield takeLatest( "FETCH_TASKS", fetchTasks );
}

export default mySaga;
