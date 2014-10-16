package me.trifunovic.spaceassault.game.enemies;

import java.util.Random;

import me.trifunovic.spaceassault.game.GameActivity;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.shape.IShape;
import org.anddev.andengine.entity.shape.modifier.IShapeModifier;
import org.anddev.andengine.entity.shape.modifier.IShapeModifier.IShapeModifierListener;
import org.anddev.andengine.entity.shape.modifier.LoopModifier;
import org.anddev.andengine.entity.shape.modifier.PathModifier;
import org.anddev.andengine.entity.shape.modifier.PathModifier.IPathModifierListener;
import org.anddev.andengine.entity.shape.modifier.ScaleModifier;
import org.anddev.andengine.entity.shape.modifier.SequenceModifier;
import org.anddev.andengine.entity.shape.modifier.ease.EaseSineInOut;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.Path;

public class EnemyShip0 extends AnimatedSprite implements IEnemy {

	private final Engine mEngine;
	private boolean killed;
	/************修改血量************/
	private int health = 2;

	public EnemyShip0(float pX, float pY, TiledTextureRegion pTiledTextureRegion, final Engine engine) {
		super(pX, pY, pTiledTextureRegion);
		mEngine = engine;
		killed=false;
		/************修改血量************/
		Random rand = new Random();
		health = rand.nextInt(3);
		
		GameActivity.enemies0.add(this);
		this.animate(GameActivity.ANIMATION_FRAMELENGTH);
	}
	
	public int getHealth(){
		return health;
	}
	
	public int hit(){
		health = health - 1;
		return health;
	}
	
	public boolean isKilled(){
		return killed;
	}

	public static EnemyShip0 reuse() {
		final EnemyShip0 enemy = GameActivity.enemiesToReuse0.get(0);
		enemy.killed = false;
		GameActivity.enemies0.add(enemy);
		GameActivity.enemiesToReuse0.remove(enemy);
		return enemy;
	}

	public void addToScene() {
		mEngine.getScene().getBottomLayer().addEntity(this);
	}

	public void removeFromScene() {
		killed=true;
		this.clearShapeModifiers();
		this.addShapeModifier(new SequenceModifier(
				new IShapeModifierListener() {
					@Override
					public void onModifierFinished(
							IShapeModifier pShapeModifier, final IShape enemy) {
						mEngine.runOnUpdateThread(new Runnable() {
							public void run() {
								mEngine.getScene().getBottomLayer().removeEntity(((EnemyShip0) enemy));
								GameActivity.enemiesToReuse0.add(0,(EnemyShip0) enemy);
								GameActivity.enemies0.remove((EnemyShip0) enemy);
								((EnemyShip0) enemy).setScale(1);
							}
						});
					}
				}, new ScaleModifier(0.3f, 1, 0)));
	}

	public void folow(Path path, float speed) {
		this.addShapeModifier(new LoopModifier(new PathModifier(speed, path,
				null, new IPathModifierListener() {
					@Override
					public void onWaypointPassed(
							final PathModifier pPathModifier,
							final IShape pShape, final int pWaypointIndex) {
						if (pWaypointIndex == 6) {
							mEngine.runOnUpdateThread(new Runnable() {
								public void run() {
									((EnemyShip0) pShape).removeFromScene();
								}
							});
						}

						if (pWaypointIndex != 0 && pWaypointIndex != 6)
							((EnemyShip0) pShape).fire();
					}
				}, EaseSineInOut.getInstance())));
	}

	public void fire() {
		if(GameActivity.options.getSoundEffects()==true)
			GameActivity.shotSound.play();
		if (GameActivity.enemyBulletsToReuse.isEmpty()) {
			final EnemyBullet metak = new EnemyBullet(this.getX() + 10,
					this.getY() + 30, GameActivity.mEnemyBulletTextureRegion,
					mEngine);
			metak.addToScene();
		} else {
			final EnemyBullet metak = EnemyBullet.reuse(this.getX() + 10,
					this.getY() + 30);
			metak.addToScene();
		}
	}

}
