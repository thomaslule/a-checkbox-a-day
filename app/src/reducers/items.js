const items = ( state = [], action ) => {
    switch ( action.type ) {
        case "FETCH_ITEMS_SUCCESS":
            return action.items;
        case "ADD_ITEM_SUCCESS":
            return [
                ...state,
                action.item
            ]
        case "CANCEL_ITEM":
            return state.map( item =>
                ( item.id === action.id )
                    ? { ...item, cancelled: true }
                    : item
            )
        case "RESTORE_ITEM":
            return state.map( item =>
                ( item.id === action.id )
                    ? { ...item, cancelled: false }
                    : item
            )
        case "COMPLETE_TASK":
            return state.map( item =>
                ( item.id === action.id )
                    ? { ...item, completed: true }
                    : item
            )
        case "UNCOMPLETE_TASK":
            return state.map( item =>
                ( item.id === action.id )
                    ? { ...item, completed: false }
                    : item
            )
        default:
            return state
    }
}

export default items
