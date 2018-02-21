package ge.apex.nika.library1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import ge.apex.nika.library1.Data.Author;
import ge.apex.nika.library1.Data.Book;
import ge.apex.nika.library1.Data.Genre;

public class GenreDetailActivity extends AppCompatActivity {

    protected final String LOG_TAG = "GenreDetailActivity";

    // Reference of DatabaseHelper class to access its DAOs and other components pushing a
    protected DatabaseHelper databaseHelper = null;

    // Declaration of DAO to interact with corresponding Genre table
    protected Dao<Genre,Integer> genreDao;
    List<Genre> genreList = null;

    TextView textView;
    Button buttonAdd;
    EditText editGenreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_genre);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        buttonAdd = (Button) findViewById(R.id.genreAddButton);
        editGenreText = (EditText) findViewById(R.id.genreEditText);
        textView= (TextView) findViewById(R.id.genre_add_textview);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            int counter = 0;
            @Override
            public void onClick(View view) {
                String message = editGenreText.getText().toString();
                doGenreDatabaseStuff(textView, message);
                counter++;
                if(counter == 3) {
                    finish();
                }
                //finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
         /*
         *  You'll need this in your class to release the helper when done.
		 */
        if(databaseHelper != null ) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    /**
     * getDatabaseHelper returns instance of DatabaseHelper class
     */
    public DatabaseHelper getDatabaseHelper() {
        if(databaseHelper == null ) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }




    public void doGenreDatabaseStuff(TextView tv, String genreName) {
        try {

            genreDao = getDatabaseHelper().getGenreDao();

            genreList = genreDao.queryForAll();
            Log.d(LOG_TAG, "WE got genreDao");

            StringBuilder sb = new StringBuilder();


            sb.append("+++++++++++++++++++++++++++++++\n\n");
            for(Genre tempGenre : genreList) {
                if(genreName.equals(tempGenre.getName())){
                    sb.append("\n\n Genre with that name already exists\n\n");
                }
                sb.append(tempGenre.toString() + " \n\n");
                Log.d(LOG_TAG, "genre name is: " + tempGenre.getName());

            }

            sb.append("+++++++++++++++++++++++++++++++ \n\n");

            if(genreName.equals("awe")) {
                Genre genre = new Genre(genreName);
                genreDao.create(genre);
            }

            tv.setText(sb.toString());



        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.i(LOG_TAG, "Done with AuthorDetailActivity at " + System.currentTimeMillis());
    }

}
