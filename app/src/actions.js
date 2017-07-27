let actions = {};

actions.fetchItems = (month) => ({
    type: "FETCH_ITEMS",
    month
});

actions.fetchItemsSuccess = (items) => ({
    type: "FETCH_ITEMS_SUCCESS",
    items
});

actions.addItem = (item) => ({
    type: "ADD_ITEM",
    item
});

actions.addItemSuccess = (item) => ({
    type: "ADD_ITEM_SUCCESS",
    item
});

actions.cancelItem = (id) => ({
    type: "CANCEL_ITEM",
    id
});

actions.restoreItem = (id) => ({
    type: "RESTORE_ITEM",
    id
});

actions.deleteItem = (id) => ({
    type: "DELETE_ITEM",
    id
});

actions.completeTask = (id) => ({
    type: "COMPLETE_TASK",
    id
});

actions.uncompleteTask = (id) => ({
    type: "UNCOMPLETE_TASK",
    id
});

actions.changeItemText = (id, newText) => ({
    type: "CHANGE_ITEM_TEXT",
    id,
    newText
});

actions.fetchJournal = (month) => ({
    type: "FETCH_JOURNAL",
    month
});

actions.fetchJournalSuccess = (journal) => ({
    type: "FETCH_JOURNAL_SUCCESS",
    journal
});

actions.editJournalEntry = (day, newText) => ({
    type: "EDIT_JOURNAL_ENTRY",
    day,
    newText
});

actions.error = (error) => ({
    type: "ERROR",
    error
});

export default actions;
