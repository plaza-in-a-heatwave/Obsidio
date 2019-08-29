package com.benberi.cadesim.game.scene.impl.control;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.math.Vector2;
import com.benberi.cadesim.GameContext;
import com.benberi.cadesim.game.entity.vessel.move.MoveType;
import com.benberi.cadesim.game.scene.SceneComponent;
import com.benberi.cadesim.game.scene.impl.control.hand.HandMove;
import com.benberi.cadesim.game.scene.impl.control.hand.impl.BigShipHandMove;
import com.benberi.cadesim.game.scene.impl.control.hand.impl.SmallShipHandMove;

public class BattleControlComponent extends SceneComponent<ControlAreaScene> implements InputProcessor {
    /**
     * for copy/paste
     */
    Toolkit toolkit;
    Clipboard clipboard;

    /**
     * The context
     */
    private GameContext context;

    /**
     * Left moves
     */
    private int leftMoves = 2;

    /**
     * Right moves
     */
    private int rightMoves = 4;

    /**
     * Forward moves
     */
    private int forwardMoves;

    /**
     * The available shoots
     */
    private int cannons = 0;

    /**
     * The selected moves
     */
    private HandMove[] movesHolder;

    /**
     * Radio button states
     */
    private boolean[] radioButtons = new boolean[3];

    private void enableRadio(int radio) {
        for (int i=0; i<radioButtons.length; i++) {
            if (i == radio) {
                radioButtons[i] = true;
            } else {
                radioButtons[i] = false;
            }
        }
    }

    /**
     * Batch renderer for sprites and textures
     */
    private SpriteBatch batch;

    /**
     * Shape renderer for shapes, used for damage/bilge and such
     */
    private ShapeRenderer shape;

    /**
     * Font for texts/messages
     */
    private BitmapFont font;
    private BitmapFont messageFont;

    /**
     * The target move
     */
    private MoveType targetMove = MoveType.RIGHT;

    /**
     * The damage of the vessel
     */
    private float damageAmount;

    /**
     * The bilge of the vessel
     */
    private float bilgeAmount;

    /**
     * If the move selection is automatic or not
     */
    private boolean auto = true;

    /**
     * The turn time
     */
    private int time = 0;

    /**
     * modifier to calculate button placement
     */
    int absheight = Gdx.graphics.getHeight(); // absolute height

    /**
     * TextField for Chat (with its own Stage)
     */
    private Stage stage;
    private TextField chatBar;

    /**
     * helpers for Textfield key processing
     */
    private int  keyDown     = -1;  // keycode if down? (or -1 for none)
    private int characterDown = -1; // charactercode if down? (or -1 for none)
    private long keyDownTimeMillis = 0;  // when did key go down?
    private boolean modifierDown = false; // ctrl or mac key
    private boolean handledChord = false; // did we handle a modifier chord?
    private static final int KEY_REPEAT_THRESHOLD_MILLIS = 500;
    private void doAccelerate() {
        if ((System.currentTimeMillis() - keyDownTimeMillis) >= KEY_REPEAT_THRESHOLD_MILLIS) {
            if (keyDown != -1) {
                handleAcceleratableKeys(keyDown);
            }
            else if (characterDown != -1) {
                handleChar((char)characterDown);
            }
        }
    }
    private void startKeyAcceleration(int keycode) {
        // use when a key has been pushed down once.
        // it schedules acceleration.
        if (!modifierDown) {
            keyDown = keycode;
            keyDownTimeMillis = System.currentTimeMillis();
            characterDown = -1;
        }
    }
    private void startCharacterAcceleration(char character) {
        // use when a charater has been pushed down once.
        // it schedules acceleration.
        if (!modifierDown) {
            characterDown = character;
            keyDownTimeMillis = System.currentTimeMillis();
            keyDown = -1;
        }
    }
    private void stopAllAcceleration() {
        // use when a key up has been detected
        if (!modifierDown) {
            keyDown = -1;
            characterDown = -1;
            keyDownTimeMillis = System.currentTimeMillis();
        }
    }
    private boolean isAcceleratingCharacter(char character) {
        if (modifierDown) {
            return false;
        }
        else if (characterDown == -1) {
            return false;
        }
        else {
            return character == characterDown;
        }
    }
    private void startModifier() {
        modifierDown = true;
        handledChord = false;
        keyDown = -1;
        characterDown = -1;
        keyDownTimeMillis = System.currentTimeMillis();
    }
    private boolean isModifying() {
        return modifierDown;
    }
    private void stopModifier() {
        modifierDown = false;
    }

    /**
     * add to the chat buffer
     */
    public void addNewMessage(String sender, String message) {
        this.displayMessage(sender + " says, \"" + message + "\".");
    }

    /**
     * Textures
     */
    private Texture shiphand;
    private Texture moves;
    private Texture emptyMoves;
    private TextureRegion leftMoveTexture;
    private TextureRegion rightMoveTexture;
    private TextureRegion forwardMoveTexture;
    private TextureRegion manuaverTexture; // for ships that only are 3 moves
    private TextureRegion emptyLeftMoveTexture;
    private TextureRegion emptyRightMoveTexture;
    private TextureRegion emptyForwardMoveTexture;
    private Texture sandTopTexture;
    private Texture sandBottomTexture;
    private TextureRegion sandBottom;
    private TextureRegion sandTop;

    private TextureRegion emptyCannon;
    private TextureRegion cannon;

    private Texture sandTrickleTexture;
    private TextureRegion sandTrickle;

    private Texture hourGlass;
    private Texture cannonSlots;
    private TextureRegion cannonLeft;
    private TextureRegion cannonRight;
    private TextureRegion emptyCannonLeft;
    private TextureRegion emptyCannonRight;
    private Texture controlBackground;
    private Texture goOceansideBackground;
    private Texture chatBackground;
    private Texture shipStatus;
    private Texture shipStatusBg;
    private TextureRegion damage;
    private TextureRegion bilge;
    private Texture moveGetTargetTexture;
    private TextureRegion moveTargetSelAuto;
    private TextureRegion moveTargetSelForce;
    private Texture title;
    private Texture radioOn;
    private Texture radioOff;
    private Texture radioOnDisable;
    private Texture radioOffDisable;
    private Texture autoOn;
    private Texture autoOff;
    private Texture autoBackground;

    private Texture cannonSelection;
    private Texture cannonSelectionEmpty;

    private Texture goOceansideUp;
    private Texture goOceansideDown;

    private Texture chatIndicator;
    private Texture chatBarBackground;
    private Texture chatButtonSend;
    private Texture chatButtonSendPressed;

    private Texture chatScrollBarUp;
    private Texture chatScrollBarUpPressed;
    private Texture chatScrollBarDown;
    private Texture chatScrollBarDownPressed;
    private Texture chatScrollBarMiddle;
    private Texture chatScrollBarScroll;
    
    // references for drawing chat/scroll
    Container<Table> chatContainer;
    Label.LabelStyle chatLabelStyle;
    Table chatTable;
    float containerTopY;
    float containerBottomY;
    

    // reference coords - MOVES control
    private int MOVES_REF_X             = 0;
    private int MOVES_REF_Y             = 75;

    private int MOVES_backgroundX       = MOVES_REF_X + 5;
    private int MOVES_backgroundY       = MOVES_REF_Y - 67;

    private int MOVES_titleX            = MOVES_REF_X + 68;
    private int MOVES_titleY            = MOVES_REF_Y + 76;

    private int MOVES_autoX             = MOVES_REF_X + 60;
    private int MOVES_autoY             = MOVES_REF_Y + 33;

    private int MOVES_autoBackgroundX   = MOVES_REF_X + 77;
    private int MOVES_autoBackgroundY   = MOVES_REF_Y + 28;

    private int MOVES_autoTextX         = MOVES_REF_X + 29;
    private int MOVES_autoTextY         = MOVES_REF_Y + 46; // text from top edge

    private int MOVES_cannonsX          = MOVES_REF_X + 50;
    private int MOVES_cannonsY          = MOVES_REF_Y + 3;

    private int MOVES_cannonsTextX      = MOVES_REF_X + 57;
    private int MOVES_cannonsTextY      = MOVES_REF_Y - 4;  // text from top edge

    private int MOVES_leftX             = MOVES_REF_X + 82;
    private int MOVES_forwardX          = MOVES_REF_X + 114;
    private int MOVES_rightX            = MOVES_REF_X + 146;
    private int MOVES_leftY             = MOVES_REF_Y + 0;
    private int MOVES_forwardY          = MOVES_REF_Y + 0;
    private int MOVES_rightY            = MOVES_REF_Y + 0;

    private int MOVES_leftRadioX        = MOVES_REF_X + 90;
    private int MOVES_forwardRadioX     = MOVES_REF_X + 122;
    private int MOVES_rightRadioX       = MOVES_REF_X + 154;
    private int MOVES_leftRadioY        = MOVES_REF_Y + 36;
    private int MOVES_forwardRadioY     = MOVES_REF_Y + 36;
    private int MOVES_rightRadioY       = MOVES_REF_Y + 36;

    private int MOVES_leftSelectX       = MOVES_REF_X + 78;
    private int MOVES_forwardSelectX    = MOVES_REF_X + 110;
    private int MOVES_rightSelectX      = MOVES_REF_X + 142;
    private int MOVES_leftSelectY       = MOVES_REF_Y - 4;
    private int MOVES_forwardSelectY    = MOVES_REF_Y - 4;
    private int MOVES_rightSelectY      = MOVES_REF_Y - 4;

    private int MOVES_leftMovesTextX    = MOVES_REF_X + 90;
    private int MOVES_forwardMovesTextX = MOVES_REF_X + 122;
    private int MOVES_rightMovesTextX   = MOVES_REF_X + 154;
    private int MOVES_leftMovesTextY    = MOVES_REF_Y - 4; // text from top edge
    private int MOVES_forwardMovesTextY = MOVES_REF_Y - 4; // "
    private int MOVES_rightMovesTextY   = MOVES_REF_Y - 4; // "

    private int MOVES_shiphandX         = MOVES_REF_X + 200;
    private int MOVES_shiphandY         = MOVES_REF_Y - 57;

    // general moveSlot
    private int MOVES_moveSlotX             = MOVES_REF_X + 216;

    // specific moveSlot
    private int MOVES_moveSlot0X            = MOVES_moveSlotX;
    private int MOVES_moveSlot0Y            = MOVES_REF_Y + 54;
    private int MOVES_moveSlot1X            = MOVES_moveSlotX;
    private int MOVES_moveSlot1Y            = MOVES_REF_Y + 20;
    private int MOVES_moveSlot2X            = MOVES_moveSlotX;
    private int MOVES_moveSlot2Y            = MOVES_REF_Y - 14;
    private int MOVES_moveSlot3X            = MOVES_moveSlotX;
    private int MOVES_moveSlot3Y            = MOVES_REF_Y - 48;

    // general cannons
    private int MOVES_cannonLeftSlotBigX    = MOVES_REF_X + 184;
    private int MOVES_cannonRightSlotSmallX = MOVES_REF_X + 245;
    private int MOVES_cannonLeftSlotSmallX  = MOVES_cannonLeftSlotBigX + 15;
    private int MOVES_cannonRightSlotBigX   = MOVES_cannonRightSlotSmallX + 15;
    private int MOVES_cannonSlot0Y          = MOVES_REF_Y + 59;
    private int MOVES_cannonSlot1Y          = MOVES_REF_Y + 25;
    private int MOVES_cannonSlot2Y          = MOVES_REF_Y - 9;
    private int MOVES_cannonSlot3Y          = MOVES_REF_Y - 43;

    // specific cannons
    private int MOVES_cannonLeftSlot0X      = MOVES_cannonLeftSlotBigX;
    private int MOVES_cannonLeftSlot0Y      = MOVES_cannonSlot0Y;
    private int MOVES_cannonRightSlot0X     = MOVES_cannonRightSlotSmallX;
    private int MOVES_cannonRightSlot0Y     = MOVES_cannonSlot0Y;
    private int MOVES_cannonLeftSlot1X      = MOVES_cannonLeftSlotBigX;
    private int MOVES_cannonLeftSlot1Y      = MOVES_cannonSlot1Y;
    private int MOVES_cannonRightSlot1X     = MOVES_cannonRightSlotSmallX;
    private int MOVES_cannonRightSlot1Y     = MOVES_cannonSlot1Y;
    private int MOVES_cannonLeftSlot2X      = MOVES_cannonLeftSlotBigX;
    private int MOVES_cannonLeftSlot2Y      = MOVES_cannonSlot2Y;
    private int MOVES_cannonRightSlot2X     = MOVES_cannonRightSlotSmallX;
    private int MOVES_cannonRightSlot2Y     = MOVES_cannonSlot2Y;
    private int MOVES_cannonLeftSlot3X      = MOVES_cannonLeftSlotBigX;
    private int MOVES_cannonLeftSlot3Y      = MOVES_cannonSlot3Y;
    private int MOVES_cannonRightSlot3X     = MOVES_cannonRightSlotSmallX;
    private int MOVES_cannonRightSlot3Y     = MOVES_cannonSlot3Y;

    // hourglass
    int MOVES_hourGlassX     = MOVES_REF_X + 290;
    int MOVES_hourGlassY     = MOVES_REF_Y - 52;
    int MOVES_sandTrickleX   = MOVES_REF_X + 303;
    int MOVES_sandTrickleY   = MOVES_REF_Y - 47;
    int MOVES_sandTopX       = MOVES_REF_X + 294;
    int MOVES_sandTopY       = MOVES_REF_Y - 5;
    int MOVES_sandBottomX    = MOVES_REF_X + 294;
    int MOVES_sandBottomY    = MOVES_REF_Y - 49;

    // ship status/ship status background
    int MOVES_shipStatusBackgroundX = MOVES_REF_X + 282;
    int MOVES_shipStatusBackgroundY = MOVES_REF_Y + 47;
    int MOVES_shipStatusX           = MOVES_shipStatusBackgroundX;
    int MOVES_shipStatusY           = MOVES_shipStatusBackgroundY;
    int MOVES_shipDamageX           = MOVES_shipStatusBackgroundX + 20;
    int MOVES_shipDamageY           = MOVES_REF_Y + 69;
    int MOVES_shipBilgeX            = MOVES_shipStatusBackgroundX + 23;
    int MOVES_shipBilgeY            = MOVES_REF_Y + 69;

    // MOVES shapes
    Rectangle MOVES_shape_auto                = new Rectangle(MOVES_autoX,             MOVES_autoY,             17, 17);
    Rectangle MOVES_shape_placingLeftCannons  = new Rectangle(MOVES_cannonLeftSlot3X,  MOVES_cannonLeftSlot3Y - 5,  32, 130);
    Rectangle MOVES_shape_placingRightCannons = new Rectangle(MOVES_cannonRightSlot3X, MOVES_cannonLeftSlot3Y - 5,  32, 130);

    Rectangle MOVES_shape_moveSlot0           = new Rectangle(MOVES_moveSlot0X,        MOVES_moveSlot0Y,        28, 28);
    Rectangle MOVES_shape_moveSlot1           = new Rectangle(MOVES_moveSlot1X,        MOVES_moveSlot1Y,        28, 28);
    Rectangle MOVES_shape_moveSlot2           = new Rectangle(MOVES_moveSlot2X,        MOVES_moveSlot2Y,        28, 28);
    Rectangle MOVES_shape_moveSlot3           = new Rectangle(MOVES_moveSlot3X,        MOVES_moveSlot3Y,        28, 28);

    Rectangle MOVES_shape_leftToken           = new Rectangle(MOVES_leftX,             MOVES_leftY,             28, 28);
    Rectangle MOVES_shape_forwardToken        = new Rectangle(MOVES_forwardX,          MOVES_forwardY,          28, 28);
    Rectangle MOVES_shape_rightToken          = new Rectangle(MOVES_rightX,            MOVES_rightY,            28, 28);

    // cannon hitboxes have Y -/+ 5 because in an actual Sea Battle you can
    // load a gun by clicking on a 28x28 square around it (regardless of
    // whether it's a large/small ship).
    Rectangle MOVES_shape_cannonLeftSlot0     = new Rectangle(MOVES_cannonLeftSlot0X,  MOVES_cannonLeftSlot0Y  - 5, 32, 18+10);
    Rectangle MOVES_shape_cannonLeftSlot1     = new Rectangle(MOVES_cannonLeftSlot1X,  MOVES_cannonLeftSlot1Y  - 5, 32, 18+10);
    Rectangle MOVES_shape_cannonLeftSlot2     = new Rectangle(MOVES_cannonLeftSlot2X,  MOVES_cannonLeftSlot2Y  - 5, 32, 18+10);
    Rectangle MOVES_shape_cannonLeftSlot3     = new Rectangle(MOVES_cannonLeftSlot3X,  MOVES_cannonLeftSlot3Y  - 5, 32, 18+10);
    Rectangle MOVES_shape_cannonRightSlot0    = new Rectangle(MOVES_cannonRightSlot0X, MOVES_cannonRightSlot0Y - 5, 32, 18+10);
    Rectangle MOVES_shape_cannonRightSlot1    = new Rectangle(MOVES_cannonRightSlot1X, MOVES_cannonRightSlot1Y - 5, 32, 18+10);
    Rectangle MOVES_shape_cannonRightSlot2    = new Rectangle(MOVES_cannonRightSlot2X, MOVES_cannonRightSlot2Y - 5, 32, 18+10);
    Rectangle MOVES_shape_cannonRightSlot3    = new Rectangle(MOVES_cannonRightSlot3X, MOVES_cannonRightSlot3Y - 5, 32, 18+10);

    Rectangle MOVES_shape_leftRadio           = new Rectangle(MOVES_leftRadioX,        MOVES_leftRadioY,        13, 13);
    Rectangle MOVES_shape_forwardRadio        = new Rectangle(MOVES_forwardRadioX,     MOVES_forwardRadioY,     13, 13);
    Rectangle MOVES_shape_rightRadio          = new Rectangle(MOVES_rightRadioX,       MOVES_rightRadioY,       13, 13);

    Rectangle MOVES_shape_placingMoves        = new Rectangle(MOVES_moveSlot3X,        MOVES_moveSlot3Y,        28, (4 * 28) + (3 * 5));
    Rectangle MOVES_shape_pickingMoves        = new Rectangle(MOVES_cannonsX,          MOVES_cannonsY,          (4 * 28) + (3 * 4), 28);

    // reference coords - GO OCEANSIDE control
    private int GOOCEANSIDE_REF_X       = 0;
    private int GOOCEANSIDE_REF_Y       = 0;
    private int GOOCEANSIDE_backgroundX = GOOCEANSIDE_REF_X + 5+336+5;
    private int GOOCEANSIDE_backgroundY = GOOCEANSIDE_REF_Y + 8;
    private int GOOCEANSIDE_buttonX     = GOOCEANSIDE_REF_X + 5+336+5 + 19;
    private int GOOCEANSIDE_buttonY     = GOOCEANSIDE_REF_Y + 8 + 24;

    // GOOCEANSIDE shapes
    Rectangle GOOCEANSIDE_shape_clickingDisengage   = new Rectangle(GOOCEANSIDE_buttonX, GOOCEANSIDE_buttonY, 98, 16);

    // reference coords - CHAT control
    private int CHAT_REF_X              = 0;
    private int CHAT_REF_Y              = 0;

    // CHAT
    private int CHAT_backgroundX        = CHAT_REF_X + 489;
    private int CHAT_backgroundY        = CHAT_REF_Y + 8;

    private int CHAT_indicatorX         = CHAT_REF_X + 492;
    private int CHAT_indicatorY         = CHAT_REF_Y + 8;

    private int CHAT_boxX               = CHAT_REF_X + 590;
    private int CHAT_boxY               = CHAT_REF_Y + 7;

    private int CHAT_buttonSendX        = CHAT_REF_X + 723;
    private int CHAT_buttonSendY        = CHAT_REF_Y + 7;
    
    private int CHAT_windowX            = CHAT_REF_X + 496;
    private int CHAT_windowY            = CHAT_REF_Y + 42;

    private int CHAT_scrollBarUpX       = CHAT_REF_X + 781;
    private int CHAT_scrollBarUpY       = CHAT_REF_Y + 163;
    private int CHAT_scrollBarDownX     = CHAT_REF_X + 781;
    private int CHAT_scrollBarDownY     = CHAT_REF_Y + 38;
    private int CHAT_scrollBarMiddleX   = CHAT_REF_X + 781;
    private int CHAT_scrollBarMiddleY   = CHAT_REF_Y + 50;
    
    private int CHAT_scrollBarScrollX   = CHAT_REF_X + 781;
    private int CHAT_scrollBarScrollY   = CHAT_REF_Y + 48;
    

    // CHAT shapes
    Rectangle CHAT_shape_clickingSend   = new Rectangle(CHAT_buttonSendX, CHAT_buttonSendY, 45, 16);
    Rectangle CHAT_shape_chatBox        = new Rectangle(CHAT_boxX, CHAT_boxY, 128, 17);
    Rectangle CHAT_shape_scrollingUp    = new Rectangle(CHAT_scrollBarUpX, CHAT_scrollBarUpY, 12, 12);
    Rectangle CHAT_shape_scrollingDown  = new Rectangle(CHAT_scrollBarDownX, CHAT_scrollBarDownY, 12, 12);
    Rectangle CHAT_shape_messageWindow  = new Rectangle(CHAT_windowX, CHAT_windowY, 280, 130);
    Rectangle CHAT_shape_scrollBar = new Rectangle(CHAT_scrollBarScrollX, CHAT_scrollBarScrollY, 12, 30);
    
    /**
     * state of buttons. true if pushed, false if not.
     */
    private boolean goOceansideButtonIsDown = false; // initial
    private boolean sendChatButtonIsDown    = false; // initial
    private boolean scrollUpButtonIsDown    = false; // initial (confusing!)
    private boolean scrollDownButtonIsDown  = false; // initial (confusing!)

    /**
     * Max length for a chat message
     */
    private static final int CHAT_MESSAGE_MAX_LENGTH = 240;
    
    /**
     * Max number of messages before clearing old ones
     */
    private static final int CHAT_MAX_NUMBER_OF_MESSAGES = 500;
    
    /**
     * size of scroll increment (px)
     */
    private static final int CHAT_WINDOW_SCROLL_INCREMENT = 3;
    
    /**
     * maximum/minimum scroll height
     */
    private final int CHAT_MAXIMUM_SCROLL_Y = CHAT_scrollBarScrollY + 86;
    private final int CHAT_MINIMUM_SCROLL_Y = CHAT_scrollBarScrollY;

    private int manuaverSlot = 3;

    private boolean isBigShip;

    private boolean  isDragging;          //       are we dragging
    private MoveType startDragMove;       // what  are we dragging
    private int      startDragSlot;       // where are we dragging from
    private Vector2 draggingPosition;
    private boolean executionMoves;
    
    private boolean  draggingScroll = false; // keeps scrollbar locked until release

    protected BattleControlComponent(GameContext context, ControlAreaScene owner, boolean big) {
        super(context, owner);
        if (big) {
            movesHolder = new BigShipHandMove[4];
            isBigShip = true;
        }
        else {
            movesHolder = new SmallShipHandMove[4];
        }

        for (int i = 0; i < movesHolder.length; i++) {
            movesHolder[i] = createMove();
        }

        radioButtons = new boolean[3];
        enableRadio(1);

        this.context = context;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        shape = new ShapeRenderer();

        toolkit=Toolkit.getDefaultToolkit();
        clipboard=Toolkit.getDefaultToolkit().getSystemClipboard();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/font/Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        font = generator.generateFont(parameter);
        
        FreeTypeFontGenerator messageFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("assets/font/Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter messageFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        messageFontParameter.size = 11;
        messageFont = messageFontGenerator.generateFont(messageFontParameter);

        title = new Texture("assets/ui/title.png");
        radioOn = new Texture("assets/ui/radio-on.png");
        radioOff = new Texture("assets/ui/radio-off.png");
        radioOnDisable = new Texture("assets/ui/radio-on-disable.png");
        radioOffDisable = new Texture("assets/ui/radio-off-disable.png");
        autoOn = new Texture("assets/ui/auto-on.png");
        autoOff = new Texture("assets/ui/auto-off.png");
        autoBackground = new Texture("assets/ui/auto_background.png");

        sandTopTexture = new Texture("assets/ui/sand_top.png");
        sandBottomTexture = new Texture("assets/ui/sand_bot.png");

        sandTrickleTexture = new Texture("assets/ui/sand_trickle.png");
        sandTrickle = new TextureRegion(sandTrickleTexture, 0, 0, 1, 43);

        sandTop = new TextureRegion(sandTopTexture, 19, 43);
        sandBottom= new TextureRegion(sandBottomTexture, 19, 43);

        cannonSlots = new Texture("assets/ui/cannonslots.png");
        moves = new Texture("assets/ui/move.png");
        emptyMoves = new Texture("assets/ui/move_empty.png");
        shiphand = new Texture("assets/ui/shiphand.png");
        hourGlass = new Texture("assets/ui/hourglass.png");
        controlBackground = new Texture("assets/ui/moves-background.png");
        shipStatus = new Texture("assets/ui/status.png");
        shipStatusBg = new Texture("assets/ui/status-bg.png");
        moveGetTargetTexture = new Texture("assets/ui/sel_border_square.png");
        cannonSelectionEmpty = new Texture("assets/ui/grapplecannon_empty.png");
        cannonSelection = new Texture("assets/ui/grapplecannon.png");
        damage = new TextureRegion(new Texture("assets/ui/damage.png"));
        bilge = new TextureRegion(new Texture("assets/ui/bilge.png"));
        damage.flip(false, true);
        bilge.flip(false, true);

        emptyCannon = new TextureRegion(cannonSelectionEmpty, 25, 0, 25, 25);
        cannon = new TextureRegion(cannonSelection, 25, 0, 25, 25);

        damage.setRegionWidth(17);
        bilge.setRegionWidth(17);

        leftMoveTexture = new TextureRegion(moves, 0, 0, 28, 28);
        forwardMoveTexture = new TextureRegion(moves, 28, 0, 28, 28);
        rightMoveTexture = new TextureRegion(moves, 56, 0, 28, 28);
        manuaverTexture = new TextureRegion(moves, 84, 0, 28, 28);

        emptyLeftMoveTexture = new TextureRegion(emptyMoves, 0, 0, 28, 28);
        emptyForwardMoveTexture = new TextureRegion(emptyMoves, 28, 0, 28, 28);
        emptyRightMoveTexture = new TextureRegion(emptyMoves, 56, 0, 28, 28);

        emptyCannonLeft = new TextureRegion(cannonSlots, 0, 0, 16, 18);
        emptyCannonRight = new TextureRegion(cannonSlots, 16, 0, 16, 18);

        cannonLeft = new TextureRegion(cannonSlots, 32, 0, 16, 18);
        cannonRight = new TextureRegion(cannonSlots, 48, 0, 16, 18);

        moveTargetSelForce = new TextureRegion(moveGetTargetTexture, 0, 0, 36, 36);
        moveTargetSelAuto = new TextureRegion(moveGetTargetTexture, 36, 0, 36, 36);

        goOceansideUp = new Texture("assets/ui/go_oceanside.png");
        goOceansideDown = new Texture("assets/ui/go_oceansidePressed.png");
        goOceansideBackground = new Texture("assets/ui/go_oceanside_background.png");

        chatBackground = new Texture("assets/ui/chat_background.png");
        chatIndicator  = new Texture("assets/ui/chat_indicator.png");
        chatBarBackground = new Texture("assets/ui/chat_bar_background.png");
        chatButtonSend = new Texture("assets/ui/chat_button_send.png");
        chatButtonSendPressed = new Texture("assets/ui/chat_button_sendPressed.png");
        // text field for chat (based off ConnectScene)
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        TextField.TextFieldStyle style = new TextField.TextFieldStyle();
        style.font = messageFont;
        style.fontColor = new Color(0.16f, 0.16f, 0.16f, 1);

        // configure chat bar
        // TODO how to change font color when selected
        //style.focusedFontColor = new Color(215f, 201f, 79f, 1);
        style.cursor = new Image(new Texture("assets/skin/textfield-cursor.png")).getDrawable();
        style.cursor.setMinWidth(1f);
        style.selection = new Image(new Texture("assets/skin/battle-textfield-selection.png")).getDrawable();
        chatBar = new TextField("", style);
        chatBar.setSize(CHAT_shape_chatBox.width - 6, CHAT_shape_chatBox.height);
        chatBar.setPosition(CHAT_boxX + 3, CHAT_boxY);
        chatBar.setColor(235f, 240f, 242f, 255f);
        chatBar.setDisabled(false);
        chatBar.setVisible(true);
        chatBar.setFocusTraversal(true);
        chatBar.setBlinkTime(0.5f);
        stage.addActor(chatBar);
        stage.setKeyboardFocus(chatBar);
        
        // make a container to encapsulate the table. this is what we will scroll.
        chatContainer = new Container<Table>();
        chatContainer.setPosition(CHAT_windowX, CHAT_windowY);
        chatContainer.align(Align.bottomLeft);
        chatContainer.fillX();
        
        // style of the chat messages
        chatLabelStyle = new LabelStyle();
        //chatLabelStyle.background = new Image(new Texture("assets/ui/chat_message.png")).getDrawable();
        chatLabelStyle.font = messageFontGenerator.generateFont(messageFontParameter);
        chatLabelStyle.fontColor = new Color();
        chatLabelStyle.fontColor.r = 0f;
        chatLabelStyle.fontColor.b = 0f;
        chatLabelStyle.fontColor.g = 0f;
        chatLabelStyle.fontColor.a = 1f;
        
        // create a table to maintain order of messages
        chatTable = new Table();
        chatTable.align(Align.bottomLeft);
        
        // add table to container, and add container to a new stage
        chatContainer.setActor(chatTable);
        stage.addActor(chatContainer);
        
        // update top/bottom
        containerTopY = CHAT_windowY;
        containerBottomY = CHAT_windowY;
        
        // set chat view and scroll to default positions
        resetChatView();

        // get scroll bar textures
        chatScrollBarUp = new Texture("assets/ui/scrollbar_top.png");
        chatScrollBarUpPressed = new Texture("assets/ui/scrollbar_topPressed.png");
        chatScrollBarDown = new Texture("assets/ui/scrollbar_bottom.png");
        chatScrollBarDownPressed = new Texture("assets/ui/scrollbar_bottomPressed.png");
        chatScrollBarMiddle = new Texture("assets/ui/scrollbar_center.png");
        chatScrollBarScroll = new Texture("assets/ui/scrollbar_scroll.png");

        // initialise
        setDamagePercentage(70);
        setBilgePercentage(30);
    }
    
    /**
     * display a message in the chat
     */
    public void displayMessage(String message) {
        if (message.length() <= CHAT_MESSAGE_MAX_LENGTH) {
            chatTable.row().padBottom(10);
            Label chat1 = new Label(message, chatLabelStyle);
            chat1.setWrap(true);
            chatTable.add(chat1).width(CHAT_shape_messageWindow.width).align(Align.left);
            
            // handle chat if it has grown too big?
            if (chatTable.getCells().size > CHAT_MAX_NUMBER_OF_MESSAGES) {
                Cell<Actor> cell = chatTable.getCells().first();
                System.out.println(cell);
                cell.getActor().remove();                     // rm actor
                chatTable.getCells().removeValue(cell, true); // rm lingering physical presence
                chatTable.invalidate();
            }
            
            // update the top and bottom positional markers of the chat window
            containerTopY = CHAT_shape_messageWindow.y + CHAT_shape_messageWindow.height - chatTable.getHeight();
            containerBottomY = CHAT_shape_messageWindow.y;
            
            updateScrollPosition();
            
            // jump scroll to bottom
            resetChatView();
        }
    }
    
    /**
     * given a change in chatbox position, update the scrollbar position
     * note: only called when not dragging scrollbar
     */
    public void updateScrollPosition() {        
        // calculate the fraction, then pos = scrollbottom + (f * (scrolltop - scrollbottom))
        float pos = containerBottomY - chatContainer.getY();
        float f = pos / Math.abs(containerBottomY - containerTopY);
        
        // set the scrollbar to 
        CHAT_shape_scrollBar.y = CHAT_scrollBarScrollY + (int) (f * (CHAT_MAXIMUM_SCROLL_Y - CHAT_MINIMUM_SCROLL_Y));
    }
    
    /**
     * move both chatbox and scrollbar to bottom ("reset")
     */
    public void resetChatView() {
        // reset scroll to bottom
        CHAT_shape_scrollBar.y = CHAT_scrollBarScrollY;
        
        // reset message window to bottom
        chatContainer.setPosition(CHAT_windowX, CHAT_windowY);
    }
    
    /** 
     * given a change in scrollbar position, update the chatbox position
     * note: only called when dragging scrollbar
     */
    public void updateChatPosition() {
        // calculate the fraction
        float f = (float)(CHAT_shape_scrollBar.y - CHAT_MINIMUM_SCROLL_Y) / (float)(CHAT_MAXIMUM_SCROLL_Y - CHAT_MINIMUM_SCROLL_Y);
        
        // only move chat container if there is enough height to scroll?     
        if (chatTable.getHeight() >= CHAT_shape_messageWindow.height)
        {
            chatContainer.setY(
                CHAT_shape_messageWindow.y +
                (int) (-1 * (f * Math.abs(containerBottomY - containerTopY + CHAT_shape_messageWindow.y)))
            );
        }
    }
    
    /**
     * scroll the chat in some direction.
     */
    public void scrollChat(boolean scrollUp) {
        if (scrollUp && ((chatContainer.getY() + chatTable.getHeight()) <= ((CHAT_shape_messageWindow.y + CHAT_shape_messageWindow.height)))) {
            // at the top
        }
        else if ((!scrollUp) && (chatContainer.getY() >= CHAT_shape_messageWindow.y))
        {
            // at the bottom
        }
        else {
            // somewhere scrolling inbetween the top and bottom
            chatContainer.setPosition(
                    chatContainer.getX(),
                    chatContainer.getY() + CHAT_WINDOW_SCROLL_INCREMENT * (scrollUp?-1:1)
            );
        }
        
        // adjust the scrollbar accordingly
        updateScrollPosition();
    }

    public void setExecutingMoves(boolean flag) {
        this.executionMoves = flag;
    }

    public HandMove createMove() {
        if (isBigShip) {
            return new BigShipHandMove();
        }
        return new SmallShipHandMove();
    }

    @Override
    public void update() {
        int turnDuration = getContext().getTurnDuration();

        double ratio = (double) 43 / (double) turnDuration;

        sandTop.setRegionY(43 - (int) Math.round(time * ratio));
        sandTop.setRegionHeight((int) Math.round(time * ratio));

        ratio =  (double) 43 / (double) turnDuration;

        sandBottom.setRegionY(43 - (int) Math.round((turnDuration - time) * ratio));
        sandBottom.setRegionHeight((int) Math.round((turnDuration - time) * ratio));
    }

    @Override
    public void render() {
        renderMoveControl();
        renderGoOceanside();
        renderChat();
        stage.act();
        stage.draw();

        // accelerate anything that needs it
        doAccelerate();
    }

    @Override
    public void dispose() {
        resetMoves();
        targetMove = MoveType.FORWARD;
        enableRadio(1);

        auto = true;
        manuaverSlot = 3;
    }


    @Override
    public boolean handleClick(float x, float y, int button) {
        if ((!goOceansideButtonIsDown) && isClickingDisengage(x,y)) {
            goOceansideButtonIsDown = true;
            return true;
        }
        else if ((!sendChatButtonIsDown) && isClickingSend(x,y)) {
            sendChatButtonIsDown = true;
            return true;
        }
        else if ((!scrollUpButtonIsDown) && isClickingScrollUp(x,y)) {
            scrollUpButtonIsDown = true;
            return true;
        }
        else if ((!scrollDownButtonIsDown) && isClickingScrollDown(x,y)) {
            scrollDownButtonIsDown = true;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * handle send message functionality, and reset TextField
     */
    private void sendChat() {
        String message = chatBar.getText();
        if (message.length() > 0 && message.length() <= CHAT_MESSAGE_MAX_LENGTH) {
            context.sendPostMessagePacket(message);
        }
        chatBar.setCursorPosition(0);
        chatBar.setText("");
        chatBar.clearSelection();
    }

    /**
     * TextField key handlers
     * I wrote my own
     * Yeah
     *
     * keycodes passed as args for consistency so we only
     * need to lookup enums once e.g. Input.Keys.LEFT
     */
    private void handleLeftButton(int keycode) {
        // move cursor to left of selection, if any,
        // and clear it
        if (chatBar.getSelection().length() > 0) {
            chatBar.setCursorPosition(chatBar.getSelectionStart());
            chatBar.clearSelection();
        }

        int p = chatBar.getCursorPosition();
        if (p > 0)
        {
            chatBar.setCursorPosition(p - 1);
        }
    }

    private void handleRightButton(int keycode) {
        // move cursor to right of selection, if any,
        // and clear it
        int length = chatBar.getSelection().length();
        if (length > 0) {
            chatBar.setCursorPosition(chatBar.getSelectionStart() + length);
            chatBar.clearSelection();
        }

        chatBar.clearSelection();
        int p = chatBar.getCursorPosition();
        if (p < (chatBar.getText().length()))
        {
            chatBar.setCursorPosition(p + 1);
        }
    }

    /**
     * helper method to remove a selection
     */
    private void removeSelection() {
        String selection = chatBar.getSelection();
        int length = selection.length();
        if (length == 0) {
            return;
        }

        int start = chatBar.getSelectionStart();

        // delete all indices not including end
        String text = chatBar.getText();

        // get the first portion, checking the range.
        String textStart = "";
        if (start > 0) {
            textStart = text.substring(0, start - 1);
        }

        String newText = textStart + text.substring(start+length, text.length());
        chatBar.setText(newText);
        chatBar.setCursorPosition(start);
        chatBar.clearSelection();
    }

    private void handleBackspace(int keycode) {
        if (chatBar.getSelection().length() > 0)
        {
            removeSelection();
        }
        else
        {
            String text = chatBar.getText();
            int p = chatBar.getCursorPosition();
            if (p > 0)
            {
                String newText =
                        text.substring(0, p - 1) +
                        text.substring(p, text.length()
                );
                chatBar.setText(newText);
                chatBar.setCursorPosition(p - 1);
            }
        }
    }

    private void handleDel(int keycode) {
        if (chatBar.getSelection().length() > 0)
        {
            removeSelection();
        }
        else
        {
            String text = chatBar.getText();
            int p = chatBar.getCursorPosition();
            if (p < text.length())
            {
                String newText =
                        text.substring(0, p) +
                        text.substring(p + 1, text.length()
                );
                chatBar.setText(newText);
                chatBar.setCursorPosition(p);
            }
        }
    }

    private void handleChar(char character) {
        // optionally remove any selection first
        if (chatBar.getSelection().length() > 0)
        {
            removeSelection();
        }

        // insert char at cursor position
        String text = chatBar.getText();
        int p = chatBar.getCursorPosition();
        String newText =
            text.substring(0, p) +
            Character.toString(character) +
            text.substring(p, text.length()
        );
        if (newText.length() <= CHAT_MESSAGE_MAX_LENGTH) {
            chatBar.setText(newText);
            chatBar.setCursorPosition(p + 1);
        }
    }

    private void handleEnter(int keycode) {
        sendChat();
    }
    /**
     * wrapper around textfield helper methods
     * returns true if handled, false otherwise
     */
    private boolean handleAcceleratableKeys(int keycode) {
        boolean handled = true; // assume we handle it
        if (keycode == Input.Keys.LEFT) {
            handleLeftButton(keycode);
        }
        else if (keycode == Input.Keys.RIGHT) {
            handleRightButton(keycode);
        }
        else if (keycode == Input.Keys.BACKSPACE)
        {
            handleBackspace(keycode);
        }
        else if (keycode == Input.Keys.FORWARD_DEL) { // not del!!
            handleDel(keycode);
        }
        else
        {
            handled = false;
        }

        return handled;
    }

    private void handleModifierChord(int keycode) {
        switch (keycode) {
        case Input.Keys.A:
            chatBar.selectAll();
            break;
        case Input.Keys.C:
            StringSelection data = new StringSelection(chatBar.getSelection());
             clipboard.setContents(data, data);
            break;
        case Input.Keys.V:
            try {
                 String pastedData = "";
                 Transferable t = clipboard.getContents(null);
                 if (t.isDataFlavorSupported(DataFlavor.stringFlavor))
                 {
                    pastedData = (String)t.getTransferData(DataFlavor.stringFlavor);

                     // optionally remove any selection first
                     if (chatBar.getSelection().length() > 0)
                     {
                         removeSelection();
                     }

                     // use the pasted data
                        String text = chatBar.getText();
                        int p = chatBar.getCursorPosition();
                        String newText =
                            text.substring(0, p) +
                            pastedData +
                            text.substring(p, text.length()
                        );
                        if (newText.length() <= CHAT_MESSAGE_MAX_LENGTH) {
                            chatBar.setText(newText);
                            chatBar.setCursorPosition(p + 1);
                        }
                 }
              } catch (Exception ex) {
                  // do nothing
              }
            break;
        default:
            // unrecognised
            break;
        }

        // don't handle again
        handledChord = true;
    }

    /**
     * return whether point is in rect or not.
     *
     * note: compensates for Y weirdness by using absheight
     * so just use normal cartesian space (x==0,y==0 bottom left)
     *
     * @param inX   input x
     * @param inY   input y
     * @param rectX rect origin x
     * @param rectY rect origin y
     * @param rectW rect width
     * @param rectH rect height
     * @return
     */
    private boolean isPointInRect(float inX, float inY, Rectangle rect) {
        return
            (inX >= rect.x) &&
            (inX < (rect.x + rect.width)) &&
            (inY >= (absheight - rect.y - rect.height)) &&
            (inY < (absheight - rect.y));
    }

    private boolean isTogglingAuto(float x, float y) {
        return isPointInRect(x,y,MOVES_shape_auto);
    }

    private boolean isPlacingLeftCannons(float x, float y) {
        return isPointInRect(x,y,MOVES_shape_placingLeftCannons);
    }

    private boolean isPlacingRightCannons(float x, float y) {
        return isPointInRect(x,y,MOVES_shape_placingRightCannons);
    }

    private boolean isClickingDisengage(float x, float y) {
        return isPointInRect(x,y,GOOCEANSIDE_shape_clickingDisengage);
    }

    private boolean isClickingSend(float x, float y) {
        return isPointInRect(x,y,CHAT_shape_clickingSend);
    }

    private boolean isClickingScrollUp(float x, float y) {
        return isPointInRect(x,y,CHAT_shape_scrollingUp);
    }

    private boolean isClickingScrollDown(float x, float y) {
        return isPointInRect(x,y,CHAT_shape_scrollingDown);
    }
    
    private boolean isDraggingScrollBar(float x, float y) {
        return isPointInRect(x,y,CHAT_shape_scrollBar);
    }

    private boolean isPlacingMoves(float x, float y) {
        return isPointInRect(x,y,MOVES_shape_placingMoves);
    }

    private boolean isPickingMoves(float x, float y) {
        // cannons, L,F,R
        return isPointInRect(x,y,MOVES_shape_pickingMoves);
    }

    private int getSlotForPosition(float x, float y) {
        // TODO enumerate these properly
        if (isPlacingMoves(x,y)) {
            if (isPointInRect(x,y,MOVES_shape_moveSlot0)) {
                return 0;
            }
            else if (isPointInRect(x,y,MOVES_shape_moveSlot1)) {
                return 1;
            }
            else if (isPointInRect(x,y,MOVES_shape_moveSlot2)) {
                return 2;
            }
            else if (isPointInRect(x,y,MOVES_shape_moveSlot3)) {
                return 3;
            }
        }
        else if (isPickingMoves(x,y)) {
            if (isPointInRect(x,y,MOVES_shape_leftToken)) {
                return 4;
            }
            else if (isPointInRect(x,y,MOVES_shape_forwardToken)) {
                return 5;
            }
            else if (isPointInRect(x,y,MOVES_shape_rightToken)) {
                return 6;
            }
            else if (isPointInRect(x,y,MOVES_shape_cannonLeftSlot0)) {
                return 7;
            }
            else if (isPointInRect(x,y,MOVES_shape_cannonLeftSlot1)) {
                return 8;
            }
            else if (isPointInRect(x,y,MOVES_shape_cannonLeftSlot2)) {
                return 9;
            }
            else if (isPointInRect(x,y,MOVES_shape_cannonLeftSlot3)) {
                return 10;
            }
            else if (isPointInRect(x,y,MOVES_shape_cannonRightSlot0)) {
                return 11;
            }
            else if (isPointInRect(x,y,MOVES_shape_cannonRightSlot1)) {
                return 12;
            }
            else if (isPointInRect(x,y,MOVES_shape_cannonRightSlot2)) {
                return 13;
            }
            else if (isPointInRect(x,y,MOVES_shape_cannonRightSlot3)) {

            }
        }

        // default return
        return -1;
    }

    @Override
    public boolean handleDrag(float x, float y, float ix, float iy) {
        if (!isDragging) {
            startDragSlot = getSlotForPosition(x, y);
            if (startDragSlot != -1) { // cant start dragging from an invalid region
                switch (startDragSlot) {
                case 4:
                case 5:
                case 6:
                    // drag only if there are moves
                    MoveType m = MoveType.forId(startDragSlot - 3);
                    if (hasMove(m)) {
                        startDragMove = m;
                        isDragging = true;
                    }
                    break;
                default:
                    startDragMove  = movesHolder[startDragSlot].getMove();
                    isDragging = true;
                    break;
                }
            }
        }

        if (isDragging) {
            draggingPosition = new Vector2(x, y);
        }

        // if we drag off disengage,
        // deactivate it with no penalty to the user.
        if (goOceansideButtonIsDown) {
            if (!isClickingDisengage(x, y)) {
                goOceansideButtonIsDown = false;
            }
        }

        // if we drag off chatButtonSend,
        // deactivate it with no penalty to the user.
        if (sendChatButtonIsDown) {
            if (!isClickingSend(x, y)) {
                sendChatButtonIsDown = false;
            }
        }

        // if we drag off chatScrollBarUp,
        // deactivate it with no penalty to the user.
        if (scrollUpButtonIsDown) {
            if (!isClickingScrollUp(x, y)) {
                scrollUpButtonIsDown = false;
            }
        }

        // if we drag off chatScrollBarDown,
        // deactivate it with no penalty to the user.
        if (scrollDownButtonIsDown) {
            if (!isClickingScrollDown(x, y)) {
                scrollDownButtonIsDown = false;
            }
        }
        
        // handle if we are clicking scroll (one time only)
        if ((!draggingScroll) && isDraggingScrollBar(x, y)) {
            draggingScroll = true;
        }

        // handle drag of scrollbar (while down)
        if (draggingScroll) {
            CHAT_shape_scrollBar.y -= iy;
            if (CHAT_shape_scrollBar.y > CHAT_MAXIMUM_SCROLL_Y) {
                CHAT_shape_scrollBar.y = CHAT_MAXIMUM_SCROLL_Y;
            }
            else if (CHAT_shape_scrollBar.y < CHAT_MINIMUM_SCROLL_Y)
            {
                CHAT_shape_scrollBar.y = CHAT_MINIMUM_SCROLL_Y;
            }
            updateChatPosition();
        }

        return false;
    }

    @Override
    public boolean handleRelease(float x, float y, int button) {
        if (isDragging) {
            isDragging = false;
            int endDragSlot = getSlotForPosition(x, y);
            if (endDragSlot == -1) { // dragged from nothing

                if (startDragSlot <= 3) {
                    getContext().sendSelectMoveSlot(startDragSlot, MoveType.NONE);
                }
            } else { // dragged from something
                if ((manuaverSlot == startDragSlot) && endDragSlot <= 3) { // cant drag manauver off to new piece
                    manuaverSlot = endDragSlot;
                    getContext().sendManuaverSlotChanged(manuaverSlot);
                } else if ((manuaverSlot == endDragSlot) && startDragSlot <= 3) { // cant drag new piece onto manuaver
                    manuaverSlot = startDragSlot;
                    getContext().sendManuaverSlotChanged(manuaverSlot);
                }

                if (startDragSlot <= 3 && endDragSlot <= 3) { // drag from place to place; swap
                    // swap
                    int tmpSlot = startDragSlot;
                    startDragSlot = endDragSlot;
                    endDragSlot = tmpSlot;

                    // update
                    getContext().sendSelectMoveSlot(startDragSlot, movesHolder[endDragSlot].getMove());
                    getContext().sendSelectMoveSlot(endDragSlot, movesHolder[startDragSlot].getMove());
                } else if (startDragSlot > 3 && endDragSlot <= 3) { // moving from available to placed; replace
                    // if there's anything there already, replace it, apart from a manuaver slot
                    if (manuaverSlot != endDragSlot) {
                        getContext().sendSelectMoveSlot(endDragSlot, MoveType.forId(startDragSlot-3));
                    }
                }
            }

            draggingPosition = null;
        } else {
            if (executionMoves) {
                return false;
            }
            if (isPlacingMoves(x, y)) {
                int slot = getSlotForPosition(x,y);
                switch (slot) {
                case 0:
                case 1:
                case 2:
                case 3:
                    handleMovePlace(slot, button);
                    break;
                default:
                    break;
                }
            }
            else if (isPlacingLeftCannons(x, y)) {

                if (isPointInRect(x,y,MOVES_shape_cannonLeftSlot0))
                {
                    getContext().sendAddCannon(0,0);
                }
                else if (isPointInRect(x,y,MOVES_shape_cannonLeftSlot1))
                {
                    getContext().sendAddCannon(0,1);
                }
                else if (isPointInRect(x,y,MOVES_shape_cannonLeftSlot2))
                {
                    getContext().sendAddCannon(0,2);
                }
                else if (isPointInRect(x,y,MOVES_shape_cannonLeftSlot3))
                {
                    getContext().sendAddCannon(0,3);
                }
            }
            else if (isPlacingRightCannons(x, y)) {
                if (isPointInRect(x,y,MOVES_shape_cannonRightSlot0))
                {
                    getContext().sendAddCannon(1,0);
                }
                else if (isPointInRect(x,y,MOVES_shape_cannonRightSlot1))
                {
                    getContext().sendAddCannon(1,1);
                }
                else if (isPointInRect(x,y,MOVES_shape_cannonRightSlot2))
                {
                    getContext().sendAddCannon(1,2);
                }
                else if (isPointInRect(x,y,MOVES_shape_cannonRightSlot3))
                {
                    getContext().sendAddCannon(1,3);
                }
            }
            else if (isTogglingAuto(x, y)) {
                if (auto) {
                    auto = false;
                }
                else {
                    auto = true;
                }
                getContext().sendToggleAuto(auto);
            }
            else if (goOceansideButtonIsDown && isClickingDisengage(x, y)) {
                getContext().sendOceansideRequestPacket();
                goOceansideButtonIsDown = false;
            }
            else if (sendChatButtonIsDown && isClickingSend(x, y)) {
                sendChat();
                sendChatButtonIsDown = false;
            }
            else if (scrollUpButtonIsDown && isClickingScrollUp(x,y)) {
                scrollUpButtonIsDown = false;
            }
            else if (scrollDownButtonIsDown && isClickingScrollDown(x,y)) {
                scrollDownButtonIsDown = false;
            }
            else if (!auto){
                // can either click on the radio button or the move
                if (
                        (isPointInRect(x,y,MOVES_shape_leftRadio)) ||
                        (isPointInRect(x,y,MOVES_shape_leftToken))
                ) {
                    targetMove = MoveType.LEFT;
                    getContext().sendGenerationTarget(targetMove);
                }
                else if (
                        (isPointInRect(x,y,MOVES_shape_forwardRadio)) ||
                        (isPointInRect(x,y,MOVES_shape_forwardToken))
                ) {
                    targetMove = MoveType.FORWARD;
                    getContext().sendGenerationTarget(targetMove);
                }
                else if (
                        (isPointInRect(x,y,MOVES_shape_rightRadio)) ||
                        (isPointInRect(x,y,MOVES_shape_rightToken))
                ) {
                     targetMove = MoveType.RIGHT;
                     getContext().sendGenerationTarget(targetMove);
                }
            }
        }
        
        if (draggingScroll) {
            draggingScroll = false;
        }
        return false;
    }

    private void handleMovePlace(int position, int button) {
        if (position == manuaverSlot) {
            return;
        }
        if (isDragging) {
            return;
        }
        HandMove move = movesHolder[position];
        if (move.getMove() == MoveType.NONE) {
            if (button == Input.Buttons.LEFT) {
                if (leftMoves > 0) {
                    placeMove(position, MoveType.LEFT, true);
                    getContext().sendSelectMoveSlot(position, MoveType.LEFT);
                }
                else if (forwardMoves > 0) {
                    placeMove(position, MoveType.FORWARD, true);
                    getContext().sendSelectMoveSlot(position, MoveType.FORWARD);
                }
                else if (rightMoves > 0) {
                    placeMove(position, MoveType.RIGHT, true);
                    getContext().sendSelectMoveSlot(position, MoveType.RIGHT);
                }
            }
            else if (button == Input.Buttons.MIDDLE) {
                if (forwardMoves > 0) {
                    placeMove(position, MoveType.FORWARD, true);
                    getContext().sendSelectMoveSlot(position, MoveType.FORWARD);
                }
                else if (rightMoves > 0) {
                    placeMove(position, MoveType.RIGHT, true);
                    getContext().sendSelectMoveSlot(position, MoveType.RIGHT);
                }
                else if (leftMoves > 0) {
                    placeMove(position, MoveType.LEFT, true);
                    getContext().sendSelectMoveSlot(position, MoveType.LEFT);
                }
            }
            else if (button == Input.Buttons.RIGHT) {
                if (rightMoves > 0) {
                    placeMove(position, MoveType.RIGHT, true);
                    getContext().sendSelectMoveSlot(position, MoveType.RIGHT);
                }
                else if (forwardMoves > 0) {
                    placeMove(position, MoveType.FORWARD, true);
                    getContext().sendSelectMoveSlot(position, MoveType.FORWARD);
                }
                else if (leftMoves > 0) {
                    placeMove(position, MoveType.LEFT, true);
                    getContext().sendSelectMoveSlot(position, MoveType.LEFT);
                }
            }
        }
        else {
            if (button == Input.Buttons.LEFT) {
                MoveType next = move.getMove().getNext();
                if (hasMove(next)) {
                    placeMove(position, next, true);
                    getContext().sendSelectMoveSlot(position, next);
                }
                else if (hasMove(next.getNext())) {
                    placeMove(position, next.getNext(), true);
                    getContext().sendSelectMoveSlot(position, next.getNext());
                }
                else if (hasMove(next.getNext().getNext())) {
                    placeMove(position, next.getNext().getNext(), true);
                    getContext().sendSelectMoveSlot(position, next.getNext().getNext());
                }
            }
            else if (button == Input.Buttons.RIGHT) {
                MoveType prev = move.getMove().getPrevious();
                if (hasMove(prev)) {
                    placeMove(position, prev, true);
                    getContext().sendSelectMoveSlot(position, prev);
                }
                else if (hasMove(prev.getPrevious())) {
                    placeMove(position, prev.getPrevious(), true);
                    getContext().sendSelectMoveSlot(position, prev.getPrevious());
                }
                else if (hasMove(prev.getPrevious().getPrevious())) {
                    placeMove(position, prev.getPrevious().getPrevious(), true);
                    getContext().sendSelectMoveSlot(position, prev.getPrevious().getPrevious());
                }
            }
        }

    }

    private boolean hasMove(MoveType move) {
        switch (move) {
            case LEFT:
                return leftMoves > 0;
            case RIGHT:
                return rightMoves > 0;
            case FORWARD:
                return forwardMoves > 0;
            default:
                return true;
        }
    }

    /**
     * Sets the turn time
     * @param time  The turn time in seconds
     */
    public void setTime(int time) {
        this.time = time;
        int sandX = sandTrickle.getRegionX();
        sandX++;
        if (sandX > 8) {
            sandX = 0;
        }

        sandTrickle.setRegionX(sandX);
        sandTrickle.setRegionWidth(1);
    }

    /**
     * Sets the damage percentage
     * @param d The percentage to set out of 100
     */
    public void setDamagePercentage(int d) {
        if (d > 100) {
            d = 100;
        }
        this.damageAmount = d;
    }

    /**
     * Sets the bilge percentage
     * @param b The percentage to set out of 100
     */
    public void setBilgePercentage(int b) {
        if (b > 100) {
            b = 100;
        }
        this.bilgeAmount = b;
    }

    public void setMoveSelAutomatic(boolean auto) {
        this.auto = auto;
    }

    /**
     * Sets the available moves to use
     * @param left      The amount of left movements
     * @param forward   The amount of forward movements
     * @param right     The amount of right movements
     */
    public void setMoves(int left, int forward, int right) {
        this.leftMoves = left;
        this.forwardMoves = forward;
        this.rightMoves = right;
    }

    /**
     * Sets the available cannonballs to use
     * @param cannonballs The number of available cannonballs for use
     */
    public void setLoadedCannonballs(int cannonballs) {
        this.cannons = cannonballs;
    }

    private void renderMoveControl() {
        batch.begin();

        // The yellow BG for tokens and moves and hourglass
        batch.draw(controlBackground, this.MOVES_backgroundX, this.MOVES_backgroundY);

        drawMoveHolder();
        drawShipStatus();
        drawTimer();
        drawMovesSelect();
        TextureRegion t = manuaverTexture; // initial, prevent crashes
        batch.draw(title, MOVES_titleX, MOVES_titleY);
        if (isDragging && startDragSlot != -1) {
            if (startDragSlot == manuaverSlot) {
                t = manuaverTexture;
            } else {
                t = getTextureForMove(startDragMove);
            }

            if ((startDragMove != MoveType.NONE) || (startDragSlot == manuaverSlot)) {
                batch.draw(t, draggingPosition.x - t.getRegionWidth() / 2, Gdx.graphics.getHeight() - draggingPosition.y - t.getRegionHeight() / 2);
            }
        }
        batch.end();
    }

    /**
     * background for disengage button / pirates aboard
     */
    private void renderGoOceanside() {
        batch.begin();
        batch.draw(goOceansideBackground, GOOCEANSIDE_backgroundX, GOOCEANSIDE_backgroundY);
        batch.draw((goOceansideButtonIsDown)?goOceansideDown:goOceansideUp, GOOCEANSIDE_buttonX, GOOCEANSIDE_buttonY);
        batch.end();
    }

    private void renderChat() {
        batch.begin();
        batch.draw(chatBackground, CHAT_backgroundX, CHAT_backgroundY);
        batch.draw(chatIndicator,  CHAT_indicatorX, CHAT_indicatorY);
        batch.draw(chatBarBackground, CHAT_boxX, CHAT_boxY);
        batch.draw(chatScrollBarScroll, CHAT_shape_scrollBar.x, CHAT_shape_scrollBar.y);
        batch.draw(sendChatButtonIsDown?chatButtonSendPressed:chatButtonSend, CHAT_buttonSendX, CHAT_buttonSendY);
        batch.draw(scrollUpButtonIsDown?chatScrollBarUpPressed:chatScrollBarUp, CHAT_scrollBarUpX, CHAT_scrollBarUpY);
        batch.draw(scrollDownButtonIsDown?chatScrollBarDownPressed:chatScrollBarDown, CHAT_scrollBarDownX, CHAT_scrollBarDownY);
        batch.draw(chatScrollBarMiddle, CHAT_scrollBarMiddleX, CHAT_scrollBarMiddleY);
        
        // if we're scrolling, do the scroll
        if (scrollUpButtonIsDown) {
            this.scrollChat(true);
        }
        else if (scrollDownButtonIsDown) {
            this.scrollChat(false);
        }
        
        batch.end();
    }

    private void drawMoveHolder() {
        // The hand bg
        batch.draw(shiphand, MOVES_shiphandX, MOVES_shiphandY);

        // get cannonheights for each slot
        ArrayList<Integer> cannonHeights = new ArrayList<Integer>();
        cannonHeights.add(MOVES_cannonSlot0Y);
        cannonHeights.add(MOVES_cannonSlot1Y);
        cannonHeights.add(MOVES_cannonSlot2Y);
        cannonHeights.add(MOVES_cannonSlot3Y);

        // get moveheights for each slot
        ArrayList<Integer> moveHeights = new ArrayList<Integer>();
        moveHeights.add(MOVES_moveSlot0Y);
        moveHeights.add(MOVES_moveSlot1Y);
        moveHeights.add(MOVES_moveSlot2Y);
        moveHeights.add(MOVES_moveSlot3Y);

        boolean isBigShip = (movesHolder instanceof BigShipHandMove[]);
        for (int i = 0; i < movesHolder.length; i++) {
            // helper variables
            HandMove move = movesHolder[i];
            boolean[] left = move.getLeft();
            boolean[] right = move.getRight();
            int cH = cannonHeights.get(i);
            int mH =   moveHeights.get(i);

            // draw left (guns AB |__| CD - place A, then B)
            // must be in this order to create blur together
            batch.draw((left[0])?cannonLeft:emptyCannonLeft, MOVES_cannonLeftSlotSmallX, cH); // left
            if (isBigShip) {
                batch.draw((left[0] && left[1])?cannonLeft:emptyCannonLeft, MOVES_cannonLeftSlotBigX, cH); // left
            }

            // draw left (guns AB |__| CD - place D, then C)
            // must be in this order to create blur together
            batch.draw((right[0] && right[1])?cannonRight:emptyCannonRight, MOVES_cannonRightSlotSmallX, cH); // right
            if (isBigShip) {
                batch.draw((right[0])?cannonRight:emptyCannonRight, MOVES_cannonRightSlotBigX, cH); // right
            }

            // draw moves and manauver
            if (i == manuaverSlot) {
                batch.draw(manuaverTexture, MOVES_moveSlotX, mH);
            }
            else
            {
                if (move.getMove() != MoveType.NONE) {
                    Color color = batch.getColor();
                    if (move.isMoveTemp()) {
                        batch.setColor(0.5F, 0.5F, 0.5F, 1F);
                    }
                    else {
                        batch.setColor(color.r, color.g, color.b, 1f);
                    }

                    batch.draw(getTextureForMove(move.getMove()), MOVES_moveSlotX, mH);
                    batch.setColor(color.r, color.g, color.b, 1f);
                }
            }
        }
    }

    private TextureRegion getTextureForMove(MoveType type) {
        switch (type) {
            case LEFT:
                return leftMoveTexture;
            case RIGHT:
                return rightMoveTexture;
            default:
            case FORWARD:
                return forwardMoveTexture;
        }
    }

    /**
     * Draws the movement selection section
     */
    private void drawMovesSelect() {
        // auto, cannons
        batch.draw(autoBackground, MOVES_autoBackgroundX, MOVES_autoBackgroundY);
        font.draw(batch, "Auto", MOVES_autoTextX, MOVES_autoTextY);
        batch.draw(auto?autoOn:autoOff,            MOVES_autoX,    MOVES_autoY);
        batch.draw((cannons>0)?cannon:emptyCannon, MOVES_cannonsX, MOVES_cannonsY);
        font.draw(batch, "x" + Integer.toString(cannons), MOVES_cannonsTextX, MOVES_cannonsTextY);

        // moves
        batch.draw((leftMoves == 0)?emptyLeftMoveTexture:leftMoveTexture, MOVES_leftX, MOVES_leftY);
        batch.draw((forwardMoves == 0)?emptyForwardMoveTexture:forwardMoveTexture, MOVES_forwardX, MOVES_forwardY);
        batch.draw((rightMoves == 0)?emptyRightMoveTexture:rightMoveTexture,  MOVES_rightX, MOVES_rightY);

        // radios
        Texture onTex  = auto?radioOnDisable:radioOn;
        Texture offTex = auto?radioOffDisable:radioOff;
        batch.draw(radioButtons[0]?onTex:offTex, MOVES_leftRadioX,    MOVES_leftRadioY);
        batch.draw(radioButtons[1]?onTex:offTex, MOVES_forwardRadioX, MOVES_forwardRadioY);
        batch.draw(radioButtons[2]?onTex:offTex, MOVES_rightRadioX,   MOVES_rightRadioY);

        // move targeting
        TextureRegion sel = auto ? moveTargetSelAuto : moveTargetSelForce;
        if (targetMove != null) {
            switch(targetMove) {
                case LEFT:
                    batch.draw(sel, MOVES_leftSelectX, MOVES_leftSelectY);
                    enableRadio(0);
                    break;
                case FORWARD:
                    batch.draw(sel, MOVES_forwardSelectX, MOVES_forwardSelectY);
                    enableRadio(1);
                    break;
                case RIGHT:
                    batch.draw(sel, MOVES_rightSelectX, MOVES_rightSelectY);
                    enableRadio(2);
                    break;
            }
        }

        // misc labels
        font.setColor(Color.BLACK);
        font.draw(batch, "x" + Integer.toString(leftMoves   ), MOVES_leftMovesTextX,    MOVES_leftMovesTextY);
        font.draw(batch, "x" + Integer.toString(forwardMoves), MOVES_forwardMovesTextX, MOVES_forwardMovesTextY);
        font.draw(batch, "x" + Integer.toString(rightMoves  ), MOVES_rightMovesTextX,   MOVES_rightMovesTextY);
    }

    /**
     * Draws the sand clock
     */
    private void drawTimer() {
        batch.draw(hourGlass, MOVES_hourGlassX, MOVES_hourGlassY);
        batch.draw(sandTrickle,MOVES_sandTrickleX, MOVES_sandTrickleY );
        batch.draw(sandTop, MOVES_sandTopX, MOVES_sandTopY);
        batch.draw(sandBottom, MOVES_sandBottomX, MOVES_sandBottomY);
    }

    /**
     * Draws ship status
     *
     * Ship damage, Ship bilge, etc
     */
    private void drawShipStatus() {
        batch.draw(shipStatusBg, MOVES_shipStatusBackgroundX, MOVES_shipStatusBackgroundY);

        batch.end();

        shape.begin(ShapeRenderer.ShapeType.Filled);

        // The values for damage and water are hard-coded here, they
        // should come from your code

        float redstuff = damageAmount / 100f;
        float redStart = 90.0f + 180.0f * (1.0f - redstuff);
        float redLength = 180.0f * redstuff;

        float bluestuff = bilgeAmount / 100;

        float blueStart = 270.0f;
        float blueLength = 180.0f * bluestuff;

        shape.setColor(new Color(131 / 255f, 6 / 255f, 0f, .7f));
        shape.arc(MOVES_shipDamageX, MOVES_shipDamageY, 16.50f, redStart, redLength);
        shape.setColor(new Color(0f, 207 / 255f, 249f, .7f));
        shape.arc(MOVES_shipBilgeX, MOVES_shipBilgeY, 16.50f, blueStart, blueLength);
        shape.end();

        batch.begin();

        batch.draw(shipStatus, MOVES_shipStatusX, MOVES_shipStatusY);
    }

    public void placeMove(int slot, MoveType move, boolean temp) {
        HandMove hm = movesHolder[slot];
        hm.setMove(move);
        hm.setMoveTemporary(temp);
    }

    public void resetMoves() {
        for (int i = 0; i < movesHolder.length; i++) {
            movesHolder[i].setMove(MoveType.NONE);
            movesHolder[i].resetLeft();
            movesHolder[i].resetRight();

            // fix stuck moves that might appear after a turn completes
            getContext().sendSelectMoveSlot(i, MoveType.NONE);
        }
        manuaverSlot = 3;
        getContext().sendManuaverSlotChanged(3);

        // fix stuck buttons if they were clicked across a turn
        // with no penalty to the user
        if (goOceansideButtonIsDown) {
            goOceansideButtonIsDown = false;
        }
        if (sendChatButtonIsDown) {
            sendChatButtonIsDown = false;
        }
        if (scrollUpButtonIsDown) {
            scrollUpButtonIsDown = false;
        }
        if (scrollDownButtonIsDown) {
            scrollDownButtonIsDown = false;
        }
    }

    /**
     * sets cannons both on server and on client
     */
    public void resetCannons()
    {
        for (int i=0; i<4; i++) {
            // count number of cannons we have set on each side
            boolean l[]  = movesHolder[i].getLeft();
            boolean r[] = movesHolder[i].getRight();
            int left  = (l[0]?1:0) + (l[1]?1:0);
            int right = (r[0]?1:0) + (r[1]?1:0);

            // calculate number of cannons we need to 'add' to cancel this
            // (we can only add cannons until rollover, so "3-left")
            // then send update n times as necessary
            if (left > 0) {
                for (int j=0; j<(3 - left); j++) {
                    getContext().sendAddCannon(0, i);
                }
                setCannons(0, i, 0);
            }
            if (right > 0) {
                for (int j=0; j<(3 - right); j++) {
                    getContext().sendAddCannon(1, i);
                }
                setCannons(1, i, 0);
            }
        }
    }

    /**
     * resets local copy of cannons only - does not send anything to server
     */
    public void setCannons(int side, int slot, int amount) {
        if (side == 0) {
            movesHolder[slot].resetLeft();
            for (int i = 0; i < amount; i++)
                movesHolder[slot].addLeft();
        }
        else if (side == 1) {
            movesHolder[slot].resetRight();
            for (int i = 0; i < amount; i++)
                movesHolder[slot].addRight();
        }
    }

    public void setMoveSealTarget(MoveType moveSealTarget) {
        this.targetMove = moveSealTarget;
    }

    public void setMovePlaces(byte[] moves, byte[] left, byte[] right) {
        for (int slot = 0; slot < 4; slot++) {
            HandMove move = movesHolder[slot];
            move.setMoveTemporary(false);
            move.resetRight();
            move.resetLeft();

            move.setMove(MoveType.forId(moves[slot]));
            for (int i = 0; i < left[slot]; i++) {
                move.addLeft();
            }
            for (int i = 0; i < right[slot]; i++) {
                move.addRight();
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if (isModifying()) {
            handleModifierChord(keycode);
            return true;
        }
        else if (keycode == Input.Keys.ENTER) {
            // enter shouldnt accelerate
            stopAllAcceleration();
            handleEnter(keycode);
            return true;
        }
        else if (keycode == Input.Keys.CONTROL_LEFT) {
            startModifier();
            return true;
        }
        else
        {
            startKeyAcceleration(keycode);
            return handleAcceleratableKeys(keycode);
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.CONTROL_LEFT)
        {
            stopModifier();
        }
        else
        {
            stopAllAcceleration();
        }

        // eat all teh keys
        return true;
    }

    @Override
    /**
     * keytyped fires on keyDown, then subsequently if key remains down.
     */
    public boolean keyTyped(char character) {
        if (character >= 32 && character < 127)
        {
            if (!isAcceleratingCharacter(character))
                // only handle once
                {
                    startCharacterAcceleration(character);
                    handleChar(character); // printable ascii
                }
                return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        // TODO Auto-generated method stub
        return false;
    }
}
