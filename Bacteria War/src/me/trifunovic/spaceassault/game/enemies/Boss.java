package me.trifunovic.spaceassault.game.enemies;

import me.trifunovic.spaceassault.game.GameActivity;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
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
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.Path;

public class Boss extends AnimatedSprite implements IEnemy {

	private final Engine mEngine;
	private boolean killed;
	private TextureRegion laserRegion;
	private Laser laser;
	private Laser laser2;
	private boolean i;
	private boolean uleteo;
	/************ÐÞ¸ÄÑªÁ¿************/
	private int health = 10;
	
	public Boss(float pX, float pY, TiledTextureRegion pTiledTextureRegion, TextureRegion laserRegion, final Engine engine) {
		super(pX, pY, pTiledTextureRegion);
		this.laserRegion = laserRegion;
		mEngine = engine;
		killed=false;
		uleteo=false;
		this.setVisible(false);
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
	
	public void fight(){
		this.setVisible(true);
		this.animate(GameActivity.ANIMATION_FRAMELENGTH);
	}
	
	public Laser getLaser(){
		return laser;
	}
	
	public Laser getLaser2(){
		return laser2;
	}
	
	@Override
	public void addToScene() {
		this.laser = new Laser(this.getX()+this.getWidth()/2, this.getY()+this.getHeight(), laserRegion, mEngine);
		this.laser2 = new Laser(this.getX()+this.getWidth()/2, laser.getY()+laser.getHeight(), laserRegion, mEngine);
		laser.addToScene();
		laser2.addToScene();
		laser.setVisible(false);
		laser2.setVisible(false);
		i=true;
		
		mEngine.getScene().registerUpdateHandler(new TimerHandler(4f,
				new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				pTimerHandler.reset();
				if(uleteo==true){
					if(i==true){
						laser.setVisible(true);
						laser2.setVisible(true);
						i=false;
					}else{
						laser.setVisible(false);
						laser2.setVisible(false);
						i=true;
					}
				}
			}
		}));
		
		
		mEngine.getScene().getBottomLayer().addEntity(this);
		Path start = new Path(2).to(200, -200).to(200, 50);
		
		this.addShapeModifier(new PathModifier(2f, start,
				null, new IPathModifierListener() {
					@Override
					public void onWaypointPassed(
							final PathModifier pPathModifier,
							final IShape pShape, final int pWaypointIndex) {
						
						if (pWaypointIndex == 1) {
							uleteo=true;
							mEngine.runOnUpdateThread(new Runnable() {
								public void run() {
									//Path p = new Path(7).to(200, 100).to(250, 100).to(100, 400)
									//.to(300, 300).to(400, 400).to(250, 100).to(200, 200);
									
									Path p = new Path(11).to(200, 50).to(50, 150).to(150, 250)
									.to(250, 250).to(350, 150).to(200, 50).to(350, 150).to(250, 250).to(150, 250).to(50, 150).to(200, 50);
									
									((Boss) pShape).addShapeModifier(new LoopModifier(new PathModifier(15, p,
											null, new IPathModifierListener() {
										@Override
										public void onWaypointPassed(
												final PathModifier pPathModifier,
												final IShape pShape, final int pWaypointIndex) {
											
											((Boss) pShape).fire(); //TODO napravi pucanje za kraljicu
										}
									}, EaseSineInOut.getInstance())));
								}
							});
						}
						
					}
				}, EaseSineInOut.getInstance()));
	}

	@Override
	public void removeFromScene() {
		killed=true;
		laser.removeFromScene();
		laser2.removeFromScene();
		this.clearShapeModifiers();
		this.addShapeModifier(new SequenceModifier(
				new IShapeModifierListener() {
					@Override
					public void onModifierFinished(
							IShapeModifier pShapeModifier, final IShape boss) {
						mEngine.runOnUpdateThread(new Runnable() {
							public void run() {
								mEngine.getScene().getBottomLayer().removeEntity(((Boss) boss));
							}
						});
					}
				}, new ScaleModifier(0.3f, 1, 0)));
	}

	@Override
	public void fire() {
		
	}
	
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		laser.setPosition(this.getX()+this.getWidth()/2-5, this.getY()+this.getHeight()-15);
		laser2.setPosition(laser.getX(), laser.getY()+laser.getHeight()-4);
		super.onManagedUpdate(pSecondsElapsed);
	}

}
