package erkan.odev.com.erkanproje;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerCreateResponse {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("job")
    @Expose
    public String job;
    @SerializedName("createdAt")
    @Expose
    public String createdAt;

}
