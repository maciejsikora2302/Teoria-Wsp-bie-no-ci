var async = require('async');

// async.waterfall([
//   function (done) {
//     done(null, 'Value 1');
//   },
//   function (value1, done) {
//     console.log(value1);
//     done(null, 'Value 2');
//   }, function (value1, done) {
//     console.log(value1);
//     done(null, 'done');
//   }
// ], function (err) {
//   if (err) throw new Error(err);
// });

// arr = [test1, test, test]

function p(after){
    console.log(1)
    after(null, 1)
}

function p2(to_print, after){
    console.log(to_print%3+1)
    after(null, to_print+1)
}
function p3(to_print, after){
    console.log("done")
    // after(null, to_print+1)
}

// var functions = [p, p2, p2]

// async.waterfall(functions, function (err) {
//     if (err) throw new Error(err);
//     // console.log("something went wrong")
//   });


function loop(n){
    var fun = []
    var functions_start = [p, p2, p2]
    fun.push(p)
    fun.push(p2)
    fun.push(p2)
    for(i=0;i<n-1;i++){
        fun.push(p2)
        fun.push(p2)
        fun.push(p2)
    }
    fun.push(p2)
    fun.push(p2)
    fun.push(p2)
    fun.push(p3)
    async.waterfall(fun, function (err) {
        if (err) throw new Error(err);
        // console.log("something went wrong")
      });
}

loop(4)