    /** 创建控制器 */
app.controller("sellerController",function ($scope, $controller, baseService) {
    /** 继承baseController */
    $controller("baseController",{$scope:$scope});


});