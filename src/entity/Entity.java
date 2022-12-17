// This stores variables that will be used in 
// player, monster and NPC classes.
package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
	public int worldX, worldY;
    public int speed;
    
    //We use this to store our image file
    public BufferedImage up1, up2, down1, down2, left1,left2, right1, right2;
    public String direction;
    
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
}
