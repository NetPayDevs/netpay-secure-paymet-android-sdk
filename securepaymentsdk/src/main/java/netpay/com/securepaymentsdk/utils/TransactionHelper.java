package netpay.com.securepaymentsdk.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import netpay.com.securepaymentsdk.listeners.NetPayListener;

/**
 * Created by AcheDev on 3/30/17.
 */

public final class TransactionHelper {

    public static String getOrderId(){
        return String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
    }

    public static NetPayListener.OnProcessTransactionListener resolveListener(ArrayList<NetPayListener.OnProcessTransactionListener> listListener, Class clazz){
        for (NetPayListener.OnProcessTransactionListener listener: listListener) {
            if (clazz.isInstance(listener)){
                return listener;
            }
        }
        return null;
    }
}
