/*  1:   */ package game1.lib;
/*  2:   */ 
/*  3:   */ import java.awt.Button;
/*  4:   */ import java.awt.Color;
/*  5:   */ import java.awt.FlowLayout;
/*  6:   */ import java.awt.Frame;
/*  7:   */ import java.awt.Label;
/*  8:   */ import java.awt.Panel;
/*  9:   */ import java.awt.Toolkit;
/* 10:   */ 
/* 11:   */ public class ErrorMessage
/* 12:   */   extends CloseableDialog
/* 13:   */ {
/* 14:   */   private Button quitButton;
/* 15:   */   
/* 16:   */   public ErrorMessage(String message)
/* 17:   */   {
/* 18:11 */     this(message, null);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public ErrorMessage(String message, Frame frame)
/* 22:   */   {
/* 23:17 */     super(frame, "ERROR!");
/* 24:   */     
/* 25:19 */     setBackground(Color.lightGray);
/* 26:   */     
/* 27:21 */     add("Center", new Label("  " + message + "  ", 1));
/* 28:   */     
/* 29:23 */     this.quitButton = new Button("Quit");
/* 30:24 */     this.quitButton.addActionListener(this);
/* 31:25 */     Panel panel = new Panel();
/* 32:26 */     panel.setLayout(new FlowLayout(1, 0, 0));
/* 33:27 */     panel.add(this.quitButton);
/* 34:28 */     add("South", panel);
/* 35:   */     
/* 36:30 */     pack();
/* 37:   */     
/* 38:32 */     positionDialog(frame);
/* 39:   */     
/* 40:34 */     beep();
/* 41:   */     
/* 42:36 */     show();
/* 43:   */     
/* 44:38 */     System.exit(0);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public static void beep()
/* 48:   */   {
/* 49:44 */     Toolkit.getDefaultToolkit().beep();
/* 50:   */   }
/* 51:   */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.lib.ErrorMessage
 * JD-Core Version:    0.7.0.1
 */