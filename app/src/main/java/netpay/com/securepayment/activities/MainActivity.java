package netpay.com.securepayment.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import netpay.com.securepayment.R;
import netpay.com.securepaymentsdk.beans.Checkout;
import netpay.com.securepaymentsdk.transactions.NetPay;
import netpay.com.securepaymentsdk.beans.Sale;
import netpay.com.securepaymentsdk.listeners.NetPayListener;

public class MainActivity extends AppCompatActivity implements NetPayListener.OnStartTransactionListener, NetPayListener.OnErrorTransactionListener, NetPayListener.OnFinishTransactionListener, NetPayListener.OnStartLoad3dsListener, NetPayListener.OnFinishLoad3dsListener{

    private static final String LOG_CAT = "MainActivity";

    private Button btnPay;
    private TextView txvResponseTransaction;

    private double totalSale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPay = (Button)findViewById(R.id.btn_pay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkoutDialog().show();
            }
        });

        txvResponseTransaction = (TextView)findViewById(R.id.txv_response_transaction);
    }

    private void buildTransaction(){
        Sale sale = new Sale();
        sale.setTotalSale(totalSale);
        sale.setOrderNumber(NetPay.createOrderNumber());
        sale.addPromotionMSI(Sale.PromotionMSI.MSI_3);

        Checkout checkout = Checkout.newInstance().address("una calle")
                .city("una ciudad")
                .customerName("Heriberto Rivera")
                .email("mailto@g.com")
                .zipCode("67190")
                .state("Nuevo Leon");

        NetPay.getIntance().addNetPayListener(this);

        NetPay.getIntance().buildTransaction(sale,checkout).startTransaction(MainActivity.this);
    }

    public AlertDialog checkoutDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Total Sale");
        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!input.getText().toString().isEmpty())
                    totalSale = Double.valueOf(input.getText().toString());
                dialog.dismiss();
                buildTransaction();


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return builder.create();
    }

    @Override
    public void onStartTransaction(Sale sale){
        Log.e(LOG_CAT, "onStartTransaction(): " + sale.getOrderNumber());
    }

    @Override
    public void onErrorTransaction(final String message, final String errorCode) {
        Log.e(LOG_CAT, "onErrorTransaction(): " + message + " Error Code: " + errorCode);
    }

    @Override
    public void onFinishTransaction(final String jsonTransaction) {
        Log.e(LOG_CAT, "onFinishTransaction(): " + jsonTransaction);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txvResponseTransaction.setText(jsonTransaction);
            }
        });

    }

    @Override
    public void onFinishLoad3ds() {
        Log.e(LOG_CAT, "onFinishLoad3ds(): " );
    }

    @Override
    public void onStartLoad3ds() {
        Log.e(LOG_CAT, "onStartLoad3ds(): ");
    }
}
