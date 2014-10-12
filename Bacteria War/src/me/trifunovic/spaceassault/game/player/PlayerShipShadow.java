package me.trifunovic.spaceassault.game.player;

import me.trifunovic.spaceassault.game.GameActivity;

import org.anddev.andengine.entity.sprite.AnimatedSprite;

public class PlayerShipShadow extends AnimatedSprite {
	
	private final PlayerShip s;

	public PlayerShipShadow(PlayerShip ship) {
		super(ship.getX(), ship.getY(), ship.getTextureRegion());
		this.animate(GameActivity.ANIMATION_FRAMELENGTH);
		this.setColor(0, 0, 0);
		this.setAlpha(0.4f);
		s=ship;
	}
	
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		
		if(s.getX()+s.getWidth()/2>= GameActivity.CAMERA_WIDTH/2)
			this.setPosition(s.getX()-((s.getX()-GameActivity.CAMERA_WIDTH/2)/2), s.getY()+10);
		else
			this.setPosition(s.getX()+((GameActivity.CAMERA_WIDTH/2-s.getX())/2), s.getY()+10);
		
		super.onManagedUpdate(pSecondsElapsed);
	}

}
