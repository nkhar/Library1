package ge.apex.nika.library1.Fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import ge.apex.nika.library1.Data.Book;
import ge.apex.nika.library1.R;

import java.util.List;


public class MyBookRecyclerViewAdapter extends RecyclerView.Adapter<MyBookRecyclerViewAdapter.ViewHolder> {

    private  List<Book> mBookValues;
    private final BookFragment.OnListBookFragmentInteractionListener mListener;

    protected final String LOG_TAG = "MyBookRecyclerAdapter";

    public MyBookRecyclerViewAdapter(List<Book> items, BookFragment.OnListBookFragmentInteractionListener listener) {
        mBookValues = items;
        mListener = listener;
    }

    /**
     This method caused instance(field) variable mGenreValues in class
     MyGenreRecyclerViewAdapter to be declared without being final. Therefore
     there might be another way to change RecyclerView.
     */
    public void updateList(List<Book> listData) {
        mBookValues = listData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View view = inflater.inflate(R.layout.fragment_book, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.mBook = mBookValues.get(position);
        holder.mBookIdTextView.setText(String.valueOf(mBookValues.get(position).getBookId()));
        holder.mTitleTextView.setText(mBookValues.get(position).getTitle());
        holder.mBookAuthorTextView.setText(mBookValues.get(position).getAuthorId().getFName() + mBookValues.get(position).getAuthorId().getLName());
        holder.mBookGenreTextView.setText(mBookValues.get(position).getGenreId().getName());
        holder.mBookYearPublishedTextView.setText(String.valueOf(mBookValues.get(position).getDate()));
        holder.mBookLanguageTextView.setText(mBookValues.get(position).getLang());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListBookFragmentInteraction(holder.mBook);
                }
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(v.getContext(), "This is a long click", Toast.LENGTH_LONG).show();
                int position = mBookValues.indexOf(holder.mBook);
               mBookValues.remove(position);
               notifyItemRemoved(position);

                return true;
            }


        });

    }

    @Override
    public int getItemCount() {
        Log.d(LOG_TAG, "size is: " + mBookValues.size());
        return mBookValues.size();
    }


    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public final View mView;
        public final TextView mBookIdTextView;
        public final TextView mTitleTextView;
        public final TextView mBookAuthorTextView;
        public final TextView mBookGenreTextView;
        public final TextView mBookYearPublishedTextView;
        public final TextView mBookLanguageTextView;
        public Book mBook;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            mView = itemView;
            mBookIdTextView = itemView.findViewById(R.id.book_id);
            mTitleTextView = itemView.findViewById(R.id.book_title);
            mBookAuthorTextView = itemView.findViewById(R.id.book_author);
            mBookGenreTextView = itemView.findViewById(R.id.book_genre);
            mBookYearPublishedTextView = itemView.findViewById(R.id.book_year_published);
            mBookLanguageTextView = itemView.findViewById(R.id.book_language);
        }

    }
}
