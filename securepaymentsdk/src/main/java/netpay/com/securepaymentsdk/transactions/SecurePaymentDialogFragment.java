package netpay.com.securepaymentsdk.transactions;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;

import netpay.com.securepaymentsdk.beans.Checkout;
import netpay.com.securepaymentsdk.R;
import netpay.com.securepaymentsdk.beans.Sale;
import netpay.com.securepaymentsdk.utils.Log;
import netpay.com.securepaymentsdk.utils.ObservableManager;
import netpay.com.securepaymentsdk.web.clients.NetPayWebClient;
import netpay.com.securepaymentsdk.web.interfaces.NetPayJsInterface;

final class SecurePaymentDialogFragment extends DialogFragment {

    private static final String LOg_CAT = "PaymentDialogFragment";

    private String url3Ds;
    private WebView wv3ds;
    private Sale sale;
    private Checkout checkout;


    public  static SecurePaymentDialogFragment newInstance(Sale sale){
        SecurePaymentDialogFragment paymentDialogFragment = new SecurePaymentDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(Sale.BUNDLE_FLAG, sale);
        paymentDialogFragment.setArguments(args);
        return paymentDialogFragment;
    }

    public  static SecurePaymentDialogFragment newInstance(Sale sale, Checkout checkout){
        SecurePaymentDialogFragment paymentDialogFragment = new SecurePaymentDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(Sale.BUNDLE_FLAG, sale);
        args.putSerializable(Checkout.BUNDLE_FLAG, checkout);
        paymentDialogFragment.setArguments(args);
        return paymentDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
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
        wv3ds.setEnabled(true);
    }

    private void buildRequestSecurePayment(){
        url3Ds = NetPay.getIntance().getUrlSecurePayment()
                + "?OrderNumber=" + sale.getOrderNumber()
                + (this.sale.getOrderNumber() != null ? "&Amount=" + this.sale.getTotalSale(): "")
                + (NetPay.getIntance().getWebHook() != null ? "&MerchantResponseURL="+ NetPay.getIntance().getWebHook():"")
                + (NetPay.getIntance().getConfigId() != null ? "&ConfigId=" + NetPay.getIntance().getConfigId(): "")
                + (NetPay.getIntance().getUserNetPayMobil() != null  ? "&Operator=" + NetPay.getIntance().getUserNetPayMobil() : "")
                + (NetPay.getIntance().getPasswordNetPayMobil() != null ? "&Channel=" + NetPay.getIntance().getPasswordNetPayMobil():"")
                + "&Currency=" + NetPay.getIntance().getCurrency()
                + (this.checkout.getZipCode() != null ? "&ZipCode=" + this.checkout.getZipCode() :"")
                + (this.checkout.getAddress() != null ? "&Address=" + this.checkout.getAddress() : "")
                + (this.checkout.getCity() != null ? "&City=" + this.checkout.getCity():"")
                + (this.checkout.getState() != null ?"&State=" + this.checkout.getState():"")
                + (this.checkout.getEmail() != null ?"&Email=" + this.checkout.getEmail():"")
                + (this.sale.getPromotionMSI() != null ? "&Promotion=" + this.sale.getPromotionMSI().getPromtion():"")
                + (NetPay.getIntance().getWebHook() != null ? "&WebHookResponse=" + NetPay.getIntance().getWebHook() : "");

        Log.e(LOg_CAT, "URL_3DS: " + url3Ds);
    }

    private void startRequestSecurePayment(){
        if (url3Ds == null || url3Ds.isEmpty()){
            ObservableManager.getInstance().notifyObservers(ObservableManager.NetPayEvents._3DS_ERROR_LOAD);
        }else{
            wv3ds.loadUrl(url3Ds);
            ObservableManager.NetPayEvents startTransaction = ObservableManager.NetPayEvents._3DS_START_TRANSACTION;
            startTransaction.setSaleTransaction(this.sale);
            ObservableManager.getInstance().notifyObservers(startTransaction);
        }
    }

    @Override
    public void onDestroyView() {
        if (this.wv3ds != null){
            this.wv3ds.freeMemory();
            this.wv3ds.clearCache(true);
            this.wv3ds.pauseTimers();
            this.wv3ds.clearCache(true);
            this.wv3ds.clearHistory();
            this.wv3ds = null;
        }
        super.onDestroyView();
    }
}
