
public class DataItemAlreadyPublishedException extends Exception {
    /**
     * Thrown when a data item has already been published with the given name
     * @param msg - provide extra details about exception
     */
    public DataItemAlreadyPublishedException(String msg) {
        super(msg);
    }
}
