import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;


public class SubscriberClient extends Client{
    static SubscriberClient client;

    public SubscriberClient() throws RemoteException, MalformedURLException, NotBoundException{
        super();
    }

    /**
     * Sends message to server to subscribe to a data item
     *
     * @param item - name of the data item subscribing to
     * @throws RemoteException
     */
    public void subscribe(String item) throws RemoteException{
        try {
            subServer.subscribe(item, this);
        } catch (DataItemNotFoundException ex) {
            System.out.println("This data item hasn't been published!");
        }


    }

    /**
     * Sends message to server to unsubscribe from a data item
     *
     * @param item - name of the data item unsubscribing from
     * @throws RemoteException
     */
    public void unSubscribe(String item) throws RemoteException{
        try{
            subServer.unSubscribe(item, this);
            System.out.println("Unsubscription successful");
        } catch (NotSubscribedException ex) {
            System.out.println("You're not currently subscribed to this data item");
        }

    }

    /** Main execution of program
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            client =  new SubscriberClient();
            Scanner textScanner = new Scanner(System.in);
            boolean quit = false;
            System.out.println("AVAILABLE COMMANDS: \n" +
                    "subscribe <dataItemName>\n"+
                    "unsubscribe <dataItemName>\n"+
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
     * Executes commands from the user through the simple text interface
     *
     * @param cmd String of user input
     * @return true if the command is a quit command, otherwise false
     * @throws RemoteException
     */
    private static boolean executeCmd(String cmd) throws RemoteException{
        String[] params = cmd.split("\\s+");
        try {
            if (params[0].equals("subscribe")) {
                client.subscribe(params[1]);
            }
            else if (params[0].equals("unsubscribe")) {
                client.unSubscribe(params[1]);
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
