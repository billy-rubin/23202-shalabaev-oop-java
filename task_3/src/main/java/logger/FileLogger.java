package logger;

import org.slf4j.LoggerFactory;

public class FileLogger implements Logger {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FileLogger.class);

    @Override
    public void info(String message) {
        logger.info(message);
    }
    @Override
    public void error(String message) {
        logger.error(message);
    }
    @Override
    public void warn(String message) {
        logger.warn(message);
    }
}
