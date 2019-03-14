/** 定义控制器层 */
app.controller('userController', function ($scope,$timeout, baseService) {


    /** 用户注册,添加用户信息 */
    $scope.user = {};
    $scope.save = function () {
        if ($scope.user.password == $scope.password && $scope.user.username != null && $scope.user.username!= '') {
            // alert("请求成功!");
            // alert($scope.smsCode);
            baseService.sendPost("/user/save?smsCode=" + $scope.smsCode, $scope.user).then(function (response) {
                if (response.data) {
                    alert("亲,注册成功!");
                    $scope.user = {};
                    $scope.password = "";
                    location.href = "http://www.pinyougou.com";
                } else {
                    alert("亲,注册失败!");
                }
            })
        } else {
            alert("两次密码不一致!")
        }
    };


    /** 发送短信验证码 */
    $scope.sendCode = function () {
        //判断手机号码是否正确
        if ($scope.user.phone && /^1[3|5|6|7|8|9]\d{9}$/.test($scope.user.phone)){
            //发送异步请求
            if ($scope.user.phone){
                baseService.sendGet("/user/sendCode?phone="+ $scope.user.phone).then(function (response) {
                    if (response.data){
                        //倒计时
                        $scope.downTime(60);
                    }else{
                        alert("发送验证码失败!")
                    }
                })
            }
        }else{
            alert("手机号码不正确!");
        }
    };


    $scope.disable = false;
    $scope.textTip = "获取短信验证码";
    //倒计时
    $scope.downTime = function (time) {
        //判断倒计时间
        if (time > 0 ){
            //设置按钮不可用
                $scope.disable = true;
                //时间减1秒
                time--;
                //显示文本修改内容
                $scope.textTip = time + "秒后重新发送";
                //调用angularJS的计时方法我
                $timeout(function () {
                    $scope.downTime(time)
                },1000);
            }else {
            //如果时间小于O则恢复按钮和文本内容
            $scope.disable = false;
            $scope.textTip = "获取短信验证码";
             }
        }
});