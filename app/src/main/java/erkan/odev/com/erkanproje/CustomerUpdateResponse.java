package erkan.odev.com.erkanproje;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerUpdateResponse {

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("job")
    @Expose
    public String job;
    @SerializedName("updatedAt")
    @Expose
    public String updatedAt;

}


