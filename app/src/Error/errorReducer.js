export default (state = '', action) => {
  switch (action.type) {
    case 'ERROR':
      return action.error.message;
    default:
      return state;
  }
};
