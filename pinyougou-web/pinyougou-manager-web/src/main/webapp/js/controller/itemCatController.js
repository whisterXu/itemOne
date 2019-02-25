/** 添加控制器 */
app.controller("itemCatController", function ($scope, $controller, baseService) {
    /** 指定继承baseController */
    $controller("baseController", {$scope: $scope});


    /**
     *  带parentId条件分页查询
     */
    $scope.findItemCatByParentId =function(parentId){
            $scope.search = function (page, rows) {
                // alert($scope.parentId);
                baseService.sendGet("/itemCat/findByPage?page=" +page + "&rows=" + rows +"&parentId="+ parentId ).then(function (response) {
                    $scope.dataList = response.data.rows;
                    //更新总记录数
                    $scope.paginationConf.totalItems = response.data.total;
                })
            };
        $scope.reload();
    };

    //定义级别变量
    $scope.grade = 1;
    $scope.selectList = function (entity,grade) {
        //把grade放入$scope中
        $scope.grade = grade;
        if (grade == 1 ) {   //如果是一级
            $scope.itemCat_1 = null;
            $scope.itemCat_2 = null;
        }else if(grade == 2 ){   //如果是二级
            $scope.itemCat_1 = entity;
            $scope.itemCat_2 = null;
        }else if(grade == 3 ){  //如果是三级
            $scope.itemCat_2 = entity;
        }
        $scope.findItemCatByParentId(entity.id)
    }
});

