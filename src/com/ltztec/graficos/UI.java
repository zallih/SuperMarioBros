package com.ltztec.graficos;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.ltztec.entities.Player;
import com.ltztec.main.Game;



public class UI {

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(15, 15, 150, 25);
		g.setColor(Color.green);
		g.fillRect(15, 15, (int)((Game.player.life/Game.player.maxLife)*150), 25);
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD,20));
		g.drawString((int)Game.player.life+"/"+(int)Game.player.maxLife, 55, 35);
		g.drawString("Coins - " + (int)Player.currentCoins+"/"+(int)Player.maxCoins, 560, 35);
	}
	
}
