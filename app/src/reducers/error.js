const error = (state = '', action) => {
  switch (action.type) {
    case 'ERROR':
      console.error(action.error);
      return action.error.message;
    default:
      return state;
  }
};

export default error;
