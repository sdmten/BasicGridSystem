/*   1:    */ package game1.hsa;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Dimension;
/*   5:    */ import java.awt.FlowLayout;
/*   6:    */ import java.awt.FontMetrics;
/*   7:    */ import java.awt.Frame;
/*   8:    */ import java.awt.Label;
/*   9:    */ import java.awt.Point;
/*  10:    */ import java.awt.Toolkit;
/*  11:    */ import java.awt.event.WindowEvent;
/*  12:    */ 
/*  13:    */ public class Status
/*  14:    */   extends CloseableFrame
/*  15:    */ {
/*  16: 19 */   private static int MINIMUM_WIDTH = 200;
/*  17:    */   Label messageLabel;
/*  18:    */   int messageWidth;
/*  19:    */   
/*  20:    */   public Status(String message)
/*  21:    */   {
/*  22: 30 */     this(message, "", null);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public Status(String message, Frame frame)
/*  26:    */   {
/*  27: 40 */     this(message, "", frame);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Status(String message, String title)
/*  31:    */   {
/*  32: 51 */     this(message, title, null);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Status(String message, String title, Frame frame)
/*  36:    */   {
/*  37: 63 */     super(title);
/*  38:    */     
/*  39: 65 */     setBackground(Color.lightGray);
/*  40:    */     
/*  41: 67 */     setLayout(new FlowLayout());
/*  42:    */     
/*  43:    */ 
/*  44: 70 */     this.messageLabel = new Label(message, 1);
/*  45: 71 */     add(this.messageLabel);
/*  46:    */     
/*  47: 73 */     pack();
/*  48: 74 */     this.messageWidth = 
/*  49: 75 */       getFontMetrics(this.messageLabel.getFont()).stringWidth(message);
/*  50:    */     
/*  51:    */ 
/*  52: 78 */     Dimension dlg = getSize();
/*  53:    */     Point loc;
/*  54:    */     Point loc;
/*  55: 80 */     if (frame == null)
/*  56:    */     {
/*  57: 83 */       Dimension screen = getToolkit().getScreenSize();
/*  58: 84 */       loc = new Point((screen.width - dlg.width) / 2, 
/*  59: 85 */         (screen.height - dlg.height) / 3);
/*  60:    */     }
/*  61:    */     else
/*  62:    */     {
/*  63: 90 */       Dimension window = frame.getSize();
/*  64: 91 */       Point windowLoc = frame.getLocation();
/*  65: 92 */       loc = new Point(windowLoc.x + (window.width - dlg.width) / 2, 
/*  66: 93 */         windowLoc.y + (window.height - dlg.height) / 3);
/*  67:    */     }
/*  68: 95 */     setLocation(loc);
/*  69:    */     
/*  70:    */ 
/*  71: 98 */     show();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Dimension getPreferredSize()
/*  75:    */   {
/*  76:102 */     Dimension d = super.getPreferredSize();
/*  77:103 */     d.width = Math.max(MINIMUM_WIDTH, d.width);
/*  78:104 */     return d;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setMessage(String newMessage)
/*  82:    */   {
/*  83:114 */     this.messageLabel.setText(newMessage);
/*  84:115 */     int newWidth = 
/*  85:116 */       getFontMetrics(this.messageLabel.getFont()).stringWidth(newMessage);
/*  86:117 */     if (newWidth > this.messageWidth)
/*  87:    */     {
/*  88:119 */       this.messageWidth = newWidth;
/*  89:120 */       this.messageLabel.invalidate();
/*  90:121 */       doLayout();
/*  91:122 */       pack();
/*  92:    */     }
/*  93:124 */     this.messageLabel.repaint();
/*  94:125 */     toFront();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void windowClosing(WindowEvent e)
/*  98:    */   {
/*  99:135 */     setVisible(false);
/* 100:    */   }
/* 101:    */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.Status
 * JD-Core Version:    0.7.0.1
 */