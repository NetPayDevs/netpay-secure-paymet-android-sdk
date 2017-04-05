package netpay.com.securepaymentsdk.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import netpay.com.securepaymentsdk.transactions.NetPay;
import netpay.com.securepaymentsdk.R;
import netpay.com.securepaymentsdk.beans.Sale;
import netpay.com.securepaymentsdk.web.clients.NetPayWebClient;
import netpay.com.securepaymentsdk.web.interfaces.NetPayJsInterface;


public final class SecurePaymentActivity extends AppCompatActivity {

    private static final String LOg_CAT = "SecurePaymentActivity";

    private String url3Ds;
    private WebView wv3ds;
    private Sale sale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secure_payment);

        getBundle();
        loadViews();
        buildRequestSecurePayment();
        startRequestSecurePayment();
    }

    private void getBundle(){
        this.sale = (Sale) getIntent().getExtras().getSerializable(Sale.BUNDLE_FLAG);
    }

    private void loadViews(){
        wv3ds = (WebView) findViewById(R.id.wv_3ds);

        wv3ds.setWebViewClient(new NetPayWebClient());
        //wv3ds.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wv3ds.getSettings().setJavaScriptEnabled(true);
        wv3ds.addJavascriptInterface(new NetPayJsInterface(this), "Android");
        wv3ds.getSettings().setLoadWithOverviewMode(true);


        //wv3ds.clearCache(true);
    }

    private void startRequestSecurePayment(){
        if (url3Ds == null || url3Ds.isEmpty())
            //TODO: Callback error URL 3DS
            wv3ds.loadUrl(url3Ds);
        else
            wv3ds.loadUrl(url3Ds);
    }

    private void buildRequestSecurePayment(){
        url3Ds = NetPay.getIntance().getUrlSecurePayment()
                + "?OrderNumber=" + sale.getOrderNumber()
                +"&Amount=" + this.sale.getTotalSale()
                +"&MerchantResponseURL=" + NetPay.getIntance().getWebHook()
                +"&ConfigId=" + NetPay.getIntance().getConfigId()
                +"&UserAcq=" + NetPay.getIntance().getUserAcq()
                +"&PasswordAcq=" + NetPay.getIntance().getPassAcq()
                +"&Currency=" + NetPay.getIntance().getCurrency()
                +"&ZipCode"
                +"&Address"
                +"&City"
                +"&State="
                +"&Email="
                +"&WebHookResponse=";
        Log.e(LOg_CAT, "URL_3DS: " + url3Ds);
    }
}
