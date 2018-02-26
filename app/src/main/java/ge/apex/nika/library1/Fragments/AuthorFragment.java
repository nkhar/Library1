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
import ge.apex.nika.library1.R;


import java.sql.SQLException;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class AuthorFragment extends Fragment {

    protected final String LOG_TAG = "AuthorFragment";


    // TODO: Customize parameters
    private int mColumnCount = 1;

    private OnListFragmentInteractionListener mListener;

    // Reference of DatabaseHelper class to access its DAOs and other components pushing a
    protected DatabaseHelper databaseHelper = null;

    // Declaration of DAO to interact with corresponding Author table
    protected Dao<Author, Integer> authorDao;
    List<Author> authorList = null;

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
        /*
        *
        *
        *
         */
        doAuthorStuff();
        /*
        *
        *
         */

        View view = inflater.inflate(R.layout.fragment_author_list, container, false);

        // Set the adapter
            Context context = view.getContext();

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvAuthors);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            adapter = new MyAuthorRecyclerViewAdapter(authorList, mListener);
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
            /*
            This does not work
             */
            authorList = authorDao.queryForAll();

            Log.d(LOG_TAG, "onResume size of authorList is: " + authorList.size());
            /*
            This method caused instance(field) variable mAuthorValues in class
            MyAuthorRecyclerViewAdapter to be declared without being final. Therefore
             there might be another way to change RecyclerView with mAuthorValues being final.
             */
            adapter.updateList(authorList);


            Log.d(LOG_TAG, "onResume size of authorList is: " + authorList.size());



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
          /*
         *  You'll need this in your class to release the helper when done.
		 */
        if(databaseHelper != null ) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Author item);
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

    public void doAuthorStuff() {
        try {

            authorDao = getDatabaseHelper().getAuthorDao();
            //List<SimpleData>
            authorList = authorDao.queryForAll();
            Log.d(LOG_TAG, "WE got authorDAO");

            // if we already have items in the database
          /*  int authorC = 1;
            for (Author author : authorList) {
                authorDao.delete(author);
                Log.i(LOG_TAG, "deleting author(" + author.getId() + ")");
                authorC++;
            }*/

          /*
            Author author = new Author("Jack", "London", 1876);
            // store it in the database
            authorDao.create(author);

            author = new Author("Mark", "Twain", 1835);
            // store it in the database
            authorDao.create(author);
            author = new Author("Charles", "Dickens", 1812);
            // store it in the database
            authorDao.create(author);
            */

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.i(LOG_TAG, "Done with AuthorFragment at " + System.currentTimeMillis());
    }

}
