package dk.boxed.android.madshoppinglist;


/**
 * Created by lars on 19/03/15.
 */
public interface OnFragmentInteractionListener {
    public void onFragmentAddInteraction(ShoppingItem item);
    public void onFragmentEditInteraction(ShoppingItem item);
    public void onFragmentListInteraction(long id);
}
