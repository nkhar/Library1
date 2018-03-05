package ge.apex.nika.library1.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j256.ormlite.android.apptools.OpenHelperManager;


import java.sql.SQLException;



import ge.apex.nika.library1.Data.Genre;
import ge.apex.nika.library1.DatabaseHelper;
import ge.apex.nika.library1.GenreDetailActivity;
import ge.apex.nika.library1.LibraryActivity;
import ge.apex.nika.library1.R;

/**
 * Created by Nika on 21/02/2018.
 * @author Nika
 * This displays genre fragment after it has been selected in navigation drawer.
 */


public class GenreFragment extends Fragment implements ILibObjectCrud<Genre>{

    protected final String LOG_TAG = "GenreFragment";


    private final int mColumnCount = 1;

    // Reference of DatabaseHelper class to access its DAOs and other components pushing a
    protected DatabaseHelper databaseHelper = null;

    static MyGenreRecyclerViewAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GenreFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate method was called");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(LOG_TAG, "onCreateView method was called");


        View view = inflater.inflate(R.layout.fragment_genre_list, container, false);


            Context context = view.getContext();
            /*
            * RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.rvGenres);
            * gave a strange error which returned null context. Look at this post in stackoverflow.
            * https://stackoverflow.com/questions/32459297/view-getcontext-return-null-why
            * so it should be changed to:
            * RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvGenres);
             */
            RecyclerView recyclerView = view.findViewById(R.id.rvGenres);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
        try {
            adapter = new MyGenreRecyclerViewAdapter(getDatabaseHelper().getGenreDao().queryForAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        adapter.setmListener(this);
        recyclerView.setAdapter(adapter);

        RecyclerView.ItemDecoration localItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(localItemDecoration);




        FloatingActionButton fab = view.findViewById(R.id.fab_genre_fragment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        */
                Intent intent = new Intent(getActivity(), GenreDetailActivity.class);
                GenreFragment.this.startActivity(intent);
            }
        });

        Log.d(LOG_TAG, "onCreateView method finished execution");
        return view;

    }



    @Override
    public void onResume() {
        Log.d(LOG_TAG, "onResume method was called");
        super.onResume();
        try {
            adapter.updateList(getDatabaseHelper().getGenreDao().queryForAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();

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
            databaseHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    public void onClick(Genre value) {
        Intent intent = new Intent(getActivity(), GenreDetailActivity.class);
        int messageID = value.getGenreId();
        intent.putExtra(LibraryActivity.EXTRA_MESSAGE_ID, messageID);
        startActivity(intent);
    }

    @Override
    public void onLongClick(Genre value) {
        try {
            getDatabaseHelper().getGenreDao().deleteById(value.getGenreId());
            adapter.updateList(getDatabaseHelper().getGenreDao().queryForAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
