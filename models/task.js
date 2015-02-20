function Task(name, done) {
    this._name = name;
    this._done = typeof(done) === 'undefined' ? false : done;
}

module.exports = Task;