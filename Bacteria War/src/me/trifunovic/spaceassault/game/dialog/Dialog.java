package me.trifunovic.spaceassault.game.dialog;

import java.lang.reflect.Field;

import me.trifunovic.spaceassault.game.GameActivity;
import me.trifunovic.spaceassault.game.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public class Dialog {

	public void showShop(final Context context) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {

				AlertDialog.Builder alert = new AlertDialog.Builder(context);

				final String[] arrayItem = new String[] { "Speed:1500",
						"Fire:1500", "Armor:1000", "Recover:3000" };

				alert.setTitle("UPDATE YOUR SHIP");
				alert.setIcon(R.drawable.trophy);
				// alert.setMessage("Your score:" +
				// String.valueOf(GameActivity.score.getScore()));
				final int selectedIndex;

				alert.setSingleChoiceItems(arrayItem, 0,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								selectedIndex = which;
							}
						});

				alert.setNeutralButton("BUY IT",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								Field field = null;
								try {
									field = dialog.getClass().getSuperclass()
											.getDeclaredField("mShowing");
									field.setAccessible(true);
								} catch (Exception e) {
									e.printStackTrace();
								}
								switch (selectedIndex) {
								case 0:
									GameActivity.mParameters
											.setPlayerShipFlySpeed(nowPlayerShipFlySpeed * 1.200f);
									if (GameActivity.score.getScore() < 1500) {
										Toast.makeText(context,
												"Score Not Enough",
												Toast.LENGTH_SHORT).show();
										// 用于不关闭对话框
										try {
											field.set(dialog, false);
										} catch (Exception e) {
											e.printStackTrace();
										}
									} else {
										GameActivity.score.subPoints(1500);
										// 用于不关闭对话框
										try {
											field.set(dialog, true);
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
									break;
								case 1:
									GameActivity.mParameters
											.setPlayerShipFireSpeed(nowPlayerShipFireSpeed * 0.800f);
									if (GameActivity.score.getScore() < 1500) {
										Toast.makeText(context,
												"Score Not Enough",
												Toast.LENGTH_SHORT).show();
										// 用于不关闭对话框
										try {
											field.set(dialog, false);
										} catch (Exception e) {
											e.printStackTrace();
										}
									} else {
										score.subPoints(1500);
										// 用于不关闭对话框
										try {
											field.set(dialog, true);
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
									break;
								case 2:
									GameActivity.mParameters.setPlayerShipArmor(mParameters
											.getPlayerShipArmor() + 1);
									if (GameActivity.score.getScore() < 1500) {
										Toast.makeText(context,
												"Score Not Enough",
												Toast.LENGTH_SHORT).show();
										// 用于不关闭对话框
										try {
											field.set(dialog, false);
										} catch (Exception e) {
											e.printStackTrace();
										}
									} else {
										score.subPoints(1000);
										// 用于不关闭对话框
										try {
											field.set(dialog, true);
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
									break;
								case 3:
									mParameters.setPlayerRecover(mParameters
											.getPlayerRecover() + 1);
									if (GameActivity.score.getScore() < 1500) {
										Toast.makeText(context,
												"Score Not Enough",
												Toast.LENGTH_SHORT).show();
										// 用于不关闭对话框
										try {
											field.set(dialog, false);
										} catch (Exception e) {
											e.printStackTrace();
										}
									} else {
										score.subPoints(3000);
										// 用于不关闭对话框
										try {
											field.set(dialog, true);
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
									break;
								default:
									break;
								}
								mEngine.start();
							}
						});

				alert.setNegativeButton("GO ON",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								try {
									Field field = dialog.getClass()
											.getSuperclass()
											.getDeclaredField("mShowing");
									field.setAccessible(true);
									field.set(dialog, true);
									mEngine.start();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

				mEngine.stop();
				alert.show();
			}
		});
	};
}
