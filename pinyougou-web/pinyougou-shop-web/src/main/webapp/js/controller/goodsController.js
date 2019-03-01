/** 定义控制器层 */
app.controller('goodsController', function ($scope, $controller, baseService) {

    /** 指定继承baseController */
    $controller('baseController', {$scope: $scope});

    /** 添加 */
    $scope.saveOrUpdate = function () {
        $scope.goods.goodsDesc.introduction = editor.html();
        /** 发送post请求 */
        baseService.sendPost("/goods/save", $scope.goods)
            .then(function (response) {
                if (response.data) {
                    /** 重新加载数据 */
                    $scope.goods = {};
                    editor.html("");
                } else {
                    alert("操作失败！");
                }
            });
    };

    /** 上传图片到FastDFS */
    $scope.upload = function () {
        baseService.uploadFile().then(function (response) {
            if (response.data.status == 200) {
                /** 设置图片访问地址 */
                $scope.picEntity.url = response.data.url;
            } else {
                alert("上传失败!");
            }
        })
    };

    /** 添加图片 */
    $scope.goods = {goodsDesc: {itemImages: []}};
    $scope.addPic = function () {
        $scope.goods.goodsDesc.itemImages.push($scope.picEntity);
    };
    /** 删除图片 */
    $scope.removePic = function (idx) {
        $scope.goods.goodsDesc.itemImages.splice(idx, 1);
    };


    /** 根据parentId查询显示数据 */
    $scope.findItemCatByParentId = function (parentId, name) {
        baseService.sendGet("/itemCat/findItemCatByParentId?parentId=" + parentId).then(function (response) {
            $scope[name] = response.data;
        });
    };

    /** 监控 goods.category1Id 变量，查询二级分类 */
    $scope.$watch('goods.category1Id', function (newValue, oldValue) {
        // alert("newValue=" + newValue +";oldValue=" + oldValue);
        if (newValue) {
            $scope.findItemCatByParentId(newValue, "itemCat2")
        } else {
            $scope.itemCat2 = [];
        }

    });

    /** 监控 goods.category2Id 变量，查询三级分类 */
    $scope.$watch("goods.category2Id", function (newValue, oldValue) {
        if (newValue) {
            $scope.findItemCatByParentId(newValue, "itemCat3");
        } else {
            $scope.itemCat3 = [];
        }
    });

    /** 监控 goods.category3Id 变量，查询分类模板ID */
    $scope.$watch("goods.category3Id", function (newValue, oldValue) {
        if (newValue) {
            for (var i = 0; i < $scope.itemCat3.length; i++) {
                var itemCat = $scope.itemCat3[i];
                if (itemCat.id == newValue) {
                    $scope.goods.typeTemplateId = itemCat.typeId;
                }
            }
        } else {
            $scope.goods.typeTemplateId = null;
        }
    });

    /** 监控 goods.category3Id 变量，查询分类模板ID */
    $scope.$watch("goods.typeTemplateId", function (newValue, oldValue) {
        if (newValue) {
            //根据模板ID查询模板对象(查询一行数据)
            baseService.sendGet("/typeTemplate/findOne?id=" + newValue).then(function (response) {
                $scope.brandIds = JSON.parse(response.data.brandIds);
                $scope.goods.goodsDesc.customAttributeItems = JSON.parse(response.data.customAttributeItems);
            });

        //    前端需要的格式:[{"id":27,"text":"网络",options:[{},{}]},{"id":32,"text":"机身内存",options:[{},{}]}]
            baseService.sendGet("/typeTemplate/findSpecByTypeTemplateId?id=" + newValue).then(function (response) {
                $scope.specList = response.data;
            });
        }else {
            $scope.brandIds=[];
        }
});

//[{"attributeValue":["移动4G","联通4G","电信4G"],"attributeName":"网络"}]





/** 查询条件对象 */
$scope.searchEntity = {};
/** 分页查询(查询条件) */
$scope.search = function (page, rows) {
    baseService.findByPage("/goods/findByPage", page,
        rows, $scope.searchEntity)
        .then(function (response) {
            /** 获取分页查询结果 */
            $scope.dataList = response.data.rows;
            /** 更新分页总记录数 */
            $scope.paginationConf.totalItems = response.data.total;
        });
};


/** 显示修改 */
$scope.show = function (entity) {
    /** 把json对象转化成一个新的json对象 */
    $scope.entity = JSON.parse(JSON.stringify(entity));
};

/** 批量删除 */
$scope.delete = function () {
    if ($scope.ids.length > 0) {
        baseService.deleteById("/goods/delete", $scope.ids)
            .then(function (response) {
                if (response.data) {
                    /** 重新加载数据 */
                    $scope.reload();
                } else {
                    alert("删除失败！");
                }
            });
    } else {
        alert("请选择要删除的记录！");
    }
};
})
;