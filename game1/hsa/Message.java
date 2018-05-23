/*   1:    */ package game1.hsa;
/*   2:    */ 
/*   3:    */ import java.awt.Button;
/*   4:    */ import java.awt.Color;
/*   5:    */ import java.awt.Dimension;
/*   6:    */ import java.awt.FlowLayout;
/*   7:    */ import java.awt.Frame;
/*   8:    */ import java.awt.Label;
/*   9:    */ import java.awt.Panel;
/*  10:    */ import java.awt.Toolkit;
/*  11:    */ 
/*  12:    */ public class Message
/*  13:    */   extends CloseableDialog
/*  14:    */ {
/*  15: 21 */   private static int MINIMUM_WIDTH = 200;
/*  16:    */   private Button okButton;
/*  17:    */   
/*  18:    */   public Message(String message)
/*  19:    */   {
/*  20: 33 */     this(message, "", null);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public Message(String message, Frame frame)
/*  24:    */   {
/*  25: 45 */     this(message, "", frame);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Message(String message, String title)
/*  29:    */   {
/*  30: 57 */     this(message, title, null);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Message(String message, String title, Frame frame)
/*  34:    */   {
/*  35: 71 */     super(frame, title);
/*  36:    */     
/*  37: 73 */     setBackground(Color.lightGray);
/*  38:    */     
/*  39:    */ 
/*  40: 76 */     add("Center", new Label("  " + message + "  ", 1));
/*  41:    */     
/*  42:    */ 
/*  43: 79 */     this.okButton = new Button("OK");
/*  44: 80 */     this.okButton.addActionListener(this);
/*  45: 81 */     Panel p = new Panel();
/*  46: 82 */     p.setLayout(new FlowLayout(1, 0, 0));
/*  47: 83 */     p.add(this.okButton);
/*  48: 84 */     add("South", p);
/*  49:    */     
/*  50: 86 */     pack();
/*  51:    */     
/*  52: 88 */     positionDialog(frame);
/*  53:    */     
/*  54: 90 */     beep();
/*  55:    */     
/*  56: 92 */     show();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static void beep()
/*  60:    */   {
/*  61:101 */     Toolkit.getDefaultToolkit().beep();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Dimension getPreferredSize()
/*  65:    */   {
/*  66:110 */     Dimension d = super.getPreferredSize();
/*  67:111 */     d.width = Math.max(MINIMUM_WIDTH, d.width);
/*  68:112 */     return d;
/*  69:    */   }
/*  70:    */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.Message
 * JD-Core Version:    0.7.0.1
 */