package com.ltztec.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.ltztec.main.Game;
import com.ltztec.world.Camera;
import com.ltztec.world.World;

public class Player extends Entity {

	public boolean right, left;
	public double speed = 1.2;
	private double gravity = 1.2;

	public double life = 100, maxLife = 100;

	public static int currentCoins = 0, maxCoins = 0; 
	
	public boolean jump = false;
	public boolean isJumping = false;
	public int jumpHeight = 40;
	public int jumpFrames = 0;
	private double vspd = 0;
	
	
	public int right_dir = 0, left_dir = 1;
	public int dir = right_dir;

	private int frames = 0, maxFrames = 8, index = 0, maxIndex = 2;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;

	private BufferedImage jumpRightPlayer;
	private BufferedImage jumpLeftPlayer;

	public Player(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);

		rightPlayer = new BufferedImage[3];
		leftPlayer = new BufferedImage[3];

		jumpRightPlayer = Game.spritesheet.getSprite(48, 0, 16, 16);
		jumpLeftPlayer = Game.spritesheet.getSprite(48, 16, 16, 16);

		for (int i = 0; i < 3; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(80 + (i * 16), 0, 16, 16);
		}
		for (int i = 0; i < 3; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(80 + (i * 16), 16, 16, 16);
		}
	}

	public void tick() {

		depth = 2;
//		vspd+=gravity;
//		if(!World.isFree((int)x,(int)(y+1)) && jump)
//		{
//			vspd = -10;
//			jump = false;
//		}
//		
//		if(!World.isFree((int)x,(int)(y+vspd))) {
//			
//			int signVsp = 0;
//			if(vspd >= 0)
//			{
//				signVsp = 1;
//			}else  {
//				signVsp = -1;
//			}
//			while(World.isFree((int)x,(int)(y+signVsp))) {
//				y = y+signVsp;
//			}
//			vspd = 0;
//		}
//		
//		y = y + vspd;
		
		Camera.x = Camera.clamp(this.getX() - Game.WIDTH / 2, 0, World.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - Game.HEIGHT / 2, 0, World.HEIGHT * 16 - Game.HEIGHT);

		if (World.isFree((int) x, (int) (y + gravity)) && isJumping == false) {
			y += gravity;

			// batendo no inimigo
			for (int i = 0; i < Game.entities.size(); i++) {
				Entity e = Game.entities.get(i);
				if (e instanceof Enemy) {
					if (Entity.isColidding(this, e)) {
						isJumping = true;
						jumpHeight = 32;
						((Enemy) e).life -= 2;

					}
				}
			}

		}

		moved = false;
		if (right && World.isFree((int) (x + speed), this.getY())) {
			moved = true;
			dir = right_dir;
			x += speed;
		} else if (left && World.isFree((int) (x - speed), this.getY())) {
			moved = true;
			dir = left_dir;
			x -= speed;
		}

		if (moved) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex) {
					index = 0;
				}
			}
		}

		if (jump == true) {
			if (!World.isFree(this.getX(), this.getY() + 1)) {
				isJumping = true;
			} else {
				jump = false;
			}
		}

		if (isJumping == true) {
			if (World.isFree(this.getX(), this.getY() - 2)) {
				y -= 2;
				jumpFrames += 2;
				jumpHeight = 40;
				if (jumpFrames == jumpHeight) {
					isJumping = false;
					jump = false;
					jumpFrames = 0;
				}
			} else {
				isJumping = false;
				jump = false;
				jumpFrames = 0;
			}
		}

		// inimigo batendo no player
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if (e instanceof Enemy) {
				if (Entity.isColidding(this, e)) {
					life-=0.3;
				}
			}
		}
		
		//Colisão com moeda
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if (e instanceof Coins) {
				if (Entity.isColidding(this, e)) {
					Player.currentCoins++;
					Game.entities.remove(i);
				}
			}
		}
		
		if(life == 0) {
			World.restartGame();
		}

	}

	public void render(Graphics g) {

		if (dir == right_dir && isJumping == false) {
			g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		} else if (dir == left_dir && isJumping == false) {
			g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}

		if (isJumping == true && dir == left_dir) {
			g.drawImage(jumpLeftPlayer, this.getX() - Camera.x, this.getY() - Camera.y, null);
		} else if (isJumping == true && dir == right_dir) {
			g.drawImage(jumpRightPlayer, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}

}
