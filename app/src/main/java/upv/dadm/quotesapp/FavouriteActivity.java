package upv.dadm.quotesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import POJO.Quotation;
import databases.AbstractQuotation;
import intermediario.IntermediarioVistaDatos;

public class FavouriteActivity extends AppCompatActivity {

    IntermediarioVistaDatos adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        //Button bAuthor = findViewById(R.id.bAuthor);

        View.OnClickListener onClickMethod = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Botón Authot Info comentado
                /*if(bAuthor.isPressed()) {
                    String authorName = "Albert Enstein";
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://en.wikipedia.org/wiki/Special:Search?search=" + authorName));

                    List<ResolveInfo> activities =
                            getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                    // Comprobar que puede resolverse la actividad requerida
                    if (activities.size() > 0) {
                        startActivity(intent);
                    }
                }*/
            }
        };

        //bAuthor.setOnClickListener(onClickMethod);

        //Se crea solo una alerta


        RecyclerView recycler = findViewById(R.id.rview);
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 1);
        recycler.setLayoutManager(manager);
        DividerItemDecoration divider = new DividerItemDecoration(this, 1);
        recycler.addItemDecoration(divider);
        List<Quotation> data = AbstractQuotation.getInstace(this).getQuotationDao().findAllQuotes();
        adapter = new IntermediarioVistaDatos(data, new IntermediarioVistaDatos.OnItemClickListener() {
            @Override
            public void onItemClick(Quotation quotation) {
                if (quotation.getQuoteAuthor() == null || quotation.getQuoteAuthor() == "") {
                    Toast.makeText(FavouriteActivity.this, getString(R.string.toastNoCarga), Toast.LENGTH_SHORT).show();
                } else {
                    String authorName = null;
                    try {
                        authorName = URLEncoder.encode(quotation.getQuoteAuthor(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://en.wikipedia.org/wiki/Special:Search?search=" + authorName));

                    List<ResolveInfo> activities =
                            getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                    // Comprobar que puede resolverse la actividad requerida
                    if (activities.size() > 0) {
                        startActivity(intent);
                    }
                }
            }
        }, new IntermediarioVistaDatos.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(FavouriteActivity.this);
                alerta.setMessage(getString(R.string.confirmation));
                alerta.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AbstractQuotation.getInstace(FavouriteActivity.this).getQuotationDao().deleteQuote(data.get(position));
                        adapter.eliminarItem(position);
                    }
                });
                alerta.setNegativeButton(getString(R.string.no), null);
                alerta.create().show();
            }
        });
        recycler.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_favourite_activity, menu);
        if(AbstractQuotation.getInstace(this).getQuotationDao().findAllQuotes().isEmpty()){
            MenuItem item = menu.getItem(0);
            item.setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(FavouriteActivity.this);
        alerta.setMessage(getString(R.string.confirmationAll));
        alerta.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                adapter.eliminarTodo();
                AbstractQuotation.getInstace(FavouriteActivity.this).getQuotationDao().deleteAllQuotes();
                item.setVisible(false);
            }
        });
        alerta.setNegativeButton(getString(R.string.no), null);
        alerta.create().show();
        return super.onOptionsItemSelected(item);
    }



    //Mock de citas de las primera prácticas
    public List<Quotation> getMockQuotations(){
        List<Quotation> lista = new ArrayList<>();
        lista.add(new Quotation("Ganar, ganar y volver a ganar", "Luis Aragonés"));
        lista.add(new Quotation("Tocó en Hugo Duro", "Miguel Ángel Román"));
        lista.add(new Quotation("Iniesta de mi vida", "Camacho"));
        lista.add(new Quotation("Amunt Valencia!", "Dicho popular"));
        lista.add(new Quotation("Ave que vuela a la cazuela", "Dicho popular"));
        lista.add(new Quotation("Siempre negativo, nunca positivo", "Louis Van Gaal"));
        lista.add(new Quotation("Un gran poder conlleva una gran responsabilidad", "Tío de Spiderman"));
        lista.add(new Quotation("cita1", ""));
        lista.add(new Quotation("cita2", ""));
        lista.add(new Quotation("cita3", ""));

        return lista;
    }
}