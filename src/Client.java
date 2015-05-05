import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public abstract class Client extends UnicastRemoteObject implements ClientCallBack {
    SubscriptionService subServer;

    //URL to the location of the subscription server
    static final String REGISTRY_URL = "rmi://localhost:1499/SubscriptionService";

    /**
     * Constructor for client, connects to subscription server
     * @throws RemoteException
     * @throws MalformedURLException
     * @throws NotBoundException
     */
    public Client() throws RemoteException, MalformedURLException, NotBoundException {
        super();
        subServer = (SubscriptionService) Naming.lookup(REGISTRY_URL);
    }

    /**
     * Call back function for use by the server to update client
     * about data items
     *
     * @param message - the message to be printed by the client
     * @throws RemoteException
     */
    @Override
    public void notify(String message) throws RemoteException {
        System.out.println(message);
    }
}
