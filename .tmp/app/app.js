'use strict';

angular.module('mdApp', ['mdApp.auth', 'mdApp.admin', 'mdApp.constants', 'ngCookies', 'ngResource', 'ngSanitize', 'btford.socket-io', 'ui.router', 'ui.bootstrap', 'validation.match']).config(function ($urlRouterProvider, $locationProvider) {
  $urlRouterProvider.otherwise('/');

  $locationProvider.html5Mode(true);
});
//# sourceMappingURL=app.js.map
