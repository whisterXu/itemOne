app.controller("baseController",function ($scope) {
    $scope.paginationConf = {
        currentPage:1,  //当前页
        totalItems:0,   //总页数
        itemsPerPage :10,    //每页显示的记录数(页大小)
        perPageOptions:[10,15,20,25,30], //下拉框选项
        onChange:function () {
            // alert($scope.paginationConf.currentPage);
            $scope.reload();
        }
    };

    /**  重新定义方法 */
    $scope.reload = function(){
        /** 重新加载列表数据 */
        // alert($scope.paginationConf.currentPage);
        $scope.search($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
    };

    /** 为复选框绑定点击事件  获取checkbod复选框的id */
    $scope.ids = [];
    $scope.updateSelection= function($event,id){
        if ($event.target.checked) {
            /** 选择把id添加到数组中 */
            $scope.ids.push(id);
        }else {
            var index = $scope.ids.indexOf(id);
            /** 删除数组中的元素  */
            $scope.ids.splice(index,1)
        }
    };
});