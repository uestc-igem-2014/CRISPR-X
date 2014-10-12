package me.trifunovic.spaceassault.game.background;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.shape.Shape;

public class ScrollBackground extends ColorBackground {

		private final Shape mShape;
		private final float mScrollChangePerSecond;
		protected float mScrollValue;

		public ScrollBackground(final Shape pShape, final float pScrollChangePerSecond) {
			super(0, 0, 0);
			this.mScrollChangePerSecond = pScrollChangePerSecond;
			mShape = pShape;
		}

		public void setScrollValue(final float pScrollValue) {
			this.mScrollValue = pScrollValue;
		}

		@Override
		public void onUpdate(final float pSecondsElapsed) {
			super.onUpdate(pSecondsElapsed);

			this.mScrollValue += this.mScrollChangePerSecond * pSecondsElapsed;
		}
		
		@Override
		public void onDraw(final GL10 pGL, final Camera pCamera) {
			super.onDraw(pGL, pCamera);
			
			pGL.glPushMatrix();
			{
				final float cameraHeight = pCamera.getHeight();
				final float shapeHeightScaled = 800;
				float baseOffset = mScrollValue % shapeHeightScaled;

				while(baseOffset > 0) {
					baseOffset -= shapeHeightScaled;
				}
				pGL.glTranslatef(0, baseOffset, 0);

				float currentMaxY = baseOffset;

				do {
					this.mShape.onDraw(pGL, pCamera);
					pGL.glTranslatef(0, shapeHeightScaled, 0);
					currentMaxY += shapeHeightScaled;
				} while(currentMaxY < cameraHeight);
			}
			pGL.glPopMatrix();
		}
	}
