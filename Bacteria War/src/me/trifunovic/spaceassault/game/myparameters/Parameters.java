package me.trifunovic.spaceassault.game.myparameters;

public class Parameters {

	public int GameRound = 1;

	public int EnemyShip0FlySpeed = 10;
	public int EnemyShip1FlySpeed = 10;
	public int EnemyShip2FlySpeed = 10;
	public float PlayerShipFlySpeed = 1.000f;

	public int EnemyShip0FireSpeed = 300;
	public int EnemyShip1FireSpeed = 300;
	public int EnemyShip2FireSpeed = 300;
	public float PlayerShipFireSpeed = 1.000f;

	public int EnemyShip0Frequency = 7;
	public int EnemyShip1Frequency = 9;
	public int EnemyShip2Frequency = 10;

	public int PlayerShipArmor = 0;
	public int PlayerShipRecover = 1;

	public void EnemyShipAllUpdata() {
		EnemyShip0Updata();
		EnemyShip1Updata();
		EnemyShip2Updata();
	}

	public void EnemyShip0Updata() {
		if (EnemyShip0FlySpeed > 5) {
			EnemyShip0FlySpeed -= 1;
		}
		if (EnemyShip0Frequency > 3) {
			EnemyShip0Frequency -= 1;
		}
		EnemyShip0FireSpeed += 50;
	}

	public void EnemyShip1Updata() {
		if (EnemyShip1FlySpeed > 5) {
			EnemyShip1FlySpeed -= 1;
		}
		if (EnemyShip1Frequency > 5) {
			EnemyShip1Frequency -= 1;
		}
		EnemyShip1FireSpeed += 50;
	}

	public void EnemyShip2Updata() {
		if (EnemyShip2FlySpeed > 5) {
			EnemyShip2FlySpeed -= 1;
		}
		if (EnemyShip2Frequency > 8) {
			EnemyShip2Frequency -= 1;
		}
		EnemyShip2FireSpeed += 50;
	}

	public int getPlayerRecover() {
		return PlayerShipRecover;
	}

	public void setPlayerRecover(int playerRecover) {
		PlayerShipRecover = playerRecover;
	}

	public int getGameRound() {
		return GameRound;
	}

	public void setGameRound(int gameRound) {
		GameRound = gameRound;
	}

	public int getEnemyShip0FlySpeed() {
		return EnemyShip0FlySpeed;
	}

	public void setEnemyShip0FlySpeed(int enemyShip0FlySpeed) {
		EnemyShip0FlySpeed = enemyShip0FlySpeed;
	}

	public int getEnemyShip1FlySpeed() {
		return EnemyShip1FlySpeed;
	}

	public void setEnemyShip1FlySpeed(int enemyShip1FlySpeed) {
		EnemyShip1FlySpeed = enemyShip1FlySpeed;
	}

	public int getEnemyShip2FlySpeed() {
		return EnemyShip2FlySpeed;
	}

	public void setEnemyShip2FlySpeed(int enemyShip2FlySpeed) {
		EnemyShip2FlySpeed = enemyShip2FlySpeed;
	}

	public float getPlayerShipFlySpeed() {
		return PlayerShipFlySpeed;
	}

	public void setPlayerShipFlySpeed(float playerShipFlySpeed) {
		if (playerShipFlySpeed < 2.0f) {
			PlayerShipFlySpeed = playerShipFlySpeed;
		}
	}

	public int getEnemyShip0FireSpeed() {
		return EnemyShip0FireSpeed;
	}

	public void setEnemyShip0FireSpeed(int enemyShip0FireSpeed) {
		EnemyShip0FireSpeed = enemyShip0FireSpeed;
	}

	public int getEnemyShip1FireSpeed() {
		return EnemyShip1FireSpeed;
	}

	public void setEnemyShip1FireSpeed(int enemyShip1FireSpeed) {
		EnemyShip1FireSpeed = enemyShip1FireSpeed;
	}

	public int getEnemyShip2FireSpeed() {
		return EnemyShip2FireSpeed;
	}

	public void setEnemyShip2FireSpeed(int enemyShip2FireSpeed) {
		EnemyShip2FireSpeed = enemyShip2FireSpeed;
	}

	public int getPlayerShipArmor() {
		return PlayerShipArmor;
	}

	public void setPlayerShipArmor(int playerShipArmor) {
		PlayerShipArmor = playerShipArmor;
	}

	public int getEnemyShip0Frequency() {
		return EnemyShip0Frequency;
	}

	public void setEnemyShip0Frequency(int enemyShip0Frequency) {
		EnemyShip0Frequency = enemyShip0Frequency;
	}

	public int getEnemyShip1Frequency() {
		return EnemyShip1Frequency;
	}

	public void setEnemyShip1Frequency(int enemyShip1Frequency) {
		EnemyShip1Frequency = enemyShip1Frequency;
	}

	public int getEnemyShip2Frequency() {
		return EnemyShip2Frequency;
	}

	public void setEnemyShip2Frequency(int enemyShip2Frequency) {
		EnemyShip2Frequency = enemyShip2Frequency;
	}

	public void setPlayerShipFireSpeed(float playerShipFireSpeed) {
		if (playerShipFireSpeed > 0.5f) {
			PlayerShipFireSpeed = playerShipFireSpeed;
		}
	}

	public float getPlayerShipFireSpeed() {
		return PlayerShipFireSpeed;
	}

}
