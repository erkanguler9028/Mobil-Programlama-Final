package erkan.odev.com.erkanproje;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import erkan.odev.com.erkanproje.api.APIUrl;
import erkan.odev.com.erkanproje.api.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserListActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<Integer> mIds = new ArrayList<>();
    private int page;
    private int total_page;
    private List<CustomerDatum> dat;
    private Button newCustomerButton;
    private RecyclerView rw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Başladı");
        setContentView(R.layout.user_list);
        page = 1;
        rw = findViewById(R.id.recycler_view);
        rw.addOnScrollListener(new CustomScrollListener());

        newCustomerButton = findViewById(R.id.newCustomer);
        newCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserListActivity.this, UserCreateActivity.class);
                startActivity(intent);
            }
        });

        initUserList(page);
    }

    public void openUserDetail(){
        Intent intent = new Intent(UserListActivity.this, UserDetailActivity.class);
        Bundle b = new Bundle();
        b.putInt("userid", 1); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
    }
    public void initUserList(int page) {

        Toast.makeText(UserListActivity.this, "Listenin devamı yükleniyor", Toast.LENGTH_SHORT).show();

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        ApiService apis = retrofit.create(ApiService.class);
        Call<CustomerListResponse> call = apis.getCustomers(page);
        call.enqueue(new Callback<CustomerListResponse>() {
            @Override
            public void onResponse(Call<CustomerListResponse> call, Response<CustomerListResponse> response) {
                total_page = response.body().totalPages;
                dat = response.body().data;

                for (int i = 0; i < dat.size(); i++){
                    mImageNames.add(dat.get(i).firstName);
                    mImageUrls.add(dat.get(i).avatar);
                    mIds.add(dat.get(i).id);
                }

                initRecyclerView();
            }

            @Override
            public void onFailure(Call<CustomerListResponse> call, Throwable t) {
                Log.d("snow", t.getMessage().toString());
            }
        });
    }
    private void initRecyclerView(){
        RecyclerView recycleView = findViewById(R.id.recycler_view);
        UserListViewAdapter adapter = new UserListViewAdapter(this, mImageNames,mImageUrls,mIds);
        recycleView.setAdapter(adapter);
        recycleView.setLayoutManager(new LinearLayoutManager(this));

        page++;
    }

    public class CustomScrollListener extends RecyclerView.OnScrollListener {

        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                if(page <= total_page)
                {
                    Log.d("-----","end");
                    initUserList(page);
                }
            }
        }

    }
}


