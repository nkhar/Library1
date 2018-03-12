package ge.apex.nika.library1.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.SQLException;

import ge.apex.nika.library1.Data.Genre;
import ge.apex.nika.library1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseGenreFragment extends GenreFragment {

    protected final String LOG_TAG = "ChooseGenreFragment";

    public ChooseGenreFragment() {
        // Required empty public constructor
    }


    @Override
    public void onClick(Genre value)  {
        try {
            Genre tempGenre =getDatabaseHelper().getGenreDao().queryForId( value.getGenreId());
            Log.d(LOG_TAG, "The genre is: " + tempGenre.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLongClick(Genre value) {
       Log.d(LOG_TAG, "Long click was called");
    }


    }
