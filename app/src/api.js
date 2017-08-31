const api = {};

function handleErrors(response) {
  if (!response.ok) {
    throw response.statusText;
  }
  return response;
}

api.fetchItems = month =>
  fetch(`/api/Item/GetMonthItems/${month}`)
    .then(handleErrors)
    .then(response => response.json());

api.addItem = item =>
  fetch('/api/Item/AddItem',
    {
      method: 'POST',
      body: JSON.stringify(item),
    })
    .then(handleErrors)
    .then(response => response.json());

api.cancelItem = id =>
  fetch('/api/Item/CancelItem',
    {
      method: 'POST',
      body: JSON.stringify({ id }),
    })
    .then(handleErrors);

api.moveItem = (id, newMonth) =>
  fetch('/api/Item/MoveItem',
    {
      method: 'POST',
      body: JSON.stringify({ id, newMonth }),
    })
    .then(handleErrors);

api.restoreItem = id =>
  fetch('/api/Item/RestoreItem',
    {
      method: 'POST',
      body: JSON.stringify({ id }),
    })
    .then(handleErrors);

api.deleteItem = id =>
  fetch('/api/Item/DeleteItem',
    {
      method: 'POST',
      body: JSON.stringify({ id }),
    })
    .then(handleErrors);

api.completeTask = id =>
  fetch('/api/Item/CompleteTask',
    {
      method: 'POST',
      body: JSON.stringify({ id }),
    })
    .then(handleErrors);

api.uncompleteTask = id =>
  fetch('/api/Item/UncompleteTask',
    {
      method: 'POST',
      body: JSON.stringify({ id }),
    })
    .then(handleErrors);

api.changeItemText = (id, newText) =>
  fetch('/api/Item/ChangeItemText',
    {
      method: 'POST',
      body: JSON.stringify({ id, newText }),
    })
    .then(handleErrors);

api.fetchJournal = month =>
  fetch(`/api/Journal/GetMonthJournal/${month}`)
    .then(handleErrors)
    .then(response => response.json());

api.editJournalEntry = (day, newText) =>
  fetch('/api/Journal/EditJournalEntry',
    {
      method: 'POST',
      body: JSON.stringify({ day, text: newText }),
    })
    .then(handleErrors);

export default api;
