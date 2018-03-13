package ge.apex.nika.library1;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


import ge.apex.nika.library1.Fragments.ChooseGenreFragment;


/**
 * Created by Nika on 12/03/2018.
 * This activity is opened from BookDetailActivity class to help in choice of GEnres.
 */

public class ChooseGenreActivity extends AppCompatActivity {

    protected final String LOG_TAG = "ChooseGenreActivity";

    public static final String EXTRA_GENRE_ACTIVITY_GENRE_ID = "ge.apex.nika.library1.genre_id";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_genre);
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Insert the fragment by replacing FrameLayout.
        ChooseGenreFragment genreFragment = new ChooseGenreFragment();
        fragmentManager.beginTransaction().replace(R.id.choose_genre_container, genreFragment, "CHOOSE_GENRE_TAG").commit();

    }

}
