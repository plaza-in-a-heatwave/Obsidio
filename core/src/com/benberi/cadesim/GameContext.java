package com.benberi.cadesim;


import com.badlogic.gdx.Gdx;
import com.benberi.cadesim.client.ClientConnectionCallback;
import com.benberi.cadesim.client.ClientConnectionTask;
import com.benberi.cadesim.client.codec.util.Packet;
import com.benberi.cadesim.client.packet.ClientPacketHandler;
import com.benberi.cadesim.client.packet.OutgoingPacket;
import com.benberi.cadesim.client.packet.in.LoginResponsePacket;
import com.benberi.cadesim.client.packet.out.*;
import com.benberi.cadesim.game.cade.Team;
import com.benberi.cadesim.game.entity.EntityManager;
import com.benberi.cadesim.game.entity.vessel.Vessel;
import com.benberi.cadesim.game.entity.vessel.move.MoveType;
import com.benberi.cadesim.game.scene.impl.connect.ConnectScene;
import com.benberi.cadesim.game.scene.impl.connect.ConnectionSceneState;
import com.benberi.cadesim.game.scene.GameScene;
import com.benberi.cadesim.game.scene.TextureCollection;
import com.benberi.cadesim.game.scene.impl.battle.SeaBattleScene;
import com.benberi.cadesim.game.scene.impl.control.ControlAreaScene;
import com.benberi.cadesim.input.GameInputProcessor;
import com.benberi.cadesim.util.GameToolsContainer;
import com.benberi.cadesim.util.RandomUtils;

import io.netty.channel.Channel;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameContext {

    private Channel serverChannel;

    public boolean clear;

    private int shipId = 0;

    /**
     * to allow client to display popup messages properly
     */
    private boolean haveServerResponse = false;

    /**
     * Constants to be populated by the server
     */
    private int turnDuration;
    private int roundDuration;

    public int getTurnDuration() {
    	return this.turnDuration;
    }

	public void setTurnDuration(int turnDuration) {
    	this.turnDuration = turnDuration;
    }

    public int getRoundDuration() {
		return roundDuration;
	}

	public void setRoundDuration(int roundDuration) {
		this.roundDuration = roundDuration;
	}

    /**
     * The main input processor of the game
     */
    private GameInputProcessor input;

    /**
     * The sea battle scene
     */
    private SeaBattleScene seaBattleScene;

    /**
     * The control area scene
     */
    private ControlAreaScene controlArea;

    /**
     * The texture collection
     */
    private TextureCollection textures;

    /**
     * The entity manager
     */
    private EntityManager entities;

    public String myVessel;
    
    public int myVesselType;

    /**
     * List of scenes
     */
    private List<GameScene> scenes = new ArrayList<GameScene>();

    /**
     * If connected to server
     */
    private boolean connected = false;

    /**
     * If the game is ready to display
     */
    private boolean isReady = false;

    /**
     * Executors service
     */
    private ExecutorService service = Executors.newSingleThreadExecutor();

    /**
     * Public GSON object
     */
    private GameToolsContainer tools;

    private ClientPacketHandler packets;

    private ConnectScene connectScene;
    public Team myTeam;

	public boolean disposeFurtherScenes = false; // done by render loop
	public boolean disposeConnectScene  = false; // done by render loop

    public GameContext(BlockadeSimulator main) {
        this.tools = new GameToolsContainer();

        entities = new EntityManager(this);
        // init client
        this.packets = new ClientPacketHandler(this);
    }

    /**
     * Create!
     */
    public void create() {
        textures = new TextureCollection(this);
        textures.create();



        this.connectScene = new ConnectScene(this);
        connectScene.create();



    }

    public List<GameScene> getScenes() {
        return this.scenes;
    }

    public EntityManager getEntities() {
        return this.entities;
    }

    public boolean isConnected() {
        return this.connected;
    }

    /**
     * Gets the tools container
     * @return {@link #tools}
     */
    public GameToolsContainer getTools() {
        return this.tools;
    }

    /**
     * Handles the incoming packet from the server
     * @param o The incoming packet
     */
    public void handlePacket(Packet o) {
    }


    /**
     * Gets the texture collection
     * @return {@link #textures}
     */
    public TextureCollection getTextures() {
         return this.textures;
    }

    /**
     * Gets the packet handler
     * @return {@link #packets}
     */
    public ClientPacketHandler getPacketHandler() {
        return packets;
    }
    
    public void createFurtherScenes(int shipId) {
    	ControlAreaScene.shipId = shipId;
    	this.input = new GameInputProcessor(this);
        this.seaBattleScene = new SeaBattleScene(this);
        seaBattleScene.create();
        this.controlArea = new ControlAreaScene(this);
        controlArea.create();
        scenes.clear();
        scenes.add(controlArea);
        scenes.add(seaBattleScene);
    }

    public SeaBattleScene getBattleScene() {
        return (SeaBattleScene) scenes.get(1);
    }

    public ControlAreaScene getControlScene() {
        return (ControlAreaScene) scenes.get(0);
    }

    public boolean isReady() {
        return isReady;
    }

    public void setIsConnected(boolean flag) {
        this.connected = flag;
    }

    public void setServerChannel(Channel serverChannel) {
        this.serverChannel = serverChannel;
    }

    public Channel getServerChannel() {
        return this.serverChannel;
    }

    /**
     * Sends a packet
     * @param p The packet to send
     */
    public void sendPacket(OutgoingPacket p) {
        p.encode();
        serverChannel.write(p);
        serverChannel.flush();
    }

    /**
     * Gets the connection scene
     * @return  {@link #connectScene}
     */
    public ConnectScene getConnectScene() {
        return connectScene;
    }

    /**
     * Sends a login packet to the server with the given display name
     * @param display   The display name
     */
    public void sendLoginPacket(String code, String display, int ship, int team) {
        LoginPacket packet = new LoginPacket();
        packet.setVersion(Constants.PROTOCOL_VERSION);
        packet.setCode(code);
        packet.setName(display);
        packet.setShip(ship);
        packet.setTeam(team);
        sendPacket(packet);
        shipId = ship;
    }

    /**
     * Sends a move placement packet
     * @param slot  The slot to place
     * @param move  The move to place
     */
    public void sendSelectMoveSlot(int slot, MoveType move) {
        PlaceMovePacket packet = new PlaceMovePacket();
        packet.setSlot(slot);
        packet.setMove(move.getId());
        sendPacket(packet);
    }
    
    /**
     * sends a move swap packet
     * basically sending 2x sendSelectMoveSlot at once to avoid spamming with packets.
     */
    public void sendSwapMovesPacket(int slot1, int slot2)
    {
        SwapMovesPacket packet = new SwapMovesPacket();
        packet.set(slot1, slot2);
        sendPacket(packet);
    }


    /**
     * Attempts to connect to server
     *
     * @param displayName   The display name
     * @param ip            The IP Address to connect
     * @throws UnknownHostException 
     */
    public void connect(final String displayName, String ip, String code, int ship, int team) throws UnknownHostException {
    	haveServerResponse = false; // reset for next connect
    	if(!RandomUtils.validIP(ip) && RandomUtils.validUrl(ip)) {
    		try {
	    		InetAddress address = InetAddress.getByName(ip); 
	    		ip = address.getHostAddress();
    		} catch(UnknownHostException e) {
    			return;
    		}
    	}
        service.execute(new ClientConnectionTask(this, ip, new ClientConnectionCallback() {
            @Override
            public void onSuccess(Channel channel) {
                serverChannel = channel; // initialize the server channel
                connectScene.setState(ConnectionSceneState.CREATING_PROFILE);
                sendLoginPacket(code, displayName, ship, team); // send login packet
                myVessel = displayName;
                myVesselType = ship;
                myTeam = Team.forId(team);
            }

            @Override
            public void onFailure() {
                // only show if server appears dead
                if (!haveServerResponse) {
                	connectScene.loginFailed();
                }
            }
        }));
    }

    /**
     * Handles a login response form the server
     *
     * @param response  The response code
     */
    public void handleLoginResponse(int response) {
        if (response != LoginResponsePacket.SUCCESS) {
        	haveServerResponse = true;
            serverChannel.disconnect();

            switch (response) {
                case LoginResponsePacket.BAD_VERSION:
                    connectScene.setPopup("Outdated client");
                    break;
                case LoginResponsePacket.NAME_IN_USE:
                    connectScene.setPopup("Display name already in use");
                    break;
                case LoginResponsePacket.BAD_SHIP:
                    connectScene.setPopup("The selected ship is not allowed");
                    break;
                case LoginResponsePacket.SERVER_FULL:
                    connectScene.setPopup("The server is full");
                    break;
                case LoginResponsePacket.BAD_NAME:
                	connectScene.setPopup("That ship name is not allowed");
                	break;
                default:
                    connectScene.setPopup("Unknown login failure");
                    break;
            }

            connectScene.setState(ConnectionSceneState.DEFAULT);
        }
        else {
        	createFurtherScenes(shipId);
            connectScene.setState(ConnectionSceneState.CREATING_MAP);
        }

    }

    public void setReady(boolean ready) {
        this.isReady = ready;
        Gdx.input.setInputProcessor(input);
        clear = true;
    }
    
    public boolean isDisposeFurtherScenes() {
		return disposeFurtherScenes;
	}

	public void setDisposeFurtherScenes(boolean disposeFurtherScenes) {
		this.disposeFurtherScenes = disposeFurtherScenes;
	}

	public boolean isDisposeConnectScene() {
		return disposeConnectScene;
	}

	public void setDisposeConnectScene(boolean disposeConnectScene) {
		this.disposeConnectScene = disposeConnectScene;
	}

	public GameInputProcessor getInputProcessor() {
    	return input;
    }

	public void dispose() {
		if (isReady()) {
			setReady(false);

			// one time - flag OpenGL to dispose unused scenes on next loop
			setDisposeFurtherScenes(true);
		}
		entities.dispose();

		setIsConnected(false);
		connectScene.setup();
		if (!connectScene.hasPopup())
			connectScene.setPopup("You have disconnected from the server.");
	}

    public void sendBlockingMoveSlotChanged(int blockingMoveSlot) {
        BlockingMoveSlotChanged packet = new BlockingMoveSlotChanged();
        packet.setSlot(blockingMoveSlot);
        sendPacket(packet);
    }

    public void sendAddCannon(int side, int slot) {
        PlaceCannonPacket packet = new PlaceCannonPacket();
        packet.setSide(side);
        packet.setSlot(slot);
        sendPacket(packet);
    }

    public void sendToggleAuto(boolean auto) {
        AutoSealGenerationTogglePacket packet = new AutoSealGenerationTogglePacket();
        packet.setToggle(auto);
        sendPacket(packet);
    }

    public void sendGenerationTarget(MoveType targetMove) {
        SetSealGenerationTargetPacket packet = new SetSealGenerationTargetPacket();
        packet.setTargetMove(targetMove.getId());
        sendPacket(packet);
    }

    public void notifyFinishTurn() {
        TurnFinishNotification packet = new TurnFinishNotification();
        sendPacket(packet);
    }
    
    public void sendDisengageRequestPacket() {
    	OceansideRequestPacket packet = new OceansideRequestPacket();
    	sendPacket(packet);
    }
    
    public void sendPostMessagePacket(String message) {
    	PostMessagePacket packet = new PostMessagePacket();
    	packet.setMessage(message);
    	sendPacket(packet);
    }
}
