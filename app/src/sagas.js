import { call, put, takeEvery, takeLatest, fork } from 'redux-saga/effects'
import { receiveTasks, taskAdded } from './actions'
import Api from "./api";

function* callFetchTasks( action ) {
    const tasks = yield call( Api.fetchTasks, action.month );
    yield put( receiveTasks( tasks ) );
}

function* callAddTask( action ) {
    const task = yield call( Api.addTask, action.task );
    yield put( taskAdded( task ) );
}

function* callCompleteTask( action ) {
    yield call( Api.completeTask, action.id );
}

function* mySaga() {
    yield takeLatest( "FETCH_TASKS", callFetchTasks );
}

function* mySaga2() {
    yield takeEvery( "ADD_TASK", callAddTask );
}

function* mySaga3() {
    yield takeEvery( "COMPLETE_TASK", callCompleteTask );
}

function* root() {
    yield fork( mySaga );
    yield fork( mySaga2 );
    yield fork( mySaga3 );
}

export default root;
