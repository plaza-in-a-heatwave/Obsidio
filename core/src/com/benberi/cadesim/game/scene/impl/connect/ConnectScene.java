package com.benberi.cadesim.game.scene.impl.connect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.benberi.cadesim.Constants;
import com.benberi.cadesim.GameContext;
import com.benberi.cadesim.game.entity.vessel.Vessel;
import com.benberi.cadesim.game.scene.GameScene;
import com.benberi.cadesim.game.scene.impl.connect.ConnectionSceneState;
import com.benberi.cadesim.game.scene.impl.connect.ResolutionTypeLabel;
import com.benberi.cadesim.game.scene.impl.connect.RoomNumberLabel;
import com.benberi.cadesim.game.scene.impl.connect.ShipTypeLabel;
import com.benberi.cadesim.game.scene.impl.connect.TeamTypeLabel;
import com.benberi.cadesim.util.RandomUtils;
import java.awt.Desktop;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

public class ConnectScene
implements GameScene,
InputProcessor {
    private GameContext context;
    private ConnectionSceneState state = ConnectionSceneState.DEFAULT;
    private long loginAttemptTimestampMillis;
    private SpriteBatch batch;
    private ShapeRenderer renderer;
    private int connectAnimationState = 0;
    private long lastConnectionAnimatinoStateChange;
    private BitmapFont font;
    private BitmapFont titleFont;
    private BitmapFont notesFont;
    private ArrayList<String> greetings = new ArrayList();
    private ArrayList<String> port_numbers = new ArrayList();
    private ArrayList<String> server_codes = new ArrayList();
    private Random prng = new Random(System.currentTimeMillis());
    private String chosenGreeting;
    private String code_url = "https://github.com/plaza-in-a-heatwave/Obsidio-Server/issues";
    private boolean failed;
    private Stage stage;
    private TextField name;
    private TextField address;
    public TextField code;
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
    private SelectBox<RoomNumberLabel> roomLabel;
    private SelectBox<TeamTypeLabel> teamType;
    private boolean popup;
    private String popupMessage;
    private String old_Name;
    private String room_info;
    private int ally_room;
    private boolean popupCloseHover;
    private boolean loginHover;
    private boolean codeURL;
    private final int MAIN_GROUP_OFFSET_Y = 20;

    public ConnectScene(GameContext ctx) {
        this.context = ctx;
    }

    @Override
    public void create() {
        Properties prop = new Properties();
        String fileName = "user.config";
        FileInputStream is = null;
        try {
            is = new FileInputStream(fileName);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            prop.load(is);
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/font/FjallaOne-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 18;
        parameter.shadowColor = new Color(0.0f, 0.0f, 0.0f, 0.8f);
        parameter.shadowOffsetY = 1;
        this.font = generator.generateFont(parameter);
        this.font.setColor(Color.YELLOW);
        parameter.size = 11;
        parameter.shadowOffsetX = 0;
        parameter.shadowOffsetY = 0;
        this.notesFont = generator.generateFont(parameter);
        this.notesFont.setColor(Color.WHITE);
        FreeTypeFontGenerator generatorTitle = new FreeTypeFontGenerator(Gdx.files.internal("assets/font/Open_Sans/OpenSans-SemiBold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterTitle = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterTitle.size = 46;
        parameterTitle.gamma = 0.9f;
        this.titleFont = generatorTitle.generateFont(parameterTitle);
        this.titleFont.setColor(Color.WHITE);
        this.greetings.add("It simulates blockades!");
        this.greetings.add("No ships were harmed, honest");
        this.greetings.add("Hot Pirate On Pirate Blockading Action");
        this.greetings.add("Job for Keep The Peace!");
        this.greetings.add("I am a sloop, I do not move!");
        this.greetings.add("Cyclist Edition");
        this.greetings.add("Home grown!");
        this.greetings.add("Blub");
        this.greetings.add("You sunk my battleship!");
        this.greetings.add("You'll never guess what happened next...");
        this.greetings.add("Inconceivable!");
        this.greetings.add("Every day I'm Simulatin'");
        this.greetings.add("Matured in oak casks for 24 months");
        this.greetings.add("Probably SFW");
        this.greetings.add("Sea monsters are always Kraken jokes");
        this.greetings.add("Without a shadow of a Trout");
        this.greetings.add("Just for the Halibut");
        this.greetings.add("Placing Moves, not Moving Plaice");
        this.greetings.add("Went to fish frowning comp. Had to Gurnard");
        this.greetings.add("Just Mullet over");
        this.greetings.add("Don't tell him, Pike!");
        this.greetings.add("Tales of Herring Do");
        this.greetings.add("Needlefish? Get all fish!");
        this.greetings.add("It's... It's... Eely good");
        this.greetings.add("Bream me up, Scotty!");
        this.greetings.add("Living the Bream");
        this.greetings.add("micro/nano Blockade SIMs available!");
        this.greetings.add("Written by pirates, for pirates");
        this.chosenGreeting = this.greetings.get(this.prng.nextInt(this.greetings.size()));
        this.batch = new SpriteBatch();
        this.background = new Texture("assets/bg.png");
        this.textfieldTexture = new Texture("assets/skin/textfield.png");
        this.loginButton = new Texture("assets/skin/login.png");
        this.loginButtonHover = new Texture("assets/skin/login-hover.png");
        this.renderer = new ShapeRenderer();
        this.stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        this.setup();
        TextField.TextFieldStyle style = new TextField.TextFieldStyle();
        style.font = this.font;
        style.fontColor = new Color(0.16f, 0.16f, 0.16f, 1.0f);
        style.cursor = new Image(new Texture("assets/skin/textfield-cursor.png")).getDrawable();
        style.selection = new Image(new Texture("assets/skin/textfield-selection.png")).getDrawable();
        this.name = new TextField(prop.getProperty("user.username"), style);
        this.name.setSize(120.0f, 49.0f);
        this.name.setPosition(170.0f, 345.0f);
        this.address = new TextField(prop.getProperty("user.last_address"), style);
        this.address.setSize(120.0f, 49.0f);
        this.address.setPosition(326.0f, 345.0f);
        this.code = new TextField(Integer.toString(Constants.SERVER_CODE), style);
        this.code.setPasswordCharacter('*');
        this.code.setPasswordMode(true);
        this.code.setSize(120.0f, 49.0f);
        this.code.setPosition(482.0f, 345.0f);
        SelectBox.SelectBoxStyle selectBoxStyle = new SelectBox.SelectBoxStyle();
        selectBoxStyle.background = new Image(new Texture("assets/skin/selectbg.png")).getDrawable();
        selectBoxStyle.font = this.font;
        selectBoxStyle.fontColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        selectBoxStyle.listStyle = new List.ListStyle();
        selectBoxStyle.listStyle.selection = new Image(new Texture("assets/skin/selectbg.png")).getDrawable();
        selectBoxStyle.listStyle.selection.setLeftWidth(5.0f);
        selectBoxStyle.listStyle.font = this.font;
        selectBoxStyle.listStyle.background = new Image(new Texture("assets/skin/select-list-bg.png")).getDrawable();
        selectBoxStyle.scrollStyle = new ScrollPane.ScrollPaneStyle();
        selectBoxStyle.background.setLeftWidth(10.0f);
        this.teamType = new SelectBox(selectBoxStyle);
        this.teamType.setSize(150.0f, 44.0f);
        this.teamType.setPosition(640.0f, 105.0f);
        this.resolutionType = new SelectBox(selectBoxStyle);
        this.resolutionType.setSize(150.0f, 44.0f);
        this.resolutionType.setPosition(640.0f, 155.0f);
        this.shipType = new SelectBox(selectBoxStyle);
        this.shipType.setSize(150.0f, 44.0f);
        this.shipType.setPosition(640.0f, 5.0f);
        this.roomLabel = new SelectBox(selectBoxStyle);
        this.roomLabel.setSize(150.0f, 44.0f);
        this.roomLabel.setPosition(640.0f, 55.0f);
        this.baghlah = new Texture("assets/skin/ships/baghlah.png");
        this.blackship = new Texture("assets/skin/ships/blackship.png");
        this.dhow = new Texture("assets/skin/ships/dhow.png");
        this.fanchuan = new Texture("assets/skin/ships/fanchuan.png");
        this.grandfrig = new Texture("assets/skin/ships/grandfrig.png");
        this.junk = new Texture("assets/skin/ships/junk.png");
        this.lgsloop = new Texture("assets/skin/ships/lgsloop.png");
        this.longship = new Texture("assets/skin/ships/longship.png");
        this.merchbrig = new Texture("assets/skin/ships/merchbrig.png");
        this.merchgal = new Texture("assets/skin/ships/merchgal.png");
        this.smsloop = new Texture("assets/skin/ships/smsloop.png");
        this.warbrig = new Texture("assets/skin/ships/warbrig.png");
        this.warfrig = new Texture("assets/skin/ships/warfrig.png");
        this.wargal = new Texture("assets/skin/ships/wargal.png");
        this.xebec = new Texture("assets/skin/ships/xebec.png");
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = this.font;
        labelStyle.fontColor = new Color(0.16f, 0.16f, 0.16f, 1.0f);
        int numLabels = Vessel.VESSEL_TYPES.size() - 1;
        ShipTypeLabel[] blob = new ShipTypeLabel[numLabels];
        blob[0] = new ShipTypeLabel(this.smsloop, Vessel.getIdFromName("smsloop"), (CharSequence)"Sloop", labelStyle);
        blob[1] = new ShipTypeLabel(this.lgsloop, Vessel.getIdFromName("lgsloop"), (CharSequence)"Cutter", labelStyle);
        blob[2] = new ShipTypeLabel(this.dhow, Vessel.getIdFromName("dhow"), (CharSequence)"Dhow", labelStyle);
        blob[3] = new ShipTypeLabel(this.fanchuan, Vessel.getIdFromName("fanchuan"), (CharSequence)"Fanchuan", labelStyle);
        blob[4] = new ShipTypeLabel(this.longship, Vessel.getIdFromName("longship"), (CharSequence)"Longship", labelStyle);
        blob[5] = new ShipTypeLabel(this.junk, Vessel.getIdFromName("junk"), (CharSequence)"Junk", labelStyle);
        blob[6] = new ShipTypeLabel(this.baghlah, Vessel.getIdFromName("baghlah"), (CharSequence)"Baghlah", labelStyle);
        blob[7] = new ShipTypeLabel(this.merchbrig, Vessel.getIdFromName("merchbrig"), (CharSequence)"MB", labelStyle);
        blob[8] = new ShipTypeLabel(this.warbrig, Vessel.getIdFromName("warbrig"), (CharSequence)"War Brig", labelStyle);
        blob[9] = new ShipTypeLabel(this.xebec, Vessel.getIdFromName("xebec"), (CharSequence)"Xebec", labelStyle);
        blob[10] = new ShipTypeLabel(this.merchgal, Vessel.getIdFromName("merchgal"), (CharSequence)"MG", labelStyle);
        blob[11] = new ShipTypeLabel(this.warfrig, Vessel.getIdFromName("warfrig"), (CharSequence)"War Frig", labelStyle);
        blob[12] = new ShipTypeLabel(this.wargal, Vessel.getIdFromName("wargal"), (CharSequence)"War Galleon", labelStyle);
        blob[13] = new ShipTypeLabel(this.grandfrig, Vessel.getIdFromName("grandfrig"), (CharSequence)"Grand Frig", labelStyle);
        this.shipType.setItems(blob);
        ResolutionTypeLabel[] blob3 = new ResolutionTypeLabel[ResolutionTypeLabel.RES_LIST.length];
        for (int i = 0; i < ResolutionTypeLabel.RES_LIST.length; ++i) {
            blob3[i] = new ResolutionTypeLabel(i, ResolutionTypeLabel.RES_LIST[i], labelStyle);
        }
        this.resolutionType.setItems(blob3);
        TeamTypeLabel[] blob2 = new TeamTypeLabel[]{new TeamTypeLabel((CharSequence)"Defender", labelStyle, 1), new TeamTypeLabel((CharSequence)"Attacker", labelStyle, 0)};
        this.teamType.setItems(blob2);
        /*
         * Parse server code/port data for rooms
         */
        try {
			room_info = ConnectScene.getProperty("user.config", "user.room_locations");
			ally_room = Integer.parseInt(ConnectScene.getProperty("user.config", "user.ally_room"));
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
						server_codes.add(temp_room_info[j]);
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Check format");
			e.printStackTrace();
		}
        RoomNumberLabel[] blob_room = new RoomNumberLabel[port_numbers.size()];
        for (int i = 0; i < port_numbers.size(); ++i) {
        	blob_room[i] = new RoomNumberLabel((CharSequence)String.format("%s %d", "Room",i+1), labelStyle, 0);
        	if (i == ally_room - 1) { //Adds allies string to whichever room is specified - for dynamically added rooms
        		blob_room[i] = new RoomNumberLabel((CharSequence)String.format("%s %d (Allies)", "Room",i+1), labelStyle, 0);
        	}
        }
        this.roomLabel.setItems(blob_room);
        try {
            this.shipType.setSelectedIndex(Integer.parseInt(prop.getProperty("user.last_ship")));
        }
        catch (IndexOutOfBoundsException e) {
            this.shipType.setSelectedIndex(Vessel.getIdFromName("warfrig"));
        }
        try {
            this.resolutionType.setSelectedIndex(Integer.parseInt(prop.getProperty("user.last_resolution")));
        }
        catch (IndexOutOfBoundsException e) {
            this.resolutionType.setSelectedIndex(0);
        }
        try {
            this.teamType.setSelectedIndex(Integer.parseInt(prop.getProperty("user.last_team")));
        }
        catch (IndexOutOfBoundsException e) {
            this.teamType.setSelectedIndex(0);
        }
        try {
            this.roomLabel.setSelectedIndex(Integer.parseInt(prop.getProperty("user.last_room_index")));
        }
        catch (IndexOutOfBoundsException e) {
            this.roomLabel.setSelectedIndex(0);
        }
        this.stage.addActor(this.name);
        this.stage.addActor(this.address);
        this.stage.addActor(this.code);
        this.stage.addActor(this.shipType);
        this.stage.addActor(this.resolutionType);
        this.stage.addActor(this.teamType);
        this.stage.addActor(this.roomLabel);
        this.name.addListener(new ChangeListener(){

            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                ConnectScene.this.old_Name = ConnectScene.this.name.getText();
            }
        });
        this.roomLabel.addListener(new ChangeListener(){

            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                try {
                    ConnectScene.this.getServerCode();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.resolutionType.addListener(new ChangeListener(){

            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                try {
                    String last_res = ConnectScene.getProperty("user.config", "user.last_resolution");
                    if (Integer.parseInt(last_res) != ConnectScene.this.resolutionType.getSelectedIndex()) {
                        String[] resolution = ResolutionTypeLabel.restypeToRes(ConnectScene.this.resolutionType.getSelectedIndex());
                        ConnectScene.changeProperty("user.config", "user.width", resolution[0]);
                        ConnectScene.changeProperty("user.config", "user.height", resolution[1]);
                        ConnectScene.changeProperty("user.config", "user.last_resolution", Integer.toString(ConnectScene.this.resolutionType.getSelectedIndex()));
                        ConnectScene.changeProperty("user.config", "user.last_room_index", Integer.toString(ConnectScene.this.roomLabel.getSelectedIndex()));
                        ConnectScene.changeProperty("user.config", "user.last_team", Integer.toString(ConnectScene.this.teamType.getSelectedIndex()));
                        Gdx.graphics.setWindowedMode(Integer.parseInt(resolution[0]), Integer.parseInt(resolution[1]));
                        ConnectScene.this.context.create();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getServerCode() {
    	
        if (this.roomLabel.getSelectedIndex() < port_numbers.size()) { //sanity check
        	System.out.println("Room " + (this.roomLabel.getSelectedIndex() + 1) + " Selected.");
            Constants.PROTOCOL_PORT = Integer.parseInt(port_numbers.get(this.roomLabel.getSelectedIndex()));
            Constants.SERVER_CODE = Integer.parseInt(server_codes.get(this.roomLabel.getSelectedIndex()));
            this.code.setText(Integer.toString(Constants.SERVER_CODE));
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void render() {
        this.stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        this.batch.begin();
        this.batch.draw(this.background, 0.0f, 0.0f);
        if (this.state == ConnectionSceneState.DEFAULT) {
            this.titleFont.draw((Batch)this.batch, "Blockade Simulator", 156.0f, 470.0f);
            this.notesFont.draw((Batch)this.batch, this.chosenGreeting, 587.0f, 449.0f);
            this.notesFont.draw((Batch)this.batch, "Version 1.9.9 by Cyclist, based on the Cadesim by Benberi", 15.0f, 75.0f);
            this.notesFont.draw((Batch)this.batch, "Inspired by the original Dachimpy Cadesim", 15.0f, 50.0f);
            this.notesFont.draw((Batch)this.batch, "Found a bug? Let us know!", 15.0f, 25.0f);
            if (this.codeURL) {
                this.notesFont.setColor(Color.SKY);
            }
            this.notesFont.draw((Batch)this.batch, this.code_url, 138.0f, 25.0f);
            this.notesFont.setColor(Color.WHITE);
            this.font.setColor(Color.WHITE);
            this.font.draw((Batch)this.batch, "Display name:", 160.0f, 420.0f);
            this.font.draw((Batch)this.batch, "Server address:", 316.0f, 420.0f);
            this.font.draw((Batch)this.batch, "Server code:", 472.0f, 420.0f);
            this.batch.draw(this.textfieldTexture, 160.0f, 345.0f, 140.0f, 49.0f);
            this.batch.draw(this.textfieldTexture, 316.0f, 345.0f, 140.0f, 49.0f);
            this.batch.draw(this.textfieldTexture, 472.0f, 345.0f, 140.0f, 49.0f);
            this.font.draw((Batch)this.batch, "Settings:", 640.0f, 225.0f);
            this.batch.draw(this.loginHover ? this.loginButton : this.loginButtonHover, 165.0f, 290.0f);
            this.font.setColor(new Color(0.1f, 0.1f, 0.1f, 1.0f));
            this.font.draw((Batch)this.batch, "Connect", 340.0f, 316.0f);
            this.batch.end();
            this.stage.act();
            this.stage.draw();
            this.batch.begin();
            Texture t = this.shipType.getSelected().getType();
            this.batch.draw(t, 735.0f, 5.0f);
            this.batch.end();
            if (this.popup) {
                Gdx.gl.glEnable(3042);
                Gdx.gl.glBlendFunc(770, 771);
                this.renderer.begin(ShapeRenderer.ShapeType.Filled);
                this.renderer.setColor(new Color(0.0f, 0.0f, 0.0f, 0.9f));
                this.renderer.rect(0.0f, 0.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                GlyphLayout layout = new GlyphLayout(this.font, this.popupMessage);
                int x = Gdx.graphics.getWidth() / 2 - 200;
                int y = Gdx.graphics.getHeight() / 2 - 50;
                int width = 400;
                int height = 50;
                this.renderer.setColor(new Color(0.8352941f, 0.21176471f, 0.20784314f, 1.0f));
                this.renderer.rect(x, y, width, height);
                if (this.popupCloseHover) {
                    this.renderer.setColor(new Color(0.98039216f, 0.3647059f, 0.3647059f, 1.0f));
                } else {
                    this.renderer.setColor(new Color(0.6666667f, 0.15294118f, 0.15294118f, 1.0f));
                }
                this.renderer.rect(x + 330, y, 70.0f, 50.0f);
                this.renderer.end();
                this.batch.begin();
                this.font.setColor(Color.WHITE);
                this.font.draw((Batch)this.batch, this.popupMessage, (float)x + (200.0f - layout.width / 2.0f) - 30.0f, (float)y + (25.0f + layout.height / 2.0f));
                this.font.draw((Batch)this.batch, "Close", (float)(x + 400 - 55), (float)y + (25.0f + layout.height / 2.0f));
                this.batch.end();
            }
        } else {
            String dot = "";
            if (this.connectAnimationState == 0) {
                dot = ".";
            } else if (this.connectAnimationState == 1) {
                dot = "..";
            } else if (this.connectAnimationState == 2) {
                dot = "...";
            }
            this.font.setColor(Color.YELLOW);
            String text = "(" + (System.currentTimeMillis() - this.loginAttemptTimestampMillis) + "ms) ";
            if (this.state == ConnectionSceneState.CONNECTING) {
                text = String.valueOf(text) + "Connecting, please wait";
            } else if (this.state == ConnectionSceneState.CREATING_PROFILE) {
                text = String.valueOf(text) + "Connected - creating profile";
            } else if (this.state == ConnectionSceneState.CREATING_MAP) {
                text = String.valueOf(text) + "Connected - waiting for board map update";
            }
            GlyphLayout layout = new GlyphLayout(this.font, text);
            this.font.draw((Batch)this.batch, String.valueOf(text) + dot, (float)(Gdx.graphics.getWidth() / 2) - layout.width / 2.0f, 300.0f);
            if (System.currentTimeMillis() - this.lastConnectionAnimatinoStateChange >= 200L) {
                ++this.connectAnimationState;
                this.lastConnectionAnimatinoStateChange = System.currentTimeMillis();
            }
            if (this.connectAnimationState > 2) {
                this.connectAnimationState = 0;
            }
            this.batch.end();
        }
    }

    public void setPopup(String message) {
        this.popup = true;
        this.popupMessage = message;
        this.name.setDisabled(true);
        this.address.setDisabled(true);
    }

    public void closePopup() {
        this.popup = false;
        this.name.setDisabled(false);
        this.address.setDisabled(false);
    }

    @Override
    public void dispose() {
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
        if (keycode == 66 || keycode == 23) {
            if (!this.popup) {
                if (this.stage.getKeyboardFocus() != this.name && this.name.getText().isEmpty()) {
                    this.stage.setKeyboardFocus(this.name);
                } else if (this.stage.getKeyboardFocus() != this.address && this.address.getText().isEmpty()) {
                    this.stage.setKeyboardFocus(this.address);
                } else {
                    try {
                        this.performLogin();
                    }
                    catch (UnknownHostException e) {
                        return this.failed;
                    }
                }
            } else {
                this.closePopup();
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
        if (this.isMouseOverCodeUrl(screenX, Gdx.graphics.getHeight() - screenY)) {
            try {
                Desktop.getDesktop().browse(URI.create(this.code_url));
            }
            catch (IOException iOException) {
                // empty catch block
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (this.popup && this.popupCloseHover) {
            this.closePopup();
        } else if (this.loginHover) {
            try {
                this.performLogin();
            }
            catch (UnknownHostException e) {
                return this.failed;
            }
        }
        return false;
    }

    private void performLogin() throws UnknownHostException {
        System.out.println("Port number: " + Constants.PROTOCOL_PORT);
        System.out.println("Server number: " + Constants.SERVER_CODE);
        this.loginAttemptTimestampMillis = System.currentTimeMillis();
        if (this.name.getText().length() > 19) {
            this.setPopup("Display name must be less than 19 letters.");
        } else if (this.code.getText().length() > 30) {
            this.setPopup("Server code must be less than 30 letters.");
        } else if (this.name.getText().length() <= 0) {
            this.setPopup("Please enter a display name.");
        } else if (this.address.getText().length() <= 0) {
            this.setPopup("Please enter an IP Address.");
        } else if (!RandomUtils.validIP(this.address.getText()) && !RandomUtils.validUrl(this.address.getText())) {
            this.setPopup("Please enter a valid IP Address or URL.");
        } else {
            try {
                String[] resolution = ResolutionTypeLabel.restypeToRes(this.resolutionType.getSelectedIndex());
                ConnectScene.changeProperty("user.config", "user.username", this.name.getText());
                ConnectScene.changeProperty("user.config", "user.last_address", this.address.getText());
                ConnectScene.changeProperty("user.config", "user.last_room_index", Integer.toString(ConnectScene.this.roomLabel.getSelectedIndex()));
                ConnectScene.changeProperty("user.config", "user.width", resolution[0]);
                ConnectScene.changeProperty("user.config", "user.height", resolution[1]);
                ConnectScene.changeProperty("user.config", "user.last_ship", Integer.toString(this.shipType.getSelectedIndex()));
                ConnectScene.changeProperty("user.config", "user.last_resolution", Integer.toString(this.resolutionType.getSelectedIndex()));
                ConnectScene.changeProperty("user.config", "user.last_team", Integer.toString(this.teamType.getSelectedIndex()));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            this.setState(ConnectionSceneState.CONNECTING);
            this.context.connect(this.name.getText(), this.address.getText(), this.code.getText(), this.shipType.getSelected().getIndex(), this.teamType.getSelected().getType());
        }
    }

    public static void changeProperty(String filename, String key, String value) throws IOException {
        Properties prop = new Properties();
        prop.load(new FileInputStream(filename));
        prop.setProperty(key, value);
        prop.store(new FileOutputStream(filename), null);
    }

    public static String getProperty(String filename, String key) throws IOException {
        Properties prop = new Properties();
        prop.load(new FileInputStream(filename));
        return prop.getProperty(key);
    }

    public boolean isMouseOverCodeUrl(float x, float y) {
        return x >= 138.0f && y >= 10.0f && x < 447.0f && y < 31.0f;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        if (this.popup) {
            int popupleftedge = width / 2 - 200;
            int popuprightedge = width / 2 + 200;
            int popuptopedge = height / 2;
            int popupbottomedge = height / 2 + 50;
            this.loginHover = false;
            this.popupCloseHover = screenX >= popupleftedge && screenX <= popuprightedge && screenY >= popuptopedge && screenY <= popupbottomedge;
        } else {
            this.codeURL = this.isMouseOverCodeUrl(screenX, height - screenY);
            this.loginHover = screenX >= 164 && screenX <= 606 && screenY >= height - 330 && screenY <= height - 290;
        }
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void loginFailed() {
        this.setPopup("Could not connect to server.");
    }

    public void setState(ConnectionSceneState state) {
        this.state = state;
    }

    public void setup() {
        this.setState(ConnectionSceneState.DEFAULT);
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(this.stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public boolean hasPopup() {
        return this.popup;
    }
}
