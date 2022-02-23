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

import java.util.List;

import POJO.Quotation;
import databases.AbstractQuotation;
import intermediario.IntermediarioVistaDatos;
import threads.BackgroundThreadFavourite;
import threads.BackgroundThreadQuotation;

public class QuotationActivity extends AppCompatActivity {

    private int nCitas = 0;

    private TextView tvQuotation;
    private TextView tvAbajo;

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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_quotation_activity, menu);
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

            //Llamar mi hilo que actualizará interfaz
            new BackgroundThreadQuotation(this).start();

            return true;
        }
        if(item.getItemId() == R.id.citaFav){
            addVisible = false;

            Quotation quotation = new Quotation(tvQuotation.getText().toString(), tvAbajo.getText().toString());
            new Thread(new Runnable() {
                @Override
                public void run() {
                // Include here the code to access the database
                    AbstractQuotation.getInstace(QuotationActivity.this).getQuotationDao().addQuote(quotation);
                }
            }).start();

            //Llama otra vez a onCreateOptionsMenu
            invalidateOptionsMenu();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tvQuotation", tvQuotation.getText().toString());
        outState.putString("tvAbajo", tvAbajo.getText().toString());
        outState.putBoolean("addVisible", addVisible);
        outState.putInt("nCitas", nCitas);
    }

    public void callAdapterMethod(Quotation quotation){
        //Comprobar si hay la cita es favorita o no
        addVisible = quotation == null;

        //Llama otra vez a onCreateOptionsMenu
        invalidateOptionsMenu();
    }

    public TextView getTvQuotation() {
        return tvQuotation;
    }
}