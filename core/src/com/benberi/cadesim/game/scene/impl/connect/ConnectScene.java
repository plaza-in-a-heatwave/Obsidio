package com.benberi.cadesim.game.scene.impl.connect;

import java.io.*;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Properties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.benberi.cadesim.Constants;
import com.benberi.cadesim.GameContext;
import com.benberi.cadesim.game.scene.GameScene;
import com.benberi.cadesim.util.RandomUtils;
import com.benberi.cadesim.game.entity.vessel.Vessel;

public class ConnectScene implements GameScene, InputProcessor {

    private GameContext context;

    // the connect state
    private ConnectionSceneState state = ConnectionSceneState.DEFAULT;
    private long loginAttemptTimestampMillis; // initialised when used

    /**
     * Batch for opening screen
     */
    private SpriteBatch batch;

    /**
     * The shape renderer
     */
    private ShapeRenderer renderer;

    private int connectAnimationState = 0;

    private long lastConnectionAnimatinoStateChange;

    private BitmapFont font;
    private BitmapFont titleFont;
    private BitmapFont notesFont;
    
    // connectscene
    private ArrayList<String> greetings = new ArrayList<String>();
    private ArrayList<String> port_numbers = new ArrayList<String>();
    private ArrayList<String> server_codes = new ArrayList<String>();
    private ArrayList<String> room_names = new ArrayList<String>();
    private java.util.Random prng = new java.util.Random(System.currentTimeMillis());
    private String chosenGreeting;
    private String code_url = "https://github.com/plaza-in-a-heatwave/Obsidio-Server/issues";

    private boolean failed;

    /**
     * The main stage for elements
     */
    private Stage stage;

    private TextField name;
    private TextField address;
    private TextField code;

    private Texture background;
    private Texture textfieldTexture;
    private Texture loginButton;
    private Texture loginButtonHover;

    private Texture baghlah;
    private Texture blackship;
    private Texture dhow;
    private Texture fanchuan;
    private Texture grandfrig;
    private Texture junk;
    private Texture lgsloop;
    private Texture longship;
    private Texture merchbrig;
    private Texture merchgal;
    private Texture smsloop;
    private Texture warbrig;
    private Texture warfrig;
    private Texture wargal;
    private Texture xebec;

    private SelectBox<ShipTypeLabel> shipType;
    private SelectBox<ResolutionTypeLabel> resolutionType;
    private SelectBox<TeamTypeLabel> teamType;
    private SelectBox<RoomNumberLabel> roomLabel;

    private boolean popup;
    private String popupMessage;
    private boolean popupCloseHover;
    private boolean loginHover;
    private boolean codeURL;
    
    private String old_Name;
    private String room_info;
    
    private final int MAIN_GROUP_OFFSET_Y = 20;

    public ConnectScene(GameContext ctx) {
        this.context = ctx;
    }

    @Override
    public void create() {

        Properties prop = new Properties();
        String fileName = "user.config";
        InputStream is = null;
        try {
            is = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // elements font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/font/FjallaOne-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 18;
        parameter.shadowColor = new Color(0, 0, 0, 0.8f);
        parameter.shadowOffsetY = 1;
        font = generator.generateFont(parameter);
        font.setColor(Color.YELLOW);
        
        // notes font
        parameter.size = 11;
        parameter.shadowOffsetX = 0;
        parameter.shadowOffsetY = 0;
        notesFont = generator.generateFont(parameter);
        notesFont.setColor(Color.WHITE);
        
        // title font
        FreeTypeFontGenerator generatorTitle = new FreeTypeFontGenerator(Gdx.files.internal("assets/font/Open_Sans/OpenSans-SemiBold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterTitle = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterTitle.size = 46;
        parameterTitle.gamma = 0.9f;
        titleFont = generatorTitle.generateFont(parameterTitle);
        titleFont.setColor(Color.WHITE);
        
        // greetings
        greetings.add("It simulates blockades!");
        greetings.add("No ships were harmed, honest");
        greetings.add("Hot Pirate On Pirate Blockading Action");
        greetings.add("Job for Keep The Peace!");
        greetings.add("I am a sloop, I do not move!");
        greetings.add("Cyclist Edition");
        greetings.add("Home grown!");
        greetings.add("Blub");
        greetings.add("You sunk my battleship!");
        greetings.add("You'll never guess what happened next...");
        greetings.add("Inconceivable!");
        greetings.add("Every day I'm Simulatin'");
        greetings.add("Matured in oak casks for 24 months");
        greetings.add("Probably SFW");
        greetings.add("Sea monsters are always Kraken jokes");
        greetings.add("Without a shadow of a Trout");
        greetings.add("Just for the Halibut");
        greetings.add("Placing Moves, not Moving Plaice");
        greetings.add("Went to fish frowning comp. Had to Gurnard");
        greetings.add("Just Mullet over");
        greetings.add("Don't tell him, Pike!");
        greetings.add("Tales of Herring Do");
        greetings.add("Needlefish? Get all fish!");
        greetings.add("It's... It's... Eely good");
        greetings.add("Bream me up, Scotty!");
        greetings.add("Living the Bream");
        greetings.add("micro/nano Blockade SIMs available!");
        greetings.add("Written by pirates, for pirates");
        chosenGreeting = greetings.get(prng.nextInt(greetings.size()));
        
        batch = new SpriteBatch();

        background = new Texture("assets/bg.png");
        textfieldTexture = new Texture("assets/skin/textfield.png");
        loginButton = new Texture("assets/skin/login.png");
        loginButtonHover = new Texture("assets/skin/login-hover.png");

        renderer = new ShapeRenderer();

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        setup();

        TextField.TextFieldStyle style = new TextField.TextFieldStyle();
        style.font = font;
        style.fontColor = new Color(0.16f, 0.16f, 0.16f, 1);
        style.cursor = new Image(new Texture("assets/skin/textfield-cursor.png")).getDrawable();
        style.selection = new Image(new Texture("assets/skin/textfield-selection.png")).getDrawable();

        name = new TextField( prop.getProperty("user.username"), style);
        name.setSize(120, 49);
        name.setPosition(170, MAIN_GROUP_OFFSET_Y + 325);

        address = new TextField( prop.getProperty("user.last_address"), style);
        address.setSize(120, 49);
        address.setPosition(326, MAIN_GROUP_OFFSET_Y + 325);
        
        code = new TextField(Constants.SERVER_CODE, style);
        code.setPasswordCharacter('*');
        code.setPasswordMode(true);
        code.setSize(120, 49);
        code.setPosition(482, MAIN_GROUP_OFFSET_Y + 325);
        
        SelectBox.SelectBoxStyle selectBoxStyle = new SelectBox.SelectBoxStyle();
        selectBoxStyle.background = new Image(new Texture("assets/skin/selectbg.png")).getDrawable();
        selectBoxStyle.font = font;
        selectBoxStyle.fontColor = new Color(1,1,1, 1);
        selectBoxStyle.listStyle = new List.ListStyle();
        selectBoxStyle.listStyle.selection = new Image(new Texture("assets/skin/selectbg.png")).getDrawable();
        selectBoxStyle.listStyle.selection.setLeftWidth(5);
        selectBoxStyle.listStyle.font = font;
        selectBoxStyle.listStyle.background = new Image(new Texture("assets/skin/select-list-bg.png")).getDrawable();
        selectBoxStyle.scrollStyle = new ScrollPane.ScrollPaneStyle();
        selectBoxStyle.background.setLeftWidth(10);

        teamType = new SelectBox<>(selectBoxStyle);
        teamType.setSize(150, 44);
        teamType.setPosition(640, 105);
        
        resolutionType = new SelectBox<>(selectBoxStyle);
        resolutionType.setSize(150, 44);
        resolutionType.setPosition(640, 155);

        shipType = new SelectBox<>(selectBoxStyle);
        shipType.setSize(150, 44);
        shipType.setPosition(640, 5);
        
        roomLabel = new SelectBox(selectBoxStyle);
        roomLabel.setSize(150.0f, 44.0f);
        roomLabel.setPosition(640, 55);

        baghlah = new Texture("assets/skin/ships/baghlah.png");
        blackship = new Texture("assets/skin/ships/blackship.png");
        dhow = new Texture("assets/skin/ships/dhow.png");
        fanchuan = new Texture("assets/skin/ships/fanchuan.png");
        grandfrig = new Texture("assets/skin/ships/grandfrig.png");
        junk = new Texture("assets/skin/ships/junk.png");
        lgsloop = new Texture("assets/skin/ships/lgsloop.png");
        longship = new Texture("assets/skin/ships/longship.png");
        merchbrig = new Texture("assets/skin/ships/merchbrig.png");
        merchgal = new Texture("assets/skin/ships/merchgal.png");
        smsloop = new Texture("assets/skin/ships/smsloop.png");
        warbrig = new Texture("assets/skin/ships/warbrig.png");
        warfrig = new Texture("assets/skin/ships/warfrig.png");
        wargal = new Texture("assets/skin/ships/wargal.png");
        xebec = new Texture("assets/skin/ships/xebec.png");

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = new Color(0.16f, 0.16f, 0.16f, 1);

        int numLabels = Vessel.VESSEL_TYPES.size() - (Constants.ENABLE_CHOOSE_BLACKSHIP?0:1);
        ShipTypeLabel[] blob = new ShipTypeLabel[numLabels];
        blob[0]  = new ShipTypeLabel(smsloop,   Vessel.getIdFromName("smsloop"),   "Sloop",       labelStyle); 
        blob[1]  = new ShipTypeLabel(lgsloop,   Vessel.getIdFromName("lgsloop"),   "Cutter",      labelStyle); 
        blob[2]  = new ShipTypeLabel(dhow,      Vessel.getIdFromName("dhow"),      "Dhow",        labelStyle); 
        blob[3]  = new ShipTypeLabel(fanchuan,  Vessel.getIdFromName("fanchuan"),  "Fanchuan",    labelStyle); 
        blob[4]  = new ShipTypeLabel(longship,  Vessel.getIdFromName("longship"),  "Longship",    labelStyle); 
        blob[5]  = new ShipTypeLabel(junk,      Vessel.getIdFromName("junk"),      "Junk",        labelStyle); 
        blob[6]  = new ShipTypeLabel(baghlah,   Vessel.getIdFromName("baghlah"),   "Baghlah",     labelStyle); 
        blob[7]  = new ShipTypeLabel(merchbrig, Vessel.getIdFromName("merchbrig"), "MB",          labelStyle); 
        blob[8]  = new ShipTypeLabel(warbrig,   Vessel.getIdFromName("warbrig"),   "War Brig",    labelStyle); 
        blob[9]  = new ShipTypeLabel(xebec,     Vessel.getIdFromName("xebec"),     "Xebec",       labelStyle); 
        blob[10] = new ShipTypeLabel(merchgal,  Vessel.getIdFromName("merchgal"),  "MG",          labelStyle); 
        blob[11] = new ShipTypeLabel(warfrig,   Vessel.getIdFromName("warfrig"),   "War Frig",    labelStyle); 
        blob[12] = new ShipTypeLabel(wargal,    Vessel.getIdFromName("wargal"),    "War Galleon", labelStyle); 
        blob[13] = new ShipTypeLabel(grandfrig, Vessel.getIdFromName("grandfrig"), "Grand Frig",  labelStyle);
        if (Constants.ENABLE_CHOOSE_BLACKSHIP)
        {
        	blob[numLabels-1] = new ShipTypeLabel(blackship, Vessel.getIdFromName("blackship"), "Black Ship",  labelStyle);
        }

        shipType.setItems(blob);

        ResolutionTypeLabel[] blob3 = new ResolutionTypeLabel[ResolutionTypeLabel.RES_LIST.length];
        for (int i=0; i<ResolutionTypeLabel.RES_LIST.length; i++)
        {
        	blob3[i] = new ResolutionTypeLabel(i, ResolutionTypeLabel.RES_LIST[i], labelStyle);
        }
        resolutionType.setItems(blob3);

        TeamTypeLabel[] blob2 = new TeamTypeLabel[2];
        blob2[0] = new TeamTypeLabel("Defender", labelStyle, TeamTypeLabel.DEFENDER);
        blob2[1] = new TeamTypeLabel("Attacker", labelStyle, TeamTypeLabel.ATTACKER);
        
        teamType.setItems(blob2);
        /*
         * Parse server code/port data for rooms
         */
        try {
			room_info = ConnectScene.getProperty("user.config", "user.room_locations");
			//Split info for each room (Port:Server Code)
			String[] rooms = room_info.split(",");
			for (int i = 0; i < rooms.length; i++) {
				String[] temp_room_info = rooms[i].split(":");
				for (int j = 0; j < temp_room_info.length;j++)
				{
					if (j % 2 == 0) {
						port_numbers.add(temp_room_info[j]);
					}
					else {
						String[] print = temp_room_info[j].split(";");
						server_codes.add(print[0]);
						room_names.add(print[1]);
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Check format");
			e.printStackTrace();
		}
        RoomNumberLabel[] blob_room = new RoomNumberLabel[port_numbers.size()];
        for (int i = 0; i < port_numbers.size(); ++i) {
        	blob_room[i] = new RoomNumberLabel((CharSequence)room_names.get(i), labelStyle, 0);
        }
        roomLabel.setItems(blob_room);
        
        // set previous values/defaults from config file
        try 
        {
        	shipType.setSelectedIndex(Integer.parseInt(prop.getProperty("user.last_ship")));
        }
        catch(IndexOutOfBoundsException e) {
        	shipType.setSelectedIndex(Vessel.getIdFromName("warfrig"));
        }
        try 
        {
        	resolutionType.setSelectedIndex(Integer.parseInt(prop.getProperty("user.last_resolution")));
        }
        catch(IndexOutOfBoundsException e) {
        	resolutionType.setSelectedIndex(0);
        }
        
        try
        {
        	teamType.setSelectedIndex(Integer.parseInt(prop.getProperty("user.last_team")));
        }
        catch(IndexOutOfBoundsException e)
        {
        	teamType.setSelectedIndex(0);
        }
        try {
            roomLabel.setSelectedIndex(Integer.parseInt(prop.getProperty("user.last_room_index")));
        }
        catch (IndexOutOfBoundsException e) {
            roomLabel.setSelectedIndex(0);
        }

        stage.addActor(name);
        stage.addActor(address);
        stage.addActor(code);
        stage.addActor(shipType);
        stage.addActor(resolutionType);
        stage.addActor(teamType);
        stage.addActor(roomLabel);
        
        getServerCode(); // initialize server code with currently selected room
        
        name.addListener(new ChangeListener(){

            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                old_Name = name.getText();
            }
        });
        roomLabel.addListener(new ChangeListener(){

            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                try {
                    getServerCode();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        resolutionType.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // if graphics state not what it was, reload graphics
                try {
                    String last_res = getProperty("user.config", "user.last_resolution");

                    if (Integer.parseInt(last_res) != resolutionType.getSelectedIndex()) {
                        String[] resolution = ResolutionTypeLabel.restypeToRes(resolutionType.getSelectedIndex());

                        // save for next time
                        changeProperty("user.config", "user.width", resolution[0]);
                        changeProperty("user.config", "user.height", resolution[1]);
                        changeProperty("user.config", "user.last_resolution", Integer.toString(resolutionType.getSelectedIndex()));
                        changeProperty("user.config", "user.last_room_index", Integer.toString(roomLabel.getSelectedIndex()));
                        changeProperty("user.config", "user.last_team", Integer.toString(teamType.getSelectedIndex()));
                        
                        // reload for now
                        Gdx.graphics.setWindowedMode(Integer.parseInt(resolution[0]), Integer.parseInt(resolution[1]));
                        context.create();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
         });
    }
    
    public void getServerCode() {
        if (roomLabel.getSelectedIndex() < port_numbers.size()) { //sanity check
        	System.out.println("Room " + (roomLabel.getSelectedIndex() + 1) + " Selected.");
            Constants.PROTOCOL_PORT = Integer.parseInt(port_numbers.get(roomLabel.getSelectedIndex()));
            Constants.SERVER_CODE = server_codes.get(roomLabel.getSelectedIndex());
            code.setText(Constants.SERVER_CODE);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        batch.begin();
        batch.draw(background, 0, 0);
        if (state == ConnectionSceneState.DEFAULT) {

        	titleFont.draw(batch, "Blockade Simulator", 156, MAIN_GROUP_OFFSET_Y + 450);
        	notesFont.draw(batch, chosenGreeting,       587, MAIN_GROUP_OFFSET_Y + 429);
        	notesFont.draw(batch, "Version " + Constants.VERSION + " by Cyclist, based on the Cadesim by Benberi", 15, 75);
        	notesFont.draw(batch, "Inspired by the original Dachimpy Cadesim", 15, 50);
        	notesFont.draw(batch, "Found a bug? Let us know!", 15, 25);
        	
        	if (codeURL) { notesFont.setColor(Color.SKY); }
        	notesFont.draw(batch, code_url, 138, 25);
            notesFont.setColor(Color.WHITE);
        	
            font.setColor(Color.WHITE);
            font.draw(batch, "Display name:",   160, MAIN_GROUP_OFFSET_Y + 400);
            font.draw(batch, "Server address:", 316, MAIN_GROUP_OFFSET_Y + 400);
            font.draw(batch, "Server code:",    472, MAIN_GROUP_OFFSET_Y + 400);
	        batch.draw(textfieldTexture, 160, MAIN_GROUP_OFFSET_Y + 325, 140, 49);
	        batch.draw(textfieldTexture, 316, MAIN_GROUP_OFFSET_Y + 325, 140, 49);
	        batch.draw(textfieldTexture, 472, MAIN_GROUP_OFFSET_Y + 325, 140, 49);
            font.draw(batch, "Settings:", 640, 195);

            batch.draw(loginHover?loginButton:loginButtonHover, 165, MAIN_GROUP_OFFSET_Y + 270);
            font.setColor(new Color(0.1f, 0.1f, 0.1f, 1));
            font.draw(batch, "Connect", 340, MAIN_GROUP_OFFSET_Y + 296);
            batch.end();

            // buttons need to be drawn, then ship texture is drawn over them
            stage.act();
            stage.draw();
            
            Texture t;
            batch.begin();
            t = shipType.getSelected().getType();
            batch.draw(t, 735, 5); // draw t, whatever it may be
            batch.end();

            if (popup) {
                Gdx.gl.glEnable(GL20.GL_BLEND);
                Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                renderer.begin(ShapeRenderer.ShapeType.Filled);
                renderer.setColor(new Color(0f, 0f, 0f, 0.9f));
                renderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

                GlyphLayout layout = new GlyphLayout(font, popupMessage);


                int x = Gdx.graphics.getWidth() / 2 - 200;
                int y = Gdx.graphics.getHeight() / 2 - 50;
                int width = 400;
                int height = 50;

                renderer.setColor(new Color(213 / 255f, 54 / 255f, 53 / 255f, 1));
                renderer.rect(x, y, width, height);

                if (popupCloseHover) {
                    renderer.setColor(new Color(250 / 255f, 93 / 255f, 93 / 255f, 1));
                } else {
                    renderer.setColor(new Color(170 / 255f, 39 / 255f, 39 / 255f, 1));
                }
                renderer.rect(x + 330, y, 70, 50);
                renderer.end();
                batch.begin();
                font.setColor(Color.WHITE);
                font.draw(batch, popupMessage, x + ((400 / 2) - layout.width / 2) - 30, y + (25 + (layout.height / 2)));

                font.draw(batch, "Close", x + 400 - 55, y + (25 + (layout.height / 2)));
                batch.end();
            }
        }
        else {

            /*
             * Cheap way of animation dots lol...
             */
            String dot = "";

            if (connectAnimationState == 0) {
                dot = ".";
            }
            else if (connectAnimationState == 1) {
                dot = "..";
            }
            else if (connectAnimationState == 2) {
                dot = "...";
            }

                font.setColor(Color.YELLOW);
                String text = "(" + ((System.currentTimeMillis() - loginAttemptTimestampMillis)) + "ms) ";

                if (state == ConnectionSceneState.CONNECTING) {
                    text += "Connecting, please wait";
                }
                else if (state == ConnectionSceneState.CREATING_PROFILE) {
                    text += "Connected - creating profile";
                    context.setIsConnected(true);
                }
                else if (state == ConnectionSceneState.CREATING_MAP) {
                    text += "Connected - waiting for board map update";
                }

                GlyphLayout layout = new GlyphLayout(font, text);
                font.draw(batch, text + dot, Gdx.graphics.getWidth() / 2 - (layout.width / 2), 300);

            if (System.currentTimeMillis() - lastConnectionAnimatinoStateChange >= 200) {
                connectAnimationState++;
                lastConnectionAnimatinoStateChange = System.currentTimeMillis();
            }
            if(connectAnimationState > 2) {
                connectAnimationState = 0;
            }
            batch.end();
        }
    }


    public void setPopup(String message) {
        popup = true;
        popupMessage = message;
        name.setDisabled(true);
        address.setDisabled(true);
    }

    public void closePopup() {
        popup = false;
        name.setDisabled(false);
        address.setDisabled(false);
    }

    @Override
    public void dispose() {
		font.dispose();
        titleFont.dispose();
        notesFont.dispose();

        batch.dispose();
        renderer.dispose();

        stage.dispose();

        background.dispose();
        textfieldTexture.dispose();
        loginButton.dispose();
        loginButtonHover.dispose();

        baghlah.dispose();
        blackship.dispose();
        dhow.dispose();
        fanchuan.dispose();
        grandfrig.dispose();
        junk.dispose();
        lgsloop.dispose();
        longship.dispose();
        merchbrig.dispose();
        merchgal.dispose();
        smsloop.dispose();
        warbrig.dispose();
        warfrig.dispose();
        wargal.dispose();
        xebec.dispose();
    }

    @Override
    public boolean handleDrag(float screenX, float screenY, float diffX, float diffY) {
        return false;
    }

    @Override
    public boolean handleClick(float x, float y, int button) {
    	return false;
    }

    @Override
    public boolean handleMouseMove(float x, float y) {
        return false;
    }

    @Override
    public boolean handleClickRelease(float x, float y, int button) {
        return false;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ENTER || keycode == Input.Keys.CENTER) {
            if (!popup) {
                if (stage.getKeyboardFocus() != name && name.getText().isEmpty()) {
                    stage.setKeyboardFocus(name);
                } else if (stage.getKeyboardFocus() != address && address.getText().isEmpty()) {
                    stage.setKeyboardFocus(address);
                } else {
                    try {
                        performLogin();
                    } catch (UnknownHostException e) {
                        return failed;
                    }
                }
            }
            else {
                closePopup();
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (isMouseOverCodeUrl(screenX, Gdx.graphics.getHeight() - screenY))
		{
	    	try {
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(code_url));
			} catch (IOException e) {
				// nvm, couldn't open URL
			}
	    	return true;
		}
		else
		{
			return false;
		}
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (popup && popupCloseHover) {
            closePopup();
        }
        else if (loginHover) {
            try {
                performLogin();
            } catch (UnknownHostException e) {
                return failed;
            }
        }
        return false;
    }

    private void performLogin() throws UnknownHostException {
        loginAttemptTimestampMillis = System.currentTimeMillis();

        if (name.getText().length() > Constants.MAX_NAME_SIZE) {
            setPopup("Display name must be less than " + Constants.MAX_NAME_SIZE + " letters.");
        }
        else if (code.getText().length() > Constants.MAX_CODE_SIZE)
        {
        	setPopup("Server code must be less than " + Constants.MAX_CODE_SIZE + " letters.");
        }
        else if (name.getText().length() <= 0) {
            setPopup("Please enter a display name.");
        }
        else if (address.getText().length() <= 0) {
            setPopup("Please enter an IP Address.");
        }
        else if (!RandomUtils.validIP(address.getText()) && !RandomUtils.validUrl(address.getText())) {
            setPopup("Please enter a valid IP Address or URL.");
        }
        else {
            // Save current choices for next time
            try {
                String[] resolution = ResolutionTypeLabel.restypeToRes(resolutionType.getSelectedIndex());
                changeProperty("user.config", "user.username", name.getText());
                changeProperty("user.config", "user.last_address", address.getText());
                changeProperty("user.config", "user.width", resolution[0]);
                changeProperty("user.config", "user.height", resolution[1]);
                changeProperty("user.config", "user.last_ship", Integer.toString(shipType.getSelectedIndex()));
                changeProperty("user.config", "user.last_resolution", Integer.toString(resolutionType.getSelectedIndex()));
                changeProperty("user.config", "user.last_team", Integer.toString(teamType.getSelectedIndex()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            setState(ConnectionSceneState.CONNECTING);
            context.connect(name.getText(), address.getText(), code.getText(), shipType.getSelected().getIndex(), teamType.getSelected().getType());
        }
    }

    public static void changeProperty(String filename, String key, String value) throws IOException {
        Properties prop =new Properties();
        prop.load(new FileInputStream(filename));
        prop.setProperty(key, value);
        prop.store(new FileOutputStream(filename),null);
    }

    public static String getProperty(String filename, String key) throws IOException {
        Properties prop =new Properties();
        prop.load(new FileInputStream(filename));
        return prop.getProperty(key);
    }
    
    public boolean isMouseOverCodeUrl(float x, float y)
    {
    	return x >= 138 && y >= 10 && x < 447 && y < 31;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        
        if (popup) {
            int popupleftedge = width / 2 - 200;
            int popuprightedge = width / 2 + 200;
            int popuptopedge = height / 2;
            int popupbottomedge = height / 2 + 50;
            loginHover = false;
           // 505 398
            popupCloseHover = screenX >= popupleftedge && screenX <= popuprightedge && screenY >= popuptopedge && screenY <= popupbottomedge;
        }
        else {
        	codeURL = isMouseOverCodeUrl(screenX, height - screenY);
            loginHover = screenX >= 164 && screenX <= 606 && screenY >= height - (MAIN_GROUP_OFFSET_Y + 310) && screenY <= height - (MAIN_GROUP_OFFSET_Y + 270);
        }
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void loginFailed() {
        setPopup("Could not connect to server.");
    }

    public void setState(ConnectionSceneState state) {
        this.state = state;
    }

    public void setup() {
        setState(ConnectionSceneState.DEFAULT);
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public boolean hasPopup() {
        return popup;
    }

}