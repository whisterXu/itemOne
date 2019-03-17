app.controller("baseController",function ($scope, $http) {

    /**
     *  获取用户登录用户名
     */
    $scope.loadUsername = function () {
        //重定向地址
        $scope.redirectUrl = encodeURIComponent(location.href);
        $http.get("/user/showName").then(function (response) {
            $scope.loginName = response.data.loginName;
        })
    }

});