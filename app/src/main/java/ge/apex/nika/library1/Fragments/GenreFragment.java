package ge.apex.nika.library1.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ge.apex.nika.library1.AuthorDetailActivity;
import ge.apex.nika.library1.GenreDetailActivity;
import ge.apex.nika.library1.R;

/**
 * Created by NATIA on 21/02/2018.
 */


public class GenreFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_genre_list, container, false);
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
}
