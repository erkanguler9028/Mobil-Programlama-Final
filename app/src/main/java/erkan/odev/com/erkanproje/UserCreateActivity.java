package erkan.odev.com.erkanproje;

import android.os.Bundle;
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

import androidx.appcompat.app.AppCompatActivity;
import erkan.odev.com.erkanproje.api.APIUrl;
import erkan.odev.com.erkanproje.api.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserCreateActivity extends AppCompatActivity {
    private EditText firstNameText;
    private EditText lastNameText;
    private Button createButton;
    private Button cancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_create);

        firstNameText = findViewById(R.id.firstName);
        lastNameText = findViewById(R.id.lastName);
        createButton = findViewById(R.id.createButton);
        cancelButton = findViewById(R.id.cancelButton);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCustomer(v);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void createCustomer(View v){
        String name = firstNameText.getText().toString();
        String job = lastNameText.getText().toString();

        final Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apis = retrofit.create(ApiService.class);

        Call<CustomerCreateResponse> call = apis.createCustomer( name, job);
        call.enqueue(new Callback<CustomerCreateResponse>() {
            @Override
            public void onResponse(Call<CustomerCreateResponse> call, Response<CustomerCreateResponse> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(UserCreateActivity.this,"Ekleme Başarılı", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(UserCreateActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CustomerCreateResponse> call, Throwable t) {
                Toast.makeText(UserCreateActivity.this,"Sistemsel Hata", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
