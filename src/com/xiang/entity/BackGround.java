package com.xiang.entity;

import java.awt.Graphics;

import com.xiang.action.ActionAble;
import com.xiang.util.GetImageUtil;

/**
* @ClassName: BackGround
* @Description: ������
* @author guoxiang
* @date 2019��8��19�� 
*
*/
public class BackGround extends GameObj implements ActionAble {

	private Integer speed;

	public BackGround() {
		
	}
	
	public BackGround(int x,int y,String imgName) {
		this.x = x;
		this.y = y;
		this.img = GetImageUtil.getImg(imgName);
		this.speed = 1;
	}
	
	@Override
	public void move() {
		x -= speed;
	}

	@Override
	public void draw(Graphics g) {
		
		g.drawImage(img, x, y, null);
		move();
		
	}
}
