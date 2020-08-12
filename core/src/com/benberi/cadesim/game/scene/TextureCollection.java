package com.benberi.cadesim.game.scene;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.benberi.cadesim.GameContext;
import com.benberi.cadesim.game.cade.Team;

public class TextureCollection {

    /**
     * Sea tile textures
     */
    private Map<String, Texture> seaTiles = new HashMap<String, Texture>();

    /**
     * Vessel spritesheet textures
     */
    private Map<String, Texture> vessels = new HashMap<String, Texture>();

    private Map<String, Texture> misc = new HashMap<String, Texture>();

    private GameContext context;

    public TextureCollection(GameContext context) {
        this.context = context;
    }

    /**
     * Creates all textures
     */
    public void create() {
        createSeaTiles();
        createVessels();
        createMisc();
    }

    /**
     * Gets a sea tile
     * @param index The index
     * @return  A texture of the tile
     */
    public Texture getSeaTile(String index) {
        return seaTiles.get(index);
    }

    /**
     * Gets a vessel spritesheet texture
     * @param index The index
     * @return A texture of the vessel spritesheet
     */
    public Texture getVessel(String index) {
        return vessels.get(index);
    }

    /**
     * Gets misc spritesheet texture
     * @param index The index
     * @return A misc texture
     */
    public Texture getMisc(String index) {
        return misc.get(index);
    }

    /**
     * Creates vessel textures
     */
    private void createVessels() {
    	// sail textures
    	vessels.put("baghlah", context.getManager().get(context.getAssetObject().baghlah,Texture.class));
    	vessels.put("blackship", context.getManager().get(context.getAssetObject().blackship,Texture.class));
    	vessels.put("dhow", context.getManager().get(context.getAssetObject().dhow,Texture.class));
    	vessels.put("fanchuan", context.getManager().get(context.getAssetObject().fanchuan,Texture.class));
    	vessels.put("grandfrig", context.getManager().get(context.getAssetObject().grandfrig,Texture.class));
    	vessels.put("junk", context.getManager().get(context.getAssetObject().junk,Texture.class));
    	vessels.put("lgsloop", context.getManager().get(context.getAssetObject().lgsloop,Texture.class));
    	vessels.put("longship", context.getManager().get(context.getAssetObject().longship,Texture.class));
    	vessels.put("merchbrig", context.getManager().get(context.getAssetObject().merchbrig,Texture.class));
    	vessels.put("merchgal", context.getManager().get(context.getAssetObject().merchgal,Texture.class));
    	vessels.put("smsloop", context.getManager().get(context.getAssetObject().smsloop,Texture.class));
    	vessels.put("warbrig", context.getManager().get(context.getAssetObject().warbrig,Texture.class));
    	vessels.put("warfrig", context.getManager().get(context.getAssetObject().warfrig,Texture.class));
    	vessels.put("wargal", context.getManager().get(context.getAssetObject().wargal,Texture.class));
    	vessels.put("xebec", context.getManager().get(context.getAssetObject().xebec_sinking,Texture.class));

    	// sink textures (the Black Ship doesn't sink!)
    	vessels.put("baghlah_sinking", context.getManager().get(context.getAssetObject().baghlah_sinking,Texture.class));
    	vessels.put("dhow_sinking", context.getManager().get(context.getAssetObject().dhow_sinking,Texture.class));
    	vessels.put("fanchuan_sinking", context.getManager().get(context.getAssetObject().fanchuan_sinking,Texture.class));
    	vessels.put("grandfrig_sinking", context.getManager().get(context.getAssetObject().grandfrig_sinking,Texture.class));
    	vessels.put("junk_sinking", context.getManager().get(context.getAssetObject().junk_sinking,Texture.class));
    	vessels.put("lgsloop_sinking", context.getManager().get(context.getAssetObject().lgsloop_sinking,Texture.class));
    	vessels.put("longship_sinking", context.getManager().get(context.getAssetObject().longship_sinking,Texture.class));
    	vessels.put("merchbrig_sinking", context.getManager().get(context.getAssetObject().merchbrig_sinking,Texture.class));
    	vessels.put("merchgal_sinking", context.getManager().get(context.getAssetObject().merchgal_sinking,Texture.class));
    	vessels.put("smsloop_sinking", context.getManager().get(context.getAssetObject().smsloop_sinking,Texture.class));
    	vessels.put("warbrig_sinking", context.getManager().get(context.getAssetObject().warbrig_sinking,Texture.class));
    	vessels.put("warfrig_sinking", context.getManager().get(context.getAssetObject().warfrig_sinking,Texture.class));
    	vessels.put("wargal_sinking", context.getManager().get(context.getAssetObject().wargal_sinking,Texture.class));
    	vessels.put("xebec_sinking", context.getManager().get(context.getAssetObject().xebec_sinking,Texture.class));
    }

    /**
     * Creates sea tiles textures
     */
    private void createSeaTiles() {
        seaTiles.put("cell", context.getManager().get(context.getAssetObject().cell,Texture.class));
        seaTiles.put("safe", context.getManager().get(context.getAssetObject().safe,Texture.class));
    }

    private void createMisc() {
        misc.put("cannonball_large", context.getManager().get(context.getAssetObject().cannonball_large,Texture.class));
        misc.put("cannonball_medium", context.getManager().get(context.getAssetObject().cannonball_medium,Texture.class));
        misc.put("cannonball_small", context.getManager().get(context.getAssetObject().cannonball_small,Texture.class));
        misc.put("splash_large", context.getManager().get(context.getAssetObject().splash_large,Texture.class));
        misc.put("splash_small", context.getManager().get(context.getAssetObject().splash_small,Texture.class));
        misc.put("explode_large", context.getManager().get(context.getAssetObject().explode_large,Texture.class));
        misc.put("explode_medium", context.getManager().get(context.getAssetObject().explode_medium,Texture.class));
        misc.put("explode_small"  , context.getManager().get(context.getAssetObject().explode_small,Texture.class));
        misc.put("hit", context.getManager().get(context.getAssetObject().hit,Texture.class));
    }
    
    public void dispose() {
    	seaTiles.clear();
    	misc.clear();
    	vessels.clear();
    }

    public static Texture prepareTextureForTeam(Texture texture, Team team) {
        // The texture data
        TextureData data = texture.getTextureData();

        // Make sure its prepared
        if (!data.isPrepared()) {
            data.prepare();
        }

        // Our pixmap
        Pixmap pixmap = data.consumePixmap();

        // Loop through all pixels
        for (int x = 0; x < pixmap.getWidth(); x++) {
            for (int y = 0; y < pixmap.getHeight(); y++) {

                // The current color in the given position
                int color = pixmap.getPixel(x, y);

                // RGBA conversion from int
                int R = ((color & 0xff000000) >>> 24);
                int G = ((color & 0x00ff0000) >>> 16);
                int B = ((color & 0x0000ff00) >>> 8);
                int A = ((color & 0x000000ff));

                // Chec
                if (R == 90 && G == 172 && B == 222 && A == 255) {
                    pixmap.drawPixel(x, y, Color.rgba8888(team.getColor()));
                }

            }
        }

        return new Texture(pixmap);
    }
}
