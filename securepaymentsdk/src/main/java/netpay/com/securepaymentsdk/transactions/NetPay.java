package netpay.com.securepaymentsdk.transactions;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import netpay.com.securepaymentsdk.beans.Checkout;
import netpay.com.securepaymentsdk.beans.Sale;
import netpay.com.securepaymentsdk.listeners.NetPayListener;
import netpay.com.securepaymentsdk.utils.ObservableManager;
import netpay.com.securepaymentsdk.utils.TransactionHelper;


public final class NetPay {

    private String configId,userAcq, passAcq, webHook, urlSecurePayment;
    private int currency;

    private Context context;

    private static NetPay iNetPay;

    private NetPay(Context context){
        this.context = context;
    }

    public static NetPay init(Context context){
        if (iNetPay == null) {
            iNetPay = new NetPay(context);
            ObservableManager.newInstance();
        }
        return iNetPay;
    }

    public static NetPay getIntance() {
        return iNetPay;
    }

    public NetPay userNetPayMobil(String userAcq){
        this.userAcq = userAcq;
        return iNetPay;
    }

    public NetPay passwordNetPayMolbil(String passAcq){
        this.passAcq = passAcq;
        return iNetPay;
    }

    public NetPay webHook(String webHook){
        this.webHook = webHook;
        return iNetPay;
    }

    public NetPay urlSecurePayment(String urlSecurePayment){
        this.urlSecurePayment = urlSecurePayment;
        return iNetPay;
    }

    public NetPay currency(int currency){
        this.currency = currency;
        return iNetPay;
    }

    public NetPay configId(String configId){
        this.configId = configId;
        return iNetPay;
    }

    public String getUserNetPayMobil() {
        return userAcq;
    }

    public String getPasswordNetPayMobil() {
        return passAcq;
    }

    public String getWebHook() {
        return webHook;
    }

    public String getUrlSecurePayment() {
        return urlSecurePayment;
    }

    public int getCurrency() {
        return currency;
    }

    public String getConfigId() {
        return configId;
    }

    public Context getContext() {
        return context;
    }

    public NetPay buildTransaction(final Sale sale){
            GatewayTransaction.getInstance().buildTransaction(sale);
        return iNetPay;
    }

    public NetPay buildTransaction(final Sale sale, final Checkout checkout){
        GatewayTransaction.getInstance().buildTransaction(sale, checkout);
        return iNetPay;
    }

    public NetPay startTransaction(AppCompatActivity activity){
        GatewayTransaction.getInstance().starTransaction(activity);
        return iNetPay;
    }

    public NetPay startTransaction(Context context, FragmentManager fragmentManager){
        GatewayTransaction.getInstance().starTransaction(context, fragmentManager);
        return iNetPay;
    }

    public static final String createOrderNumber(){
        return TransactionHelper.getOrderId();
    }

    public NetPay addNetPayListener(NetPayListener.OnProcessTransactionListener onProcessTransactionListener){
        GatewayTransaction.getInstance().addListener(onProcessTransactionListener);
        return iNetPay;
    }

}
