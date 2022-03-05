package fragments;

import static android.content.Context.CONNECTIVITY_SERVICE;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import POJO.Quotation;
import databases.AbstractQuotation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import threads.BackgroundThreadQuotation;
import upv.dadm.quotesapp.R;
import webService.WebService;

public class QuotationFragment extends Fragment {

    private TextView tvQuotation;
    private TextView tvAbajo;

    private boolean addVisible;

    Retrofit retrofit;
    WebService webService;
    Menu menu;
    View view;

    public QuotationFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_quotation, null);
        this.view = view;
        tvQuotation = view.findViewById(R.id.tvHello);
        tvAbajo = view.findViewById(R.id.tvAbajo);

        FloatingActionButton floatingActionButton = view.findViewById(R.id.fabButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVisible = false;

                Quotation quotation = new Quotation(tvQuotation.getText().toString(), tvAbajo.getText().toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Include here the code to access the database
                        AbstractQuotation.getInstace(requireContext()).getQuotationDao().addQuote(quotation);
                    }
                }).start();

                //Llama otra vez a onCreateOptionsMenu
                getActivity().invalidateOptionsMenu();
            }
        });

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.srlQuotation);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
                String http = sharedPreferences.getString("httpRequest", "");

                if(hasInternet()){
                    String language = sharedPreferences.getString("quotationLanguage","");
                    if(http.equals("GET")){
                        ocultarActionBarVisibleProgressBar();
                        Call<Quotation> call = webService.getQuotationWeb(language);
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
                        Call<Quotation> call = webService.postQuotation("getQuote", "json", language);
                        call.enqueue(new Callback<Quotation>() {
                            @Override
                            public void onResponse(Call<Quotation> call, Response<Quotation> response) {
                                nuevaCitaWeb(response.body());
                            }

                            @Override
                            public void onFailure(Call<Quotation> call, Throwable t) {
                                Toast.makeText(getContext(), "Fallo en post", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }else {
                    Toast.makeText(getContext(), getString(R.string.sinConexion), Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(savedInstanceState == null){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
            String name = sharedPreferences.getString("username", "");

            if (name.replace(" ","").equals("")){
                name = "Nameless One";
            }

            TextView tvName = view.findViewById(R.id.tvHello);
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
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_quotation_activity, menu);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.fabButton);
        if (addVisible) floatingActionButton.setVisibility(View.VISIBLE);
        else floatingActionButton.setVisibility(View.INVISIBLE);
        this.menu = menu;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.nuevaCita){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
            String http = sharedPreferences.getString("httpRequest", "");

            if(hasInternet()){
                String language = sharedPreferences.getString("quotationLanguage","");
                if(http.equals("GET")){
                    ocultarActionBarVisibleProgressBar();
                    Call<Quotation> call = webService.getQuotationWeb(language);
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
                    Call<Quotation> call = webService.postQuotation("getQuote", "json", language);
                    call.enqueue(new Callback<Quotation>() {
                        @Override
                        public void onResponse(Call<Quotation> call, Response<Quotation> response) {
                            nuevaCitaWeb(response.body());
                        }

                        @Override
                        public void onFailure(Call<Quotation> call, Throwable t) {
                            Toast.makeText(getContext(), "Fallo en post", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }else {
                Toast.makeText(getContext(), getString(R.string.sinConexion), Toast.LENGTH_SHORT).show();
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tvQuotation", tvQuotation.getText().toString());
        outState.putString("tvAbajo", tvAbajo.getText().toString());
        outState.putBoolean("addVisible", addVisible);
    }

    public void callAdapterMethod(Quotation quotation){
        //Comprobar si hay la cita es favorita o no
        addVisible = quotation == null;

        //Llama otra vez a onCreateOptionsMenu
        getActivity().invalidateOptionsMenu();
    }

    public TextView getTvQuotation() {
        return tvQuotation;
    }

    public boolean hasInternet(){
        boolean result = false;
        ConnectivityManager manager = (ConnectivityManager) requireContext().getSystemService(CONNECTIVITY_SERVICE);
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
        FloatingActionButton floatingActionButton = view.findViewById(R.id.fabButton);
        if (addVisible) floatingActionButton.setVisibility(View.VISIBLE);
        else floatingActionButton.setVisibility(View.INVISIBLE);
        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.srlQuotation);
        swipeRefreshLayout.setRefreshing(true);
    }

    public void nuevaCitaWeb(Quotation quotation){
        if(quotation == null){
            Toast.makeText(getContext(), getString(R.string.mensajeCitaNula), Toast.LENGTH_SHORT).show();
        }else{
            tvQuotation.setText(quotation.getQuoteText());
            tvAbajo.setText(quotation.getQuoteAuthor());
            SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.srlQuotation);
            swipeRefreshLayout.setRefreshing(false);
            new BackgroundThreadQuotation(this).start();
        }

    }
}