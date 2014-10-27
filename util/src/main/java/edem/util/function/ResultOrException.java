package edem.util.function;

public class ResultOrException<RESULT, EXCEPTION extends Exception> {

    private final RESULT result;
    private final EXCEPTION exception;

    public ResultOrException(RESULT result) {
        this.result = result;
        this.exception = null;
    }

    public ResultOrException(EXCEPTION exception) {
        this.result = null;
        this.exception = exception;
    }

    public boolean hasResult() {
        // result may be null but if there is an exception that'll never be null
        return exception == null;
    }

    public RESULT getResult() {
        return result;
    }

    public boolean hasException() {
        return exception != null;
    }

    public EXCEPTION getException() {
        return exception;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("ResultOrException: ");

        if (hasResult()) {
            sb.append("Result: [").append(getResult().toString()).append("]");
        } else {
            sb.append("Exception: [").append(getException().toString()).append("]");
        }

        return sb.toString();
    }

}