(function () {
    'use strict';
    var controllerId = 'admin';
    angular.module('app').controller(controllerId, ['$modal', 'common', admin]);

    function admin($modal, common) {
        var getLogFn = common.logger.getLogFn;
        var log = getLogFn(controllerId);

        var vm = this;
        vm.title = 'Admin';

        activate();

        function activate() {
            common.activateController([], controllerId)
                .then(function () { log('Activated Admin View'); });
        }
    }
})();