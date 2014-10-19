package me.trifunovic.spaceassault.game.enemies;

import me.trifunovic.spaceassault.game.GameActivity;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

public class EnemyBullet extends Sprite {

	private final Engine mEngine;

	public EnemyBullet(float pX, float pY,
			TextureRegion pTiledTextureRegion, Engine engine) {
		super(pX, pY, pTiledTextureRegion);
		mEngine = engine;
		//设置子弹xy方向
		this.setVelocity(0, 300);
		GameActivity.enemyBullets.add(this);
	}

	public static EnemyBullet reuse(float posx, float posy) {
		final EnemyBullet bullet = GameActivity.enemyBulletsToReuse.get(0);
		GameActivity.enemyBullets.add(bullet);
		GameActivity.enemyBulletsToReuse.remove(bullet);
		bullet.setPosition(posx, posy);
		return bullet;
	}

	public void addToScene() {
		mEngine.getScene().getBottomLayer().addEntity(this);
	}

	public void removeFromScene() {
		mEngine.getScene().getBottomLayer().removeEntity(this);
		GameActivity.enemyBulletsToReuse.add(0, this);
		GameActivity.enemyBullets.remove(this);
	}

	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		final EnemyBullet bullet = this;
		if (this.getY() > GameActivity.CAMERA_HEIGHT) {
			mEngine.runOnUpdateThread(new Runnable() {
				public void run() {
					bullet.removeFromScene();
				}
			});
		}

		super.onManagedUpdate(pSecondsElapsed);
	}

}
