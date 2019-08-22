package com.benberi.cadesim.game.scene.impl.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.benberi.cadesim.GameContext;
import com.benberi.cadesim.game.entity.vessel.move.MoveType;
import com.benberi.cadesim.game.scene.SceneComponent;
import com.benberi.cadesim.game.scene.impl.control.hand.HandMove;
import com.benberi.cadesim.game.scene.impl.control.hand.impl.BigShipHandMove;
import com.benberi.cadesim.game.scene.impl.control.hand.impl.SmallShipHandMove;

public class BattleControlComponent extends SceneComponent<ControlAreaScene> {

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
     * Font for texts
     */
    private BitmapFont font;

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
    int heightmod = Gdx.graphics.getHeight() - 700;
    int absheight = Gdx.graphics.getHeight(); // absolute height
    
    
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

    private Texture cannonSelection;
    private Texture cannonSelectionEmpty;
    
    private Texture goOceansideUp;
    private Texture goOceansideDown;
    
    // reference coords - MOVES control
    private int MOVES_REF_X             = 0;
    private int MOVES_REF_Y             = 75;

    private int MOVES_backgroundX       = MOVES_REF_X + 5;
    private int MOVES_backgroundY       = MOVES_REF_Y - 67;
    
    private int MOVES_titleX            = MOVES_REF_X + 66;
    private int MOVES_titleY            = MOVES_REF_Y + 76;
    
    private int MOVES_autoX             = MOVES_REF_X + 58;
    private int MOVES_autoY             = MOVES_REF_Y + 33;

    private int MOVES_autoTextX         = MOVES_REF_X + 30;
    private int MOVES_autoTextY         = MOVES_REF_Y + 45; // text from top edge

    private int MOVES_cannonsX          = MOVES_REF_X + 48;
    private int MOVES_cannonsY          = MOVES_REF_Y + 3;

    private int MOVES_cannonsTextX      = MOVES_REF_X + 56;
    private int MOVES_cannonsTextY      = MOVES_REF_Y - 5;  // text from top edge

    private int MOVES_leftX             = MOVES_REF_X + 80;
    private int MOVES_forwardX          = MOVES_REF_X + 110;
    private int MOVES_rightX            = MOVES_REF_X + 140;
    private int MOVES_leftY             = MOVES_REF_Y + 0;
    private int MOVES_forwardY          = MOVES_REF_Y + 0;
    private int MOVES_rightY            = MOVES_REF_Y + 0;

    private int MOVES_leftRadioX        = MOVES_REF_X + 88;
    private int MOVES_forwardRadioX     = MOVES_REF_X + 118;
    private int MOVES_rightRadioX       = MOVES_REF_X + 148;
    private int MOVES_leftRadioY        = MOVES_REF_Y + 36;
    private int MOVES_forwardRadioY     = MOVES_REF_Y + 36;
    private int MOVES_rightRadioY       = MOVES_REF_Y + 36;
    
    private int MOVES_leftSelectX       = MOVES_REF_X + 76;
    private int MOVES_forwardSelectX    = MOVES_REF_X + 106;
    private int MOVES_rightSelectX      = MOVES_REF_X + 136;
    private int MOVES_leftSelectY       = MOVES_REF_Y - 4;
    private int MOVES_forwardSelectY    = MOVES_REF_Y - 4;
    private int MOVES_rightSelectY      = MOVES_REF_Y - 4;
    
    private int MOVES_leftMovesTextX    = MOVES_REF_X + 88;    
    private int MOVES_forwardMovesTextX = MOVES_REF_X + 118;
    private int MOVES_rightMovesTextX   = MOVES_REF_X + 148;
    private int MOVES_leftMovesTextY    = MOVES_REF_Y - 5; // text from top edge
    private int MOVES_forwardMovesTextY = MOVES_REF_Y - 5; // "
    private int MOVES_rightMovesTextY   = MOVES_REF_Y - 5; // " shiphand:195,19
    
    private int MOVES_shiphandX         = MOVES_REF_X + 195;
    private int MOVES_shiphandY         = MOVES_REF_Y - 57;
    
    private int MOVES_moveSlot0X            = MOVES_REF_X + 208; // 208-239
    private int MOVES_moveSlot0Y            = MOVES_REF_Y + 54;
    private int MOVES_moveSlot1X            = MOVES_REF_X + 208;
    private int MOVES_moveSlot1Y            = MOVES_REF_Y + 20;
    private int MOVES_moveSlot2X            = MOVES_REF_X + 208;
    private int MOVES_moveSlot2Y            = MOVES_REF_Y - 14;
    private int MOVES_moveSlot3X            = MOVES_REF_X + 208;
    private int MOVES_moveSlot3Y            = MOVES_REF_Y - 48;
    
    private int MOVES_cannonLeftSlot0X      = MOVES_REF_X + 181; // left - 181 && x <= 206;
    private int MOVES_cannonLeftSlot0Y      = 0;
    private int MOVES_cannonRightSlot0X     = MOVES_REF_X + 241; // right - 241 && x <= 271;
    private int MOVES_cannonRightSlot0Y     = 0;
    private int MOVES_cannonLeftSlot1X      = MOVES_REF_X + 181;
    private int MOVES_cannonLeftSlot1Y      = 0;
    private int MOVES_cannonRightSlot1X     = MOVES_REF_X + 241;
    private int MOVES_cannonRightSlot1Y     = 0;
    private int MOVES_cannonLeftSlot2X      = MOVES_REF_X + 181;
    private int MOVES_cannonLeftSlot2Y      = 0;
    private int MOVES_cannonRightSlot2X     = MOVES_REF_X + 241;
    private int MOVES_cannonRightSlot2Y     = 0;
    private int MOVES_cannonLeftSlot3X      = MOVES_REF_X + 181;
    private int MOVES_cannonLeftSlot3Y      = 0;
    private int MOVES_cannonRightSlot3X     = MOVES_REF_X + 241;
    private int MOVES_cannonRightSlot3Y     = 0;
    
    // reference coords - GO OCEANSIDE control
    private int GOOCEANSIDE_REF_X       = 0;
    private int GOOCEANSIDE_REF_Y       = 0;
    private int GOOCEANSIDE_backgroundX = GOOCEANSIDE_REF_X + 5+336+5;
	private int GOOCEANSIDE_backgroundY = GOOCEANSIDE_REF_Y + 8;
	private int GOOCEANSIDE_buttonX     = GOOCEANSIDE_REF_X + 5+336+5 + 19;
	private int GOOCEANSIDE_buttonY     = GOOCEANSIDE_REF_Y + 8 + 24;

	/**
	 * state of goOceanside button. true if pushed, false if not.
	 */
	private boolean goOceansideButtonIsDown = false; // initial


    private int manuaverSlot = 3;

    private boolean isBigShip;

    private boolean  isDragging;     //       are we dragging
    private MoveType startDragMove;  // what  are we dragging
    private int      startDragSlot;  // where are we dragging from
    private Vector2 draggingPosition;
    private boolean executionMoves;

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
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        shape = new ShapeRenderer();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/font/Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        font = generator.generateFont(parameter);

        title = new Texture("assets/ui/title.png");
        radioOn = new Texture("assets/ui/radio-on.png");
        radioOff = new Texture("assets/ui/radio-off.png");
        radioOnDisable = new Texture("assets/ui/radio-on-disable.png");
        radioOffDisable = new Texture("assets/ui/radio-off-disable.png");
        autoOn = new Texture("assets/ui/auto-on.png");
        autoOff = new Texture("assets/ui/auto-off.png");

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

        setDamagePercentage(70);
        setBilgePercentage(30);

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
    	// only activate the disengage click if it's not active already
    	if (!goOceansideButtonIsDown) {
    		if (isClickingDisengage(x, y)) {
    			goOceansideButtonIsDown = true;
    		}
    	}
        return false;
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
    private boolean isPointInRect(float inX, float inY, int rectX, int rectY, int rectW, int rectH) {
    	return
    		(inX >= rectX) &&
    		(inX < (rectX + rectW)) &&
    		(inY >= (absheight - rectY - rectH)) &&
    		(inY < (absheight - rectY));
    }


    private boolean isTogglingAuto(float x, float y) {
    	return isPointInRect(x,y,MOVES_autoX,MOVES_autoY,17,17);
    }

    private boolean isPlacingLeftCannons(float x, float y) {
        return x >= 181 && x <= 206;
    }

    private boolean isPlacingRightCannons(float x, float y) {
        return x >= 241 && x <= 271;
    }
    
    private boolean isClickingDisengage(float x, float y) {
    	return isPointInRect(x,y,GOOCEANSIDE_buttonX, GOOCEANSIDE_buttonY, 98, 16);
    }
    
//    private boolean isPlacingMoves(float x, float y) {
//        return x >= 208 && x < 240 && y >= (heightmod + 538) && y <= (heightmod + 670);
//    }
//
//    private boolean isPickingMoves(float x, float y) {
//        return (x >= 80) && (x <= 166) && (y >= heightmod + 598) && (y <= heightmod + 624);
//    }

    private int getSlotForPosition(float x, float y) {
    	// move placing slots
    	if (isPointInRect(x,y,MOVES_moveSlot0X,MOVES_moveSlot0Y,28,28)) {
    		return 0;
    	}
    	else if (isPointInRect(x,y,MOVES_moveSlot1X,MOVES_moveSlot1Y,28,28)) {
    		return 1;
    	}
    	else if (isPointInRect(x,y,MOVES_moveSlot2X,MOVES_moveSlot2Y,28,28)) {
    		return 2;
    	}
    	else if (isPointInRect(x,y,MOVES_moveSlot3X,MOVES_moveSlot3Y,28,28)) {
    		return 3;
    	}

    	// token slots
    	else if (isPointInRect(x,y,MOVES_leftX, MOVES_leftY,28,28)) {
    		return 4;
    	}
    	else if (isPointInRect(x,y,MOVES_forwardX, MOVES_forwardY,28,28)) {
    		return 5;
    	}
    	else if (isPointInRect(x,y,MOVES_rightX, MOVES_rightY,28,28)) {
    		return 6;
    	}
    	else {
    		return -1;
    	}
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
			    if (y >= heightmod + 538 && y <= heightmod + 569) {
			        handleMovePlace(0, button);
			    }
			    else if (y >= heightmod + 573 && y <= heightmod + 603) {
			        handleMovePlace(1, button);
			    }
			    else if (y >= heightmod + 606 && y <= heightmod + 637) {
			        handleMovePlace(2, button);
			    }
			    else if(y >= heightmod + 642 && y <= heightmod + 670) {
			        handleMovePlace(3, button);
			    }
			}
			else if (isPlacingLeftCannons(x, y)) {
			    if (y >= heightmod + 548 && y <= heightmod + 562) {
			        getContext().sendAddCannon(0, 0);
			    }
			    else if (y >= heightmod + 582 && y <= heightmod + 597) {
			        getContext().sendAddCannon(0, 1);
			    }
			    else if (y >= heightmod + 618 && y <= heightmod + 630) {
			        getContext().sendAddCannon(0, 2);
			    }
			    else if (y >= heightmod + 650 && y <= heightmod + 665) {
			        getContext().sendAddCannon(0, 3);
			    }
			}
			else if (isPlacingRightCannons(x, y)) {
			    if (y >= heightmod + 548 && y <= heightmod + 562) {
			        getContext().sendAddCannon(1, 0);
			    }
			    else if (y >= heightmod + 582 && y <= heightmod + 597) {
			        getContext().sendAddCannon(1, 1);
			    }
			    else if (y >= heightmod + 618 && y <= heightmod + 630) {
			        getContext().sendAddCannon(1, 2);
			    }
			    else if (y >= heightmod + 650 && y <= heightmod + 665) {
			        getContext().sendAddCannon(1, 3);
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
			else if (isClickingDisengage(x, y)) {
				getContext().sendOceansideRequestPacket();
				goOceansideButtonIsDown = false;
			}
			else if (!auto){
			    if (isChosedLeft(x, y)) {
			        this.targetMove = MoveType.LEFT;
			        getContext().sendGenerationTarget(targetMove);
			    }
			    else if (isChosedForward(x, y)) {
			        this.targetMove = MoveType.FORWARD;
			        getContext().sendGenerationTarget(targetMove);
			    }
			     else if (isChosedRight(x, y)) {
			         this.targetMove = MoveType.RIGHT;
			         getContext().sendGenerationTarget(targetMove);
			    }
			}
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

    private boolean isPlacingMoves(float x, float y) {
        return isPointInRect(
        		x,
        		y,
        		MOVES_moveSlot3X,
        		MOVES_moveSlot3Y,
        		28,
        		(4 * 28) + (3 * 5)
        );
    }

    private boolean isPickingMoves(float x, float y) {
    	return isPointInRect(
        		x,
        		y,
        		MOVES_leftX,
        		MOVES_leftY,
        		(3 * 28) + (2 * 2),
        		28
        );
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

    private void drawMoveHolder() {

        // The hand bg
        batch.draw(shiphand, this.MOVES_shiphandX, this.MOVES_shiphandY);


        int height = 173 - 40;
        for (int i = 0; i < movesHolder.length; i++) {
            HandMove move = movesHolder[i];

            boolean[] left = move.getLeft();
            boolean[] right = move.getRight();

            batch.draw(emptyCannonLeft, 336 - 61 - 81, height); // left
            if (left[0]) {
                batch.draw(cannonLeft, 336 - 61 - 81, height); // left
            }
            batch.draw(emptyCannonRight, 336 - 61 - 35, height); // right
            if (right[0]) {
                batch.draw(cannonRight, 336 - 61 - 35, height); // left
            }

            if (movesHolder instanceof BigShipHandMove[]) {
                batch.draw(emptyCannonLeft, 336 - 61 - 96, height); // left
                if (left[0] && left[1]) {
                    batch.draw(cannonLeft, 336 - 61 - 96, height); // left
                }
                batch.draw(emptyCannonRight, 336 - 61 - 20, height); // right
                if (right[0] && right[1]) {
                    batch.draw(cannonRight, 336 - 61 - 20, height); // right
                }
            }

            if (i == manuaverSlot) {
                batch.draw(manuaverTexture, 336 - 61 - 64, height - 4);
            }
            else {
                if (move.getMove() != MoveType.NONE) {
                    Color color = batch.getColor();
                    if (move.isMoveTemp()) {
                        batch.setColor(0.5F, 0.5F, 0.5F, 1F);
                    }
                    else {
                        batch.setColor(color.r, color.g, color.b, 1f);
                    }

                    batch.draw(getTextureForMove(move.getMove()), 336 - 61 - 64, height - 4);
                    batch.setColor(color.r, color.g, color.b, 1f);
                }
            }

            height -= 34;
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
        font.draw(batch, "x" + Integer.toString(leftMoves), MOVES_leftMovesTextX, MOVES_leftMovesTextY);
        font.draw(batch, "x" + Integer.toString(forwardMoves), MOVES_forwardMovesTextX, MOVES_forwardMovesTextY);
        font.draw(batch, "x" + Integer.toString(rightMoves), MOVES_rightMovesTextX, MOVES_rightMovesTextY);
    }

    /**
     * Draws the sand clock
     */
    private void drawTimer() {
        batch.draw(hourGlass, 336 - 27 - 20, 25);
        batch.draw(sandTrickle,336 - 27 - 7, 30 );
        batch.draw(sandTop, 336 - 27 - 16, 72);
        batch.draw(sandBottom, 336 - 27 - 16, 28);
    }

    /**
     * Draws ship status
     *
     * Ship damage, Ship bilge, etc
     */
    private void drawShipStatus() {
        int x = 336 - 43 - 12;
        int y = 175 - 50;
        batch.draw(shipStatusBg, x, y);

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
        shape.arc(301, 146, 16.5f, redStart, redLength);
        shape.setColor(new Color(0f, 207 / 255f, 249f, .7f));
        shape.arc(304, 146, 16.5f, blueStart, blueLength);
        shape.end();

        batch.begin();

        batch.draw(shipStatus, x, y);
    }


    public boolean isChosedLeft(float x, float y) {
    	return isPointInRect(x,y,MOVES_leftRadioX,MOVES_leftRadioY,13,13);
    }

    public boolean isChosedForward(float x, float y) {
    	return isPointInRect(x,y,MOVES_forwardRadioX,MOVES_forwardRadioY,13,13);
    }

    public boolean isChosedRight(float x, float y) {
    	return isPointInRect(x,y,MOVES_rightRadioX,MOVES_rightRadioY,13,13);
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

        // fix stuck disengage button if it was clicked across a turn
        // with no penalty to the user
        if (goOceansideButtonIsDown) {
        	goOceansideButtonIsDown = false;
        }
    }

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
}
