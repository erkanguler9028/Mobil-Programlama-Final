package erkan.odev.com.erkanproje;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerUpdateDatum {

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("job")
    @Expose
    public String job;
}
