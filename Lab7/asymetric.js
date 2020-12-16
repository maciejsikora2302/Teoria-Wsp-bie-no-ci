const async = require('async');

var allWaitingTime = 0;

const AVAILABLE = 0;
const ACQUIRED = 1;

var N = 5;

var awaiting = []
for (var i = 0; i < N; i++) {
    awaiting.push(1);
}

const nextPower = id => {    
    awaiting[id] += 1;
    // console.log(`awaiting[${id}] == ${awaiting[id]}. Two to the power == ${2**awaiting[id]}`);
    return 2**awaiting[id];

}

const parallel = (tasks, callback) => {
    let i = tasks.length;

    tasks.forEach(func => func(() => {
        i--;
        if(i === 0) callback();
    }))
}


var Fork = function (id) {
    this.id = id;
    this.state = AVAILABLE;
    return this;
}

Fork.prototype.acquire = function (id, cb) {
    const attempt = (i, sum) => {

        if (this.state === AVAILABLE) {
            console.log(`Fork{${this.id}} acquired by Philosopher{${id}}`);
            allWaitingTime += sum;
            awaiting[id] = 1;

            this.state = ACQUIRED;
            cb();
        } else {
            console.log(`Fork number{${this.id}}, Philosopher{${id}} awaits for: ${nextPower(id)}`);
            const timeout = nextPower(id);
            setTimeout(() => attempt(i + 1, sum + timeout), timeout);
        }
    }

    setTimeout(() => attempt(0, 0), 1);
}

Fork.prototype.release = function () {
    console.log(`Fork{${this.id}} released`);
    this.state = AVAILABLE;
}

var Philosopher = function (id, forks) {
    this.id = id;
    this.forks = forks;
    this.f1 = id % forks.length;
    this.f2 = (id + 1) % forks.length;
    return this;
}

Philosopher.prototype.eat = function (cb) {
    console.log(`Philosopher{${this.id}} started eating`);
    setTimeout(cb, 1000);
}

Philosopher.prototype.startAsym = function (count, callback) {
    const forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;

    const acquireLeftFork = cb => forks[f1].acquire(id, cb);
    const acquireRightFork = cb => forks[f2].acquire(id, cb);
    const eat = cb => this.eat(cb);

    const tasks = id % 2 === 0 ? [acquireLeftFork, acquireRightFork, eat] : [acquireRightFork, acquireLeftFork, eat];

    const attempt = i => {
        if (i < count) {
            async.waterfall(
                tasks,
                (err, result) => {
                    console.log(`Philosopher ${this.id} finished eating`);
                    forks[f1].release();
                    forks[f2].release();
                    attempt(i + 1);
                }
            );
        } else {
            callback();
        }
    }

    setTimeout(() => attempt(0), 1);
}

const analyze = N => {
    const count = 3;
    const forks = [];
    const philosophers = []

    for (let i = 0; i < N; i++) {
        forks.push(new Fork(i));
    }

    for (let i = 0; i < N; i++) {
        philosophers.push(new Philosopher(i, forks));
    }

    const tasks = [];
    for (let i = 0; i < N; i++) {
        tasks.push(cb => philosophers[i].startAsym(count, cb))
    }

    parallel(tasks, () => console.log(`avg time waiting for N = ${N}: ${allWaitingTime / (N * count)} ms`));
}


analyze(5);
