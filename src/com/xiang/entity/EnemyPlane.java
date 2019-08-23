package com.xiang.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.xiang.action.ActionAble;
import com.xiang.client.GameClient;
import com.xiang.util.GetImageUtil;

/**
* @ClassName: EnemyPlane
* @Description: �л���
* @author guoxiang
* @date 2019��8��20�� 
*
*/
public class EnemyPlane extends Plane implements ActionAble {

	
	
	private Integer speed;
	
	public GameClient gc;
	
	public EnemyPlane() {
		
	}
	
	public EnemyPlane(int x,int y,String imgName,GameClient gc,boolean isGood) {
		this.x = x;
		this.y = y;
		this.img = GetImageUtil.getImg(imgName);
		this.speed = 4;
		this.gc = gc;
		this.isGood = isGood;
	}
	
	@Override
	public void move() {
		x -= speed;
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, x, y, null);
		move();
		
		if(random.nextInt(500)>490) {
			fire();
		}
	}
	
	// �����
	Random random = new Random();
	
	// �л�����
	public void fire() {
		Bullet bullet = new Bullet(x, y+18, "bullet/enemyBullet.png", gc,false);
		gc.bullets.add(bullet);
	}
	
	
	
	// ��ȡ�л��ľ���
	public Rectangle getRec() {
		return new Rectangle(x, y, this.img.getWidth(null), this.img.getHeight(null));
	}
}
