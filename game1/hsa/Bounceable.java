/*  1:   */ package game1.hsa;
/*  2:   */ 
/*  3:   */ import java.awt.Color;
/*  4:   */ import java.awt.Graphics;
/*  5:   */ 
/*  6:   */ public abstract class Bounceable
/*  7:   */ {
/*  8:   */   private double x;
/*  9:   */   private double y;
/* 10:   */   private double direction;
/* 11:   */   private double speed;
/* 12:   */   private Color color;
/* 13:   */   
/* 14:   */   public Bounceable(double newX, double newY, double newDirection, double newSpeed)
/* 15:   */   {
/* 16:16 */     this.x = newX;
/* 17:17 */     this.y = newY;
/* 18:18 */     this.direction = newDirection;
/* 19:19 */     this.speed = newSpeed;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public double getX()
/* 23:   */   {
/* 24:25 */     return this.x;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public double getY()
/* 28:   */   {
/* 29:31 */     return this.y;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void setLocation(double newX, double newY)
/* 33:   */   {
/* 34:37 */     this.x = newX;
/* 35:38 */     this.y = newY;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public double getDirection()
/* 39:   */   {
/* 40:44 */     return this.direction;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void setDirection(double newDirection)
/* 44:   */   {
/* 45:50 */     this.direction = newDirection;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public double getSpeed()
/* 49:   */   {
/* 50:56 */     return this.speed;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public void setSpeed(double newSpeed)
/* 54:   */   {
/* 55:62 */     this.speed = newSpeed;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public Color getColor()
/* 59:   */   {
/* 60:68 */     return this.color;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public void setColor(Color newColor)
/* 64:   */   {
/* 65:74 */     this.color = newColor;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public abstract void paint(Graphics paramGraphics, int paramInt1, int paramInt2);
/* 69:   */   
/* 70:   */   public abstract double getTop();
/* 71:   */   
/* 72:   */   public abstract double getBottom();
/* 73:   */   
/* 74:   */   public abstract double getLeft();
/* 75:   */   
/* 76:   */   public abstract double getRight();
/* 77:   */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.Bounceable
 * JD-Core Version:    0.7.0.1
 */