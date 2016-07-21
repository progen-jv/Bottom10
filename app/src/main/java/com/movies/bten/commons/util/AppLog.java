package com.movies.bten.commons.util;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created with IntelliJ IDEA.
 * User: Progen
 * Date: 7/9/13
 * Time: 12:23 PM
 * To change this template use File | Settings | File Templates.
 */

public class AppLog {
    private static boolean isEnable = false;
    private static Writer writer;
    private static PrintWriter printWriter;


    // Mandatory: We need to initialize this class when the application starts.
    public static void initializeAppLog(boolean _isEnable) {
        isEnable = _isEnable;
    }


    //Send a DEBUG log message.
    public static void debug(String tag, String message) {
        if (isEnable) {
            message = (message == null) ? "" : message;
            Log.d("DEBUG: " + tag, message);
        }
    }


    //Send a DEBUG log message and log the exception.
    public static void debug(String tag, String message, Throwable throwable) {
        if (isEnable) {
            message = (message == null) ? "" : message;
            Log.d("DEBUG: " + tag, message, throwable);
        }
    }


    //Send a INFO log message and log the exception.
    public static void info(String tag, String message) {
        if (isEnable) {
            message = (message == null) ? "" : message;
            Log.i("INFO: " + tag, message);
        }
    }


    //Send a INFO log message.
    public static void info(String tag, String message, Throwable throwable) {
        if (isEnable) {
            message = (message == null) ? "" : message;
            Log.i("INFO: " + tag, message, throwable);
        }
    }


    //Send a VERBOSE log message.
    public static void verbose(String tag, String message) {
        if (isEnable) {
            message = (message == null) ? "" : message;
            Log.v("VERBOSE: " + tag, message);
        }
    }


    //Send a VERBOSE log message and log the exception.
    public static void verbose(String tag, String message, Throwable throwable) {
        if (isEnable) {
            message = (message == null) ? "" : message;
            Log.v("VERBOSE: " + tag, message, throwable);
        }
    }


    //Send a WARN log message and log the exception.
    public static void warning(String tag, String message) {
        if (isEnable) {
            message = (message == null) ? "" : message;
            Log.w("WARNING: " + tag, message);
        }
    }


    //Send a WARN log message.
    public static void warning(String tag, String message, Throwable throwable) {
        if (isEnable) {
            message = (message == null) ? "" : message;
            Log.w("WARNING: " + tag, message, throwable);
        }
    }


    //Send a ERROR log message.
    public static void error(String tag, String message) {
        if (isEnable) {
            message = (message == null) ? "" : message;
            Log.e("ERROR: " + tag, message);
        }
    }


    //Send a ERROR log message and log the exception.
    public static void error(String tag, String message, Throwable throwable) {
        if (isEnable) {
            message = (message == null) ? "" : message;
            Log.e("ERROR: " + tag, message, throwable);
        }
    }


    //Send a ERROR log message and log the exception.
    public static void error(String tag, Exception exception) {
        if (isEnable) {
            writer = new StringWriter();
            printWriter = new PrintWriter(writer);
            exception.printStackTrace(printWriter);

            Log.e(tag, writer.toString());
        }
    }


    //What a Terrible Failure: Report a condition that should never happen.
    public static void fatalError(String tag, String message) {
        if (isEnable) {
            message = (message == null) ? "" : message;
            Log.e("WTF: " + tag, message);
        }
    }


    //What a Terrible Failure: Report an exception that should never happen.
    public static void fatalError(String tag, String message, Throwable throwable) {
        if (isEnable) {
            message = (message == null) ? "" : message;
            Log.e("WTF: " + tag, message, throwable);
        }
    }


    //What a Terrible Failure: Report an exception that should never happen.
    public static void fatalError(String tag, Exception exception) {
        if (isEnable) {
            writer = new StringWriter();
            printWriter = new PrintWriter(writer);
            exception.printStackTrace(printWriter);

            Log.e(tag, writer.toString());
        }
    }
}
