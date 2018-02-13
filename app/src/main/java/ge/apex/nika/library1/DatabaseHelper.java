package ge.apex.nika.library1;

/**
 * Created by NATIA on 13/02/2018.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

import ge.apex.nika.library1.Data.Author;
import ge.apex.nika.library1.Data.Book;
import ge.apex.nika.library1.Data.Genre;
import ge.apex.nika.library1.Data.IssuedBook;

/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "Library1.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    // the DAO object we use to access the various data tables
    private Dao<Author, Integer> authorDao = null;
    private Dao<Book, Integer> bookDao = null;
    private Dao<Genre, Integer> genreDao = null;
    private Dao<IssuedBook, Integer> issuedBookDao = null;


    /* DatabaseHelper public constructor.
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }
}
