package com.xiang.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;
import java.util.Random;

import com.xiang.action.ActionAble;
import com.xiang.client.GameClient;
import com.xiang.constant.Constant;
import com.xiang.util.GetImageUtil;
import com.xiang.util.SinglePlay;

/**
* @ClassName: Bullet
* @Description: �ӵ���
* @author guoxiang
* @date 2019��8��19�� 
*
*/
public class Bullet extends GameObj implements ActionAble {

	// ���ʲ������ֵĶ���
	SinglePlay singlePlay = new SinglePlay();
	
	// �����ٶ�
	private Integer speed;
	
	// �õ��ͻ���
	public GameClient gc;
	
	// �ӵ�����
	public boolean isGood;
	
	
	public Bullet() {
		
	}
	
	public Bullet(int x,int y,String imgName,GameClient gc,boolean isGood) {
		this.x = x;
		this.y = y;
		this.img = GetImageUtil.getImg(imgName);
		this.speed = 50;
		this.gc = gc;
		this.isGood = isGood;
	}
	
	@Override
	public void move() {
		if(isGood) {
			x += speed;
		}
		else {
			x -= speed;
		}
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, x, y, null);
		move();
		outOfBounds();
	}
	
	// �ӵ�Խ������
	public void outOfBounds() {
		if(x > Constant.GAME_WIDTH||x < 0) {
			gc.bullets.remove(this);
		}
	}
	
	// ���
	Random random = new Random();
	
	// �ӵ���һ���л�
	public boolean hitMonster(Plane plane) {
		Boom boom = new Boom(plane.x, plane.y,gc);;
		if(this.getRec().intersects(plane.getRec())&&this.isGood!=plane.isGood) {
			if(plane.isGood) {
				singlePlay.play("com/xiang/sound/music03.mp3");
				plane.blood -= 10;
				if(plane.blood == 0) {
					singlePlay.play("com/xiang/sound/music04.mp3");
					// ��������
					gc.planes.remove(plane);
					
					
				}
				// �Ƴ��ӵ�
				gc.bullets.remove(this);
			}
			else {
				singlePlay.play("com/xiang/sound/music03.mp3");
				if(plane instanceof Boss) {
					
					plane.blood -= 10;
					
					// �Ƴ��ӵ�
					gc.bullets.remove(this);
					if(plane.blood <= 0) {
						gc.bosss.remove(plane);
						// �Ƴ��ӵ�
						gc.bullets.remove(this);
					}
				}
				else {
					// �Ƴ����е���
					gc.enemys.remove(plane);
					// �Ƴ��ӵ�
					gc.bullets.remove(this);

					// �������һ������
					if(random.nextInt(500)>0) {
						Prop prop = new Prop(plane.x+plane.img.getWidth(null)/2, plane.y, "prop/prop.png");
						gc.props.add(prop);
					}
				}
			}
			// ��ӱ�ը
			gc.booms.add(boom);
			return true;
		}
		return false;
	}
	
	// �ӵ������л�
	public boolean hitMonsters(List<Plane> monsters) {
		if(monsters == null) {
			return false;
		}
		for(int i=0;i<monsters.size();i++) {
			if(hitMonster(monsters.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	// ��ȡ�ӵ��ľ���
	public Rectangle getRec() {
		return new Rectangle(x, y, this.img.getWidth(null), this.img.getHeight(null));
	}
	
}
