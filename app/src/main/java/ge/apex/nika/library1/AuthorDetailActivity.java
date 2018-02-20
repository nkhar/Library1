package ge.apex.nika.library1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_author);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(LibraryActivity.EXTRA_MESSAGE);
        TextView textView = (TextView) findViewById(R.id.author_detail_textview);
        textView.setText(message);
        doAllDaoStuff(textView);
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

    public void doAllDaoStuff(TextView tv) {
        try {

            authorDao = getDatabaseHelper().getAuthorDao();
            //List<SimpleData>
            authorList = authorDao.queryForAll();
            Log.d(LOG_TAG, "WE got authorDAO");

            genreDao = getDatabaseHelper().getGenreDao();
            //List<SimpleData>
            genreList = genreDao.queryForAll();
            Log.d(LOG_TAG, "WE got genreDao");

            bookDao = getDatabaseHelper().getBookDao();
            //List<SimpleData>
            bookList = bookDao.queryForAll();
            Log.d(LOG_TAG, "WE got bookDao");

            // if we already have items in the database
          /*  int authorC = 1;
            for (Author author : authorList) {
                authorDao.delete(author);
                Log.i(LOG_TAG, "deleting author(" + author.getId() + ")");
                authorC++;
            }
            */

            StringBuilder sb = new StringBuilder();

           /*
            Genre genre = new Genre("Adventure");
            // store it in the database
            genreDao.create(genre);
            sb.append(" " + genre.toString() + " \n");

            Author  author = authorDao.queryForId(1);

            Book book = new Book("White Fang", author, genre, 1906, "ENG");
            bookDao.create(book);
            book = new Book("The call of the wild", author, genre, 1903, "ENG");
            bookDao.create(book);









            genre = new Genre("Detective");
            // store it in the database
            genreDao.create(genre);
            sb.append(" " + genre.toString() + " \n");

            genre = new Genre("Mystery");
            // store it in the database
            genreDao.create(genre);
            sb.append(" " + genre.toString() + " \n");

            author = authorDao.queryForId(2);

            book = new Book("Adventures of Huckleberry Finn", author, genre, 1884, "RUS");
            bookDao.create(book);

            genre = new Genre("Novel");
            // store it in the database
            genreDao.create(genre);
            sb.append(" " + genre.toString() + " \n");

            author = authorDao.queryForId(3);

            book = new Book("Great Expectations", author, genre, 1884, "RUS");
            bookDao.create(book);

            genre = new Genre("Horror");
            // store it in the database
            genreDao.create(genre);
            sb.append(" " + genre.toString() + " \n");



            */
            sb.append("+++++++++++++++++++++++++++++++");
            for(Book tempBook : bookList) {
                sb.append(tempBook.toString() + " \n\n");
            }
            sb.append("+++++++++++++++++++++++++++++++ \n\n");

            Author author = authorDao.queryForId(1);
            sb.append(author);

            tv.setText(sb.toString());



        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.i(LOG_TAG, "Done with AuthorDetailActivity at " + System.currentTimeMillis());
    }
}
