
public class NotSubscribedException extends Exception {
    /**
     * Thrown when a subscriber isn't subscribed to a data item
     * @param msg - extra details about exception
     */
    public NotSubscribedException(String msg) {
        super(msg);
    }
}
