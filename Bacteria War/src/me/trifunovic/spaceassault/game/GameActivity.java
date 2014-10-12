package me.trifunovic.spaceassault.game;

import static android.view.ViewGroup.LayoutParams.FILL_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import java.io.IOException;
import java.util.ArrayList;

import me.trifunovic.spaceassault.game.background.ScrollBackground;
import me.trifunovic.spaceassault.game.effects.Explosion;
import me.trifunovic.spaceassault.game.enemies.Boss;
import me.trifunovic.spaceassault.game.enemies.EnemyBullet;
import me.trifunovic.spaceassault.game.enemies.EnemyShip;
import me.trifunovic.spaceassault.game.hud.HudScore;
import me.trifunovic.spaceassault.game.level.Level;
import me.trifunovic.spaceassault.game.options.Options;
import me.trifunovic.spaceassault.game.player.Bullet;
import me.trifunovic.spaceassault.game.player.PlayerShip;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.audio.sound.Sound;
import org.anddev.andengine.audio.sound.SoundFactory;
import org.anddev.andengine.collision.BaseCollisionChecker;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.shape.IShape;
import org.anddev.andengine.entity.shape.modifier.ColorModifier;
import org.anddev.andengine.entity.shape.modifier.DelayModifier;
import org.anddev.andengine.entity.shape.modifier.IShapeModifier;
import org.anddev.andengine.entity.shape.modifier.IShapeModifier.IShapeModifierListener;
import org.anddev.andengine.entity.shape.modifier.AlphaModifier;
import org.anddev.andengine.entity.shape.modifier.PathModifier;
import org.anddev.andengine.entity.shape.modifier.RotationByModifier;
import org.anddev.andengine.entity.shape.modifier.ScaleModifier;
import org.anddev.andengine.entity.shape.modifier.SequenceModifier;
import org.anddev.andengine.entity.shape.modifier.ease.EaseSineInOut;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.BuildableTexture;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.builder.BlackPawnTextureBuilder;
import org.anddev.andengine.opengl.texture.builder.ITextureBuilder.TextureSourcePackingException;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.opengl.view.RenderSurfaceView;
import org.anddev.andengine.sensor.accelerometer.AccelerometerData;
import org.anddev.andengine.sensor.accelerometer.IAccelerometerListener;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.HorizontalAlign;
import org.anddev.andengine.util.Path;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class GameActivity extends BaseGameActivity implements IAccelerometerListener {

	// CONSTANTS
	public static final int CAMERA_WIDTH = 480;
	public static final int CAMERA_HEIGHT = 800;
	public static final int ANIMATION_FRAMELENGTH = 60;

	// CAMERA
	private Camera camera;

	// TEXTURES AND FONTS
	private BuildableTexture mBuildableTexture;
	private static TiledTextureRegion mShipTextureRegion;
	private static TiledTextureRegion mEnemyTextureRegion;
	public static TextureRegion mBulletTextureRegion;
	public static TextureRegion mEnemyBulletTextureRegion;
	private static TiledTextureRegion mExplosionTextureRegion;
	private TiledTextureRegion mBossTextureRegion;
	private TextureRegion mLaserTextureRegion;
	private Texture font_texture;
	private Texture gameOver_font_texture;
	private Font font;
	private Font gameOver_font;
	private Texture mOnScreenControlTexture;
    private TextureRegion mOnScreenControlBaseTextureRegion;
    private TextureRegion mOnScreenControlKnobTextureRegion;
	private Texture mAutoScrollBackgroundTexture;
	private TextureRegion mScrollLayer;

	// SOUNDS
	public static Sound shotSound;
	private static Sound explosionSound;
	private static Sound gameOverSound;

	private Music music;

	// OBJECTS
	private PlayerShip ship;
	private Boss boss;
	public static ArrayList<EnemyShip> enemies;
	public static ArrayList<EnemyShip> enemiesToReuse;
	public static ArrayList<Explosion> explosions;
	public static ArrayList<Explosion> explosionsToReuse;
	public static ArrayList<Bullet> bullets;
	public static ArrayList<Bullet> bulletsToReuse;
	public static ArrayList<EnemyBullet> enemyBullets;
	public static ArrayList<EnemyBullet> enemyBulletsToReuse;

	// PATHS
	public static final Path[] paths = new Path[4];

	// VARIABLES
	public static final HudScore score = new HudScore();
	public static boolean isGameOver;
	public static boolean isGameReady;
	public static boolean isBossFight;

	// SHARED PREFERENCES
	public static Options options;
	
	// SCORE - TEXTVIEW
	private static TextView scoreView;
	
	// DODATNO
	private Rectangle healthbar;
	private Rectangle okvir;
	
	// LEVEL
	private Level level;
	
	// POZADINA
	private ScrollBackground ScrollBackground;
	
	// FADE
	private Rectangle fade;
	
	@Override
	protected void onSetContentView() {
		final RelativeLayout relativeLayout = new RelativeLayout(this);
		final FrameLayout.LayoutParams relativeLayoutLayoutParams = new FrameLayout.LayoutParams(FILL_PARENT, FILL_PARENT);

		scoreView = new TextView(getApplicationContext());
		Typeface font = Typeface.createFromAsset(getAssets(),"font/pf_tempesta_five.ttf");
		scoreView.setTypeface(font);
		
		scoreView.setPadding(30, 30, 0, 0);
		scoreView.setTextColor(Color.BLACK);
		scoreView.setGravity(Gravity.CENTER);

		this.mRenderSurfaceView = new RenderSurfaceView(this, this.mEngine);
		this.mRenderSurfaceView.applyRenderer();

		final LayoutParams surfaceViewLayoutParams = new RelativeLayout.LayoutParams(super.createSurfaceViewLayoutParams());
		surfaceViewLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		relativeLayout.addView(this.mRenderSurfaceView, surfaceViewLayoutParams);
		relativeLayout.addView(scoreView, this.createAdViewLayoutParams());

		this.setContentView(relativeLayout, relativeLayoutLayoutParams);
	}

	private LayoutParams createAdViewLayoutParams() {
		final LayoutParams adViewLayoutParams = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
		adViewLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		adViewLayoutParams.addRule(RelativeLayout.ALIGN_LEFT);
		return adViewLayoutParams;
	}

	@Override
	public Engine onLoadEngine() {
		options = new Options(GameActivity.this);
		this.camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		final EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.PORTRAIT, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), this.camera);

		if (options.getSoundEffects() == true)
			engineOptions.setNeedsSound(true);

		if (options.getMusic() == true)
			engineOptions.setNeedsMusic(true);

		return new Engine(engineOptions);
	}

	@Override
	public void onLoadResources() {
		
		this.mBuildableTexture = new BuildableTexture(512, 512, TextureOptions.DEFAULT);

		TextureRegionFactory.setAssetBasePath("img/");
		
		mLaserTextureRegion = TextureRegionFactory.createFromAsset(this.mBuildableTexture, this, "laser.png");
		
		GameActivity.mShipTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mBuildableTexture, this, "ship2.png", 4, 1);
		GameActivity.mEnemyTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mBuildableTexture, this, "enemy2.png", 4, 1);
		GameActivity.mBulletTextureRegion = TextureRegionFactory.createFromAsset(this.mBuildableTexture, this, "meci.png");
		GameActivity.mEnemyBulletTextureRegion = TextureRegionFactory.createFromAsset(this.mBuildableTexture, this, "meci_neprijatelji.png");
		GameActivity.mExplosionTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mBuildableTexture, this, "explosion2.png", 4, 2);

		if (GameActivity.options.getControlls().equals("3")) {
			this.mOnScreenControlTexture = new Texture(256, 128, TextureOptions.BILINEAR);
	        this.mOnScreenControlBaseTextureRegion = TextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, this, "onscreen_control_base.png", 0, 0);
	        this.mOnScreenControlKnobTextureRegion = TextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, this, "onscreen_control_knob.png", 128, 0);
	
	        this.mEngine.getTextureManager().loadTextures(this.mOnScreenControlTexture);
		}
        
		mBossTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mBuildableTexture, this, "boss.png", 2, 2);
		
		try {
			this.mBuildableTexture.build(new BlackPawnTextureBuilder());
		} catch (final TextureSourcePackingException e) {
			Debug.e(e);
		}

		this.mAutoScrollBackgroundTexture = new Texture(512, 1024, TextureOptions.DEFAULT);
		this.mScrollLayer = TextureRegionFactory.createFromAsset(this.mAutoScrollBackgroundTexture, this,"background0.png", 0, 0);

		FontFactory.setAssetBasePath("font/");
		font_texture = new Texture(256, 256, TextureOptions.BILINEAR);
		font = FontFactory.createFromAsset(font_texture, this,"pf_tempesta_five.ttf", 60, true, Color.RED);
		
		gameOver_font_texture = new Texture(256, 256, TextureOptions.BILINEAR);
		gameOver_font = FontFactory.createFromAsset(gameOver_font_texture,this, "pf_tempesta_five.ttf", 60, true, Color.BLACK);
		
		this.mEngine.getTextureManager().loadTextures(mBuildableTexture, font_texture,
				gameOver_font_texture, mAutoScrollBackgroundTexture);
		this.mEngine.getFontManager().loadFont(font);
		this.mEngine.getFontManager().loadFont(gameOver_font);

		if (options.getSoundEffects() == true) {
			SoundFactory.setAssetBasePath("snd/");
			try {
				GameActivity.shotSound = SoundFactory.createSoundFromAsset(
						this.mEngine.getSoundManager(), this, "shot.ogg");
				GameActivity.explosionSound = SoundFactory
						.createSoundFromAsset(this.mEngine.getSoundManager(),
								this, "explosion.ogg");
				GameActivity.gameOverSound = SoundFactory.createSoundFromAsset(
						this.mEngine.getSoundManager(), this, "gameover.ogg");
			} catch (final IOException e) {
				Debug.e("Error", e);
			}
		}

		if (options.getMusic() == true) {
			MusicFactory.setAssetBasePath("snd/");
			try {
				this.music = MusicFactory.createMusicFromAsset(
						this.mEngine.getMusicManager(), this, "music.ogg");
				this.music.setLooping(true);
			} catch (final IOException e) {
				Debug.e("Error", e);
			}
		}

		if (GameActivity.options.getControlls().equals("1"))
			this.enableAccelerometerSensor(this);
		
	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		
		mEngine.stop();
		gameInit();

		loadScene();
		
		return level.getScene();
	}
	
	public void showScore(){
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				
				AlertDialog.Builder alert = new AlertDialog.Builder(GameActivity.this);

				alert.setTitle("LEVEL CLEARED");
				alert.setIcon(R.drawable.trophy);
				alert.setMessage("Your score:");

				final TextView lblScore = new TextView(GameActivity.this);
				lblScore.setTextColor(Color.rgb(20,164,255));
				lblScore.setTextSize(32f);
				Typeface font = Typeface.createFromAsset(getAssets(),"font/pf_tempesta_five.ttf");
				lblScore.setTypeface(font);
				lblScore.setPadding(0, 0, 0, 20);
				lblScore.setGravity(Gravity.CENTER_HORIZONTAL);
				
				lblScore.setText(String.valueOf(GameActivity.score.getScore()));
				alert.setView(lblScore);

				alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					mEngine.start();
				}
				});
				mEngine.stop();
				alert.show();
			}
		});
	};
	
	private void goToNextLevel(){
		fade.addShapeModifier(new SequenceModifier(new IShapeModifierListener() {
			
			@Override
			public void onModifierFinished(IShapeModifier pShapeModifier, IShape pShape) {
				
				showScore();
				
				mEngine.clearUpdateHandlers();
				level.getScene().clearUpdateHandlers();
				level.getScene().clearChildScene();
				level.getScene().clearTouchAreas();
				
				level.next();
				bulletsToReuse.clear();
				enemiesToReuse.clear();
				enemyBulletsToReuse.clear();
				explosionsToReuse.clear();
				
				loadScene();

				//添加控制
				level.getScene().registerUpdateHandler(new TimerHandler(0.8f, new ITimerCallback() {
					@Override
					public void onTimePassed(final TimerHandler pTimerHandler) {
						pTimerHandler.reset();
						if (isGameReady == true)
							ship.fire();
					}
				}));
				
				fade.addShapeModifier(new SequenceModifier(new IShapeModifierListener() {
					@Override
					public void onModifierFinished(IShapeModifier pShapeModifier, IShape pShape) {
						gameGetReady();
					}
				},new DelayModifier(1), new AlphaModifier(0.5f, 1, 0)));		
			}
		},new AlphaModifier(0.5f, 0, 1)));
	}
	
	public void loadScene(){
		isGameOver = false;
		isGameReady = false;
		isBossFight = false;
		
		if(level.getLevel()!=0){
			mAutoScrollBackgroundTexture.clearTextureSources();
	        TextureRegionFactory.createFromAsset(mAutoScrollBackgroundTexture,this, "background"+level.getLevel()+".png", 0, 0);
		}
		
        level.getScene().setBackground(ScrollBackground);
		level.getScene().getTopLayer().addEntity(okvir);
		level.getScene().getTopLayer().addEntity(healthbar);
		level.getScene().getTopLayer().addEntity(fade);

		level.getScene().registerUpdateHandler(new TimerHandler(5-level.getLevel(), new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				pTimerHandler.reset();
				
				if (isGameReady == true){
					if(score.getScore()>=10000){
						if(boss.isVisible()==false && enemies.size()==0 && isBossFight==false){
							isBossFight=true;
							gameWarning();
						}
					}else{
						if(level.getScene().getWaves().getCurrentWave()==null){ // ako je zadnji wave
							if(enemies.size()==0){
								gameLevelCleared();
							}
						}else{
							for(int i=0;i<level.getScene().getWaves().getCurrentWave().size();i++){
								if(level.getScene().getWaves().getCurrentWave().getType(i).equals("ship")){
									for(int j=0;j<level.getScene().getWaves().getCurrentWave().getCount(i);j++){
										createEnemyShip(paths[j]);
									}
								}
							}
							level.getScene().getWaves().next();
						}
					}
				}
			}
		}));

		level.getScene().registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() {}

			@Override
			public void onUpdate(float pSecondsElapsed) {
				checkCollusions();
			}
		});

		ship.setPosition(CAMERA_WIDTH / 2 - mShipTextureRegion.getTileWidth() / 2, CAMERA_HEIGHT + 300);
		ship.addToScene(level.getScene());
		
		if(options.getAutoFire()==true){
			level.getScene().registerUpdateHandler(new TimerHandler(0.8f, new ITimerCallback() {
				@Override
				public void onTimePassed(final TimerHandler pTimerHandler) {
					pTimerHandler.reset();
					if (isGameReady == true)
						ship.fire();
				}
			}));
		}
		
		if (GameActivity.options.getControlls().equals("3")) {
		
			final AnalogOnScreenControl analogOnScreenControl = new AnalogOnScreenControl(CAMERA_WIDTH-this.mOnScreenControlBaseTextureRegion.getWidth(), CAMERA_HEIGHT - this.mOnScreenControlBaseTextureRegion.getHeight()-30, camera, this.mOnScreenControlBaseTextureRegion, this.mOnScreenControlKnobTextureRegion, 0.1f, 200, new IAnalogOnScreenControlListener() {
	            @Override
	            public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
	                    ship.setVelocity(pValueX * 150, pValueY * 150);
	            }
	
	            @Override
	            public void onControlClick(final AnalogOnScreenControl pAnalogOnScreenControl) {}
		    });
			
		    analogOnScreenControl.getControlBase().setAlpha(0.5f);
		    analogOnScreenControl.getControlBase().setScaleCenter(0, 128);
		    analogOnScreenControl.getControlBase().setScale(0.75f);
		    analogOnScreenControl.getControlKnob().setScale(0.75f);
		    analogOnScreenControl.refreshControlKnobPosition();
		
		    level.getScene().setChildScene(analogOnScreenControl);
		}
	}

	@Override
	public void onLoadComplete() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				scoreView.setText("SCORE: 0");	
			}
		});
		
		if (options.getMusic() == true) {
			music.play();
		}
		mEngine.start();
		
		fade.addShapeModifier(new SequenceModifier(new IShapeModifierListener() {
			@Override
			public void onModifierFinished(IShapeModifier pShapeModifier, IShape pShape) {
				gameGetReady();
			}
		},new DelayModifier(1), new AlphaModifier(0.5f, 1, 0)));				

	}

	@Override
	public void onAccelerometerChanged(AccelerometerData accelerometer) {
		if (isGameReady == true)
			ship.move(accelerometer);
		else
			ship.setVelocity(0);
	}

	@Override
	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {
		if (isGameReady == true) {
			
			if (GameActivity.options.getControlls().equals("2")) {
				if (pKeyCode == KeyEvent.KEYCODE_DPAD_LEFT
						&& pEvent.getAction() == KeyEvent.ACTION_DOWN) {
					ship.setVelocity(-100, 0);
					return true;
				} else if (pKeyCode == KeyEvent.KEYCODE_DPAD_RIGHT
						&& pEvent.getAction() == KeyEvent.ACTION_DOWN) {
					ship.setVelocity(100, 0);
					return true;
				} else if (pKeyCode == KeyEvent.KEYCODE_DPAD_UP
						&& pEvent.getAction() == KeyEvent.ACTION_DOWN) {
					ship.setVelocity(0, -100);
					return true;
				} else if (pKeyCode == KeyEvent.KEYCODE_DPAD_DOWN
						&& pEvent.getAction() == KeyEvent.ACTION_DOWN) {
					ship.setVelocity(0, 100);
					return true;
				}
			}

			if(options.getAutoFire()==false)
				if (pKeyCode == KeyEvent.KEYCODE_DPAD_CENTER
						&& pEvent.getAction() == KeyEvent.ACTION_DOWN) {
					ship.fire();
					return true;
				}
		}

		if (pKeyCode == KeyEvent.KEYCODE_BACK
				&& pEvent.getAction() == KeyEvent.ACTION_DOWN) {
			showExitDialog();
			return true;
		}
		
		return super.onKeyDown(pKeyCode, pEvent);
	}

	public void gameInit() {
		
		score.reset();

		enemies = new ArrayList<EnemyShip>();
		enemiesToReuse = new ArrayList<EnemyShip>();

		bullets = new ArrayList<Bullet>();
		bulletsToReuse = new ArrayList<Bullet>();

		enemyBullets = new ArrayList<EnemyBullet>();
		enemyBulletsToReuse = new ArrayList<EnemyBullet>();

		explosions = new ArrayList<Explosion>();
		explosionsToReuse = new ArrayList<Explosion>();

		paths[0] = new Path(7).to(50, -60).to(50, 150).to(400, 150).to(300, 250).to(400, 500).to(150, 400).to(400, -60);
		paths[1] = new Path(7).to(100, -60).to(250, 100).to(100, 400).to(300, 300).to(400, 400).to(250, 100).to(300, -60);
		paths[2] = new Path(7).to(300, -60).to(400, 120).to(300, 100).to(200, 400).to(200, 320).to(150, 300).to(200, -60);
		paths[3] = new Path(7).to(400, -60).to(200, 50).to(50, 300).to(300, 400).to(150, 400).to(300, 200).to(100, -60);
	
		level = new Level(this);
		ScrollBackground = new ScrollBackground(new Sprite(0, CAMERA_HEIGHT - this.mScrollLayer.getHeight(),this.mScrollLayer), 100);
		
		fade = new Rectangle(0, 0, 480, 800);
		fade.setColor(0, 0, 0);
		fade.setAlpha(1);
		
		boss = new Boss(200, -200, mBossTextureRegion, mLaserTextureRegion, getEngine());
		
		okvir = new Rectangle(310, 30, 140, 18);
		okvir.setColor(0.1f, 0.1f, 0.1f);
		
		healthbar = new Rectangle(314, 34, 132, 10);
		healthbar.setColor(1, 0, 0);
		
		ship = new PlayerShip(CAMERA_WIDTH / 2 - mShipTextureRegion.getTileWidth() / 2, CAMERA_HEIGHT + 300, mShipTextureRegion, getEngine());
		
		//修改的代码，加入重力控制和自动开火
		this.enableAccelerometerSensor(this);
		level.getScene().registerUpdateHandler(new TimerHandler(0.8f, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				pTimerHandler.reset();
				if (isGameReady == true)
					ship.fire();
			}
		}));
	}

	public void gameGetReady() {
		ship.addShapeModifier(new SequenceModifier(
				new IShapeModifierListener() {
					@Override
					public void onModifierFinished(
							IShapeModifier pShapeModifier, final IShape enemy) {
						runOnUpdateThread(new Runnable() {
							public void run() {

								final Text textCenter = new Text(
										CAMERA_WIDTH / 2 - 150,
										CAMERA_HEIGHT / 2 - 60, gameOver_font,
										"GET\nREADY", HorizontalAlign.CENTER);
								mEngine.getScene().getTopLayer()
										.addEntity(textCenter);
								textCenter
										.addShapeModifier(new SequenceModifier(
												new IShapeModifierListener() {

													@Override
													public void onModifierFinished(
															IShapeModifier pShapeModifier,
															IShape pShape) {
														GameActivity.isGameReady = true;
													}
												}, new ScaleModifier(1, 0, 1),
												new DelayModifier(1),
												new ScaleModifier(1, 1, 0)));

							}
						});
					}
				}, new PathModifier(2, new Path(2).to(
						CAMERA_WIDTH / 2 - mShipTextureRegion.getTileWidth()
								/ 2, CAMERA_HEIGHT + 100).to(
						CAMERA_WIDTH / 2 - mShipTextureRegion.getTileWidth()
								/ 2, CAMERA_HEIGHT - 160), EaseSineInOut
						.getInstance())));
	}
	
	public void gameLevelCleared() {
		final Text textCenter = new Text(
				CAMERA_WIDTH / 2 - 215,
				CAMERA_HEIGHT / 2 - 60, gameOver_font,
				"LEVEL\nCLEARED", HorizontalAlign.CENTER);
		mEngine.getScene().getTopLayer()
				.addEntity(textCenter);
		textCenter
				.addShapeModifier(new SequenceModifier(
						new IShapeModifierListener() {
							@Override
							public void onModifierFinished(
									IShapeModifier pShapeModifier,
									IShape pShape) {
								GameActivity.isGameReady = false;
								ship.addShapeModifier(new SequenceModifier(
										new IShapeModifierListener() {
											@Override
											public void onModifierFinished(
													IShapeModifier pShapeModifier, final IShape enemy) {
												runOnUpdateThread(new Runnable() {
													public void run() {
														//finish();
														goToNextLevel();
													}
												});
											}
										}, new PathModifier(2, new Path(2).to(ship.getX(),ship.getY()).to(
												ship.getX(), -100), EaseSineInOut
												.getInstance())));
							}
						}, new ScaleModifier(1, 0, 1),
						new DelayModifier(1),
						new ScaleModifier(1, 1, 0)));
	}
	
	public void gameWarning() {
		
		final Text textCenter = new Text(
				CAMERA_WIDTH / 2 - 195,
				CAMERA_HEIGHT / 2 - 60, font,
				"WARNING", HorizontalAlign.CENTER);
		mEngine.getScene().getTopLayer()
				.addEntity(textCenter);
		
		textCenter
				.addShapeModifier(new SequenceModifier(
						new IShapeModifierListener() {

							@Override
							public void onModifierFinished(
									IShapeModifier pShapeModifier,
									IShape pShape) {
								//GameActivity.isBossFight = true;
								
								boss.addToScene();
								boss.fight();
							}
						}, new ScaleModifier(1, 0, 1),
						new DelayModifier(1),
						new RotationByModifier(1.5f, 360),
						new DelayModifier(1),
						new ScaleModifier(1, 1, 0)));
	}

	public void createEnemyShip(Path path) {

		if (enemiesToReuse.isEmpty()) {
			final EnemyShip enemy = new EnemyShip(CAMERA_WIDTH / 2
					- mShipTextureRegion.getTileWidth() / 2, -50,
					mEnemyTextureRegion, getEngine());
			enemy.addToScene();
			enemy.folow(path, 10);
		} else {
			final EnemyShip enemy = EnemyShip.reuse();
			enemy.addToScene();
			enemy.folow(path, 10);
		}
	}

	public void checkCollusions() {

		// COLLUSION BETWEEN SHIP AND ENEMY BULLETS
		if (enemyBullets.size() > 0) {
			for (int i = 0; i < enemyBullets.size(); i++) {
				final EnemyBullet bullet = enemyBullets.get(i);
				if (BaseCollisionChecker.checkAxisAlignedRectangleCollision(
						bullet.getX(), bullet.getY(),
						bullet.getX() + bullet.getWidth(), bullet.getY()
								+ bullet.getHeight(), ship.getX(), ship.getY(),
						ship.getX() + ship.getWidth(),
						ship.getY() + ship.getHeight()) == true) {
					runOnUpdateThread(new Runnable() {
						public void run() {

							bullet.removeFromScene();
							ship.hit();
							
							
							// ISTAMPAJ HEALTH BAR
							Debug.d("HEALTH > "+ship.getHealth());
							if(ship.getHealth()>=0){
								/************修改血量************/
								healthbar.setWidth((ship.getHealth()+1)*5);
							}else{
								/************修改血量************/
								healthbar.setWidth((ship.getHealth()+1)*5);
								healthbar.setVisible(false);
								okvir.setVisible(false);
							}
							
							if (GameActivity.options.getVibration() == true)
								vibrate();

							if (ship.getHealth() < 0) {
								if (GameActivity.options.getSoundEffects() == true)
									explosionSound.play();
								makeExplosion(ship.getX(), ship.getY());
								showGameOver();
							}
						}
					});
				}
			}
		}
		
		if(boss!=null && boss.getLaser()!=null){
			if(boss.getLaser().isVisible()==true)
			{
				if ((BaseCollisionChecker.checkAxisAlignedRectangleCollision(
						boss.getLaser().getX(), boss.getLaser().getY(),
						boss.getLaser().getX() + boss.getLaser().getWidth(), boss.getLaser().getY()
								+ boss.getLaser().getHeight(), ship.getX(), ship.getY(),
						ship.getX() + ship.getWidth(),
						ship.getY() + ship.getHeight()) == true && ship.isKilled()==false) ||
						(BaseCollisionChecker.checkAxisAlignedRectangleCollision(
								boss.getLaser2().getX(), boss.getLaser2().getY(),
								boss.getLaser2().getX() + boss.getLaser2().getWidth(), boss.getLaser2().getY()
										+ boss.getLaser2().getHeight(), ship.getX(), ship.getY(),
								ship.getX() + ship.getWidth(),
								ship.getY() + ship.getHeight()) == true && ship.isKilled()==false)
				) {
					runOnUpdateThread(new Runnable() {
						public void run() {
							ship.kill();
							
							if (GameActivity.options.getVibration() == true)
								vibrate();
							
							if (GameActivity.options.getSoundEffects() == true)
									explosionSound.play();
						
							makeExplosion(ship.getX(), ship.getY());
							showGameOver();
						}
					});
				}
			}
		}

		// COLLUSION BETWEEN ENEMY AND SHIP BULLETS
		if (bullets.size() > 0) {
			
				for (int i = 0; i < bullets.size(); i++) {
					final Bullet bullet = bullets.get(i);
					
					if (enemies.size() > 0) {
						for (int j = 0; j < enemies.size(); j++) {
							final EnemyShip enemy = enemies.get(j);
							if (BaseCollisionChecker
									.checkAxisAlignedRectangleCollision(
											bullet.getX(), bullet.getY(),
											bullet.getX() + bullet.getWidth(),
											bullet.getY() + bullet.getHeight(),
											enemy.getX(), enemy.getY(),
											enemy.getX() + enemy.getWidth(),
											enemy.getY() + enemy.getHeight()) == true
									&& !enemy.isKilled()) {
								runOnUpdateThread(new Runnable() {
									public void run() {
										score.addPoints();

										/************修改血量************/
										ship.addHealth();
										healthbar.setWidth((ship.getHealth()+1)*5);
										
										refreshScore();
										bullet.removeFromScene();
										if (GameActivity.options.getSoundEffects() == true)
											explosionSound.play();
										makeExplosion(enemy.getX(), enemy.getY());
										enemy.removeFromScene();
									}
								});
							}
						}
					}
					
					if (BaseCollisionChecker
							.checkAxisAlignedRectangleCollision(
									bullet.getX(), bullet.getY(),
									bullet.getX() + bullet.getWidth(),
									bullet.getY() + bullet.getHeight(),
									boss.getX(), boss.getY(), boss.getX()
											+ boss.getWidth(), boss.getY()
											+ boss.getHeight()) == true
							&& !boss.isKilled()) {
						runOnUpdateThread(new Runnable() {
							public void run() {
								score.addPoints();
								refreshScore();
								bullet.removeFromScene();
								if (GameActivity.options.getSoundEffects() == true)
									explosionSound.play();
								makeExplosion(boss.getX()+15, boss.getY()+15);
								boss.addShapeModifier(new SequenceModifier(new ColorModifier(0.3f, 1, 1, 1, 0, 1, 0), new ColorModifier(0.3f, 1, 1, 0, 1, 0, 1)));
							}
						});
					}
				}
		}
	}
	
	public void refreshScore(){
		runOnUiThread(new Runnable() {	
			@Override
			public void run() {
				GameActivity.scoreView.setText("SCORE: "+score.getScore());
			}
		});
	}

	public void makeExplosion(float posx, float posy) {
		if (explosionsToReuse.isEmpty())
			new Explosion(posx, posy, mExplosionTextureRegion, getEngine());
		else
			Explosion.reuse(posx, posy);
	}

	public void vibrate() {
		// VIBRACIJA KADA JE GAME OVER ILI KADA TI POGODE BROD
		// BITNO JE DA U MANIFESTU IMA艩 PRIVILEGIJE -->
		// <uses-permission android:name="android.permission.VIBRATE"/>
		final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(500);
	}

	public void showGameOver() {
		ship.addShapeModifier(new SequenceModifier(
				new IShapeModifierListener() {
					@Override
					public void onModifierFinished(
							IShapeModifier pShapeModifier, IShape pShape) {
						runOnUpdateThread(new Runnable() {
							public void run() {
								isGameOver = true;

								if (GameActivity.options.getSoundEffects() == true)
									GameActivity.gameOverSound.play();

								ship.removeFromScene();
								ship.setPosition(-20, ship.getAccelerationY()); // IZBACI GA SA VIDA SCENE

								final Text textCenter = new Text(
										CAMERA_WIDTH / 2 - 120,
										CAMERA_HEIGHT / 2 - 60, gameOver_font,
										"GAME\nOVER", HorizontalAlign.CENTER);
								mEngine.getScene().getTopLayer()
										.addEntity(textCenter);
								textCenter
										.addShapeModifier(new SequenceModifier(
												new IShapeModifierListener() {

													@Override
													public void onModifierFinished(
															IShapeModifier pShapeModifier,
															IShape pShape) {
														ship.clearShapeModifiers();
														if (options.getMusic() == true) {
															music.stop();
														}
														
														mEngine.stop();

														final Highscore highscore = new Highscore(getApplicationContext());
														
														if(highscore.inHighscore(score.getScore()))
														{
														runOnUiThread(new Runnable() {
															
															@Override
															public void run() {
																AlertDialog.Builder alert = new AlertDialog.Builder(GameActivity.this);

																alert.setTitle("HIGHSCORE");
																alert.setIcon(R.drawable.trophy);
																alert.setMessage("Score : "+score.getScore()+"\nEnter your name:");

																final LinearLayout layout = new LinearLayout(GameActivity.this);
																final EditText input = new EditText(GameActivity.this);
																layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
																input.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
																layout.setPadding(20, 0, 20, 0);
																layout.addView(input);
																
																alert.setView(layout);

																alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
																public void onClick(DialogInterface dialog, int whichButton) {
																  String value = input.getText().toString();
																  
																	highscore.addScore(value,score.getScore());
																  finish();
																  }
																});

																alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
																  public void onClick(DialogInterface dialog, int whichButton) {
																	  finish();
																  }
																});

																alert.show();
															}
														});
														}else{
															finish();
														}

													}
												}, new ScaleModifier(1, 0, 1),
												new DelayModifier(2),
												new ScaleModifier(1, 1, 0),
												new DelayModifier(1)));
							}
						});
					}
				}, new ScaleModifier(0.3f, 1, 0)));
		
		ship.getShadow().addShapeModifier(new SequenceModifier(new ScaleModifier(0.3f, 1, 0)));
	}

	public void showExitDialog() {
		if (options.getMusic() == true) {
			music.pause();
		}
		mEngine.stop();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to exit?")
				.setCancelable(false)
				.setTitle("EXIT")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								if (options.getMusic() == true) {
									music.stop();
								}
								finish();
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						if (options.getMusic() == true) {
							music.play();
						}
						mEngine.start();
					}
				});
		AlertDialog alert = builder.create();
		alert.setIcon(R.drawable.icon);
		alert.show();
	}
}
