/** 定义搜索控制器 */
app.controller("searchController", function ($scope,$controller,$sce,$location, baseService) {
    $controller("baseController",{$scope:$scope});
    /**
     * 搜索方法
     * @type searchParams = {}
     */
    $scope.searchParams = {};
    $scope.search = function () {
        //显示搜索结果关键字   按"小米"关键字，搜索到 39条记录.
        $scope.searchKeyword = $scope.searchParams.keyword;
        //初始化显示关键字为false   按"小米"关键字，搜索到 39条记录.
        $scope.showKeyword = false;
        baseService.sendPost("/Search", $scope.searchParams).then(function (response) {
            $scope.resultMap = response.data;
            //    初始化页码
            $scope.initPageNum();
            if ($scope.searchParams.keyword != null && $scope.searchParams.keyword != '')
                //当搜索到记过后显示关键字  按"小米"关键字，搜索到 39条记录.
                $scope.showKeyword = true;
        });
    };

    /**
     * 将文本转换成html
     * @param html
     * @returns {*}
     */
    $scope.trustHtml = function (html) {
        return $sce.trustAsHtml(html);
    };

    /** 定义搜索参数对象 */
    $scope.searchParams = {keyword : '', category : '', brand : '', price : '', spec : {} ,page:1,rows:15,sortField:'',sort:''};

    /** 添加搜索选项方法 */
    $scope.addSearchItem = function (key,value) {
        /** 判断是商品分类、品牌、价格 */
        if (key == 'category' || key == 'brand' || key == 'price') {
            $scope.searchParams[key] = value;
        } else {
            /** 规格选项 */
            $scope.searchParams.spec[key] = value;
        }
        /** 添加搜索参数后, 执行搜索方法 */
        $scope.search();
    };
    /** 删除搜索选项方法 */
    $scope.removeSearchItem= function (key) {
        if (key == 'category' || key == 'brand' || key == 'price') {
            $scope.searchParams[key] = "";
        } else {
            /** 规格选项 */
             $scope.searchParams.spec = {};
        }
        /** 删除搜索参数时,执行搜索方法 */
        $scope.search();
    };

    /** 初始化分页页码 */
    $scope.initPageNum = function () {
        $scope.pageNum = [];
        //获取总页数
        var firstPage = 1;
        var lastPage = $scope.resultMap.totalPages;

        //显示省略号....
        $scope.firstDot = true;
        $scope.lastDot = true;
        //判断总页数
        if ($scope.resultMap.totalPages > 5) {
            //如果当前页靠首页近些
            if ($scope.searchParams.page <= 3) {
                lastPage = 5;
                // 在后面显示省略号
                $scope.firstDot = false;
            } else if ($scope.searchParams.page >= $scope.resultMap.totalPages - 3) {
                //如果当前页靠尾页近些
                firstPage = $scope.resultMap.totalPages - 4;
                // 在前面显示省略号
                $scope.lastDot = false;
            } else {
                //如果当前页在中间
                firstPage = $scope.searchParams.page - 2;
                lastPage = $scope.searchParams.page + 2;
            }
        }else {
            $scope.firstDot = false;
            $scope.lastDot = false;
        }
        //循环添加到数组中
        for (var i = firstPage; i <= lastPage; i++) {
            $scope.pageNum.push(i);
        }
    };


    /** 页码改变查询数据 */
    $scope.pageSearch = function (pageNum) {
        var pageNum = parseInt(pageNum);
        // 上一页, 下一页,跳转页码
        if (pageNum >= 1 && pageNum <= $scope.resultMap.totalPages  && pageNum != $scope.searchParams.page) {
            $scope.searchParams.page = pageNum;
            $scope.jumpPage = $scope.searchParams.page;
                /** 执行搜索方法 */
            $scope.search();
        }
    };

    /** 排序 */
    $scope.sortSearch =function (key,value) {
        $scope.searchParams.sortField = key;
        $scope.searchParams.sort = value;
        $scope.search()
    };

    /** 获取门户窗口传过来关键字 */
    $scope.getKeyword = function () {
        //获取门户页面搜索框传过来的关键字,赋值给$scope.searchParams.keyword
        $scope.searchParams.keyword = $location.search().keyword;
        // alert($scope.searchParams.keyword);
        //执行搜索
        $scope.search()
    }
});
