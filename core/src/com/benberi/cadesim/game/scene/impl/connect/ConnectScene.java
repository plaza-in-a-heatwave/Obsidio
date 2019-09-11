package com.benberi.cadesim.game.scene.impl.connect;

import java.io.*;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

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

public class ConnectScene implements GameScene, InputProcessor {

    private GameContext context;

    private ConnectionSceneState state = ConnectionSceneState.DEFAULT;

    /**
     * Batch for opening screen
     */
    private SpriteBatch batch;

    /**
     * The shape renderer
     */
    private ShapeRenderer renderer;

    /**
     * Background texture
     */
    private Texture background;

    private int connectAnimationState = 0;

    private long lastConnectionAnimatinoStateChange;

    private BitmapFont font;
    private BitmapFont titleFont;
    private BitmapFont notesFont;
    
    // connectscene
    private ArrayList<String> greetings = new ArrayList<String>();
    private java.util.Random prng = new java.util.Random(System.currentTimeMillis());
    private String chosenGreeting;
    private String code_url = "https://github.com/plaza-in-a-heatwave/Obsidio/issues";

    private boolean failed;

    /**
     * The main stage for elements
     */
    private Stage stage;

    /**
     * The username textfield
     */
    private TextField name;

    /**
     * The address textfield
     */
    private TextField address;

    /**
     * Username textfield texture
     */
    private Texture nameTexture;

    private SelectBox<ShipTypeLabel> shipType;
    private SelectBox<ResolutionTypeLabel> resolutionType;
    private SelectBox<TeamTypeLabel> teamType;

    /**
     * The address textfield texture
     */
    private Texture addressTexture;

    /**
     * The login button texture
     */
    private Texture loginButton;

    private Texture loginButtonHover;

    private Texture junk;
    private Texture wf;
    private Texture xebec;
    private Texture wg;
    private Texture wb;

    /**
     * If a popup is open
     */
    private boolean popup;

    private boolean loggingIn;


    /**
     * The popup message
     */
    private String popupMessage;

    private boolean popupCloseHover;
    private boolean loginHover;
    private boolean codeURL;
    private boolean validating;

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
        greetings.add("Praise Cyclist!");
        greetings.add("Home grown!");
        greetings.add("Blub");
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
        greetings.add("Brigging Back Blockades");
        greetings.add("micro/nano Blockade SIMs available!");
        chosenGreeting = greetings.get(prng.nextInt(greetings.size()));
        
        batch = new SpriteBatch();

        background = new Texture("assets/bg.png");
        nameTexture = new Texture("assets/skin/textfield-name.png");
        addressTexture = new Texture("assets/skin/textfield-address.png");
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
        name.setSize(160, 49);
        name.setPosition(170, 225);

        address = new TextField( prop.getProperty("user.last_address"), style);
        address.setSize(225, 49);
        address.setPosition(370, 225);

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


        shipType = new SelectBox<>(selectBoxStyle);
        shipType.setSize(150, 44);
        shipType.setPosition(640, 225);
        
        resolutionType = new SelectBox<>(selectBoxStyle);
        resolutionType.setSize(150, 44);
        resolutionType.setPosition(640, 175);

        teamType = new SelectBox<>(selectBoxStyle);
        teamType.setSize(150, 44);
        teamType.setPosition(640, 125);

        junk = new Texture("assets/skin/ships/junk.png");
        wb = new Texture("assets/skin/ships/wb.png");
        xebec = new Texture("assets/skin/ships/xebec.png");
        wg = new Texture("assets/skin/ships/wg.png");
        wf = new Texture("assets/skin/ships/wf.png");

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = new Color(0.16f, 0.16f, 0.16f, 1);

        ShipTypeLabel[] blob = new ShipTypeLabel[5];
        blob[0] = new ShipTypeLabel(ShipTypeLabel.JUNK,"Junk", labelStyle);
        blob[1] = new ShipTypeLabel(ShipTypeLabel.WB,"WB",labelStyle);
        blob[2] = new ShipTypeLabel(ShipTypeLabel.XEBEC,"Xebec", labelStyle);
        blob[3] = new ShipTypeLabel(ShipTypeLabel.WG,"WG", labelStyle);
        blob[4] = new ShipTypeLabel(ShipTypeLabel.WF,"WF", labelStyle);
        
        shipType.setItems(blob);
        shipType.setSelectedIndex(Integer.parseInt(prop.getProperty("user.last_ship")));

        ResolutionTypeLabel[] blob3 = new ResolutionTypeLabel[5];
        blob3[0] = new ResolutionTypeLabel(ResolutionTypeLabel.defaultsize,"720p", labelStyle);
        blob3[1] = new ResolutionTypeLabel(ResolutionTypeLabel.eighthundred,"800x600", labelStyle);
        blob3[2] = new ResolutionTypeLabel(ResolutionTypeLabel.teneighty,"1080p", labelStyle);
        blob3[3] = new ResolutionTypeLabel(ResolutionTypeLabel.fourteenforty,"1440p", labelStyle);
        blob3[4] = new ResolutionTypeLabel(ResolutionTypeLabel.fourk,"4K", labelStyle);

        // TODO (src: https://www.rapidtables.com/web/dev/screen-resolution-statistics.html)
        // TODO so we should support: (v == have)
        // can have 11 max
        // 800x600   // v
        // 1024x768
        // 1280x800
        // 1280x1024
        // 1366x768
        // 1440x900
        // 1600x900
        // 1680x1050
        // 1920x1080
        // 2500x1400 // v
        // 3600x2000 // v
        
        resolutionType.setItems(blob3);
        resolutionType.setSelectedIndex(Integer.parseInt(prop.getProperty("client.last_resolution")));;

        
        TeamTypeLabel[] blob2 = new TeamTypeLabel[2];
        blob2[0] = new TeamTypeLabel("Green", labelStyle);
        blob2[1] = new TeamTypeLabel("Red" ,labelStyle);
        
        teamType.setItems(blob2);

        stage.addActor(name);
        stage.addActor(address);
        stage.addActor(address);
        stage.addActor(shipType);
        stage.addActor(resolutionType);
        stage.addActor(teamType);

        resolutionType.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // if graphics state not what it was, reload graphics
                try {
                    String last_res = getProperty("user.config", "client.last_resolution");

                    if (Integer.parseInt(last_res) != resolutionType.getSelectedIndex()) {
                        String[] resolution = restypeToRes(resolutionType.getSelectedIndex());

                        // save for next time
                        changeProperty("user.config", "client.width", resolution[0]);
                        changeProperty("user.config", "client.height", resolution[1]);
                        changeProperty("user.config", "client.last_resolution", Integer.toString(resolutionType.getSelectedIndex()));

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

    @Override
    public void update() {

    }

    @Override
    public void render() {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        batch.begin();
        batch.draw(background, 0, 0);
        if (state == ConnectionSceneState.DEFAULT) {

        	titleFont.draw(batch, "Blockade Simulator", 156, 350);
        	notesFont.draw(batch, chosenGreeting, 587, 329);
        	notesFont.draw(batch, "Based on the original by Benberi", 15, 50);
        	notesFont.draw(batch, "Found a bug? Let us know!", 15, 25);
        	
        	if (codeURL) { notesFont.setColor(Color.SKY); }
        	notesFont.draw(batch, code_url, 138, 25);
            notesFont.setColor(Color.WHITE);
        	
            font.setColor(Color.WHITE);
            font.draw(batch, "Display name:", 160, 300);
            batch.draw(nameTexture, 160, 225);
            font.draw(batch, "IP Address:", 360, 300);
            batch.draw(addressTexture, 360, 225);

            if (!loginHover) {
                batch.draw(loginButton, 165, 170);
            } else {
                batch.draw(loginButtonHover, 165, 170);
            }
            font.setColor(new Color(0.1f, 0.1f, 0.1f, 1));


            font.draw(batch, "Connect", 340, 196);
            batch.end();

            // buttons need to be drawn, then ship texture is drawn over them
            stage.act();
            stage.draw();
            
            Texture t;
            batch.begin();
            switch (shipType.getSelected().getType()) {
                case ShipTypeLabel.JUNK:  t = junk;  break;
                case ShipTypeLabel.WB:    t = wb;    break;
                case ShipTypeLabel.XEBEC: t = xebec; break;
                case ShipTypeLabel.WG:    t = wg;    break;
                case ShipTypeLabel.WF:    t = wf;    break;
                default:                  t = wf;    break;
            }
            batch.draw(t, 735, 225); // draw t, whatever it may be
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
                String text = "";

                if (state == ConnectionSceneState.CREATING_PROFILE) {
                    text = "Creating profile";
                }
                else if (state == ConnectionSceneState.CONNECTING) {
                    text = "Connecting, please wait";
                }
                else if (state == ConnectionSceneState.CREATING_MAP) {
                    text = "Waiting for board map update";
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


        batch.begin();
            font.setColor(Color.YELLOW);
            font.draw(batch, Constants.name + " (version " + Constants.VERSION + ")", 15, Gdx.graphics.getHeight() - 20);
            batch.end();

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
        if (name.getText().length() > Constants.MAX_NAME_SIZE) {
            setPopup("Display name must be shorter.");
        }
        else if (name.getText().length() <= 0) {
            setPopup("Please enter a display name");
        }
        else if (address.getText().length() <= 0) {
            setPopup("Please enter an IP Address");
        }
        else if (!RandomUtils.validIP(address.getText()) && !RandomUtils.validUrl(address.getText())) {
            setPopup("Please enter a valid IP Address or url");
        }
        else {
            // Save current choices for next time
            try {
                String[] resolution = restypeToRes(resolutionType.getSelectedIndex());
                changeProperty("user.config", "user.username", name.getText());
                changeProperty("user.config", "user.last_address", address.getText());
                changeProperty("user.config", "client.width", resolution[0]);
                changeProperty("user.config", "client.height", resolution[1]);
                changeProperty("user.config", "user.last_ship", Integer.toString(shipType.getSelectedIndex()));
                changeProperty("user.config", "client.last_resolution", Integer.toString(resolutionType.getSelectedIndex()));
                
            } catch (IOException e) {
                e.printStackTrace();
            }

            setState(ConnectionSceneState.CONNECTING);
            context.connect(name.getText(), address.getText(), shipType.getSelected().getType(), teamType.getSelected().getType());
        }
    }

    String[] restypeToRes(int restype) {
        String[] resolution = new String[2];
        switch (restype) {
        case 0:
            resolution[0] = "1240";
            resolution[1] = "680";
            break;
        case 1:
            resolution[0] = "800";
            resolution[1] = "600";
            break;
        case 2:
            resolution[0] = "1800";
            resolution[1] = "1000";
            break;
        case 3:
            resolution[0] = "2500";
            resolution[1] = "1400";
            break;
        case 4:
            resolution[0] = "3600";
            resolution[1] = "2000";
            break;
        default:
            // safe default
            resolution[0] = "800";
            resolution[1] = "600";
            break;
        }

        return resolution;
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
    	return x >= 138 && x < 415 && y >= 10 && y < 31;
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
            loginHover = screenX >= 164 && screenX <= 606 && screenY >= height - 210 && screenY <= height - 170;
        }
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void loginFailed() {
        loggingIn = false;
        setPopup("Could not connect to server.");
    }

    public void setValidating(boolean validating) {
        this.validating = validating;
        this.loggingIn = validating;
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
