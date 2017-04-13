package netpay.com.securepayment;

import android.app.Application;

import netpay.com.securepaymentsdk.transactions.NetPay;


public class SubApplication extends Application {

    private static final int  CURRENCY = 484;
    private static final String CONFIG_ID = "001",USER_ACQ = "mandala@reservaciones.com.mx", PASS_ACQ = "mandalares", URL_SECURE_PAYMENT = "https://cert.netpay.com.mx/SecurePayment/NetPayPayment.jsp";
    //https://cert.netpay.com.mx/SecurePayment/Responsive.jsp
    //https://cert.netpay.com.mx/SecurePayment/ResponseError.jsp
    //https://cert.netpay.com.mx/SecurePayment/NetPayPayment.jsp
    //https://cert.netpay.com.mx/SecurePayment/ResponseError.jsp?ResponseCode=90&ResponseMessage=Autenticaci%F3n%203D%20Secure%20no%20fue%20exitosa.%20

    @Override
    public void onCreate() {
        super.onCreate();
        NetPay.init(this)
                .configId(CONFIG_ID)
                .userNetPayMobil(USER_ACQ)
                .passwordNetPayMobil(PASS_ACQ)
                .currency(CURRENCY)
                .urlSecurePayment(URL_SECURE_PAYMENT);
    }
}
