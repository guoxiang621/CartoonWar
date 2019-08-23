package com.xiang.client;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import com.xiang.constant.Constant;
import com.xiang.entity.BackGround;
import com.xiang.entity.Boom;
import com.xiang.entity.Boss;
import com.xiang.entity.Bullet;
import com.xiang.entity.EnemyPlane;
import com.xiang.entity.GameOver;
import com.xiang.entity.Plane;
import com.xiang.entity.Prop;
import com.xiang.util.GetImageUtil;
import com.xiang.util.SoundPlayer;

/**
* @ClassName: GameClient
* @Description: ��Ϸ�ͻ���
* @author guoxiang
* @date 2019��8��17�� 
*
*/
public class GameClient extends Frame {

	// ����һ��plane����
//	Plane plane = new Plane(100, 200, "plane/001.png",this,true);
	
	// ����һ���ҷ�����
	public List<Plane> planes = new ArrayList<Plane>();
	
	// �������߼���
	public List<Prop> props = new ArrayList<Prop>();
	
	// ����һ���ӵ��ļ���
	public List<Bullet> bullets = new ArrayList<Bullet>();
	
	// �����з�����
	public List<Plane> enemys = new ArrayList<Plane>();
	
	// ����һ������ͼ����
	BackGround backImg = new BackGround(0, 0, "background.png");
	
	// ������Ϸ��������
	GameOver gameOver = new GameOver(Constant.GAME_WIDTH/4+40, Constant.GAME_HEIGHT/4+40, "gameover.png");
	
	// ����һ����ը����
	public List<Boom> booms = new ArrayList<Boom>();
	
	// ����һ��boss����
	public List<Plane> bosss = new ArrayList<Plane>();
	
	// ���ͼƬ��˸����
	@Override
	public void update(Graphics g) {
		Image backImg = createImage(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		Graphics backg = backImg.getGraphics();
		Color color = backg.getColor();
		backg.setColor(Color.WHITE);
		backg.fillRect(0, 0, Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		backg.setColor(color);
		paint(backg);
		g.drawImage(backImg, 0, 0, null);
	}
	
	Plane plane = null;
	SoundPlayer soundPlayer = null;		
	// ���ɿͻ��˴���
	public void launchFrame() {
		soundPlayer = new SoundPlayer("com/xiang/sound/music01.mp3");
		soundPlayer.start();
		
		
		// ���ô��ڴ�С
		this.setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		// ����
		this.setTitle("��ͨ����ս");
		// �Ƿ��ܹ���ʾ
		this.setVisible(true);
		// ��ֹ���
		this.setResizable(false);
		// ���ھ���
		this.setLocationRelativeTo(null);
		// �رմ���   ��ӹرռ�����
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		plane = new Plane(100, 200, "plane/001.png",this,true);
		// ���ҷ���������Լ�
		planes.add(plane);
		
		// ���������
		//this.addMouseListener(new MouseAdapter() {
		//	@Override
		//	public void mouseClicked(MouseEvent e) {
		//		System.out.println("�����һ�����");
		//	}
		//});
		// ��Ӽ��̼���
		this.addKeyListener(new KeyAdapter() {
			// �������µ������
			@Override
			public void keyPressed(KeyEvent e) {
				plane.keyPressed(e);
			}
			@Override
			public void keyReleased(KeyEvent e) {
				plane.keyReleased(e);
			}
		});
		
		// �����߳�
		new paintThread().start();
		
		// �����л�
		EnemyPlane enemyPlane1 = new EnemyPlane(Constant.GAME_WIDTH, 50, "enemy/enemy01.png",this,false);
		EnemyPlane enemyPlane2 = new EnemyPlane(Constant.GAME_WIDTH, 350, "enemy/enemy02.png",this,false);
		EnemyPlane enemyPlane3 = new EnemyPlane(Constant.GAME_WIDTH, 600, "enemy/enemy03.png",this,false);
		EnemyPlane enemyPlane4 = new EnemyPlane(Constant.GAME_WIDTH+100, 600, "enemy/enemy03.png",this,false);
		EnemyPlane enemyPlane5 = new EnemyPlane(Constant.GAME_WIDTH+100, 350, "enemy/enemy02.png",this,false);
		EnemyPlane enemyPlane6 = new EnemyPlane(Constant.GAME_WIDTH+100, 50, "enemy/enemy01.png",this,false);
		EnemyPlane enemyPlane7 = new EnemyPlane(Constant.GAME_WIDTH+100, 500, "enemy/enemy02.png",this,false);
		
		// ���з���������ӵ���
		enemys.add(enemyPlane1);
		enemys.add(enemyPlane2);
		enemys.add(enemyPlane3);
		enemys.add(enemyPlane4);
		enemys.add(enemyPlane5);
		enemys.add(enemyPlane6);
		enemys.add(enemyPlane7);
		
		// ���boss
		bosss.add(new Boss(Constant.GAME_WIDTH, 400, this,"boss/boss.png",false));
	}
	// ��дpaint����
	@Override
	public void paint(Graphics g) {
		backImg.draw(g);
//		g.drawLine(1000, 0, 1000, 800);
		
		
		// ѭ������ÿ���ӵ�
		for(int i=0;i<bullets.size();i++) {
			Bullet bullet = bullets.get(i);
			bullet.draw(g);
			bullet.hitMonsters(enemys);
			bullet.hitMonsters(planes);
			bullet.hitMonsters(bosss);
		}
		// ��ǿforѭ���в������κβ���
//		for(Bullet bullet:bullets) {
//			bullet.draw(g);
//		}
		
		g.drawString("��ǰ�ӵ�������:"+bullets.size(), 10, 40);
		g.drawString("��ǰ�л�������:"+enemys.size(), 10, 60);
		g.drawString("��ǰ��ը������:"+booms.size(), 10, 80);
		g.drawString("��ǰ�ҷ�������:"+planes.size(), 10, 100);
		g.drawString("��ǰ���ߵ�����:"+props.size(), 10, 120);
		
		for(int i=0;i<planes.size();i++) {
			Plane plane2 = planes.get(i);
			plane2.draw(g);
			plane2.containItems(props);
		}
		
		g.setColor(Color.BLACK);
		// ѭ�����з�
		for(int i=0;i<enemys.size();i++) {
			enemys.get(i).draw(g);
		}
		
		// ѭ����ը
		for(int i=0;i<booms.size();i++) {
			booms.get(i).draw(g);
		}
		
		// ѭ������
		for(int i=0;i<props.size();i++) {
			props.get(i).draw(g);
		}

		if(enemys.size() == 0) {
			// ѭ��boss
			for(int i=0;i<bosss.size();i++) {
				bosss.get(i).draw(g);
			}
		}
		
		if(planes.size() == 0) {
			gameOver.draw(g);
			
		}
//		if(planes.size() == 0) {
//			soundPlayer.stop();
//			new SoundPlayer("com/xiang/sound/gameover.mp3").start();
//		}
		
	}
	
	// �ڲ���
	class paintThread extends Thread{
		@Override
		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
