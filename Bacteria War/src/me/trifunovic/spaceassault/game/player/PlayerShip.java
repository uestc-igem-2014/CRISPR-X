package me.trifunovic.spaceassault.game.player;

import me.trifunovic.spaceassault.game.GameActivity;
import me.trifunovic.spaceassault.game.myparameters.Parameters;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.shape.modifier.ColorModifier;
import org.anddev.andengine.entity.shape.modifier.SequenceModifier;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.sensor.accelerometer.AccelerometerData;

public class PlayerShip extends AnimatedSprite {

	private final Engine mEngine;
	private final PlayerShipShadow shadow;
	/************ 修改血量 ************/
	private int health = 25;
	private boolean killed = false;
	private int numBullets;

	public boolean isKilled() {
		return killed;
	}

	public void kill() {
		killed = true;
	}

	public PlayerShip(float pX, float pY,
			TiledTextureRegion pTiledTextureRegion, Engine engine) {
		super(pX, pY, pTiledTextureRegion);
		mEngine = engine;
		this.animate(GameActivity.ANIMATION_FRAMELENGTH);
		numBullets = 10;
		shadow = new PlayerShipShadow(this);
	}

	public PlayerShipShadow getShadow() {
		return shadow;
	}

	public void addToScene(Scene scene) {
		scene.getBottomLayer().addEntity(shadow);
		scene.getBottomLayer().addEntity(this);
	}

	public void removeFromScene() {
		mEngine.getScene().getBottomLayer().removeEntity(shadow);
		mEngine.getScene().getBottomLayer().removeEntity(this);
	}

	//被小怪击中
	public void hit(int blood) {
		/* blood是计算得到的血量 */
		if(blood >= 0) {
			health = health - blood -5;
		} else {
			health --;
		}
		this.addShapeModifier(new SequenceModifier(new ColorModifier(0.3f, 1,
				1, 1, 0, 1, 0), new ColorModifier(0.3f, 1, 1, 0, 1, 0, 1)));
	}

	//被boss击中
	public void hit2(int blood) {
		/* blood是计算得到的血量 */
		if(blood >= 0) {
			health = health - blood - 12;
		} else {
			health --;
		}
		this.addShapeModifier(new SequenceModifier(new ColorModifier(0.3f, 1,
				1, 1, 0, 1, 0), new ColorModifier(0.3f, 1, 1, 0, 1, 0, 1)));
	}

	public void addHealth(int blood) {
		/* blood是计算得到的血量 */
		if (health < 25) {
			health = health + 1 + blood;
			if(health > 25)
				health = 25;
		} else
			return;
	}

	public int getHealth() {
		return health;
	}

	public void fire() {

		if (GameActivity.bullets.size() < numBullets
				&& GameActivity.isGameOver == false) {
			// Debug.d("MECI="+GameActivity.bullets.size()+"     UNISTENO="+GameActivity.bulletsToReuse.size());
			if (GameActivity.options.getSoundEffects() == true)
				GameActivity.shotSound.play();

			if (GameActivity.bulletsToReuse.size() == 0) {
				final Bullet bullet = new Bullet(this.getX() + 10, this.getY(),
						GameActivity.mBulletTextureRegion, mEngine);
				bullet.addToScene();
				// Debug.d("NEW");
			} else {
				Bullet.reuse(this.getX() + 10, this.getY());
				// Debug.d("REUSE");
			}
		}
	}

	public void move(AccelerometerData accelerometer ,Parameters mParameters) {
		if (GameActivity.isGameOver == false) {
			if (this.getX() > 20
					&& this.getX() < mEngine.getCamera().getWidth() - 20
							- this.getWidth()) {
				this.setVelocityX(-accelerometer.getX() * 50 * mParameters.getPlayerShipFlySpeed());
			} else {
				this.setVelocityX(0);

				if (this.getX() <= 20 && accelerometer.getX() < 0)
					this.setVelocityX(-accelerometer.getX() * 50 * mParameters.getPlayerShipFlySpeed());

				if (this.getX() >= mEngine.getCamera().getWidth() - 20
						- this.getWidth()
						&& accelerometer.getX() > 0)
					this.setVelocityX(-accelerometer.getX() * 50 * mParameters.getPlayerShipFlySpeed());
			}

			if (this.getY() > mEngine.getCamera().getHeight() - 200
					&& this.getY() < mEngine.getCamera().getHeight() - 20
							- this.getHeight()) {
				this.setVelocityY(accelerometer.getY() * 50 * mParameters.getPlayerShipFlySpeed());
			} else {
				this.setVelocityY(0);

				if (this.getY() <= mEngine.getCamera().getHeight() - 200
						&& accelerometer.getY() > 0)
					this.setVelocityY(accelerometer.getY() * 50 * mParameters.getPlayerShipFlySpeed());

				if (this.getY() >= mEngine.getCamera().getHeight() - 20
						- this.getHeight()
						&& accelerometer.getY() < 0)
					this.setVelocityY(accelerometer.getY() * 50 * mParameters.getPlayerShipFlySpeed());
			}
		}
	}

}
