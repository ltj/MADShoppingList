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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddItemFragment newInstance(String param1, String param2) {
        AddItemFragment fragment = new AddItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AddItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

            if (item != null) {
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