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
    //定义数据储存格式
    $scope.goods = {goodsDesc: {itemImages: [], specificationItems: []}};
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
            //当模板ID发生改变的时候清空后面规格选项的值
            $scope.goods.goodsDesc.specificationItems = [];
            $scope.goods.items = [];
        } else {
            $scope.brandIds = [];
        }
    });

//     $scope.goods.goodsDesc.specificationItems = [];
//[{"attributeValue":["移动4G","联通4G","电信4G"],"attributeName":"网络"}]
    $scope.updateSpecAttr = function ($event, specName, optionName) {
        /** 根据json对象的key到json数组中搜索该key对应的对象 */
        var json = $scope.searchJsonByKey($scope.goods.goodsDesc.specificationItems, "attributeName", specName);
        /** 判断对象是否为空 */
        if (json) {  //不为空
            //判断checkbox是否选中
            if ($event.target.checked) {  //选中
                //添加规格选项到attributeValue数组中
                json.attributeValue.push(optionName)
            } else {  //没有选中
                // 删除attributeValue中的规格选项
                var idx = json.attributeValue.indexOf(optionName);
                json.attributeValue.splice(idx, 1);

                // 如果全部取消了,删除这条记录
                if (json.attributeValue.length == 0) {
                    var index = $scope.goods.goodsDesc.specificationItems.indexOf(json);
                    $scope.goods.goodsDesc.specificationItems.splice(index, 1);
                }
            }

        } else {
            //如果json等于空,数组中添加格式:{"attributeValue":[optionName],"attributeName":specName}
            $scope.goods.goodsDesc.specificationItems.push({"attributeValue": [optionName], "attributeName": specName});
        }
    };
    $scope.searchJsonByKey = function (jsonArr, key, value) {
        //遍历这个数组
        for (var i = 0; i < jsonArr.length; i++) {
            //  得到一个对象元素  -->{"attributeValue":["移动4G","联通4G","电信4G"],"attributeName":"网络"}
            var json = jsonArr[i];
            //判断json对象这个Key对应值是否与value相等,
            //json[key]  ==> json.attributeName == "网络";
            if (json[key] == value) {  //相等 ,返回json
                return json;
            }
        }
        //全部不相等 ,返回空
        return null;
    };

    $scope.createItems = function () {

        //spec :{"网络":"联通4G","机身内存":"64G"}
        //初始化SKU数组 Items数据格式
        $scope.goods.items = [{spec: {}, price: 0, num: 0, status: '0', isDefault: '0'}];
        // 用户选中的选项规格数组
        var specItems = $scope.goods.goodsDesc.specificationItems;
        /** 循环选中的规格选项数组 */
        for (var i = 0; i < specItems.length; i++) {
            //得到一个json对象{"attributeValue":["移动4G","联通4G","电信4G"],"attributeName":"网络"}
            var specItem = specItems[i];

            //生成SKU数组
            $scope.goods.items = $scope.swapItems($scope.goods.items, specItem.attributeValue, specItem.attributeName);

        }
    };
    //转化$scope.goods.items数组元素 ,根据用户选中的attributeValue数组中的元素生成一个新的SKU数组
    $scope.swapItems = function (items, attributeValue, attributeName) {
        var newItems = [];
        //items :[{spec:{"网络":"联通4G","机身内存":"64G"},price:0,num:0,status:'0',isDefault:'0'}];
        //attributeName :"网络"
        for (var i = 0; i < items.length; i++) {
            //获得一个item对象
            var item = items[i];
            //attributeValue : ["移动4G","联通4G","电信4G"]
            for (var j = 0; j < attributeValue.length; j++) {
                //获得一个optionName属性
                var newItem = JSON.parse(JSON.stringify(item));
                var optionName = attributeValue[j];
                //把optionName添加到spec数组中
                //spec :{"网络":"联通4G","机身内存":"64G"}
                newItem.spec[attributeName] = optionName;
                newItems.push(newItem)
            }
        }
        return newItems;
    };

    //判断是否选择,如果没有选择或者是选中了并选中了规格和item属性,再次取消勾选,
    // 则需要把 specificationItems  和items 设置为空.
    $scope.isEnableSpec = function ($event) {
        if (!$event.target.checked) {
            $scope.goods.goodsDesc.specificationItems = [];
            $scope.goods.items = [];
        }
    };

    /** 商品管理带条件分页查询 */
    $scope.search = function (page, rows) {
        baseService.findByPage("/goods/findByPage", page, rows, $scope.searchEntity).then(function (response) {
            $scope.dataList = response.data.rows;
            //    更新每页显示记录数
            $scope.paginationConf.totalItems = response.data.total;
        })
    };

    //定义数组.将数字状态修改为中文系显示,
    $scope.status = ['未审核', '已审核', '审核未通过', '关闭'];


    $scope.updateIsMarketable = function (status) {

        if ($scope.ids.length == 0) {
            alert("请选择您要操作的选项!");
        } else {
            baseService.sendGet("/goods/updateIsMarketable?ids=" + $scope.ids + "&isMarketable=" + status).then(function (response) {
                if (response.data) {
                    $scope.reload();
                    //清空数组
                    $scope.ids = [];
                } else {
                    alert("亲,操作失败!")
                }
            })
        }
    };


    /**
     * 删除商品(修改is_delete的状态码)
     * @param status
     */
    $scope.delete = function (auditStatus) {
        if ($scope.ids.length == 0) {
            alert("请选择您要删除的选项!");
        } else {
            if (confirm("您确定要删除吗?")) {
                baseService.sendGet("/goods/delete?ids=" + $scope.ids + "&auditStatus=" + auditStatus).then(function (response) {
                    if (response.data) {
                        $scope.reload();
                    } else {
                        alert("亲,操作失败!");
                    }
                })
            }
        }
    }
});