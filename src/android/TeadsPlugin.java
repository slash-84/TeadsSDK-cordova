//
//  TeadsPlugin
//  TeadsSDK
//
//  Created by Nikolaï Roycourt on 2/16/15.
//  Copyright (c) 2015 Teads. All rights reserved.
//

package tv.teads.plugin;


import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;

import org.apache.cordova.ScrollEvent;
import org.json.JSONArray;
import org.json.JSONException;


import tv.teads.sdk.publisher.TeadsConfiguration;
import tv.teads.sdk.publisher.TeadsError;
import tv.teads.sdk.publisher.TeadsInterstitial;
import tv.teads.sdk.publisher.TeadsInterstitialEventListener;
import tv.teads.sdk.publisher.TeadsNativeVideo;
import tv.teads.sdk.publisher.TeadsNativeVideoEventListener;


import android.app.Activity;
import android.util.Log;
import android.webkit.WebView;

public class TeadsPlugin extends CordovaPlugin implements TeadsInterstitialEventListener, TeadsNativeVideoEventListener {
    
    /** Cordova Actions. */
    
    //TeadsAdFactory
    private static final String ACTION_ADFACTORY_LOAD_NATIVE        = "loadNativeVideoAdWithPidToAdFactory";
    private static final String ACTION_ADFACTORY_LOAD_INTERSTITIAL  = "loadInterstitialAdWithPidToAdFactory";
    
    //Teads fullscreen video
    private static final String ACTION_INFLOW_INIT                  = "initInFlowWithPlacementId";
    private static final String ACTION_INFLOW_ISLOADED              = "getInFlowIsLoaded";
    private static final String ACTION_INFLOW_SET_PREDOWNLOAD       = "setPreDownloadInFlow";
    private static final String ACTION_INFLOW_LOAD                  = "loadInFlow";
    private static final String ACTION_INFLOW_LOAD_FROM_ADFACTORY   = "loadInFlowFromAdFactory";
    private static final String ACTION_INFLOW_SHOW                  = "showInFlow";
    private static final String ACTION_INFLOW_CLEAN                 = "cleanInFlow";
    private static final String ACTION_INFLOW_ONLAYOUTCHANGE        = "onLayoutChangeInFlow";
    
    //Teads native video (inRead & inBoard)
    //init inBoard
    private static final String ACTION_NATIVE_INIT_INBOARD          = "initInBoardWithPlacementId";
    //init inRead
    private static final String ACTION_NATIVE_INIT_INREAD           = "initinReadWithPlacementId";

    //Native video (inRead & inBoard) common methods
    private static final String ACTION_NATIVE_ISLOADED              = "getNativeVideoIsLoaded";
    private static final String ACTION_NATIVE_SET_PREDOWNLOAD       = "setPreDownLoadNativeVideo";
    private static final String ACTION_NATIVE_LOAD                  = "loadNativeVideo";
    private static final String ACTION_NATIVE_LOAD_FROM_FACTORY     = "loadNativeVideoFromAdFactory";
    private static final String ACTION_NATIVE_CLEAN                 = "cleanNativeVideo";
    private static final String ACTION_NATIVE_REQUEST_PAUSE         = "requestPauseNativeVideo";
    private static final String ACTION_NATIVE_REQUEST_PLAY          = "requestPlayNativeVideo";
    private static final String ACTION_NATIVE_VIEW_DID_APPEAR       = "viewControllerAppearedForNativeVideo";
    private static final String ACTION_NATIVE_DID_DISAPPEAR         = "viewControllerDisappearedForNativeVideo";

    
    private static final String TAG = "TeadsPluginLog";
    
    TeadsInterstitial mTeadsInterstitial;

    TeadsNativeVideo mTeadsNativeVideo;

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        PluginResult result = null;

        //TeadsAdFactory
        if (ACTION_ADFACTORY_LOAD_NATIVE.equals(action)) {
            result = loadNativeVideoAdWithPidToAdFactory(data, callbackContext);

        } else if (ACTION_ADFACTORY_LOAD_INTERSTITIAL.equals(action)) {
            result = loadInterstitialAdWithPidToAdFactory(data, callbackContext);
        }

        //Teads fullscreen video
        else if (ACTION_INFLOW_INIT.equals(action)) {
            result = initInFlowWithPlacementId(data, callbackContext);

        } else if (ACTION_INFLOW_ISLOADED.equals(action)) {
            result = getInFlowIsLoaded(data, callbackContext);

        } else if (ACTION_INFLOW_SET_PREDOWNLOAD.equals(action)) {
            result = setPreDownloadInFlow(data, callbackContext);

        } else if (ACTION_INFLOW_LOAD.equals(action)) {
            result = loadInFlow(data, callbackContext);

        } else if (ACTION_INFLOW_LOAD_FROM_ADFACTORY.equals(action)) {
            result = loadInFlowFromAdFactory(data, callbackContext);

        } else if (ACTION_INFLOW_SHOW.equals(action)) {
            result = showInFlow(data, callbackContext);

        } else if (ACTION_INFLOW_CLEAN.equals(action)) {
            result = cleanInFlow(data, callbackContext);

        } else if (ACTION_INFLOW_ONLAYOUTCHANGE.equals(action)) {
            result = onLayoutChangeInFlow(data, callbackContext);
        }

        //Teads native video (inRead & inBoard)
        else if (ACTION_NATIVE_INIT_INBOARD.equals(action)) {
            result = initInBoardWithPlacementId(data, callbackContext);

        } else if (ACTION_NATIVE_INIT_INREAD.equals(action)) {
            result = initinReadWithPlacementId(data, callbackContext);
        }
        //Native video (inRead & inBoard) common methods
        else if (ACTION_NATIVE_ISLOADED.equals(action)) {
            result = getNativeVideoIsLoaded(data, callbackContext);

        } else if (ACTION_NATIVE_SET_PREDOWNLOAD.equals(action)) {
            result = setPreDownLoadNativeVideo(data, callbackContext);

        } else if (ACTION_NATIVE_LOAD.equals(action)) {
            result = loadNativeVideo(data, callbackContext);

        } else if (ACTION_NATIVE_LOAD_FROM_FACTORY.equals(action)) {
            result = loadNativeVideoFromAdFactory(data, callbackContext);

        } else if (ACTION_NATIVE_CLEAN.equals(action)) {
            result = cleanNativeVideo(data, callbackContext);

        } else if (ACTION_NATIVE_REQUEST_PAUSE.equals(action)) {
            result = requestPauseNativeVideo(data, callbackContext);

        } else if (ACTION_NATIVE_REQUEST_PLAY.equals(action)) {
            result = requestPlayNativeVideo(data, callbackContext);

        } else if (ACTION_NATIVE_VIEW_DID_APPEAR.equals(action)) {
            result = viewControllerAppearedForNativeVideo(data, callbackContext);

        } else if (ACTION_NATIVE_DID_DISAPPEAR.equals(action)) {
            result = viewControllerDisappearedForNativeVideo(data, callbackContext);

        } else {
            result = new PluginResult(PluginResult.Status.INVALID_ACTION);
        }


        if (result != null) {
            callbackContext.sendPluginResult(result);
        }

        return true;
    }

    @Override
    public Object onMessage(String id, Object data) {
        if (mTeadsNativeVideo != null) {
            if (id.equalsIgnoreCase("onScrollChanged")) {
                ScrollEvent myEvent = (ScrollEvent)data;
                //myEvent.l
                //myEvent.t
                //myEvent.nl
                //myEvent.nt
                //myEvent.getTargetView() (type View)
                mTeadsNativeVideo.getWebViewScrollListener().onScroll(myEvent.nl, myEvent.nt);
            }
            else if (id.equalsIgnoreCase("onPageFinished")) {
                mTeadsNativeVideo.getWebViewClientListener().onPageFinished((WebView)webView.getView(), (String)data);
            }
            else if (id.equalsIgnoreCase("onScaleChanged")) {

            }
        }

        return super.onMessage(id, data);
    }

    @Override
    public boolean onOverrideUrlLoading(String url) {
        if (mTeadsNativeVideo != null) {
            mTeadsNativeVideo.getWebViewClientListener().shouldOverrideUrlLoading((WebView)webView.getView(), url);
        }

        return super.onOverrideUrlLoading(url);
    }

    private PluginResult loadNativeVideoAdWithPidToAdFactory(JSONArray data, final CallbackContext callbackContext) {
        //Not implemented yet in Teads Android SDK
        PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT, "Ad Factory is not available yet in Teads SDK for Android");
        return result;
    }

    private PluginResult loadInterstitialAdWithPidToAdFactory(JSONArray data, final CallbackContext callbackContext) {
        //Not implemented yet in Teads Android SDK
        PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT, "Ad Factory is not available yet in Teads SDK for Android");
        return result;
    }
    
    private PluginResult initInFlowWithPlacementId(JSONArray data, final CallbackContext callbackContext) {
        //String pid = data.getString(0);
           /*
         cordova.getActivity().runOnUiThread(new Runnable() {
         @Override
         public void run() {
         Context context = cordova.getActivity().getApplicationContext();
         Intent intent = new Intent(context, MyNewActivityGap.class);
         cordova.getActivity().startActivity(intent);
         }
         });
         */

        PluginResult result = null;
        try {
            String pid = data.getString(0);
            mTeadsInterstitial = new TeadsInterstitial(cordova.getActivity(), pid, this);

            result = new PluginResult(PluginResult.Status.OK);

        } catch (ClassCastException ex) {
            Log.d(TAG, "error executeInitInterstitial = " + ex.getMessage());
            result = new PluginResult(PluginResult.Status.ERROR, ex.getMessage());
        } catch (JSONException ex) {
            Log.d(TAG, "error executeInitInterstitial = " + ex.getMessage());
            result = new PluginResult(PluginResult.Status.ERROR, ex.getMessage());
        }
        return result;
    }

    private PluginResult getInFlowIsLoaded(JSONArray data, final CallbackContext callbackContext) {
        if (mTeadsInterstitial == null) {
            return new PluginResult(PluginResult.Status.ERROR, "Teads Interstitial is null, call initInFlowWithPlacementId first.");
        }

        PluginResult result = new PluginResult(PluginResult.Status.OK, mTeadsInterstitial.isLoaded());
        return result;
    }

    private PluginResult setPreDownloadInFlow(JSONArray data, final CallbackContext callbackContext) {
        //Non applicable to Teads Android SDK
        PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT, "Pre-download call is not necessary in Teads SDK for Android");
        return result;
    }
    
    private PluginResult loadInFlow(JSONArray data, final CallbackContext callbackContext) {
        PluginResult result = null;

        if (mTeadsInterstitial == null) {
            return new PluginResult(PluginResult.Status.ERROR, "Teads Interstitial is null, call initInFlowWithPlacementId first.");
        }

        try {
            cordova.getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    mTeadsInterstitial.load();

                    if (callbackContext != null) {
                        callbackContext.success();
                    }
                }
            });

            result = new PluginResult(PluginResult.Status.OK);
        } catch (Exception e) {
            Log.e(TAG, "error LoadInterstitial = " + e.getMessage());
            result = new PluginResult(PluginResult.Status.ERROR, e.getMessage());
        }

        return result;
    }

    private PluginResult loadInFlowFromAdFactory(JSONArray data, final CallbackContext callbackContext) {
        //Not implemented yet in Teads Android SDK
        PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT, "Ad Factory is not available yet in Teads SDK for Android");
        return result;
    }
    
    private PluginResult showInFlow(JSONArray data, final CallbackContext callbackContext) {
        PluginResult result = null;

        if (mTeadsInterstitial == null) {
            return new PluginResult(PluginResult.Status.ERROR, "Teads Interstitial is null, call initInFlowWithPlacementId first.");
        }

        if(mTeadsInterstitial.isLoaded()){
            try {
                mTeadsInterstitial.show();
                result = new PluginResult(PluginResult.Status.OK);
            } catch (Exception e) {
                Log.d(TAG, "error ShowInterstitial = " + e.getMessage());
                result = new PluginResult(PluginResult.Status.ERROR, e.getMessage());
            }
        } else {
            callbackContext.error("Teads Interstitial is not loaded");
            result = new PluginResult(PluginResult.Status.ERROR, "Teads Interstitial is not loaded, call loadInFlow first.");
        }

        return result;
    }

    private PluginResult cleanInFlow(JSONArray data, final CallbackContext callbackContext) {
        if (mTeadsInterstitial == null) {
            return new PluginResult(PluginResult.Status.ERROR, "Teads Interstitial is null, call initInFlowWithPlacementId first.");
        }

        mTeadsInterstitial.clean();
        return null;
    }

    private PluginResult onLayoutChangeInFlow(JSONArray data, final CallbackContext callbackContext) {
        //Non applicable to Teads Android SDK
        PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT, "onLayoutChangeInFlow is not used in Teads SDK for Android");
        return result;
    }

    //Teads native video (inRead & inBoard)

    private PluginResult initInBoardWithPlacementId(JSONArray data, final CallbackContext callbackContext) {
        PluginResult result = null;
        try {
            final String pid = data.getString(0);
            cordova.getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    mTeadsNativeVideo = new TeadsNativeVideo(
                            cordova.getActivity(),
                            (WebView)webView.getView(),
                            pid,
                            TeadsNativeVideo.NativeVideoContainerType.inBoard,
                            TeadsPlugin.this,
                            null,
                            null
                    );
                }
            });

            result = new PluginResult(PluginResult.Status.OK);

        } catch (ClassCastException ex) {
            Log.d(TAG, "Error new TeadsNativeVideo : " , ex);
            result = new PluginResult(PluginResult.Status.ERROR, ex.getMessage());
        } catch (JSONException ex) {
            Log.d(TAG, "Error new TeadsNativeVideo : " , ex);
            result = new PluginResult(PluginResult.Status.ERROR, ex.getMessage());
        }
        return result;
    }

    private PluginResult initinReadWithPlacementId(JSONArray data, final CallbackContext callbackContext) {
        PluginResult result = null;
        final TeadsConfiguration config = new TeadsConfiguration();

        try {
            final String pid = data.getString(0);
            config.webViewSelector = data.getString(1);

            cordova.getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    mTeadsNativeVideo = new TeadsNativeVideo(
                            cordova.getActivity(),
                            (WebView)webView.getView(),
                            "27695",
                            TeadsNativeVideo.NativeVideoContainerType.inRead,
                            TeadsPlugin.this,
                            config,
                            null
                    );
                }
            });

            result = new PluginResult(PluginResult.Status.OK);

        } catch (ClassCastException ex) {
            Log.d(TAG, "Error new TeadsNativeVideo : " , ex);
            result = new PluginResult(PluginResult.Status.ERROR, ex.getMessage());
        } catch (JSONException ex) {
            Log.d(TAG, "Error new TeadsNativeVideo : " , ex);
            result = new PluginResult(PluginResult.Status.ERROR, ex.getMessage());
        }

        return result;
    }

    //Native video (inRead & inBoard) common methods
    private PluginResult getNativeVideoIsLoaded(JSONArray data, final CallbackContext callbackContext) {
        if (mTeadsNativeVideo == null) {
            return new PluginResult(PluginResult.Status.ERROR, "Teads Native Video is null, call initInBoardWithPlacementId or initinReadWithPlacementId first.");
        }

        PluginResult result = new PluginResult(PluginResult.Status.OK, mTeadsNativeVideo.isLoaded());

        return result;
    }

    private PluginResult setPreDownLoadNativeVideo(JSONArray data, final CallbackContext callbackContext) {
        //Non applicable to Teads Android SDK
        PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT, "Pre-download call is not necessary in Teads Android SDK");
        return result;
    }

    private PluginResult loadNativeVideo(JSONArray data, final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTeadsNativeVideo != null) {
                    mTeadsNativeVideo.load();
                }
            }
         });
        PluginResult result = new PluginResult(PluginResult.Status.OK);
        return result;
    }

    private PluginResult loadNativeVideoFromAdFactory(JSONArray data, final CallbackContext callbackContext) {
        //Not implemented yet in Teads Android SDK
        PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT, "Ad Factory is not available yet in Teads SDK for Android");
        return result;
    }

    private PluginResult cleanNativeVideo(JSONArray data, final CallbackContext callbackContext) {
        if (mTeadsNativeVideo == null) {
            return new PluginResult(PluginResult.Status.ERROR, "Teads Native Video is null, call initInBoardWithPlacementId or initinReadWithPlacementId first.");
        }

        mTeadsNativeVideo.clean();
        return null;
    }

    private PluginResult requestPauseNativeVideo(JSONArray data, final CallbackContext callbackContext) {
        if (mTeadsNativeVideo == null) {
            return new PluginResult(PluginResult.Status.ERROR, "Teads Native Video is null, call initInBoardWithPlacementId or initinReadWithPlacementId first.");
        }

        mTeadsNativeVideo.requestPause();
        return null;
    }

    private PluginResult requestPlayNativeVideo(JSONArray data, final CallbackContext callbackContext) {
        if (mTeadsNativeVideo == null) {
            return new PluginResult(PluginResult.Status.ERROR, "Teads Native Video is null, call initInBoardWithPlacementId or initinReadWithPlacementId first.");
        }

        mTeadsNativeVideo.requestResume();
        return null;
    }

    private PluginResult viewControllerAppearedForNativeVideo(JSONArray data, final CallbackContext callbackContext) {
        //Non applicable to Teads Android SDK
        PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT, "viewControllerAppearedForNativeVideo does not apply to Teads SDK for Android");
        return result;
    }

    private PluginResult viewControllerDisappearedForNativeVideo(JSONArray data, final CallbackContext callbackContext) {
        //Non applicable to Teads Android SDK
        PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT, "viewControllerDisappearedForNativeVideo does not apply to Teads SDK for Android");
        return result;
    }
    
    
    /**
     *  TeadsInterstitial Event Listener
     */
    
    @Override
    public void teadsInterstitialDidFailLoading(TeadsError error) {
        String teadsErrorType = "";
        switch (error.getErrorCode()) {
            case 0:
                teadsErrorType = "NetworkError";
                break;
            case 1:
                teadsErrorType = "AdServerError";
                break;
            case 2:
                teadsErrorType = "AdServerBadResponse";
                break;
            case 3:
                teadsErrorType = "AdFailsToLoad";
                break;
            case 4:
                teadsErrorType = "NoAdsAvailable";
                break;
            case 5:
                teadsErrorType = "InternalError";
                break;
            case 6:
                teadsErrorType = "TimeoutError";
                break;
            default:
                break;
        }

        String data = "{'teadsError':{'code' : '"+teadsErrorType +"', 'name' : '"+error.getName() +"', 'message' : '"+error.getMessage()+"'}}";
        webView.loadUrl("javascript:cordova.fireDocumentEvent('teadsInterstitialDidFailLoading', "+data+");");
        Log.e(TAG, "teadsInterstitialDidFailLoading : " + error.getMessage());
    }
    
    @Override
    public void teadsInterstitialWillLoad() {
        webView.loadUrl("javascript:cordova.fireDocumentEvent('teadsInterstitialWillLoad');");
    }
    
    @Override
    public void teadsInterstitialDidLoad() {
        webView.loadUrl("javascript:cordova.fireDocumentEvent('teadsInterstitialDidLoad');");
    }
    
    @Override
    public void teadsInterstitialWillTakeOverFullScreen() {
        webView.loadUrl("javascript:cordova.fireDocumentEvent('teadsInterstitialWillTakeOverFullScreen');");
    }
    
    @Override
    public void teadsInterstitialDidTakeOverFullScreen() {
        webView.loadUrl("javascript:cordova.fireDocumentEvent('teadsInterstitialDidTakeOverFullScreen');");
    }
    
    @Override
    public void teadsInterstitialWillDismissFullScreen() {
        webView.loadUrl("javascript:cordova.fireDocumentEvent('teadsInterstitialWillDismissFullScreen');");
    }
    
    @Override
    public void teadsInterstitialDidDismissFullScreen() {
        webView.loadUrl("javascript:cordova.fireDocumentEvent('teadsInterstitialDidDismissFullScreen');");
    }
    
    @Override
    public void teadsInterstitialRewardUnlocked() {
        webView.loadUrl("javascript:cordova.fireDocumentEvent('teadsInterstitialRewardUnlocked');");
    }
    
    /**
     * Teads Native Video Listener
     */

     @Override
     public void didFailLoading(TeadsError error) {
         String teadsErrorType = "";
         switch (error.getErrorCode()) {
             case 0:
                 teadsErrorType = "NetworkError";
                 break;
             case 1:
                 teadsErrorType = "AdServerError";
                 break;
             case 2:
                 teadsErrorType = "AdServerBadResponse";
                 break;
             case 3:
                 teadsErrorType = "AdFailsToLoad";
                 break;
             case 4:
                 teadsErrorType = "NoAdsAvailable";
                 break;
             case 5:
                 teadsErrorType = "InternalError";
                 break;
             case 6:
                 teadsErrorType = "TimeoutError";
                 break;
             default:
                 break;
         }

         String data = "{'teadsError':{'code' : '"+teadsErrorType +"', 'name' : '"+error.getName() +"', 'message' : '"+error.getMessage()+"'}}";
         webView.loadUrl("javascript:cordova.fireDocumentEvent('teadsNativeVideoDidFailLoading', "+data+");");
         Log.e(TAG, "didFailLoading : " + error.getMessage());
     }
     
     @Override
     public void nativeVideoWillLoad() {
         webView.loadUrl("javascript:cordova.fireDocumentEvent('teadsNativeVideoWillLoad');");
     }
     
     @Override
     public void nativeVideoDidLoad() {
         webView.loadUrl("javascript:cordova.fireDocumentEvent('teadsNativeVideoDidLoad');");
     }
     
     @Override
     public void nativeVideoWillStart() {
         webView.loadUrl("javascript:cordova.fireDocumentEvent('teadsNativeVideoWillStart');");
     }
     
     @Override
     public void nativeVideoDidStart() {
         webView.loadUrl("javascript:cordova.fireDocumentEvent('teadsNativeVideoDidStart');");
     }
     
     @Override
     public void nativeVideoWillStop() {
         webView.loadUrl("javascript:cordova.fireDocumentEvent('teadsNativeVideoWillStop');");
     }
     
     @Override
     public void nativeVideoDidStop() {
         webView.loadUrl("javascript:cordova.fireDocumentEvent('teadsNativeVideoDidStop');");
     }
     
     @Override
     public void nativeVideoDidResume() {
         webView.loadUrl("javascript:cordova.fireDocumentEvent('teadsNativeVideoDidResume');");
     }
     
     @Override
     public void nativeVideoDidPause() {
         webView.loadUrl("javascript:cordova.fireDocumentEvent('teadsNativeVideoDidPause');");
     }
     
     @Override
     public void nativeVideoDidMute() {
         webView.loadUrl("javascript:cordova.fireDocumentEvent('teadsNativeVideoDidMute');");
     }
     
     @Override
     public void nativeVideoDidUnmute() {
         webView.loadUrl("javascript:cordova.fireDocumentEvent('teadsNativeVideoDidUnmute');");
     }
     
     @Override
     public void nativeVideoDidOpenInternalBrowser() {
         webView.loadUrl("javascript:cordova.fireDocumentEvent('teadsNativeVideoDidOpenInternalBrowser');");
     }
     
     @Override
     public void nativeVideoDidClickBrowserClosed() {
         webView.loadUrl("javascript:cordova.fireDocumentEvent('teadsNativeVideoDidClickBrowserClose');");
     }
     
     @Override
     public void nativeVideoWillTakerOverFullScreen() {
         webView.loadUrl("javascript:cordova.fireDocumentEvent('teadsNativeVideoWillTakerOverFullScreen');");
     }
     
     @Override
     public void nativeVideoDidTakeOverFullScreen() {
         webView.loadUrl("javascript:cordova.fireDocumentEvent('teadsNativeVideoDidTakeOverFullScreen');");
     }
     
     @Override
     public void nativeVideoWillDismiss () {
         webView.loadUrl("javascript:cordova.fireDocumentEvent('teadsNativeVideoWillDismissFullscreen');");
     }
     
     @Override
     public void nativeVideoDidDismiss() {
         webView.loadUrl("javascript:cordova.fireDocumentEvent('teadsNativeVideoDidDismissFullscreen');");
     }
     
     @Override
     public void nativeVideoSkipButtonTapped() {
         webView.loadUrl("javascript:cordova.fireDocumentEvent('teadsNativeVideoSkipButtonTapped');");
     }
     
     @Override
     public void nativeVideoSkipButtonDidShow() {
         webView.loadUrl("javascript:cordova.fireDocumentEvent('teadsNativeVideoSkipButtonDidShow');");
     }
     
     @Override
     public void nativeVideoWillExpand() {
         webView.loadUrl("javascript:cordova.fireDocumentEvent('teadsNativeVideoWillExpand');");
     }
     
     @Override
     public void nativeVideoDidExpand() {
         webView.loadUrl("javascript:cordova.fireDocumentEvent('teadsNativeVideoDidExpand');");
     }
     
     @Override
     public void nativeVideoWillCollapse() {
         webView.loadUrl("javascript:cordova.fireDocumentEvent('teadsNativeVideoWillCollapse');");
     }
     
     @Override
     public void nativeVideoDidCollapse() {
         webView.loadUrl("javascript:cordova.fireDocumentEvent('teadsNativeVideoDidCollapse');");
     }
}
