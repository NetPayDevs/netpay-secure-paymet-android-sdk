package netpay.com.securepaymentsdk.beans;

import java.io.Serializable;
import java.text.DecimalFormat;

import netpay.com.securepaymentsdk.exceptions.PromotionException;

import static netpay.com.securepaymentsdk.beans.Sale.PromotionMSI.sale_12MSI;
import static netpay.com.securepaymentsdk.beans.Sale.PromotionMSI.sale_3MSI;
import static netpay.com.securepaymentsdk.beans.Sale.PromotionMSI.sale_6MSI;
import static netpay.com.securepaymentsdk.beans.Sale.PromotionMSI.sale_9MSI;

/**
 * Created by AcheDev on 3/29/17.
 */

public final class Sale implements Serializable{

    public static final String BUNDLE_FLAG = "sale_bundle_flag";

    private String orderNumber;
    private double totalSale;
    private PromotionMSI promotionMSI;

    public static final double VALIDATE_MIN_AMOUNT = 0.00, VALIDATE_MAX_AMOUNT= 1000000.00;

    public Sale (){}

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

    public boolean addPromotionMSI(PromotionMSI promotionMSI){

        this.promotionMSI = null;

        switch (promotionMSI){
            case MSI_3:
                if(this.totalSale < sale_3MSI)
                    throw new PromotionException("This promotion does not apply, the amount must be greater than 300.00");
                else
                    this.promotionMSI = promotionMSI;
                break;
            case MSI_6:
                if(this.totalSale < sale_6MSI)
                    throw new PromotionException("This promotion does not apply, the amount must be greater than 600.00");
                else
                    this.promotionMSI = promotionMSI;
                break;
            case MSI_9:
                if(this.totalSale < sale_9MSI)
                    throw new PromotionException("This promotion does not apply, the amount must be greater than 900.00");
                else
                    this.promotionMSI = promotionMSI;
                break;
            case MSI_12:
                if(this.totalSale < sale_12MSI)
                    throw new PromotionException("This promotion does not apply, the amount must be greater than 1,200.00");
                else
                    this.promotionMSI = promotionMSI;
                break;
        }

        if (this.promotionMSI != null)
            return true;
        else
            return false;
    }

    public PromotionMSI getPromotionMSI(){
        return this.promotionMSI;
    }


    public enum  PromotionMSI implements Serializable{

        MSI_3,MSI_6,MSI_9,MSI_12;

        static final int sale_3MSI = 300;
        static final int sale_6MSI = 600;
        static final int sale_9MSI = 900;
        static final int sale_12MSI = 1200;

        public String getPromtion(){
            switch (this){
                case MSI_3:
                    return "000303";
                case MSI_6:
                    return "000603";
                case MSI_9:
                    return "000903";
                case MSI_12:
                    return "001203";
                default:
                    return null;
            }
        }
    }
}