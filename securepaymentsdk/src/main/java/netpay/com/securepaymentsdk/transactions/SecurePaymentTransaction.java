package netpay.com.securepaymentsdk.transactions;


import android.content.Context;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import netpay.com.securepaymentsdk.beans.Checkout;
import netpay.com.securepaymentsdk.beans.Sale;
import netpay.com.securepaymentsdk.listeners.NetPayListener;
import netpay.com.securepaymentsdk.utils.ObservableManager;
import netpay.com.securepaymentsdk.utils.TransactionHelper;

/**
 * Created by AcheDev on 3/29/17.
 */

class SecurePaymentTransaction implements Observer{

    private static final String LOG_CAT = "SecureTransaction";

    private static SecurePaymentTransaction iSecurePaymentTransaction;
    private Sale sale;
    private Checkout checkout;

    private DialogFragment dialogFragment;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;

    ArrayList<NetPayListener.OnProcessTransactionListener> listListener = new ArrayList();

    private SecurePaymentTransaction (){}

    public static SecurePaymentTransaction getInstance(){
        if (iSecurePaymentTransaction == null) {
            iSecurePaymentTransaction = new SecurePaymentTransaction();
            ObservableManager.getInstance().addObserver(iSecurePaymentTransaction);
        }
        return iSecurePaymentTransaction;
    }

    void buildTransaction(final Sale sale) {
        this.sale = sale;
    }

    void buildTransaction(final Sale sale, Checkout checkout) {
        this.sale = sale;
        this.checkout = checkout;
    }

    void addListener(NetPayListener.OnProcessTransactionListener onProcessTransactionListener) {
        if(!listListener.contains(onProcessTransactionListener))
            this.listListener.add(onProcessTransactionListener);
    }

    void startTransaction(AppCompatActivity activity) {
        this.fragmentManager = activity.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        startTransaction();
    }

    void startTransaction(Context context, FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.fragmentTransaction = fragmentManager.beginTransaction();

        startTransaction();
    }

    private void startTransaction(){
        if (isValidateOrderNumber(this.sale) && isValidateTotalSale(sale) && this.fragmentManager != null && this.fragmentTransaction != null){
            Fragment prev = this.fragmentManager.findFragmentByTag("dialog_3ds");
            if(prev != null)
                this.fragmentTransaction.remove(prev);
            fragmentTransaction.addToBackStack(null);

            dialogFragment = SecurePaymentDialogFragment.newInstance(this.sale, checkout);
            dialogFragment.show(fragmentTransaction,"dialog_3ds");
        }
    }

    private void dismiss3dsDialogFragment(){

        if(dialogFragment != null) {
            dialogFragment.dismiss();
        }
    }

    private boolean isValidateTotalSale(Sale sale){
        if (sale != null && sale.getTotalSale() > Sale.VALIDATE_MIN_AMOUNT && sale.getTotalSale() < Sale.VALIDATE_MAX_AMOUNT){
            return true;
        }else {
            sendErrorTransactionListener("Total Sale is invalid: $" + this.sale.getTotalSale(), NetPayListener.OnErrorTransactionListener.TOTAL_SALE);
            return false;
        }
    }

    private boolean isValidateOrderNumber(Sale sale){
        if (sale != null && !sale.getOrderNumber().isEmpty())
            return true;
        else
            sendErrorTransactionListener("Order Number is invalid, is null o empty", NetPayListener.OnErrorTransactionListener.ORDER_NUMBER);
            return false;
    }

    private void sendErrorTransactionListener(String message, String errorCode) {

        this.dismiss3dsDialogFragment();

        NetPayListener.OnErrorTransactionListener errorTransactionListener = ( NetPayListener.OnErrorTransactionListener) TransactionHelper.resolveListener(this.listListener, NetPayListener.OnErrorTransactionListener.class);

        if (errorTransactionListener != null)
            errorTransactionListener.onErrorTransaction(message, errorCode);
    }

    @Override
    public void update(Observable observable, Object arg) {
        if (arg instanceof ObservableManager.NetPayEvents) {

            switch (((ObservableManager.NetPayEvents) arg)) {
                case _3DS_STARTED_LOAD:
                    NetPayListener.OnStartLoad3dsListener startLoad3dsListener = ( NetPayListener.OnStartLoad3dsListener) TransactionHelper.resolveListener(this.listListener, NetPayListener.OnStartLoad3dsListener.class);

                    if (startLoad3dsListener != null)
                        startLoad3dsListener.onStartLoad3ds();
                    break;

                case _3DS_FINISH_LOAD:
                    NetPayListener.OnFinishLoad3dsListener finishLoad3dsListener  = ( NetPayListener.OnFinishLoad3dsListener) TransactionHelper.resolveListener(this.listListener, NetPayListener.OnFinishLoad3dsListener.class);

                    if (finishLoad3dsListener != null)
                        finishLoad3dsListener.onFinishLoad3ds();
                    break;

                case _3DS_ERROR_LOAD:
                    this.dismiss3dsDialogFragment();
                    this.sendErrorTransactionListener("Internet Error", NetPayListener.OnErrorTransactionListener.INTERNET_ERROR);
                    break;

                case _3DS_START_TRANSACTION:
                    NetPayListener.OnStartTransactionListener startTransactionListener = ( NetPayListener.OnStartTransactionListener) TransactionHelper.resolveListener(this.listListener, NetPayListener.OnStartTransactionListener.class);

                    if (startTransactionListener != null)
                        startTransactionListener.onStartTransaction(((ObservableManager.NetPayEvents) arg).getSale());
                    break;

                case _3DS_FINISH_TRANSACTION:
                    this.dismiss3dsDialogFragment();
                    NetPayListener.OnFinishTransactionListener finishTransactionListener = ( NetPayListener.OnFinishTransactionListener) TransactionHelper.resolveListener(this.listListener, NetPayListener.OnFinishTransactionListener.class);

                    if (finishTransactionListener != null)
                        finishTransactionListener.onFinishTransaction(((ObservableManager.NetPayEvents) arg).getJsonTransaction());
                    break;
            }
        }
    }
}