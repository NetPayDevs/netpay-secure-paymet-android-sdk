package netpay.com.securepaymentsdk.transactions;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import netpay.com.securepaymentsdk.beans.Checkout;
import netpay.com.securepaymentsdk.beans.Sale;
import netpay.com.securepaymentsdk.listeners.NetPayListener;

final class GatewayTransaction {

    private static GatewayTransaction iGatewayTransaction;

    private GatewayTransaction(){}

    public static GatewayTransaction getInstance(){
        if(iGatewayTransaction == null)
            iGatewayTransaction = new GatewayTransaction();
        return iGatewayTransaction;
    }

    /*public void buildTransaction(Card card, Sale sale){
         SecurePaymentTransaction.getInstance().buildTransaction(card,sale);
    }*/

    void buildTransaction(final Sale sale){
         SecurePaymentTransaction.getInstance().buildTransaction(sale);
    }

    void buildTransaction(final Sale sale, final Checkout checkout){
        SecurePaymentTransaction.getInstance().buildTransaction(sale, checkout);
    }

    void starTransaction(AppCompatActivity activity){
        SecurePaymentTransaction.getInstance().startTransaction(activity);
    }

    void starTransaction(Context context, FragmentManager fragmentManager){
        SecurePaymentTransaction.getInstance().startTransaction(context, fragmentManager);
    }

    void addListener(NetPayListener.OnProcessTransactionListener onProcessTransactionListener){
        SecurePaymentTransaction.getInstance().addListener(onProcessTransactionListener);
    }
}
