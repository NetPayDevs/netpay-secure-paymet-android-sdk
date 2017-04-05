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

import netpay.com.securepayment.R;
import netpay.com.securepaymentsdk.beans.Checkout;
import netpay.com.securepaymentsdk.transactions.NetPay;
import netpay.com.securepaymentsdk.beans.Sale;
import netpay.com.securepaymentsdk.listeners.NetPayListener;

public class MainActivity extends AppCompatActivity implements NetPayListener.OnStartTransactionListener, NetPayListener.OnErrorTransactionListener, NetPayListener.OnFinishTransactionListener{

    private static final String LOG_CAT = "MainActivity";

    private Button btnPay;
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
    }

    private void buildTransaction(){
        Sale sale = new Sale();
        sale.setTotalSale(totalSale);
        sale.setOrderNumber(NetPay.createOrderNumber());

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
                buildTransaction();
                dialog.dismiss();

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
    public void onStartTransaction() {
        Log.e(LOG_CAT, "onStartTransaction()");
    }

    @Override
    public void onErrorTransaction(String message, String errorCode) {
        Log.e(LOG_CAT, "onErrorTransaction(): " + message + " Error Code: " + errorCode);
    }

    @Override
    public void onFinishTransaction(String jsonTransaction) {
        Log.e(LOG_CAT, "onFinishTransaction(): " + jsonTransaction);
    }
}
