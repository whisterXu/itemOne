/** 添加控制器 */
app.controller("sellerCheckController",function ($scope,$controller,baseService) {
    /** 指定继承baseController */
    $controller("baseController",{$scope:$scope});
    /**
     * 带条件分页查询
     * @type {{status: number}}
     */
    $scope.searchEntity = {status:0};
    $scope.search = function (page, rows) {
        baseService.findByPage("/seller/findByPage",page,rows,$scope.searchEntity).then(function (response) {
            //response : {total:"" ,rows[{},{},{}]} 这种格式
            //获取响应数据
            $scope.dataList = response.data.rows;
            //更新总记录数
            $scope.paginationConf.totalItems = response.data.total;
        })
    };

    /** 数据回显方法 */
    $scope.show = function (entity) {
        $scope.entity = JSON.parse(JSON.stringify(entity));
    };

    /**
     * 修改审核状态码
     * @param sellerId
     * @param status
     */
    $scope.updateStatus = function (sellerId, status) {
        baseService.sendGet("/seller/updateStatus?sellerId="+sellerId +
            "&status=" + status).then(function (response) {
            if (response.data) {
                $scope.reload();
            }else {
                alert("审核失败!");
            }
        })
    }
});