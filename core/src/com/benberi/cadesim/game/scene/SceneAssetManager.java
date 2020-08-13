package com.benberi.cadesim.game.scene;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class SceneAssetManager {
	public final AssetManager manager = new AssetManager();
	/*
	 * Ship Textures
	 */
	public String baghlahSkin = "assets/skin/ships/baghlah.png";
	public String blackshipSkin ="assets/skin/ships/blackship.png";
	public String dhowSkin = "assets/skin/ships/dhow.png";
	public String fanchuanSkin ="assets/skin/ships/fanchuan.png";
	public String grandfrigSkin = "assets/skin/ships/grandfrig.png";
	public String junkSkin ="assets/skin/ships/junk.png";
	public String lgsloopSkin = "assets/skin/ships/lgsloop.png";
	public String longshipSkin ="assets/skin/ships/longship.png";
	public String merchbrigSkin = "assets/skin/ships/merchbrig.png";
	public String merchgalSkin ="assets/skin/ships/merchgal.png";
	public String smsloopSkin = "assets/skin/ships/smsloop.png";
	public String warbrigSkin ="assets/skin/ships/warbrig.png";
	public String warfrigSkin = "assets/skin/ships/warfrig.png";
	public String wargalSkin ="assets/skin/ships/wargal.png";
	public String xebecSkin = "assets/skin/ships/xebec.png";
	
	public String baghlah = "assets/vessel/baghlah/sail.png";
	public String blackship ="assets/vessel/blackship/sail.png";
	public String dhow = "assets/vessel/dhow/sail.png";
	public String fanchuan ="assets/vessel/fanchuan/sail.png";
	public String grandfrig = "assets/vessel/grandfrig/sail.png";
	public String junk ="assets/vessel/junk/sail.png";
	public String lgsloop = "assets/vessel/lgsloop/sail.png";
	public String longship ="assets/vessel/longship/sail.png";
	public String merchbrig = "assets/vessel/merchbrig/sail.png";
	public String merchgal ="assets/vessel/merchgal/sail.png";
	public String smsloop = "assets/vessel/smsloop/sail.png";
	public String warbrig ="assets/vessel/warbrig/sail.png";
	public String warfrig = "assets/vessel/warfrig/sail.png";
	public String wargal ="assets/vessel/wargal/sail.png";
	public String xebec = "assets/vessel/xebec/sail.png";
	
	public String baghlah_sinking = "assets/vessel/baghlah/sink.png";
//	public String blackship_sinking ="assets/vessel/blackship/sink.png";
	public String dhow_sinking = "assets/vessel/dhow/sink.png";
	public String fanchuan_sinking ="assets/vessel/fanchuan/sink.png";
	public String grandfrig_sinking = "assets/vessel/grandfrig/sink.png";
	public String junk_sinking = "assets/vessel/junk/sink.png";
	public String lgsloop_sinking = "assets/vessel/lgsloop/sink.png";
	public String longship_sinking ="assets/vessel/longship/sink.png";
	public String merchbrig_sinking = "assets/vessel/merchbrig/sink.png";
	public String merchgal_sinking ="assets/vessel/merchgal/sink.png";
	public String smsloop_sinking = "assets/vessel/smsloop/sink.png";
	public String warbrig_sinking ="assets/vessel/warbrig/sink.png";
	public String warfrig_sinking = "assets/vessel/warfrig/sink.png";
	public String wargal_sinking ="assets/vessel/wargal/sink.png";
	public String xebec_sinking = "assets/vessel/xebec/sink.png";
	
	/*
	 * Map Textures
	 */
	public String cell = "assets/sea/cell.png";
	public String safe = "assets/sea/safezone.png";
	public String sea = "assets/sea/sea1.png";
	
	public String bigrock ="assets/sea/rocks_big.png";
	public String smallrock = "assets/sea/rocks_small.png";
	public String whirlpool = "assets/sea/whirl.png";
	public String wind = "assets/sea/wind.png";
	/*
	 * Cannon Textures
	 */
	public String cannonball_large = "assets/projectile/cannonball_large.png";
    public String cannonball_medium = "assets/projectile/cannonball_medium.png";
    public String cannonball_small = "assets/projectile/cannonball_small.png";
    public String splash_large = "assets/effects/splash_large.png";
    public String splash_small = "assets/effects/splash_small.png";
    public String explode_large = "assets/effects/explode_large.png";
    public String explode_medium = "assets/effects/explode_medium.png";
    public String explode_small = "assets/effects/explode_small.png";
    public String hit = "assets/effects/hit.png";
    
	/*
	 * Misc Textures
	 */
    public String infoPanel = "assets/ui/info.png";
    public String contenders = "assets/cade/contender_icons.png";
    public String flag = "assets/cade/buoy_symbols.png";
    public String flagTexture = "assets/cade/buoy.png";
    
    public String background = "assets/bg.png";
    public String textfieldTexture = "assets/skin/textfield.png";
    public String loginButton = "assets/skin/login.png";
    public String loginButtonHover = "assets/skin/login-hover.png";
    
    public String cursor = "assets/skin/textfield-cursor.png";
    public String selection  = "assets/skin/textfield-selection.png";
    
    public String battleSelection = "assets/skin/battle-textfield-selection.png";
    
    public String selectBoxBackground = "assets/skin/selectbg.png";
    public String selectBoxListSelection = "assets/skin/selectbg.png";
    public String selectBoxListBackground  = "assets/skin/select-list-bg.png";
    

    public String title = "assets/ui/title.png";
    public String radioOn = "assets/ui/radio-on.png";
    public String radioOff = "assets/ui/radio-off.png";
    public String radioOnDisable = "assets/ui/radio-on-disable.png";
    public String radioOffDisable = "assets/ui/radio-off-disable.png";
    public String autoOn = "assets/ui/auto-on.png";
    public String autoOff = "assets/ui/auto-off.png";
    public String autoBackground = "assets/ui/auto_background.png";
    public String sandTop = "assets/ui/sand_top.png";
    public String sandBottom = "assets/ui/sand_bot.png";
    public String sandTrickle = "assets/ui/sand_bot.png";
    public String cannonSlot = "assets/ui/cannonslots.png";
    public String moves = "assets/ui/move.png";
    public String emptyMoves = "assets/ui/move_empty.png";
    public String toolTipBackground = "assets/ui/tooltip_background.png";
    public String shipHand = "assets/ui/shiphand.png";
    public String hourGlass = "assets/ui/hourglass.png";
    public String controlBackground = "assets/ui/moves-background.png";
    public String shipStatus = "assets/ui/status.png";
    public String shipStatusBg = "assets/ui/status-bg.png";
    public String moveGetTarget = "assets/ui/sel_border_square.png";
    public String cannonSelectionEmpty = "assets/ui/grapplecannon_empty.png";
    public String cannonSelection = "assets/ui/grapplecannon.png";
    public String damage = "assets/ui/grapplecannon.png";
    public String bilge = "assets/ui/bilge.png";
    
    public String menuUp = "assets/ui/settings.png";
    public String menuDown = "assets/ui/settings.png";
    
    public String disengageUp = "assets/ui/disengage.png";
    public String disengageDown = "assets/ui/disengagePressed.png";
    public String disengageBackground = "assets/ui/center_background.png";
    
    public String chatBackground = "assets/ui/chat_background.png";
    public String chatBackgroundFrame = "assets/ui/chat_background_frame.png";
    public String chatIndicator  = "assets/ui/chat_indicator.png";
    public String chatBarBackground = "assets/ui/chat_bar_background.png";
    public String chatButtonSend = "assets/ui/chat_button_send.png";
    public String chatButtonSendPressed = "assets/ui/chat_button_sendPressed.png";
    
    public String chatScrollBarUp = "assets/ui/scrollbar_top.png";
    public String chatScrollBarUpPressed = "assets/ui/scrollbar_topPressed.png";
    public String chatScrollBarDown = "assets/ui/scrollbar_bottom.png";
    public String chatScrollBarDownPressed = "assets/ui/scrollbar_bottomPressed.png";
    public String chatScrollBarMiddle = "assets/ui/scrollbar_center.png";
    public String chatScrollBarScroll = "assets/ui/scrollbar_scroll.png";
    
    public String chatMessagePlayer = "assets/ui/chat_message_player.png";
    public String chatMessageServerBroadcast = "assets/ui/chat_message_server_broadcast.png";
    public String chatMessageServerPrivate = "assets/ui/chat_message_server_private.png";

    
    public void loadSeaBattle() {
    	manager.load(sea, Texture.class);
    	manager.load(cell, Texture.class);
    	manager.load(safe, Texture.class);
    	manager.load(bigrock, Texture.class);
    	manager.load(smallrock, Texture.class);
    	manager.load(whirlpool, Texture.class);
    	manager.load(wind, Texture.class);
    	manager.load(infoPanel, Texture.class);
    	manager.load(contenders, Texture.class);
    	manager.load(flag, Texture.class);
    	manager.load(flagTexture, Texture.class);
//    	manager.load(menuUp, Texture.class);
//    	manager.load(menuDown, Texture.class);
    	
    }
    
    public void loadShipInfo() {
    	manager.load(cannonball_large, Texture.class);
        manager.load(cannonball_medium, Texture.class);
        manager.load(cannonball_small, Texture.class);
        manager.load(splash_large, Texture.class);
        manager.load(splash_small, Texture.class);
        manager.load(explode_large, Texture.class);
        manager.load(explode_medium, Texture.class);
        manager.load(explode_small, Texture.class);
        manager.load(hit, Texture.class);

    }
    
    public void loadControl() {
        manager.load(title, Texture.class);
        manager.load(radioOn, Texture.class);
        manager.load(radioOff, Texture.class);
        manager.load(radioOnDisable, Texture.class);
        manager.load(radioOffDisable, Texture.class);
        manager.load(autoOn, Texture.class);
        manager.load(autoOff, Texture.class);
        manager.load(autoBackground, Texture.class);
        manager.load(sandTop, Texture.class);
        manager.load(sandBottom, Texture.class);
        manager.load(sandTrickle, Texture.class);
        manager.load(cannonSlot, Texture.class);
        manager.load(moves, Texture.class);
        manager.load(emptyMoves, Texture.class);
        manager.load(toolTipBackground, Texture.class);
        manager.load(shipHand, Texture.class);
        manager.load(hourGlass, Texture.class);
        manager.load(controlBackground, Texture.class);
        manager.load(shipStatus, Texture.class);
        manager.load(shipStatusBg, Texture.class);
        manager.load(moveGetTarget, Texture.class);
        manager.load(cannonSelectionEmpty, Texture.class);
        manager.load(cannonSelection, Texture.class);
        manager.load(damage, Texture.class);
        manager.load(bilge, Texture.class);
        
        manager.load(disengageUp, Texture.class);
        manager.load(disengageDown, Texture.class);
        manager.load(disengageBackground, Texture.class);
        
        manager.load(chatBackground, Texture.class);
        manager.load(chatBackgroundFrame, Texture.class);
        manager.load(chatIndicator, Texture.class);
        manager.load(chatBarBackground, Texture.class);
        manager.load(chatButtonSend, Texture.class);
        manager.load(chatButtonSendPressed, Texture.class);
        
        manager.load(chatScrollBarUp, Texture.class);
        manager.load(chatScrollBarUpPressed, Texture.class);
        manager.load(chatScrollBarDown, Texture.class);
        manager.load(chatScrollBarDownPressed, Texture.class);
        manager.load(chatScrollBarMiddle, Texture.class);
        manager.load(chatScrollBarScroll, Texture.class);
        
        manager.load(chatMessagePlayer, Texture.class);
        manager.load(chatMessageServerBroadcast, Texture.class);
        manager.load(chatMessageServerPrivate, Texture.class);
    }
    
	public void loadConnectSceneTextures() {
		manager.load(background, Texture.class);
		manager.load(textfieldTexture, Texture.class);
		manager.load(loginButton, Texture.class);
		manager.load(loginButtonHover, Texture.class);
		manager.load(cursor, Texture.class);
		manager.load(selection, Texture.class);
		manager.load(selectBoxBackground, Texture.class);
		manager.load(selectBoxListBackground, Texture.class);
		manager.load(selectBoxListSelection, Texture.class);
		loadSkinShipTexture();
		
	}
	
	public void loadAllShipTextures() {
		loadNormalShipTexture();
    	manager.load(baghlah_sinking, Texture.class);
    	manager.load(dhow_sinking, Texture.class);
    	manager.load(fanchuan_sinking, Texture.class);
    	manager.load(grandfrig_sinking, Texture.class);
    	manager.load(junk_sinking, Texture.class);
    	manager.load(lgsloop_sinking, Texture.class);
    	manager.load(longship_sinking, Texture.class);
    	manager.load(merchbrig_sinking, Texture.class);
    	manager.load(merchgal_sinking, Texture.class);
    	manager.load(smsloop_sinking, Texture.class);
    	manager.load(warbrig_sinking, Texture.class);
    	manager.load(warfrig_sinking, Texture.class);
    	manager.load(wargal_sinking, Texture.class);
    	manager.load(xebec_sinking, Texture.class);
	}
	
    public void loadNormalShipTexture() {
    	manager.load(baghlah, Texture.class);
    	manager.load(blackship, Texture.class);
    	manager.load(dhow, Texture.class);
    	manager.load(fanchuan, Texture.class);
    	manager.load(grandfrig, Texture.class);
    	manager.load(junk, Texture.class);
    	manager.load(lgsloop, Texture.class);
    	manager.load(longship, Texture.class);
    	manager.load(merchbrig, Texture.class);
    	manager.load(merchgal, Texture.class);
    	manager.load(smsloop, Texture.class);
    	manager.load(warbrig, Texture.class);
    	manager.load(warfrig, Texture.class);
    	manager.load(wargal, Texture.class);
    	manager.load(xebec, Texture.class);
	}
    
    private void loadSkinShipTexture() {
    	manager.load(baghlahSkin, Texture.class);
    	manager.load(blackshipSkin, Texture.class);
    	manager.load(dhowSkin, Texture.class);
    	manager.load(fanchuanSkin, Texture.class);
    	manager.load(grandfrigSkin, Texture.class);
    	manager.load(junkSkin, Texture.class);
    	manager.load(lgsloopSkin, Texture.class);
    	manager.load(longshipSkin, Texture.class);
    	manager.load(merchbrigSkin, Texture.class);
    	manager.load(merchgalSkin, Texture.class);
    	manager.load(smsloopSkin, Texture.class);
    	manager.load(warbrigSkin, Texture.class);
    	manager.load(warfrigSkin, Texture.class);
    	manager.load(wargalSkin, Texture.class);
    	manager.load(xebecSkin, Texture.class);
	}
}
