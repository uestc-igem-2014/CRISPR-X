package me.trifunovic.spaceassault.game.enemies;


import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.shape.IShape;
import org.anddev.andengine.entity.shape.modifier.IShapeModifier;
import org.anddev.andengine.entity.shape.modifier.IShapeModifier.IShapeModifierListener;
import org.anddev.andengine.entity.shape.modifier.ScaleModifier;
import org.anddev.andengine.entity.shape.modifier.SequenceModifier;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class Mine extends AnimatedSprite implements IEnemy {

	private final Engine mEngine;
	private boolean killed;

	public Mine(float pX, float pY, TiledTextureRegion pTiledTextureRegion, final Engine engine) {
		super(pX, pY, pTiledTextureRegion);
		mEngine = engine;
		killed=false;
		this.animate(60);
		this.setVelocityY(100);
	}
	
	public boolean isKilled(){
		return killed;
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
								mEngine.getScene().getBottomLayer().removeEntity(((Mine) enemy));
							}
						});
					}
				}, new ScaleModifier(0.3f, 1, 0)));
	}

	@Override
	public void fire() {
		// TODO Auto-generated method stub
	}

}
