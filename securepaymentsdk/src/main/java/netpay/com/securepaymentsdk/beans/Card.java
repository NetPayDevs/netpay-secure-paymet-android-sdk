package netpay.com.securepaymentsdk.beans;

import java.io.Serializable;

/**
 * Created by AcheDev on 3/30/17.
 */

public class Card implements Serializable{

    private String cardType;
    private long numberCard;
    private String dateExpire;
    private int secureCode;
    private String customerEmail;
}
