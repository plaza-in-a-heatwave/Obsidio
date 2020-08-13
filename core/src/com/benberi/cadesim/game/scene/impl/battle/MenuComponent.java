package com.benberi.cadesim.game.scene.impl.battle;

import java.awt.Rectangle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.InputProcessor;
import com.benberi.cadesim.GameContext;
import com.benberi.cadesim.game.scene.SceneComponent;
import com.benberi.cadesim.game.scene.impl.connect.ConnectionSceneState;

public class MenuComponent extends SceneComponent<SeaBattleScene> implements InputProcessor {
    /**
     * The context
     */
    private GameContext context;

    /**
     * Batch renderer for sprites and textures
     */
    private SpriteBatch batch;

    /**
     * Textures
     */
    private Texture menuUp;
    private Texture menuDown;
    private Texture lobbyUp;
    private Texture lobbyDown;
    private Texture mapUp;
    private Texture mapDown;
    
    private BitmapFont font;
    FreeTypeFontGenerator generator;
    private ShapeRenderer shapeRender;
    // reference coords - menu control
    private int MENU_REF_X       = 0;
    private int MENU_REF_Y       = 0;
    private int MENU_buttonX     = MENU_REF_X + 760;
    private int MENU_buttonY     = MENU_REF_Y + 360;

    private int MENU_tableX     = MENU_REF_X + 700;
    private int MENU_tableY     = MENU_REF_Y + 0;
    
    private int MENU_lobbyButtonX     = MENU_REF_X + 720;
    private int MENU_lobbyButtonY     = MENU_REF_Y + 322;
    
    private int MENU_mapsButtonX     = MENU_REF_X + 720;
    private int MENU_mapsButtonY     = MENU_REF_Y + 288;
    
	
    // DISENGAGE shapes
    Rectangle menuButton_Shape   = new Rectangle(MENU_buttonX+4, 13, 25, 25);
    Rectangle menuTable_Shape   = new Rectangle(MENU_tableX+10, 40, 90, 80);
    Rectangle menuLobby_Shape   = new Rectangle(MENU_lobbyButtonX, 50, 70, 22);
    Rectangle menuMap_Shape   = new Rectangle(MENU_mapsButtonX, 84, 70, 22);

    /**
     * state of buttons. true if pushed, false if not.
     */
    private boolean menuButtonIsDown = false; // initial
	private boolean menuLobbyIsDown = false; // initial
	private boolean menuMapsIsDown = false; // initial
    
    protected MenuComponent(GameContext context, SeaBattleScene owner) {
        super(context, owner);
        this.context = context;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRender = new ShapeRenderer();
        menuUp = context.getManager().get(context.getAssetObject().menuUp,Texture.class);
        menuDown = context.getManager().get(context.getAssetObject().menuDown,Texture.class);
        lobbyUp = context.getManager().get(context.getAssetObject().lobbyUp,Texture.class);
        lobbyDown = context.getManager().get(context.getAssetObject().lobbyDown,Texture.class);
        mapUp = context.getManager().get(context.getAssetObject().mapUp,Texture.class);
        mapDown = context.getManager().get(context.getAssetObject().mapDown,Texture.class);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/font/FjallaOne-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 10;
        parameter.shadowColor = new Color(0, 0, 0, 0.2f);
        parameter.shadowOffsetY = 1;
        font = generator.generateFont(parameter);
        font.setColor(Color.BLACK);
    
    }
    
    @Override
    public void update() {
    }

    @Override
    public void render() {
        batch.begin();
        batch.draw((menuButtonIsDown)?menuDown:menuUp, MENU_buttonX, MENU_buttonY);
        if(menuButtonIsDown == true) {
        	batch.draw((menuLobbyIsDown)?lobbyDown:lobbyUp, MENU_lobbyButtonX, MENU_lobbyButtonY);
        	font.draw(batch, "Lobby", MENU_lobbyButtonX+26, MENU_lobbyButtonY+22);
//        	batch.draw((menuMapsIsDown)?mapDown:mapUp, MENU_mapsButtonX, MENU_mapsButtonY);
//        	font.draw(batch, "Show Maps", MENU_mapsButtonX + 17, MENU_mapsButtonY+21);
        }
        batch.end();
    }

    @Override
    public void dispose() {
    }


    @Override
    public boolean handleClick(float x, float y, int button) {
    	if ((!menuButtonIsDown) && isClickingMenuButton(x,y)) {
            menuButtonIsDown = true;
            return true;
        }
    	else if(menuButtonIsDown && isClickingLobbyButton(x,y)){
    		menuLobbyIsDown = true;
    		context.disconnect();
    		return true;
    	}
    	else if(menuButtonIsDown && isClickingMapsButton(x,y)) {
    		menuMapsIsDown = true;
    		System.out.println("Map Clicked");
    		return true;
    	}
        else if(!isClickingMenuTable(x,y)){
        	menuButtonIsDown = false;
        	menuLobbyIsDown = false;
        	menuMapsIsDown = false;
        	return false;
        }
        else {
            return false;
        }
    }

    /**
     * return whether point is in rect or not.
     */
    private boolean isPointInRect(float mouseX, float mouseY, Rectangle rec) {
    	if (( mouseX >= rec.getMinX() && mouseX <= rec.getMaxX() )
    		   && ( mouseY >= rec.getMinY() && mouseY <= rec.getMaxY()))
    		   {
    			return true;
    		   }
    	else {
    		return false;
    	}
    }

    private boolean isClickingMenuButton(float x, float y) {
        return isPointInRect(x,y,menuButton_Shape);
    }
    
    private boolean isClickingMenuTable(float x, float y) {
        return isPointInRect(x,y,menuTable_Shape);
    }
    
    private boolean isClickingLobbyButton(float x, float y) {
        return isPointInRect(x,y,menuLobby_Shape);
    }
    
    private boolean isClickingMapsButton(float x, float y) {
        return isPointInRect(x,y,menuMap_Shape);
    }
    
    @Override
    public boolean handleDrag(float x, float y, float ix, float iy) {
        // deactivate it with no penalty to the user.
        return false;
    }

    @Override
    public boolean handleRelease(float x, float y, int button) {
    	menuMapsIsDown = false;
    	menuLobbyIsDown = false;
        return false;
    }

    public void resetPlacedMovesAfterTurn() {
        // fix stuck buttons if they were clicked across a turn
        // with no penalty to the user
        if (menuButtonIsDown) {
//            menuButtonIsDown = false;
        	menuMapsIsDown = false;
        	menuLobbyIsDown = false;
        }
    }

	@Override
	public boolean keyDown(int arg0) {
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		return false;
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		return false;
	}

	@Override
	public boolean handleMouseMove(float x, float y) {
		return false;
	}

	
	public static Texture createRoundPixMap(Color color, int width,
            int height, int cornerRadius) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.Alpha);
        pixmap.setBlending(Pixmap.Blending.None);
        pixmap.setColor(color.r,color.g,color.b,0.4f);
        pixmap.fillCircle(cornerRadius, cornerRadius, cornerRadius);
        pixmap.fillCircle(width - cornerRadius, cornerRadius, cornerRadius);
        pixmap.fillCircle(width - cornerRadius, height - cornerRadius, cornerRadius);
        pixmap.fillCircle(cornerRadius, height - cornerRadius, cornerRadius);
        pixmap.fillRectangle(0, cornerRadius, width, height - (cornerRadius * 2));
        pixmap.fillRectangle(cornerRadius, 0, width - (cornerRadius * 2), height);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }
}
