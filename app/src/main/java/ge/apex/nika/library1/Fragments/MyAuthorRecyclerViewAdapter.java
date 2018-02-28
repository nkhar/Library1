package ge.apex.nika.library1.Fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ge.apex.nika.library1.Data.Author;

import ge.apex.nika.library1.R;

import java.util.List;

public class MyAuthorRecyclerViewAdapter extends RecyclerViewListAdapter<MyAuthorRecyclerViewAdapter.ViewHolder, Author> {



    protected final String LOG_TAG = "MyAuthorRecyclerAdapter";

    public MyAuthorRecyclerViewAdapter(List<Author> items) {
        super(items);
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_author, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final Author value) {
        if(value == null)
            return;

        holder.mAuthor = value;
        holder.mIdView.setText(String.valueOf(value.getId()));
        holder.mFName.setText(value.getFName());
        holder.mLName.setText(value.getLName());
        holder.mDateBorn.setText(String.valueOf(value.getDateBorn()));
        final ILibObjectCrud<Author> listener = getmListener();
        if(listener != null)
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(value);
                }
            });
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
