package dk.boxed.android.madshoppinglist;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


import dk.boxed.android.madshoppinglist.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class ShoppingItemFragment extends ListFragment {

    private OnFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ShoppingItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setListAdapter(new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
//                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS));
        ShoppingItemDAO dao = new ShoppingItemDAO(getActivity());
        dao.open();
        Cursor cursor = dao.getItems();

        String[] fromColumns = {ShoppingListContract.ShoppingItem.COLUMN_NAME_NAME,
                ShoppingListContract.ShoppingItem.COLUMN_NAME_AMOUNT};
        int[] toViews = { android.R.id.text1, android.R.id.text2 };

        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_2, cursor, fromColumns,
                toViews, 0);

        setListAdapter(cursorAdapter);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            mListener.onFragmentListInteraction(id);
        }
    }

}
