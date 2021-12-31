package thingkingInJava;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

/**
 * @author wincher
 * @date   2017/6/28.
 * src:12.4.1 异常与记录日志
 */
class LoggingException extends Exception {
    private static Logger logger = Logger.getLogger(LoggingException.class.getName());
    public LoggingException() {
        StringWriter trace = new StringWriter();
        printStackTrace(new PrintWriter(trace));
        logger.severe(trace.toString());
    }
}
public class LoggingExceptions {
    public static void main(String[] args) {
        try {
            throw new LoggingException();
        } catch (LoggingException e) {
            System.err.println("Caught " + e);
        }
        try {
            throw new LoggingException();
        } catch (LoggingException e) {
            System.out.println("Caught " + e);
        }
        throw new RuntimeException();
    }
}
