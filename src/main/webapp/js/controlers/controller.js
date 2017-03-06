'use strict';
var module = angular.module('converter', []);
module.controller('convertController', function ($scope, $http) {
    console.log('Converter controller');

    var data = {
        file: ''
    };

    var requestCount = 0;

    $scope.state = 'start';

    $scope.convert = function () {
        $scope.state = 'convert';
        $scope.stateMessage = 'Обработка файлов началась. Это может занять несколько минут. Или часов...';
    };

    $scope.check = function () {
        if (requestCount < 2) {
            $scope.stateMessage = 'Обработка файлов ещё не закончилась. Это может занять несколько минут.';
            requestCount += 1;
            return;
        }

        $scope.state = 'success';
    };

    $scope.back = function () {
        $scope.state = 'start';
        requestCount = 0;
    };

    // $scope.convert = function () {
    //     $http.post('convert', data).then(
    //         function success(response) {
    //
    //         },
    //         function fail(err) {
    //
    //         }
    //     );
    // };

});