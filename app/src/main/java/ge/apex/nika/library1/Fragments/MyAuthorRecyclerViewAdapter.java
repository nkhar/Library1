package ge.apex.nika.library1.Fragments;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ge.apex.nika.library1.Data.Author;
import ge.apex.nika.library1.Fragments.AuthorFragment.OnListFragmentInteractionListener;

import ge.apex.nika.library1.R;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Author} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyAuthorRecyclerViewAdapter extends RecyclerView.Adapter<MyAuthorRecyclerViewAdapter.ViewHolder> {

    private List<Author> mAuthorValues;
    private final OnListFragmentInteractionListener mListener;

    protected final String LOG_TAG = "MyAuthorRecyclerAdapter";

    public MyAuthorRecyclerViewAdapter(List<Author> items, OnListFragmentInteractionListener listener) {
        mAuthorValues = items;
        mListener = listener;
    }

    /**
     This method caused instance(field) variable mAuthorValues in class
     MyAuthorRecyclerViewAdapter to be declared without being final. Therefore
     there might be another way to change RecyclerView.
     */
    public void updateList(List<Author> listData) {
        mAuthorValues = listData;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_author, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mAuthor = mAuthorValues.get(position);
        holder.mIdView.setText(mAuthorValues.get(position).getId() + "");
        holder.mFName.setText(mAuthorValues.get(position).getFName());
        holder.mLName.setText(mAuthorValues.get(position).getLName());
        holder.mDateBorn.setText(mAuthorValues.get(position).getDateBorn() + "");

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mAuthor);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d(LOG_TAG, "size is: " + mAuthorValues.size());
        return mAuthorValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mFName;
        public final TextView mLName;
        public final TextView mDateBorn;
        public Author mAuthor;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.author_id);
            mFName = (TextView) view.findViewById(R.id.author_FName);
            mLName = (TextView) view.findViewById(R.id.author_LName);
            mDateBorn = (TextView) view.findViewById(R.id.author_DateBorn);
        }

      @Override
        public String toString() {
            return  " " + mFName.getText() + "'";
        }
    }
}
