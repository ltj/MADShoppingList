package dk.boxed.android.madshoppinglist;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class EditItemFragment extends Fragment {

    private static final String TAG = "EditItemFragment";


    private OnFragmentInteractionListener mListener;
    private ShoppingItem item;

    /* Fragment factory method
    *  sets the ShoppingItem object
    */
    public static EditItemFragment newInstance(ShoppingItem item) {
        EditItemFragment fragment = new EditItemFragment();
        fragment.setItem(item);
        return fragment;
    }

    public EditItemFragment() {
        // Required empty public constructor
    }

    public void setItem(ShoppingItem item) { this.item = item; }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_item, container, false);

        Button plus = (Button) view.findViewById(R.id.button_plus_amount);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlus();
            }
        });

        Button minus = (Button) view.findViewById(R.id.button_minus_amount);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMinus();
            }
        });

        if (item != null) {
            TextView label = (TextView) view.findViewById(R.id.textView_edit_item);
            label.setText(item.getName());
        }

        return view;
    }


    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentEditInteraction(item);
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

    private void onMinus() {
        Log.i(TAG, "Minus clicked");
        if (item != null) {
            item.decrementAmount();
            onButtonPressed();
        }
    }

    private void onPlus() {
        Log.i(TAG, "Plus clicked");
        if (item != null)  {
            item.incrementAmount();
            onButtonPressed();
        }

    }

}
