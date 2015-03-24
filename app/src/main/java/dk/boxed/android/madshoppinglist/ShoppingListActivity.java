package dk.boxed.android.madshoppinglist;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SimpleCursorAdapter;


public class ShoppingListActivity extends ActionBarActivity
        implements OnFragmentInteractionListener, View.OnClickListener {

    private static final String TAG = "ShoppingListActivity.";
    private static final String ADD_ITEM_FRAGMENT_TAG = "add_item_fragment";
    private static final String EDIT_ITEM_FRAGMENT_TAG = "edit_item_fragment";

    private ShoppingItemDAO itemDAO;
    private ListFragment list;
    private boolean topFragmentAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        // get DAO and open db
        itemDAO = new ShoppingItemDAO(this);
        itemDAO.open();

        // add fragment if needed
        if (!topFragmentAdded) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            AddItemFragment addItemFragment = new AddItemFragment();
            fragmentTransaction.replace(R.id.top_fragment_container, addItemFragment,
                    ADD_ITEM_FRAGMENT_TAG);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            topFragmentAdded = true;
        }

        // get list fragment
        list = (ListFragment) getFragmentManager().findFragmentById(R.id.list_fragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // close db
        if(itemDAO != null) itemDAO.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shopping_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onFragmentAddInteraction(ShoppingItem item) {
        Log.d(TAG, item.getName() + " " + item.getAmount());
        updateListAdapter();
    }

    @Override
    public void onFragmentEditInteraction(ShoppingItem item) {

    }

    @Override
    public void onFragmentListInteraction(ShoppingItem item) {
        swapTopFragment(EDIT_ITEM_FRAGMENT_TAG);
    }

    @Override
    public void onClick(View v) {
        // add item clicks
        if (v.getId() == R.id.ok_button_add) {
            Fragment current = getFragmentManager().findFragmentByTag(ADD_ITEM_FRAGMENT_TAG);
            if (current != null) {
                ((AddItemFragment) current).onButtonPressed(itemDAO);
            }
        }
        // edit item clicks
        if (v.getId() == R.id.ok_button_edit) {
            // TODO: Handle db edits
            // Swap fragment
            swapTopFragment(ADD_ITEM_FRAGMENT_TAG);
        }
    }

    private void updateListAdapter() {
        if (list != null) {
            SimpleCursorAdapter adapter = (SimpleCursorAdapter) list.getListAdapter();
            adapter.swapCursor(itemDAO.getItems());
            adapter.notifyDataSetChanged();
        }
    }

    private void swapTopFragment(String newtag) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment next = null;

        switch (newtag) {
            case ADD_ITEM_FRAGMENT_TAG:
                next = new AddItemFragment();
                break;
            case EDIT_ITEM_FRAGMENT_TAG:
                next = new EditItemFragment();
        }

        if (next != null) {
            fragmentTransaction.replace(R.id.top_fragment_container, next,
                    newtag);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
