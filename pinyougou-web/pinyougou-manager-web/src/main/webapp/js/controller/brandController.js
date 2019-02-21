/** 添加控制器 */
app.controller("brandController",function ($scope,$controller,baseService) {
    /** 指定继承baseController */
    $controller("baseController",{$scope:$scope});

    //创建searchEntity对象
    $scope.searchEntity = {};
    /** 分页查询品牌信息 */
    $scope.search = function (page,rows) {
        // alert($scope.searchEntity);
        baseService.findByPage("/brand/findByPage",page,rows,$scope.searchEntity).then(function (response) {
            //response : {total:"" ,rows[{},{},{}]} 这种格式
            //获取响应数据
            $scope.dataList = response.data.rows;
            //更新总页数
            $scope.paginationConf.totalItems=response.data.total;
        })
    };

    /** 添加或修改的方法 */
    $scope.addOrUpdate = function () {
        var url ="/save";
        if ($scope.entity.id){
            url ="/update"
        }
        baseService.sendGet("/brand"+ url,$scope.entity ).then(function (response) {
            if (response.data) {
                $scope.reload()
            }else {
                alert("亲,添加失败!")
            }
        });
    };
    /** 数据回显方法 */
    $scope.show = function (entity) {
        $scope.entity = JSON.parse(JSON.stringify(entity));
    };

    /** 删除方法 */
    $scope.delete = function () {
        if ($scope.ids.length == 0){
            alert("请选择您要删除的选项!");
        } else {
            if (confirm("您确定要删除吗?")) {
                baseService.deleteById("/brand/delete?ids="+$scope.ids).then(function (response) {
                    if (response.data) {
                        $scope.reload();
                    }else {
                        alert("删除出错!")
                    }
                })
            }
        }
    };

});
