/*     */ package game1.hsa;
/*     */ 
/*     */ import java.awt.Canvas;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Rectangle;
/*     */ 
/*     */ class Animator$WindowCanvas
/*     */   extends Canvas
/*     */   implements DrawGraphics
/*     */ {
/*     */   SavePrint savePrint;
/*     */   int xSize;
/*     */   int ySize;
/*     */   Image offscreen;
/*     */   
/*     */   public Animator$WindowCanvas(Animator paramAnimator, int xSize, int ySize)
/*     */   {
/* 461 */     this.xSize = xSize;
/* 462 */     this.ySize = ySize;
/* 463 */     setSize(xSize, ySize);
/*     */     
/* 465 */     this.savePrint = new SavePrint(this, this, xSize, ySize);
/*     */   }
/*     */   
/*     */   public void addNotify()
/*     */   {
/* 471 */     super.addNotify();
/* 472 */     this.offscreen = createImage(this.xSize, this.ySize);
/*     */   }
/*     */   
/*     */   public void paint(Graphics g)
/*     */   {
/* 478 */     update(g);
/*     */   }
/*     */   
/*     */   public void update(Graphics g)
/*     */   {
/* 484 */     Rectangle r = g.getClipBounds();
/* 485 */     g.drawImage(this.offscreen, r.x, r.y, r.x + r.width, 
/* 486 */       r.y + r.width, r.x, r.y, r.x + r.width, 
/* 487 */       r.y + r.width, null, null);
/*     */   }
/*     */   
/*     */   public void drawWindowToGraphics(Graphics g)
/*     */   {
/* 495 */     g.drawImage(this.offscreen, 0, 0, null);
/*     */   }
/*     */   
/*     */   public void drawWindowToGraphics(Graphics g, int width, int height)
/*     */   {
/* 501 */     g.drawImage(this.offscreen, 0, 0, null);
/*     */   }
/*     */   
/*     */   public int getMargin()
/*     */   {
/* 507 */     return 0;
/*     */   }
/*     */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.Animator.WindowCanvas
 * JD-Core Version:    0.7.0.1
 */