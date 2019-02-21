/** 添加控制器 */
app.controller("specificationController",function ($scope,$controller,baseService) {
    /** 指定继承baseController */
    $controller("baseController",{$scope:$scope});



    /** 分页查询 */
    $scope.searchEntity = {};
    $scope.search = function(page,rows){
        baseService.findByPage("/specification/findByPage",page,rows,$scope.searchEntity).then(function (response) {
            $scope.dataList = response.data.rows;
            $scope.paginationConf.totalItems=response.data.total;
        })
    };


    //添加行
    $scope.addTableRows = function () {
        $scope.entity.specifications.push({});
    };

    //删除行
    $scope.deleteTableRow = function (index) {
        $scope.entity.specifications.splice(index,1);
    };

    $scope.saveOrUpdate=function () {
        var url ="/save";
        if ($scope.entity.id) {
            url = "/update"
        }
        baseService.sendGet("/specification"+url,$scope.entity).then(function (response) {
            if (response.data) {
                $scope.reload();
            }else {
                alert("添加/修改失败!")
            }
        })
    }
});