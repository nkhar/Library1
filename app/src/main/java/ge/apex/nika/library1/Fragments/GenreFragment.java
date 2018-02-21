package ge.apex.nika.library1.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import ge.apex.nika.library1.AuthorDetailActivity;
import ge.apex.nika.library1.Data.Genre;
import ge.apex.nika.library1.DatabaseHelper;
import ge.apex.nika.library1.GenreDetailActivity;
import ge.apex.nika.library1.R;

/**
 * Created by NATIA on 21/02/2018.
 */


public class GenreFragment extends Fragment {

    protected final String LOG_TAG = "GenreFragment";


    // TODO: Customize parameters
    private int mColumnCount = 1;

    private OnListGenreFragmentInteractionListener mListener;

    // Reference of DatabaseHelper class to access its DAOs and other components pushing a
    protected DatabaseHelper databaseHelper = null;

    // Declaration of DAO to interact with corresponding Author table
    protected Dao<Genre, Integer> genreDao;
    List<Genre> genreList = null;

    MyGenreRecyclerViewAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GenreFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        /*
        *
        *
        *
         */
        doGenreStuff();
        /*
        *
        *
         */

        View view = inflater.inflate(R.layout.fragment_genre_list, container, false);



            Context context = view.getContext();
            /*
            * RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.rvGenres);
            * gave a strange error which returned null context. Look at this post in stackoverflow.
            * https://stackoverflow.com/questions/32459297/view-getcontext-return-null-why
            * so it should be changed to:
            * RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvGenres);
             */
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvGenres);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
        adapter = new MyGenreRecyclerViewAdapter(genreList, mListener);
        recyclerView.setAdapter(new MyGenreRecyclerViewAdapter(genreList, mListener));





        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_genre_fragment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        */;
                Intent intent = new Intent(getActivity(), GenreDetailActivity.class);
                GenreFragment.this.startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
        genreList = genreDao.queryForAll();
        adapter.notifyDataSetChanged();

    } catch (SQLException e) {
        e.printStackTrace();
    }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListGenreFragmentInteractionListener) {
            mListener = (OnListGenreFragmentInteractionListener) context;
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
    public interface OnListGenreFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListGenreFragmentInteraction(Genre genreItem);
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


    public void doGenreStuff() {
        try {

            genreDao = getDatabaseHelper().getGenreDao();
            //List<SimpleData>
            genreList = genreDao.queryForAll();
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
        Log.i(LOG_TAG, "Done with GenreFragment at " + System.currentTimeMillis());
    }
}
