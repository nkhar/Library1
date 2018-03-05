package ge.apex.nika.library1.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.j256.ormlite.dao.Dao;

import ge.apex.nika.library1.AuthorDetailActivity;
import ge.apex.nika.library1.Data.Author;
import ge.apex.nika.library1.DatabaseHelper;
import ge.apex.nika.library1.LibraryActivity;
import ge.apex.nika.library1.R;


import java.sql.SQLException;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link}
 * interface.
 */
public class AuthorFragment extends Fragment implements ILibObjectCrud<Author> {

    protected final String LOG_TAG = "AuthorFragment";

    private int mColumnCount = 1;

    // Reference of DatabaseHelper class to access its DAOs and other components pushing a
    protected DatabaseHelper databaseHelper = null;

    MyAuthorRecyclerViewAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AuthorFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_author_list, container, false);

        // Set the adapter
            Context context = view.getContext();

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvAuthors);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

        try {
            adapter = new MyAuthorRecyclerViewAdapter(getDatabaseHelper().getAuthorDao().queryForAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        adapter.setmListener(this);
            recyclerView.setAdapter(adapter);

            RecyclerView.ItemDecoration localItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(localItemDecoration);


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_author_fragment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        */;
                Intent intent = new Intent(getActivity(), AuthorDetailActivity.class);
                AuthorFragment.this.startActivity(intent);
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        Log.d(LOG_TAG, "onResume method was called");
        super.onResume();
        try {
            adapter.updateList(getDatabaseHelper().getAuthorDao().queryForAll());
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
    public void onClick(Author value) {
        Intent intent = new Intent(getActivity(), AuthorDetailActivity.class);
        int messageID = value.getId();
        intent.putExtra(LibraryActivity.EXTRA_MESSAGE, messageID);
        startActivity(intent);
    }

    @Override
    public void onLongClick(Author value) {

    }
}
