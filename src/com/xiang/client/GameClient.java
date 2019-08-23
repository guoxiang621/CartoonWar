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
* @Description: 游戏客户端
* @author guoxiang
* @date 2019年8月17日 
*
*/
public class GameClient extends Frame {

	// 创建一个plane出来
//	Plane plane = new Plane(100, 200, "plane/001.png",this,true);
	
	// 创建一个我方集合
	public List<Plane> planes = new ArrayList<Plane>();
	
	// 创建道具集合
	public List<Prop> props = new ArrayList<Prop>();
	
	// 创建一个子弹的集合
	public List<Bullet> bullets = new ArrayList<Bullet>();
	
	// 创建敌方集合
	public List<Plane> enemys = new ArrayList<Plane>();
	
	// 创建一个背景图出来
	BackGround backImg = new BackGround(0, 0, "background.png");
	
	// 创建游戏结束界面
	GameOver gameOver = new GameOver(Constant.GAME_WIDTH/4+40, Constant.GAME_HEIGHT/4+40, "gameover.png");
	
	// 创建一个爆炸集合
	public List<Boom> booms = new ArrayList<Boom>();
	
	// 创建一个boss集合
	public List<Plane> bosss = new ArrayList<Plane>();
	
	// 解决图片闪烁问题
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
	// 生成客户端窗口
	public void launchFrame() {
		soundPlayer = new SoundPlayer("com/xiang/sound/music01.mp3");
		soundPlayer.start();
		
		
		// 设置窗口大小
		this.setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		// 标题
		this.setTitle("卡通大作战");
		// 是否能够显示
		this.setVisible(true);
		// 禁止最大化
		this.setResizable(false);
		// 窗口居中
		this.setLocationRelativeTo(null);
		// 关闭窗口   添加关闭监听器
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		plane = new Plane(100, 200, "plane/001.png",this,true);
		// 往我方容器添加自己
		planes.add(plane);
		
		// 添加鼠标监听
		//this.addMouseListener(new MouseAdapter() {
		//	@Override
		//	public void mouseClicked(MouseEvent e) {
		//		System.out.println("你点了一下鼠标");
		//	}
		//});
		// 添加键盘监听
		this.addKeyListener(new KeyAdapter() {
			// 键盘摁下的情况下
			@Override
			public void keyPressed(KeyEvent e) {
				plane.keyPressed(e);
			}
			@Override
			public void keyReleased(KeyEvent e) {
				plane.keyReleased(e);
			}
		});
		
		// 启动线程
		new paintThread().start();
		
		// 创建敌机
		EnemyPlane enemyPlane1 = new EnemyPlane(Constant.GAME_WIDTH, 50, "enemy/enemy01.png",this,false);
		EnemyPlane enemyPlane2 = new EnemyPlane(Constant.GAME_WIDTH, 350, "enemy/enemy02.png",this,false);
		EnemyPlane enemyPlane3 = new EnemyPlane(Constant.GAME_WIDTH, 600, "enemy/enemy03.png",this,false);
		EnemyPlane enemyPlane4 = new EnemyPlane(Constant.GAME_WIDTH+100, 600, "enemy/enemy03.png",this,false);
		EnemyPlane enemyPlane5 = new EnemyPlane(Constant.GAME_WIDTH+100, 350, "enemy/enemy02.png",this,false);
		EnemyPlane enemyPlane6 = new EnemyPlane(Constant.GAME_WIDTH+100, 50, "enemy/enemy01.png",this,false);
		EnemyPlane enemyPlane7 = new EnemyPlane(Constant.GAME_WIDTH+100, 500, "enemy/enemy02.png",this,false);
		
		// 往敌方容器中添加敌人
		enemys.add(enemyPlane1);
		enemys.add(enemyPlane2);
		enemys.add(enemyPlane3);
		enemys.add(enemyPlane4);
		enemys.add(enemyPlane5);
		enemys.add(enemyPlane6);
		enemys.add(enemyPlane7);
		
		// 添加boss
		bosss.add(new Boss(Constant.GAME_WIDTH, 400, this,"boss/boss.png",false));
	}
	// 重写paint方法
	@Override
	public void paint(Graphics g) {
		backImg.draw(g);
//		g.drawLine(1000, 0, 1000, 800);
		
		
		// 循环画出每个子弹
		for(int i=0;i<bullets.size();i++) {
			Bullet bullet = bullets.get(i);
			bullet.draw(g);
			bullet.hitMonsters(enemys);
			bullet.hitMonsters(planes);
			bullet.hitMonsters(bosss);
		}
		// 增强for循环中不能做任何操作
//		for(Bullet bullet:bullets) {
//			bullet.draw(g);
//		}
		
		g.drawString("当前子弹的数量:"+bullets.size(), 10, 40);
		g.drawString("当前敌机的数量:"+enemys.size(), 10, 60);
		g.drawString("当前爆炸的数量:"+booms.size(), 10, 80);
		g.drawString("当前我方的数量:"+planes.size(), 10, 100);
		g.drawString("当前道具的数量:"+props.size(), 10, 120);
		
		for(int i=0;i<planes.size();i++) {
			Plane plane2 = planes.get(i);
			plane2.draw(g);
			plane2.containItems(props);
		}
		
		g.setColor(Color.BLACK);
		// 循环画敌方
		for(int i=0;i<enemys.size();i++) {
			enemys.get(i).draw(g);
		}
		
		// 循环爆炸
		for(int i=0;i<booms.size();i++) {
			booms.get(i).draw(g);
		}
		
		// 循环道具
		for(int i=0;i<props.size();i++) {
			props.get(i).draw(g);
		}

		if(enemys.size() == 0) {
			// 循环boss
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
	
	// 内部类
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
