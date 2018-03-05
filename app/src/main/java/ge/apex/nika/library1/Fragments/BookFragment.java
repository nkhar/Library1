package ge.apex.nika.library1.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import ge.apex.nika.library1.BookDetailActivity;
import ge.apex.nika.library1.Data.Author;
import ge.apex.nika.library1.Data.Book;
import ge.apex.nika.library1.Data.Genre;
import ge.apex.nika.library1.DatabaseHelper;
import ge.apex.nika.library1.R;

import java.sql.SQLException;
import java.util.List;

import static ge.apex.nika.library1.LibraryActivity.EXTRA_MESSAGE;

/**
 * A fragment representing a list of Items.
 */
public class BookFragment extends Fragment implements ILibObjectCrud<Book> {

    protected final String LOG_TAG = "BookFragment";

    private final int mColumnCount = 1;


    // Reference of DatabaseHelper class to access its DAOs and other components pushing a
    protected DatabaseHelper databaseHelper = null;

    MyBookRecyclerViewAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BookFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        // Set the adapter
            Context context = view.getContext();

            RecyclerView recyclerView = view.findViewById(R.id.rvBooks);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

        try {
            adapter = new MyBookRecyclerViewAdapter(getDatabaseHelper().getBookDao().queryForAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        adapter.setmListener(this);
        recyclerView.setAdapter(adapter);

        RecyclerView.ItemDecoration localItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(localItemDecoration);

        FloatingActionButton fab = view.findViewById(R.id.fab_book_fragment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                BookFragment.this.startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        Log.d(LOG_TAG, "onResume method was called");
        super.onResume();
        try {
            adapter.updateList(getDatabaseHelper().getBookDao().queryForAll());
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
    public void onClick(Book value) {
        Intent intent = new Intent(getActivity(), BookDetailActivity.class);
        int messageID = value.getBookId();
        intent.putExtra(EXTRA_MESSAGE, messageID);
        startActivity(intent);

    }

    @Override
    public void onLongClick(Book value) {

    }

}
