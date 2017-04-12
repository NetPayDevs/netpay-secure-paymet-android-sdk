package netpay.com.securepaymentsdk.listeners;

import android.os.Parcelable;

import java.io.Serializable;

import netpay.com.securepaymentsdk.beans.Sale;

/**
 * Created by AcheDev on 3/22/17.
 */

public final class NetPayListener {

    public interface OnProcessTransactionListener {

    }

    public interface OnStartLoad3dsListener extends OnProcessTransactionListener {
        void onStartLoad3ds();
    }
    public interface OnFinishLoad3dsListener extends OnProcessTransactionListener {
        void onFinishLoad3ds();
    }


    public interface OnStartTransactionListener extends OnProcessTransactionListener{
        void onStartTransaction(Sale sale);
    }

    public interface OnFinishTransactionListener extends OnProcessTransactionListener {
        void onFinishTransaction(final String jsonTransaction);
    }

    public interface OnErrorTransactionListener extends OnProcessTransactionListener {
        String ORDER_NUMBER = "1111", TOTAL_SALE = "2222", INTERNET_ERROR = "3333";
        void onErrorTransaction(final String message, final String errorCode);
    }
}
