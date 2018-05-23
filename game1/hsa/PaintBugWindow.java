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
/*  13:    */ import java.awt.Graphics2D;
/*  14:    */ import java.awt.GridBagConstraints;
/*  15:    */ import java.awt.GridBagLayout;
/*  16:    */ import java.awt.Image;
/*  17:    */ import java.awt.Insets;
/*  18:    */ import java.awt.Label;
/*  19:    */ import java.awt.Panel;
/*  20:    */ import java.awt.Rectangle;
/*  21:    */ import java.awt.Scrollbar;
/*  22:    */ import java.awt.Toolkit;
/*  23:    */ import java.awt.Window;
/*  24:    */ import java.awt.event.ActionEvent;
/*  25:    */ import java.awt.event.ActionListener;
/*  26:    */ import java.awt.event.AdjustmentEvent;
/*  27:    */ import java.awt.event.AdjustmentListener;
/*  28:    */ import java.awt.event.WindowAdapter;
/*  29:    */ import java.awt.event.WindowEvent;
/*  30:    */ import java.awt.print.Book;
/*  31:    */ import java.awt.print.PageFormat;
/*  32:    */ import java.awt.print.PrinterException;
/*  33:    */ import java.awt.print.PrinterJob;
/*  34:    */ import java.io.DataOutputStream;
/*  35:    */ import java.io.PrintStream;
/*  36:    */ 
/*  37:    */ class PaintBugWindow
/*  38:    */   extends Frame
/*  39:    */   implements ActionListener, AdjustmentListener
/*  40:    */ {
/*  41:    */   static final int DEFAULT_XSIZE = 400;
/*  42:    */   static final int DEFAULT_YSIZE = 300;
/*  43:    */   static final int INIT_SLIDER_POS = 50;
/*  44:    */   static final int MAX_PAINTBUGS = 1000;
/*  45:    */   int xSize;
/*  46:    */   int ySize;
/*  47: 24 */   int delayTime = calculateDelayTimeFromSlider(50);
/*  48:    */   WindowCanvas canvas;
/*  49:    */   Button saveButton;
/*  50:    */   Button printButton;
/*  51:    */   Scrollbar scrollBar;
/*  52:    */   
/*  53:    */   public PaintBugWindow(boolean drawGrid)
/*  54:    */   {
/*  55: 33 */     this(400, 300, drawGrid);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public PaintBugWindow(int newXSize, int newYSize, boolean drawGrid)
/*  59:    */   {
/*  60: 39 */     super("Paint Bugs");
/*  61:    */     
/*  62: 41 */     this.xSize = newXSize;
/*  63: 42 */     this.ySize = newYSize;
/*  64: 43 */     this.canvas = new WindowCanvas(this.xSize, this.ySize, drawGrid);
/*  65:    */     
/*  66:    */ 
/*  67: 46 */     addWindowListener(new WindowCloser());
/*  68:    */     
/*  69: 48 */     this.saveButton = new Button("Save");
/*  70: 49 */     this.printButton = new Button("Print");
/*  71: 50 */     this.scrollBar = 
/*  72: 51 */       new Scrollbar(0, 50, 0, 0, 100);
/*  73:    */     
/*  74:    */ 
/*  75: 54 */     this.saveButton.addActionListener(this);
/*  76: 55 */     this.printButton.addActionListener(this);
/*  77:    */     
/*  78:    */ 
/*  79: 58 */     this.scrollBar.addAdjustmentListener(this);
/*  80:    */     
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84: 63 */     Panel scrollPanel = new Panel();
/*  85: 64 */     Label slower = new Label("Slower", 2);
/*  86: 65 */     Label faster = new Label("Faster", 0);
/*  87: 66 */     Label setDelay = new Label("Set Speed", 1);
/*  88: 67 */     Font labelFont = new Font("SansSerif", 0, 10);
/*  89: 68 */     slower.setFont(labelFont);
/*  90: 69 */     faster.setFont(labelFont);
/*  91: 70 */     setDelay.setFont(labelFont);
/*  92: 71 */     scrollPanel.setLayout(new BorderLayout());
/*  93: 72 */     scrollPanel.add(slower, "West");
/*  94: 73 */     scrollPanel.add(faster, "East");
/*  95: 74 */     scrollPanel.add(setDelay, "North");
/*  96: 75 */     scrollPanel.add(this.scrollBar, "Center");
/*  97:    */     
/*  98:    */ 
/*  99: 78 */     Panel controlPanel = new Panel();
/* 100: 79 */     GridBagLayout gb = new GridBagLayout();
/* 101: 80 */     GridBagConstraints cn = new GridBagConstraints();
/* 102: 81 */     Label label1 = new Label("   ");
/* 103: 82 */     Label label2 = new Label("   ");
/* 104: 83 */     controlPanel.setBackground(Color.lightGray);
/* 105: 84 */     controlPanel.setLayout(gb);
/* 106:    */     
/* 107:    */ 
/* 108:    */ 
/* 109: 88 */     controlPanel.add(label1);
/* 110: 89 */     controlPanel.add(this.saveButton);
/* 111: 90 */     controlPanel.add(this.printButton);
/* 112: 91 */     controlPanel.add(scrollPanel);
/* 113: 92 */     controlPanel.add(label2);
/* 114:    */     
/* 115:    */ 
/* 116: 95 */     cn.insets = new Insets(4, 4, 4, 4);
/* 117: 96 */     gb.setConstraints(this.saveButton, cn);
/* 118: 97 */     gb.setConstraints(this.printButton, cn);
/* 119:    */     
/* 120:    */ 
/* 121:    */ 
/* 122:101 */     cn.insets = new Insets(0, 4, 4, 4);
/* 123:102 */     cn.fill = 2;
/* 124:103 */     cn.weightx = 1.0D;
/* 125:104 */     gb.setConstraints(scrollPanel, cn);
/* 126:    */     
/* 127:    */ 
/* 128:107 */     add(controlPanel, "North");
/* 129:    */     
/* 130:109 */     add(this.canvas, "South");
/* 131:    */     
/* 132:111 */     pack();
/* 133:    */     
/* 134:    */ 
/* 135:114 */     Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
/* 136:115 */     setLocation(screen.width - getWidth(), 0);
/* 137:    */     
/* 138:117 */     show();
/* 139:    */   }
/* 140:    */   
/* 141:    */   public int getXSize()
/* 142:    */   {
/* 143:123 */     return this.xSize;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public int getYSize()
/* 147:    */   {
/* 148:129 */     return this.ySize;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setSpeed(int speed)
/* 152:    */   {
/* 153:135 */     int value = Math.min(Math.max(speed, 0), 100);
/* 154:136 */     this.scrollBar.setValue(value);
/* 155:137 */     this.delayTime = calculateDelayTimeFromSlider(value);
/* 156:    */   }
/* 157:    */   
/* 158:    */   public Thread moveBug(PaintBug bug, double xPos, double yPos, double direction, double distance, Color color, int trailWidth, boolean trailVisible, String label)
/* 159:    */   {
/* 160:148 */     PaintBugThread t = new PaintBugThread(bug, this.canvas, xPos, yPos, 
/* 161:149 */       direction, distance, color, trailWidth, trailVisible);
/* 162:150 */     t.start();
/* 163:151 */     return t;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public Thread rotateBug(PaintBug bug, double xPos, double yPos, double direction, double changeAngle, Color color)
/* 167:    */   {
/* 168:161 */     PaintBugThread t = new PaintBugThread(bug, this.canvas, xPos, yPos, 
/* 169:162 */       direction, changeAngle, color);
/* 170:163 */     t.start();
/* 171:164 */     return t;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void showBug(PaintBug bug, double xPos, double yPos, double direction, Color color, String label)
/* 175:    */   {
/* 176:171 */     this.canvas.showBug(bug, xPos, yPos, direction, color, label);
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void hideBug(PaintBug bug, double xPos, double yPos, double direction, Color color, String label)
/* 180:    */   {
/* 181:178 */     this.canvas.hideBug(bug, xPos, yPos, direction, color, label);
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void actionPerformed(ActionEvent evt)
/* 185:    */   {
/* 186:184 */     if (evt.getSource() == this.saveButton)
/* 187:    */     {
/* 188:187 */       FileDialog fd = new FileDialog(this, "Save PaintBug Window", 
/* 189:188 */         1);
/* 190:189 */       fd.setFile("PaintBug.bmp");
/* 191:190 */       fd.show();
/* 192:192 */       if (fd.getFile() == null) {
/* 193:194 */         return;
/* 194:    */       }
/* 195:197 */       String fileName = fd.getDirectory() + fd.getFile();
/* 196:199 */       if (fileName.indexOf(".*.*") != -1) {
/* 197:201 */         fileName = fileName.substring(0, fileName.length() - 4);
/* 198:    */       }
/* 199:205 */       this.canvas.savePrint.saveToFile(fileName);
/* 200:    */     }
/* 201:207 */     else if (evt.getSource() == this.printButton)
/* 202:    */     {
/* 203:209 */       PrinterJob printerJob = PrinterJob.getPrinterJob();
/* 204:210 */       Book book = new Book();
/* 205:211 */       book.append(this.canvas.savePrint, new PageFormat());
/* 206:212 */       printerJob.setPageable(book);
/* 207:213 */       if (!printerJob.printDialog()) {
/* 208:215 */         return;
/* 209:    */       }
/* 210:    */       try
/* 211:    */       {
/* 212:220 */         printerJob.print();
/* 213:    */       }
/* 214:    */       catch (PrinterException exception)
/* 215:    */       {
/* 216:224 */         System.err.println("Printing error: " + exception);
/* 217:    */       }
/* 218:    */     }
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void adjustmentValueChanged(AdjustmentEvent evt)
/* 222:    */   {
/* 223:232 */     this.delayTime = calculateDelayTimeFromSlider(evt.getValue());
/* 224:    */   }
/* 225:    */   
/* 226:    */   int calculateDelayTimeFromSlider(int value)
/* 227:    */   {
/* 228:    */     int delay;
/* 229:    */     int delay;
/* 230:240 */     if (value <= 10)
/* 231:    */     {
/* 232:242 */       delay = 1500 + (10 - value) * 250;
/* 233:    */     }
/* 234:    */     else
/* 235:    */     {
/* 236:    */       int delay;
/* 237:244 */       if (value < 25) {
/* 238:246 */         delay = 750 + (25 - value) * 50;
/* 239:    */       } else {
/* 240:250 */         delay = (100 - value) * 10;
/* 241:    */       }
/* 242:    */     }
/* 243:252 */     delay /= 20;
/* 244:    */     
/* 245:254 */     return delay;
/* 246:    */   }
/* 247:    */   
/* 248:    */   class WindowCanvas
/* 249:    */     extends Canvas
/* 250:    */     implements DrawGraphics
/* 251:    */   {
/* 252:    */     static final int LEFT_TOP_MARGIN = 5;
/* 253:    */     static final int RIGHT_BOTTOM_MARGIN = 15;
/* 254:    */     static final int TEXT_MARGIN = 5;
/* 255:    */     Font bugLabelFont;
/* 256:    */     Font gridLabelFont;
/* 257:    */     FontMetrics bugLabelFM;
/* 258:    */     FontMetrics gridLabelFM;
/* 259:    */     boolean drawGrid;
/* 260:    */     int textWidth;
/* 261:    */     int textHeight;
/* 262:    */     int labelWidth;
/* 263:    */     int labelHeight;
/* 264:    */     int xSize;
/* 265:    */     int ySize;
/* 266:    */     int canvasWidth;
/* 267:    */     int canvasHeight;
/* 268:    */     int left;
/* 269:    */     int top;
/* 270:    */     int textLeft;
/* 271:    */     int textTop;
/* 272:    */     Image offscreenTrails;
/* 273:    */     Image offscreen;
/* 274:    */     SavePrint savePrint;
/* 275:286 */     PaintBug[] bugs = new PaintBug[1000];
/* 276:287 */     double[] bugX = new double[1000];
/* 277:288 */     double[] bugY = new double[1000];
/* 278:289 */     double[] bugDirection = new double[1000];
/* 279:290 */     Color[] bugColor = new Color[1000];
/* 280:291 */     String[] bugLabel = new String[1000];
/* 281:293 */     int numBugs = 0;
/* 282:    */     static final int ROWS_GRABBED_AT_A_TIME = 75;
/* 283:    */     Status status;
/* 284:    */     static final int BI_RGB = 0;
/* 285:    */     static final int BI_COMPRESSED_RLE8 = 1;
/* 286:    */     static final int BI_COMPRESSED_RLE4 = 2;
/* 287:    */     DataOutputStream out;
/* 288:    */     
/* 289:    */     public WindowCanvas(int xSize, int ySize, boolean drawGrid)
/* 290:    */     {
/* 291:307 */       this.gridLabelFont = new Font("Serif", 0, 12);
/* 292:308 */       this.gridLabelFM = getFontMetrics(this.gridLabelFont);
/* 293:309 */       this.textWidth = this.gridLabelFM.stringWidth("0000");
/* 294:310 */       this.textHeight = this.gridLabelFM.getAscent();
/* 295:311 */       this.xSize = xSize;
/* 296:312 */       this.ySize = ySize;
/* 297:313 */       this.drawGrid = drawGrid;
/* 298:314 */       this.bugLabelFont = new Font("SanSerif", 1, 10);
/* 299:315 */       this.bugLabelFM = getFontMetrics(this.bugLabelFont);
/* 300:316 */       this.labelHeight = this.bugLabelFM.getAscent();
/* 301:317 */       if (drawGrid)
/* 302:    */       {
/* 303:319 */         this.textLeft = (5 + this.textWidth);
/* 304:320 */         this.left = (10 + this.textWidth);
/* 305:321 */         this.canvasWidth = (this.left + xSize + 15);
/* 306:322 */         this.textTop = (5 + this.textHeight);
/* 307:323 */         this.top = (10 + this.textHeight);
/* 308:324 */         this.canvasHeight = (this.top + ySize + 15);
/* 309:    */       }
/* 310:    */       else
/* 311:    */       {
/* 312:328 */         this.left = 5;
/* 313:329 */         this.canvasWidth = (this.left + xSize + 5);
/* 314:330 */         this.top = 5;
/* 315:331 */         this.canvasHeight = (this.top + ySize + 5);
/* 316:    */       }
/* 317:333 */       setSize(this.canvasWidth, this.canvasHeight);
/* 318:    */       
/* 319:335 */       this.savePrint = new SavePrint(this, this, this.canvasWidth, this.canvasHeight);
/* 320:    */     }
/* 321:    */     
/* 322:    */     public void paint(Graphics g)
/* 323:    */     {
/* 324:341 */       update(g);
/* 325:    */     }
/* 326:    */     
/* 327:    */     public void update(Graphics g)
/* 328:    */     {
/* 329:348 */       Rectangle r = g.getClipBounds();
/* 330:349 */       Graphics offscreenG = this.offscreen.getGraphics();
/* 331:350 */       offscreenG.drawImage(this.offscreenTrails, r.x, r.y, r.x + r.width, 
/* 332:351 */         r.y + r.width, r.x, r.y, r.x + r.width, 
/* 333:352 */         r.y + r.width, null, null);
/* 334:355 */       for (int cnt = 0; cnt < this.numBugs; cnt++) {
/* 335:357 */         drawOneBug(offscreenG, this.left + this.bugX[cnt], this.top + this.bugY[cnt], 
/* 336:358 */           this.bugDirection[cnt], this.bugColor[cnt], 
/* 337:359 */           this.bugLabel[cnt]);
/* 338:    */       }
/* 339:363 */       g.drawImage(this.offscreen, r.x, r.y, r.x + r.width, 
/* 340:364 */         r.y + r.width, r.x, r.y, r.x + r.width, 
/* 341:365 */         r.y + r.width, null, null);
/* 342:    */     }
/* 343:    */     
/* 344:    */     public void drawWindowToGraphics(Graphics g)
/* 345:    */     {
/* 346:374 */       g.drawImage(this.offscreenTrails, 0, 0, null);
/* 347:377 */       for (int cnt = 0; cnt < this.numBugs; cnt++) {
/* 348:379 */         drawOneBug(g, this.left + this.bugX[cnt], this.top + this.bugY[cnt], 
/* 349:380 */           this.bugDirection[cnt], this.bugColor[cnt], 
/* 350:381 */           this.bugLabel[cnt]);
/* 351:    */       }
/* 352:    */     }
/* 353:    */     
/* 354:    */     public void drawWindowToGraphics(Graphics g, int width, int height) {}
/* 355:    */     
/* 356:    */     public int getMargin()
/* 357:    */     {
/* 358:394 */       return 0;
/* 359:    */     }
/* 360:    */     
/* 361:    */     private void drawOneBug(Graphics g, double x, double y, double direction, Color bugColor, String label)
/* 362:    */     {
/* 363:403 */       int ix = (int)Math.round(x);
/* 364:404 */       int iy = (int)Math.round(y);
/* 365:    */       
/* 366:406 */       g.setColor(bugColor);
/* 367:409 */       if (!label.equals(""))
/* 368:    */       {
/* 369:411 */         g.setFont(this.bugLabelFont);
/* 370:412 */         g.drawString(label, (int)Math.round(x + 10.0D), 
/* 371:413 */           (int)Math.round(y - 10.0D));
/* 372:    */       }
/* 373:416 */       Graphics2D g2d = (Graphics2D)g.create();
/* 374:417 */       g2d.rotate(Math.toRadians(-direction), x, y);
/* 375:    */       
/* 376:419 */       g2d.fillOval(ix - 8, iy - 4, 16, 8);
/* 377:420 */       g2d.setColor(Color.black);
/* 378:421 */       g2d.drawOval(ix - 8, iy - 4, 15, 7);
/* 379:    */       
/* 380:423 */       g2d.setColor(Color.black);
/* 381:424 */       g2d.drawArc(ix + 4, iy - 4, 8, 4, 30, 110);
/* 382:425 */       g2d.drawArc(ix + 5, iy, 8, 4, 225, 90);
/* 383:    */     }
/* 384:    */     
/* 385:    */     public void hideBug(PaintBug bug, double xPos, double yPos, double direction, Color color, String label)
/* 386:    */     {
/* 387:434 */       for (int cnt = 0; cnt < this.numBugs; cnt++) {
/* 388:436 */         if (bug == this.bugs[cnt])
/* 389:    */         {
/* 390:439 */           for (int cnt1 = cnt + 1; cnt1 < this.numBugs; cnt1++)
/* 391:    */           {
/* 392:441 */             this.bugs[(cnt1 - 1)] = this.bugs[cnt1];
/* 393:442 */             this.bugX[(cnt1 - 1)] = this.bugX[cnt1];
/* 394:443 */             this.bugY[(cnt1 - 1)] = this.bugY[cnt1];
/* 395:444 */             this.bugDirection[(cnt1 - 1)] = this.bugDirection[cnt1];
/* 396:445 */             this.bugColor[(cnt1 - 1)] = this.bugColor[cnt1];
/* 397:446 */             this.bugLabel[(cnt1 - 1)] = this.bugLabel[cnt1];
/* 398:    */           }
/* 399:449 */           this.numBugs -= 1;
/* 400:450 */           break;
/* 401:    */         }
/* 402:    */       }
/* 403:    */     }
/* 404:    */     
/* 405:    */     public void showBug(PaintBug bug, double xPos, double yPos, double direction, Color color, String label)
/* 406:    */     {
/* 407:459 */       this.bugs[this.numBugs] = bug;
/* 408:460 */       this.bugX[this.numBugs] = xPos;
/* 409:461 */       this.bugY[this.numBugs] = yPos;
/* 410:462 */       this.bugDirection[this.numBugs] = direction;
/* 411:463 */       this.bugColor[this.numBugs] = color;
/* 412:464 */       this.bugLabel[this.numBugs] = label;
/* 413:    */       
/* 414:466 */       this.numBugs += 1;
/* 415:    */       
/* 416:468 */       repaint();
/* 417:    */     }
/* 418:    */     
/* 419:    */     public void moveBug(PaintBug bug, double xPos, double yPos, double direction)
/* 420:    */     {
/* 421:476 */       for (int cnt = 0; cnt < this.numBugs; cnt++) {
/* 422:478 */         if (bug == this.bugs[cnt])
/* 423:    */         {
/* 424:480 */           this.bugX[cnt] = xPos;
/* 425:481 */           this.bugY[cnt] = yPos;
/* 426:482 */           this.bugDirection[cnt] = direction;
/* 427:483 */           break;
/* 428:    */         }
/* 429:    */       }
/* 430:    */     }
/* 431:    */     
/* 432:    */     public void drawBugTrail(double xPos, double yPos, int trailWidth, Color color)
/* 433:    */     {
/* 434:492 */       Graphics g = this.offscreenTrails.getGraphics();
/* 435:493 */       g.setColor(color);
/* 436:    */       
/* 437:495 */       g.fillOval(this.left + (int)Math.round(xPos - trailWidth / 2.0D), 
/* 438:496 */         this.top + (int)Math.round(yPos - trailWidth / 2.0D), 
/* 439:497 */         trailWidth, trailWidth);
/* 440:    */     }
/* 441:    */     
/* 442:    */     public void addNotify()
/* 443:    */     {
/* 444:503 */       super.addNotify();
/* 445:    */       
/* 446:505 */       this.offscreen = createImage(this.canvasWidth, this.canvasHeight);
/* 447:506 */       this.offscreenTrails = createImage(this.canvasWidth, this.canvasHeight);
/* 448:    */       
/* 449:508 */       int right = this.left + (this.xSize - 1);
/* 450:509 */       int bottom = this.top + (this.ySize - 1);
/* 451:510 */       if (this.drawGrid)
/* 452:    */       {
/* 453:512 */         Graphics g = this.offscreenTrails.getGraphics();
/* 454:    */         
/* 455:514 */         g.setFont(this.gridLabelFont);
/* 456:515 */         g.setColor(Color.gray);
/* 457:518 */         for (int y = 0; y <= this.ySize; y += 25)
/* 458:    */         {
/* 459:520 */           String number = y;
/* 460:    */           
/* 461:522 */           g.drawString(number, 
/* 462:523 */             this.textLeft - this.gridLabelFM.stringWidth(number), 
/* 463:524 */             y + this.top + this.textHeight / 2);
/* 464:525 */           g.drawLine(this.left, y + this.top, right, y + this.top);
/* 465:    */         }
/* 466:529 */         for (int x = 0; x <= this.xSize; x += 25)
/* 467:    */         {
/* 468:531 */           String number = x;
/* 469:532 */           g.drawString(number, 
/* 470:533 */             x + this.left - this.gridLabelFM.stringWidth(number) / 2, 
/* 471:534 */             this.textTop);
/* 472:535 */           g.drawLine(x + this.left, this.top, x + this.left, bottom);
/* 473:    */         }
/* 474:    */       }
/* 475:    */     }
/* 476:    */   }
/* 477:    */   
/* 478:    */   class PaintBugThread
/* 479:    */     extends Thread
/* 480:    */   {
/* 481:    */     int commandKind;
/* 482:    */     PaintBug bug;
/* 483:    */     PaintBugWindow.WindowCanvas canvas;
/* 484:    */     double xPos;
/* 485:    */     double yPos;
/* 486:    */     double direction;
/* 487:    */     double distance;
/* 488:    */     double changeAngle;
/* 489:    */     Color color;
/* 490:    */     int trailWidth;
/* 491:    */     boolean trailVisible;
/* 492:    */     
/* 493:    */     public PaintBugThread(PaintBug bug, PaintBugWindow.WindowCanvas canvas, double xPos, double yPos, double direction, double distance, Color color, int trailWidth, boolean trailVisible)
/* 494:    */     {
/* 495:556 */       this.commandKind = 1;
/* 496:557 */       this.bug = bug;
/* 497:558 */       this.canvas = canvas;
/* 498:559 */       this.xPos = xPos;
/* 499:560 */       this.yPos = yPos;
/* 500:561 */       this.direction = direction;
/* 501:562 */       this.distance = distance;
/* 502:563 */       this.color = color;
/* 503:564 */       this.trailWidth = trailWidth;
/* 504:565 */       this.trailVisible = trailVisible;
/* 505:    */     }
/* 506:    */     
/* 507:    */     public PaintBugThread(PaintBug bug, PaintBugWindow.WindowCanvas canvas, double xPos, double yPos, double direction, double changeAngle, Color color)
/* 508:    */     {
/* 509:573 */       this.commandKind = 2;
/* 510:574 */       this.bug = bug;
/* 511:575 */       this.canvas = canvas;
/* 512:576 */       this.xPos = xPos;
/* 513:577 */       this.yPos = yPos;
/* 514:578 */       this.direction = direction;
/* 515:579 */       this.changeAngle = changeAngle;
/* 516:580 */       this.color = color;
/* 517:    */     }
/* 518:    */     
/* 519:    */     public void run()
/* 520:    */     {
/* 521:586 */       if (this.commandKind == 1)
/* 522:    */       {
/* 523:588 */         int distanceTravelled = 0;
/* 524:590 */         while (distanceTravelled < this.distance)
/* 525:    */         {
/* 526:592 */           double distStep = 
/* 527:593 */             Math.min(1.0D, this.distance - distanceTravelled);
/* 528:    */           
/* 529:595 */           this.xPos += distStep * Math.cos(Math.toRadians(this.direction));
/* 530:596 */           this.yPos -= distStep * Math.sin(Math.toRadians(this.direction));
/* 531:597 */           if (this.trailVisible) {
/* 532:599 */             this.canvas.drawBugTrail(this.xPos, this.yPos, this.trailWidth, this.color);
/* 533:    */           }
/* 534:601 */           this.canvas.moveBug(this.bug, this.xPos, this.yPos, this.direction);
/* 535:602 */           if (this.bug.label.length() > 0)
/* 536:    */           {
/* 537:604 */             int above = Math.max(15, 10 + this.canvas.labelHeight + 3);
/* 538:605 */             int right = 25 + Math.max(5, this.canvas.bugLabelFM.stringWidth(this.bug.label) + 3);
/* 539:606 */             this.canvas.repaint(
/* 540:607 */               this.canvas.left + (int)Math.round(this.xPos) - 15, 
/* 541:608 */               this.canvas.top + (int)Math.round(this.yPos) - above, 
/* 542:609 */               right, 15 + above);
/* 543:    */           }
/* 544:    */           else
/* 545:    */           {
/* 546:613 */             this.canvas.repaint(
/* 547:614 */               this.canvas.left + (int)Math.round(this.xPos) - 15, 
/* 548:615 */               this.canvas.top + (int)Math.round(this.yPos) - 15, 
/* 549:616 */               30, 30);
/* 550:    */           }
/* 551:    */           try
/* 552:    */           {
/* 553:620 */             Thread.sleep(PaintBugWindow.this.delayTime);
/* 554:    */           }
/* 555:    */           catch (InterruptedException localInterruptedException) {}
/* 556:626 */           distanceTravelled++;
/* 557:    */         }
/* 558:    */       }
/* 559:629 */       else if (this.commandKind == 2)
/* 560:    */       {
/* 561:631 */         int sign = 1;
/* 562:632 */         if (this.changeAngle < 0.0D) {
/* 563:634 */           sign = -1;
/* 564:    */         }
/* 565:636 */         while (Math.abs(this.changeAngle) > 0.0D)
/* 566:    */         {
/* 567:638 */           double angleStep = Math.min(5.0D, Math.abs(this.changeAngle));
/* 568:639 */           angleStep *= sign;
/* 569:    */           
/* 570:641 */           this.direction += angleStep;
/* 571:642 */           this.canvas.moveBug(this.bug, this.xPos, this.yPos, this.direction);
/* 572:643 */           this.canvas.repaint(this.canvas.left + (int)Math.round(this.xPos) - 20, 
/* 573:644 */             this.canvas.top + (int)Math.round(this.yPos) - 20, 40, 40);
/* 574:    */           try
/* 575:    */           {
/* 576:647 */             Thread.sleep(PaintBugWindow.this.delayTime);
/* 577:    */           }
/* 578:    */           catch (InterruptedException localInterruptedException1) {}
/* 579:653 */           this.changeAngle -= angleStep;
/* 580:    */         }
/* 581:    */       }
/* 582:    */     }
/* 583:    */   }
/* 584:    */   
/* 585:    */   class WindowCloser
/* 586:    */     extends WindowAdapter
/* 587:    */   {
/* 588:    */     WindowCloser() {}
/* 589:    */     
/* 590:    */     public void windowClosing(WindowEvent e)
/* 591:    */     {
/* 592:665 */       e.getWindow().dispose();
/* 593:666 */       System.exit(0);
/* 594:    */     }
/* 595:    */   }
/* 596:    */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.PaintBugWindow
 * JD-Core Version:    0.7.0.1
 */