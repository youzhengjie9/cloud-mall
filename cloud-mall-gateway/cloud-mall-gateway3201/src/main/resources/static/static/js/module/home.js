/**
 * home page
 */

angular.module('home', ['ui.router'])
    .config(['$stateProvider',
        function ($stateProvider) {
            $stateProvider
                .state("home", {
                    url: '/home',
                    abstract:true,
                    templateUrl: '/web/index/view/homePage',
                    resolve: {
                        productsResolve: function (httpService) {

                            return httpService.get('/static/json/products.json');
                        },
                        productObj: function (httpService, util,$state) {
                            return httpService.get('/web/product/selectAllProduct')
                                .then(function (data) {
                                    return util.queryItemById(data.products, 1);
                                });
                        },
                        //函数:返回$http返回的promise,最终得到的就是后台返回值.
                        promiseObj2: function ($http) {
                            return $http({method: 'GET', url: '/web/product/selectAllProduct'});
                        },
                        //函数:返回$http返回的promise返回的promise,最终得到的是.then里面return的内容
                        promiseObj3: function ($http, util) {
                            return $http({method: 'GET', url: '/web/product/selectAllProduct'})
                                .then(function (data) {
                                    //promise.then返回的promise对象,会被.then()函数里的返回值解析.这适用于对返回值做一些处理后再返回.
                                    return util.queryItemById(data.data.products, 1);
                                });
                        }
                    }
                })
                .state("home.main",{
                    url:'',
                    views: {
                        "":{
                            templateUrl: '/web/index/view/homeMain',
                            controller:function(){}
                        },
                        "loginUser":{
                            templateUrl: '/web/index/view/homeHomeUserLogin',
                            controller:function($scope,loginService,$rootScope){}
                        }
                    },
                    ncyBreadcrumb:{
                        label:"首页"
                    }
                })
                .state("home.products",{ //产品信息
                    //home.products({productId:childrenItem.id})
                    url:'/product/:productId',
                    // url:'/product?product=:productId',
                    //abstract:true,
                    templateUrl: '/web/index/view/homeProduct',
                    resolve: {
                        productObj:function (httpService, util,$state,$stateParams) {
                            return httpService.get('/web/product/selectAllProduct')
                                .then(function (data) {
                                    return util.queryItemById(data.products, $stateParams.productId);
                                });
                        }
                    },
                    controller:function($scope,productObj,$window,$state,$stateParams,productsResolve){
                        $scope.product =productObj;
                        $scope.product.details = productsResolve.detail_info;
                        $scope.isFlag = false;
                        // //因为不会获取angular定义的product属性,所以选择用AJAX发送productid给后端
                        $.ajax({
                            url: "/web/index/view/sendCookie",
                            data: {
                                "pid": productObj.productId
                            },
                            type: "GET",
                            async:false,
                            success: function (data) {

                            }
                        });
                        //监听滚动条，滚动到一定位置时显示头部bar导航条
                        $window.onscroll = function () {
                            var offsetY = document.documentElement.scrollTop || document.body.scrollTop;
                            $scope.$apply(function(){
                                if(offsetY>=140){
                                    $scope.isFlag  = true;
                                }else{
                                    $scope.isFlag = false;
                                }
                            });
                        }
                        //$state.go('.detail',{detailType:"监听"}); 报错
                        //Could not resolve '.detail' from state 'home.products.detail'

                        $state.go('home.products.detail',{detailType:$scope.product.details[0].name});

                    }

                })
                .state("home.products.detail",{ //产品详情信息
                    url:'/{detailType}',
                    templateUrl: '/web/index/view/homeProductDetail',
                    controller:function($scope,$stateParams,util){
                        $scope.product.detail =util.queryItemByField($scope.product.details,$stateParams.detailType,"name");

                    }
                })

                .state("home.search",{ //搜索产品信息
                    url:'/search/:searchKey',
                    templateUrl: '/web/index/view/homeSearch',
                    controller:function($scope,$stateParams,productsResolve,util){
                        // $scope.searchResultList=util.queryArrByField(productsResolve.products,$stateParams.searchKey,"name");
                        $.ajax({
                            url: "/web/search/SearchData",
                            data: {
                                "text": $stateParams.searchKey //$stateParams.searchKey是搜索关键字
                            },
                            type: "GET",
                            success: function (data) {
                                $scope.searchResultList=data.products; //获取搜索来的数据
                            }
                        });
                        // $scope.searchResultList = productsResolve.products;
                    },
                    ncyBreadcrumb:{
                        label:"全部结果",
                        parent:"home.main",
                        param:"searchKey"
                    }
                })
                .state("error404",{
                    url:'/error404',
                    templateUrl: '/web/index/view/page404',
                    controller:function($scope,$stateParams){
                    }
                })
        }
    ])