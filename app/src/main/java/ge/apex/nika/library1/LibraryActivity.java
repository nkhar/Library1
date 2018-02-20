package ge.apex.nika.library1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import ge.apex.nika.library1.Data.Author;
import ge.apex.nika.library1.Fragments.AuthorFragment;


public class LibraryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AuthorFragment.OnListFragmentInteractionListener{

    // Widgets
   // TextView mTextView;

    protected final String LOG_TAG = "LibraryActivity";
    protected final static int MAX_NUM_TO_CREATE = 8;

    public static final String EXTRA_MESSAGE = "ge.apex.nika.library1.MESSAGE";

    // Reference of DatabaseHelper class to access its DAOs and other components pushing a
    protected DatabaseHelper databaseHelper = null;

    // Declaration of DAO to interact with corresponding Author table
   // protected Dao<Author, Integer> authorDao;
   // List<Author> authorList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // my code
       // mTextView = (TextView) findViewById(R.id.textview_display_database_tables);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.library, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // get support fragment manager.
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_authors) {
            //doSampleAuthorStuff("AuthorStuff",mTextView);

            // Insert the fragment by replacing FrameLayout.
            AuthorFragment authorFragment = new AuthorFragment();
            fragmentManager.beginTransaction().replace(R.id.flContent, authorFragment, "AUTHORTAG").commit();

        } else if (id == R.id.nav_books) {
            // Insert the fragment by replacing FrameLayout.
           // BookFragment bookFragment = new BookFragment();
           // fragmentManager.beginTransaction().replace(R.id.flContent, bookFragment, "BOOKTAG").commit();

        } else if (id == R.id.nav_genres) {
            // Insert the fragment by replacing FrameLayout.
           // GenreFragment genreFragment = new GenreFragment();
           // fragmentManager.beginTransaction().replace(R.id.flContent, genreFragment, "GENRETAG").commit();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        setTitle(item.getTitle());
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    /**
     * Activity lifecycle method called when it is destroyed.
     */
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
     * Do sample author stuff
     */
  /*  protected void doSampleAuthorStuff(String action, TextView tv) {

        try {

            authorDao = getDatabaseHelper().getAuthorDao();
            //List<SimpleData>
            authorList = authorDao.queryForAll();
            Log.d(LOG_TAG, "WE got authorDAO");

            // our string builder for building the content-view
            StringBuilder sb = new StringBuilder();
            sb.append("Found ").append(authorList.size()).append(" entries in DB in ").append(action).append("()\n");

            // if we already have items in the database
            int authorC = 1;
            for (Author author : authorList) {
                sb.append('#').append(authorC).append(": ").append(author).append('\n');
                authorC++;
            }
            sb.append("------------------------------------------\n");
            sb.append("Deleted ids:");

            for (Author author : authorList) {
                authorDao.delete(author);
                sb.append(' ').append(author.getId());
                Log.i(LOG_TAG, "deleting author(" + author.getId() + ")");
                authorC++;
            }
            sb.append('\n');
            sb.append("------------------------------------------\n");

            int createNum;
            do {
                createNum = new Random().nextInt(MAX_NUM_TO_CREATE) + 1;
            } while (createNum == authorList.size());
            sb.append("Creating ").append(createNum).append(" new entries:\n");
            for (int i = 0; i < createNum; i++) {
                // create a new simple object
                long millis = System.currentTimeMillis();
                // SimpleData simple = new SimpleData(millis);
                Author author = new Author("Jack", "London", 1876);
                // store it in the database
                authorDao.create(author);

                Log.i(LOG_TAG, "created author(" + millis + ")");
                // output it
                sb.append('#').append(i + 1).append(": ");
                sb.append(author).append('\n');
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    // ignore
                }
            }

            Log.i(LOG_TAG, "authorDao has table " +  authorDao.getTableName());

            tv.setText(sb.toString());
            Log.i(LOG_TAG, "Done with authors at " + System.currentTimeMillis());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    */

    @Override
    public void onListFragmentInteraction(Author author) {

        Intent intent = new Intent(this, AuthorDetailActivity.class);
        String message = author.getFName();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
