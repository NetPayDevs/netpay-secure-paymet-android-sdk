package netpay.com.securepaymentsdk.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import netpay.com.securepaymentsdk.beans.Checkout;
import netpay.com.securepaymentsdk.transactions.NetPay;
import netpay.com.securepaymentsdk.R;
import netpay.com.securepaymentsdk.beans.Sale;
import netpay.com.securepaymentsdk.utils.ObservableManager;
import netpay.com.securepaymentsdk.web.clients.NetPayWebClient;
import netpay.com.securepaymentsdk.web.interfaces.NetPayJsInterface;


public final class SecurePaymentDialogFragment extends DialogFragment {

    private static final String LOg_CAT = "PaymentDialogFragment";

    private String url3Ds;
    private WebView wv3ds;
    private Sale sale;
    private Checkout checkout;


    public  static SecurePaymentDialogFragment newInstance(Sale sale){
        SecurePaymentDialogFragment dialogFragment = new SecurePaymentDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(Sale.BUNDLE_FLAG, sale);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    public  static SecurePaymentDialogFragment newInstance(Sale sale, Checkout checkout){
        SecurePaymentDialogFragment dialogFragment = new SecurePaymentDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(Sale.BUNDLE_FLAG, sale);
        args.putSerializable(Checkout.BUNDLE_FLAG, checkout);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getBundle();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_3ds, container, false);
        loadViews(v);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        buildRequestSecurePayment();
        startRequestSecurePayment();
    }

    private void getBundle(){
        if(getArguments() != null) {
            this.sale = (Sale) getArguments().getSerializable(Sale.BUNDLE_FLAG);
            this.checkout = (Checkout)getArguments().getSerializable(Checkout.BUNDLE_FLAG);
        }
    }

    private void loadViews(View v){
        wv3ds = (WebView)v.findViewById(R.id.wv_3ds);

        wv3ds.setWebViewClient(new NetPayWebClient());
        wv3ds.getSettings().setJavaScriptEnabled(true);
        wv3ds.addJavascriptInterface(new NetPayJsInterface(getContext()), "NetPay");
        wv3ds.getSettings().setLoadWithOverviewMode(true);
        wv3ds.clearCache(true);
    }

    private void buildRequestSecurePayment(){
        url3Ds = NetPay.getIntance().getUrlSecurePayment()
                + "?OrderNumber=" + sale.getOrderNumber()
                +"&Amount=" + this.sale.getTotalSale()
                +"&MerchantResponseURL=" + NetPay.getIntance().getWebHook()
                +"&ConfigId=" + NetPay.getIntance().getConfigId()
                +"&Operator=" + NetPay.getIntance().getUserAcq()
                +"&Channel=" + NetPay.getIntance().getPassAcq()
                +"&Currency=" + NetPay.getIntance().getCurrency()
                +"&CardHolderName="
                +"&ZipCode=" + (this.checkout != null ? this.checkout.getZipCode():"")
                +"&Address=" + (this.checkout != null ? this.checkout.getAddress():"")
                +"&City=" + (this.checkout != null ? this.checkout.getCity():"")
                +"&State=" + (this.checkout != null ? this.checkout.getState():"")
                +"&Email=" + (this.checkout != null ? this.checkout.getEmail():"")
                +"&WebHookResponse=";

        Log.e(LOg_CAT, "URL_3DS: " + url3Ds);
    }

    private void startRequestSecurePayment(){
        if (url3Ds == null || url3Ds.isEmpty()){
            ObservableManager.getInstance().notifyObservers(ObservableManager.NetPayEvents._3DS_ERROR_LOAD);
        }else{
            wv3ds.setVisibility(View.GONE);
            wv3ds.loadUrl(url3Ds);
            //wv3ds.loadUrl("https://cert.netpay.com.mx/SecurePayment/ResponseError.jsp?ResponseCode=90&ResponseMessage=Autenticaci%F3n%203D%20Secure%20no%20fue%20exitosa.%20");
            ObservableManager.getInstance().notifyObservers(ObservableManager.NetPayEvents._3DS_STARTED_LOAD);
        }
    }
}
