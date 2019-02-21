/** 添加控制器 */
app.controller("specificationController", function ($scope, $controller, baseService) {
    /** 指定继承baseController */
    $controller("baseController", {$scope: $scope});


    /** 分页查询 */
    $scope.searchEntity = {};
    $scope.search = function (page, rows) {
        baseService.findByPage("/specification/findByPage", page, rows, $scope.searchEntity).then(function (response) {
            $scope.dataList = response.data.rows;
            $scope.paginationConf.totalItems = response.data.total;
        })
    };


    // $scope.entity.specifications =[];
    //添加行
    $scope.addTableRows = function () {
        $scope.entity.specificationOptions.push({});
    };

    //删除行
    $scope.deleteTableRow = function (idx) {
        $scope.entity.specificationOptions.splice(idx, 1);
    };

    /** 保存和更新的方法 */
    $scope.saveOrUpdate = function () {
        var url = "/save";
        if ($scope.entity.id) {
            url = "/update"
        }
        baseService.sendPost("/specification" + url, $scope.entity).then(function (response) {
            if (response.data) {
                $scope.reload();
            } else {
                alert("添加/修改失败!")
            }
        })
    };

    $scope.delete = function () {
        if ($scope.ids.length == 0) {
            alert("请选择你要删除的选项!")
        } else {
            if (confirm("你确定要删除吗?")) {
                baseService.deleteById("/specification/delete",$scope.ids).then(function (response) {
                    if (response.data) {
                        $scope.reload();
                    }else{
                        alert("删除失败!")
                    }
                })
            }
        }
    }

});