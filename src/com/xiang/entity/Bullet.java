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
* @Description: 子弹类
* @author guoxiang
* @date 2019年8月19日 
*
*/
public class Bullet extends GameObj implements ActionAble {

	// 单词播放音乐的对象
	SinglePlay singlePlay = new SinglePlay();
	
	// 创建速度
	private Integer speed;
	
	// 拿到客户端
	public GameClient gc;
	
	// 子弹类型
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
	
	// 子弹越界销毁
	public void outOfBounds() {
		if(x > Constant.GAME_WIDTH||x < 0) {
			gc.bullets.remove(this);
		}
	}
	
	// 随机
	Random random = new Random();
	
	// 子弹打一个敌机
	public boolean hitMonster(Plane plane) {
		Boom boom = new Boom(plane.x, plane.y,gc);;
		if(this.getRec().intersects(plane.getRec())&&this.isGood!=plane.isGood) {
			if(plane.isGood) {
				singlePlay.play("com/xiang/sound/music03.mp3");
				plane.blood -= 10;
				if(plane.blood == 0) {
					singlePlay.play("com/xiang/sound/music04.mp3");
					// 销毁自身
					gc.planes.remove(plane);
					
					
				}
				// 移除子弹
				gc.bullets.remove(this);
			}
			else {
				singlePlay.play("com/xiang/sound/music03.mp3");
				if(plane instanceof Boss) {
					
					plane.blood -= 10;
					
					// 移除子弹
					gc.bullets.remove(this);
					if(plane.blood <= 0) {
						gc.bosss.remove(plane);
						// 移除子弹
						gc.bullets.remove(this);
					}
				}
				else {
					// 移除打中敌人
					gc.enemys.remove(plane);
					// 移除子弹
					gc.bullets.remove(this);

					// 随机生成一个道具
					if(random.nextInt(500)>0) {
						Prop prop = new Prop(plane.x+plane.img.getWidth(null)/2, plane.y, "prop/prop.png");
						gc.props.add(prop);
					}
				}
			}
			// 添加爆炸
			gc.booms.add(boom);
			return true;
		}
		return false;
	}
	
	// 子弹打多个敌机
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
	
	// 获取子弹的矩形
	public Rectangle getRec() {
		return new Rectangle(x, y, this.img.getWidth(null), this.img.getHeight(null));
	}
	
}
