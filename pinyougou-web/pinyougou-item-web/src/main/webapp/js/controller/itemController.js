/** 定义搜索控制器 */
app.controller("itemController", function ($scope) {

    /** 定义购买数量操作方法 */
    $scope.num =1;
    $scope.addNum = function(num){
        $scope.num += num;
        if ($scope.num < 1){
            $scope.num =1;
        }
    };

    /** 记录用户选择的规格选项 */
    $scope.specItems = {};
    /** 定义用户选择规格选项的方法 */
    $scope.selectSpec = function(key, value){
        $scope.specItems[key] = value;

        /** 查找对应的sku商品*/
        $scope.searchSku();
    };

    /** 判断规格选项是否被选中 */
    $scope.isSelected = function (key, value) {
        return $scope.specItems[key] == value;
    };

    $scope.loadSku = function () {
        //取第一个sku  ,经过排序后默认的sku
        //{"brand":"苹果","category":"手机","categoryid":560,
        // "createTime":1521297784000,"goodsId":149187842867973,
        // "id":1369326,"image":"http://image.pinyougou.com/jd/wKgMg1qtKEOATL9nAAFti6upbx4132.jpg",
        // "isDefault":"1","num":9999,"price":6688.00,"seller":"品优购","sellerId":"admin",
        // "spec":"{\"网络\":\"移动4G\",\"机身内存\":\"64G\"}",
        // "status":"1","title":"Apple iPhone 8 Plus (A1864) 移动4G 64G","updateTime":1521297784000}

        $scope.sku = itemList[0];
        /** 获取SKU商品选择的选项规格 */
        $scope.specItems = JSON.parse($scope.sku.spec);
    };

    /** 查找对应的sku商品 */
    $scope.searchSku = function () {
        for (var i = 0; i < itemList.length; i++) {
            /** 判断规格选项是否为用户选中的*/
            if (itemList[i].spec == JSON.stringify($scope.specItems)) {
                $scope.sku = itemList[i];
                return;
            }
        }
    };


    /** 加入购物车 */
    $scope.addToCat = function () {
        alert("已加入购物车");
    }
});
