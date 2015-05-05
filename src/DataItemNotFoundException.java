
public class DataItemNotFoundException extends Exception {
    /**
     * Thrown when a data item hasn't been published
     * @param msg - extra details about exception
     */
    public DataItemNotFoundException(String msg) {
        super(msg);
    }
}
