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

import ge.apex.nika.library1.Data.Author;
import ge.apex.nika.library1.Data.Book;
import ge.apex.nika.library1.Data.Genre;

/**
 * Created by NATIA on 20/02/2018.
 */

public class AuthorDetailActivity extends AppCompatActivity{

    protected final String LOG_TAG = "AuthorDetailActivity";

    // Reference of DatabaseHelper class to access its DAOs and other components pushing a
    protected DatabaseHelper databaseHelper = null;

    // Declaration of DAO to interact with corresponding Author table
    protected Dao<Author, Integer> authorDao;
    List<Author> authorList = null;
    // Declaration of DAO to interact with corresponding Book table
    protected Dao<Book,Integer> bookDao;
    List<Book> bookList = null;
    // Declaration of DAO to interact with corresponding Genre table
    protected Dao<Genre,Integer> genreDao;
    List<Genre> genreList = null;

    TextView textView;
    Button buttonAddAuthor;
    EditText editFirstNameText;
    EditText editLastNameText;
    EditText editDateBornText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_author);
        Toolbar toolbar = (Toolbar) findViewById(R.id.author_detail_toolbar);
        setSupportActionBar(toolbar);
        buttonAddAuthor = (Button) findViewById(R.id.authorAddButton);
        editFirstNameText = (EditText) findViewById(R.id.authorFirstNameEditText);
        editLastNameText = (EditText) findViewById(R.id.authorLastNameEditText);
        editDateBornText = (EditText) findViewById(R.id.authorDateBornEditText);
        textView= (TextView) findViewById(R.id.author_detail_textview);

        // Get the Intent that started this activity and extract the author ID
        Intent intent = getIntent();
        int messageID = intent.getIntExtra(LibraryActivity.EXTRA_MESSAGE, 0);
        TextView textView = (TextView) findViewById(R.id.author_detail_textview);
        displayAuthorInfo(messageID, textView);

       // doAllDaoStuff(textView);

        buttonAddAuthor.setOnClickListener(new View.OnClickListener() {
            int counter = 0;
            @Override
            public void onClick(View view) {
                addAuthor();
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



    public  void displayAuthorInfo (int authorID, TextView tv) {
        try {
            authorDao = getDatabaseHelper().getAuthorDao();
           Log.d(LOG_TAG, "WE got authorDAO");
           Author localTempAuthor=authorDao.queryForId(authorID);
           tv.setText(localTempAuthor.getId() + "");
           editFirstNameText.setText(localTempAuthor.getFName());
           editLastNameText.setText(localTempAuthor.getLName());
           editDateBornText.setText(localTempAuthor.getDateBorn() + "");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.i(LOG_TAG, "Done with displayAuthorInfo " + System.currentTimeMillis());
    }

    public void addAuthor() {
        try {
            authorDao = getDatabaseHelper().getAuthorDao();
            Log.d(LOG_TAG, "WE got authorDAO");

            String fName = editFirstNameText.getText().toString();
            String lName = editLastNameText.getText().toString();
            int dBorn = Integer.parseInt(editDateBornText.getText().toString());
            if(fName.equals("Oscar") && lName.equals("Wilde") && dBorn == 1854) {
                Author localTempAuthor = new Author(fName, lName, dBorn);
                authorDao.create(localTempAuthor);
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.i(LOG_TAG, "Done with addAuthor " + System.currentTimeMillis());
    }

}
