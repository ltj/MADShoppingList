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
import android.widget.TextView;
import android.widget.Toast;


public class ShoppingListActivity extends ActionBarActivity
        implements OnFragmentInteractionListener, View.OnClickListener {

    private static final String TAG = "ShoppingListActivity.";

    private static final String ADD_ITEM_FRAGMENT_TAG = "add_item_fragment";
    private static final String EDIT_ITEM_FRAGMENT_TAG = "edit_item_fragment";

    private static final String STATE_TOP_FRAGMENT_LABEL = "top_frag_label";
    private static final String STATE_CURRENT_ITEM_ID = "state_item_id";
    private static final String STATE_CURRENT_ITEM_NAME = "state_item_name";
    private static final String STATE_CURRENT_ITEM_AMOUNT = "state_item_amount";

    private ShoppingItemDAO itemDAO;
    private ListFragment list;
    private String currentTopFragmentTag = ADD_ITEM_FRAGMENT_TAG;
    private ShoppingItem currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        // get DAO and open db
        itemDAO = new ShoppingItemDAO(this);
        itemDAO.open();

        if (savedInstanceState != null) {
            currentTopFragmentTag = savedInstanceState.getString(STATE_TOP_FRAGMENT_LABEL);
            currentItem = new ShoppingItem(
                    savedInstanceState.getLong(STATE_CURRENT_ITEM_ID),
                    savedInstanceState.getString(STATE_CURRENT_ITEM_NAME),
                    savedInstanceState.getInt(STATE_CURRENT_ITEM_AMOUNT)
            );
        }

        // put fragment in top UI
        ShoppingItem item =
                (currentTopFragmentTag == ADD_ITEM_FRAGMENT_TAG ? null : currentItem);
        swapTopFragment(currentTopFragmentTag, item);

        // get list fragment
        list = (ListFragment) getFragmentManager().findFragmentById(R.id.list_fragment);

        updateTotal();
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
        if(item.getId() == -1) {
            // db error
            Toast.makeText(this, "DB error", Toast.LENGTH_SHORT).show();
        }
        else if (item.getAmount() == 0) {
            // invalid amount
            Toast.makeText(this, item.getName() + " not added. Zero amount.", Toast.LENGTH_SHORT).show();
        }
        else {
            // All good. Re-query
            updateListAdapter();
            Toast.makeText(this, item.getName() + " added to list.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFragmentEditInteraction(ShoppingItem item) {
        if (item.getAmount() > 0) {
            itemDAO.updateAmount(item.getId(), item.getAmount());
            updateListAdapter();
        }
        if (item.getAmount() == 0) {
            itemDAO.deleteItem(item.getId());
            updateListAdapter();
            swapTopFragment(ADD_ITEM_FRAGMENT_TAG, null);
        }
    }

    @Override
    public void onFragmentListInteraction(ShoppingItem item) {
        currentItem = item;
        swapTopFragment(EDIT_ITEM_FRAGMENT_TAG, item);
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
            // Swap fragment
            swapTopFragment(ADD_ITEM_FRAGMENT_TAG, null);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (currentItem != null) {
            outState.putLong(STATE_CURRENT_ITEM_ID, currentItem.getId());
            outState.putString(STATE_CURRENT_ITEM_NAME, currentItem.getName());
            outState.putInt(STATE_CURRENT_ITEM_AMOUNT, currentItem.getAmount());
        }
        outState.putString(STATE_TOP_FRAGMENT_LABEL, currentTopFragmentTag);
        super.onSaveInstanceState(outState);
    }

    private void updateListAdapter() {
        if (list != null) {
            SimpleCursorAdapter adapter = (SimpleCursorAdapter) list.getListAdapter();
            adapter.swapCursor(itemDAO.getItems());
            adapter.notifyDataSetChanged();
            updateTotal();
        }
    }

    private void swapTopFragment(String newtag, ShoppingItem item) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment next = null;

        switch (newtag) {
            case ADD_ITEM_FRAGMENT_TAG:
                next = new AddItemFragment();
                break;
            case EDIT_ITEM_FRAGMENT_TAG:
                next = EditItemFragment.newInstance(item);
        }

        if (next != null) {
            fragmentTransaction.replace(R.id.top_fragment_container, next,
                    newtag);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

        currentTopFragmentTag = newtag;
    }

    private void updateTotal() {
        Long total = itemDAO.getTotal();
        TextView showAmount = (TextView) findViewById(R.id.textView_total_amount);
        showAmount.setText(total.toString());
    }

}
