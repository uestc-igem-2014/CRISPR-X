package me.trifunovic.spaceassault.game.enemies;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.shape.modifier.AlphaModifier;
import org.anddev.andengine.entity.shape.modifier.LoopModifier;
import org.anddev.andengine.entity.shape.modifier.SequenceModifier;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

public class Laser extends Sprite {

	private final Engine mEngine;

	public Laser(float pX, float pY,
			TextureRegion pTiledTextureRegion, Engine engine) {
		super(pX, pY, pTiledTextureRegion);
		mEngine = engine;
		
	}

	public void addToScene() {
		mEngine.getScene().getBottomLayer().addEntity(this);
		addShapeModifier(new LoopModifier(new SequenceModifier(new AlphaModifier(0.5f, 1, 0.5f), new AlphaModifier(0.5f, 0.5f, 1))));
	}

	public void removeFromScene() {
		mEngine.getScene().getBottomLayer().removeEntity(this);
	}

	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {

		super.onManagedUpdate(pSecondsElapsed);
	}

}
