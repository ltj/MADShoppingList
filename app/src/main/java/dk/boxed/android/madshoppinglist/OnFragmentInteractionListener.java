package dk.boxed.android.madshoppinglist;


/**
 * Interface for fragment callbacks
 */
public interface OnFragmentInteractionListener {
    public void onFragmentAddInteraction(ShoppingItem item);
    public void onFragmentEditInteraction(ShoppingItem item);
    public void onFragmentListInteraction(ShoppingItem item);
}
