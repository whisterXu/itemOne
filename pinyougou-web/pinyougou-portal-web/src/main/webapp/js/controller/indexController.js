/** 定义首页控制器层 */
app.controller("indexController", function($scope,$controller,baseService){
    $controller("baseController",{$scope: $scope})

    $scope.findCategoryByCategoryId = function (id) {
        baseService.sendGet("/findCategoryByCategoryId?categoryId="+id).then(function (response) {
            $scope.contentList = response.data;
            // alert("取到了数据:" + $scope.contentList);
        })
    };

    $scope.search = function () {
        var keyword = $scope.searchParams.keyword ? $scope.searchParams.keyword : '';
        location.href="http://search.pinyougou.com?keyword=" + keyword;
    }
});