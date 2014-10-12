package me.trifunovic.spaceassault.game.player;

import me.trifunovic.spaceassault.game.GameActivity;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

public class Bullet extends Sprite {

	private final Engine mEngine;

	public Bullet(float pX, float pY, TextureRegion pTiledTextureRegion, Engine engine) {
		super(pX, pY, pTiledTextureRegion);
		mEngine = engine;
		this.setVelocity(0, -300);
		GameActivity.bullets.add(this);
	}
	public static Bullet reuse(float posx, float posy) {
		final Bullet bullet = GameActivity.bulletsToReuse.get(0);
		GameActivity.bullets.add(bullet);
		GameActivity.bulletsToReuse.remove(bullet);
		bullet.setVisible(true);
		bullet.setPosition(posx, posy);
		bullet.setVelocity(0, -300);
		return bullet;
	}
	
	public void addToScene() {
		mEngine.getScene().getBottomLayer().addEntity(this);
	}

	public void removeFromScene() {
		this.setVelocity(0,0);
		this.setVisible(false);
		GameActivity.bulletsToReuse.add(this);
		GameActivity.bullets.remove(this);
	}

	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		final Bullet bullet = this;
		if(bullet.isVisible())
			if (this.getY() < -this.getHeight()) {
				mEngine.runOnUpdateThread(new Runnable() {
					public void run() {
							bullet.removeFromScene();
					}
				});
			}

		super.onManagedUpdate(pSecondsElapsed);
	}
}
