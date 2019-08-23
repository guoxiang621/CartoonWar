package com.xiang.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import com.xiang.action.ActionAble;
import com.xiang.client.GameClient;
import com.xiang.constant.Constant;
import com.xiang.util.GetImageUtil;

public class Boss extends Plane implements ActionAble {

	private boolean up;
	
	private int speed;
	
	 
	public Boss() {
		// TODO Auto-generated constructor stub
	}
	
	public Boss(int x,int y,GameClient gc,String imgName,boolean isGood) {
		this.x = x;
		this.y = y;
		this.gc = gc;
		this.speed = 3;
		this.img = GetImageUtil.getImg(imgName);
		this.up = true;
		this.isGood = isGood;
		this.blood = 200;
	}
	
	
	// 动态初始化一个图片数组
//	private static Image[] imgs = new Image[6];
//	static {
//		for(int i=0;i<imgs.length;i++) {
//			imgs[i] = GetImageUtil.getImg("boss/boss"+(i+1)+".png");
//		}
//	}
	
//	int count = 0;
	@Override
	public void draw(Graphics g) {
//		if(count > 5) {
//			count = 0;
//		}
		g.drawImage(img, x, y, null);
		move();
//		g.drawString("当前血量："+blood, x+15, y-10);
		g.setColor(Color.RED);
		g.drawRect(x+15, y-30, 200, 10);
		g.fillRect(x+15, y-30, blood, 10);
	}
	
	@Override
	public void move() {
		x -= speed;
		if(x<1000) {
			x = 1000;
			if(up) {
				y -= speed;
			}
			if(!up) {
				y += speed;
			}
			if(y>Constant.GAME_HEIGHT-img.getHeight(null)) {
				up = true;
			}
			if(y<30) {
				up = false;
			}
		}
		if(random.nextInt(500)>450) {
			fire();
		}
	}
	
	// 生成随机数
	Random random = new Random();
	
	@Override
	public void fire() {
		singlePlay.play("com/xiang/sound/music02.mp3");
		Bullet b = new Bullet(x, y+this.img.getHeight(null)/4-11, "bullet/enemyBullet.png",gc,false);
		gc.bullets.add(b);
	}
}
