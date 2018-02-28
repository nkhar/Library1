package ge.apex.nika.library1.Fragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ge.apex.nika.library1.Data.Author;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Author} and makes a call to the
 * specified {@link AuthorFragment}.
 * TODO: Replace the implementation with code for your data type.
 */


public  abstract class RecyclerViewListAdapter<T extends RecyclerView.ViewHolder, E> extends RecyclerView.Adapter<T> {
    private List<E> mValues;
    private View.OnClickListener mViewClick;
    private ILibObjectCrud<E> mListener;



    public void updateList(List<E> listData) {
        mValues = listData;
        notifyDataSetChanged();
    }
    public RecyclerViewListAdapter() {
    }

    public RecyclerViewListAdapter(List<E> items) {
        mValues = items;
    }

    public abstract T onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(final T holder, int position) {
        if(mValues != null)
            onBindViewHolder(holder, mValues.get(position));
        else
            onBindViewHolder(holder, null);
    }

    @Override
    public int getItemCount() {
        if(mValues == null)
            return 0;
        return mValues.size();
    }

    public abstract void onBindViewHolder(final T holder, final E value);

    public View.OnClickListener getmViewClick() {
        return mViewClick;
    }

    public void setmViewClick(View.OnClickListener mViewClick) {
        this.mViewClick = mViewClick;
    }

    public ILibObjectCrud<E> getmListener() {
        return mListener;
    }

    public void setmListener(ILibObjectCrud<E> mListener) {
        this.mListener = mListener;
    }




}

interface ILibObjectCrud<E> {
    void onClick(E value);
    void onLongClick(E value);
}
