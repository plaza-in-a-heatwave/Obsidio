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
    	vessels.put("baghlah", new Texture("assets/vessel/baghlah/sail.png"));
    	vessels.put("blackship", new Texture("assets/vessel/blackship/sail.png"));
    	vessels.put("dhow", new Texture("assets/vessel/dhow/sail.png"));
    	vessels.put("fanchuan", new Texture("assets/vessel/fanchuan/sail.png"));
    	vessels.put("grandfrig", new Texture("assets/vessel/grandfrig/sail.png"));
    	vessels.put("junk", new Texture("assets/vessel/junk/sail.png"));
    	vessels.put("lgsloop", new Texture("assets/vessel/lgsloop/sail.png"));
    	vessels.put("longship", new Texture("assets/vessel/longship/sail.png"));
    	vessels.put("merchbrig", new Texture("assets/vessel/merchbrig/sail.png"));
    	vessels.put("merchgal", new Texture("assets/vessel/merchgal/sail.png"));
    	vessels.put("smsloop", new Texture("assets/vessel/smsloop/sail.png"));
    	vessels.put("warbrig", new Texture("assets/vessel/warbrig/sail.png"));
    	vessels.put("warfrig", new Texture("assets/vessel/warfrig/sail.png"));
    	vessels.put("wargal", new Texture("assets/vessel/wargal/sail.png"));
    	vessels.put("xebec", new Texture("assets/vessel/xebec/sail.png"));

    	// sink textures (the Black Ship doesn't sink!)
    	vessels.put("baghlah_sinking", new Texture("assets/vessel/baghlah/sink.png"));
    	vessels.put("dhow_sinking", new Texture("assets/vessel/dhow/sink.png"));
    	vessels.put("fanchuan_sinking", new Texture("assets/vessel/fanchuan/sink.png"));
    	vessels.put("grandfrig_sinking", new Texture("assets/vessel/grandfrig/sink.png"));
    	vessels.put("junk_sinking", new Texture("assets/vessel/junk/sink.png"));
    	vessels.put("lgsloop_sinking", new Texture("assets/vessel/lgsloop/sink.png"));
    	vessels.put("longship_sinking", new Texture("assets/vessel/longship/sink.png"));
    	vessels.put("merchbrig_sinking", new Texture("assets/vessel/merchbrig/sink.png"));
    	vessels.put("merchgal_sinking", new Texture("assets/vessel/merchgal/sink.png"));
    	vessels.put("smsloop_sinking", new Texture("assets/vessel/smsloop/sink.png"));
    	vessels.put("warbrig_sinking", new Texture("assets/vessel/warbrig/sink.png"));
    	vessels.put("warfrig_sinking", new Texture("assets/vessel/warfrig/sink.png"));
    	vessels.put("wargal_sinking", new Texture("assets/vessel/wargal/sink.png"));
    	vessels.put("xebec_sinking", new Texture("assets/vessel/xebec/sink.png"));
    }

    /**
     * Creates sea tiles textures
     */
    private void createSeaTiles() {
        seaTiles.put("cell", new Texture("assets/sea/cell.png"));
        seaTiles.put("safe", new Texture("assets/sea/safezone.png"));
    }

    private void createMisc() {
        misc.put("cannonball_large", new Texture("assets/projectile/cannonball_large.png"));
        misc.put("cannonball_medium", new Texture("assets/projectile/cannonball_medium.png"));
        misc.put("cannonball_small", new Texture("assets/projectile/cannonball_small.png"));
        misc.put("splash_large", new Texture("assets/effects/splash_large.png"));
        misc.put("splash_small", new Texture("assets/effects/splash_small.png"));
        misc.put("explode_large", new Texture("assets/effects/explode_large.png"));
        misc.put("explode_medium", new Texture("assets/effects/explode_medium.png"));
        misc.put("explode_small"  , new Texture("assets/effects/explode_small.png"));
        misc.put("hit", new Texture("assets/effects/hit.png"));
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
