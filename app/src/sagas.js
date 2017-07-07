import { call, put, takeEvery, takeLatest, fork } from "redux-saga/effects"
import actions from "./actions"
import api from "./api";

function* watchFetchItems() {
    yield takeLatest( "FETCH_ITEMS",
        function* ( action ) {
            try {
                const items = yield call( api.fetchItems, action.month );
                yield put( actions.fetchItemsSuccess( items ) );
            } catch ( e ) {
                yield put( actions.error( e ) );
            }
        } );
}

function* watchAddItem() {
    yield takeEvery( "ADD_ITEM",
        function* ( action ) {
            try {
                const item = yield call( api.addItem, action.item );
                yield put( actions.addItemSuccess( item ) );
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
    yield fork( watchFetchItems );
    yield fork( watchAddItem );
    yield fork( watchCompleteTask );
};
