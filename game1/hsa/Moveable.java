/*  1:   */ package game1.hsa;
/*  2:   */ 
/*  3:   */ import java.awt.Color;
/*  4:   */ import java.awt.Graphics;
/*  5:   */ 
/*  6:   */ public class Moveable
/*  7:   */ {
/*  8:   */   public static final int DIAMETER = 40;
/*  9:   */   private double x;
/* 10:   */   private double y;
/* 11:   */   private double direction;
/* 12:   */   private double speed;
/* 13:   */   private Color color;
/* 14:   */   
/* 15:   */   public Moveable(double newX, double newY, double newDirection, double newSpeed)
/* 16:   */   {
/* 17:18 */     this.x = newX;
/* 18:19 */     this.y = newY;
/* 19:20 */     this.direction = newDirection;
/* 20:21 */     this.speed = newSpeed;
/* 21:22 */     this.color = Color.red;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public double getX()
/* 25:   */   {
/* 26:28 */     return this.x;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public double getY()
/* 30:   */   {
/* 31:34 */     return this.y;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void setLocation(double newX, double newY)
/* 35:   */   {
/* 36:40 */     this.x = newX;
/* 37:41 */     this.y = newY;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public double getDirection()
/* 41:   */   {
/* 42:47 */     return this.direction;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void setDirection(double newDirection)
/* 46:   */   {
/* 47:53 */     this.direction = newDirection;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public double getSpeed()
/* 51:   */   {
/* 52:59 */     return this.speed;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public void setSpeed(double newSpeed)
/* 56:   */   {
/* 57:65 */     this.speed = newSpeed;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public Color getColor()
/* 61:   */   {
/* 62:71 */     return this.color;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public void setColor(Color newColor)
/* 66:   */   {
/* 67:77 */     this.color = newColor;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public void paint(Graphics g, int xPos, int yPos)
/* 71:   */   {
/* 72:83 */     g.setColor(this.color);
/* 73:84 */     g.fillOval(xPos - 20, yPos - 20, 40, 40);
/* 74:   */   }
/* 75:   */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.Moveable
 * JD-Core Version:    0.7.0.1
 */