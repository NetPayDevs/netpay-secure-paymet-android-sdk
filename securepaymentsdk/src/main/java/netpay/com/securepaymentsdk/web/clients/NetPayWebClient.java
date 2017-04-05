package netpay.com.securepaymentsdk.web.clients;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import netpay.com.securepaymentsdk.utils.ObservableManager;

/**
 * Created by AcheDev on 3/30/17.
 */

public class NetPayWebClient extends WebViewClient{

    private static final String LOG_CAT = "NetPayWebClient";
    private boolean isReceiverError;

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        Log.e(LOG_CAT, "onPageStarted(): " + url);
        super.onPageStarted(view, url, favicon);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.e(LOG_CAT, "Deprecated shouldOverrideUrlLoading(): " + url);
        this.isReceiverError = false;
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        Log.e(LOG_CAT, "onPageFinished(): " + url);

        if (isReceiverError) {
            view.setVisibility(View.GONE);
        }else{
            view.setVisibility(View.VISIBLE);
            ObservableManager.getInstance().notifyObservers(ObservableManager.NetPayEvents._3DS_FINISH_LOAD);
        }
        super.onPageFinished(view, url);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        Log.e(LOG_CAT, "onReceivedError(): error code: " + errorCode + "description: " + description + "failingUrl: " + failingUrl);
        this.isReceiverError = true;
        ObservableManager.getInstance().notifyObservers(ObservableManager.NetPayEvents._3DS_ERROR_LOAD);
        super.onReceivedError(view, errorCode, description, failingUrl);
    }
}
