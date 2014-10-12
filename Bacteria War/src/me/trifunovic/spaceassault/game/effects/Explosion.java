package me.trifunovic.spaceassault.game.effects;

import me.trifunovic.spaceassault.game.GameActivity;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class Explosion extends AnimatedSprite {

	private final Engine mEngine;

	public Explosion(float pX, float pY, TiledTextureRegion pTiledTextureRegion, final Engine engine) {
		super(pX, pY, pTiledTextureRegion);
		mEngine = engine;
		GameActivity.explosions.add(this);
		addToScene();
		explode();
	}

	public static Explosion reuse(float posx, float posy) {
		final Explosion explosion = GameActivity.explosionsToReuse.get(0);
		GameActivity.explosions.add(explosion);
		GameActivity.explosionsToReuse.remove(explosion);
		explosion.setPosition(posx, posy);
		explosion.explode();
		explosion.setVisible(true);
		return explosion;
	}

	public void addToScene() {
		mEngine.getScene().getTopLayer().addEntity(this);
	}

	public void explode() {
		this.animate(GameActivity.ANIMATION_FRAMELENGTH, false,
				new IAnimationListener() {
					@Override
					public void onAnimationEnd(final AnimatedSprite explosion) {
						explosion.setVisible(false);
						GameActivity.explosionsToReuse.add((Explosion) explosion);
						GameActivity.explosions.remove(explosion);
					}
			}
		);
	}

}
