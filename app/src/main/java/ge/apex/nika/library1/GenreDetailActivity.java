package ge.apex.nika.library1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import ge.apex.nika.library1.Data.Genre;

public class GenreDetailActivity extends AppCompatActivity {

    protected final String LOG_TAG = "GenreDetailActivity";

    // Reference of DatabaseHelper class to access its DAOs and other components.
    protected DatabaseHelper databaseHelper = null;

    // Declaration of DAO to interact with corresponding Genre table
    protected Dao<Genre,Integer> genreDao;
    List<Genre> genreList = null;

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


        /*
        * Getting intent to retrieve arguments passed by choosing RecyclerView item.
         */
        Intent localIntent = getIntent();
        final int localGenreId = localIntent.getIntExtra(LibraryActivity.EXTRA_MESSAGE_ID, 0);
        // write text in edit fields using id passed through intent
        displayGenreInfo(localGenreId);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (localGenreId == 0) {
                    Log.d(LOG_TAG, "Genre should be created");
                    createGenreInDatabase();
                }
                else {
                    Log.d(LOG_TAG, "Genre was selected so it should be updated");
                    updateGenreInDatabase(localGenreId);
                }
                finish();
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


    private void displayGenreInfo(int genreId) {
        if(genreId == 0) return;
        try{
            genreDao = getDatabaseHelper().getGenreDao();
            Log.d(LOG_TAG, "WE got genreDAO");
            Genre genre = genreDao.queryForId(genreId);
            editGenreText.setText(genre.getName());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateGenreInDatabase(int genreId) {
        try{
            genreDao = getDatabaseHelper().getGenreDao();
            Genre genre = genreDao.queryForId(genreId);
            String genreName = editGenreText.getText().toString();
            genre.setName(genreName);
            genreDao.update(genre);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void createGenreInDatabase() {
        try {

            genreDao = getDatabaseHelper().getGenreDao();
            genreList = genreDao.queryForAll();
            Log.d(LOG_TAG, "WE got genreDao");
            String genreName = editGenreText.getText().toString();
            Genre genre = new Genre(genreName);
            genreDao.create(genre);
            genreList = genreDao.queryForAll();
            Log.d(LOG_TAG, "The genre list size is: " + genreList.size());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.i(LOG_TAG, "Done with GenreDetailActivity at " + System.currentTimeMillis());
    }

}
