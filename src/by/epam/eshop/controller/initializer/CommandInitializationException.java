package by.epam.eshop.controller.initializer;

/**
 * Created by Aspire on 02.06.2016.
 */
public class CommandInitializationException extends RuntimeException {
    public CommandInitializationException() {
    }

    public CommandInitializationException(String message) {
        super(message);
    }

    public CommandInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandInitializationException(Throwable cause) {
        super(cause);
    }

    public CommandInitializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
