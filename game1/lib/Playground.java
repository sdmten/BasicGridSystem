/*   1:    */ package game1.lib;
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
/*  31:    */   public Playground()
/*  32:    */   {
/*  33: 20 */     button.addActionListener(this);
/*  34: 21 */     field.addKeyListener(this);
/*  35: 22 */     displayWindow("Control Window", "Test");
/*  36: 23 */     setFocusable(true);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static void displayWindow(String frameName, String labelName)
/*  40:    */   {
/*  41: 29 */     frame = new JFrame(frameName);
/*  42: 30 */     frame.setDefaultCloseOperation(3);
/*  43: 31 */     frame.setSize(new Dimension(500, 500));
/*  44: 32 */     frame.setBackground(Color.WHITE);
/*  45:    */     
/*  46:    */ 
/*  47:    */ 
/*  48: 36 */     label = new JLabel(labelName);
/*  49: 37 */     label.setBackground(Color.WHITE);
/*  50: 38 */     label.setPreferredSize(new Dimension(150, 150));
/*  51: 39 */     frame.getContentPane().add(label, "Center");
/*  52: 40 */     button.setSize(new Dimension(10, 10));
/*  53: 41 */     frame.getContentPane().add(button, "South");
/*  54: 42 */     frame.getContentPane().add(field, "North");
/*  55:    */     
/*  56:    */ 
/*  57: 45 */     frame.setVisible(true);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void actionPerformed(ActionEvent e)
/*  61:    */   {
/*  62: 51 */     String fieldText = field.getText();
/*  63: 52 */     JFrame frame2 = new JFrame("Pop-up!");
/*  64: 53 */     frame2.setDefaultCloseOperation(3);
/*  65: 54 */     frame2.setSize(new Dimension(250, 150));
/*  66: 55 */     JLabel label2 = new JLabel(fieldText);
/*  67: 56 */     frame2.getContentPane().add(label2, "West");
/*  68: 57 */     frame2.setVisible(true);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void keyPressed(KeyEvent e) {}
/*  72:    */   
/*  73:    */   public void keyReleased(KeyEvent e) {}
/*  74:    */   
/*  75:    */   public synchronized void keyTyped(KeyEvent e)
/*  76:    */   {
/*  77: 79 */     key = e.getKeyChar();
/*  78: 80 */     kbdBuffer[kbdBufferHead] = e.getKeyChar();
/*  79: 81 */     kbdBufferHead = (kbdBufferHead + 1) % kbdBuffer.length;
/*  80: 82 */     notify();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public synchronized char getKey()
/*  84:    */   {
/*  85: 88 */     char key1 = key;
/*  86: 89 */     key = '\000';
/*  87: 90 */     return key1;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public synchronized char getChar()
/*  91:    */     throws InterruptedException
/*  92:    */   {
/*  93: 96 */     Runnable r = new Runnable()
/*  94:    */     {
/*  95:    */       public void run()
/*  96:    */       {
/*  97:    */         try
/*  98:    */         {
/*  99:103 */           wait();
/* 100:    */         }
/* 101:    */         catch (InterruptedException localInterruptedException) {}
/* 102:    */       }
/* 103:113 */     };
/* 104:114 */     char ch = key;
/* 105:115 */     key = '\000';
/* 106:116 */     return ch;
/* 107:    */   }
/* 108:    */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.lib.Playground
 * JD-Core Version:    0.7.0.1
 */