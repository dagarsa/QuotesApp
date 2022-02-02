package upv.dadm.quotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Button bGet = findViewById(R.id.bGet);
        Button bFavourite = findViewById(R.id.bFavourite);
        Button bSettings = findViewById(R.id.bSettings);
        Button bAbout = findViewById(R.id.bAbout);

        Log.d("mensaje", "estoy en dasboardActivity");

        View.OnClickListener onClickMethod = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bGet.isPressed()){
                    Toast.makeText(DashboardActivity.this, "Has pulsado " + bGet.getText(), Toast.LENGTH_LONG).show();
                }
                if (bFavourite.isPressed()){
                    Toast.makeText(DashboardActivity.this, "Has pulsado " + bFavourite.getText(), Toast.LENGTH_LONG).show();
                }
                if (bSettings.isPressed()){
                    Toast.makeText(DashboardActivity.this, "Has pulsado " + bSettings.getText(), Toast.LENGTH_LONG).show();
                }
                if (bAbout.isPressed()){
                    //Toast.makeText(DashboardActivity.this, "Has pulsado " + bAbout.getText(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(DashboardActivity.this, AboutActivity.class);
                    startActivity(intent);
                }

            }
        };

        bGet.setOnClickListener(onClickMethod);
        bFavourite.setOnClickListener(onClickMethod);
        bSettings.setOnClickListener(onClickMethod);
        bAbout.setOnClickListener(onClickMethod);



    }
}