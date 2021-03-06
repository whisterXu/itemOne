//定义购物车控制器controller
app.controller("orderController",function ($scope,$interval,$location,$controller,baseService) {
    //继承baseController
    $controller("baseController",{$scope:$scope});
    $controller("cartController",{$scope:$scope});

    $scope.findAddress = function () {

        baseService.sendGet("/order/findAddress").then(function (response) {
            $scope.addressList = response.data;
            /** 循环用户地址集合 */
            $scope.address = $scope.addressList[0];
        })
    };

    /**
     *  记录用户选中的地址
     * @param item
     */
    $scope.selectedAddress = function (item) {
        $scope.address = item;
    };


    /**
     * 判断是否是用户选中的地址
     * @param address
     */
    $scope.isSelectedAddress = function (item) {
        return $scope.address == item;
    };

    $scope.order = {paymentType:1,receiverAreaName:"",receiverMobile:"",receiver:""};
    //选择支付方式
    $scope.selectPayType = function (payType) {
        $scope.order.paymentType= payType;
    };

    /**
     * 保存订单(创建订单)
     */
    $scope.saveOrder = function () {
        //收件人地址
        $scope.order.receiverAreaName = $scope.address.address;
        //收件人手机号码
        $scope.order.receiverMobile = $scope.address.mobile;
        //收件人
        $scope.order.receiver = $scope.address.contact;
        baseService.sendPost("/order/save",$scope.order ).then(function (response) {
            if (response.data) {
                if($scope.order.paymentType == 1){
                    location.href = "/order/pay.html";
                }else{
                    alert("操作失败!");
                }
            }
        })
    };

    //生成二维码
    $scope.genPayCode = function () {
        baseService.sendGet("/order/genPayCode").then(function (response) {
            /** 获取金额(转化成元) */
            $scope.money = (response.data.totalFee / 100).toFixed(2);
            /** 获取订单交易号 */
            $scope.outTradeNo = response.data.outTradeNo;
            // 支付二维码
            document.getElementById("qrCode").src="/barcode?url=" + response.data.codeUrl;

            var item = $interval(function () {
                baseService.sendGet("/order/queryPayStatus?outTradeNo=" + $scope.outTradeNo).then(function (response) {
                    //状态码为为1 :支付成功跳转到支付成功页面
                    $scope.totalFee = response.data.totalFee;
                    if (response.data.status == 1) {
                        //取消定时器
                        $interval.cancel(item);
                        location.href = "/order/paysuccess.html?totalMoney="+ $scope.totalFee / 100;
                    }

                    //状态码为为3:支付失败跳转到支付失败页面
                    if(response.data.status == 3){// 支付失败
                        // 取消定时器
                        $interval.cancel(timer);
                        location.href = "/order/payfail.html";
                    }

                })
            },3000,60);

        })
    };

    /** 获取支付总金额 */
    $scope.getMoney = function(){
        return $location.search().totalMoney;
    };

});