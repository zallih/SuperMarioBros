package com.ltztec.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.ltztec.main.Game;
import com.ltztec.main.Sound;
import com.ltztec.world.Camera;
import com.ltztec.world.World;

public class Enemy extends Entity {

	private double speed = 0.8;

	public boolean right = true, left = false;;

	private int maskx = 2, masky = 2, maskw = 16, maskh = 16;
	public static int frames = 0, maxFrames = 30, index = 0, maxIndex = 1;
	public static BufferedImage[] sprites;

	private double gravity = 1.2;

	public int life = 4;
	public boolean isDamage = false;
	private int damageFrames = 0;



	public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);

		sprites = new BufferedImage[2];
		sprites[0] = Game.spritesheet.getSprite(32, 32, 16, 16);
		sprites[1] = Game.spritesheet.getSprite(48, 32, 16, 16);
		
		for (int i = 0; i < 2; i++) {
			sprites[i] = Game.spritesheet.getSprite(32 + (i * 16), 32, 16, 16);
		}
		
	}

	public void tick() {
		depth = 1;
		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			//System.out.println(index);
			if (index > maxIndex) {
				index = 0;
			}
		}

		if (World.isFree((int) x, (int) (y + gravity))) {
			y += gravity;
		} else {

			if (right == true) {
				if (World.isFree((int) (x + speed), (int) y)) {
					x += speed;
					if (World.isFree((int) (x + 16), (int) (y + 1))) {
						right = false;
						left = true;
					}
				} else {
					right = false;
					left = true;

				}

			} else if (left == true) {

				if (World.isFree((int) (x - speed), (int) y)) {

					x -= speed;
					if (World.isFree((int) (x - 16), (int) (y + 1))) {
						right = true;
						left = false;
					}
				} else {
					right = true;
					left = false;

				}
			}
		}
		
		
		if (life <= 0) {
			this.destroySelf();
			return;
		}
		
		if (isDamage) {
			Sound.hit.play();
			this.damageFrames++;
			if (this.damageFrames == 5) {
				this.damageFrames = 0;
				this.isDamage = false;
			}
		}
		
	}

	
	public void destroySelf() {
		Game.enemies.remove(this);
		Game.entities.remove(this);
		Game.currentEnemy--;
	}
	
	public boolean isCollidingWithPlayer() {
		
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY(), maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);
		return enemyCurrent.intersects(player);
	}


	
	public void reder(Graphics g) {

		g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);

	}

}
