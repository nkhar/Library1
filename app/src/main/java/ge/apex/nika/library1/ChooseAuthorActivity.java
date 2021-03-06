package ge.apex.nika.library1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import ge.apex.nika.library1.Fragments.ChooseAuthorFragment;

/**
 * Created by Nika on 12/03/2018.
 * This activity is opened from BookDetailActivity to show the list of authors for choice.
 */

public class ChooseAuthorActivity extends AppCompatActivity {

    protected final String LOG_TAG = "ChooseAuthorActivity";

    public static final String EXTRA_AUTHOR_ACTIVITY_AUTHOR_ID = "ge.apex.nika.library1.author_id";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_author);
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Insert the fragment by replacing FrameLayout.
        ChooseAuthorFragment authorFragment = new ChooseAuthorFragment();
        fragmentManager.beginTransaction().replace(R.id.choose_author_container, authorFragment, "CHOOSE_AUTHOR_TAG").commit();
    }
}
