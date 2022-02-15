package upv.dadm.quotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import fragments.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fcv_settings, SettingsFragment.class, null)
                .commit();
    }
}