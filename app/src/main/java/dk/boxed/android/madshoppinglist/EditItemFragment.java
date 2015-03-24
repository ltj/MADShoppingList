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


public class EditItemFragment extends Fragment {

    private static final String TAG = "EditItemFragment";
    private OnFragmentInteractionListener mListener;


    public EditItemFragment() {
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

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentEditInteraction(new ShoppingItem(0, "", 0));
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
    }

    private void onPlus() {
        Log.i(TAG, "Plus clicked");
    }

}
