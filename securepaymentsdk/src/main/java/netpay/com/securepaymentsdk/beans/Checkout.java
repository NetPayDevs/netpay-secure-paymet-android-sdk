package netpay.com.securepaymentsdk.beans;

import java.io.Serializable;

/**
 * Created by AcheDev on 3/31/17.
 */

public final class Checkout implements Serializable{

    public static final String BUNDLE_FLAG = "checkout_bundle_flag";
    private static Checkout iCheackout;

    private String customerName;
    private String zipCode;
    private String address;
    private String city;
    private String state;
    private String email;

    private Checkout(){
    }

    public static Checkout newInstance(){
        return iCheackout = new Checkout();
    }

    public static Checkout getInstance(){
        return iCheackout;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Checkout customerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Checkout zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Checkout address(String address) {
        this.address = address;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Checkout city(String city) {
        this.city = city;
        return this;
    }

    public String getState() {
        return state;
    }

    public Checkout state(String state) {
        this.state = state;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Checkout email(String email) {
        this.email = email;
        return this;
    }
}
