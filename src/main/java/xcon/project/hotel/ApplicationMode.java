package xcon.project.hotel;

/** Specifies the modes the application can run in. */
public enum ApplicationMode {

    /**
     * Standalone mode. The data file will be accessed directly without network
     * access
     */
    ALONE,

    /**
     * Network client mode which contacts a server instance via RMI.
     */
    NETWORKED,

    /**
     * Server mode. The server handles requests from network cliets and accesses
     * the data file
     */
    SERVER,

    /**
     * Stub mode. This is a standalone mode which uses an in-memory DBAccess
     * implementation
     */
    STUB;

    public boolean needsLocalDatabaseFile() {
        return this == ApplicationMode.ALONE || this == ApplicationMode.SERVER;
    }

    public boolean needsPortNumber() {
        return this == ApplicationMode.NETWORKED
            || this == ApplicationMode.SERVER;
    }

    public boolean needsHostName() {
        return this == ApplicationMode.NETWORKED;
    }

    public static ApplicationMode parseArguments(String[] args) {

        // When the user has not specified any command line parameters the
        // application runs in network mode
        if (args.length == 0) {
            return ApplicationMode.NETWORKED;
        }

        if ("server".equalsIgnoreCase(args[0])) {
            return ApplicationMode.SERVER;
        }

        if ("alone".equalsIgnoreCase(args[0])) {
            return ApplicationMode.ALONE;
        }

        if ("stub".equalsIgnoreCase(args[0])) {
            return ApplicationMode.STUB;
        }
        throw new IllegalArgumentException("Could not parse argument: "
            + args[0] + ". Allowed arguments are 'server' and 'alone'");
    }
}
