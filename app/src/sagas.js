import { call, put, takeEvery, takeLatest, fork } from "redux-saga/effects"
import actions from "./actions"
import api from "./api";

function* watchFetchTasks() {
    yield takeLatest( "FETCH_TASKS",
        function* ( action ) {
            const tasks = yield call( api.fetchTasks, action.month );
            yield put( actions.receiveTasks( tasks ) );
        } );
}

function* watchAddTask() {
    yield takeEvery( "ADD_TASK",
        function* ( action ) {
            const task = yield call( api.addTask, action.task );
            yield put( actions.taskAdded( task ) );
        } );
}

function* watchCompleteTask() {
    yield takeEvery( "COMPLETE_TASK",
        function* ( action ) {
            yield call( api.completeTask, action.id );
        } );
}

export default function* () {
    yield fork( watchFetchTasks );
    yield fork( watchAddTask );
    yield fork( watchCompleteTask );
};
