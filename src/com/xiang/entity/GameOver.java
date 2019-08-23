package com.xiang.entity;

import java.awt.Graphics;

import javax.swing.JButton;

import com.xiang.action.ActionAble;
import com.xiang.util.GetImageUtil;

public class GameOver extends GameObj implements ActionAble {

	public GameOver() {
		// TODO Auto-generated constructor stub
	}
	
	public GameOver(int x,int y,String imgName) {
		this.x = x;
		this.y = y;
		this.img = GetImageUtil.getImg(imgName);
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(img, x, y, null);
		
	}
}
