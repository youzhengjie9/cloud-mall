/**
 * 搜索框指令
 */
angular.module("search", ['httpMd'])
    .directive('xmSearch', function factory(httpService,$timeout, $state) {
        var directiveDefinitionObject = {
            restrict: 'AE',
            replace:true,
            templateUrl:"/aaa",
            scope:{},
            priority: 0,
            compile: function compile(tElement, tAttrs, transclude) {
                return function (scope, element, attrs) {
                    scope.searchResultList=[];
                    scope.closeContent = function(){
                        scope.searchResultList=[];
                    };
                    scope.changeSearchVal=function(){
                        $timeout(function(){
                            httpService.get('/web/search/SearchData').then(function (data) {
                                scope.searchResultList = data.products;
                            });
                        },300);
                    };
                    scope.goSearchState = function(key){
                        $state.go('home.search',{'searchKey':key});
                    }
                }
            }
        };
        return directiveDefinitionObject;
    })