'use strict';
var module = angular.module('converter', []);
module.controller('controller', function ($scope, $http) {
    console.log('Converter controller');

    var data = {
        file: ''
    };

    $scope.convert = function () {

        $http.post('convert', data).then(
            function success(response) {

            },
            function fail(err) {

            }
        );

    };


});