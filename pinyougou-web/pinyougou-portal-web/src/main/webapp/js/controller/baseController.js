app.controller("baseController", function ($scope,baseService) {


    $scope.loadUsername = function () {
        //定义重定向URL
        $scope.redirectUrl = window.encodeURIComponent(location.href);
        baseService.sendGet("/user/showName").then(function (response) {
            $scope.loginName = response.data.loginName;
        })
    }
});