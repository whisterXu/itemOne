/** 添加控制器 */
app.controller("typeTemplateController", function ($scope, $controller, baseService) {
    /** 指定继承baseController */
    $controller("baseController", {$scope: $scope});

    /** 分页查询 */
    $scope.searchEntity = {};
    $scope.search = function (page, rows) {

        baseService.findByPage("/typeTemplate/findByPage", page, rows, $scope.searchEntity).then(function (response) {
            $scope.dataList = response.data.rows;
            /** 更新总记录数 */
            $scope.paginationConf.totalItems = response.data.total;
            $scope.dataList = response.data.rows;
            $scope.reload()
        })
    };


    /** 增加一行 */
    $scope.addTableRow = function () {
        $scope.entity.customAttributeItems.push({});
    };
    /** 删除一行 */
    $scope.deleteTableRow = function (idx) {
        $scope.entity.customAttributeItems.splice(idx, 1);
    };



    /** 品牌列表 */
    $scope.findBrandList = function () {
        baseService.sendGet("/brand/findBrandList").then(function (response) {
            $scope.brandList = {data: response.data};
        });

        /** 规格列表 */
        baseService.sendGet("/specification/findSpecificationList").then(function (response) {
            $scope.specificationList = {data: response.data};
        })
    };


    /**
     * 模板增加和更新的方法
     */
    $scope.saveOrUpdate = function () {
        var url = "/save";
        if ($scope.entity.id){
            url = "/update";
        }
        baseService.sendPost("/typeTemplate"+url,$scope.entity).then(function (response) {
            if (response.data){
                $scope.reload();
            } else{
                alert("亲,添加失败!");
            }
        })
    };

    /** 修改时回显数据 */
    $scope.show = function (entity) {
        //把entity先转换成字符串再转换成Json对象
        $scope.entity = JSON.parse(JSON.stringify(entity));
        $scope.entity.specIds = JSON.parse(entity.specIds);
        $scope.entity.brandIds = JSON.parse(entity.brandIds);
        $scope.entity.customAttributeItems = JSON.parse(entity.customAttributeItems)
    };

    /** 删除方法
     * 1. 调用baseController中的updateSelection方法,更新复选框的选择
     * 2.调用baseService中deleteById删除方法,发送异步请求
     * */

    $scope.delete = function () {
        baseService.deleteById("/typeTemplate/delete",$scope.ids).then(function (response) {
            if (response.data) {
                $scope.reload();
            }else{
                alert("亲,删除失败!");
            }
        })
    };

    $scope.jsonArr2Str = function (jsonArrStr) {
        var jsonArr = JSON.parse(jsonArrStr);
        var resArr = [];
        for (var i = 0; i < jsonArr.length; i++) {
            var json = jsonArr[i];
            resArr.push(json.text);
        }
        return resArr.join(",");
    }
});