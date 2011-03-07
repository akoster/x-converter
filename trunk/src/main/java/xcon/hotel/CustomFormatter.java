package xcon.hotel;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CustomFormatter extends Formatter {

    @Override
    public String format(LogRecord arg0) {

        StringBuilder b = new StringBuilder();
        b.append(new Date());
        b.append(" ");
        b.append(arg0.getLevel());
        b.append(" ");
        b.append(arg0.getSourceClassName());
        b.append(" ");
        b.append(arg0.getSourceMethodName());
        b.append(" ");
        /** 
        <pre>
        element:1 java.lang.Thread
        element:2 xcon.hotel.CustomFormatter
        element:3 java.util.logging.StreamHandler
        element:4 java.util.logging.ConsoleHandler
        element:5 java.util.logging.Logger
        element:6 java.util.logging.Logger
        element:7 java.util.logging.Logger
        element:8 java.util.logging.Logger </pre>*/

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int lineNumber = stackTrace[8].getLineNumber();
        b.append(lineNumber);
        b.append(" ");
        b.append(arg0.getMessage());
        b.append(System.getProperty("line.separator"));
        return b.toString();
    }
}
