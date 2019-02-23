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
    // /** 规格列表 */
    // $scope.findSpecificationList = function () {
    //     baseService.sendGet("/specification/findSpecificationList").then(function (response) {
    //         $scope.specificationList = {data: response.data};
    //     })
    // };



    /** 品牌列表 */
    // $scope.brandList = {data: [{id: 1, text: '联想'}, {id: 2, text: '华为'}, {id: 3, text: '小米'}]};
    // $scope.brandList.dataList
    // $scope.brandList = {data:$scope.brandList.dataList}


});