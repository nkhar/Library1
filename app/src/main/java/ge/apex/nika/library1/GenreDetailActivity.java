package ge.apex.nika.library1;

import android.content.Intent;
import android.os.Bundle;
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
        Toolbar toolbar = findViewById(R.id.genre_detail_toolbar);
        setSupportActionBar(toolbar);
        buttonAdd = findViewById(R.id.genreAddButton);
        editGenreText = findViewById(R.id.genreEditText);
        textView= findViewById(R.id.genre_add_textview);


        /*
        * Getting intent to retrieve arguments passed by choosing RecyclerView item.
         */
        Intent localIntent = getIntent();
        final String localGenreName = localIntent.getStringExtra(LibraryActivity.EXTRA_MESSAGE);
        final int localGenreId = localIntent.getIntExtra(LibraryActivity.EXTRA_MESSAGE_ID, 0);
        editGenreText.setText(localGenreName);


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            int counter = 0;
            @Override
            public void onClick(View view) {
                String message = editGenreText.getText().toString();
                if (localGenreId == 0) {
                    Log.d(LOG_TAG, "Genre should be created");
                    createGenreInDatabase(textView, message);
                }
                else {
                    Log.d(LOG_TAG, "Genre was selected so it should be updated");
                    updateGenreInDatabase(localGenreId, message);
                }
                    counter++;
                    if (counter == 3) {
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


    public void updateGenreInDatabase(int genreId, String genreName) {
        try{
            genreDao = getDatabaseHelper().getGenreDao();
            Genre genre = genreDao.queryForId(genreId);
            genre.setName(genreName);
            genreDao.update(genre);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void createGenreInDatabase(TextView tv, String genreName) {
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

            if(genreName.equals("GRIMM")) {
                Genre genre = new Genre(genreName);
                genreDao.create(genre);
                genreList = genreDao.queryForAll();
            }

            Log.d(LOG_TAG, "Thr genre list size is: " + genreList.size());
            tv.setText(sb.toString());



        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.i(LOG_TAG, "Done with GenreDetailActivity at " + System.currentTimeMillis());
    }

}
