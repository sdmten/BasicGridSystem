/*   1:    */ package game1.hsa;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Font;
/*   5:    */ import java.awt.Graphics;
/*   6:    */ import java.awt.Image;
/*   7:    */ import java.awt.image.ImageObserver;
/*   8:    */ 
/*   9:    */ class ConsoleCanvasGraphics
/*  10:    */   extends ConsoleCanvas
/*  11:    */   implements DrawGraphics
/*  12:    */ {
/*  13:    */   protected static final int NO_SCALING = -1;
/*  14:    */   protected Image offscreenImage;
/*  15: 41 */   protected boolean inXORMode = false;
/*  16:    */   protected Color xorColor;
/*  17:    */   protected int cursorRow;
/*  18:    */   protected int cursorCol;
/*  19:    */   
/*  20:    */   public ConsoleCanvasGraphics(ConsoleParent parent, int rows, int columns, int fontSize)
/*  21:    */   {
/*  22: 56 */     super(parent, rows, columns, fontSize);
/*  23: 57 */     this.cursorRow = 1;
/*  24: 58 */     this.cursorCol = 1;
/*  25:    */     
/*  26: 60 */     this.savePrint = new SavePrint(this, this, this.numXPixels + 4, 
/*  27: 61 */       this.numYPixels + 4);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void addNotify()
/*  31:    */   {
/*  32: 71 */     super.addNotify();
/*  33:    */     
/*  34:    */ 
/*  35: 74 */     this.offscreenImage = createImage(this.numXPixels, this.numYPixels);
/*  36: 75 */     Graphics offscreenGraphics = this.offscreenImage.getGraphics();
/*  37: 76 */     offscreenGraphics.setFont(this.font);
/*  38:    */     
/*  39:    */ 
/*  40: 79 */     offscreenGraphics.setColor(Color.white);
/*  41: 80 */     offscreenGraphics.fillRect(0, 0, this.numXPixels, this.numYPixels);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public synchronized void clearRect(int x, int y, int width, int height)
/*  45:    */   {
/*  46: 90 */     Graphics offscreenGraphics = this.offscreenImage.getGraphics();
/*  47: 91 */     Graphics onscreenGraphics = getGraphics();
/*  48:    */     
/*  49:    */ 
/*  50: 94 */     offscreenGraphics.clearRect(x, y, width, height);
/*  51: 97 */     if (this.macOSX)
/*  52:    */     {
/*  53: 99 */       repaint();
/*  54:100 */       return;
/*  55:    */     }
/*  56:104 */     if ((this.cursorVisible) || (!this.hasFocus)) {
/*  57:105 */       toggleCursor();
/*  58:    */     }
/*  59:106 */     onscreenGraphics.translate(2, 2);
/*  60:107 */     onscreenGraphics.clearRect(x, y, width, height);
/*  61:109 */     if ((this.cursorVisible) || (!this.hasFocus)) {
/*  62:110 */       toggleCursor();
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public synchronized void clearScreen(Color bgColor)
/*  67:    */   {
/*  68:119 */     Graphics offscreenGraphics = this.offscreenImage.getGraphics();
/*  69:120 */     Graphics onscreenGraphics = getGraphics();
/*  70:    */     
/*  71:    */ 
/*  72:123 */     offscreenGraphics.setColor(bgColor);
/*  73:124 */     offscreenGraphics.fillRect(0, 0, this.numXPixels, this.numYPixels);
/*  74:127 */     if (this.macOSX)
/*  75:    */     {
/*  76:129 */       repaint();
/*  77:130 */       return;
/*  78:    */     }
/*  79:134 */     if ((this.cursorVisible) || (!this.hasFocus)) {
/*  80:135 */       toggleCursor();
/*  81:    */     }
/*  82:136 */     onscreenGraphics.translate(2, 2);
/*  83:137 */     onscreenGraphics.setColor(bgColor);
/*  84:138 */     onscreenGraphics.fillRect(0, 0, this.numXPixels, this.numYPixels);
/*  85:140 */     if ((this.cursorVisible) || (!this.hasFocus)) {
/*  86:141 */       toggleCursor();
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   public synchronized void clearToEOL(int row, int col, Color bgColor)
/*  91:    */   {
/*  92:151 */     int x = (col - 1) * this.fontWidth;
/*  93:152 */     int y = (row - 1) * this.fontHeight;
/*  94:153 */     int len = this.numXPixels - x;
/*  95:154 */     Graphics offscreenGraphics = this.offscreenImage.getGraphics();
/*  96:155 */     Graphics onscreenGraphics = getGraphics();
/*  97:    */     
/*  98:    */ 
/*  99:158 */     offscreenGraphics.setColor(bgColor);
/* 100:159 */     offscreenGraphics.fillRect(x, y, len, this.fontHeight);
/* 101:162 */     if (this.macOSX)
/* 102:    */     {
/* 103:164 */       repaint();
/* 104:165 */       return;
/* 105:    */     }
/* 106:169 */     if ((this.cursorVisible) || (!this.hasFocus)) {
/* 107:170 */       toggleCursor();
/* 108:    */     }
/* 109:171 */     onscreenGraphics.translate(2, 2);
/* 110:172 */     onscreenGraphics.setColor(bgColor);
/* 111:173 */     onscreenGraphics.fillRect(x, y, len, this.fontHeight);
/* 112:175 */     if ((this.cursorVisible) || (!this.hasFocus)) {
/* 113:176 */       toggleCursor();
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   public synchronized void copyArea(int x, int y, int width, int height, int deltaX, int deltaY)
/* 118:    */   {
/* 119:187 */     Graphics offscreenGraphics = this.offscreenImage.getGraphics();
/* 120:188 */     Graphics onscreenGraphics = getGraphics();
/* 121:    */     
/* 122:    */ 
/* 123:191 */     offscreenGraphics.copyArea(x, y, width, height, deltaX, deltaY);
/* 124:198 */     if (this.macOSX)
/* 125:    */     {
/* 126:200 */       repaint();
/* 127:201 */       return;
/* 128:    */     }
/* 129:204 */     onscreenGraphics.translate(2, 2);
/* 130:205 */     onscreenGraphics.drawImage(this.offscreenImage, 0, 0, this);
/* 131:207 */     if ((this.cursorVisible) || (!this.hasFocus)) {
/* 132:208 */       toggleCursor();
/* 133:    */     }
/* 134:    */   }
/* 135:    */   
/* 136:    */   public synchronized void draw3DRect(int x, int y, int width, int height, boolean raised, Color color)
/* 137:    */   {
/* 138:220 */     Graphics offscreenGraphics = getOffscreenGraphics(color);
/* 139:221 */     offscreenGraphics.draw3DRect(x, y, width, height, raised);
/* 140:224 */     if (this.macOSX)
/* 141:    */     {
/* 142:226 */       repaint();
/* 143:227 */       return;
/* 144:    */     }
/* 145:231 */     Graphics onscreenGraphics = getOnscreenGraphics(color);
/* 146:232 */     onscreenGraphics.draw3DRect(x, y, width, height, raised);
/* 147:234 */     if ((this.cursorVisible) || (!this.hasFocus)) {
/* 148:235 */       toggleCursor();
/* 149:    */     }
/* 150:    */   }
/* 151:    */   
/* 152:    */   public synchronized void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle, Color color)
/* 153:    */   {
/* 154:248 */     Graphics offscreenGraphics = getOffscreenGraphics(color);
/* 155:249 */     offscreenGraphics.drawArc(x, y, width, height, startAngle, arcAngle);
/* 156:252 */     if (this.macOSX)
/* 157:    */     {
/* 158:254 */       repaint();
/* 159:255 */       return;
/* 160:    */     }
/* 161:259 */     Graphics onscreenGraphics = getOnscreenGraphics(color);
/* 162:260 */     onscreenGraphics.drawArc(x, y, width, height, startAngle, arcAngle);
/* 163:262 */     if ((this.cursorVisible) || (!this.hasFocus)) {
/* 164:263 */       toggleCursor();
/* 165:    */     }
/* 166:    */   }
/* 167:    */   
/* 168:    */   public synchronized void drawImage(Image img, int x, int y, ImageObserver obs)
/* 169:    */   {
/* 170:274 */     Graphics offscreenGraphics = getOffscreenGraphics(Color.black);
/* 171:275 */     offscreenGraphics.drawImage(img, x, y, obs);
/* 172:278 */     if (this.macOSX)
/* 173:    */     {
/* 174:280 */       repaint();
/* 175:281 */       return;
/* 176:    */     }
/* 177:285 */     Graphics onscreenGraphics = getOnscreenGraphics(Color.black);
/* 178:286 */     onscreenGraphics.drawImage(img, x, y, obs);
/* 179:288 */     if ((this.cursorVisible) || (!this.hasFocus)) {
/* 180:289 */       toggleCursor();
/* 181:    */     }
/* 182:    */   }
/* 183:    */   
/* 184:    */   public synchronized void drawLine(int x1, int y1, int x2, int y2, Color color)
/* 185:    */   {
/* 186:300 */     Graphics offscreenGraphics = getOffscreenGraphics(color);
/* 187:301 */     offscreenGraphics.drawLine(x1, y1, x2, y2);
/* 188:304 */     if (this.macOSX)
/* 189:    */     {
/* 190:306 */       repaint();
/* 191:307 */       return;
/* 192:    */     }
/* 193:311 */     Graphics onscreenGraphics = getOnscreenGraphics(color);
/* 194:312 */     onscreenGraphics.drawLine(x1, y1, x2, y2);
/* 195:314 */     if ((this.cursorVisible) || (!this.hasFocus)) {
/* 196:315 */       toggleCursor();
/* 197:    */     }
/* 198:    */   }
/* 199:    */   
/* 200:    */   public synchronized void drawOval(int x, int y, int width, int height, Color color)
/* 201:    */   {
/* 202:327 */     Graphics offscreenGraphics = getOffscreenGraphics(color);
/* 203:328 */     offscreenGraphics.drawOval(x, y, width, height);
/* 204:331 */     if (this.macOSX)
/* 205:    */     {
/* 206:333 */       repaint();
/* 207:334 */       return;
/* 208:    */     }
/* 209:338 */     Graphics onscreenGraphics = getOnscreenGraphics(color);
/* 210:339 */     onscreenGraphics.drawOval(x, y, width, height);
/* 211:341 */     if ((this.cursorVisible) || (!this.hasFocus)) {
/* 212:342 */       toggleCursor();
/* 213:    */     }
/* 214:    */   }
/* 215:    */   
/* 216:    */   public synchronized void drawPolygon(int[] xPoints, int[] yPoints, int nPoints, Color color)
/* 217:    */   {
/* 218:353 */     Graphics offscreenGraphics = getOffscreenGraphics(color);
/* 219:354 */     offscreenGraphics.drawPolygon(xPoints, yPoints, nPoints);
/* 220:357 */     if (this.macOSX)
/* 221:    */     {
/* 222:359 */       repaint();
/* 223:360 */       return;
/* 224:    */     }
/* 225:364 */     Graphics onscreenGraphics = getOnscreenGraphics(color);
/* 226:365 */     onscreenGraphics.drawPolygon(xPoints, yPoints, nPoints);
/* 227:367 */     if ((this.cursorVisible) || (!this.hasFocus)) {
/* 228:368 */       toggleCursor();
/* 229:    */     }
/* 230:    */   }
/* 231:    */   
/* 232:    */   public synchronized void drawRect(int x, int y, int width, int height, Color color)
/* 233:    */   {
/* 234:380 */     Graphics offscreenGraphics = getOffscreenGraphics(color);
/* 235:381 */     offscreenGraphics.drawRect(x, y, width, height);
/* 236:384 */     if (this.macOSX)
/* 237:    */     {
/* 238:386 */       repaint();
/* 239:387 */       return;
/* 240:    */     }
/* 241:391 */     Graphics onscreenGraphics = getOnscreenGraphics(color);
/* 242:392 */     onscreenGraphics.drawRect(x, y, width, height);
/* 243:394 */     if ((this.cursorVisible) || (!this.hasFocus)) {
/* 244:395 */       toggleCursor();
/* 245:    */     }
/* 246:    */   }
/* 247:    */   
/* 248:    */   public synchronized void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight, Color color)
/* 249:    */   {
/* 250:407 */     Graphics offscreenGraphics = getOffscreenGraphics(color);
/* 251:408 */     offscreenGraphics.drawRoundRect(x, y, width, height, arcWidth, 
/* 252:409 */       arcHeight);
/* 253:412 */     if (this.macOSX)
/* 254:    */     {
/* 255:414 */       repaint();
/* 256:415 */       return;
/* 257:    */     }
/* 258:419 */     Graphics onscreenGraphics = getOnscreenGraphics(color);
/* 259:420 */     onscreenGraphics.drawRoundRect(x, y, width, height, arcWidth, 
/* 260:421 */       arcHeight);
/* 261:423 */     if ((this.cursorVisible) || (!this.hasFocus)) {
/* 262:424 */       toggleCursor();
/* 263:    */     }
/* 264:    */   }
/* 265:    */   
/* 266:    */   public synchronized void drawString(String str, int x, int y, Font font, Color color)
/* 267:    */   {
/* 268:435 */     Graphics offscreenGraphics = getOffscreenGraphics(color);
/* 269:436 */     offscreenGraphics.setFont(font);
/* 270:437 */     offscreenGraphics.drawString(str, x, y);
/* 271:440 */     if (this.macOSX)
/* 272:    */     {
/* 273:442 */       repaint();
/* 274:443 */       return;
/* 275:    */     }
/* 276:447 */     Graphics onscreenGraphics = getOnscreenGraphics(color);
/* 277:448 */     onscreenGraphics.setFont(font);
/* 278:449 */     onscreenGraphics.drawString(str, x, y);
/* 279:451 */     if (this.cursorVisible) {
/* 280:452 */       toggleCursor();
/* 281:    */     }
/* 282:    */   }
/* 283:    */   
/* 284:    */   public synchronized void drawText(int row, int col, String text, Color fgColor, Color bgColor)
/* 285:    */   {
/* 286:463 */     int x = (col - 1) * this.fontWidth;
/* 287:464 */     int y = (row - 1) * this.fontHeight;
/* 288:465 */     Graphics offscreenGraphics = this.offscreenImage.getGraphics();
/* 289:466 */     Graphics onscreenGraphics = getGraphics();
/* 290:    */     
/* 291:    */ 
/* 292:    */ 
/* 293:    */ 
/* 294:471 */     offscreenGraphics.setColor(bgColor);
/* 295:472 */     offscreenGraphics.fillRect(x, y, this.fontWidth * text.length(), this.fontHeight);
/* 296:    */     
/* 297:    */ 
/* 298:475 */     offscreenGraphics.setColor(fgColor);
/* 299:476 */     offscreenGraphics.setFont(this.font);
/* 300:477 */     offscreenGraphics.drawString(text, x, y + this.fontHeight - this.fontBase);
/* 301:480 */     if (this.macOSX)
/* 302:    */     {
/* 303:482 */       repaint();
/* 304:483 */       return;
/* 305:    */     }
/* 306:486 */     if (this.cursorVisible) {
/* 307:487 */       toggleCursor();
/* 308:    */     }
/* 309:492 */     onscreenGraphics.translate(2, 2);
/* 310:493 */     onscreenGraphics.setColor(bgColor);
/* 311:494 */     onscreenGraphics.fillRect(x, y, this.fontWidth * text.length(), this.fontHeight);
/* 312:    */     
/* 313:    */ 
/* 314:497 */     onscreenGraphics.setColor(fgColor);
/* 315:498 */     onscreenGraphics.setFont(this.font);
/* 316:499 */     onscreenGraphics.drawString(text, x, y + this.fontHeight - this.fontBase);
/* 317:501 */     if (this.cursorVisible) {
/* 318:502 */       toggleCursor();
/* 319:    */     }
/* 320:    */   }
/* 321:    */   
/* 322:    */   public void drawWindowToGraphics(Graphics g)
/* 323:    */   {
/* 324:512 */     g.translate(2, 2);
/* 325:    */     
/* 326:    */ 
/* 327:515 */     g.setColor(Color.white);
/* 328:516 */     for (int cnt = 1; cnt <= 2; cnt++) {
/* 329:518 */       g.drawRect(-cnt, -cnt, 
/* 330:519 */         this.numXPixels + 2 * cnt - 1, this.numYPixels + 2 * cnt - 1);
/* 331:    */     }
/* 332:523 */     g.drawImage(this.offscreenImage, 0, 0, this);
/* 333:    */     
/* 334:525 */     g.translate(-2, -2);
/* 335:    */   }
/* 336:    */   
/* 337:    */   public void drawWindowToGraphics(Graphics g, int pageWidth, int pageHeight)
/* 338:    */   {
/* 339:533 */     g.translate(4, 4);
/* 340:    */     int ySize;
/* 341:    */     int ySize;
/* 342:    */     int xSize;
/* 343:535 */     if (pageWidth / this.numXPixels < pageHeight / this.numYPixels)
/* 344:    */     {
/* 345:538 */       int xSize = pageWidth - 4 - 4;
/* 346:539 */       ySize = (int)(xSize / this.numXPixels * this.numYPixels + 0.5D);
/* 347:    */     }
/* 348:    */     else
/* 349:    */     {
/* 350:544 */       ySize = pageHeight - 4 - 4;
/* 351:545 */       xSize = (int)(ySize / this.numYPixels * this.numXPixels + 0.5D);
/* 352:    */     }
/* 353:549 */     g.setColor(Color.white);
/* 354:550 */     for (int cnt = 1; cnt <= 2; cnt++) {
/* 355:552 */       g.drawRect(-cnt, -cnt, 
/* 356:553 */         xSize + 2 * cnt - 1, ySize + 2 * cnt - 1);
/* 357:    */     }
/* 358:557 */     g.drawImage(this.offscreenImage, 0, 0, xSize, ySize, this);
/* 359:    */     
/* 360:559 */     g.translate(-4, -4);
/* 361:    */   }
/* 362:    */   
/* 363:    */   public int getMargin()
/* 364:    */   {
/* 365:565 */     return 2;
/* 366:    */   }
/* 367:    */   
/* 368:    */   public synchronized void fill3DRect(int x, int y, int width, int height, boolean raised, Color color)
/* 369:    */   {
/* 370:577 */     Graphics offscreenGraphics = getOffscreenGraphics(color);
/* 371:578 */     offscreenGraphics.fill3DRect(x, y, width, height, raised);
/* 372:581 */     if (this.macOSX)
/* 373:    */     {
/* 374:583 */       repaint();
/* 375:584 */       return;
/* 376:    */     }
/* 377:588 */     Graphics onscreenGraphics = getOnscreenGraphics(color);
/* 378:589 */     onscreenGraphics.fill3DRect(x, y, width, height, raised);
/* 379:591 */     if (this.cursorVisible) {
/* 380:592 */       toggleCursor();
/* 381:    */     }
/* 382:    */   }
/* 383:    */   
/* 384:    */   public synchronized void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle, Color color)
/* 385:    */   {
/* 386:605 */     Graphics offscreenGraphics = getOffscreenGraphics(color);
/* 387:606 */     offscreenGraphics.setColor(color);
/* 388:607 */     offscreenGraphics.fillArc(x, y, width, height, startAngle, arcAngle);
/* 389:610 */     if (this.macOSX)
/* 390:    */     {
/* 391:612 */       repaint();
/* 392:613 */       return;
/* 393:    */     }
/* 394:617 */     Graphics onscreenGraphics = getOnscreenGraphics(color);
/* 395:618 */     onscreenGraphics.fillArc(x, y, width, height, startAngle, arcAngle);
/* 396:620 */     if ((this.cursorVisible) || (!this.hasFocus)) {
/* 397:621 */       toggleCursor();
/* 398:    */     }
/* 399:    */   }
/* 400:    */   
/* 401:    */   public synchronized void fillOval(int x, int y, int width, int height, Color color)
/* 402:    */   {
/* 403:633 */     Graphics offscreenGraphics = getOffscreenGraphics(color);
/* 404:634 */     offscreenGraphics.fillOval(x, y, width, height);
/* 405:637 */     if (this.macOSX)
/* 406:    */     {
/* 407:639 */       repaint();
/* 408:640 */       return;
/* 409:    */     }
/* 410:644 */     Graphics onscreenGraphics = getOnscreenGraphics(color);
/* 411:645 */     onscreenGraphics.fillOval(x, y, width, height);
/* 412:647 */     if ((this.cursorVisible) || (!this.hasFocus)) {
/* 413:648 */       toggleCursor();
/* 414:    */     }
/* 415:    */   }
/* 416:    */   
/* 417:    */   public synchronized void fillPolygon(int[] xPoints, int[] yPoints, int nPoints, Color color)
/* 418:    */   {
/* 419:659 */     Graphics offscreenGraphics = getOffscreenGraphics(color);
/* 420:660 */     offscreenGraphics.fillPolygon(xPoints, yPoints, nPoints);
/* 421:663 */     if (this.macOSX)
/* 422:    */     {
/* 423:665 */       repaint();
/* 424:666 */       return;
/* 425:    */     }
/* 426:670 */     Graphics onscreenGraphics = getOnscreenGraphics(color);
/* 427:671 */     onscreenGraphics.fillPolygon(xPoints, yPoints, nPoints);
/* 428:673 */     if ((this.cursorVisible) || (!this.hasFocus)) {
/* 429:674 */       toggleCursor();
/* 430:    */     }
/* 431:    */   }
/* 432:    */   
/* 433:    */   public synchronized void fillRect(int x, int y, int width, int height, Color color)
/* 434:    */   {
/* 435:686 */     Graphics offscreenGraphics = getOffscreenGraphics(color);
/* 436:687 */     offscreenGraphics.fillRect(x, y, width, height);
/* 437:690 */     if (this.macOSX)
/* 438:    */     {
/* 439:692 */       repaint();
/* 440:693 */       return;
/* 441:    */     }
/* 442:697 */     Graphics onscreenGraphics = getOnscreenGraphics(color);
/* 443:698 */     onscreenGraphics.fillRect(x, y, width, height);
/* 444:700 */     if ((this.cursorVisible) || (!this.hasFocus)) {
/* 445:701 */       toggleCursor();
/* 446:    */     }
/* 447:    */   }
/* 448:    */   
/* 449:    */   public synchronized void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight, Color color)
/* 450:    */   {
/* 451:713 */     Graphics offscreenGraphics = getOffscreenGraphics(color);
/* 452:714 */     offscreenGraphics.fillRoundRect(x, y, width, height, arcWidth, 
/* 453:715 */       arcHeight);
/* 454:718 */     if (this.macOSX)
/* 455:    */     {
/* 456:720 */       repaint();
/* 457:721 */       return;
/* 458:    */     }
/* 459:725 */     Graphics onscreenGraphics = getOnscreenGraphics(color);
/* 460:726 */     onscreenGraphics.fillRoundRect(x, y, width, height, arcWidth, 
/* 461:727 */       arcHeight);
/* 462:729 */     if ((this.cursorVisible) || (!this.hasFocus)) {
/* 463:730 */       toggleCursor();
/* 464:    */     }
/* 465:    */   }
/* 466:    */   
/* 467:    */   public int getCurrentColumn()
/* 468:    */   {
/* 469:741 */     return this.cursorCol;
/* 470:    */   }
/* 471:    */   
/* 472:    */   public int getCurrentRow()
/* 473:    */   {
/* 474:752 */     return this.cursorRow;
/* 475:    */   }
/* 476:    */   
/* 477:    */   public int getDrawingAreaHeight()
/* 478:    */   {
/* 479:763 */     return this.numYPixels;
/* 480:    */   }
/* 481:    */   
/* 482:    */   protected Graphics getOffscreenGraphics(Color color)
/* 483:    */   {
/* 484:773 */     Graphics offscreenGraphics = this.offscreenImage.getGraphics();
/* 485:776 */     if (this.inXORMode) {
/* 486:777 */       offscreenGraphics.setXORMode(this.xorColor);
/* 487:    */     }
/* 488:778 */     offscreenGraphics.setColor(color);
/* 489:    */     
/* 490:780 */     return offscreenGraphics;
/* 491:    */   }
/* 492:    */   
/* 493:    */   protected Graphics getOnscreenGraphics(Color color)
/* 494:    */   {
/* 495:793 */     Graphics onscreenGraphics = getGraphics();
/* 496:795 */     if ((this.cursorVisible) || (!this.hasFocus)) {
/* 497:796 */       toggleCursor();
/* 498:    */     }
/* 499:797 */     if (this.inXORMode) {
/* 500:798 */       onscreenGraphics.setXORMode(this.xorColor);
/* 501:    */     }
/* 502:799 */     onscreenGraphics.translate(2, 2);
/* 503:800 */     onscreenGraphics.setColor(color);
/* 504:801 */     onscreenGraphics.clipRect(0, 0, this.numXPixels, this.numYPixels);
/* 505:    */     
/* 506:803 */     return onscreenGraphics;
/* 507:    */   }
/* 508:    */   
/* 509:    */   public int getDrawingAreaWidth()
/* 510:    */   {
/* 511:814 */     return this.numXPixels;
/* 512:    */   }
/* 513:    */   
/* 514:    */   public void paint(Graphics g)
/* 515:    */   {
/* 516:824 */     if (!this.macOSX) {
/* 517:826 */       if (this.cursorVisible) {
/* 518:827 */         toggleCursor();
/* 519:    */       }
/* 520:    */     }
/* 521:830 */     drawWindowToGraphics(g);
/* 522:832 */     if ((this.cursorVisible) || (!this.hasFocus)) {
/* 523:833 */       toggleCursor();
/* 524:    */     }
/* 525:    */   }
/* 526:    */   
/* 527:    */   public synchronized void scrollUpALine(Color bgColor)
/* 528:    */   {
/* 529:867 */     Graphics offscreenGraphics = this.offscreenImage.getGraphics();
/* 530:868 */     Graphics onscreenGraphics = getGraphics();
/* 531:    */     
/* 532:    */ 
/* 533:    */ 
/* 534:    */ 
/* 535:873 */     offscreenGraphics.copyArea(0, this.fontHeight, this.numXPixels, 
/* 536:874 */       this.numYPixels - this.fontHeight, 0, -this.fontHeight);
/* 537:    */     
/* 538:    */ 
/* 539:877 */     offscreenGraphics.setColor(bgColor);
/* 540:878 */     offscreenGraphics.fillRect(0, this.numYPixels - this.fontHeight, this.numXPixels, 
/* 541:879 */       this.fontHeight);
/* 542:888 */     if (this.macOSX)
/* 543:    */     {
/* 544:890 */       repaint();
/* 545:891 */       return;
/* 546:    */     }
/* 547:894 */     onscreenGraphics.translate(2, 2);
/* 548:895 */     onscreenGraphics.drawImage(this.offscreenImage, 0, 0, this);
/* 549:897 */     if ((this.cursorVisible) || (!this.hasFocus)) {
/* 550:898 */       toggleCursor();
/* 551:    */     }
/* 552:    */   }
/* 553:    */   
/* 554:    */   public synchronized void setCursorPos(int row, int col)
/* 555:    */   {
/* 556:907 */     if ((this.cursorVisible) || (!this.hasFocus)) {
/* 557:908 */       toggleCursor();
/* 558:    */     }
/* 559:909 */     this.cursorRow = row;
/* 560:910 */     this.cursorCol = col;
/* 561:911 */     if ((this.cursorVisible) || (!this.hasFocus)) {
/* 562:912 */       toggleCursor();
/* 563:    */     }
/* 564:    */   }
/* 565:    */   
/* 566:    */   public synchronized void setPaintMode()
/* 567:    */   {
/* 568:921 */     this.inXORMode = false;
/* 569:    */   }
/* 570:    */   
/* 571:    */   public synchronized void setXORMode(Color xorColor)
/* 572:    */   {
/* 573:930 */     this.inXORMode = true;
/* 574:931 */     this.xorColor = xorColor;
/* 575:    */   }
/* 576:    */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.ConsoleCanvasGraphics
 * JD-Core Version:    0.7.0.1
 */