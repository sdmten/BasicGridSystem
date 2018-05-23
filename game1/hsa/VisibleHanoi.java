/*   1:    */ package game1.hsa;
/*   2:    */ 
/*   3:    */ import java.awt.BorderLayout;
/*   4:    */ import java.awt.Button;
/*   5:    */ import java.awt.Canvas;
/*   6:    */ import java.awt.Color;
/*   7:    */ import java.awt.Dimension;
/*   8:    */ import java.awt.FileDialog;
/*   9:    */ import java.awt.Font;
/*  10:    */ import java.awt.FontMetrics;
/*  11:    */ import java.awt.Frame;
/*  12:    */ import java.awt.Graphics;
/*  13:    */ import java.awt.GridBagConstraints;
/*  14:    */ import java.awt.GridBagLayout;
/*  15:    */ import java.awt.Image;
/*  16:    */ import java.awt.Insets;
/*  17:    */ import java.awt.Label;
/*  18:    */ import java.awt.Panel;
/*  19:    */ import java.awt.Rectangle;
/*  20:    */ import java.awt.Scrollbar;
/*  21:    */ import java.awt.Toolkit;
/*  22:    */ import java.awt.Window;
/*  23:    */ import java.awt.event.ActionEvent;
/*  24:    */ import java.awt.event.ActionListener;
/*  25:    */ import java.awt.event.AdjustmentEvent;
/*  26:    */ import java.awt.event.AdjustmentListener;
/*  27:    */ import java.awt.event.WindowAdapter;
/*  28:    */ import java.awt.event.WindowEvent;
/*  29:    */ import java.awt.print.Book;
/*  30:    */ import java.awt.print.PageFormat;
/*  31:    */ import java.awt.print.PrinterException;
/*  32:    */ import java.awt.print.PrinterJob;
/*  33:    */ import java.io.PrintStream;
/*  34:    */ 
/*  35:    */ public class VisibleHanoi
/*  36:    */   extends Frame
/*  37:    */   implements ActionListener, AdjustmentListener
/*  38:    */ {
/*  39:    */   static final int DEFAULT_XSIZE = 500;
/*  40:    */   static final int DEFAULT_YSIZE = 300;
/*  41:    */   static final int INIT_SLIDER_POS = 50;
/*  42:    */   int xSize;
/*  43:    */   int ySize;
/*  44: 20 */   int delayTime = calculateDelayTimeFromSlider(50);
/*  45:    */   WindowCanvas canvas;
/*  46:    */   Button saveButton;
/*  47:    */   Button printButton;
/*  48:    */   Scrollbar scrollBar;
/*  49:    */   
/*  50:    */   public VisibleHanoi(int numDisks)
/*  51:    */   {
/*  52: 29 */     super("Towers of Hanio");
/*  53:    */     
/*  54: 31 */     this.xSize = 500;
/*  55: 32 */     this.ySize = 300;
/*  56: 33 */     this.canvas = new WindowCanvas(this.xSize, this.ySize, numDisks);
/*  57:    */     
/*  58:    */ 
/*  59: 36 */     addWindowListener(new WindowCloser());
/*  60:    */     
/*  61: 38 */     this.saveButton = new Button("Save");
/*  62: 39 */     this.printButton = new Button("Print");
/*  63: 40 */     this.scrollBar = 
/*  64: 41 */       new Scrollbar(0, 50, 0, 0, 100);
/*  65:    */     
/*  66:    */ 
/*  67: 44 */     this.saveButton.addActionListener(this);
/*  68: 45 */     this.printButton.addActionListener(this);
/*  69:    */     
/*  70:    */ 
/*  71: 48 */     this.scrollBar.addAdjustmentListener(this);
/*  72:    */     
/*  73:    */ 
/*  74:    */ 
/*  75:    */ 
/*  76: 53 */     Panel scrollPanel = new Panel();
/*  77: 54 */     Label slower = new Label("Slower", 2);
/*  78: 55 */     Label faster = new Label("Faster", 0);
/*  79: 56 */     Label setDelay = new Label("Set Speed", 1);
/*  80: 57 */     Font labelFont = new Font("SansSerif", 0, 10);
/*  81: 58 */     slower.setFont(labelFont);
/*  82: 59 */     faster.setFont(labelFont);
/*  83: 60 */     setDelay.setFont(labelFont);
/*  84: 61 */     scrollPanel.setLayout(new BorderLayout());
/*  85: 62 */     scrollPanel.add(slower, "West");
/*  86: 63 */     scrollPanel.add(faster, "East");
/*  87: 64 */     scrollPanel.add(setDelay, "North");
/*  88: 65 */     scrollPanel.add(this.scrollBar, "Center");
/*  89:    */     
/*  90:    */ 
/*  91: 68 */     Panel controlPanel = new Panel();
/*  92: 69 */     GridBagLayout gb = new GridBagLayout();
/*  93: 70 */     GridBagConstraints cn = new GridBagConstraints();
/*  94: 71 */     Label label1 = new Label("   ");
/*  95: 72 */     Label label2 = new Label("   ");
/*  96: 73 */     controlPanel.setBackground(Color.lightGray);
/*  97: 74 */     controlPanel.setLayout(gb);
/*  98:    */     
/*  99:    */ 
/* 100:    */ 
/* 101: 78 */     controlPanel.add(label1);
/* 102: 79 */     controlPanel.add(this.saveButton);
/* 103: 80 */     controlPanel.add(this.printButton);
/* 104: 81 */     controlPanel.add(scrollPanel);
/* 105: 82 */     controlPanel.add(label2);
/* 106:    */     
/* 107:    */ 
/* 108: 85 */     cn.insets = new Insets(4, 4, 4, 4);
/* 109: 86 */     gb.setConstraints(this.saveButton, cn);
/* 110: 87 */     gb.setConstraints(this.printButton, cn);
/* 111:    */     
/* 112:    */ 
/* 113:    */ 
/* 114: 91 */     cn.insets = new Insets(0, 4, 4, 4);
/* 115: 92 */     cn.fill = 2;
/* 116: 93 */     cn.weightx = 1.0D;
/* 117: 94 */     gb.setConstraints(scrollPanel, cn);
/* 118:    */     
/* 119:    */ 
/* 120: 97 */     add(controlPanel, "North");
/* 121:    */     
/* 122: 99 */     add(this.canvas, "South");
/* 123:    */     
/* 124:101 */     pack();
/* 125:    */     
/* 126:    */ 
/* 127:104 */     Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
/* 128:105 */     setLocation(screen.width - getSize().width, 0);
/* 129:    */     
/* 130:107 */     show();
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setSpeed(int speed)
/* 134:    */   {
/* 135:113 */     int value = Math.min(Math.max(speed, 0), 100);
/* 136:114 */     this.scrollBar.setValue(value);
/* 137:115 */     this.delayTime = calculateDelayTimeFromSlider(value);
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void moveTop(int fromPost, int toPost)
/* 141:    */   {
/* 142:121 */     this.canvas.moveTop(fromPost - 1, toPost - 1);
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void actionPerformed(ActionEvent evt)
/* 146:    */   {
/* 147:127 */     if (evt.getSource() == this.saveButton)
/* 148:    */     {
/* 149:130 */       FileDialog fd = new FileDialog(this, "Save PaintBug Window", 
/* 150:131 */         1);
/* 151:132 */       fd.setFile("PaintBug.bmp");
/* 152:133 */       fd.show();
/* 153:135 */       if (fd.getFile() == null) {
/* 154:137 */         return;
/* 155:    */       }
/* 156:140 */       String fileName = fd.getDirectory() + fd.getFile();
/* 157:142 */       if (fileName.indexOf(".*.*") != -1) {
/* 158:144 */         fileName = fileName.substring(0, fileName.length() - 4);
/* 159:    */       }
/* 160:148 */       this.canvas.savePrint.saveToFile(fileName);
/* 161:    */     }
/* 162:150 */     else if (evt.getSource() == this.printButton)
/* 163:    */     {
/* 164:152 */       PrinterJob printerJob = PrinterJob.getPrinterJob();
/* 165:153 */       Book book = new Book();
/* 166:154 */       book.append(this.canvas.savePrint, new PageFormat());
/* 167:155 */       printerJob.setPageable(book);
/* 168:156 */       if (!printerJob.printDialog()) {
/* 169:158 */         return;
/* 170:    */       }
/* 171:    */       try
/* 172:    */       {
/* 173:163 */         printerJob.print();
/* 174:    */       }
/* 175:    */       catch (PrinterException exception)
/* 176:    */       {
/* 177:167 */         System.err.println("Printing error: " + exception);
/* 178:    */       }
/* 179:    */     }
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void adjustmentValueChanged(AdjustmentEvent evt)
/* 183:    */   {
/* 184:175 */     this.delayTime = calculateDelayTimeFromSlider(evt.getValue());
/* 185:    */   }
/* 186:    */   
/* 187:    */   int calculateDelayTimeFromSlider(int value)
/* 188:    */   {
/* 189:    */     int delay;
/* 190:    */     int delay;
/* 191:183 */     if (value <= 10)
/* 192:    */     {
/* 193:185 */       delay = 1500 + (10 - value) * 250;
/* 194:    */     }
/* 195:    */     else
/* 196:    */     {
/* 197:    */       int delay;
/* 198:187 */       if (value < 25) {
/* 199:189 */         delay = 750 + (25 - value) * 50;
/* 200:    */       } else {
/* 201:193 */         delay = (100 - value) * 10;
/* 202:    */       }
/* 203:    */     }
/* 204:195 */     delay /= 20;
/* 205:    */     
/* 206:197 */     return delay;
/* 207:    */   }
/* 208:    */   
/* 209:    */   class WindowCanvas
/* 210:    */     extends Canvas
/* 211:    */     implements DrawGraphics
/* 212:    */   {
/* 213:    */     static final int BASE_THICKNESS = 30;
/* 214:    */     static final int BASE_MARGIN = 15;
/* 215:    */     static final int POLE_THICKNESS = 10;
/* 216:    */     static final int POLE_TOP = 70;
/* 217:    */     static final int MIN_DISK_WIDTH = 25;
/* 218:    */     static final int DISK_THICKNESS = 18;
/* 219:    */     static final int DISK_MOVE_TOP = 40;
/* 220:    */     Font poleLabelFont;
/* 221:    */     Font diskLabelFont;
/* 222:    */     FontMetrics poleLabelFM;
/* 223:    */     FontMetrics diskLabelFM;
/* 224:    */     int poleLabelWidth;
/* 225:    */     int poleLabelHeight;
/* 226:    */     int diskLabelWidth;
/* 227:    */     int diskLabelHeight;
/* 228:220 */     String[] poleLabels = { "1", "2", "3" };
/* 229:221 */     String[] diskLabels = { "a", "b", "c", "d", "e", "f", "g", "h" };
/* 230:    */     int[] poleX;
/* 231:    */     int[] diskRadius;
/* 232:    */     int[] diskY;
/* 233:    */     int[][] posts;
/* 234:    */     SavePrint savePrint;
/* 235:    */     int xSize;
/* 236:    */     int ySize;
/* 237:    */     Image offscreen;
/* 238:    */     
/* 239:    */     public WindowCanvas(int xSize, int ySize, int disks)
/* 240:    */     {
/* 241:232 */       int pos = 0;
/* 242:    */       
/* 243:    */ 
/* 244:235 */       this.posts = new int[3][9];
/* 245:236 */       for (int counter = disks; counter > 0; counter--) {
/* 246:238 */         this.posts[0][(pos++)] = counter;
/* 247:    */       }
/* 248:240 */       this.poleLabelFont = new Font("Serif", 0, 12);
/* 249:241 */       this.poleLabelFM = getFontMetrics(this.poleLabelFont);
/* 250:242 */       this.poleLabelWidth = this.poleLabelFM.stringWidth("0");
/* 251:243 */       this.poleLabelHeight = this.poleLabelFM.getAscent();
/* 252:244 */       this.xSize = xSize;
/* 253:245 */       this.ySize = ySize;
/* 254:246 */       this.diskLabelFont = new Font("SanSerif", 1, 10);
/* 255:247 */       this.diskLabelFM = getFontMetrics(this.diskLabelFont);
/* 256:248 */       this.diskLabelWidth = this.diskLabelFM.stringWidth("m");
/* 257:249 */       this.diskLabelHeight = this.diskLabelFM.getAscent();
/* 258:    */       
/* 259:251 */       setSize(xSize, ySize);
/* 260:    */       
/* 261:253 */       int sep = (xSize - 60 - 30) / 6;
/* 262:    */       
/* 263:255 */       this.poleX = new int[3];
/* 264:256 */       this.poleX[0] = (30 + sep);
/* 265:257 */       this.poleX[1] = (this.poleX[0] + 10 + 2 * sep);
/* 266:258 */       this.poleX[2] = (this.poleX[1] + 10 + 2 * sep);
/* 267:    */       
/* 268:260 */       int diskDiff = (sep - 5 - 25) / 7;
/* 269:261 */       this.diskRadius = new int[8];
/* 270:262 */       this.diskRadius[0] = 30;
/* 271:263 */       for (int disk = 1; disk < 8; disk++) {
/* 272:265 */         this.diskRadius[disk] = (this.diskRadius[(disk - 1)] + diskDiff);
/* 273:    */       }
/* 274:267 */       this.diskY = new int[8];
/* 275:268 */       this.diskY[0] = (ySize - 15 - 30 - 18 - 3);
/* 276:269 */       for (int disk = 1; disk < 8; disk++) {
/* 277:271 */         this.diskY[disk] = (this.diskY[(disk - 1)] - 18 - 3);
/* 278:    */       }
/* 279:274 */       this.savePrint = new SavePrint(this, this, xSize, ySize);
/* 280:    */     }
/* 281:    */     
/* 282:    */     public void addNotify()
/* 283:    */     {
/* 284:280 */       super.addNotify();
/* 285:281 */       this.offscreen = createImage(this.xSize, this.ySize);
/* 286:282 */       drawBackgroundToOffscreen();
/* 287:    */     }
/* 288:    */     
/* 289:    */     public void paint(Graphics g)
/* 290:    */     {
/* 291:288 */       update(g);
/* 292:    */     }
/* 293:    */     
/* 294:    */     public void update(Graphics g)
/* 295:    */     {
/* 296:295 */       Rectangle r = g.getClipBounds();
/* 297:296 */       g.drawImage(this.offscreen, r.x, r.y, r.x + r.width, 
/* 298:297 */         r.y + r.width, r.x, r.y, r.x + r.width, 
/* 299:298 */         r.y + r.width, null, null);
/* 300:    */     }
/* 301:    */     
/* 302:    */     public void moveTop(int fromPost, int toPost)
/* 303:    */     {
/* 304:306 */       Graphics g = this.offscreen.getGraphics();
/* 305:    */       
/* 306:    */ 
/* 307:309 */       int pos = 8;
/* 308:310 */       while (this.posts[fromPost][pos] == 0) {
/* 309:312 */         pos--;
/* 310:    */       }
/* 311:314 */       int fromPosition = pos;
/* 312:315 */       int diskNo = this.posts[fromPost][pos];
/* 313:316 */       pos = 8;
/* 314:317 */       while ((pos >= 0) && (this.posts[toPost][pos] == 0)) {
/* 315:319 */         pos--;
/* 316:    */       }
/* 317:321 */       int toPosition = pos + 1;
/* 318:    */       
/* 319:323 */       this.posts[fromPost][fromPosition] = 0;
/* 320:325 */       for (int y = this.diskY[fromPosition]; y > 40; y -= 3)
/* 321:    */       {
/* 322:327 */         drawBackgroundToOffscreen();
/* 323:328 */         drawDisk(diskNo, this.poleX[fromPost], y, g);
/* 324:329 */         repaint();
/* 325:330 */         delay();
/* 326:    */       }
/* 327:332 */       if (fromPost < toPost) {
/* 328:334 */         for (int x = this.poleX[fromPost]; x < this.poleX[toPost]; x += 3)
/* 329:    */         {
/* 330:336 */           drawBackgroundToOffscreen();
/* 331:337 */           drawDisk(diskNo, x, 40, g);
/* 332:338 */           repaint();
/* 333:339 */           delay();
/* 334:    */         }
/* 335:    */       } else {
/* 336:344 */         for (int x = this.poleX[fromPost]; x > this.poleX[toPost]; x -= 3)
/* 337:    */         {
/* 338:346 */           drawBackgroundToOffscreen();
/* 339:347 */           drawDisk(diskNo, x, 40, g);
/* 340:348 */           repaint();
/* 341:349 */           delay();
/* 342:    */         }
/* 343:    */       }
/* 344:352 */       for (int y = 40; y < this.diskY[toPosition]; y += 3)
/* 345:    */       {
/* 346:354 */         drawBackgroundToOffscreen();
/* 347:355 */         drawDisk(diskNo, this.poleX[toPost], y, g);
/* 348:356 */         repaint();
/* 349:357 */         delay();
/* 350:    */       }
/* 351:359 */       this.posts[toPost][toPosition] = diskNo;
/* 352:    */     }
/* 353:    */     
/* 354:    */     public void drawWindowToGraphics(Graphics g)
/* 355:    */     {
/* 356:367 */       g.drawImage(this.offscreen, 0, 0, null);
/* 357:    */     }
/* 358:    */     
/* 359:    */     public void drawWindowToGraphics(Graphics g, int width, int height)
/* 360:    */     {
/* 361:373 */       g.drawImage(this.offscreen, 0, 0, null);
/* 362:    */     }
/* 363:    */     
/* 364:    */     public int getMargin()
/* 365:    */     {
/* 366:379 */       return 0;
/* 367:    */     }
/* 368:    */     
/* 369:    */     private void delay()
/* 370:    */     {
/* 371:    */       try
/* 372:    */       {
/* 373:387 */         Thread.sleep(VisibleHanoi.this.delayTime);
/* 374:    */       }
/* 375:    */       catch (InterruptedException localInterruptedException) {}
/* 376:    */     }
/* 377:    */     
/* 378:    */     private void drawDisk(int diskNo, int x, int y, Graphics g)
/* 379:    */     {
/* 380:400 */       int radius = this.diskRadius[(diskNo - 1)];
/* 381:    */       
/* 382:    */ 
/* 383:403 */       g.setColor(Color.green);
/* 384:404 */       g.fillRoundRect(x + 5 - radius, y, 
/* 385:405 */         2 * radius, 18, 10, 10);
/* 386:    */       
/* 387:407 */       g.setColor(Color.black);
/* 388:408 */       g.drawRoundRect(x + 5 - radius, y, 
/* 389:409 */         2 * radius, 18, 10, 10);
/* 390:    */       
/* 391:411 */       g.setColor(Color.black);
/* 392:412 */       g.setFont(this.diskLabelFont);
/* 393:413 */       g.drawString(this.diskLabels[(diskNo - 1)], 
/* 394:414 */         x + (10 - this.diskLabelFM.stringWidth(this.diskLabels[(diskNo - 1)])) / 2, 
/* 395:415 */         y + 18 - (18 - this.diskLabelHeight) / 2);
/* 396:    */     }
/* 397:    */     
/* 398:    */     private void drawBackgroundToOffscreen()
/* 399:    */     {
/* 400:421 */       Graphics g = this.offscreen.getGraphics();
/* 401:    */       
/* 402:423 */       g.setColor(Color.white);
/* 403:424 */       g.fillRect(0, 0, this.xSize, this.ySize);
/* 404:425 */       g.setColor(Color.red);
/* 405:426 */       for (int post = 0; post < 3; post++) {
/* 406:428 */         g.fillRoundRect(this.poleX[post], 70, 10, 
/* 407:429 */           this.ySize - 70 - 15, 10, 10);
/* 408:    */       }
/* 409:433 */       g.setColor(Color.darkGray);
/* 410:434 */       g.fillRoundRect(15, 
/* 411:435 */         this.ySize - 15 - 30, 
/* 412:436 */         this.xSize - 30, 30, 10, 10);
/* 413:    */       
/* 414:    */ 
/* 415:439 */       g.setFont(this.poleLabelFont);
/* 416:440 */       g.setColor(Color.white);
/* 417:441 */       for (int pole = 0; pole < 3; pole++) {
/* 418:443 */         g.drawString(this.poleLabels[pole], 
/* 419:444 */           this.poleX[pole] + (10 - this.poleLabelWidth) / 2, 
/* 420:445 */           this.ySize - 15 - (30 - this.poleLabelWidth) / 2);
/* 421:    */       }
/* 422:449 */       for (int post = 0; post < 3; post++)
/* 423:    */       {
/* 424:451 */         int pos = 0;
/* 425:452 */         while (this.posts[post][pos] != 0)
/* 426:    */         {
/* 427:454 */           drawDisk(this.posts[post][pos], 
/* 428:455 */             this.poleX[post], this.diskY[pos], g);
/* 429:456 */           pos++;
/* 430:    */         }
/* 431:    */       }
/* 432:    */     }
/* 433:    */   }
/* 434:    */   
/* 435:    */   class WindowCloser
/* 436:    */     extends WindowAdapter
/* 437:    */   {
/* 438:    */     WindowCloser() {}
/* 439:    */     
/* 440:    */     public void windowClosing(WindowEvent e)
/* 441:    */     {
/* 442:468 */       e.getWindow().dispose();
/* 443:469 */       System.exit(0);
/* 444:    */     }
/* 445:    */   }
/* 446:    */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.VisibleHanoi
 * JD-Core Version:    0.7.0.1
 */