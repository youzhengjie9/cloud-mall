/**
 * 创建总的入口模块：xmStoreApp，它依赖其它所有模块
 */

var xmStoreApp = angular.module('xmStoreApp',
    [
        'home',
        'login',
        'register',
        'cart',
        'buyNow',
        'search',
        'ui.router',
        'order.router',
        'focusImgMd',
        'ulBoxMd',
        'httpMd',
        'utilMd',
        'goodsFilterMd',
        'loginMd',
        'cartMd',
        'checkboxMd',
        'xmIncludeMd',
        'breadcrumbsMd'
    ]
);
xmStoreApp
    .config(['$stateProvider', '$urlRouterProvider',
        function ($stateProvider, $urlRouterProvider) {
            $urlRouterProvider
            // 配置路由重定向,默认返回到主页
            .otherwise('/home');
        }
    ])
    //配置系统初始需要的数据
    .run(function ($rootScope, httpService, $state, loginService) {
        //用户登录信息绑定在跟作用域下，因为各个状态路由可能都需要涉及登录信息
        $rootScope.user = loginService.isLogin();
        $rootScope.outLogin = function () {
            loginService.outLogin();
            $rootScope.user = null;
        }

        //调用服务获取主页需要显示的焦点图
        // httpService.get('/static/json/tsconfig.json').then(function (data) {
        //     $rootScope.imgItems = data.focusImg;
        // });

        httpService.get('/web/slideshow/selectSlideShow').then(function (data) {
            $rootScope.imgItems = data.focusImg;
        });


        //调用服务获取分类表的数据 =====这里会报错？？暂时没发现原因
        // httpService.get('/Classify/selectAllClassify').then(function (data) {
        //     $rootScope.ulItems = data.fenlei;
        // });
        httpService.get('/static/json/fenlei.json').then(function (data) {
            $rootScope.ulItems = data.fenlei;
        });

        //调用服务获取产品表的数据
        httpService.get('/web/product/selectAllProduct').then(function (data) {
            $rootScope.ulItemsContent = data.products;
        });

        //调用服务获取主页明星单品
        // httpService.get('/static/json/tsconfig.json').then(function (data) {
        //     $rootScope.mxdp = data.mxdp;
        //     $rootScope.wntj = data.wntj;
        // });

        httpService.get('/web/img/selectmxdpAndwntj').then(function (data) {
            $rootScope.mxdp = data.mxdp;
            $rootScope.wntj = data.wntj;
        });

        //调用服务获取搜索页面过滤选择项
        httpService.get('/static/json/tsconfig.json').then(function (data) {
            $rootScope.filterTypes = data.filterTypes;
            $rootScope.filterContents = data.filterContents;
        });

        //根据分类表的数据构造一个合设的分类数组
        httpService.get('/web/Classify/selectAllClassify').then(function (data) {
            var arr = [];
            var arr2 = [];
            for (var i = 0; i < data.fenleiheshe.length; i++) {
                var ulListTmp = [];
                angular.forEach(data.fenlei, function (obj, index) {
                    if (data.fenleiheshe[i].text.indexOf(obj.text) != -1) {
                        ulListTmp.push(obj);
                    }
                })
                arr.push(ulListTmp);
            }
            angular.forEach(arr, function (item, index) {
                var objTmp = {id: '', text: ''};
                var idArr = [];
                var textArr = [];
                angular.forEach(item, function (obj, index) {
                    idArr.push(obj.id);
                    textArr.push(obj.text);
                })
                objTmp.id = '$' + idArr.join('$') + '$';
                objTmp.text = textArr.join(" ");
                arr2.push(objTmp);
            })
            $rootScope.ulItemsHeShe = arr2;
        });

        //判定状态改变事件
        $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
        });
        $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
            $rootScope.previousState_name = fromState.name;
            $rootScope.previousState_params = fromParams;
        });

        //实现返回上一状态的函数 back button function   ng-click="back()"
        $rootScope.back = function () {
            $state.go($rootScope.previousState_name, $rootScope.previousState_params);
        };
    })




