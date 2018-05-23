/*   1:    */ package game1.hsa;
/*   2:    */ 
/*   3:    */ import java.awt.Canvas;
/*   4:    */ import java.awt.Color;
/*   5:    */ import java.awt.Dimension;
/*   6:    */ import java.awt.FileDialog;
/*   7:    */ import java.awt.Font;
/*   8:    */ import java.awt.FontMetrics;
/*   9:    */ import java.awt.Graphics;
/*  10:    */ import java.awt.Toolkit;
/*  11:    */ import java.awt.event.FocusEvent;
/*  12:    */ import java.awt.event.FocusListener;
/*  13:    */ import java.awt.print.Book;
/*  14:    */ import java.awt.print.PageFormat;
/*  15:    */ import java.awt.print.PrinterException;
/*  16:    */ import java.awt.print.PrinterJob;
/*  17:    */ import java.io.PrintStream;
/*  18:    */ import java.util.Properties;
/*  19:    */ 
/*  20:    */ abstract class ConsoleCanvas
/*  21:    */   extends Canvas
/*  22:    */   implements FocusListener, Runnable
/*  23:    */ {
/*  24:    */   protected static final int MARGIN = 2;
/*  25:    */   protected static final int DEPTH = 3;
/*  26:    */   protected static final int GREY_MARGIN = 5;
/*  27:    */   protected static final String CONSOLE_CURSOR_THREAD_NAME = "Console Cursor Thread";
/*  28:    */   protected static final String SCREEN_UPDATER_THREAD_NAME = "Screen Updater";
/*  29:    */   protected ConsoleParent parentConsole;
/*  30:    */   protected int numRows;
/*  31:    */   protected int numCols;
/*  32:    */   protected int numXPixels;
/*  33:    */   protected int numYPixels;
/*  34: 55 */   protected Font font = null;
/*  35: 56 */   protected int fontWidth = 0;
/*  36: 56 */   protected int fontHeight = 0;
/*  37: 56 */   protected int fontBase = 0;
/*  38:    */   protected Thread cursorThread;
/*  39: 65 */   protected boolean cursorVisible = false;
/*  40: 67 */   protected int cursorBlinking = 0;
/*  41: 69 */   protected boolean hasFocus = false;
/*  42:    */   protected SavePrint savePrint;
/*  43: 73 */   private boolean killCursorThread = false;
/*  44: 75 */   protected boolean macOSX = false;
/*  45:    */   
/*  46:    */   public ConsoleCanvas(ConsoleParent parent, int rows, int columns, int fontSize)
/*  47:    */   {
/*  48: 89 */     this.parentConsole = parent;
/*  49: 90 */     this.numRows = rows;
/*  50: 91 */     this.numCols = columns;
/*  51:    */     
/*  52: 93 */     Properties p = System.getProperties();
/*  53: 94 */     if (p.getProperty("os.name").equals("Mac OS X")) {
/*  54: 96 */       this.macOSX = true;
/*  55:    */     }
/*  56:102 */     this.font = new Font("monospaced", 0, fontSize);
/*  57:103 */     FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(this.font);
/*  58:104 */     this.fontHeight = (fm.getHeight() + fm.getLeading());
/*  59:105 */     this.fontBase = fm.getDescent();
/*  60:106 */     this.fontWidth = 0;
/*  61:107 */     for (int ch = 32; ch < 127; ch++) {
/*  62:109 */       this.fontWidth = Math.max(this.fontWidth, fm.charWidth(ch));
/*  63:    */     }
/*  64:113 */     this.numXPixels = (this.numCols * this.fontWidth);
/*  65:114 */     this.numYPixels = (this.numRows * this.fontHeight);
/*  66:115 */     setSize(this.numXPixels + 4, this.numYPixels + 4);
/*  67:    */     
/*  68:    */ 
/*  69:118 */     setBackground(Color.white);
/*  70:    */     
/*  71:    */ 
/*  72:121 */     addFocusListener(this);
/*  73:122 */     addKeyListener(this.parentConsole);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void addNotify()
/*  77:    */   {
/*  78:132 */     super.addNotify();
/*  79:    */     
/*  80:    */ 
/*  81:135 */     this.cursorThread = new Thread(this);
/*  82:136 */     this.cursorThread.setName("Console Cursor Thread");
/*  83:137 */     this.cursorThread.start();
/*  84:    */   }
/*  85:    */   
/*  86:    */   protected synchronized void blinkCursor()
/*  87:    */   {
/*  88:146 */     if (this.macOSX)
/*  89:    */     {
/*  90:148 */       this.cursorVisible = (!this.cursorVisible);
/*  91:149 */       repaint();
/*  92:150 */       return;
/*  93:    */     }
/*  94:153 */     if (this.hasFocus) {
/*  95:155 */       toggleCursor();
/*  96:    */     }
/*  97:157 */     this.cursorVisible = (!this.cursorVisible);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public synchronized void focusGained(FocusEvent e)
/* 101:    */   {
/* 102:167 */     if (!this.hasFocus)
/* 103:    */     {
/* 104:169 */       if (this.macOSX)
/* 105:    */       {
/* 106:171 */         this.hasFocus = true;
/* 107:172 */         repaint();
/* 108:173 */         return;
/* 109:    */       }
/* 110:176 */       toggleCursor();
/* 111:177 */       this.hasFocus = true;
/* 112:178 */       if (this.cursorVisible) {
/* 113:179 */         toggleCursor();
/* 114:    */       }
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public synchronized void focusLost(FocusEvent e)
/* 119:    */   {
/* 120:190 */     if (this.hasFocus)
/* 121:    */     {
/* 122:192 */       if (this.macOSX)
/* 123:    */       {
/* 124:194 */         this.hasFocus = false;
/* 125:195 */         repaint();
/* 126:196 */         return;
/* 127:    */       }
/* 128:199 */       if (this.cursorVisible) {
/* 129:200 */         toggleCursor();
/* 130:    */       }
/* 131:201 */       this.hasFocus = false;
/* 132:202 */       toggleCursor();
/* 133:    */     }
/* 134:    */   }
/* 135:    */   
/* 136:    */   public abstract int getCurrentColumn();
/* 137:    */   
/* 138:    */   public abstract int getCurrentRow();
/* 139:    */   
/* 140:    */   public synchronized void invertScreen()
/* 141:    */   {
/* 142:226 */     Graphics g = getGraphics();
/* 143:    */     
/* 144:228 */     g.translate(2, 2);
/* 145:229 */     g.setColor(Color.white);
/* 146:230 */     g.setXORMode(Color.black);
/* 147:    */     
/* 148:    */ 
/* 149:233 */     g.fillRect(0, 0, this.numXPixels, this.numYPixels);
/* 150:234 */     Toolkit.getDefaultToolkit().sync();
/* 151:    */     try
/* 152:    */     {
/* 153:239 */       Thread.sleep(50L);
/* 154:    */     }
/* 155:    */     catch (Exception localException) {}
/* 156:245 */     if (this.macOSX)
/* 157:    */     {
/* 158:247 */       g.setPaintMode();
/* 159:248 */       repaint();
/* 160:249 */       return;
/* 161:    */     }
/* 162:253 */     g.fillRect(0, 0, this.numXPixels, this.numYPixels);
/* 163:254 */     Toolkit.getDefaultToolkit().sync();
/* 164:    */     
/* 165:256 */     g.setPaintMode();
/* 166:    */   }
/* 167:    */   
/* 168:    */   public boolean isFocusTraversable()
/* 169:    */   {
/* 170:262 */     return true;
/* 171:    */   }
/* 172:    */   
/* 173:    */   protected boolean isMainRunning()
/* 174:    */   {
/* 175:309 */     return !Console.mainReturned;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void killCursorThread()
/* 179:    */   {
/* 180:318 */     this.killCursorThread = true;
/* 181:319 */     this.cursorThread.interrupt();
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void printContents()
/* 185:    */   {
/* 186:328 */     PrinterJob printerJob = PrinterJob.getPrinterJob();
/* 187:329 */     Book book = new Book();
/* 188:330 */     book.append(this.savePrint, new PageFormat());
/* 189:331 */     printerJob.setPageable(book);
/* 190:332 */     if (!printerJob.printDialog()) {
/* 191:334 */       return;
/* 192:    */     }
/* 193:    */     try
/* 194:    */     {
/* 195:339 */       printerJob.print();
/* 196:    */     }
/* 197:    */     catch (PrinterException exception)
/* 198:    */     {
/* 199:343 */       System.err.println("Printing error: " + exception);
/* 200:    */     }
/* 201:345 */     requestFocus();
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void run()
/* 205:    */   {
/* 206:356 */     boolean mainIsRunning = true;
/* 207:358 */     while (!this.killCursorThread)
/* 208:    */     {
/* 209:360 */       blinkCursor();
/* 210:362 */       if ((mainIsRunning) && (!isMainRunning()))
/* 211:    */       {
/* 212:364 */         mainIsRunning = false;
/* 213:365 */         this.parentConsole.mainStopped();
/* 214:366 */         return;
/* 215:    */       }
/* 216:    */       try
/* 217:    */       {
/* 218:371 */         Thread.sleep(300L);
/* 219:    */       }
/* 220:    */       catch (Exception localException) {}
/* 221:    */     }
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void saveContents()
/* 225:    */   {
/* 226:386 */     FileDialog fd = new FileDialog(this.parentConsole.window, 
/* 227:387 */       "Save Console Window", 1);
/* 228:388 */     fd.setFile("Console.bmp");
/* 229:389 */     fd.show();
/* 230:391 */     if (fd.getFile() == null) {
/* 231:393 */       return;
/* 232:    */     }
/* 233:396 */     String fileName = fd.getDirectory() + fd.getFile();
/* 234:398 */     if (fileName.indexOf(".*.*") != -1) {
/* 235:400 */       fileName = fileName.substring(0, fileName.length() - 4);
/* 236:    */     }
/* 237:404 */     this.savePrint.saveToFile(fileName);
/* 238:    */   }
/* 239:    */   
/* 240:    */   public synchronized void setCursorVisible(boolean visible)
/* 241:    */   {
/* 242:415 */     if (visible)
/* 243:    */     {
/* 244:417 */       this.cursorBlinking += 1;
/* 245:418 */       if (this.cursorBlinking == 1) {
/* 246:420 */         if ((this.cursorVisible) || (!this.hasFocus)) {
/* 247:422 */           toggleCursor();
/* 248:    */         }
/* 249:    */       }
/* 250:    */     }
/* 251:    */     else
/* 252:    */     {
/* 253:428 */       if (this.cursorBlinking == 1) {
/* 254:430 */         if ((this.cursorVisible) || (!this.hasFocus)) {
/* 255:432 */           toggleCursor();
/* 256:    */         }
/* 257:    */       }
/* 258:435 */       this.cursorBlinking -= 1;
/* 259:    */     }
/* 260:    */   }
/* 261:    */   
/* 262:    */   protected synchronized void toggleCursor()
/* 263:    */   {
/* 264:445 */     if (this.cursorBlinking > 0)
/* 265:    */     {
/* 266:447 */       int x = (getCurrentColumn() - 1) * this.fontWidth;
/* 267:448 */       int y = (getCurrentRow() - 1) * this.fontHeight;
/* 268:449 */       Graphics g = getGraphics();
/* 269:    */       
/* 270:451 */       g.translate(2, 2);
/* 271:452 */       g.setColor(Color.white);
/* 272:453 */       g.setXORMode(Color.black);
/* 273:454 */       if (this.hasFocus) {
/* 274:456 */         g.fillRect(x, y, this.fontWidth, this.fontHeight);
/* 275:    */       } else {
/* 276:460 */         g.drawRect(x, y, this.fontWidth, this.fontHeight);
/* 277:    */       }
/* 278:462 */       g.setPaintMode();
/* 279:463 */       Toolkit.getDefaultToolkit().sync();
/* 280:    */     }
/* 281:    */   }
/* 282:    */   
/* 283:    */   public Dimension getMinimumSize()
/* 284:    */   {
/* 285:473 */     return new Dimension(this.numXPixels + 4, this.numYPixels + 4);
/* 286:    */   }
/* 287:    */   
/* 288:    */   public Dimension getPreferredSize()
/* 289:    */   {
/* 290:482 */     return getMinimumSize();
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void update(Graphics g)
/* 294:    */   {
/* 295:491 */     paint(g);
/* 296:    */   }
/* 297:    */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.ConsoleCanvas
 * JD-Core Version:    0.7.0.1
 */