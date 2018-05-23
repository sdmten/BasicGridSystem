/*  1:   */ package game1.hsa;
/*  2:   */ 
/*  3:   */ import java.awt.BorderLayout;
/*  4:   */ import java.awt.Button;
/*  5:   */ import java.awt.Color;
/*  6:   */ import java.awt.Container;
/*  7:   */ import java.awt.FlowLayout;
/*  8:   */ import java.awt.Frame;
/*  9:   */ import java.awt.Panel;
/* 10:   */ 
/* 11:   */ class ConsoleFrame
/* 12:   */   extends Frame
/* 13:   */ {
/* 14:   */   private Button saveButton;
/* 15:   */   private Button printButton;
/* 16:   */   private Button quitButton;
/* 17:   */   
/* 18:   */   public ConsoleFrame(ConsoleParent parent, Panel consoleCanvasPanelInner)
/* 19:   */   {
/* 20:25 */     super("");
/* 21:   */     
/* 22:27 */     addFocusListener(parent);
/* 23:28 */     addKeyListener(parent);
/* 24:29 */     addWindowListener(parent);
/* 25:   */     
/* 26:   */ 
/* 27:   */ 
/* 28:33 */     Panel consoleCanvasPanelOuter = new Panel();
/* 29:34 */     consoleCanvasPanelOuter.setLayout(new BorderLayout());
/* 30:35 */     consoleCanvasPanelOuter.addFocusListener(parent);
/* 31:36 */     consoleCanvasPanelOuter.addKeyListener(parent);
/* 32:   */     
/* 33:   */ 
/* 34:39 */     consoleCanvasPanelInner.addFocusListener(parent);
/* 35:40 */     consoleCanvasPanelInner.addKeyListener(parent);
/* 36:   */     
/* 37:   */ 
/* 38:43 */     ConsoleEdges consoleEdgeLeft = new ConsoleEdges(3);
/* 39:44 */     consoleEdgeLeft.addFocusListener(parent);
/* 40:45 */     ConsoleEdges consoleEdgeRight = new ConsoleEdges(4);
/* 41:46 */     consoleEdgeRight.addFocusListener(parent);
/* 42:47 */     ConsoleEdges consoleEdgeTop = new ConsoleEdges(1);
/* 43:48 */     consoleEdgeTop.addFocusListener(parent);
/* 44:49 */     ConsoleEdges consoleEdgeBottom = new ConsoleEdges(2);
/* 45:50 */     consoleEdgeBottom.addFocusListener(parent);
/* 46:51 */     consoleCanvasPanelOuter.add("North", consoleEdgeTop);
/* 47:52 */     consoleCanvasPanelOuter.add("South", consoleEdgeBottom);
/* 48:53 */     consoleCanvasPanelOuter.add("East", consoleEdgeRight);
/* 49:54 */     consoleCanvasPanelOuter.add("West", consoleEdgeLeft);
/* 50:55 */     consoleCanvasPanelOuter.add("Center", consoleCanvasPanelInner);
/* 51:56 */     consoleCanvasPanelOuter.add("Center", consoleCanvasPanelInner);
/* 52:   */     
/* 53:58 */     this.saveButton = new Button(" Save ");
/* 54:59 */     this.saveButton.setActionCommand("save");
/* 55:60 */     this.saveButton.addActionListener(parent);
/* 56:61 */     this.printButton = new Button(" Print ");
/* 57:62 */     this.printButton.setActionCommand("print");
/* 58:63 */     this.printButton.addActionListener(parent);
/* 59:64 */     this.quitButton = new Button(" Quit ");
/* 60:65 */     this.quitButton.setActionCommand("quit");
/* 61:66 */     this.quitButton.addActionListener(parent);
/* 62:67 */     Panel buttonPanel = new Panel();
/* 63:68 */     buttonPanel.addFocusListener(parent);
/* 64:69 */     buttonPanel.setBackground(Color.lightGray);
/* 65:70 */     buttonPanel.setLayout(new FlowLayout(1, 40, 5));
/* 66:71 */     buttonPanel.add(this.saveButton);
/* 67:72 */     buttonPanel.add(this.printButton);
/* 68:73 */     buttonPanel.add(this.quitButton);
/* 69:   */     
/* 70:   */ 
/* 71:76 */     add("Center", consoleCanvasPanelOuter);
/* 72:77 */     add("North", buttonPanel);
/* 73:   */     
/* 74:   */ 
/* 75:80 */     setBackground(Color.green);
/* 76:   */   }
/* 77:   */   
/* 78:   */   protected void enableButtons(boolean enable)
/* 79:   */   {
/* 80:86 */     this.saveButton.setEnabled(enable);
/* 81:87 */     this.printButton.setEnabled(enable);
/* 82:88 */     this.quitButton.setEnabled(enable);
/* 83:   */   }
/* 84:   */   
/* 85:   */   protected void mainStopped()
/* 86:   */   {
/* 87:94 */     this.quitButton.setLabel(" Close ");
/* 88:   */     
/* 89:96 */     this.quitButton.getParent().validate();
/* 90:   */     
/* 91:98 */     this.quitButton.getParent().repaint();
/* 92:   */   }
/* 93:   */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.ConsoleFrame
 * JD-Core Version:    0.7.0.1
 */