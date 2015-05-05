import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Based on the RMI Call back example by M. L. Liu (listed example on http://orb.essex.ac.uk/ce/ce329)
 */
public interface ClientCallBack extends Remote {

    public void notify(String message) throws RemoteException;

}
