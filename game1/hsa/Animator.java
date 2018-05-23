/*   1:    */ package game1.hsa;
/*   2:    */ 
/*   3:    */ import java.awt.Button;
/*   4:    */ import java.awt.Canvas;
/*   5:    */ import java.awt.Color;
/*   6:    */ import java.awt.Dimension;
/*   7:    */ import java.awt.FileDialog;
/*   8:    */ import java.awt.Frame;
/*   9:    */ import java.awt.Graphics;
/*  10:    */ import java.awt.GridBagConstraints;
/*  11:    */ import java.awt.GridBagLayout;
/*  12:    */ import java.awt.Image;
/*  13:    */ import java.awt.Insets;
/*  14:    */ import java.awt.Label;
/*  15:    */ import java.awt.MediaTracker;
/*  16:    */ import java.awt.Panel;
/*  17:    */ import java.awt.Rectangle;
/*  18:    */ import java.awt.Toolkit;
/*  19:    */ import java.awt.Window;
/*  20:    */ import java.awt.event.ActionEvent;
/*  21:    */ import java.awt.event.ActionListener;
/*  22:    */ import java.awt.event.KeyEvent;
/*  23:    */ import java.awt.event.KeyListener;
/*  24:    */ import java.awt.event.WindowAdapter;
/*  25:    */ import java.awt.event.WindowEvent;
/*  26:    */ import java.awt.print.Book;
/*  27:    */ import java.awt.print.PageFormat;
/*  28:    */ import java.awt.print.PrinterException;
/*  29:    */ import java.awt.print.PrinterJob;
/*  30:    */ import java.io.PrintStream;
/*  31:    */ import java.util.Vector;
/*  32:    */ 
/*  33:    */ public class Animator
/*  34:    */   extends Frame
/*  35:    */   implements ActionListener, KeyListener
/*  36:    */ {
/*  37:    */   static final int DEFAULT_XSIZE = 400;
/*  38:    */   static final int DEFAULT_YSIZE = 300;
/*  39:    */   private int xSize;
/*  40:    */   private int ySize;
/*  41: 20 */   private long lastTime = 0L;
/*  42: 21 */   private Vector items = new Vector();
/*  43: 22 */   private Vector keysPressed = new Vector();
/*  44:    */   WindowCanvas canvas;
/*  45:    */   Button saveButton;
/*  46:    */   Button printButton;
/*  47:    */   
/*  48:    */   public Animator()
/*  49:    */   {
/*  50: 30 */     this(400, 300);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Animator(int newXSize, int newYSize)
/*  54:    */   {
/*  55: 36 */     super("Animator");
/*  56:    */     
/*  57: 38 */     this.xSize = newXSize;
/*  58: 39 */     this.ySize = newYSize;
/*  59: 40 */     this.canvas = new WindowCanvas(this.xSize, this.ySize);
/*  60:    */     
/*  61:    */ 
/*  62: 43 */     addWindowListener(new WindowCloser());
/*  63:    */     
/*  64: 45 */     addKeyListener(this);
/*  65: 46 */     this.canvas.addKeyListener(this);
/*  66:    */     
/*  67: 48 */     this.saveButton = new Button("Save");
/*  68: 49 */     this.printButton = new Button("Print");
/*  69:    */     
/*  70:    */ 
/*  71: 52 */     this.saveButton.addActionListener(this);
/*  72: 53 */     this.printButton.addActionListener(this);
/*  73:    */     
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77: 58 */     Panel controlPanel = new Panel();
/*  78: 59 */     GridBagLayout gb = new GridBagLayout();
/*  79: 60 */     GridBagConstraints cn = new GridBagConstraints();
/*  80: 61 */     Label label1 = new Label("   ");
/*  81: 62 */     Label label2 = new Label("   ");
/*  82: 63 */     controlPanel.setBackground(Color.lightGray);
/*  83: 64 */     controlPanel.setLayout(gb);
/*  84:    */     
/*  85:    */ 
/*  86:    */ 
/*  87: 68 */     controlPanel.add(label1);
/*  88: 69 */     controlPanel.add(this.saveButton);
/*  89: 70 */     controlPanel.add(this.printButton);
/*  90: 71 */     controlPanel.add(label2);
/*  91:    */     
/*  92:    */ 
/*  93: 74 */     cn.insets = new Insets(4, 4, 4, 4);
/*  94: 75 */     gb.setConstraints(this.saveButton, cn);
/*  95: 76 */     gb.setConstraints(this.printButton, cn);
/*  96:    */     
/*  97:    */ 
/*  98: 79 */     add(controlPanel, "North");
/*  99:    */     
/* 100: 81 */     add(this.canvas, "South");
/* 101:    */     
/* 102: 83 */     pack();
/* 103:    */     
/* 104:    */ 
/* 105: 86 */     Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
/* 106: 87 */     setLocation(screen.width - getSize().width, 0);
/* 107:    */     
/* 108: 89 */     show();
/* 109: 90 */     this.canvas.requestFocus();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public int getXSize()
/* 113:    */   {
/* 114: 96 */     return this.xSize;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public int getYSize()
/* 118:    */   {
/* 119:102 */     return this.ySize;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void add(Moveable item)
/* 123:    */   {
/* 124:108 */     this.items.add(item);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void add(Bounceable item)
/* 128:    */   {
/* 129:114 */     this.items.add(item);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void add(Paintable item)
/* 133:    */   {
/* 134:120 */     this.items.add(item);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void delay(int delayTime)
/* 138:    */   {
/* 139:128 */     long currentTime = System.currentTimeMillis();
/* 140:129 */     if (currentTime - this.lastTime >= delayTime)
/* 141:    */     {
/* 142:131 */       this.lastTime = currentTime;
/* 143:132 */       return;
/* 144:    */     }
/* 145:134 */     delayTime -= (int)(currentTime - this.lastTime);
/* 146:    */     try
/* 147:    */     {
/* 148:137 */       Thread.sleep(delayTime);
/* 149:    */     }
/* 150:    */     catch (InterruptedException localInterruptedException) {}
/* 151:143 */     this.lastTime = System.currentTimeMillis();
/* 152:    */   }
/* 153:    */   
/* 154:    */   private void drawMoveable(Moveable item, Graphics g)
/* 155:    */   {
/* 156:152 */     double x = item.getX();
/* 157:153 */     double y = item.getY();
/* 158:154 */     double direction = item.getDirection();
/* 159:155 */     double speed = item.getSpeed();
/* 160:    */     
/* 161:157 */     x += Math.cos(Math.toRadians(direction)) * speed;
/* 162:158 */     y -= Math.sin(Math.toRadians(direction)) * speed;
/* 163:    */     boolean inBounds;
/* 164:    */     do
/* 165:    */     {
/* 166:162 */       inBounds = true;
/* 167:164 */       if (x < 0.0D)
/* 168:    */       {
/* 169:166 */         x = -x;
/* 170:167 */         if (direction <= 180.0D) {
/* 171:169 */           direction = 180.0D - direction;
/* 172:    */         } else {
/* 173:173 */           direction = 540.0D - direction;
/* 174:    */         }
/* 175:175 */         inBounds = false;
/* 176:    */       }
/* 177:177 */       if (x > this.xSize)
/* 178:    */       {
/* 179:179 */         x = this.xSize - (x - this.xSize);
/* 180:180 */         if (direction <= 180.0D) {
/* 181:182 */           direction = 180.0D - direction;
/* 182:    */         } else {
/* 183:186 */           direction = 540.0D - direction;
/* 184:    */         }
/* 185:188 */         inBounds = false;
/* 186:    */       }
/* 187:190 */       if (y < 0.0D)
/* 188:    */       {
/* 189:192 */         y = -y;
/* 190:193 */         direction = 360.0D - direction;
/* 191:194 */         inBounds = false;
/* 192:    */       }
/* 193:196 */       if (y > this.ySize)
/* 194:    */       {
/* 195:198 */         y = this.ySize - (y - this.ySize);
/* 196:199 */         direction = 360.0D - direction;
/* 197:200 */         inBounds = false;
/* 198:    */       }
/* 199:160 */     } while (!
/* 200:    */     
/* 201:    */ 
/* 202:    */ 
/* 203:    */ 
/* 204:    */ 
/* 205:    */ 
/* 206:    */ 
/* 207:    */ 
/* 208:    */ 
/* 209:    */ 
/* 210:    */ 
/* 211:    */ 
/* 212:    */ 
/* 213:    */ 
/* 214:    */ 
/* 215:    */ 
/* 216:    */ 
/* 217:    */ 
/* 218:    */ 
/* 219:    */ 
/* 220:    */ 
/* 221:    */ 
/* 222:    */ 
/* 223:    */ 
/* 224:    */ 
/* 225:    */ 
/* 226:    */ 
/* 227:    */ 
/* 228:    */ 
/* 229:    */ 
/* 230:    */ 
/* 231:    */ 
/* 232:    */ 
/* 233:    */ 
/* 234:    */ 
/* 235:    */ 
/* 236:    */ 
/* 237:    */ 
/* 238:    */ 
/* 239:    */ 
/* 240:    */ 
/* 241:    */ 
/* 242:203 */       inBounds);
/* 243:204 */     item.setLocation(x, y);
/* 244:205 */     item.setDirection(direction);
/* 245:206 */     item.paint(g, (int)Math.round(x), (int)Math.round(y));
/* 246:    */   }
/* 247:    */   
/* 248:    */   private void drawBounceable(Bounceable item, Graphics g)
/* 249:    */   {
/* 250:215 */     double x = item.getX();
/* 251:216 */     double y = item.getY();
/* 252:217 */     double direction = item.getDirection();
/* 253:218 */     double speed = item.getSpeed();
/* 254:    */     
/* 255:220 */     x += Math.cos(Math.toRadians(direction)) * speed;
/* 256:221 */     y -= Math.sin(Math.toRadians(direction)) * speed;
/* 257:222 */     item.setLocation(x, y);
/* 258:    */     boolean inBounds;
/* 259:    */     do
/* 260:    */     {
/* 261:226 */       inBounds = true;
/* 262:228 */       if (item.getLeft() < 0.0D)
/* 263:    */       {
/* 264:230 */         item.setLocation(
/* 265:231 */           item.getX() - 2.0D * item.getLeft(), 
/* 266:232 */           item.getY());
/* 267:233 */         if (direction <= 180.0D) {
/* 268:235 */           direction = 180.0D - direction;
/* 269:    */         } else {
/* 270:239 */           direction = 540.0D - direction;
/* 271:    */         }
/* 272:241 */         inBounds = false;
/* 273:    */       }
/* 274:243 */       if (item.getRight() > this.xSize)
/* 275:    */       {
/* 276:245 */         item.setLocation(
/* 277:246 */           item.getX() - 2.0D * (item.getRight() - this.xSize), 
/* 278:247 */           item.getY());
/* 279:248 */         if (direction <= 180.0D) {
/* 280:250 */           direction = 180.0D - direction;
/* 281:    */         } else {
/* 282:254 */           direction = 540.0D - direction;
/* 283:    */         }
/* 284:256 */         inBounds = false;
/* 285:    */       }
/* 286:258 */       if (item.getTop() < 0.0D)
/* 287:    */       {
/* 288:260 */         item.setLocation(item.getX(), 
/* 289:261 */           item.getY() - 2.0D * item.getTop());
/* 290:262 */         direction = 360.0D - direction;
/* 291:263 */         inBounds = false;
/* 292:    */       }
/* 293:265 */       if (item.getBottom() > this.ySize)
/* 294:    */       {
/* 295:268 */         item.setLocation(item.getX(), 
/* 296:269 */           item.getY() - 2.0D * (item.getBottom() - this.ySize));
/* 297:270 */         direction = 360.0D - direction;
/* 298:271 */         inBounds = false;
/* 299:    */       }
/* 300:224 */     } while (!
/* 301:    */     
/* 302:    */ 
/* 303:    */ 
/* 304:    */ 
/* 305:    */ 
/* 306:    */ 
/* 307:    */ 
/* 308:    */ 
/* 309:    */ 
/* 310:    */ 
/* 311:    */ 
/* 312:    */ 
/* 313:    */ 
/* 314:    */ 
/* 315:    */ 
/* 316:    */ 
/* 317:    */ 
/* 318:    */ 
/* 319:    */ 
/* 320:    */ 
/* 321:    */ 
/* 322:    */ 
/* 323:    */ 
/* 324:    */ 
/* 325:    */ 
/* 326:    */ 
/* 327:    */ 
/* 328:    */ 
/* 329:    */ 
/* 330:    */ 
/* 331:    */ 
/* 332:    */ 
/* 333:    */ 
/* 334:    */ 
/* 335:    */ 
/* 336:    */ 
/* 337:    */ 
/* 338:    */ 
/* 339:    */ 
/* 340:    */ 
/* 341:    */ 
/* 342:    */ 
/* 343:    */ 
/* 344:    */ 
/* 345:    */ 
/* 346:    */ 
/* 347:    */ 
/* 348:    */ 
/* 349:    */ 
/* 350:274 */       inBounds);
/* 351:275 */     item.setDirection(direction);
/* 352:276 */     item.paint(g, (int)Math.round(x), (int)Math.round(y));
/* 353:    */   }
/* 354:    */   
/* 355:    */   public void drawFrame()
/* 356:    */   {
/* 357:282 */     Graphics g = this.canvas.offscreen.getGraphics();
/* 358:    */     
/* 359:284 */     g.clearRect(0, 0, this.xSize, this.ySize);
/* 360:287 */     for (int counter = 0; counter < this.items.size(); counter++)
/* 361:    */     {
/* 362:289 */       Object item = this.items.elementAt(counter);
/* 363:292 */       if ((item instanceof Moveable))
/* 364:    */       {
/* 365:294 */         drawMoveable((Moveable)item, g);
/* 366:    */       }
/* 367:296 */       else if ((item instanceof Bounceable))
/* 368:    */       {
/* 369:298 */         drawBounceable((Bounceable)item, g);
/* 370:    */       }
/* 371:    */       else
/* 372:    */       {
/* 373:302 */         Paintable paintableItem = (Paintable)item;
/* 374:    */         
/* 375:304 */         paintableItem.paint(g);
/* 376:    */       }
/* 377:    */     }
/* 378:308 */     this.canvas.repaint();
/* 379:    */   }
/* 380:    */   
/* 381:    */   public boolean isKeyPressed(int keyCode)
/* 382:    */   {
/* 383:314 */     for (int i = 0; i < this.keysPressed.size(); i++) {
/* 384:316 */       if (((Integer)this.keysPressed.elementAt(i)).intValue() == keyCode) {
/* 385:318 */         return true;
/* 386:    */       }
/* 387:    */     }
/* 388:322 */     return false;
/* 389:    */   }
/* 390:    */   
/* 391:    */   public Image loadImage(String path)
/* 392:    */   {
/* 393:330 */     Image picture = getToolkit().getImage(path);
/* 394:331 */     prepareImage(picture, this);
/* 395:    */     
/* 396:    */ 
/* 397:    */ 
/* 398:    */ 
/* 399:336 */     MediaTracker tracker = new MediaTracker(this);
/* 400:    */     
/* 401:338 */     tracker.addImage(picture, 0);
/* 402:    */     try
/* 403:    */     {
/* 404:344 */       tracker.waitForAll();
/* 405:    */     }
/* 406:    */     catch (InterruptedException localInterruptedException) {}
/* 407:351 */     if (tracker.isErrorAny()) {
/* 408:353 */       throw new RuntimeException("Couldn't load picture located at \"" + 
/* 409:354 */         path + "\"");
/* 410:    */     }
/* 411:357 */     return picture;
/* 412:    */   }
/* 413:    */   
/* 414:    */   public void actionPerformed(ActionEvent evt)
/* 415:    */   {
/* 416:363 */     if (evt.getSource() == this.saveButton)
/* 417:    */     {
/* 418:366 */       FileDialog fd = new FileDialog(this, "Save Animator Window", 
/* 419:367 */         1);
/* 420:368 */       fd.setFile("Animator.bmp");
/* 421:369 */       fd.show();
/* 422:371 */       if (fd.getFile() == null) {
/* 423:373 */         return;
/* 424:    */       }
/* 425:376 */       String fileName = fd.getDirectory() + fd.getFile();
/* 426:378 */       if (fileName.indexOf(".*.*") != -1) {
/* 427:380 */         fileName = fileName.substring(0, fileName.length() - 4);
/* 428:    */       }
/* 429:384 */       this.canvas.savePrint.saveToFile(fileName);
/* 430:    */     }
/* 431:386 */     else if (evt.getSource() == this.printButton)
/* 432:    */     {
/* 433:388 */       PrinterJob printerJob = PrinterJob.getPrinterJob();
/* 434:389 */       Book book = new Book();
/* 435:390 */       book.append(this.canvas.savePrint, new PageFormat());
/* 436:391 */       printerJob.setPageable(book);
/* 437:392 */       if (!printerJob.printDialog()) {
/* 438:394 */         return;
/* 439:    */       }
/* 440:    */       try
/* 441:    */       {
/* 442:399 */         printerJob.print();
/* 443:    */       }
/* 444:    */       catch (PrinterException exception)
/* 445:    */       {
/* 446:403 */         System.err.println("Printing error: " + exception);
/* 447:    */       }
/* 448:    */     }
/* 449:    */   }
/* 450:    */   
/* 451:    */   public void keyPressed(KeyEvent e)
/* 452:    */   {
/* 453:415 */     int keyCode = e.getKeyCode();
/* 454:417 */     for (int i = 0; i < this.keysPressed.size(); i++) {
/* 455:419 */       if (((Integer)this.keysPressed.elementAt(i)).intValue() == keyCode) {
/* 456:421 */         return;
/* 457:    */       }
/* 458:    */     }
/* 459:424 */     this.keysPressed.addElement(new Integer(keyCode));
/* 460:    */   }
/* 461:    */   
/* 462:    */   public void keyReleased(KeyEvent e)
/* 463:    */   {
/* 464:430 */     int keyCode = e.getKeyCode();
/* 465:432 */     for (int i = 0; i < this.keysPressed.size(); i++) {
/* 466:434 */       if (((Integer)this.keysPressed.elementAt(i)).intValue() == keyCode)
/* 467:    */       {
/* 468:436 */         this.keysPressed.removeElementAt(i);
/* 469:437 */         return;
/* 470:    */       }
/* 471:    */     }
/* 472:    */   }
/* 473:    */   
/* 474:    */   public void keyTyped(KeyEvent e) {}
/* 475:    */   
/* 476:    */   class WindowCanvas
/* 477:    */     extends Canvas
/* 478:    */     implements DrawGraphics
/* 479:    */   {
/* 480:    */     SavePrint savePrint;
/* 481:    */     int xSize;
/* 482:    */     int ySize;
/* 483:    */     Image offscreen;
/* 484:    */     
/* 485:    */     public WindowCanvas(int xSize, int ySize)
/* 486:    */     {
/* 487:461 */       this.xSize = xSize;
/* 488:462 */       this.ySize = ySize;
/* 489:463 */       setSize(xSize, ySize);
/* 490:    */       
/* 491:465 */       this.savePrint = new SavePrint(this, this, xSize, ySize);
/* 492:    */     }
/* 493:    */     
/* 494:    */     public void addNotify()
/* 495:    */     {
/* 496:471 */       super.addNotify();
/* 497:472 */       this.offscreen = createImage(this.xSize, this.ySize);
/* 498:    */     }
/* 499:    */     
/* 500:    */     public void paint(Graphics g)
/* 501:    */     {
/* 502:478 */       update(g);
/* 503:    */     }
/* 504:    */     
/* 505:    */     public void update(Graphics g)
/* 506:    */     {
/* 507:484 */       Rectangle r = g.getClipBounds();
/* 508:485 */       g.drawImage(this.offscreen, r.x, r.y, r.x + r.width, 
/* 509:486 */         r.y + r.width, r.x, r.y, r.x + r.width, 
/* 510:487 */         r.y + r.width, null, null);
/* 511:    */     }
/* 512:    */     
/* 513:    */     public void drawWindowToGraphics(Graphics g)
/* 514:    */     {
/* 515:495 */       g.drawImage(this.offscreen, 0, 0, null);
/* 516:    */     }
/* 517:    */     
/* 518:    */     public void drawWindowToGraphics(Graphics g, int width, int height)
/* 519:    */     {
/* 520:501 */       g.drawImage(this.offscreen, 0, 0, null);
/* 521:    */     }
/* 522:    */     
/* 523:    */     public int getMargin()
/* 524:    */     {
/* 525:507 */       return 0;
/* 526:    */     }
/* 527:    */   }
/* 528:    */   
/* 529:    */   class WindowCloser
/* 530:    */     extends WindowAdapter
/* 531:    */   {
/* 532:    */     WindowCloser() {}
/* 533:    */     
/* 534:    */     public void windowClosing(WindowEvent e)
/* 535:    */     {
/* 536:517 */       e.getWindow().dispose();
/* 537:518 */       System.exit(0);
/* 538:    */     }
/* 539:    */   }
/* 540:    */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.Animator
 * JD-Core Version:    0.7.0.1
 */