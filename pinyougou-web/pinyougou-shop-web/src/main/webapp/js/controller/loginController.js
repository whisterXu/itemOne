/** 添加控制器 */
app.controller("loginController", function ($scope, $controller, baseService) {
    /** 指定继承baseController */
    $controller("baseController", {$scope: $scope});
    // 获取登录用户名
    $scope.showLoginName = function () {
        baseService.sendGet("/showLoginName")
            .then(function(response){
                // 获取响应数据
                $scope.loginName = response.data.loginName;
            });
    }

});