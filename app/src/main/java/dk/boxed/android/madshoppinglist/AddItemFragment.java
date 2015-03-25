package dk.boxed.android.madshoppinglist;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


public class AddItemFragment extends Fragment {

    private static final String TAG = "AddItemFragment.";

    private OnFragmentInteractionListener mListener;

    public AddItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_item, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(ShoppingItemDAO itemDAO) {
        if (mListener != null) {
            EditText name = (EditText) getView().findViewById(R.id.editTextItem);
            EditText amount = (EditText) getView().findViewById(R.id.editTextAmount);
            ShoppingItem item;

            try {
                item = new ShoppingItem(name.getText().toString(),
                        Integer.parseInt(amount.getText().toString()));
            }
            catch (NumberFormatException e) {
                // handle bad input in amount
                item = null;
            }

            if (item != null && item.getAmount() != 0) {
                long rowId = itemDAO.insertItem(item.getName(), item.getAmount());

                if (rowId != -1) {
                    item.setId(rowId);
                    name.setText("");
                    amount.setText("");
                }
            }

            mListener.onFragmentAddInteraction(item);
        }
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
}
