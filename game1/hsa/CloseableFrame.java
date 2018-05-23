/*  1:   */ package game1.hsa;
/*  2:   */ 
/*  3:   */ import java.awt.Dimension;
/*  4:   */ import java.awt.Frame;
/*  5:   */ import java.awt.Point;
/*  6:   */ import java.awt.Toolkit;
/*  7:   */ import java.awt.Window;
/*  8:   */ import java.awt.event.ActionEvent;
/*  9:   */ import java.awt.event.ActionListener;
/* 10:   */ import java.awt.event.WindowEvent;
/* 11:   */ import java.awt.event.WindowListener;
/* 12:   */ 
/* 13:   */ class CloseableFrame
/* 14:   */   extends Frame
/* 15:   */   implements ActionListener, WindowListener
/* 16:   */ {
/* 17:   */   public CloseableFrame(String title)
/* 18:   */   {
/* 19:28 */     super(title);
/* 20:29 */     setResizable(false);
/* 21:30 */     addWindowListener(this);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void actionPerformed(ActionEvent e)
/* 25:   */   {
/* 26:39 */     dispose();
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void positionDialog(Window window)
/* 30:   */   {
/* 31:49 */     Dimension dlgSize = getSize();
/* 32:   */     Point loc;
/* 33:   */     Point loc;
/* 34:51 */     if (window == null)
/* 35:   */     {
/* 36:54 */       Dimension screenSize = getToolkit().getScreenSize();
/* 37:55 */       loc = new Point(
/* 38:56 */         (screenSize.width - dlgSize.width) / 2, 
/* 39:57 */         (screenSize.height - dlgSize.height) / 3);
/* 40:   */     }
/* 41:   */     else
/* 42:   */     {
/* 43:61 */       Dimension windowSize = window.getSize();
/* 44:62 */       Point windowLoc = window.getLocation();
/* 45:63 */       loc = new Point(
/* 46:64 */         windowLoc.x + (windowSize.width - dlgSize.width) / 2, 
/* 47:65 */         windowLoc.y + (windowSize.height - dlgSize.height) / 3);
/* 48:   */     }
/* 49:67 */     setLocation(loc);
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void windowActivated(WindowEvent e) {}
/* 53:   */   
/* 54:   */   public void windowClosed(WindowEvent e) {}
/* 55:   */   
/* 56:   */   public void windowClosing(WindowEvent e)
/* 57:   */   {
/* 58:96 */     dispose();
/* 59:   */   }
/* 60:   */   
/* 61:   */   public void windowDeactivated(WindowEvent e) {}
/* 62:   */   
/* 63:   */   public void windowDeiconified(WindowEvent e) {}
/* 64:   */   
/* 65:   */   public void windowIconified(WindowEvent e) {}
/* 66:   */   
/* 67:   */   public void windowOpened(WindowEvent e) {}
/* 68:   */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.CloseableFrame
 * JD-Core Version:    0.7.0.1
 */