'use strict';
var module = angular.module('converter', []);
module.controller('ConvertController', function ($scope, $http) {
    console.log('Converter controller');

    var data = {
        file: ''
    };

    var requestCount = 0;

    $scope.state = 'start';

    $scope.convert = function () {
        $scope.state = 'convert';
        $scope.stateMessage = 'Обработка файлов началась. Это может занять несколько минут.';
    };

    $scope.check = function () {
        //n - от 0 до 5
        //Math.random() * (5 - 0) + 0;
        var n = Math.round(Math.random() * 5);

        if (n == 4) {
            $scope.stateMessage = 'Во время обработки файлов произошла ошибка';
            //TODO задизаблить кнопку проверки статуса
            var btn = angular.element(document.querySelector(".check-btn"));
            btn.addClass("disabled");
        }

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