package dk.boxed.android.madshoppinglist;

/**
 * Created by lars on 21/03/15.
 */
public class ShoppingItem {

    private long id;
    private String name;
    private int amount;

    public ShoppingItem(long id, String name, int amount) {
        this.id = id;
        this.name = name;
        this.amount = (amount > 0 ? amount : 0);
    }

    // constructor for new entries w. unknown id's
    public ShoppingItem(String name, int amount) {
        this(-1, name, amount);
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) { this.id = id; }

    public void decrementAmount() {
        if (amount > 0) amount--;
    }

    public void incrementAmount() {
        amount++;
    }
}
