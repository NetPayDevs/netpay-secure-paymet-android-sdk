package netpay.com.securepaymentsdk.web.interfaces;

import android.content.Context;
import android.util.JsonToken;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import org.json.JSONObject;

import netpay.com.securepaymentsdk.utils.ObservableManager;

/**
 * Created by AcheDev on 3/31/17.
 */

public class NetPayJsInterface {
    Context mContext;

    /** Instantiate the interface and set the context */
    public NetPayJsInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Log.e("Interface", "showToast()");
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void finishTransaction(String jsonTransaction) {

        Log.e("Interface", "finishTransaction(): " + jsonTransaction);
        //Toast.makeText(mContext, jsonTransaction, Toast.LENGTH_SHORT).show();

        ObservableManager.NetPayEvents finishTransaction = ObservableManager.NetPayEvents._3DS_FINISH_TRANSACTION;
        finishTransaction.setJsonTransaction(jsonTransaction);
        ObservableManager.getInstance().notifyObservers(finishTransaction);
    }
}
