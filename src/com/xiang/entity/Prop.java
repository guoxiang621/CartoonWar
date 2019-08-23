package com.xiang.entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.xiang.action.ActionAble;
import com.xiang.constant.Constant;
import com.xiang.util.GetImageUtil;

/**
* @ClassName: Prop
* @Description: ������
* @author guoxiang
* @date 2019��8��21�� 
*
*/
public class Prop extends GameObj implements ActionAble {

	private int speed;
	
	public Prop() {
		// TODO Auto-generated constructor stub
	}
	
	public Prop(int x,int y,String imgName) {
		this.x = x;
		this.y = y;
		this.img = GetImageUtil.getImg(imgName);
		this.speed = 5;
	}
	
	
	private double theta = Math.PI/4;
	@Override
	public void move() {
		x += speed*Math.cos(theta);
		y += speed*Math.sin(theta);
		if(x>Constant.GAME_WIDTH-img.getWidth(null)) {
			theta = Math.PI-theta;
		}
		if(y<35 || y>Constant.GAME_HEIGHT-img.getWidth(null)) {
			theta = -theta;
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(img, x, y, null);
		move();
	}

	// �õ���ǰ���ߵľ���
	public Rectangle getRect() {
		return new Rectangle(x, y, this.img.getWidth(null), this.img.getHeight(null));
		
	}
}
