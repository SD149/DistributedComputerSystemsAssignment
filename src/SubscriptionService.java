import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SubscriptionService extends Remote {

    /**
     * Used by a subscriber to subscribe to updates for a data item with the given name
     * @param dataItem - name of the item subscribing to
     * @param callBack - reference to call back object to inform client about updates
     * @throws RemoteException
     * @throws DataItemNotFoundException - if the data item hasn't been published yet
     */
    public void subscribe(String dataItem, ClientCallBack callBack) throws RemoteException, DataItemNotFoundException;

    /**
     * Unsubscribes to a data item with the given name
     * @param dataItem - name of item unsubscribing from
     * @param callBack - reference to call back object from client
     * @throws RemoteException
     * @throws NotSubscribedException - if the user isn't subscribed to the data item
     */
    public void unSubscribe(String dataItem, ClientCallBack callBack) throws RemoteException, NotSubscribedException;

    /**
     * Used by publishers to publish a data item
     * @param dataItem - name of the data item
     * @param value - the initial value
     * @return - published ID, to be used in update/delete methods
     * @throws RemoteException
     * @throws DataItemAlreadyPublishedException - if a published item with the name already exists
     */
    public int publish(String dataItem, int value) throws RemoteException, DataItemAlreadyPublishedException;

    /**
     * Updates the value of a data item
     * @param dataItem - name of the item
     * @param publishedId - published ID, returned to user when item was published
     * @param newVal - new value of the data item
     * @throws RemoteException
     * @throws DataItemNotFoundException - if the data item hasn't been published
     */
    public void update(String dataItem, int publishedId, int newVal) throws RemoteException, DataItemNotFoundException;

    /**
     * Deletes a data item
     * @param dataItem - name of the data item
     * @param publishedId - published ID, returned to user when item was published
     * @throws RemoteException
     * @throws DataItemNotFoundException - if the data item hasn't been published
     */
    public void delete(String dataItem, int publishedId) throws RemoteException, DataItemNotFoundException;



}
