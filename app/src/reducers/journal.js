const journal = (state = [], action) => {
    switch (action.type) {
        case "FETCH_JOURNAL_SUCCESS":
            return action.journal;
        case "EDIT_JOURNAL_ENTRY":
            return state.map(entry =>
                (entry.day === action.day)
                    ? { ...entry, text: action.newText }
                    : entry
            )
        default:
            return state
    }
}

export default journal
