package com.xiang.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;

import com.xiang.action.ActionAble;
import com.xiang.client.GameClient;
import com.xiang.constant.Constant;
import com.xiang.util.GetImageUtil;
import com.xiang.util.SinglePlay;

/**
* @ClassName: Plane
* @Description: ����һ���ɻ���
* @author guoxiang
* @date 2019��8��19�� 
*
*/
public class Plane extends GameObj implements ActionAble{
	
	// ���ʲ������ֵĶ���
	SinglePlay singlePlay = new SinglePlay();
	
	// �ٶ�
	private Integer speed;
	// ���򲼶�����
	private boolean left,up,right,down;
	
	// �ͻ����ù���
	public GameClient gc;
	
	// �ж��Ҿ����ǵо�
	public boolean isGood;
	
	// ��ӷɻ��ӵ��ȼ�
	public int bulletLevel = 1;
	
	// ����Ѫ��
	public int blood;
	
	public Plane() {
		
	}
	public Plane(int x,int y,String imgName,GameClient gc,boolean isGood) {
		this.x = x;
		this.y = y;
		this.img = GetImageUtil.getImg(imgName);
		this.speed = 20;
		this.gc = gc;
		this.isGood = isGood;
		this.blood = 100;
	}
	
	// �ƶ��ķ���
	@Override
	public void move() {
		if(left) {
			x -= speed;
		}
		if(up) {
			y -= speed;
		}
		if(right) {
			x += speed;
		}
		if(down) {
			y += speed;
		}
		outOfBound();
	}
	
	// ��һ���ɻ�����
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, x, y, null);
		move();
		g.drawString("��ǰ�ҷ�Ѫ��:"+blood, 10, 140);
		g.setColor(Color.RED);
		g.drawRect(x+15, y-30, 100, 10);
		g.fillRect(x+15, y-30, blood, 10);
	}
	
	// ����߽�����
	public void outOfBound() {
		if(y <= 25) {
			y = 25;
		}
		if(x <= -10) {
			x = -10;
		}
		if(x >= Constant.GAME_WIDTH-img.getWidth(null)) {
			x = Constant.GAME_WIDTH-img.getWidth(null);
		}
		if(y >= Constant.GAME_HEIGHT-img.getHeight(null)) {
			y = Constant.GAME_HEIGHT-img.getHeight(null);
		}
	}
	
	// �������£����ط���
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			left = true;
			break;
		case KeyEvent.VK_W:
			up = true;
			break;
		case KeyEvent.VK_D:
			right = true;
			break;
		case KeyEvent.VK_S:
			down = true;
			break;
		default:
			break;
		}
	}
	// �����ͷţ����ط���
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			left = false;
			break;
		case KeyEvent.VK_W:
			up = false;
			break;
		case KeyEvent.VK_D:
			right = false;
			break;
		case KeyEvent.VK_S:
			down = false;
			break;
		case KeyEvent.VK_J:
			fire();
			break;
		default:
			break;
		}		
	}
	
	// �ҷ��ɻ�����
	public void fire() {
		if(gc.planes.size() != 0) {
			singlePlay.play("com/xiang/sound/music02.mp3");
			Bullet b = new Bullet(x+this.img.getWidth(null), y+this.img.getHeight(null)/4, "bullet/bullet00"+bulletLevel+".png",gc,true);
			gc.bullets.add(b);
		}
//		gc.bullets.add(b);
	}
	
	// ��ȡ��ǰ�ɻ��ľ���
	public Rectangle getRec() {
		return new Rectangle(x, y, this.img.getWidth(null), this.img.getHeight(null));
	}
	
	//	����ҷ���ɫ��ײ����
	public void containItem(Prop prop) {
		if(this.getRec().intersects(prop.getRect())) {
			// �Ƴ�����
			gc.props.remove(prop);
			// �����ӵ��ȼ�
			if(bulletLevel>6) {
				bulletLevel = 7;
				return;
			}
			this.bulletLevel += 1;
			
		}
	}
	
	// ����ҷ���ɫ��ײ�������
	public void containItems(List<Prop> props) {
		if(props == null) {
			return;
		}
		else {
			for(int i=0;i<props.size();i++) {
				containItem(props.get(i));
			}
		}
	}
}
