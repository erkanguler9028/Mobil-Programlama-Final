package erkan.odev.com.erkanproje.api;

import erkan.odev.com.erkanproje.Customer;
import erkan.odev.com.erkanproje.CustomerCreateResponse;
import erkan.odev.com.erkanproje.CustomerDatum;
import erkan.odev.com.erkanproje.CustomerListResponse;
import erkan.odev.com.erkanproje.CustomerLoginResponse;
import erkan.odev.com.erkanproje.CustomerRegisterResponse;
import erkan.odev.com.erkanproje.CustomerUpdateDatum;
import erkan.odev.com.erkanproje.CustomerUpdateResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;



public interface ApiService {

    @GET("api/users?page=2")
    Call<CustomerListResponse> getCutomers();
    @GET("api/users")
    Call<CustomerListResponse> getCustomers(@Query("page") int page);
    @GET("api/users")
    Call<Customer> getCustomer(@Query("id") int id);
    @PUT("api/users/{id}")
    Call<CustomerUpdateResponse> updateCustomer(@Path("id") int id, @Body CustomerUpdateDatum customerUpdateDatum);
    @DELETE("api/users/{id}")
    Call<String> deleteCustomer(@Path("id") int id);
    @FormUrlEncoded
    @POST("api/users")
    Call<CustomerCreateResponse> createCustomer(@Field("name") String name, @Field("job") String job);

    @FormUrlEncoded
    @POST("api/login")
    Call<CustomerLoginResponse> loginCustomer(@Field("email") String email, @Field("password") String password);
    @FormUrlEncoded
    @POST("api/register")
    Call<CustomerRegisterResponse> registerCustomer(@Field("email") String email, @Field("password") String password);

}
