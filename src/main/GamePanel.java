package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
//  screen settings
  final int originalTileSize =16; //16x16 tile
  final int scale = 3;
  
  public final int tileSize = originalTileSize * scale; // 16*3 pixels
  public final int maxScreenCol = 16;
  public final int maxScreenRow = 12;
  public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
  public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
  
  // WORLD SETTINGS
  public final int maxWorldCol = 50;
  public final int maxWorldRow = 50;
  
  //FPS (Frame per second
  int FPS = 60;
  
  TileManager tileM = new TileManager(this);
  
  KeyHandler keyH = new KeyHandler();
  Sound music = new Sound();
  Sound se = new Sound();
  
  public CollisionChecker cChecker = new CollisionChecker(this);
  public AssetSetter aSetter = new AssetSetter(this);
  public UI ui = new UI(this);
  Thread gameThread;
  
  // ENTITY AND OBJECT
  public Player player = new Player(this,keyH);
  public SuperObject obj[] = new SuperObject[10];
  
  
  public GamePanel(){
      this.setPreferredSize(new Dimension(screenWidth, screenHeight));
      this.setBackground(Color.black);
      this.setDoubleBuffered(true);
      this.addKeyListener(keyH);
      this.setFocusable(true);
  }
  
  public void setupGame() {
	  
	  aSetter.setObject();
	  
	  playMusic(0);
  }
  
  public void startGameThread(){
      gameThread = new Thread(this);
      gameThread.start();
  }
  
  @Override
  public void run() {
      
      double drawInterval = 1000000000/FPS; // 1000000000 is 1 sec in terms of nanosec
          // this is giving me 0.1666 seconds
          
      double nextDrawTime = System.nanoTime() + drawInterval;
          // System.nanoTime here will give me current time
          
      while(gameThread != null){
          
//          1 UPDATE : update information such as character position
          update();  

//          2 DRAW : draw the screen with the updated information
          repaint(); 
          
          try {
              double remainingTime = nextDrawTime - System.nanoTime();
              // sleep takes take in mili sec so converting it
              remainingTime = remainingTime/1000000;
              
              if(remainingTime<0){
                  remainingTime = 0;
              }
              
              Thread.sleep((long)remainingTime);
              
              nextDrawTime += drawInterval;
          } 
          catch (InterruptedException e)
          {
              e.printStackTrace();
          }
      }
  }
  public void update() {
      player.update();
  }
  @Override
  public void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D)g;
      
      // TILE
      tileM.draw(g2);
      
      // OBJECT
      for(int i=0;i< obj.length;i++) {
    	  
    	  if(obj[i] != null) {
    		  obj[i].draw(g2, this);
    	  }
      }
      
      // PLAYER
      player.draw(g2);
      
      //UI
      ui.draw(g2);
      
      g2.dispose();
  }
  public void playMusic(int i) {
	  
	  music.setFile(i);
	  music.play();
	  music.loop();
  }
  
  public void stopMusic() {
	  
	  music.stop();
  }
  
  public void playSE(int i) {
	  
	  se.setFile(i);
	  se.play();
  }
}
