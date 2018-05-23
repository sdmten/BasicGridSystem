/*  1:   */ package game1.lib;
/*  2:   */ 
/*  3:   */ import java.awt.Button;
/*  4:   */ import java.awt.Color;
/*  5:   */ import java.awt.FlowLayout;
/*  6:   */ import java.awt.Frame;
/*  7:   */ import java.awt.Label;
/*  8:   */ import java.awt.Panel;
/*  9:   */ 
/* 10:   */ public class Congratulations
/* 11:   */   extends CloseableDialog
/* 12:   */ {
/* 13:   */   private Button quitButton;
/* 14:   */   
/* 15:   */   public Congratulations(String message)
/* 16:   */   {
/* 17:10 */     this(message, null);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public Congratulations(String message, Frame frame)
/* 21:   */   {
/* 22:15 */     super(frame, "Congratulations!");
/* 23:   */     
/* 24:17 */     setBackground(Color.lightGray);
/* 25:   */     
/* 26:19 */     add("Center", new Label("   " + message + "   ", 1));
/* 27:   */     
/* 28:21 */     this.quitButton = new Button("Quit");
/* 29:22 */     this.quitButton.addActionListener(this);
/* 30:23 */     Panel panel = new Panel();
/* 31:24 */     panel.setLayout(new FlowLayout(1, 0, 0));
/* 32:25 */     panel.add(this.quitButton);
/* 33:26 */     add("South", panel);
/* 34:27 */     pack();
/* 35:28 */     positionDialog(frame);
/* 36:29 */     ErrorMessage.beep();
/* 37:   */     
/* 38:31 */     show();
/* 39:   */     
/* 40:33 */     System.exit(0);
/* 41:   */   }
/* 42:   */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.lib.Congratulations
 * JD-Core Version:    0.7.0.1
 */