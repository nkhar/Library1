package ge.apex.nika.library1;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import ge.apex.nika.library1.Fragments.AuthorFragment;
import ge.apex.nika.library1.Fragments.BookFragment;
import ge.apex.nika.library1.Fragments.GenreFragment;


public class LibraryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    protected final String LOG_TAG = "LibraryActivity";

    public static final String EXTRA_MESSAGE_ID = "ge.apex.nika.library1.MESSAGE.ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "We are in onCreate method");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // get support fragment manager.
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_authors) {
            //doSampleAuthorStuff("AuthorStuff",mTextView);

            // Insert the fragment by replacing FrameLayout.
            AuthorFragment authorFragment = new AuthorFragment();
            fragmentManager.beginTransaction().replace(R.id.flContent, authorFragment, "AUTHOR_TAG").commit();

        } else if (id == R.id.nav_books) {
            // Insert the fragment by replacing FrameLayout.
            BookFragment bookFragment = new BookFragment();
            fragmentManager.beginTransaction().replace(R.id.flContent, bookFragment, "BOOK_TAG").commit();

        } else if (id == R.id.nav_genres) {
            // Insert the fragment by replacing FrameLayout.
            GenreFragment genreFragment = new GenreFragment();
            fragmentManager.beginTransaction().replace(R.id.flContent, genreFragment, "GENRE_TAG").commit();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        setTitle(item.getTitle());
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
