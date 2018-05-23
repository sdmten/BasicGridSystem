/*   1:    */ package game1.hsa;
/*   2:    */ 
/*   3:    */ import java.awt.Canvas;
/*   4:    */ import java.awt.Color;
/*   5:    */ import java.awt.Dimension;
/*   6:    */ import java.awt.Graphics;
/*   7:    */ 
/*   8:    */ class ConsoleEdges
/*   9:    */   extends Canvas
/*  10:    */ {
/*  11:    */   protected static final int TOP = 1;
/*  12:    */   protected static final int BOTTOM = 2;
/*  13:    */   protected static final int LEFT = 3;
/*  14:    */   protected static final int RIGHT = 4;
/*  15:    */   private int edge;
/*  16:    */   
/*  17:    */   public ConsoleEdges(int edge)
/*  18:    */   {
/*  19: 36 */     this.edge = edge;
/*  20: 38 */     switch (edge)
/*  21:    */     {
/*  22:    */     case 1: 
/*  23:    */     case 2: 
/*  24: 42 */       setBackground(Color.lightGray);
/*  25: 43 */       setSize(100, 8);
/*  26: 44 */       break;
/*  27:    */     case 3: 
/*  28:    */     case 4: 
/*  29: 47 */       setBackground(Color.lightGray);
/*  30: 48 */       setSize(8, 100);
/*  31:    */     }
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void paint(Graphics g)
/*  35:    */   {
/*  36: 59 */     int gm = 5;
/*  37: 60 */     int depth = 3;
/*  38: 62 */     switch (this.edge)
/*  39:    */     {
/*  40:    */     case 1: 
/*  41: 65 */       for (int cnt = 1; cnt < depth; cnt++)
/*  42:    */       {
/*  43: 67 */         g.setColor(Color.gray);
/*  44: 68 */         g.drawLine(gm + cnt - 1, gm + cnt - 1, getSize().width - gm - cnt - 1, gm + cnt - 1);
/*  45: 69 */         g.drawLine(gm + cnt - 1, gm + cnt - 1, gm + cnt - 1, getSize().height);
/*  46: 70 */         g.setColor(Color.white);
/*  47: 71 */         g.drawLine(getSize().width - gm - depth + cnt, gm + depth - cnt - 1, getSize().width - gm - depth + cnt, getSize().height);
/*  48:    */       }
/*  49: 74 */       g.setColor(Color.black);
/*  50: 75 */       g.drawLine(gm + depth - 1, gm + depth - 1, getSize().width - gm - depth - 1, gm + depth - 1);
/*  51: 76 */       g.setColor(Color.lightGray);
/*  52: 77 */       g.drawLine(getSize().width - gm - depth, gm + depth - 1, getSize().width - gm - depth, gm + depth - 1);
/*  53: 78 */       break;
/*  54:    */     case 2: 
/*  55: 80 */       g.setColor(Color.lightGray);
/*  56: 81 */       g.drawLine(gm + depth - 1, 0, getSize().width, 0);
/*  57: 82 */       g.setColor(Color.white);
/*  58: 83 */       g.drawLine(getSize().width - gm - depth + 1, 0, getSize().width - gm - 1, 0);
/*  59: 85 */       for (int cnt = 1; cnt < depth; cnt++)
/*  60:    */       {
/*  61: 87 */         g.setColor(Color.white);
/*  62: 88 */         g.drawLine(gm + cnt, depth - cnt, getSize().width - gm - 1, depth - cnt);
/*  63: 89 */         g.setColor(Color.gray);
/*  64: 90 */         g.drawLine(gm + cnt - 1, depth - cnt, gm + cnt - 1, 0);
/*  65:    */       }
/*  66: 92 */       break;
/*  67:    */     case 3: 
/*  68: 94 */       g.setColor(Color.gray);
/*  69: 95 */       for (int cnt = 1; cnt < depth; cnt++) {
/*  70: 97 */         g.drawLine(gm + cnt - 1, 0, gm + cnt - 1, getSize().height);
/*  71:    */       }
/*  72:100 */       g.setColor(Color.black);
/*  73:101 */       g.drawLine(gm + depth - 1, 0, gm + depth - 1, getSize().height);
/*  74:102 */       break;
/*  75:    */     case 4: 
/*  76:104 */       g.setColor(Color.lightGray);
/*  77:105 */       g.drawLine(0, 0, 0, getSize().height);
/*  78:    */       
/*  79:107 */       g.setColor(Color.white);
/*  80:108 */       for (int cnt = 1; cnt < depth; cnt++) {
/*  81:110 */         g.drawLine(cnt, 0, cnt, getSize().height);
/*  82:    */       }
/*  83:    */     }
/*  84:    */   }
/*  85:    */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.ConsoleEdges
 * JD-Core Version:    0.7.0.1
 */