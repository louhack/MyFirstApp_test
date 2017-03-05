package com.example.louac.myfirstapp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.louac.myfirstapp.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class HeadlinesFragment extends Fragment implements SearchView.OnQueryTextListener{


    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private MyHeadlinesFragmentRecyclerViewAdapter adapter;
/*
    */
/**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

  /*  public HeadlinesFragment(){

    }
*/


    @SuppressWarnings("unused")
    public static HeadlinesFragment newInstance(int columnCount) {
        HeadlinesFragment fragment = new HeadlinesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_headlines_list, container, false);

        SearchView sView = (SearchView) getActivity().findViewById(R.id.searchView);
        sView.setOnQueryTextListener(this);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter = new MyHeadlinesFragmentRecyclerViewAdapter(DummyContent.ITEMS, mListener);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<DummyContent.DummyItem> filteredModelList = filter(DummyContent.ITEMS, newText);

        adapter.setFilter(filteredModelList);
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.list);
        recyclerView.setAdapter(adapter);

        return true;
    }

    private List<DummyContent.DummyItem> filter(List<DummyContent.DummyItem> models, String query) {
        query = query.toLowerCase();
        final List<DummyContent.DummyItem> filteredModelList = new ArrayList<>();
        //DummyContent.DummyItem model = new DummyContent.DummyItem(null,null,null);
        for (DummyContent.DummyItem model : models) {
            final String text = model.content;
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
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
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(DummyContent.DummyItem item);
    }
}
