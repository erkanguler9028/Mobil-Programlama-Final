package erkan.odev.com.erkanproje;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.ResponseBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;

import androidx.appcompat.app.AppCompatActivity;
import erkan.odev.com.erkanproje.api.APIUrl;
import erkan.odev.com.erkanproje.api.ApiService;
import erkan.odev.com.erkanproje.api.ModelError;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private TextView userNameView;
    private TextView passwordView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        button = findViewById(R.id.buttonLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });
    }

    public void openLoginActivity(){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void customerRegister(View v) {
        userNameView = findViewById(R.id.kullaniciAdiInput);
        passwordView = findViewById(R.id.sifreInput);

        final Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apis = retrofit.create(ApiService.class);
        String username = userNameView.getText().toString();
        String password = passwordView.getText().toString();
        Call<CustomerRegisterResponse> call = apis.registerCustomer( username, password);
        call.enqueue(new Callback<CustomerRegisterResponse>() {
            @Override
            public void onResponse(Call<CustomerRegisterResponse> call, Response<CustomerRegisterResponse> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this,"Kayıt Başarılı", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CustomerRegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,"Sistemsel Hata", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
