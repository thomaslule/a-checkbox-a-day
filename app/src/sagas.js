import { call, put, takeEvery, takeLatest, fork } from "redux-saga/effects"
import actions from "./actions"
import api from "./api";

function* watchFetchItems() {
    yield takeLatest("FETCH_ITEMS",
        function* (action) {
            try {
                const items = yield call(api.fetchItems, action.month);
                yield put(actions.fetchItemsSuccess(items));
            } catch (e) {
                yield put(actions.error(e));
            }
        });
}

function* watchAddItem() {
    yield takeEvery("ADD_ITEM",
        function* (action) {
            try {
                const item = yield call(api.addItem, action.item);
                yield put(actions.addItemSuccess(item));
            } catch (e) {
                yield put(actions.error(e));
            }
        });
}

function* watchCancelItem() {
    yield takeEvery("CANCEL_ITEM",
        function* (action) {
            try {
                yield call(api.cancelItem, action.id);
            } catch (e) {
                yield put(actions.error(e));
            }
        });
}

function* watchRestoreItem() {
    yield takeEvery("RESTORE_ITEM",
        function* (action) {
            try {
                yield call(api.restoreItem, action.id);
            } catch (e) {
                yield put(actions.error(e));
            }
        });
}

function* watchDeleteItem() {
    yield takeEvery("DELETE_ITEM",
        function* (action) {
            try {
                yield call(api.deleteItem, action.id);
            } catch (e) {
                yield put(actions.error(e));
            }
        });
}

function* watchCompleteTask() {
    yield takeEvery("COMPLETE_TASK",
        function* (action) {
            try {
                yield call(api.completeTask, action.id);
            } catch (e) {
                yield put(actions.error(e));
            }
        });
}

function* watchUncompleteTask() {
    yield takeEvery("UNCOMPLETE_TASK",
        function* (action) {
            try {
                yield call(api.uncompleteTask, action.id);
            } catch (e) {
                yield put(actions.error(e));
            }
        });
}

function* watchChanteItemText() {
    yield takeEvery("CHANGE_ITEM_TEXT",
        function* (action) {
            try {
                yield call(api.changeItemText, action.id, action.newText);
            } catch (e) {
                yield put(actions.error(e));
            }
        });
}

function* watchFetchJournal() {
    yield takeEvery("FETCH_JOURNAL",
        function* (action) {
            try {
                const journal = yield call(api.fetchJournal, action.month);
                yield put(actions.fetchJournalSuccess(journal));
            } catch (e) {
                yield put(actions.error(e));
            }
        });
}

function* watchEditJournalEntry() {
    yield takeEvery("EDIT_JOURNAL_ENTRY",
        function* (action) {
            try {
                yield call(api.editJournalEntry, action.day, action.newText);
            } catch (e) {
                yield put(actions.error(e));
            }
        });
}

export default function* () {
    yield fork(watchFetchItems);
    yield fork(watchAddItem);
    yield fork(watchCancelItem);
    yield fork(watchRestoreItem);
    yield fork(watchDeleteItem);
    yield fork(watchCompleteTask);
    yield fork(watchUncompleteTask);
    yield fork(watchChanteItemText);
    yield fork(watchFetchJournal);
    yield fork(watchEditJournalEntry);
};
