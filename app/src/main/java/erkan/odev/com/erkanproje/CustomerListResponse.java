package erkan.odev.com.erkanproje;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerListResponse {

    @SerializedName("page")
    @Expose
    public Integer page;
    @SerializedName("per_page")
    @Expose
    public Integer perPage;
    @SerializedName("total")
    @Expose
    public Integer total;
    @SerializedName("total_pages")
    @Expose
    public Integer totalPages;
    @SerializedName("data")
    @Expose
    public List<CustomerDatum> data = null;

    public List<CustomerDatum> getCustomer(){
        return data;
    }
}






