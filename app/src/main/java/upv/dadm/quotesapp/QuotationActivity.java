package upv.dadm.quotesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class QuotationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);

        TextView tvName = findViewById(R.id.tvHello);
        String name = " Nameless One";
        String tvGetHello = getString(R.string.tvGetHello, name);
        tvName.setText(tvGetHello);

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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.nuevaCita){
            TextView tvQuotation = findViewById(R.id.tvHello);
            TextView tvAbajo = findViewById(R.id.tvAbajo);

            tvQuotation.setText(getString(R.string.tvSample));
            tvAbajo.setText(getString(R.string.tvSampleAuthor));
            return true;
        }
        if(item.getItemId() == R.id.citaFav){
            //Se completará esto durante la Práctica 3
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}