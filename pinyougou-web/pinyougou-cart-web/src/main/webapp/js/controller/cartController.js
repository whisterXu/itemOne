//定义购物车控制器controller
app.controller("cartController",function ($scope, $controller,baseService) {
    //继承baseController
    $controller("baseController",{$scope:$scope});


    /**
     * 添加购物车的方法
     */
    $scope.addCart = function(itemId,num){
        baseService.sendGet("/cart/addCart?itemId=" + itemId + "&num=" +num).then(function (response) {
            if (response.data) {
                $scope.findCart();
            }
        })
    };

    /**
     * 查询购物车数据
     */
    $scope.findCart= function () {
        baseService.sendGet("/cart/findCart").then(function (response) {
            //获得相应数据
            $scope.cartList = response.data;

            //购物车商品总数和总金额计算
            $scope.total();
        })
    };

    /**
     * 购物车商品总数和总金额计算
     */
    $scope.total = function(){
        $scope.totalEntity = {totalNum : 0, totalMoney : 0.00};
        for (var i = 0; i < $scope.cartList.length; i++) {
            var cart = $scope.cartList[i];
            for (var j = 0; j < cart.orderItems.length ; j++) {
                var orderItem = cart.orderItems[j];
                $scope.totalEntity.totalNum += orderItem.num;
                $scope.totalEntity.totalMoney += orderItem.totalFee;
            }
        }
    }



});