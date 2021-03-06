package dk.boxed.android.madshoppinglist;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class ShoppingItemFragment extends ListFragment {

    private static final String TAG = "ShoppingItemFragment";
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

        ShoppingItemDAO dao = new ShoppingItemDAO(getActivity());
        dao.open();
        Cursor cursor = dao.getItems();

        String[] fromColumns = {ShoppingListContract.ShoppingItem.COLUMN_NAME_NAME,
                ShoppingListContract.ShoppingItem.COLUMN_NAME_AMOUNT};
        int[] toViews = { R.id.text1, R.id.text2 };

        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(getActivity(),
                R.layout.shopping_list_item, cursor, fromColumns,
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

        Cursor cursor = ((SimpleCursorAdapter)getListAdapter()).getCursor();
        cursor.moveToPosition(position);
        String name = cursor.getString(cursor.
                getColumnIndexOrThrow(ShoppingListContract.ShoppingItem.COLUMN_NAME_NAME));
        int amount = cursor.getInt(cursor.
                getColumnIndexOrThrow(ShoppingListContract.ShoppingItem.COLUMN_NAME_AMOUNT));

        if (null != mListener) {
            mListener.onFragmentListInteraction(new ShoppingItem(id, name, amount));
        }
    }

}
