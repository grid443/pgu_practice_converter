'use strict';
var module = angular.module('converter', []);
module.controller('ConvertController', function ($scope, $http) {
    console.log('Converter controller');

    //Свойство StartController
    var data = {
        file: ''
    };

    //Свойство ProcessingController
    var requestCount = 0;

    //Больше не используем, т.к. заменяем на angular-ui-router
    $scope.state = 'start';

    //Метод StartController
    $scope.convert = function () {
        $scope.state = 'convert'; // $state.goMode('processing');
        $scope.stateMessage = 'Обработка файлов началась. Это может занять несколько минут.';
    };

    //Метод StartController
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

    //Метод ProcessingController
    $scope.check = function () {
        //n - от 0 до 5
        //Math.random() * (5 - 0) + 0;
        var n = Math.round(Math.random() * 5);

        if (n == 4) {
            $scope.stateMessage = 'Во время обработки файлов произошла ошибка';

            //TODO задизаблить кнопку проверки статуса
            //Мне кажется, что лучше сделать на шаблоне тэгом ng-disabled,
            //ибо так более понятно
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

    //Общий метод всех контроллеров, кроме StartController
    $scope.back = function () {
        $scope.state = 'start';
        requestCount = 0;
    };

});