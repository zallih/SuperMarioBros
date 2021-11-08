package com.ltztec.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.ltztec.main.Game;
import com.ltztec.world.Camera;



public class Coins extends Entity{

	private int frames = 0, maxFrames = 8, index = 0, maxIndex = 3;
	public static BufferedImage coinSprite[];
	
	public Coins(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		
		
		coinSprite = new BufferedImage[4];
		
		for (int i = 0; i < 4; i++) {
			coinSprite[i] = Game.spritesheet.getSprite(0 + (i * 16), 64, 16, 16);
		}
	}
	
	
	public void tick() {
		depth = 0;
		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if (index > maxIndex) {
				index = 0;
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(coinSprite[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
}
