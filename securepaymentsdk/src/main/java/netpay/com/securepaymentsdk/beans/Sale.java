package netpay.com.securepaymentsdk.beans;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Created by AcheDev on 3/29/17.
 */

public final class Sale implements Serializable{

    public static final String BUNDLE_FLAG = "sale_bundle_flag";
    private String orderNumber;
    private double totalSale;

    public static final double VALIDATE_MIN_AMOUNT = 0.00, VALIDATE_MAX_AMOUNT= 1000000.00;

    public Sale (){}

    /*public Sale (String orderNumber, Double totalSale){
        this.orderNumber = orderNumber;
        this.totalSale = totalSale;
    }*/

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Double getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(double totalSale) {
        if (totalSale <= VALIDATE_MIN_AMOUNT || totalSale > VALIDATE_MAX_AMOUNT){
            throw new IllegalArgumentException("Total sale must not be $00.00 or less or the total sale should not be greater than $1,000,000.00");
        }else{
            this.totalSale = Double.valueOf(new DecimalFormat("#.##").format(totalSale));
        }
    }
}