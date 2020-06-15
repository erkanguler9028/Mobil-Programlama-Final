
package erkan.odev.com.erkanproje;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerAd {

    @SerializedName("company")
    @Expose
    public String company;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("text")
    @Expose
    public String text;

}