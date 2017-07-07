const items = ( state = [], action ) => {
    switch ( action.type ) {
        case "FETCH_ITEMS_SUCCESS":
            return action.items;
        case "ADD_ITEM_SUCCESS":
            return [
                ...state,
                action.item
            ]
        case "COMPLETE_TASK":
            return state.map( item =>
                ( item.id === action.id )
                    ? { ...item, completed: true }
                    : item
            )
        default:
            return state
    }
}

export default items
