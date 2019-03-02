/** 定义控制器层 */
app.controller('goodsController', function ($scope, $controller, baseService) {

    /** 指定继承baseController */
    $controller('baseController', {$scope: $scope});


    /** 商品管理带条件分页查询 */
    $scope.search = function (page,rows) {
        baseService.findByPage("/goods/findByPage",page,rows,$scope.searchEntity).then(function (response) {
            $scope.dataList = response.data.rows;
        //    更新每页显示记录数
            $scope.paginationConf.totalItems = response.data.total;
        })
    };

    //定义数组.将数字状态修改为中文系显示,
    $scope.status = ['未审核','已审核','审核未通过','关闭'];


    $scope.updateStatus = function (auditStatus) {
        baseService.sendGet("/goods/updateStatus?ids="+$scope.ids + "&auditStatus=" + auditStatus).then(function (response) {
            if (response.data) {
                $scope.reload();
            }else{
                alert("亲,操作失败!");
            }
        })
    };


    /**
     * 删除商品(修改is_delete的状态码)
     * @param status
     */
    $scope.delete = function (auditStatus) {
        baseService.sendGet("/goods/delete?ids=" + $scope.ids+"&auditStatus=" + auditStatus).then(function (response) {
            if (response.data) {
                $scope.reload();
            }else{
                alert("亲,操作失败!");
            }
        })
    }
});