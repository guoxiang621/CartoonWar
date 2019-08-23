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
* @Description: 创建一个飞机类
* @author guoxiang
* @date 2019年8月19日 
*
*/
public class Plane extends GameObj implements ActionAble{
	
	// 单词播放音乐的对象
	SinglePlay singlePlay = new SinglePlay();
	
	// 速度
	private Integer speed;
	// 方向布尔变量
	private boolean left,up,right,down;
	
	// 客户端拿过来
	public GameClient gc;
	
	// 判断我军还是敌军
	public boolean isGood;
	
	// 添加飞机子弹等级
	public int bulletLevel = 1;
	
	// 增加血量
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
	
	// 移动的方法
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
	
	// 画一个飞机出来
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, x, y, null);
		move();
		g.drawString("当前我方血量:"+blood, 10, 140);
		g.setColor(Color.RED);
		g.drawRect(x+15, y-30, 100, 10);
		g.fillRect(x+15, y-30, blood, 10);
	}
	
	// 处理边界问题
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
	
	// 键盘摁下（开关法）
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
	// 键盘释放（开关法）
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
	
	// 我方飞机开火
	public void fire() {
		if(gc.planes.size() != 0) {
			singlePlay.play("com/xiang/sound/music02.mp3");
			Bullet b = new Bullet(x+this.img.getWidth(null), y+this.img.getHeight(null)/4, "bullet/bullet00"+bulletLevel+".png",gc,true);
			gc.bullets.add(b);
		}
//		gc.bullets.add(b);
	}
	
	// 获取当前飞机的矩形
	public Rectangle getRec() {
		return new Rectangle(x, y, this.img.getWidth(null), this.img.getHeight(null));
	}
	
	//	检测我方角色碰撞道具
	public void containItem(Prop prop) {
		if(this.getRec().intersects(prop.getRect())) {
			// 移除道具
			gc.props.remove(prop);
			// 更改子弹等级
			if(bulletLevel>6) {
				bulletLevel = 7;
				return;
			}
			this.bulletLevel += 1;
			
		}
	}
	
	// 检测我方角色碰撞多个道具
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
