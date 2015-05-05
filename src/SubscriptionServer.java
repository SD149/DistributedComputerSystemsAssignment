import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;

public class SubscriptionServer extends UnicastRemoteObject implements SubscriptionService{

    //port number of RMI register
    public static final int REGISTRY_PORT = 1499;

    //URL to provide RMI services on
    public static final String REGISTRY_URL = "//localhost:"
            + REGISTRY_PORT + "/SubscriptionService";

    private ArrayList<DataItem> dataItems;

    public SubscriptionServer() throws RemoteException {
        dataItems = new ArrayList<>();
    }

    public static void main(String[] args) throws RemoteException {
        try {
            Registry registry = LocateRegistry.getRegistry(REGISTRY_PORT);
            registry.list();
        } catch (RemoteException e) {
            // No valid registry at that port, so create one
            Registry registry = LocateRegistry.createRegistry(REGISTRY_PORT);
            System.out.println("Created registry");
        }
        try {
            SubscriptionServer service = new SubscriptionServer();
            Naming.rebind(REGISTRY_URL, service);
            System.out.println("Subscription service registered at " + REGISTRY_URL);
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param dataItem - name of the item subscribing to
     * @param callBack - reference to call back object to inform client about updates
     * @throws RemoteException
     * @throws DataItemNotFoundException
     */
    @Override
    public void subscribe(String dataItem, ClientCallBack callBack) throws RemoteException, DataItemNotFoundException {
        boolean subbed = false;
        for (DataItem d: dataItems) {
            if (d.getName().equals(dataItem)) {
                d.addSubscriber(callBack);
                callBack.notify("Current value of " + dataItem + ": " + d.getValue());
                subbed = true;
                break;
            }
        }
        if (!subbed) {
            throw new DataItemNotFoundException("This data item hasn't been published");
        }
    }

    /**
     *
     * @param dataItem - name of item unsubscribing from
     * @param callBack - reference to call back object from client
     * @throws RemoteException
     * @throws NotSubscribedException
     */
    @Override
    public void unSubscribe(String dataItem, ClientCallBack callBack) throws RemoteException, NotSubscribedException {
        boolean unsubbed = false;
        for (DataItem d: dataItems) {
            if (d.getName().equals(dataItem)) {
                d.removeSubscriber(callBack);
                unsubbed = true;
                break;
            }
        }
        if (!unsubbed) {
            throw new NotSubscribedException("Not currently subscribed to this data item");
        }
    }

    /**
     *
     * @param dataItem - name of the data item
     * @param value - the initial value
     * @return - published ID to be used in update/delete methods
     * @throws RemoteException
     * @throws DataItemAlreadyPublishedException
     */
    @Override
    public int publish(String dataItem, int value) throws RemoteException, DataItemAlreadyPublishedException {
        for (DataItem d: dataItems) {
            if (d.getName().equals(dataItem)) {
                throw new DataItemAlreadyPublishedException("This data item has already been published");
            }
        }

        DataItem item = new DataItem(dataItem, value);
        dataItems.add(item);
        return item.getId();
    }

    /**
     *
     * @param dataItem - name of the item
     * @param publishedId - published ID, returned to user when item was published
     * @param newVal - new value of the data item
     * @throws RemoteException
     * @throws DataItemNotFoundException
     */
    @Override
    public void update(String dataItem, int publishedId, int newVal) throws RemoteException, DataItemNotFoundException{
        boolean updated = false;
        for (DataItem d : dataItems) {
            if (d.getId() == publishedId && d.getName().equals(dataItem)) {
                d.updateValue(newVal);
                for (ClientCallBack subscriber : d.getSubscribers()) {
                    subscriber.notify("New value of " + d.getName() + ": " + d.getValue());
                }
                updated = true;
                break;
            }
        }
        if (!updated) {
            //doesnt exist, throw exception
            throw new DataItemNotFoundException("Invalid combination of ID and Name");
        }
    }

    /**
     *
     * @param dataItem - name of the data item
     * @param publishedId - published ID, returned to user when item was published
     * @throws RemoteException
     * @throws DataItemNotFoundException
     */
    @Override
    public void delete(String dataItem, int publishedId) throws RemoteException, DataItemNotFoundException{
        boolean removed = false;
        Iterator<DataItem> it = dataItems.iterator();
        while (it.hasNext()) {
            DataItem d = it.next();
            if (d.getId() == publishedId && d.getName().equals(dataItem)) {
                it.remove();
                removed = true;
                break;
            }
        }

        if (!removed) {
            //doesnt exist, throw exception
            throw new DataItemNotFoundException("Invalid combination of ID and Name");
        }
    }
}
