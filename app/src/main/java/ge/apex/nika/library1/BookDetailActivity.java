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
import ge.apex.nika.library1.Data.Book;
import ge.apex.nika.library1.Data.Genre;


public class BookDetailActivity extends AppCompatActivity {

    protected final String LOG_TAG = "BookDetailActivity";

    static final int CHOOSE_AUTHOR_REQUEST = 1;
    static final int CHOOSE_GENRE_REQUEST = 2;

    // Reference of DatabaseHelper class to access its DAOs and other components pushing a
    protected DatabaseHelper databaseHelper = null;

    // Declaration of DAO to interact with corresponding Book table
    protected Dao<Book, Integer> bookDao;
    List<Book> bookList = null;

    protected Dao<Author, Integer> authorDao;
    List<Author> authorList = null;

    protected Dao<Genre, Integer> genreDao;
    List<Genre> genreList = null;

    Button buttonAddBook;
    Button buttonChooseGenre;
    Button buttonChooseAuthor;
    EditText editBookTitleText;
    EditText editBookLanguageText;
    EditText editBookDatePublishedText;

    int mGenreId;
    int mAuthorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buttonAddBook = findViewById(R.id.bookAddButton);
        buttonChooseAuthor = findViewById(R.id.buttonChooseAuthor);
        buttonChooseGenre = findViewById(R.id.buttonChooseGenre);
        editBookTitleText = findViewById(R.id.bookTitleEditText);
        editBookLanguageText = findViewById(R.id.bookLanguageEditText);
        editBookDatePublishedText = findViewById(R.id.bookDatePublishedEditText);

        // Get the Intent that started this activity and get the book id;
        Intent intent = getIntent();
        final int localBookId = intent.getIntExtra(LibraryActivity.EXTRA_MESSAGE_ID, 0);
        // write text in edit fields using id passed through intent
        displayBookInfo(localBookId);

        buttonAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (localBookId == 0) {
                    Log.d(LOG_TAG, "Book should be created");
                    createBookInDatabase();
                } else {
                    Log.d(LOG_TAG, "Book was selected so it should be updated");
                    updateBookInDatabase(localBookId);
                }
                finish();

            }
        });

        buttonChooseGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Choose Genre Button was clicked, activity should start that displays the fragment with recycler view");
                Intent intent = new Intent(BookDetailActivity.this, ChooseGenreActivity.class);
                startActivityForResult(intent, CHOOSE_GENRE_REQUEST);
            }
        });

        buttonChooseAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Choose Author Button was clicked, activity should start that displays the fragment with recycler view");
                Intent intent = new Intent(BookDetailActivity.this, ChooseAuthorActivity.class);
                startActivityForResult(intent, CHOOSE_AUTHOR_REQUEST);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == CHOOSE_GENRE_REQUEST) {
            if (data == null) {
                return;
            }
            mGenreId = data.getIntExtra(ChooseGenreActivity.EXTRA_GENRE_ACTIVITY_GENRE_ID, 0);
            buttonChooseGenre.setText(String.valueOf(mGenreId));
            buttonGenreSetText(buttonChooseGenre, mGenreId);
        }

        if (requestCode == CHOOSE_AUTHOR_REQUEST) {
            if (data == null) {
                return;
            }
            mAuthorId = data.getIntExtra(ChooseAuthorActivity.EXTRA_AUTHOR_ACTIVITY_AUTHOR_ID, 0);
            buttonAuthorSetText(buttonChooseAuthor, mAuthorId);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
         /*
         *  You'll need this in your class to release the helper when done.
		 */
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    /**
     * getDatabaseHelper returns instance of DatabaseHelper class
     */
    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    private void displayBookInfo(int bookId) {
        if (bookId == 0) return;
        try {
            bookDao = getDatabaseHelper().getBookDao();
            Log.d(LOG_TAG, "We got bookDao");
            Book localTempBook = bookDao.queryForId(bookId);

            editBookTitleText.setText(localTempBook.getTitle());
            editBookLanguageText.setText(localTempBook.getLang());
            editBookDatePublishedText.setText(String.valueOf(localTempBook.getDate()));
            buttonChooseGenre.setText(localTempBook.getGenreId().getName());
            buttonChooseAuthor.setText(localTempBook.getAuthorId().getFName() + " " + localTempBook.getAuthorId().getLName());


        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.i(LOG_TAG, "Done with displayBookInfo " + System.currentTimeMillis());
    }

    private void updateBookInDatabase(int bookID) {
        try {
            bookDao = getDatabaseHelper().getBookDao();
            Book book = bookDao.queryForId(bookID);
            String title = editBookTitleText.getText().toString();
            book.setTitle(title);
            String language = editBookLanguageText.getText().toString();
            book.setLang(language);
            int datePublished = Integer.parseInt(editBookDatePublishedText.getText().toString());
            book.setDate(datePublished);

            // updating author and genre involves DAOs so it is a bit different.
            if (mAuthorId != 0) {
                authorDao = getDatabaseHelper().getAuthorDao();
                book.setAuthorId(authorDao.queryForId(mAuthorId));
            }
            if (mGenreId != 0) {
                genreDao = getDatabaseHelper().getGenreDao();
                book.setGenreId(genreDao.queryForId(mGenreId));
            }

            bookDao.update(book);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void createBookInDatabase() {
        try {
            bookDao = getDatabaseHelper().getBookDao();
            Log.d(LOG_TAG, "We got bookDao");
            authorDao = getDatabaseHelper().getAuthorDao();
            genreDao = getDatabaseHelper().getGenreDao();
            authorList = authorDao.queryForAll();
            genreList = genreDao.queryForAll();

            Log.d(LOG_TAG, "The Author id of chosen author is: " + mAuthorId);
            Author localAuthor = authorDao.queryForId(mAuthorId);
            Log.d(LOG_TAG, "The Genre id of chosen genre is: " + mGenreId);
            Genre localGenre = genreDao.queryForId(mGenreId);
            String title = editBookTitleText.getText().toString();
            String language = editBookLanguageText.getText().toString();
            int datePublished;
            if (editBookDatePublishedText.getText().toString().equals("") || editBookDatePublishedText.getText().toString() == null) {
                datePublished = 0;
            } else {
                datePublished = Integer.parseInt(editBookDatePublishedText.getText().toString());
            }

            if (localAuthor == null || localGenre == null || title.equals("") || language.equals("")) {
                return;
            }

            Book localTempBook = new Book(title, localAuthor, localGenre, datePublished, language);
            Log.d(LOG_TAG, "The chosen genre is: " + localTempBook.getGenreId().getName());
            Log.d(LOG_TAG, "The chosen author is: " + localTempBook.getAuthorId().getFName() + " " + localTempBook.getAuthorId().getLName());

            bookDao.create(localTempBook);
            bookList = bookDao.queryForAll();
            Log.d(LOG_TAG, "The book list size is: " + bookList.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.i(LOG_TAG, "Done with createBook " + System.currentTimeMillis());

    }


    private void buttonGenreSetText(Button button, int id) {
        if (id == 0) {
            button.setText(R.string.no_item_with_id_was_found);
        } else {
            try {
                genreDao = getDatabaseHelper().getGenreDao();
                button.setText(genreDao.queryForId(id).getName());

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void buttonAuthorSetText(Button button, int id) {
        if (id == 0) {
            button.setText(R.string.no_item_with_id_was_found);
        } else {
            try {
                authorDao = getDatabaseHelper().getAuthorDao();
                button.setText(authorDao.queryForId(id).getFName() + " " + authorDao.queryForId(id).getLName());

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
