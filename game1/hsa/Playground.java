/*   1:    */ package game1.hsa;
/*   2:    */ 
/*   3:    */ import java.awt.Button;
/*   4:    */ import java.awt.Color;
/*   5:    */ import java.awt.Container;
/*   6:    */ import java.awt.Dimension;
/*   7:    */ import java.awt.event.ActionEvent;
/*   8:    */ import java.awt.event.ActionListener;
/*   9:    */ import java.awt.event.KeyEvent;
/*  10:    */ import java.awt.event.KeyListener;
/*  11:    */ import javax.swing.JFrame;
/*  12:    */ import javax.swing.JLabel;
/*  13:    */ import javax.swing.JPanel;
/*  14:    */ import javax.swing.JTextArea;
/*  15:    */ import javax.swing.JTextField;
/*  16:    */ 
/*  17:    */ public class Playground
/*  18:    */   extends JPanel
/*  19:    */   implements ActionListener, KeyListener
/*  20:    */ {
/*  21:  9 */   static Button button = new Button();
/*  22:    */   static JFrame frame;
/*  23:    */   static JLabel label;
/*  24: 12 */   static JTextField field = new JTextField(20);
/*  25:    */   static JTextArea area;
/*  26: 14 */   static int kbdBufferHead = 0;
/*  27: 14 */   static int kbdBufferTail = 0;
/*  28: 15 */   static char[] kbdBuffer = new char[1000];
/*  29:    */   static char key;
/*  30:    */   
/*  31:    */   public static void displayWindow(String frameName, String labelName)
/*  32:    */   {
/*  33: 29 */     frame = new JFrame(frameName);
/*  34: 30 */     frame.setDefaultCloseOperation(3);
/*  35: 31 */     frame.setSize(new Dimension(500, 500));
/*  36: 32 */     frame.setBackground(Color.WHITE);
/*  37:    */     
/*  38:    */ 
/*  39:    */ 
/*  40: 36 */     label = new JLabel(labelName);
/*  41: 37 */     label.setBackground(Color.WHITE);
/*  42: 38 */     label.setPreferredSize(new Dimension(150, 150));
/*  43: 39 */     frame.getContentPane().add(label, "Center");
/*  44: 40 */     button.setSize(new Dimension(10, 10));
/*  45: 41 */     frame.getContentPane().add(button, "South");
/*  46: 42 */     frame.getContentPane().add(field, "North");
/*  47:    */     
/*  48:    */ 
/*  49: 45 */     frame.setVisible(true);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void actionPerformed(ActionEvent e)
/*  53:    */   {
/*  54: 51 */     String fieldText = field.getText();
/*  55: 52 */     JFrame frame2 = new JFrame("Pop-up!");
/*  56: 53 */     frame2.setDefaultCloseOperation(3);
/*  57: 54 */     frame2.setSize(new Dimension(250, 150));
/*  58: 55 */     JLabel label2 = new JLabel(fieldText);
/*  59: 56 */     frame2.getContentPane().add(label2, "West");
/*  60: 57 */     frame2.setVisible(true);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void keyPressed(KeyEvent e) {}
/*  64:    */   
/*  65:    */   public void keyReleased(KeyEvent e) {}
/*  66:    */   
/*  67:    */   public synchronized void keyTyped(KeyEvent e)
/*  68:    */   {
/*  69: 79 */     key = e.getKeyChar();
/*  70: 80 */     kbdBuffer[kbdBufferHead] = e.getKeyChar();
/*  71: 81 */     kbdBufferHead = (kbdBufferHead + 1) % kbdBuffer.length;
/*  72: 82 */     notify();
/*  73:    */   }
/*  74:    */   
/*  75:    */   public synchronized char getKey()
/*  76:    */   {
/*  77: 88 */     char key1 = key;
/*  78: 89 */     key = '\000';
/*  79: 90 */     return key1;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public synchronized char getChar()
/*  83:    */     throws InterruptedException
/*  84:    */   {
/*  85: 96 */     Runnable r = new Runnable()
/*  86:    */     {
/*  87:    */       public void run()
/*  88:    */       {
/*  89:    */         try
/*  90:    */         {
/*  91:103 */           wait();
/*  92:    */         }
/*  93:    */         catch (InterruptedException localInterruptedException) {}
/*  94:    */       }
/*  95:113 */     };
/*  96:114 */     char ch = key;
/*  97:115 */     key = '\000';
/*  98:116 */     return ch;
/*  99:    */   }
/* 100:    */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.Playground
 * JD-Core Version:    0.7.0.1
 */