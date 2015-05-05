import java.util.ArrayList;
import java.util.List;

public class DataItem {
    private static int nextId = 1; //used to create unique published ID for each published item

    private int id; //published ID
    private String name;
    private int value;
    private List<ClientCallBack> subscribers;

    /**
     * Constructor for data item
     * @param name - Name of the data item, cannot be changed later
     * @param val - the initial value, can be changed later
     */
    public DataItem(String name, int val){
        id = nextId++;
        this.name = name;
        value = val;
        subscribers = new ArrayList<>();
    }

    /**
     * Add a subscriber to a particular data item
     *
     * @param client - reference to the clients call back object
     */
    public void addSubscriber(ClientCallBack client) {
        subscribers.add(client);
    }

    /**
     * Removes a subscriber from a data item
     *
     * @param client - reference to clients call back object
     *               so it can be removed
     */
    public void removeSubscriber(ClientCallBack client) {
        subscribers.remove(client);
    }

    /**
     *
     * @return - a list of subscribers (call back objects) on data item
     */
    public List<ClientCallBack> getSubscribers() {
        return subscribers;
    }

    /**
     * Updates the value of a data item
     * @param newVal - new value to be set
     */
    public void updateValue(int newVal) {
        value = newVal;
    }

    /**
     *
     * @return - the published ID of the data item, to be used
     *          by the publisher in updating/deletion
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return - name of the data item
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return - value of data item
     */
    public int getValue() {
        return value;
    }

}
