package erkan.odev.com.erkanproje;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Customer {

    @SerializedName("data")
    @Expose
    public CustomerDatum data;
    @SerializedName("ad")
    @Expose
    public CustomerAd ad;

}