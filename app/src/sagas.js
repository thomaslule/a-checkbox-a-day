import { call, put, takeEvery, takeLatest, fork } from "redux-saga/effects"
import actions from "./actions"
import api from "./api";

function* watchFetchTasks() {
    yield takeLatest( "FETCH_TASKS",
        function* ( action ) {
            try {
                const tasks = yield call( api.fetchTasks, action.month );
                yield put( actions.fetchTasksSuccess( tasks ) );
            } catch ( e ) {
                yield put( actions.error( e ) );
            }
        } );
}

function* watchAddTask() {
    yield takeEvery( "ADD_TASK",
        function* ( action ) {
            try {
                const task = yield call( api.addTask, action.task );
                yield put( actions.addTaskSuccess( task ) );
            } catch ( e ) {
                yield put( actions.error( e ) );
            }
        } );
}

function* watchCompleteTask() {
    yield takeEvery( "COMPLETE_TASK",
        function* ( action ) {
            try {
                yield call( api.completeTask, action.id );
            } catch ( e ) {
                yield put( actions.error( e ) );
            }
        } );
}

export default function* () {
    yield fork( watchFetchTasks );
    yield fork( watchAddTask );
    yield fork( watchCompleteTask );
};
