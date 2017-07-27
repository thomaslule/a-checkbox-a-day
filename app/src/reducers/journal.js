const journal = (state = [], action) => {
    switch (action.type) {
        case "FETCH_JOURNAL_SUCCESS":
            return action.journal;
        default:
            return state
    }
}

export default journal
