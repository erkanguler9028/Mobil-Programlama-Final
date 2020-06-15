package erkan.odev.com.erkanproje;

import androidx.appcompat.app.AppCompatActivity;
import erkan.odev.com.erkanproje.api.APIUrl;
import erkan.odev.com.erkanproje.api.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    private TextView userNameView;
    private TextView passwordView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button = findViewById(R.id.buttonRegister);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });
    }

    public void openRegisterActivity(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void customerLogin(View v) {
        userNameView = findViewById(R.id.kullaniciAdiInput);
        passwordView = findViewById(R.id.sifreInput);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apis = retrofit.create(ApiService.class);
        String username = userNameView.getText().toString();
        String password = passwordView.getText().toString();
        Call<CustomerLoginResponse> call = apis.loginCustomer( username, password);
        call.enqueue(new Callback<CustomerLoginResponse>() {
            @Override
            public void onResponse(Call<CustomerLoginResponse> call, Response<CustomerLoginResponse> response) {

                if (response.isSuccessful()) {
                    if(response.errorBody() != null){
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
                        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(LoginActivity.this,"Giriş Başarılı, Yönlendirilirken lütfen bekleyin", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent gecis = new Intent(LoginActivity.this, UserListActivity.class);
                                startActivity(gecis);
                                finish();
                            }
                        }, 2000);
                    }

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
                    Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CustomerLoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Sistemsel Hata", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
