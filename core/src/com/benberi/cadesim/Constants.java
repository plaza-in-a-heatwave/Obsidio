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
    public static final String serverBroadcast = "<cadesim_broadcast>";
    public static final String serverPrivate   = "<cadesim_private>";

    /**
     * Version of client
     */
    public static final String VERSION = "1.9.9";
    public static final int PROTOCOL_VERSION = 12; // MUST match server

	public static final int MAX_NAME_SIZE = 19;   // name of player

	public static final int MAX_CODE_SIZE = 30;   // server code

    /**
     * The default port the simulator is using
     */
    public static int PROTOCOL_PORT = 4970;

    /**
     * whether or not players can choose the black ship. A debugging option.
     */
    public static final boolean ENABLE_CHOOSE_BLACKSHIP = false;
}
