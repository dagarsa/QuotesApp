package upv.dadm.quotesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import fragments.AboutFragment;
import fragments.FavouriteFragment;
import fragments.QuotationFragment;
import fragments.SettingsFragment;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bnvDashboard);

        NavigationBarView.OnItemSelectedListener onItemSelectedListener = new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Class<? extends Fragment> fragment = null;
                if (item.getItemId() == R.id.itemGetQuotation){
                    fragment = QuotationFragment.class;
                    getSupportActionBar().setTitle(R.string.get);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fcvDashboard, fragment, null)
                            .commit();
                }
                if (item.getItemId() == R.id.itemFavourite){
                    fragment = FavouriteFragment.class;
                    getSupportActionBar().setTitle(R.string.favourite);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fcvDashboard, fragment, null)
                            .commit();
                }
                if (item.getItemId() == R.id.itemSettings){
                    fragment = SettingsFragment.class;
                    getSupportActionBar().setTitle(R.string.settings);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fcvDashboard, fragment, null)
                            .commit();
                }
                if (item.getItemId() == R.id.itemAbout){
                    fragment = AboutFragment.class;
                    getSupportActionBar().setTitle(R.string.about);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fcvDashboard, fragment, null)
                            .commit();
                }
                return true;
            }
        };

        bottomNavigationView.setOnItemSelectedListener(onItemSelectedListener);

        Class<? extends Fragment> fragment = null;
        if(savedInstanceState==null){
            fragment = QuotationFragment.class;
            getSupportActionBar().setTitle(R.string.get);
            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fcvDashboard, fragment, null)
                    .commit();
        }
    }
}