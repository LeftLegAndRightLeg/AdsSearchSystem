var myApp = angular.module('myApp', []);

myApp.controller('showCampaign', function($scope, CampaingData) {
    $scope.$watch(function () { return CampaingData.getResultData(); }, function (newValue, oldValue) {
        if (newValue !== oldValue){
            $scope.campaingData = newValue;
        }
    });

});

myApp.controller('showAd', function($scope, AdData) {
    $scope.$watch(function () { return AdData.getResultData(); }, function (newValue, oldValue) {
        if (newValue !== oldValue){
            $scope.adData = newValue;
        }
    });

});


myApp.factory('CampaingData', function(){
    var data =
    {
        ResultData: ''
    };

    return {
        getResultData: function () {
            return data.ResultData;
        },
        setResultData: function (resultData) {
            data.ResultData = resultData;
        }
    };
});
myApp.factory('AdData', function(){
    var data =
    {
        ResultData: ''
    };

    return {
        getResultData: function () {
            return data.ResultData;
        },
        setResultData: function (resultData) {
            data.ResultData = resultData;
        }
    };
});

myApp.controller('getAllCampaign', function($scope, $http, CampaingData, AdData) {
    $scope.getAllCampaign = function(){
        var url = '/getAllCampaign';

        $http.get(url).success(function(response) {
            console.log(JSON.stringify(response));
            CampaingData.setResultData(response);
            AdData.setResultData('');
        }, function(response) {
        });
    };

});

myApp.controller('getAllAd', function($scope, $http, AdData, CampaingData) {
    $scope.getAllAd = function(){
        var url = '/getAllAd';

        $http.get(url).success(function(response) {
            console.log(JSON.stringify(response));
            AdData.setResultData(response);
            CampaingData.setResultData('');
        }, function(response) {
        });
    };

});

myApp.controller('deleteAd', function($scope, $http, AdData, CampaingData) {
    $scope.deleteAd = function(){
        var url = '/deleteAd/' + $scope.adId;
        $scope.adId = null;
        $http.get(url).success(function(response) {
            console.log(JSON.stringify(response));
            AdData.setResultData(response);
            CampaingData.setResultData('');
        }, function(response) {
        });
    };

});

myApp.controller('searchAd', function($scope, $http, AdData, CampaingData) {
    $scope.searchAd = function(){
        var url = '/searchAd/' + $scope.keyWords;
        $scope.keyWords = null;
        $http.get(url).success(function(response) {
            console.log(JSON.stringify(response));
            AdData.setResultData(response);
            CampaingData.setResultData('');
        }, function(response) {
        });
    };

});

myApp.controller('addCampaign', function($scope, $http, CampaingData, AdData) {
    $scope.addCampaign = function(){
        var url = '/addCampaign';

        var fd = new FormData();
        fd.append('name', $scope.name);
        fd.append('budget', $scope.budget);

        $scope.name = null;
        $scope.budget = null;

        $http.post(url, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
            .success(function(response){
                console.log(JSON.stringify(response));
                CampaingData.setResultData(response);
                AdData.setResultData('');
            })
            .error(function(){
            });
    }

});

myApp.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });

        }
    };
}]);

myApp.service('fileUpload', ['$http','AdData','CampaingData', function ($http, AdData, CampaingData) {
    this.uploadFileToUrl = function(file, uploadUrl, keyWords, name, description, pClick, bid, campaignId){
        var fd = new FormData();
        fd.append('adImage', file);

        fd.append('keyWords', keyWords);
        fd.append('name', name);
        fd.append('description', description);
        fd.append('pClick', pClick);
        fd.append('bid', bid);
        fd.append('campaignId', campaignId);

        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
            .success(function(response){
                console.log(JSON.stringify(response));
                AdData.setResultData(response);
                CampaingData.setResultData('');
            })
            .error(function(){
            });
    }
}]);



myApp.controller('addAd', ['$scope', 'fileUpload', function($scope, fileUpload){

    $scope.uploadFile = function(){
        var file = $scope.adImage;

        var keyWords = $scope.keyWords;
        var name = $scope.name;
        var description = $scope.description;
        var pClick = $scope.pClick;
        var bid = $scope.bid;
        var campaignId = $scope.campaignId;

        var uploadUrl = "/addAd";

        $scope.keyWords = null;
        $scope.name = null;
        $scope.description = null;
        $scope.pClick = null;
        $scope.bid = null;
        $scope.campaignId = null;
        angular.element("input[type='file']").val(null);
        fileUpload.uploadFileToUrl(file, uploadUrl, keyWords, name, description, pClick, bid, campaignId);

    };

}]);