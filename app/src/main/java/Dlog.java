public class Dlog {

    static final String TAG = "TedPark";


    /**
     * Log Level Error
     **/
    public static final void e(String message) {
    }

    /**
     * Log Level Warning
     **/
    public static final void w(String message) {
    }

    /**
     * Log Level Information
     **/
    public static final void i(String message) {
    }

    /**
     * Log Level Debug
     **/
    public static final void d(String message) {
    }

    /**
     * Log Level Verbose
     **/
    public static final void v(String message) {
    }


    public static String buildLogMsg(String message) {

        StackTraceElement ste = Thread.currentThread().getStackTrace()[4];

        StringBuilder sb = new StringBuilder();

        sb.append("[");
        sb.append(ste.getFileName().replace(".java", ""));
        sb.append("::");
        sb.append(ste.getMethodName());
        sb.append("]");
        sb.append(message);

        return sb.toString();

    }

}