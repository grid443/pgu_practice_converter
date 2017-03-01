'use strict';
var module = angular.module('converter', []);
module.controller('controller', function ($scope, $http) {
    var data = {
        file: ''
    };

    $http.post('convert', data).then(
        function success(response) {

        },
        function fail(err) {

        }
    );

});