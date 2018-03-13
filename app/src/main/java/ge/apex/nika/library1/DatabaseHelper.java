package ge.apex.nika.library1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

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
    private static final int DATABASE_VERSION = 7;

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
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreateDatabaseHelper");
            TableUtils.createTable(connectionSource, Author.class);
            TableUtils.createTable(connectionSource, Book.class);
            TableUtils.createTable(connectionSource, Genre.class);
            TableUtils.createTable(connectionSource, IssuedBook.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            // throw new RuntimeException(e);
        }

        // here we try inserting data in the on-create as a test
        // RuntimeExceptionDao<SimpleData, Integer> dao = getSimpleDataDao();
        //  long millis = System.currentTimeMillis();
        // create some entries in the onCreate
        // SimpleData simple = new SimpleData(millis);
        // dao.create(simple);
        // simple = new SimpleData(millis + 1);
        //   dao.create(simple);
        //  Log.i(DatabaseHelper.class.getName(), "created new entries in onCreate: " + millis);
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Author.class, true);
            TableUtils.dropTable(connectionSource, Book.class, true);
            TableUtils.dropTable(connectionSource, Genre.class, true);
            TableUtils.dropTable(connectionSource, IssuedBook.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            // throw new RuntimeException(e);
        }
    }

    /**
     * Create the getDao methods of all database tables to access those from android code.
     * Insert, delete, read, update everything will be happening through DAOs
     */
    public Dao<Author, Integer> getAuthorDao() throws SQLException {
        if (authorDao == null) {
            authorDao = getDao(Author.class);
        }
        return authorDao;
    }

    public Dao<Book, Integer> getBookDao() throws SQLException {
        if (bookDao == null) {
            bookDao = getDao(Book.class);
        }
        return bookDao;
    }

    public Dao<Genre, Integer> getGenreDao() throws SQLException {
        if (genreDao == null) {
            genreDao = getDao(Genre.class);
        }
        return genreDao;
    }

    public Dao<IssuedBook, Integer> getIssuedBookDao() throws SQLException {
        if (issuedBookDao == null) {
            issuedBookDao = getDao(IssuedBook.class);
        }
        return issuedBookDao;
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        authorDao = null;
        bookDao = null;
    }
}
