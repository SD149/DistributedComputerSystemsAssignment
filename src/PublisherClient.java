import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;


public class PublisherClient extends Client {
    static PublisherClient client;

    public PublisherClient() throws RemoteException, MalformedURLException, NotBoundException {
        super();
    }

    /**
     * Publishes a data item
     * Prints out published ID to user if successful
     * If data item with name already exists, this information is output to the user
     *
     * @param name of data item
     * @param initialVal - initial value of item
     * @throws RemoteException

     */
    public void publish(String name, int initialVal) throws RemoteException{
        try {
            int pubId = subServer.publish(name, initialVal);
            System.out.println("Published ID is " + pubId);
        } catch (DataItemAlreadyPublishedException e) {
            System.out.println("This data item has already been published!");
        }
    }

    /**
     * Updates a data item
     * If data item is not published or wrong published ID is used, error is output to the user
     *
     * @param name - name of the data item
     * @param pubId - published ID of data item
     * @param newVal - the new value of the data item
     * @throws RemoteException
     */
    public void update(String name, int pubId, int newVal) throws RemoteException {
        try {
            subServer.update(name, pubId, newVal);
        } catch (DataItemNotFoundException e) {
            System.out.println("No data item with specified ID and name has been published.");
        }
    }

    /**
     * Deletes a specified data item
     * If data item hasn't been published or wrong published ID is used, error output to user
     * @param name - name of the data item
     * @param pubId - published ID of the data item
     * @throws RemoteException
     */
    public void delete(String name, int pubId) throws RemoteException {
        try {
            subServer.delete(name, pubId);
        } catch (DataItemNotFoundException e) {
            System.out.println("No data item with specified ID and name has been published.");
        }
    }

    /**Main execution of program
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            client =  new PublisherClient();
            Scanner textScanner = new Scanner(System.in);
            boolean quit = false;
            System.out.println("AVAILABLE COMMANDS: \n" +
                    "publish <dataItemName> <initialValue>\n"+
                    "update <dataItemName> <publishedID> <newValue>\n"+
                    "delete <dataItemName> <publishedID>\n"+
                    "quit\n");

            while (!quit) {
                System.out.println("Enter a command: ");
                String input = textScanner.nextLine();
                quit = executeCmd(input);
            }
        } catch(Exception ex) {
            System.out.println("EXCEPTION IN CLIENT: " + ex);
        }
        System.exit(0);

    }

    /**
     * Executes commands input by the user through text interface
     * If command isn't valid this is output to the user
     *
     * @param cmd - the line input by the user
     * @return - true if user has quit, otherwise false
     * @throws RemoteException
     */
    private static boolean executeCmd(String cmd) throws RemoteException{
        String[] params = cmd.split("\\s+");
        try {
            if (params[0].equals("publish")) {
                client.publish(params[1], Integer.parseInt(params[2]));
                System.out.println(params[1] + " published");
            }
            else if (params[0].equals("update")) {
                client.update(params[1], Integer.parseInt(params[2]), Integer.parseInt(params[3]));
                System.out.println(params[1] + " updated");
            }
            else if (params[0].equals("delete")) {
                client.delete(params[1], Integer.parseInt(params[2]));
                System.out.println(params[1] + " deleted successfully");
            }
            else if (params[0].equals("quit")) {
                return true;
            }
            else {
                System.out.println("That command isn't valid.");
            }
        } catch (NumberFormatException ex) {
            System.out.println("You entered invalid parameters (a string where an integer should exist)");
        }

        return false;
    }
}
