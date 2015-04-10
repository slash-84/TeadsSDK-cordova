cordova.define("tv.teads.plugin", function(require, exports, module) { var argscheck = require('cordova/argscheck'),
    exec = require('cordova/exec');});
    

//$(document).ready(function () {
//  $(window).scroll(function () {

//  });
//});


var teadsExport = {};

function TeadsPlugin() {};


/**
 * Teads Ad Factory
 */

/**
 * Load a native video (e.g. : video that can be used later from an inRead or inBoard) from Ad Factory
 * 
 * @param  {string} pid is the Placement ID of the native video to load
 * 
 * @param  {function()} successCallback
 * 
 * @param  {function()} failureCallback
 */
TeadsPlugin.prototype.loadNativeVideoAdWithPidFromAdFactory = function(pid, successCallback, failureCallback) {
	if(typeof pid === 'string') {
		cordova.exec( successCallback, failureCallback, 'TeadsPlugin', 'loadNativeVideoAdWithPidFromAdFactory', [pid]);
	} else {
		console.log('Error : placement id provided for loadNativeVideoAdWithPidFromAdFactory is not a string');
	}
};

/**
 * Load a fullscreen video (e.g. : video that can be used later from an inFlow) from Ad Factory
 * 
 * @param  {string} pid is the Placement ID of the native video to load
 * 
 * @param  {function()} successCallback
 * 
 * @param  {function()} failureCallback
 */
TeadsPlugin.prototype.loadFullscreenAdWithPidFromAdFactory = function(pid, successCallback, failureCallback) {
	if(typeof pid === 'string') {
		ccordova.exec( successCallback, failureCallback, 'TeadsPlugin', 'loadFullscreenAdWithPidFromAdFactory', [pid]);
	} else {
		console.log('Error : placement id provided for loadNativeVideoAdWithPidFromAdFactory is not a string');
	}
};


/**
 * Teads inFlow
 */
TeadsPlugin.prototype.initInFlowWithPlacementId = function(pid, successCallback, failureCallback) {
	if(typeof pid === 'string') {
		cordova.exec(successCallback, failureCallback, 'TeadsPlugin', 'initInFlowWithPlacementId', [pid] );
	} else {
		console.log('Error : placement id provided for loadNativeVideoAdWithPidFromAdFactory is not a string');
	}
};

/**
 * Get inFlow isLoaded status
 *
 * @return {boolean} isLoadedResponse : false = not loaded, true = loaded
 *
**/
TeadsPlugin.prototype.getInFlowIsLoaded = function(successCallback, failureCallback) {
	cordova.exec( 
		function(isLoadedResponse) { 
			successCallback(isLoadedResponse);
		}, 
		failureCallback,
		'TeadsPlugin', 
		'getInFlowIsLoaded',
		[]);
};

TeadsPlugin.prototype.setPreDownloadInFlow = function(mustPreDownload, successCallback, failureCallback) {
	if(typeof mustPreDownload === 'boolean') {
		cordova.exec( successCallback, failureCallback, 'TeadsPlugin', 'setPreDownloadInFlow', [mustPreDownload]);
	} else {
		console.log('Error : placement id provided for setPreDownloadInFlow is not a boolean');
	}
};

TeadsPlugin.prototype.loadInFlow = function(successCallback, failureCallback) {
	cordova.exec( successCallback, failureCallback, 'TeadsPlugin', 'loadInFlow', []);
};

TeadsPlugin.prototype.loadInFlowFromAdFactory = function(successCallback, failureCallback) {
	cordova.exec( successCallback, failureCallback, 'TeadsPlugin', 'loadInFlowFromAdFactory', []);
};

TeadsPlugin.prototype.showInFlow = function(successCallback, failureCallback) {
	cordova.exec( successCallback, failureCallback, 'TeadsPlugin', 'showInFlow', [] );
};

TeadsPlugin.prototype.cleanInFlow = function(successCallback, failureCallback) {
	cordova.exec( successCallback, failureCallback, 'TeadsPlugin', 'cleanInFlow', [] );
};


/**
 * Teads Native Video (inBoard and inRead)
 */

TeadsPlugin.prototype.initInBoardWithPlacementId = function(pid, successCallback, failureCallback) {
	cordova.exec( function(successParam) { }, 
		function(errorParam) {alert('Error initInBoardWithPlacementId');},
		'TeadsPlugin', 
		'initInBoardWithPlacementId', 
		[pid] );
};

TeadsPlugin.prototype.initInReadWithPlacementIdAndPlaceholder = function(pid, placeholder, successCallback, failureCallback) {
	cordova.exec( successCallback, failureCallback, 'TeadsPlugin', 'initinReadWithPlacementId', [pid, placeholder] );
};

/**
 * Get NativeVideo isLoaded status
 *
 * @return {boolean} isLoadedResponse : false = not loaded, true = loaded
 *
**/
TeadsPlugin.prototype.getNativeVideoIsLoaded = function(successCallback, failureCallback) {
	cordova.exec( 
		function(isLoadedResponse) { 
			successCallback(isLoadedResponse);
		}, 
		failureCallback,
		'TeadsPlugin', 
		'getNativeVideoIsLoaded',
		[]);
};

TeadsPlugin.prototype.setPreDownLoadNativeVideo = function(mustPreDownload, successCallback, failureCallback) {
	cordova.exec( successCallback, failureCallback, 'TeadsPlugin', 'setPreDownLoadNativeVideo', [mustPreDownload] );
};

TeadsPlugin.prototype.loadNativeVideo = function(successCallback, failureCallback) {
	cordova.exec( successCallback, failureCallback, 'TeadsPlugin', 'loadNativeVideo', [] );
};

TeadsPlugin.prototype.loadNativeVideoFromAdFactory = function(successCallback, failureCallback) {
	cordova.exec( successCallback, failureCallback, 'TeadsPlugin', 'loadNativeVideoFromAdFactory', []);
};

TeadsPlugin.prototype.cleanNativeVideo = function(successCallback, failureCallback) {
	cordova.exec( successCallback, failureCallback, 'TeadsPlugin', 'cleanNativeVideo', [] );
};

TeadsPlugin.prototype.onLayoutChangeNativeVideo = function(successCallback, failureCallback) {
	cordova.exec( successCallback, failureCallback, 'TeadsPlugin', 'onLayoutChangeNativeVideo', [] );
};

TeadsPlugin.prototype.requestPauseNativeVideo = function(successCallback, failureCallback) {
	cordova.exec( successCallback, failureCallback, 'TeadsPlugin', 'requestPauseNativeVideo', [] );
};

TeadsPlugin.prototype.requestPlayNativeVideo = function(successCallback, failureCallback) {
	cordova.exec( successCallback, failureCallback, 'TeadsPlugin', 'requestPlayNativeVideo', [] );
};

TeadsPlugin.prototype.viewControllerAppearedForNativeVideo = function(successCallback, failureCallback) {
	cordova.exec( successCallback, failureCallback, 'TeadsPlugin', 'viewControllerAppearedForNativeVideo', [] );
};

TeadsPlugin.prototype.viewControllerDisappearedForNativeVideo = function( successCallback, failureCallback) {
	cordova.exec( successCallback, failureCallback, 'TeadsPlugin', 'viewControllerDisappearedForNativeVideo', [] );
};


var teads = new TeadsPlugin(); 
module.exports = teads;

//});