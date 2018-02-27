package ge.apex.nika.library1.Fragments;

import android.content.Context;
import android.os.Bundle;
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

import ge.apex.nika.library1.Data.Author;
import ge.apex.nika.library1.Data.Book;
import ge.apex.nika.library1.Data.Genre;
import ge.apex.nika.library1.DatabaseHelper;
import ge.apex.nika.library1.R;

import java.sql.SQLException;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListBookFragmentInteractionListener}
 * interface.
 */
public class BookFragment extends Fragment {

    protected final String LOG_TAG = "BookFragment";

    private int mColumnCount = 1;

    private OnListBookFragmentInteractionListener mListener;

    // Reference of DatabaseHelper class to access its DAOs and other components pushing a
    protected DatabaseHelper databaseHelper = null;

    // Declaration of DAO to interact with corresponding Book table
    protected Dao<Book, Integer> bookDao;
    List<Book> bookList = null;

    // Declaration of DAO to interact with corresponding Author table
    protected Dao<Author, Integer> authorDao;
    List<Author> authorList = null;

    // Declaration of DAO to interact with corresponding Genre table
    protected Dao<Genre, Integer> genreDao;
    List<Genre> genreList = null;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*
        *
        *
        *
         */
        doBookStuff();
        /*
        *
        *
         */

        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        // Set the adapter
            Context context = view.getContext();
            RecyclerView recyclerView = view.findViewById(R.id.rvBooks);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

        adapter = new MyBookRecyclerViewAdapter(bookList, mListener);
        recyclerView.setAdapter(adapter);

        RecyclerView.ItemDecoration localItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(localItemDecoration);

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
            bookList = bookDao.queryForAll();

            Log.d(LOG_TAG, "onResume size of bookList is: " + bookList.size());
            /*
            This method caused instance(field) variable mAuthorValues in class
            MyAuthorRecyclerViewAdapter to be declared without being final. Therefore
             there might be another way to change RecyclerView with mAuthorValues being final.
             */
           adapter.updateList(bookList);


            Log.d(LOG_TAG, "onResume size of authorList is: " + bookList.size());



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListBookFragmentInteractionListener) {
            mListener = (OnListBookFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListBookFragmentInteractionListener");
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
    public interface OnListBookFragmentInteractionListener {

        void onListBookFragmentInteraction(Book item);
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

    public void doBookStuff() {
        try {

            bookDao = getDatabaseHelper().getBookDao();
            //List<SimpleData>
            bookList = bookDao.queryForAll();
            Log.d(LOG_TAG, "WE got bookDao");

            // if we already have items in the database



        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.i(LOG_TAG, "Done with BookFragment at " + System.currentTimeMillis());
    }
}
