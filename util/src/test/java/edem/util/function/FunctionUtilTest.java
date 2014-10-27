package edem.util.function;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import org.testng.annotations.Test;

@Test
public class FunctionUtilTest {
    
    private static final String EXCEPTION_MESSAGE = "Exception Message";
    private static final String FUNCTION_RESULT = "Function Result";
    
    public void testWrapGoodResult() {
        // given
        boolean throwException = false;
        Function<Boolean, ResultOrException<String, IOException>> wrappedFunction = FunctionUtil.wrapException(FunctionUtilTest::test);
        
        // when
        ResultOrException<String, IOException> resultOrException = wrappedFunction.apply(throwException);
        
        // then
        assertThat(resultOrException.hasResult(), is(true));
        assertThat(resultOrException.hasException(), is(false));
        assertThat(resultOrException.getResult(), is(FUNCTION_RESULT));
        assertThat(resultOrException.getException(), is(nullValue()));
    }
    
    public void testWrapException() {
        // given
        boolean throwException = true;
        Function<Boolean, ResultOrException<String, IOException>> wrappedFunction = FunctionUtil.wrapException(FunctionUtilTest::test);
        
        // when
        ResultOrException<String, IOException> resultOrException = wrappedFunction.apply(throwException);
        
        // then
        assertThat(resultOrException.hasResult(), is(false));
        assertThat(resultOrException.hasException(), is(true));
        assertThat(resultOrException.getResult(), is(nullValue()));
        assertThat(resultOrException.getException(), isA(IOException.class));
        assertThat(resultOrException.getException().getMessage(), is(EXCEPTION_MESSAGE));
    }
    
    public void testUnwrapGoodResult() throws IOException {
        // given
        ResultOrException<String, IOException> resultOrException = new ResultOrException(FUNCTION_RESULT);
        
        // when
        String result = FunctionUtil.unwrap(resultOrException);
        
        // then
        assertThat(result, is(FUNCTION_RESULT));
    }
    
    @Test(expectedExceptions = IOException.class, expectedExceptionsMessageRegExp = EXCEPTION_MESSAGE)
    public void testUnwrapException() throws IOException {
        // given
        ResultOrException<String, IOException> resultOrException = new ResultOrException(new IOException(EXCEPTION_MESSAGE));
        
        // when
        FunctionUtil.unwrap(resultOrException);
    }
    
    public void testUnwrapAllGoodResults() throws IOException {
        // given
        List<ResultOrException<String, IOException>> resultOrExceptions = Arrays.asList(new ResultOrException(FUNCTION_RESULT), new ResultOrException(FUNCTION_RESULT));
        
        // when
        Collection<String> result = FunctionUtil.unwrap(resultOrExceptions);
        
        // then
        assertThat(result, contains(FUNCTION_RESULT, FUNCTION_RESULT));
    
    }
    @Test(expectedExceptions = IOException.class, expectedExceptionsMessageRegExp = EXCEPTION_MESSAGE)
    public void testUnwrapIncludingException() throws IOException {
        // given
        List<ResultOrException<String, IOException>> resultOrExceptions = Arrays.asList(new ResultOrException(FUNCTION_RESULT), new ResultOrException(new IOException(EXCEPTION_MESSAGE)));
        
        // when
        FunctionUtil.unwrap(resultOrExceptions);
    }
    
    private static String test(boolean throwException) throws IOException {
        if (throwException) {
            throw new IOException(EXCEPTION_MESSAGE);
        }
        
        return FUNCTION_RESULT;
    }
    
}
