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
    $scope.specificationItems = {};
    /** 定义用户选择规格选项的方法 */
    $scope.selectSpec = function(key, value){
        $scope.specificationItems[key] = value;

    };

    /** 判断规格选项是否被选中 */
    $scope.isSelected = function (key, value) {
        alert($scope.specificationItems[key] == value);

        return $scope.specificationItems[key] == value;
    }
});
