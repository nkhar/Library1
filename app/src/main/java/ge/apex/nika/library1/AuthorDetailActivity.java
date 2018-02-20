package ge.apex.nika.library1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by NATIA on 20/02/2018.
 */

public class AuthorDetailActivity extends AppCompatActivity{

    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_author);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(LibraryActivity.EXTRA_MESSAGE);
        TextView textView = (TextView) findViewById(R.id.author_detail_textview);
        textView.setText(message);
    }

}
