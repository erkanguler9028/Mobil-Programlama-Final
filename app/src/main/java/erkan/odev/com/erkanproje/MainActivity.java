package erkan.odev.com.erkanproje;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView splashYazi;
    private ImageView splashResim;
    private static int GecisSuresi = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        splashYazi = findViewById(R.id.splashYazi);
        splashResim = findViewById(R.id.splashResim);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent gecis = new Intent(MainActivity.this, LoginActivity.class);
                //Intent gecis = new Intent(MainActivity.this, UserListActivity.class);
                startActivity(gecis);
                finish();
            }
        }, GecisSuresi);
    }


}
