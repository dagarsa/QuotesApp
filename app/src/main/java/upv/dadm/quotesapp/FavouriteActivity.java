package upv.dadm.quotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class FavouriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        Button bAuthor = findViewById(R.id.bAuthor);

        View.OnClickListener onClickMethod = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bAuthor.isPressed()) {
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
                }
            }
        };

        bAuthor.setOnClickListener(onClickMethod);
    }
}