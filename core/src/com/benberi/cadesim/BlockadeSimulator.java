package com.benberi.cadesim;

import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.benberi.cadesim.game.scene.GameScene;

public class BlockadeSimulator extends ApplicationAdapter {

	/**
	 * The game context
	 */
    
	private GameContext context;

	@Override
	public void create () {
		context = new GameContext(this);
		context.create();
	}


	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		context.getPacketHandler().tickQueue();

		// dispose scenes when navigating away from them
		if (context.isDisposeFurtherScenes()) {
			context.setDisposeFurtherScenes(false); // one time only
			// dispose scenes
			for (GameScene scene : context.getScenes()) {
				if (scene != null) {
					scene.dispose();
				}
			}
			context.getScenes().clear();
			if (context.getEntities() != null) {
				context.getEntities().dispose();
			}

			// make a new connectscene
			context.getConnectScene().create();
		} else if (context.isDisposeConnectScene()) {
			context.setDisposeConnectScene(false); // one time only

			context.getConnectScene().dispose();
		}

		// play scenes
		if (!context.isReady()) {
			context.getConnectScene().update();
			context.getConnectScene().render();
			return;
		} else {
			for (GameScene scene : context.getScenes()) {
				scene.update();
				scene.render();
			}
		}

	}

	@Override
	public void dispose () {
	}
}
