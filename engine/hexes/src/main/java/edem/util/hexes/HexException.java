package edem.util.hexes;

public class HexException extends Exception {

    public HexException(String message) {
        super(message);
    }
    
    public HexException(Throwable throwable) {
        super(throwable);
    }
    
    public HexException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
}
