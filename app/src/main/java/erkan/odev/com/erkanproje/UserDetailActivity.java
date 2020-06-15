package erkan.odev.com.erkanproje;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import erkan.odev.com.erkanproje.api.APIUrl;
import erkan.odev.com.erkanproje.api.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserDetailActivity extends AppCompatActivity {
    private Integer userid;
    private Customer customerDatum;

    private EditText firstNameText;
    private EditText lastNameText;
    private ImageView profileImageView;
    private Button updateButton;
    private Button deleteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_detail);

        firstNameText = findViewById(R.id.firstName);
        lastNameText = findViewById(R.id.lastName);
        profileImageView = findViewById(R.id.imageView);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        Bundle b = getIntent().getExtras();
        userid = -1; // or other values
        if(b != null)
        {
            userid = b.getInt("userid");
            initCustomer();
        }

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCustomer(v);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCustomer(v);
            }
        });

    }

    public void initCustomer() {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        ApiService apis = retrofit.create(ApiService.class);
        Call<Customer> call = apis.getCustomer(userid);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                CustomerDatum customer = response.body().data;
                firstNameText.setText(customer.firstName);
                lastNameText.setText(customer.lastName);
                Picasso.get().load(customer.avatar).into(profileImageView);
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Log.d("snow", t.getMessage().toString());
            }
        });
    }

    public void updateCustomer(View v){
        String firstName = firstNameText.getText().toString();
        String lastName = lastNameText.getText().toString();

        final Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apis = retrofit.create(ApiService.class);
        CustomerUpdateDatum cd = new CustomerUpdateDatum();
        cd.name = firstName;
        cd.job = lastName;

        Call<CustomerUpdateResponse> call = apis.updateCustomer( userid, cd);
        call.enqueue(new Callback<CustomerUpdateResponse>() {
            @Override
            public void onResponse(Call<CustomerUpdateResponse> call, Response<CustomerUpdateResponse> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(UserDetailActivity.this,"Güncelleme Başarılı", Toast.LENGTH_SHORT).show();
                } else {
                    String resp = null;
                    String errorMessage = "";
                    try {
                        resp = response.errorBody().string();
                        JSONObject jObject = new JSONObject(resp);
                        errorMessage = jObject.getString("error");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(UserDetailActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CustomerUpdateResponse> call, Throwable t) {
                Toast.makeText(UserDetailActivity.this,"Sistemsel Hata", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteCustomer(View v){

        final Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apis = retrofit.create(ApiService.class);
        CustomerUpdateDatum cd = new CustomerUpdateDatum();

        Call<String> call = apis.deleteCustomer(userid);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(UserDetailActivity.this,"Silme Başarılı", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    String resp = null;
                    String errorMessage = "";
                    try {
                        resp = response.errorBody().string();
                        JSONObject jObject = new JSONObject(resp);
                        errorMessage = jObject.getString("error");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(UserDetailActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(UserDetailActivity.this,"Sistemsel Hata", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
