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
    private ShoppingItem item;

    public static AddItemFragment newInstance(ShoppingItem item) {
        AddItemFragment fragment = new AddItemFragment();
        fragment.setItem(item);
        return fragment;
    }

    public AddItemFragment() {
        // Required empty public constructor
    }

    public void setItem(ShoppingItem item) {
        this.item = item;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);

        if (item != null) {
            EditText name = (EditText) view.findViewById(R.id.editTextItem);
            name.setText(item.getName());
            EditText amount = (EditText) view.findViewById(R.id.editTextAmount);
            if (item.getAmount() > 0) amount.setText(((Integer)item.getAmount()).toString());
        }

        return view;
    }


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
