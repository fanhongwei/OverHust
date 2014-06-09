package com.unique.overhust.fragment;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.unique.overhust.CommonUtils.Database.DBManager;
import com.unique.overhust.CommonUtils.ListViewAdapter;
import com.unique.overhust.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchResultsFragment extends Fragment {
    private View view;
    private ImageView backImageView;
    private ListView mListView;

    private String searchString;


    public SearchResultsFragment() {
        // Required empty public constructor

    }

    public static SearchResultsFragment newInstance(String key) {
        SearchResultsFragment searchResultsFragment = new SearchResultsFragment();
        searchResultsFragment.searchString = key;
        return searchResultsFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = getActivity().getLayoutInflater().inflate(R.layout.fragment_search_results, null);

        backImageView = (ImageView) view.findViewById(R.id.search_back);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mListView = (ListView) view.findViewById(R.id.fragment_search_results_listview);
        DBManager.open(getActivity());
        Cursor mCursor = DBManager.getCursor("name", searchString);
        if (mCursor.getCount() == 0) {
            mCursor = DBManager.getCursor("category", searchString);
        }
        mListView.setAdapter(new ListViewAdapter(getActivity(), mCursor, false));
        DBManager.close();
    }
}
