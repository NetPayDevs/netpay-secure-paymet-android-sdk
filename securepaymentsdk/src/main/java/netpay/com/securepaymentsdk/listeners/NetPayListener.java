package netpay.com.securepaymentsdk.listeners;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by AcheDev on 3/22/17.
 */

public final class NetPayListener {

    public interface OnProcessTransactionListener {

    }

    public interface OnStartTransactionListener extends OnProcessTransactionListener{
        void onStartTransaction();
    }

    public interface OnFinishTransactionListener extends OnProcessTransactionListener {
        void onFinishTransaction(String jsonTransaction);
    }

    public interface OnErrorTransactionListener extends OnProcessTransactionListener {
        String ORDER_NUMBER = "1111", TOTAL_SALE = "2222", INTERNET_ERROR = "3333";
        void onErrorTransaction(String message, String errorCode);
    }
}
