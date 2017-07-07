let actions = {};

actions.fetchItems = ( month ) => ( {
    type: "FETCH_ITEMS",
    month
} );

actions.fetchItemsSuccess = ( items ) => ( {
    type: "FETCH_ITEMS_SUCCESS",
    items
} );

actions.addItem = ( item ) => ( {
    type: "ADD_ITEM",
    item
} );

actions.addItemSuccess = ( item ) => ( {
    type: "ADD_ITEM_SUCCESS",
    item
} );

actions.completeTask = ( id ) => ( {
    type: "COMPLETE_TASK",
    id
} );

actions.error = (error) => ({
    type: "ERROR",
    error
});

export default actions;
