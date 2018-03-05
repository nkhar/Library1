package ge.apex.nika.library1.Fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ge.apex.nika.library1.Data.Genre;
import ge.apex.nika.library1.R;

/**
 * Created by Nika on 21/02/2018.
 */

public class MyGenreRecyclerViewAdapter extends RecyclerViewListAdapter<MyGenreRecyclerViewAdapter.ViewHolder, Genre> {

    protected final String LOG_TAG = "MyGenreRecyclerAdapter";

    public MyGenreRecyclerViewAdapter(List <Genre> items) {
       super(items);
    }


    @NonNull
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
    public void onBindViewHolder(final ViewHolder holder, final Genre value) {

        if(value == null)
            return;
        holder.mGenre =value;
        holder.mIdTextView.setText(String.valueOf(value.getGenreId()));
        holder.mGenreNameTextView.setText(value.getName());
        final ILibObjectCrud listener = getmListener();
        if(listener != null) {
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(value);
                }
            });
        }
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
            mIdTextView = itemView.findViewById(R.id.genre_id);
            mGenreNameTextView = itemView.findViewById(R.id.genre_Name);

        }

    }
}
