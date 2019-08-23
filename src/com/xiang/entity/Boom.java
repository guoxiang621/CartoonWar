package com.xiang.entity;

import java.awt.Graphics;
import java.awt.Image;

import com.xiang.action.ActionAble;
import com.xiang.client.GameClient;
import com.xiang.util.GetImageUtil;

/**
* @ClassName: Boom
* @Description: ��ը��
* @author guoxiang
* @date 2019��8��20�� 
*
*/
public class Boom extends GameObj implements ActionAble {

	private boolean isLive;

	private GameClient gc;
	
	// ��̬��ʼ����ը
	private static Image[] boomImgs = new Image[5];
	static
	{
		for(int i=0;i<5;i++) {
			boomImgs[i] = GetImageUtil.getImg("boom/"+(i+1)+".png");
		}
	}
	
	
	public Boom() {
		// TODO Auto-generated constructor stub
	}
	
	public Boom(int x,int y,GameClient gc) {
		this.x = x;
		this.y = y;
		this.isLive = true;
		this.gc = gc;
	}
	
	
	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}

	int count = 0;
	@Override
	public void draw(Graphics g) {
		if(isLive) {
			if(count > 4) {
				isLive = false;
				gc.booms.remove(this);
				return;
			}
		}
		g.drawImage(boomImgs[count++], x-20, y-20, null);
	}

	
}
