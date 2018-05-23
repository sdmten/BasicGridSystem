/*   1:    */ package game1.lib;
/*   2:    */ 
/*   3:    */ import java.awt.Dialog;
/*   4:    */ import java.awt.Dimension;
/*   5:    */ import java.awt.Frame;
/*   6:    */ import java.awt.Point;
/*   7:    */ import java.awt.Toolkit;
/*   8:    */ import java.awt.Window;
/*   9:    */ import java.awt.event.ActionEvent;
/*  10:    */ import java.awt.event.ActionListener;
/*  11:    */ import java.awt.event.WindowEvent;
/*  12:    */ import java.awt.event.WindowListener;
/*  13:    */ 
/*  14:    */ class CloseableDialog
/*  15:    */   extends Dialog
/*  16:    */   implements ActionListener, WindowListener
/*  17:    */ {
/*  18:    */   public CloseableDialog(Frame parent, String title)
/*  19:    */   {
/*  20: 31 */     super(parent == null ? new Frame() : parent, title, true);
/*  21: 32 */     setResizable(false);
/*  22: 33 */     addWindowListener(this);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void actionPerformed(ActionEvent e)
/*  26:    */   {
/*  27: 44 */     dispose();
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void positionDialog(Window window)
/*  31:    */   {
/*  32: 56 */     Dimension dlgSize = getSize();
/*  33:    */     Point loc;
/*  34:    */     Point loc;
/*  35: 58 */     if (window == null)
/*  36:    */     {
/*  37: 61 */       Dimension screenSize = getToolkit().getScreenSize();
/*  38: 62 */       loc = new Point(
/*  39: 63 */         (screenSize.width - dlgSize.width) / 2, 
/*  40: 64 */         (screenSize.height - dlgSize.height) / 3);
/*  41:    */     }
/*  42:    */     else
/*  43:    */     {
/*  44: 68 */       Dimension windowSize = window.getSize();
/*  45: 69 */       Point windowLoc = window.getLocation();
/*  46: 70 */       loc = new Point(
/*  47: 71 */         windowLoc.x + (windowSize.width - dlgSize.width) / 2, 
/*  48: 72 */         windowLoc.y + (windowSize.height - dlgSize.height) / 3);
/*  49:    */     }
/*  50: 74 */     setLocation(loc);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void windowActivated(WindowEvent e) {}
/*  54:    */   
/*  55:    */   public void windowClosed(WindowEvent e) {}
/*  56:    */   
/*  57:    */   public void windowClosing(WindowEvent e)
/*  58:    */   {
/*  59:109 */     dispose();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void windowDeactivated(WindowEvent e) {}
/*  63:    */   
/*  64:    */   public void windowDeiconified(WindowEvent e) {}
/*  65:    */   
/*  66:    */   public void windowIconified(WindowEvent e) {}
/*  67:    */   
/*  68:    */   public void windowOpened(WindowEvent e) {}
/*  69:    */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.lib.CloseableDialog
 * JD-Core Version:    0.7.0.1
 */