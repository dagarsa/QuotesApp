package upv.dadm.quotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Button bGet = findViewById(R.id.bGet);
        Button bFavourite = findViewById(R.id.bFavourite);
        Button bSettings = findViewById(R.id.bSettings);
        Button bAbout = findViewById(R.id.bAbout);
    }
}