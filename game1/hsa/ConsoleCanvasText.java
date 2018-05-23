/*   1:    */ package game1.hsa;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Dimension;
/*   5:    */ import java.awt.Graphics;
/*   6:    */ import java.awt.Scrollbar;
/*   7:    */ 
/*   8:    */ class ConsoleCanvasText
/*   9:    */   extends ConsoleCanvas
/*  10:    */ {
/*  11: 38 */   private StringBuffer[] text = new StringBuffer[1000];
/*  12: 39 */   private int numLines = 1;
/*  13: 40 */   private int topLine = 1;
/*  14: 45 */   int startOfInputNumLines = 0;
/*  15: 46 */   int startOfInputLastLineLength = 0;
/*  16:    */   Scrollbar scrollbar;
/*  17:    */   
/*  18:    */   public ConsoleCanvasText(ConsoleParent parent, Scrollbar scrollbar, int rows, int columns, int fontSize)
/*  19:    */   {
/*  20: 64 */     super(parent, rows, columns, fontSize);
/*  21:    */     
/*  22:    */ 
/*  23: 67 */     this.scrollbar = scrollbar;
/*  24:    */     
/*  25:    */ 
/*  26: 70 */     this.text[0] = new StringBuffer();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public synchronized void addNewLine()
/*  30:    */   {
/*  31: 79 */     if (this.numLines == this.text.length)
/*  32:    */     {
/*  33: 81 */       StringBuffer[] text1 = new StringBuffer[this.text.length + 1000];
/*  34: 82 */       if (text1 == null)
/*  35:    */       {
/*  36: 86 */         int numLinesToBeEliminated = this.text.length / 3;
/*  37: 87 */         System.arraycopy(this.text, numLinesToBeEliminated, this.text, 0, 
/*  38: 88 */           this.text.length - numLinesToBeEliminated);
/*  39: 89 */         this.numLines -= numLinesToBeEliminated;
/*  40:    */       }
/*  41:    */       else
/*  42:    */       {
/*  43: 93 */         System.arraycopy(this.text, 0, text1, 0, this.text.length);
/*  44: 94 */         this.text = text1;
/*  45:    */       }
/*  46:    */     }
/*  47: 98 */     if ((this.cursorVisible) || (!this.hasFocus)) {
/*  48: 98 */       toggleCursor();
/*  49:    */     }
/*  50: 99 */     this.numLines += 1;
/*  51:100 */     this.text[(this.numLines - 1)] = new StringBuffer();
/*  52:101 */     if ((this.cursorVisible) || (!this.hasFocus)) {
/*  53:101 */       toggleCursor();
/*  54:    */     }
/*  55:103 */     if (this.numLines <= this.numRows) {
/*  56:105 */       this.scrollbar.setMaximum(this.numRows + 1);
/*  57:    */     } else {
/*  58:109 */       this.scrollbar.setMaximum(this.numLines + 1);
/*  59:    */     }
/*  60:111 */     this.scrollbar.setVisibleAmount(this.numRows);
/*  61:112 */     if (this.numLines - this.topLine >= this.numRows)
/*  62:    */     {
/*  63:114 */       setTopLine(this.numLines - this.numRows + 1);
/*  64:115 */       this.scrollbar.setValue(this.topLine);
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public synchronized void addText(String newText)
/*  69:    */   {
/*  70:125 */     if (this.numLines - this.topLine >= this.numRows)
/*  71:    */     {
/*  72:128 */       this.text[(this.numLines - 1)].append(newText);
/*  73:    */       
/*  74:130 */       setTopLine(this.numLines - this.numRows + 1);
/*  75:131 */       if ((this.cursorVisible) || (!this.hasFocus)) {
/*  76:131 */         toggleCursor();
/*  77:    */       }
/*  78:132 */       return;
/*  79:    */     }
/*  80:136 */     Graphics g = getGraphics();
/*  81:137 */     g.translate(2, 2);
/*  82:138 */     g.setColor(Color.black);
/*  83:139 */     g.setFont(this.font);
/*  84:140 */     int yPos = (this.numLines - this.topLine) * this.fontHeight + this.fontHeight - this.fontBase;
/*  85:141 */     if ((this.cursorVisible) || (!this.hasFocus)) {
/*  86:141 */       toggleCursor();
/*  87:    */     }
/*  88:144 */     this.text[(this.numLines - 1)].append(newText);
/*  89:    */     
/*  90:    */ 
/*  91:147 */     g.drawString(this.text[(this.numLines - 1)].toString(), 0, yPos);
/*  92:149 */     if ((this.cursorVisible) || (!this.hasFocus)) {
/*  93:149 */       toggleCursor();
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void deleteFromStartOfInput()
/*  98:    */   {
/*  99:157 */     this.numLines = this.startOfInputNumLines;
/* 100:158 */     this.text[(this.numLines - 1)].setLength(this.startOfInputLastLineLength);
/* 101:159 */     doDraw(getGraphics());
/* 102:    */   }
/* 103:    */   
/* 104:    */   protected synchronized void doDraw(Graphics g)
/* 105:    */   {
/* 106:166 */     int bottomLine = Math.min(this.numLines, this.topLine + this.numRows - 1);
/* 107:167 */     if ((this.cursorVisible) || (!this.hasFocus)) {
/* 108:167 */       toggleCursor();
/* 109:    */     }
/* 110:168 */     g.translate(2, 2);
/* 111:    */     
/* 112:    */ 
/* 113:171 */     g.clearRect(-2, -2, getSize().width, getSize().height);
/* 114:    */     
/* 115:    */ 
/* 116:174 */     g.setColor(Color.black);
/* 117:175 */     g.setFont(this.font);
/* 118:176 */     for (int line = this.topLine; line <= bottomLine; line++)
/* 119:    */     {
/* 120:178 */       int yPos = (line - this.topLine) * this.fontHeight + this.fontHeight - this.fontBase;
/* 121:179 */       g.drawString(this.text[(line - 1)].toString(), 0, yPos);
/* 122:    */     }
/* 123:182 */     if ((this.cursorVisible) || (!this.hasFocus)) {
/* 124:182 */       toggleCursor();
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void eraseLastChar()
/* 129:    */   {
/* 130:190 */     if (this.text[(this.numLines - 1)].length() == 0) {
/* 131:192 */       this.numLines -= 1;
/* 132:    */     }
/* 133:196 */     this.text[(this.numLines - 1)].setLength(this.text[(this.numLines - 1)].length() - 1);
/* 134:197 */     doDraw(getGraphics());
/* 135:    */   }
/* 136:    */   
/* 137:    */   public int getCurrentColumn()
/* 138:    */   {
/* 139:206 */     return this.text[(this.numLines - 1)].length() + 1;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public int getCurrentRow()
/* 143:    */   {
/* 144:215 */     return this.numLines - this.topLine + 1;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void markStartOfInput()
/* 148:    */   {
/* 149:223 */     this.startOfInputNumLines = this.numLines;
/* 150:224 */     this.startOfInputLastLineLength = this.text[(this.numLines - 1)].length();
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void paint(Graphics g)
/* 154:    */   {
/* 155:231 */     doDraw(g);
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void printContents()
/* 159:    */   {
/* 160:238 */     throw new Error("Unresolved compilation problems: \n\tConsoleSavePrint cannot be resolved to a type\n\tConsoleSavePrint cannot be resolved to a type\n\tConsoleSavePrint cannot be resolved to a variable\n");
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void saveContents()
/* 164:    */   {
/* 165:248 */     throw new Error("Unresolved compilation problems: \n\tConsoleSavePrint cannot be resolved to a type\n\tConsoleSavePrint cannot be resolved to a type\n\tConsoleSavePrint cannot be resolved to a variable\n");
/* 166:    */   }
/* 167:    */   
/* 168:    */   public synchronized void setTopLine(int newTopLine)
/* 169:    */   {
/* 170:259 */     newTopLine = Math.max(1, 
/* 171:260 */       Math.min(newTopLine, this.numLines - this.numRows + 2));
/* 172:262 */     if (this.topLine != newTopLine)
/* 173:    */     {
/* 174:264 */       this.topLine = newTopLine;
/* 175:265 */       repaint();
/* 176:    */     }
/* 177:    */   }
/* 178:    */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.ConsoleCanvasText
 * JD-Core Version:    0.7.0.1
 */