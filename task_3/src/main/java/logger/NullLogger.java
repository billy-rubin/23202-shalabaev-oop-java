package logger;

public class NullLogger implements Logger {
    @Override
    public void info(String message) {}
    @Override
    public void warn(String message) {}
    @Override
    public void error(String message) {}
}

