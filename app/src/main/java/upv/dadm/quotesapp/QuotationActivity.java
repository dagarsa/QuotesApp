package upv.dadm.quotesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import POJO.Quotation;
import databases.AbstractQuotation;
import intermediario.IntermediarioVistaDatos;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import threads.BackgroundThreadFavourite;
import threads.BackgroundThreadQuotation;
import webService.WebService;

public class QuotationActivity extends AppCompatActivity {

    //private int nCitas = 0;

    private TextView tvQuotation;
    private TextView tvAbajo;

    private boolean addVisible;

    Retrofit retrofit;
    WebService webService;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);

        tvQuotation = findViewById(R.id.tvHello);
        tvAbajo = findViewById(R.id.tvAbajo);

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
            //nCitas = savedInstanceState.getInt("nCitas");
            tvQuotation.setText(savedInstanceState.getString("tvQuotation"));
            tvAbajo.setText(savedInstanceState.getString("tvAbajo"));
            addVisible = savedInstanceState.getBoolean("addVisible");
        }

        retrofit = new Retrofit.Builder().baseUrl("https://api.forismatic.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        webService = retrofit.create(WebService.class);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_quotation_activity, menu);
        menu.findItem(R.id.citaFav).setVisible(addVisible);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.nuevaCita){
            /*tvQuotation = findViewById(R.id.tvHello);
            tvAbajo = findViewById(R.id.tvAbajo);
            tvQuotation.setText(getString(R.string.tvSample, nCitas));
            tvAbajo.setText(getString(R.string.tvSampleAuthor, nCitas));
            nCitas++;

            //Llamar mi hilo que actualizará interfaz
            new BackgroundThreadQuotation(this).start();*/

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String http = sharedPreferences.getString("httpRequest", "");

            if(hasInternet()){

                if(http.equals("GET")){
                    ocultarActionBarVisibleProgressBar();
                    Call<Quotation> call = webService.getQuotationWeb("en");
                    call.enqueue(new Callback<Quotation>() {
                        @Override
                        public void onResponse(@NonNull Call<Quotation> call, @NonNull Response<Quotation> response) {
                            Log.d("mensaje", "Ha acertado - " + response.body());
                            nuevaCitaWeb(response.body());
                        }

                        @Override
                        public void onFailure(@NonNull Call<Quotation> call, @NonNull Throwable t) {
                            Log.d("mensaje", "Ha fallado");
                            nuevaCitaWeb(null);
                        }
                    });
                }
                if(http.equals("POST")){
                    ocultarActionBarVisibleProgressBar();
                    Call<Quotation> call = webService.postQuotation("getQuote", "json", "en");
                    call.enqueue(new Callback<Quotation>() {
                        @Override
                        public void onResponse(Call<Quotation> call, Response<Quotation> response) {
                            nuevaCitaWeb(response.body());
                        }

                        @Override
                        public void onFailure(Call<Quotation> call, Throwable t) {
                            Toast.makeText(QuotationActivity.this, "Fallo en post", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }else {
                Toast.makeText(this, getString(R.string.sinConexion), Toast.LENGTH_SHORT).show();
            }

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
        //outState.putInt("nCitas", nCitas);
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

    public boolean hasInternet(){
        boolean result = false;
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > 22) {
            final Network activeNetwork = manager.getActiveNetwork();
            if (activeNetwork != null) {
                final NetworkCapabilities networkCapabilities = manager.getNetworkCapabilities(activeNetwork);
                result = networkCapabilities != null && (
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
            }
        } else {
            NetworkInfo info = manager.getActiveNetworkInfo();
            result = ((info != null) && (info.isConnected()));
        }
        return result;
    }

    public void ocultarActionBarVisibleProgressBar(){
        menu.findItem(R.id.nuevaCita).setVisible(false);
        menu.findItem(R.id.citaFav).setVisible(false);
        this.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
    }

    public void nuevaCitaWeb(Quotation quotation){
        if(quotation == null){
            Toast.makeText(this, getString(R.string.mensajeCitaNula), Toast.LENGTH_SHORT).show();
        }else{
            tvQuotation.setText(quotation.getQuoteText());
            tvAbajo.setText(quotation.getQuoteAuthor());
            this.findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
            new BackgroundThreadQuotation(this).start();
        }

    }
}