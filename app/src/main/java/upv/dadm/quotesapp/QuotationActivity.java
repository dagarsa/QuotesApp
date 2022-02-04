package upv.dadm.quotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.ExtractedText;
import android.widget.ImageButton;
import android.widget.TextView;

public class QuotationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);

        TextView tvName = findViewById(R.id.tvHello);
        tvName.setText("Hello Nameless One\nPress Refresh to get a new quotation");
        ImageButton iButton = findViewById(R.id.iButton);

        iButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tvQuotation = findViewById(R.id.tvHello);
                TextView tvAbajo = findViewById(R.id.tvAbajo);

                tvQuotation.setText(getString(R.string.tvSample));
                tvAbajo.setText(getString(R.string.tvSampleAuthor));
            }
        });

    }
}