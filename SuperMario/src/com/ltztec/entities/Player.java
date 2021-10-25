package com.ltztec.entities;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.ltztec.main.Game;
import com.ltztec.world.Camera;
import com.ltztec.world.World;


public class Player extends Entity{
	
	public boolean right, left;
	public double speed = 1.1;
	private double gravity = 1.3;
	
	public int right_dir = 0, left_dir = 1;
	public int dir = right_dir;
	
	private int frames = 0, maxFrames = 8, index = 0, maxIndex = 2;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	
	
	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
		
		rightPlayer = new BufferedImage[3];
		leftPlayer = new BufferedImage[3];
		
		
		for (int i = 0; i < 3; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(80 + (i * 16), 0, 16, 16);
		}
		for (int i = 0; i < 3; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(80 + (i * 16), 16, 16, 16);
		}
	}
	
	public void tick(){
		
		if(World.isFree((int)x, (int)(y+gravity))) {
			y+=gravity;
		}
		
		depth = 2;
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
	}
	

	

	public void render(Graphics g) {
	
			if (dir == right_dir) {
				g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				
			} else if (dir == left_dir) {
				g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				
			}
	}
	


}
