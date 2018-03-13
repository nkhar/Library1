package ge.apex.nika.library1.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import java.sql.SQLException;

import ge.apex.nika.library1.ChooseAuthorActivity;
import ge.apex.nika.library1.Data.Author;

/**
 * Created by Nika on 13/03/2018.
 * This is subclass of AuthorFragment, that should help BookDetailActivity class to choose author.
 * It is hosted by ChooseAuthorActivity.
 * @see ge.apex.nika.library1.ChooseAuthorActivity
 * @see ge.apex.nika.library1.BookDetailActivity
 * @see AuthorFragment
 */

public class ChooseAuthorFragment extends AuthorFragment {
    protected final String LOG_TAG = "ChooseAuthorFragment";

    public ChooseAuthorFragment() {
        // Required empty public constructor
    }



    @Override
    public void onClick(Author value)  {
        try {
            Author tempAuthor =getDatabaseHelper().getAuthorDao().queryForId( value.getId());
            Log.d(LOG_TAG, "The Author is: " + tempAuthor.toString());
            returnResult(tempAuthor);
            getActivity().finish();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLongClick(Author value) {
        Log.d(LOG_TAG, "Long click was called");
    }



    private void returnResult(Author author){
        Intent tempData = new Intent();
        tempData.putExtra(ChooseAuthorActivity.EXTRA_AUTHOR_ACTIVITY_AUTHOR_ID, author.getId());
        getActivity().setResult(Activity.RESULT_OK, tempData);
    }

}

