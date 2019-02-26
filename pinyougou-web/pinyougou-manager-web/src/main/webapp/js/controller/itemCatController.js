/** 添加控制器 */
app.controller("itemCatController", function ($scope, $controller, baseService) {
    /** 指定继承baseController */
    $controller("baseController", {$scope: $scope});


    /**
     *  带parentId条件分页查询
     */
    /** 定义变量记录父级id */
    $scope.parentId = 0;
    $scope.findItemCatByParentId =function(parentId){
        $scope.parentId = parentId;
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
    /** 面包屑导航功能 */
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
        //调用分页查询方法
        $scope.findItemCatByParentId(entity.id)
    };

    /** 模板查询方法 */
    $scope.findTypeTemplateList = function () {
        baseService.sendGet("/typeTemplate/findTypeTemplateList").then(function (response) {
            $scope.typeTemplateList  = response.data;
        })
    };

    $scope.saveOrUpdate = function () {
        var url = "save";  //添加
        if ($scope.itemCat.id){
            url = "update"; //修改
        }else {
            /** 添加时设置父级id */
            $scope.itemCat.parentId = $scope.parentId;
        }
        // alert(itemCat_1.id);
        baseService.sendPost("/itemCat/" + url,$scope.itemCat).then(function (response) {
            if (response.data) {
             /** 重新加载数据 */
                $scope.findItemCatByParentId($scope.parentId);
                $scope.itemCat = null;
            }else {
                alert("操作失败!")
            }
        })
    };

    /**  修改分类前数据回显 */
    $scope.show = function (entity) {
        $scope.itemCat = JSON.parse(JSON.stringify(entity));
    };


    /** 删除分类 */

    $scope.delete = function () {
        baseService.deleteById("/itemCat/delete",$scope.ids).then(function (value) {
            if ($scope.ids == 0){
                alert("请选择你要删除的选项!");
            } else {
                if (value.data) {
                    $scope.findItemCatByParentId($scope.parentId);
                }else {
                    alert("亲,删除失败!");
                }
            }
        })
    }

});

