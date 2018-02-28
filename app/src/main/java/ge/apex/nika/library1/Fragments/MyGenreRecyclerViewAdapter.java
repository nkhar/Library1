package ge.apex.nika.library1.Fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ge.apex.nika.library1.Data.Genre;
import ge.apex.nika.library1.R;

/**
 * Created by NATIA on 21/02/2018.
 */

public class MyGenreRecyclerViewAdapter extends RecyclerView.Adapter<MyGenreRecyclerViewAdapter.ViewHolder> {

    private  List<Genre> mGenreValues;
    private final GenreFragment.OnListGenreFragmentInteractionListener mListener;
    private final GenreFragment.OnListGenreFragmentLongClickListener mLongClickListener;

    protected final String LOG_TAG = "MyGenreRecyclerAdapter";

    public MyGenreRecyclerViewAdapter(List<Genre> items, GenreFragment.OnListGenreFragmentInteractionListener listener, GenreFragment.OnListGenreFragmentLongClickListener longClickListener) {
        mGenreValues = items;
        mListener = listener;
        mLongClickListener = longClickListener;
    }

    /**
            This method caused instance(field) variable mGenreValues in class
            MyGenreRecyclerViewAdapter to be declared without being final. Therefore
             there might be another way to change RecyclerView.
             */
    public void updateList(List<Genre> listData) {
        mGenreValues = listData;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View view = inflater.inflate(R.layout.fragment_genre, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mGenre = mGenreValues.get(position);
        holder.mIdTextView.setText(mGenreValues.get(position).getGenreId() + "");
        holder.mGenreNameTextView.setText(mGenreValues.get(position).getName());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListGenreFragmentInteraction(holder.mGenre);
                }
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(v.getContext(), "This is a long click", Toast.LENGTH_LONG).show();
                if (null != mLongClickListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mLongClickListener.onListGenreFragmentLongClickListener(holder.mGenre);
                }

                //int position = mGenreValues.indexOf(holder.mGenre);
               // mGenreValues.remove(position);
                //notifyItemRemoved(position);

                return true;
            }


        });

    }

    @Override
    public int getItemCount() {
        Log.d(LOG_TAG, "size is: " + mGenreValues.size());
        return mGenreValues.size();
    }


    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public final View mView;
        public final TextView mIdTextView;
        public final TextView mGenreNameTextView;
        public Genre mGenre;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            mView = itemView;
            mIdTextView = (TextView) itemView.findViewById(R.id.genre_id);
            mGenreNameTextView = (TextView) itemView.findViewById(R.id.genre_Name);

        }

    }
}
