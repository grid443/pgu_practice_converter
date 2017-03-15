'use strict';
var module = angular.module('converter', []);

module.config(function ($stateProvider) {
    $stateProvider
        .state('start', { //загрузка файлов, переход на convert
            url: '/start',
            views: {
                'convert': {
                    templateUrl: 'js/views/main/start.html',
                    controller: 'StartController'
                }
            }
        })
        .state('processing', { //проверка статуса переход в start(если тыркнули "назад"), success или fail
            url: '/processing',
            views: {
                'convert': {
                    templateUrl: 'js/views/main/processing.html',
                    controller: 'ProcessingController'
                }
            }
        })
        .state('success', { //отображение списка файлов, переход в start
            url: '/success',
            views: {
                'convert': {
                    templateUrl: 'js/views/main/success.html',
                    controller: 'SuccessController'
                }
            }
        })
        .state('list', { //по идее, это history
            url: '/list',
            views: {
                'convert': {
                    templateUrl: 'js/views/list.html',
                    controller: 'ListController'
                }
            }
        });
});