app.controller("baseController",function ($scope,baseService) {

    /**
     * 获取用户登录用户名
     */
    $scope.loadUsername = function () {
         $scope.redirectUrl = encodeURIComponent(location.href)
        baseService.sendGet("/user/showName").then(function (response) {
             $scope.loginName = response.data.loginName;
        })
    }
});