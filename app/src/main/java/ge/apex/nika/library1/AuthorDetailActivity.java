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

import ge.apex.nika.library1.Data.Author;


/**
 * Created by Nika on 20/02/2018.
 * This displays detail view of author
 */

public class AuthorDetailActivity extends AppCompatActivity{

    protected final String LOG_TAG = "AuthorDetailActivity";

    // Reference of DatabaseHelper class to access its DAOs and other components
    protected DatabaseHelper databaseHelper = null;

    // Declaration of DAO to interact with corresponding Author table
    protected Dao<Author, Integer> authorDao;
    List<Author> authorList = null;

    Button buttonAddAuthor;
    EditText editFirstNameText;
    EditText editLastNameText;
    EditText editDateBornText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_author);
        Toolbar toolbar = findViewById(R.id.author_detail_toolbar);
        setSupportActionBar(toolbar);
        buttonAddAuthor = findViewById(R.id.authorAddButton);
        editFirstNameText = findViewById(R.id.authorFirstNameEditText);
        editLastNameText = findViewById(R.id.authorLastNameEditText);
        editDateBornText = findViewById(R.id.authorDateBornEditText);

        // Get the Intent that started this activity and extract the author ID
        Intent intent = getIntent();
        final int localAuthorId = intent.getIntExtra(LibraryActivity.EXTRA_MESSAGE_ID, 0);
        // write text in edit fields using id passed through intent
        displayAuthorInfo(localAuthorId);

        buttonAddAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(localAuthorId == 0) {
                    Log.d(LOG_TAG, "Author should be created");
                    createAuthorInDatabase();
                }
                else{
                    Log.d(LOG_TAG, "Author was selected so it should be updated");
                    updateAuthorInDatabase(localAuthorId);
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



    public  void displayAuthorInfo (int authorID) {
        if(authorID == 0) return;
        try {
            authorDao = getDatabaseHelper().getAuthorDao();
           Log.d(LOG_TAG, "WE got authorDAO");

           Author localTempAuthor=authorDao.queryForId(authorID);

           editFirstNameText.setText(localTempAuthor.getFName());
           editLastNameText.setText(localTempAuthor.getLName());
           editDateBornText.setText(String.valueOf(localTempAuthor.getDateBorn()));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.i(LOG_TAG, "Done with displayAuthorInfo " + System.currentTimeMillis());
    }

    public void updateAuthorInDatabase(int authorID) {
        try{
            authorDao = getDatabaseHelper().getAuthorDao();
            Author author = authorDao.queryForId(authorID);
            String firstName = editFirstNameText.getText().toString();
            author.setFName(firstName);
            String lastName = editLastNameText.getText().toString();
            author.setLName(lastName);
            int dateBorn = Integer.parseInt(editDateBornText.getText().toString());
            author.setDateBorn(dateBorn);
            authorDao.update(author);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createAuthorInDatabase() {
        try {
            authorDao = getDatabaseHelper().getAuthorDao();
            Log.d(LOG_TAG, "WE got authorDAO");

            String fName = editFirstNameText.getText().toString();
            String lName = editLastNameText.getText().toString();
            int dBorn = Integer.parseInt(editDateBornText.getText().toString());

            Author localTempAuthor = new Author(fName, lName, dBorn);
            authorDao.create(localTempAuthor);
            authorList = authorDao.queryForAll();
            Log.d(LOG_TAG, "The author list size is: " + authorList.size());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.i(LOG_TAG, "Done with createAuthor " + System.currentTimeMillis());
    }

}
