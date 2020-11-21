
function printAsync(s, cb) {
    var delay = Math.floor((Math.random()*300));
    setTimeout(function() {
        console.log(s);
        if (cb) cb();
    }, delay);
 }
 
 function task1(cb) {
     printAsync("1", function() {
         task2(cb);
     });
 }
 
 function task2(cb) {
     printAsync("2", function() {
         task3(cb);
     });
 }
 
 function task3(cb) {
     printAsync("3", cb);
 }
 
 /* 
 ** Zadanie:
 ** Napisz funkcje loop(n), ktora powoduje wykonanie powyzszej 
 ** sekwencji zadan n razy. Czyli: 1 2 3 1 2 3 1 2 3 ... done
 ** 
 */
 
 function getFirstTask(depth, cb){
     if(depth>0){ 
         return function(){task1(getFirstTask(depth-1, cb))}
     }
     return cb;
 }
 
 function loop(n){
     task1(getFirstTask(n, function(){console.log("done")}))
 }
 
 loop(4);
 