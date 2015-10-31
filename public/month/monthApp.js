angular.module("month", [ "journalEntry", "item", "ngRoute" ])
.config(["$routeProvider", function($routeProvider) {
  
  $routeProvider.when("/month/:month", {
    templateUrl: "month/month.html",
    controller: "monthCtrl"
  });

}]);
