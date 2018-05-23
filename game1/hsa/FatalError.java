/*  1:   */ package game1.hsa;
/*  2:   */ 
/*  3:   */ import java.awt.Button;
/*  4:   */ import java.awt.Color;
/*  5:   */ import java.awt.FlowLayout;
/*  6:   */ import java.awt.Frame;
/*  7:   */ import java.awt.Label;
/*  8:   */ import java.awt.Panel;
/*  9:   */ 
/* 10:   */ public class FatalError
/* 11:   */   extends CloseableDialog
/* 12:   */ {
/* 13:   */   private Button quitButton;
/* 14:   */   
/* 15:   */   public FatalError(String message)
/* 16:   */   {
/* 17:27 */     this(message, null);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public FatalError(String message, Frame frame)
/* 21:   */   {
/* 22:38 */     super(frame, "Fatal Error");
/* 23:   */     
/* 24:40 */     setBackground(Color.lightGray);
/* 25:   */     
/* 26:   */ 
/* 27:43 */     add("Center", new Label("  " + message + "  ", 1));
/* 28:   */     
/* 29:   */ 
/* 30:46 */     this.quitButton = new Button("Quit");
/* 31:47 */     this.quitButton.addActionListener(this);
/* 32:48 */     Panel p = new Panel();
/* 33:49 */     p.setLayout(new FlowLayout(1, 0, 0));
/* 34:50 */     p.add(this.quitButton);
/* 35:51 */     add("South", p);
/* 36:   */     
/* 37:53 */     pack();
/* 38:   */     
/* 39:55 */     positionDialog(frame);
/* 40:   */     
/* 41:57 */     Message.beep();
/* 42:   */     
/* 43:59 */     show();
/* 44:   */     
/* 45:61 */     System.exit(0);
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.FatalError
 * JD-Core Version:    0.7.0.1
 */