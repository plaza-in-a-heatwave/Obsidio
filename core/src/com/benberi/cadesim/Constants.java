package com.benberi.cadesim;

public class Constants {

	/**
	 * Name of client
	 */
    public static final String name = "Cadesim client";
    
    /**
     * Name the server identifies itself with
     * Only the server will send chats with this name
     */
    public static final String serverName = "Cadesim server";

    /**
     * Version of client
     */
    public static final String VERSION = "1.9.5";

    /**
     * The protocol version to allow connections to (must match server)
     */
    public static final int PROTOCOL_VERSION = 8; // internal use only

	public static final int MAX_NAME_SIZE = 19;   // name of player

    /**
     * The default port the simulator is using
     */
    public static int PROTOCOL_PORT = 4666;
}
