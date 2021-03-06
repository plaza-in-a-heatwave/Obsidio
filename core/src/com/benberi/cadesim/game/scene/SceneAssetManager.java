package com.benberi.cadesim.game.scene;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.assets.AssetDescriptor;

public class SceneAssetManager {
	public final AssetManager manager = new AssetManager();
	
	/*
	 * Parameters for fonts
	 */
	public  FreetypeFontLoader.FreeTypeFontLoaderParameter parameter = 
			new FreetypeFontLoader.FreeTypeFontLoaderParameter();
	public  FreetypeFontLoader.FreeTypeFontLoaderParameter parameterNotes = 
			new FreetypeFontLoader.FreeTypeFontLoaderParameter();
	public  FreetypeFontLoader.FreeTypeFontLoaderParameter parameterTitle = 
			new FreetypeFontLoader.FreeTypeFontLoaderParameter();
	public  FreetypeFontLoader.FreeTypeFontLoaderParameter parameterSea = 
			new FreetypeFontLoader.FreeTypeFontLoaderParameter();
	public  FreetypeFontLoader.FreeTypeFontLoaderParameter parameterControl = 
			new FreetypeFontLoader.FreeTypeFontLoaderParameter();
	public  FreetypeFontLoader.FreeTypeFontLoaderParameter parameterMessage = 
			new FreetypeFontLoader.FreeTypeFontLoaderParameter();
	public  FreetypeFontLoader.FreeTypeFontLoaderParameter parameterMenu = 
			new FreetypeFontLoader.FreeTypeFontLoaderParameter();
	
	
	public  FreetypeFontLoader.FreeTypeFontLoaderParameter parameterInfoTeam = 
			new FreetypeFontLoader.FreeTypeFontLoaderParameter();
	public  FreetypeFontLoader.FreeTypeFontLoaderParameter parameterInfoPoints = 
			new FreetypeFontLoader.FreeTypeFontLoaderParameter();
	public  FreetypeFontLoader.FreeTypeFontLoaderParameter parameterInfoTime = 
			new FreetypeFontLoader.FreeTypeFontLoaderParameter();
	public  FreetypeFontLoader.FreeTypeFontLoaderParameter parameterInfoBreak = 
			new FreetypeFontLoader.FreeTypeFontLoaderParameter();
	
	public FreetypeFontLoader.FreeTypeFontLoaderParameter parameterMessageFont = 
			new FreetypeFontLoader.FreeTypeFontLoaderParameter();
	
	/*
	 * Font names - used to call the font
	 */
	private String REGULARFONT = "fontRegular.ttf";
	private String TITLEFONT = "fontTitle.ttf";
	private String NOTESFONT = "fontNotes.ttf";
	private String SEAFONT = "fontSea.ttf";
	private String CONTROLFONT = "fontControl.ttf";
	private String CHATFONT = "fontChat.ttf";
	private String MENUFONT = "fontMenu.ttf";
    private String FONTTEAMATTACKER = "fontTeamAttacker.ttf";
    private String FONTTEAMDEFENDER = "fontTeamDefender.ttf";
    private String FONTTEAMATTACKER_POINTS = "fontTeamAttackerPoints.ttf";
    private String FONTTEAMDEFENDER_POINTS = "fontTeamDefenderPoints.ttf";
    private String FONTTIME = "fontTime.ttf";
    private String FONTBREAK = "fontBreak.ttf";
    private String CHATMESSAGEFONT = "fontChatMessage.ttf";
	
    /*
	 * Ship texture location
	 */
	private final static String BAGHLAHSKIN = "assets/skin/ships/baghlah.png";
	private final static String BLACKSHIPSKIN ="assets/skin/ships/blackship.png";
	private final static String DHOWSKIN = "assets/skin/ships/dhow.png";
	private final static String FANCHUANSKIN ="assets/skin/ships/fanchuan.png";
	private final static String GRANDFRIGSKIN = "assets/skin/ships/grandfrig.png";
	private final static String JUNKSKIN ="assets/skin/ships/junk.png";
	private final static String LGSLOOPSKIN = "assets/skin/ships/lgsloop.png";
	private final static String LONGSHIPSKIN ="assets/skin/ships/longship.png";
	private final static String MERCHBRIGSKIN = "assets/skin/ships/merchbrig.png";
	private final static String MERCHGALSKIN ="assets/skin/ships/merchgal.png";
	private final static String SMSLOOPSKIN = "assets/skin/ships/smsloop.png";
	private final static String WARBRIGSKIN ="assets/skin/ships/warbrig.png";
	private final static String WARFRIGSKIN = "assets/skin/ships/warfrig.png";
	private final static String WARGALSKIN ="assets/skin/ships/wargal.png";
	private final static String XEBECSKIN = "assets/skin/ships/xebec.png";
	
	private final static String BAGHLAH = "assets/vessel/baghlah/sail.png";
	private final static String BLACKSHIP ="assets/vessel/blackship/sail.png";
	private final static String DHOW = "assets/vessel/dhow/sail.png";
	private final static String FANCHUAN ="assets/vessel/fanchuan/sail.png";
	private final static String GRANDFRIG = "assets/vessel/grandfrig/sail.png";
	private final static String JUNK ="assets/vessel/junk/sail.png";
	private final static String LGSLOOP = "assets/vessel/lgsloop/sail.png";
	private final static String LONGSHIP ="assets/vessel/longship/sail.png";
	private final static String MERCHBRIG = "assets/vessel/merchbrig/sail.png";
	private final static String MERCHGAL ="assets/vessel/merchgal/sail.png";
	private final static String SMSLOOP = "assets/vessel/smsloop/sail.png";
	private final static String WARBRIG ="assets/vessel/warbrig/sail.png";
	private final static String WARFRIG = "assets/vessel/warfrig/sail.png";
	private final static String WARGAL ="assets/vessel/wargal/sail.png";
	private final static String XEBEC = "assets/vessel/xebec/sail.png";
	
	private final static String BAGHLAH_SINKING = "assets/vessel/baghlah/sink.png";
//	private final static String blackship_sinking ="assets/vessel/blackship/sink.png";
	private final static String DHOW_SINKING = "assets/vessel/dhow/sink.png";
	private final static String FANCHUAN_SINKING ="assets/vessel/fanchuan/sink.png";
	private final static String GRANDFRIG_SINKING = "assets/vessel/grandfrig/sink.png";
	private final static String JUNK_SINKING = "assets/vessel/junk/sink.png";
	private final static String LGSLOOP_SINKING = "assets/vessel/lgsloop/sink.png";
	private final static String LONGSHIP_SINKING ="assets/vessel/longship/sink.png";
	private final static String MERCHBRIG_SINKING = "assets/vessel/merchbrig/sink.png";
	private final static String MERCHGAL_SINKING ="assets/vessel/merchgal/sink.png";
	private final static String SMSLOOP_SINKING = "assets/vessel/smsloop/sink.png";
	private final static String WARBRIG_SINKING ="assets/vessel/warbrig/sink.png";
	private final static String WARFRIG_SINKING = "assets/vessel/warfrig/sink.png";
	private final static String WARGAL_SINKING ="assets/vessel/wargal/sink.png";
	private final static String XEBEC_SINKING = "assets/vessel/xebec/sink.png";
	
	/*
	 * Map texture location
	 */
	private final static String CELL = "assets/sea/cell.png";
	private final static String SAFE = "assets/sea/safezone.png";
	private final static String SEA = "assets/sea/sea1.png";
	
	private final static String BIGROCK ="assets/sea/rocks_big.png";
	private final static String SMALLROCK = "assets/sea/rocks_small.png";
	private final static String WHIRLPOOL = "assets/sea/whirl.png";
	private final static String WIND = "assets/sea/wind.png";
	/*
	 * Cannon texture location
	 */
	private final static String CANNONBALL_LARGE = "assets/projectile/cannonball_large.png";
    private final static String CANNONBALL_MEDIUM = "assets/projectile/cannonball_medium.png";
    private final static String CANNONBALL_SMALL = "assets/projectile/cannonball_small.png";
    private final static String SPLASH_LARGE = "assets/effects/splash_large.png";
    private final static String SPLASH_SMALL = "assets/effects/splash_small.png";
    private final static String EXPLODE_LARGE = "assets/effects/explode_large.png";
    private final static String EXPLODE_MEDIUM = "assets/effects/explode_medium.png";
    private final static String EXPLODE_SMALL = "assets/effects/explode_small.png";
    private final static String HIT = "assets/effects/hit.png";
    
	/*
	 * Misc texture location
	 */
    private final static String INFOPANEL = "assets/ui/info.png";
    private final static String CONTENDERS = "assets/cade/contender_icons.png";
    private final static String FLAG = "assets/cade/buoy_symbols.png";
    private final static String FLAGTEXTURE = "assets/cade/buoy.png";
    
    private final static String BACKGROUND = "assets/bg.png";
    private final static String TEXTFIELDTEXTURE = "assets/skin/textfield.png";
    private final static String LOGINBUTTON = "assets/skin/login.png";
    private final static String LOGINBUTTONHOVER = "assets/skin/login-hover.png";

    private final static String CURSOR = "assets/skin/textfield-cursor.png";
    private final static String SELECTION  = "assets/skin/textfield-selection.png";
    
    private final static String BATTLESELECTION = "assets/skin/battle-textfield-selection.png";
    
    private final static String SELECTBOXBACKGROUND = "assets/skin/selectbg.png";
    private final static String SELECTBOXLISTSELECTION = "assets/skin/selectbg.png";
    private final static String SELECTBOXLISTBACKGROUND  = "assets/skin/select-list-bg.png";
    

    private final static String TITLE = "assets/ui/title.png";
    private final static String RADIOON = "assets/ui/radio-on.png";
    private final static String RADIOOFF = "assets/ui/radio-off.png";
    private final static String RADIOONDISABLE = "assets/ui/radio-on-disable.png";
    private final static String RADIOOFFDISABLE = "assets/ui/radio-off-disable.png";
    private final static String AUTOON = "assets/ui/auto-on.png";
    private final static String AUTOOFF = "assets/ui/auto-off.png";
    private final static String AUTOBACKGROUND = "assets/ui/auto_background.png";
    private final static String SANDTOP = "assets/ui/sand_top.png";
    private final static String SANDBOTTOM = "assets/ui/sand_bot.png";
    private final static String SANDTRICKLE = "assets/ui/sand_trickle.png";
    private final static String CANNONSLOT = "assets/ui/cannonslots.png";
    private final static String MOVES = "assets/ui/move.png";
    private final static String EMPTYMOVES = "assets/ui/move_empty.png";
    private final static String TOOLTIPBACKGROUND = "assets/ui/tooltip_background.png";
    private final static String SHIPHAND = "assets/ui/shiphand.png";
    private final static String HOURGLASS = "assets/ui/hourglass.png";
    private final static String CONTROLBACKGROUND = "assets/ui/moves-background.png";
    private final static String SHIPSTATUS = "assets/ui/status.png";
    private final static String SHIPSTATUSBG = "assets/ui/status-bg.png";
    private final static String MOVEGETTARGET = "assets/ui/sel_border_square.png";
    private final static String CANNONSELECTIONEMPTY = "assets/ui/grapplecannon_empty.png";
    private final static String CANNONSELECTION = "assets/ui/grapplecannon.png";
    private final static String DAMAGE = "assets/ui/grapplecannon.png";
    private final static String BILGE = "assets/ui/bilge.png";
    
    private final static String MENUUP = "assets/ui/settings.png";
    private final static String MENUDOWN = "assets/ui/settings-disabled.png";
    private final static String LOBBYUP = "assets/ui/menu_button.png";
    private final static String LOBBYDOWN = "assets/ui/menu_button-disabled.png";
    private final static String MAPSUP = "assets/ui/menu_button.png";
    private final static String MAPSDOWN = "assets/ui/menu_button-disabled.png";
    
    private final static String DISENGAGEUP = "assets/ui/disengage.png";
    private final static String DISENGAGEDOWN = "assets/ui/disengagePressed.png";
    private final static String DISENGAGEBACKGROUND = "assets/ui/center_background.png";
    
    private final static String CHATBACKGROUND = "assets/ui/chat_background.png";
    private final static String CHATBACKGROUNDFRAME = "assets/ui/chat_background_frame.png";
    private final static String CHATINDICATOR  = "assets/ui/chat_indicator.png";
    private final static String CHATBARBACKGROUND = "assets/ui/chat_bar_background.png";
    private final static String CHATBUTTONSEND = "assets/ui/chat_button_send.png";
    private final static String CHATBUTTONSENDPRESSED = "assets/ui/chat_button_sendPressed.png";
    
    private final static String CHATSCROLLBARUP = "assets/ui/scrollbar_top.png";
    private final static String CHATSCROLLBARUPPRESSED = "assets/ui/scrollbar_topPressed.png";
    private final static String CHATSCROLLBARDOWN = "assets/ui/scrollbar_bottom.png";
    private final static String CHATSCROLLBARDOWNPRESSED = "assets/ui/scrollbar_bottomPressed.png";
    private final static String CHATSCROLLBARMIDDLE = "assets/ui/scrollbar_center.png";
    private final static String CHATSCROLLBARSCROLL = "assets/ui/scrollbar_scroll.png";
    
    private final static String CHATMESSAGEPLAYER = "assets/ui/chat_message_player.png";
    private final static String CHATMESSAGESERVERBROADCAST = "assets/ui/chat_message_server_broadcast.png";
    private final static String CHATMESSAGESERVERPRIVATE = "assets/ui/chat_message_server_private.png";

    
    /*
     * asset descriptors for textures
     */
	public AssetDescriptor<Texture> baghlahSkin = new AssetDescriptor<Texture>(BAGHLAHSKIN, Texture.class);
	public AssetDescriptor<Texture> blackshipSkin = new AssetDescriptor<Texture>(BLACKSHIPSKIN, Texture.class);
	public AssetDescriptor<Texture> dhowSkin = new AssetDescriptor<Texture>(DHOWSKIN, Texture.class);
	public AssetDescriptor<Texture> fanchuanSkin = new AssetDescriptor<Texture>(FANCHUANSKIN, Texture.class);
	public AssetDescriptor<Texture> grandfrigSkin = new AssetDescriptor<Texture>(GRANDFRIGSKIN, Texture.class);
	public AssetDescriptor<Texture> junkSkin = new AssetDescriptor<Texture>(JUNKSKIN, Texture.class);
	public AssetDescriptor<Texture> lgsloopSkin = new AssetDescriptor<Texture>(LGSLOOPSKIN, Texture.class);
	public AssetDescriptor<Texture> longshipSkin = new AssetDescriptor<Texture>(LONGSHIPSKIN, Texture.class);
	public AssetDescriptor<Texture> merchbrigSkin = new AssetDescriptor<Texture>(MERCHBRIGSKIN, Texture.class);
	public AssetDescriptor<Texture> merchgalSkin = new AssetDescriptor<Texture>(MERCHGALSKIN, Texture.class);
	public AssetDescriptor<Texture> smsloopSkin = new AssetDescriptor<Texture>(SMSLOOPSKIN, Texture.class);
	public AssetDescriptor<Texture> warbrigSkin = new AssetDescriptor<Texture>(WARBRIGSKIN, Texture.class);
	public AssetDescriptor<Texture> warfrigSkin = new AssetDescriptor<Texture>(WARFRIGSKIN, Texture.class);
	public AssetDescriptor<Texture> wargalSkin = new AssetDescriptor<Texture>(WARGALSKIN, Texture.class);
	public AssetDescriptor<Texture> xebecSkin = new AssetDescriptor<Texture>(XEBECSKIN, Texture.class);
	public AssetDescriptor<Texture> baghlah = new AssetDescriptor<Texture>(BAGHLAH, Texture.class);
	public AssetDescriptor<Texture> blackship = new AssetDescriptor<Texture>(BLACKSHIP, Texture.class);
	public AssetDescriptor<Texture> dhow  = new AssetDescriptor<Texture>(DHOW, Texture.class);
	public AssetDescriptor<Texture> fanchuan = new AssetDescriptor<Texture>(FANCHUAN, Texture.class);
	public AssetDescriptor<Texture> grandfrig = new AssetDescriptor<Texture>(GRANDFRIG, Texture.class);
	public AssetDescriptor<Texture> junk = new AssetDescriptor<Texture>(JUNK, Texture.class);
	public AssetDescriptor<Texture> lgsloop = new AssetDescriptor<Texture>(LGSLOOP, Texture.class);
	public AssetDescriptor<Texture> longship = new AssetDescriptor<Texture>(LONGSHIP, Texture.class);
	public AssetDescriptor<Texture> merchbrig = new AssetDescriptor<Texture>(MERCHBRIG, Texture.class);
	public AssetDescriptor<Texture> merchgal = new AssetDescriptor<Texture>(MERCHGAL, Texture.class);
	public AssetDescriptor<Texture> smsloop = new AssetDescriptor<Texture>(SMSLOOP, Texture.class);
	public AssetDescriptor<Texture> warbrig = new AssetDescriptor<Texture>(WARBRIG, Texture.class);
	public AssetDescriptor<Texture> warfrig = new AssetDescriptor<Texture>(WARFRIG, Texture.class);
	public AssetDescriptor<Texture> wargal = new AssetDescriptor<Texture>(WARGAL, Texture.class);
	public AssetDescriptor<Texture> xebec = new AssetDescriptor<Texture>(XEBEC, Texture.class);
	public AssetDescriptor<Texture> baghlah_sinking = new AssetDescriptor<Texture>(BAGHLAH_SINKING, Texture.class);
	public AssetDescriptor<Texture> dhow_sinking = new AssetDescriptor<Texture>(DHOW_SINKING, Texture.class);
	public AssetDescriptor<Texture> fanchuan_sinking = new AssetDescriptor<Texture>(FANCHUAN_SINKING, Texture.class);
	public AssetDescriptor<Texture> grandfrig_sinking = new AssetDescriptor<Texture>(GRANDFRIG_SINKING, Texture.class);
	public AssetDescriptor<Texture> junk_sinking = new AssetDescriptor<Texture>(JUNK_SINKING, Texture.class);
	public AssetDescriptor<Texture> lgsloop_sinking = new AssetDescriptor<Texture>(LGSLOOP_SINKING, Texture.class);
	public AssetDescriptor<Texture> longship_sinking = new AssetDescriptor<Texture>(LONGSHIP_SINKING, Texture.class);
	public AssetDescriptor<Texture> merchbrig_sinking = new AssetDescriptor<Texture>(MERCHBRIG_SINKING, Texture.class);
	public AssetDescriptor<Texture> merchgal_sinking = new AssetDescriptor<Texture>(MERCHGAL_SINKING, Texture.class);
	public AssetDescriptor<Texture> smsloop_sinking = new AssetDescriptor<Texture>(SMSLOOP_SINKING, Texture.class);
	public AssetDescriptor<Texture> warbrig_sinking = new AssetDescriptor<Texture>(WARBRIG_SINKING, Texture.class);
	public AssetDescriptor<Texture> warfrig_sinking = new AssetDescriptor<Texture>(WARFRIG_SINKING, Texture.class);
	public AssetDescriptor<Texture> wargal_sinking = new AssetDescriptor<Texture>(WARGAL_SINKING, Texture.class);
	public AssetDescriptor<Texture> xebec_sinking = new AssetDescriptor<Texture>(XEBEC_SINKING, Texture.class);
	
	/*
	 * Map Textures
	 */
	public AssetDescriptor<Texture> cell = new AssetDescriptor<Texture>(CELL, Texture.class);
	public AssetDescriptor<Texture> safe = new AssetDescriptor<Texture>(SAFE, Texture.class);
	public AssetDescriptor<Texture> sea = new AssetDescriptor<Texture>(SEA, Texture.class);
	
	public AssetDescriptor<Texture> bigrock = new AssetDescriptor<Texture>(BIGROCK, Texture.class);
	public AssetDescriptor<Texture> smallrock = new AssetDescriptor<Texture>(SMALLROCK, Texture.class);
	public AssetDescriptor<Texture> whirlpool = new AssetDescriptor<Texture>(WHIRLPOOL, Texture.class);
	public AssetDescriptor<Texture> wind = new AssetDescriptor<Texture>(WIND, Texture.class);

	public AssetDescriptor<Texture> cannonball_large = new AssetDescriptor<Texture>(CANNONBALL_LARGE, Texture.class);
    public AssetDescriptor<Texture> cannonball_medium = new AssetDescriptor<Texture>(CANNONBALL_MEDIUM, Texture.class);
    public AssetDescriptor<Texture> cannonball_small = new AssetDescriptor<Texture>(CANNONBALL_SMALL, Texture.class);
    public AssetDescriptor<Texture> splash_large = new AssetDescriptor<Texture>(SPLASH_LARGE, Texture.class);
    public AssetDescriptor<Texture> splash_small = new AssetDescriptor<Texture>(SPLASH_SMALL, Texture.class);
    public AssetDescriptor<Texture> explode_large = new AssetDescriptor<Texture>(EXPLODE_LARGE, Texture.class);
    public AssetDescriptor<Texture> explode_medium = new AssetDescriptor<Texture>(EXPLODE_MEDIUM, Texture.class);
    public AssetDescriptor<Texture> explode_small = new AssetDescriptor<Texture>(EXPLODE_SMALL, Texture.class);
    public AssetDescriptor<Texture> hit = new AssetDescriptor<Texture>(HIT, Texture.class);

    public AssetDescriptor<Texture> infoPanel = new AssetDescriptor<Texture>(INFOPANEL, Texture.class);
    public AssetDescriptor<Texture> contenders = new AssetDescriptor<Texture>(CONTENDERS, Texture.class);
    public AssetDescriptor<Texture> flag = new AssetDescriptor<Texture>(FLAG, Texture.class);
    public AssetDescriptor<Texture> flagTexture = new AssetDescriptor<Texture>(FLAGTEXTURE, Texture.class);
    
    public AssetDescriptor<Texture> background = new AssetDescriptor<Texture>(BACKGROUND, Texture.class);
    public AssetDescriptor<Texture> textfieldTexture = new AssetDescriptor<Texture>(TEXTFIELDTEXTURE, Texture.class);
    public AssetDescriptor<Texture> loginButton = new AssetDescriptor<Texture>(LOGINBUTTON, Texture.class);
    public AssetDescriptor<Texture> loginButtonHover = new AssetDescriptor<Texture>(LOGINBUTTONHOVER, Texture.class);
    
    public AssetDescriptor<Texture> cursor = new AssetDescriptor<Texture>(CURSOR, Texture.class);
    public AssetDescriptor<Texture> selection = new AssetDescriptor<Texture>(SELECTION, Texture.class);
    public AssetDescriptor<Texture> battleSelection = new AssetDescriptor<Texture>(BATTLESELECTION, Texture.class);
    
    public AssetDescriptor<Texture> selectBoxBackground = new AssetDescriptor<Texture>(SELECTBOXBACKGROUND, Texture.class);
    public AssetDescriptor<Texture> selectBoxListSelection = new AssetDescriptor<Texture>(SELECTBOXLISTSELECTION, Texture.class);
    public AssetDescriptor<Texture> selectBoxListBackground = new AssetDescriptor<Texture>(SELECTBOXLISTBACKGROUND, Texture.class);
   
    public AssetDescriptor<Texture> title = new AssetDescriptor<Texture>(TITLE, Texture.class);
    public AssetDescriptor<Texture> radioOn = new AssetDescriptor<Texture>(RADIOON, Texture.class);
    public AssetDescriptor<Texture> radioOff = new AssetDescriptor<Texture>(RADIOOFF, Texture.class);
    public AssetDescriptor<Texture> radioOnDisable = new AssetDescriptor<Texture>(RADIOONDISABLE, Texture.class);
    public AssetDescriptor<Texture> radioOffDisable = new AssetDescriptor<Texture>(RADIOOFFDISABLE, Texture.class);
    public AssetDescriptor<Texture> autoOn = new AssetDescriptor<Texture>(AUTOON, Texture.class);
    public AssetDescriptor<Texture> autoOff = new AssetDescriptor<Texture>(AUTOOFF, Texture.class);
    public AssetDescriptor<Texture> autoBackground = new AssetDescriptor<Texture>(AUTOBACKGROUND, Texture.class);
    public AssetDescriptor<Texture> sandTop = new AssetDescriptor<Texture>(SANDTOP, Texture.class);
    public AssetDescriptor<Texture> sandBottom = new AssetDescriptor<Texture>(SANDBOTTOM, Texture.class);
    public AssetDescriptor<Texture> sandTrickle = new AssetDescriptor<Texture>(SANDTRICKLE, Texture.class);
    public AssetDescriptor<Texture> cannonSlot = new AssetDescriptor<Texture>(CANNONSLOT, Texture.class);
    public AssetDescriptor<Texture> moves = new AssetDescriptor<Texture>(MOVES, Texture.class);
    public AssetDescriptor<Texture> emptyMoves = new AssetDescriptor<Texture>(EMPTYMOVES, Texture.class);
    public AssetDescriptor<Texture> toolTipBackground = new AssetDescriptor<Texture>(TOOLTIPBACKGROUND, Texture.class);
    public AssetDescriptor<Texture> shipHand = new AssetDescriptor<Texture>(SHIPHAND, Texture.class);
    public AssetDescriptor<Texture> hourGlass = new AssetDescriptor<Texture>(HOURGLASS, Texture.class);
    public AssetDescriptor<Texture> controlBackground = new AssetDescriptor<Texture>(CONTROLBACKGROUND, Texture.class);
    public AssetDescriptor<Texture> shipStatus = new AssetDescriptor<Texture>(SHIPSTATUS, Texture.class);
    public AssetDescriptor<Texture> shipStatusBg = new AssetDescriptor<Texture>(SHIPSTATUSBG, Texture.class);
    public AssetDescriptor<Texture> moveGetTarget = new AssetDescriptor<Texture>(MOVEGETTARGET, Texture.class);
    public AssetDescriptor<Texture> cannonSelectionEmpty = new AssetDescriptor<Texture>(CANNONSELECTIONEMPTY, Texture.class);
    public AssetDescriptor<Texture> cannonSelection = new AssetDescriptor<Texture>(CANNONSELECTION, Texture.class);
    public AssetDescriptor<Texture> damage = new AssetDescriptor<Texture>(DAMAGE, Texture.class);
    public AssetDescriptor<Texture> bilge = new AssetDescriptor<Texture>(BILGE, Texture.class);
    
    public AssetDescriptor<Texture> menuUp = new AssetDescriptor<Texture>(MENUUP, Texture.class);
    public AssetDescriptor<Texture> menuDown = new AssetDescriptor<Texture>(MENUDOWN, Texture.class);
    public AssetDescriptor<Texture> lobbyUp = new AssetDescriptor<Texture>(LOBBYUP, Texture.class);
    public AssetDescriptor<Texture> lobbyDown = new AssetDescriptor<Texture>(LOBBYDOWN, Texture.class);
    public AssetDescriptor<Texture> mapsUp = new AssetDescriptor<Texture>(MAPSUP, Texture.class);
    public AssetDescriptor<Texture> mapsDown = new AssetDescriptor<Texture>(MAPSDOWN, Texture.class);
    
    public AssetDescriptor<Texture> disengageUp = new AssetDescriptor<Texture>(DISENGAGEUP, Texture.class);
    public AssetDescriptor<Texture> disengageDown = new AssetDescriptor<Texture>(DISENGAGEDOWN, Texture.class);
    public AssetDescriptor<Texture> disengageBackground = new AssetDescriptor<Texture>(DISENGAGEBACKGROUND, Texture.class);
    
    public AssetDescriptor<Texture> chatBackground = new AssetDescriptor<Texture>(CHATBACKGROUND, Texture.class);
    public AssetDescriptor<Texture> chatBackgroundFrame = new AssetDescriptor<Texture>(CHATBACKGROUNDFRAME, Texture.class);
    public AssetDescriptor<Texture> chatIndicator = new AssetDescriptor<Texture>(CHATINDICATOR, Texture.class);
    public AssetDescriptor<Texture> chatBarBackground = new AssetDescriptor<Texture>(CHATBARBACKGROUND, Texture.class);
    public AssetDescriptor<Texture> chatButtonSend = new AssetDescriptor<Texture>(CHATBUTTONSEND, Texture.class);
    public AssetDescriptor<Texture> chatButtonSendPressed = new AssetDescriptor<Texture>(CHATBUTTONSENDPRESSED, Texture.class);
    
    public AssetDescriptor<Texture> chatScrollBarUp = new AssetDescriptor<Texture>(CHATSCROLLBARUP, Texture.class);
    public AssetDescriptor<Texture> chatScrollBarUpPressed = new AssetDescriptor<Texture>(CHATSCROLLBARUPPRESSED, Texture.class);
    public AssetDescriptor<Texture> chatScrollBarDown = new AssetDescriptor<Texture>(CHATSCROLLBARDOWN, Texture.class);
    public AssetDescriptor<Texture> chatScrollBarDownPressed = new AssetDescriptor<Texture>(CHATSCROLLBARDOWNPRESSED, Texture.class);
    public AssetDescriptor<Texture> chatScrollBarMiddle = new AssetDescriptor<Texture>(CHATSCROLLBARMIDDLE, Texture.class);
    public AssetDescriptor<Texture> chatScrollBarScroll = new AssetDescriptor<Texture>(CHATSCROLLBARSCROLL, Texture.class);
    
    public AssetDescriptor<Texture> chatMessagePlayer= new AssetDescriptor<Texture>(CHATMESSAGEPLAYER, Texture.class);
    public AssetDescriptor<Texture> chatMessageServerBroadcast= new AssetDescriptor<Texture>(CHATMESSAGESERVERBROADCAST, Texture.class);
    public AssetDescriptor<Texture> chatMessageServerPrivate= new AssetDescriptor<Texture>(CHATMESSAGESERVERPRIVATE, Texture.class);

	/*
	 * Font asset descriptors
	 */
	public AssetDescriptor<BitmapFont> regularFont;
	public AssetDescriptor<BitmapFont> titleFont;
	public AssetDescriptor<BitmapFont> notesFont;
	public AssetDescriptor<BitmapFont> seaFont;
	public AssetDescriptor<BitmapFont> controlFont;
	public AssetDescriptor<BitmapFont> chatFont;
	public AssetDescriptor<BitmapFont> menuFont;
	
	public AssetDescriptor<BitmapFont> fontTeamAttacker;
	public AssetDescriptor<BitmapFont> fontTeamDefender;
	public AssetDescriptor<BitmapFont> fontTeamAttacker_Points;
	public AssetDescriptor<BitmapFont> fontTeamDefender_Points;
	public AssetDescriptor<BitmapFont> fontTime;
	public AssetDescriptor<BitmapFont> fontBreak;
	
	public AssetDescriptor<BitmapFont> chatMessageFont;
	
    public void loadSeaBattle() {
    	manager.load(sea);
    	manager.load(cell);
    	manager.load(safe);
    	manager.load(bigrock);
    	manager.load(smallrock);
    	manager.load(whirlpool);
    	manager.load(wind);
    	manager.load(infoPanel);
    	manager.load(contenders);
    	manager.load(flag);
    	manager.load(flagTexture);
    	manager.load(menuUp);
    	manager.load(menuDown);
    	manager.load(lobbyUp);
    	manager.load(lobbyDown);
    	manager.load(mapsUp);
    	manager.load(mapsDown);
    	manager.load(battleSelection);
    	
    }
    
    public void loadShipInfo() {
    	manager.load(cannonball_large);
        manager.load(cannonball_medium);
        manager.load(cannonball_small);
        manager.load(splash_large);
        manager.load(splash_small);
        manager.load(explode_large);
        manager.load(explode_medium);
        manager.load(explode_small);
        manager.load(hit);

    }
    
    public void loadControl() {
        manager.load(title);
        manager.load(radioOn);
        manager.load(radioOff);
        manager.load(radioOnDisable);
        manager.load(radioOffDisable);
        manager.load(autoOn);
        manager.load(autoOff);
        manager.load(autoBackground);
        manager.load(sandTop);
        manager.load(sandBottom);
        manager.load(sandTrickle);
        manager.load(cannonSlot);
        manager.load(moves);
        manager.load(emptyMoves);
        manager.load(toolTipBackground);
        manager.load(shipHand);
        manager.load(hourGlass);
        manager.load(controlBackground);
        manager.load(shipStatus);
        manager.load(shipStatusBg);
        manager.load(moveGetTarget);
        manager.load(cannonSelectionEmpty);
        manager.load(cannonSelection);
        manager.load(damage);
        manager.load(bilge);
        
        manager.load(disengageUp);
        manager.load(disengageDown);
        manager.load(disengageBackground);
        
        manager.load(chatBackground);
        manager.load(chatBackgroundFrame);
        manager.load(chatIndicator);
        manager.load(chatBarBackground);
        manager.load(chatButtonSend);
        manager.load(chatButtonSendPressed);
        
        manager.load(chatScrollBarUp);
        manager.load(chatScrollBarUpPressed);
        manager.load(chatScrollBarDown);
        manager.load(chatScrollBarDownPressed);
        manager.load(chatScrollBarMiddle);
        manager.load(chatScrollBarScroll);
        
        manager.load(chatMessagePlayer);
        manager.load(chatMessageServerBroadcast);
        manager.load(chatMessageServerPrivate);
    }
    
	public void loadConnectSceneTextures() {
		manager.load(background);
		manager.load(textfieldTexture);
		manager.load(loginButton);
		manager.load(loginButtonHover);
		manager.load(cursor);
		manager.load(selection);
		manager.load(selectBoxBackground);
		manager.load(selectBoxListBackground);
		manager.load(selectBoxListSelection);
		loadSkinShipTexture();
		
	}
	
	public void loadAllShipTextures() {
		loadNormalShipTexture();
    	manager.load(baghlah_sinking);
    	manager.load(dhow_sinking);
    	manager.load(fanchuan_sinking);
    	manager.load(grandfrig_sinking);
    	manager.load(junk_sinking);
    	manager.load(lgsloop_sinking);
    	manager.load(longship_sinking);
    	manager.load(merchbrig_sinking);
    	manager.load(merchgal_sinking);
    	manager.load(smsloop_sinking);
    	manager.load(warbrig_sinking);
    	manager.load(warfrig_sinking);
    	manager.load(wargal_sinking);
    	manager.load(xebec_sinking);
	}
	
    public void loadNormalShipTexture() {
    	manager.load(baghlah);
    	manager.load(blackship);
    	manager.load(dhow);
    	manager.load(fanchuan);
    	manager.load(grandfrig);
    	manager.load(junk);
    	manager.load(lgsloop);
    	manager.load(longship);
    	manager.load(merchbrig);
    	manager.load(merchgal);
    	manager.load(smsloop);
    	manager.load(warbrig);
    	manager.load(warfrig);
    	manager.load(wargal);
    	manager.load(xebec);
	}
    
    private void loadSkinShipTexture() {
    	manager.load(baghlahSkin);
    	manager.load(blackshipSkin);
    	manager.load(dhowSkin);
    	manager.load(fanchuanSkin);
    	manager.load(grandfrigSkin);
    	manager.load(junkSkin);
    	manager.load(lgsloopSkin);
    	manager.load(longshipSkin);
    	manager.load(merchbrigSkin);
    	manager.load(merchgalSkin);
    	manager.load(smsloopSkin);
    	manager.load(warbrigSkin);
    	manager.load(warfrigSkin);
    	manager.load(wargalSkin);
    	manager.load(xebecSkin);
	}
    
	public void loadFonts() {
		FileHandleResolver resolver = new InternalFileHandleResolver();
		manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        
        parameter.fontFileName = "assets/font/FjallaOne-Regular.ttf";
        parameter.fontParameters.size = 18;
        parameter.fontParameters.color = new Color(Color.WHITE);
        parameter.fontParameters.shadowColor = new Color(0, 0, 0, 0.8f);
        parameter.fontParameters.shadowOffsetY = 1;
        //load regular font
        regularFont = new AssetDescriptor<BitmapFont>(REGULARFONT, BitmapFont.class, parameter);
        manager.load(regularFont);
        
        parameterNotes.fontFileName = "assets/font/FjallaOne-Regular.ttf";
        parameterNotes.fontParameters.size = 11;
        parameterNotes.fontParameters.color = new Color(Color.WHITE);
        parameterNotes.fontParameters.shadowColor = new Color(0, 0, 0, 0.8f);
        parameterNotes.fontParameters.shadowOffsetX = 0;
        parameterNotes.fontParameters.shadowOffsetY = 0;
        //load note font
        notesFont = new AssetDescriptor<BitmapFont>(NOTESFONT, BitmapFont.class, parameterNotes);
        manager.load(notesFont);
        
        parameterTitle.fontFileName = "assets/font/Open_Sans/OpenSans-SemiBold.ttf";
        parameterTitle.fontParameters.size = 46;
        parameterTitle.fontParameters.color = new Color(Color.WHITE);
        parameterTitle.fontParameters.gamma = 0.9f;
        
        titleFont = new AssetDescriptor<BitmapFont>(TITLEFONT, BitmapFont.class, parameterTitle);
        manager.load(titleFont);
        
        parameterSea.fontFileName = "assets/font/Roboto-Regular.ttf";
        parameterSea.fontParameters.size = 10;
        parameterSea.fontParameters.spaceX = 0;
        parameterSea.fontParameters.shadowColor = new Color(0, 0, 0, 0.5f);
        parameterSea.fontParameters.borderColor = Color.BLACK;
        parameterSea.fontParameters.borderWidth = 1;
        parameterSea.fontParameters.borderStraight = true;
        parameterSea.fontParameters.shadowOffsetY = 1;
        parameterSea.fontParameters.shadowOffsetX = 1;
        
        seaFont = new AssetDescriptor<BitmapFont>(SEAFONT, BitmapFont.class, parameterSea);
        manager.load(seaFont);
        
        parameterControl.fontFileName = "assets/font/Roboto-Regular.ttf";
        parameterControl.fontParameters.size = 12;
        
        controlFont = new AssetDescriptor<BitmapFont>(CONTROLFONT, BitmapFont.class, parameterControl);
        manager.load(controlFont);
        

        parameterMessage.fontFileName = "assets/font/Roboto-Regular.ttf";
        parameterMessage.fontParameters.size = 11;
        
        chatFont = new AssetDescriptor<BitmapFont>(CHATFONT, BitmapFont.class, parameterMessage);
        manager.load(chatFont);
        
        parameterMenu.fontFileName = "assets/font/FjallaOne-Regular.ttf";
        parameterMenu.fontParameters.size = 10;
        parameterMenu.fontParameters.shadowColor = new Color(0, 0, 0, 0.2f);
        parameterMenu.fontParameters.shadowOffsetY = 1;
        parameterMenu.fontParameters.color = Color.BLACK;
        
        menuFont = new AssetDescriptor<BitmapFont>(MENUFONT, BitmapFont.class, parameterMenu);
        manager.load(menuFont);
    
        parameterInfoTeam.fontFileName = "assets/font/FjallaOne-Regular.ttf";
        parameterInfoTeam.fontParameters.size = 14;
        parameterInfoTeam.fontParameters.shadowColor = new Color(0, 0, 0, 0.8f);
        parameterInfoTeam.fontParameters.shadowOffsetY = 1;
        
        fontTeamAttacker = new AssetDescriptor<BitmapFont>(FONTTEAMATTACKER, BitmapFont.class, parameterInfoTeam);
        manager.load(fontTeamAttacker);
        
        fontTeamDefender = new AssetDescriptor<BitmapFont>(FONTTEAMDEFENDER, BitmapFont.class, parameterInfoTeam);
        manager.load(fontTeamDefender);
        
        parameterInfoPoints.fontFileName = "assets/font/FjallaOne-Regular.ttf";
        parameterInfoPoints.fontParameters.size = 13;
        parameterInfoPoints.fontParameters.shadowColor = new Color(0, 0, 0, 0.6f);
        parameterInfoPoints.fontParameters.shadowOffsetY = 1;
        
        fontTeamAttacker_Points = new AssetDescriptor<BitmapFont>(FONTTEAMATTACKER_POINTS, BitmapFont.class, parameterInfoPoints);
        manager.load(fontTeamAttacker_Points);
        
        fontTeamDefender_Points = new AssetDescriptor<BitmapFont>(FONTTEAMDEFENDER_POINTS, BitmapFont.class, parameterInfoPoints);
        manager.load(fontTeamDefender_Points);
        
        parameterInfoTime.fontFileName = "assets/font/BreeSerif-Regular.ttf";
        parameterInfoTime.fontParameters.size = 30;
        parameterInfoTime.fontParameters.color = new Color(1, 230 / 255f, 59 / 255f, 1);
        parameterInfoTime.fontParameters.shadowColor = new Color(0, 0, 0, 0.3f);
        parameterInfoTime.fontParameters.shadowOffsetY = 2;     
        
        fontTime = new AssetDescriptor<BitmapFont>(FONTTIME, BitmapFont.class, parameterInfoTime);
        manager.load(fontTime);
        
        parameterInfoBreak.fontFileName = "assets/font/Roboto-Regular.ttf";
        parameterInfoBreak.fontParameters.size = 13;
        parameterInfoBreak.fontParameters.color = new Color(1, 230 / 255f, 59 / 255f, 1);
        parameterInfoBreak.fontParameters.shadowColor = new Color(0, 0, 0, 0.3f);
        parameterInfoBreak.fontParameters.shadowOffsetY = 2;       
        fontBreak = new AssetDescriptor<BitmapFont>(FONTBREAK, BitmapFont.class, parameterInfoBreak);
        manager.load(fontBreak);
       
        parameterMessageFont.fontFileName = "assets/font/Roboto-Regular.ttf";
        parameterMessageFont.fontParameters.size = 11;
        
        chatMessageFont = new AssetDescriptor<BitmapFont>(CHATMESSAGEFONT, BitmapFont.class, parameterMessageFont);
        manager.load(chatMessageFont);


	}
	
}
