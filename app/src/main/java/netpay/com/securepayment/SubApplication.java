package netpay.com.securepayment;

import android.app.Application;

import netpay.com.securepaymentsdk.transactions.NetPay;


public class SubApplication extends Application {

    private static final int  CURRENCY = 484;
    private static final String CONFIG_ID = "001",USER_ACQ = "mandala@reservaciones.com.mx", PASS_ACQ = "mandalares", URL_SECURE_PAYMENT = "https://cert.netpay.com.mx/SecurePayment/Responsive.jsp";
    //https://cert.netpay.com.mx/SecurePayment/Responsive.jsp
    //https://cert.netpay.com.mx/SecurePayment/ResponseError.jsp

    @Override
    public void onCreate() {
        super.onCreate();
        NetPay.init(this)
                .configId(CONFIG_ID)
                .userAcq(USER_ACQ)
                .passwordAcq(PASS_ACQ)
                .currency(CURRENCY)
                .urlSecurePayment(URL_SECURE_PAYMENT);
    }
}
