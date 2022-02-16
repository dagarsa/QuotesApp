package upv.dadm.quotesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class QuotationActivity extends AppCompatActivity {

    private int nCitas = 0;

    private Menu menu;

    TextView tvQuotation;
    TextView tvAbajo;

    private boolean addVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);

        if(savedInstanceState == null){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String name = sharedPreferences.getString("username", "");

            if (name.replace(" ","").equals("")){
                name = "Nameless One";
            }

            TextView tvName = findViewById(R.id.tvHello);
            String tvGetHello = getString(R.string.tvGetHello, name);
            tvName.setText(tvGetHello);
        }else {
            nCitas = savedInstanceState.getInt("nCitas");
            tvQuotation = findViewById(R.id.tvHello);
            tvAbajo = findViewById(R.id.tvAbajo);
            tvQuotation.setText(savedInstanceState.getString("tvQuotation"));
            tvAbajo.setText(savedInstanceState.getString("tvAbajo"));
            addVisible = savedInstanceState.getBoolean("addVisible");
        }



        //Comentado ya que era un antiguo botón de Refresh
        /*ImageButton iButton = findViewById(R.id.iButton);

        iButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tvQuotation = findViewById(R.id.tvHello);
                TextView tvAbajo = findViewById(R.id.tvAbajo);

                tvQuotation.setText(getString(R.string.tvSample));
                tvAbajo.setText(getString(R.string.tvSampleAuthor));
            }
        });*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_quotation_activity, menu);
        this.menu = menu;
        menu.findItem(R.id.citaFav).setVisible(addVisible);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.nuevaCita){
            tvQuotation = findViewById(R.id.tvHello);
            tvAbajo = findViewById(R.id.tvAbajo);
            tvQuotation.setText(getString(R.string.tvSample, nCitas));
            tvAbajo.setText(getString(R.string.tvSampleAuthor, nCitas));
            nCitas++;
            addVisible = true;
            menu.findItem(R.id.citaFav).setVisible(addVisible);
            return true;
        }
        if(item.getItemId() == R.id.citaFav){
            //Se completará esto durante la Práctica 3
            addVisible = false;
            menu.findItem(R.id.citaFav).setVisible(addVisible);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tvQuotation", (String) tvQuotation.getText());
        outState.putString("tvAbajo", (String) tvAbajo.getText());
        outState.putBoolean("addVisible", addVisible);
        outState.putInt("nCitas", nCitas);
    }
}