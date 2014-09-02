package edem.util.function;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class FunctionUtil {
    
    private FunctionUtil() {
        // NOOP
    }
    
    public static <INPUT, OUTPUT, EXCEPTION extends Exception> 
        Function<INPUT, ResultOrException<OUTPUT, EXCEPTION>> wrapException(ThrowingFunction<INPUT, OUTPUT, EXCEPTION> function) {
        return (INPUT operand) -> {
            try {
                OUTPUT output = function.apply(operand);
                return new ResultOrException<>(output);
            } catch (Exception e) {
                return new ResultOrException<>((EXCEPTION) e);
            }
        };
    }
        
    public static <OUTPUT, EXCEPTION extends Exception> OUTPUT unwrap(ResultOrException<OUTPUT, EXCEPTION> resultOrException) throws EXCEPTION {
        if (resultOrException.hasResult()) {
            return resultOrException.getResult();
        }
        
        throw resultOrException.getException();
    }
    
    public static <OUTPUT, EXCEPTION extends Exception> Collection<OUTPUT> unwrap(Collection<ResultOrException<OUTPUT, EXCEPTION>> resultOrExceptions) throws EXCEPTION {
        ResultOrException<OUTPUT, EXCEPTION> exception = resultOrExceptions.stream().filter(ResultOrException::hasException).findFirst().get();
        
        if (exception != null) {
            throw exception.getException();
        }
        
        return resultOrExceptions
                .stream()
                .map(ResultOrException::getResult)
                .collect(Collectors.toList());
    }
        
    @FunctionalInterface
    public static interface ThrowingFunction<INPUT, OUTPUT, EXCEPTION extends Exception> {
        OUTPUT apply(INPUT i) throws EXCEPTION;
    }
    
}
