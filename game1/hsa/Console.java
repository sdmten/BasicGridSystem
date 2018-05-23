/*    1:     */ package game1.hsa;
/*    2:     */ 
/*    3:     */ import java.awt.BorderLayout;
/*    4:     */ import java.awt.Color;
/*    5:     */ import java.awt.Font;
/*    6:     */ import java.awt.Image;
/*    7:     */ import java.awt.Panel;
/*    8:     */ import java.awt.event.KeyEvent;
/*    9:     */ import java.awt.image.ImageObserver;
/*   10:     */ 
/*   11:     */ public class Console
/*   12:     */   extends ConsoleParent
/*   13:     */ {
/*   14:  29 */   protected int currentCol = 1;
/*   15:  29 */   protected int currentRow = 1;
/*   16:  30 */   protected int actualCol = 1;
/*   17:  30 */   protected int actualRow = 1;
/*   18:  31 */   protected int startRow = 0;
/*   19:  31 */   protected int startCol = 0;
/*   20:  32 */   protected Color textColor = Color.black;
/*   21:  33 */   protected Color textBGColor = Color.white;
/*   22:  34 */   protected boolean echoOn = true;
/*   23:  35 */   protected boolean clearToEOL = true;
/*   24:  41 */   protected Color graphicsColor = Color.black;
/*   25:  42 */   protected Font font = new Font("Serif", 0, 12);
/*   26:  46 */   protected static boolean mainReturned = false;
/*   27:     */   char key;
/*   28:     */   protected ConsoleCanvasGraphics graphicsCanvas;
/*   29:     */   
/*   30:     */   public Console()
/*   31:     */   {
/*   32:  55 */     this(25, 80, 14, "Console");
/*   33:     */   }
/*   34:     */   
/*   35:     */   public Console(int fontSize)
/*   36:     */   {
/*   37:  67 */     this(25, 80, fontSize, "Console");
/*   38:     */   }
/*   39:     */   
/*   40:     */   public Console(int rows, int columns)
/*   41:     */   {
/*   42:  80 */     this(rows, columns, 14, "Console");
/*   43:     */   }
/*   44:     */   
/*   45:     */   public Console(int rows, int columns, int fontSize)
/*   46:     */   {
/*   47:  94 */     this(rows, columns, fontSize, "Console");
/*   48:     */   }
/*   49:     */   
/*   50:     */   public Console(int rows, int columns, int fontSize, String title)
/*   51:     */   {
/*   52: 109 */     this.consoleCanvas = new ConsoleCanvasGraphics(this, rows, columns, 
/*   53: 110 */       fontSize);
/*   54:     */     
/*   55:     */ 
/*   56: 113 */     this.graphicsCanvas = ((ConsoleCanvasGraphics)this.consoleCanvas);
/*   57:     */     
/*   58:     */ 
/*   59: 116 */     this.consoleCanvasPanelInner = new Panel();
/*   60: 117 */     this.consoleCanvasPanelInner.setLayout(new BorderLayout());
/*   61: 118 */     this.consoleCanvasPanelInner.add("Center", this.consoleCanvas);
/*   62:     */     
/*   63: 120 */     super.initialize(rows, columns, fontSize, title);
/*   64:     */   }
/*   65:     */   
/*   66:     */   public Console(int rows, int columns, String title)
/*   67:     */   {
/*   68: 134 */     this(rows, columns, 14, title);
/*   69:     */   }
/*   70:     */   
/*   71:     */   public Console(int fontSize, String title)
/*   72:     */   {
/*   73: 147 */     this(25, 80, fontSize, title);
/*   74:     */   }
/*   75:     */   
/*   76:     */   public Console(String title)
/*   77:     */   {
/*   78: 159 */     this(25, 80, 14, title);
/*   79:     */   }
/*   80:     */   
/*   81:     */   public void clear()
/*   82:     */   {
/*   83: 168 */     this.graphicsCanvas.clearScreen(this.textBGColor);
/*   84: 169 */     this.currentRow = 1;
/*   85: 170 */     this.currentCol = 1;
/*   86: 171 */     this.actualRow = 1;
/*   87: 172 */     this.actualCol = 1;
/*   88: 173 */     this.graphicsCanvas.setCursorPos(this.currentRow, this.currentCol);
/*   89:     */   }
/*   90:     */   
/*   91:     */   public void clearRect(int x, int y, int width, int height)
/*   92:     */   {
/*   93: 184 */     this.graphicsCanvas.clearRect(x, y, width, height);
/*   94:     */   }
/*   95:     */   
/*   96:     */   public void copyArea(int x, int y, int width, int height, int delta_x, int delta_y)
/*   97:     */   {
/*   98: 197 */     this.graphicsCanvas.copyArea(x, y, width, height, delta_x, delta_y);
/*   99:     */   }
/*  100:     */   
/*  101:     */   public void draw3DRect(int x, int y, int width, int height, boolean raised)
/*  102:     */   {
/*  103: 210 */     this.graphicsCanvas.draw3DRect(x, y, width, height, raised, 
/*  104: 211 */       this.graphicsColor);
/*  105:     */   }
/*  106:     */   
/*  107:     */   public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle)
/*  108:     */   {
/*  109: 225 */     this.graphicsCanvas.drawArc(x, y, width, height, startAngle, 
/*  110: 226 */       arcAngle, this.graphicsColor);
/*  111:     */   }
/*  112:     */   
/*  113:     */   public void drawImage(Image img, int x, int y, ImageObserver obs)
/*  114:     */   {
/*  115: 237 */     this.graphicsCanvas.drawImage(img, x, y, obs);
/*  116:     */   }
/*  117:     */   
/*  118:     */   public void drawLine(int x1, int y1, int x2, int y2)
/*  119:     */   {
/*  120: 248 */     this.graphicsCanvas.drawLine(x1, y1, x2, y2, this.graphicsColor);
/*  121:     */   }
/*  122:     */   
/*  123:     */   public void drawMapleLeaf(int x, int y, int width, int height)
/*  124:     */   {
/*  125: 270 */     float rx = width;
/*  126: 271 */     float ry = height;
/*  127: 272 */     float xc = x + rx / 2.0F;
/*  128: 273 */     float yc = y + height;
/*  129:     */     
/*  130: 275 */     int[] xPoints = new int[26];
/*  131: 276 */     int[] yPoints = new int[26];
/*  132: 277 */     xPoints[0] = ((int)(xc + rx * 0.021423D));
/*  133: 278 */     yPoints[0] = ((int)(yc - ry * 0.215686D));
/*  134: 279 */     xPoints[1] = ((int)(xc + rx * 0.27078D));
/*  135: 280 */     yPoints[1] = ((int)(yc - ry * 0.203804D));
/*  136: 281 */     xPoints[2] = ((int)(xc + rx * 0.27182D));
/*  137: 282 */     yPoints[2] = ((int)(yc - ry * 0.295752D));
/*  138: 283 */     xPoints[3] = ((int)(xc + rx * 0.482015D));
/*  139: 284 */     yPoints[3] = ((int)(yc - ry * 0.411765D));
/*  140: 285 */     xPoints[4] = ((int)(xc + rx * 0.443046D));
/*  141: 286 */     yPoints[4] = ((int)(yc - ry * 0.483267D));
/*  142: 287 */     xPoints[5] = ((int)(xc + rx * 0.5D));
/*  143: 288 */     yPoints[5] = ((int)(yc - ry * 0.587435D));
/*  144: 289 */     xPoints[6] = ((int)(xc + rx * 0.363353D));
/*  145: 290 */     yPoints[6] = ((int)(yc - ry * 0.619576D));
/*  146: 291 */     xPoints[7] = ((int)(xc + rx * 0.342287D));
/*  147: 292 */     yPoints[7] = ((int)(yc - ry * 0.6938490000000001D));
/*  148: 293 */     xPoints[8] = ((int)(xc + rx * 0.153596D));
/*  149: 294 */     yPoints[8] = ((int)(yc - ry * 0.612537D));
/*  150: 295 */     xPoints[9] = ((int)(xc + rx * 0.201601D));
/*  151: 296 */     yPoints[9] = ((int)(yc - ry * 0.918462D));
/*  152: 297 */     xPoints[10] = ((int)(xc + rx * 0.093001D));
/*  153: 298 */     yPoints[10] = ((int)(yc - ry * 0.894514D));
/*  154: 299 */     xPoints[11] = ((int)xc);
/*  155: 300 */     yPoints[11] = ((int)(yc - ry));
/*  156: 301 */     xPoints[12] = ((int)(xc - rx * 0.093001D));
/*  157: 302 */     yPoints[12] = yPoints[10];
/*  158: 303 */     xPoints[13] = ((int)(xc - rx * 0.201601D));
/*  159: 304 */     yPoints[13] = yPoints[9];
/*  160: 305 */     xPoints[14] = ((int)(xc - rx * 0.153596D));
/*  161: 306 */     yPoints[14] = yPoints[8];
/*  162: 307 */     xPoints[15] = ((int)(xc - rx * 0.342287D));
/*  163: 308 */     yPoints[15] = yPoints[7];
/*  164: 309 */     xPoints[16] = ((int)(xc - rx * 0.363353D));
/*  165: 310 */     yPoints[16] = yPoints[6];
/*  166: 311 */     xPoints[17] = ((int)(xc - rx * 0.5D));
/*  167: 312 */     yPoints[17] = yPoints[5];
/*  168: 313 */     xPoints[18] = ((int)(xc - rx * 0.443046D));
/*  169: 314 */     yPoints[18] = yPoints[4];
/*  170: 315 */     xPoints[19] = ((int)(xc - rx * 0.482015D));
/*  171: 316 */     yPoints[19] = yPoints[3];
/*  172: 317 */     xPoints[20] = ((int)(xc - rx * 0.27182D));
/*  173: 318 */     yPoints[20] = yPoints[2];
/*  174: 319 */     xPoints[21] = ((int)(xc - rx * 0.2707796D));
/*  175: 320 */     yPoints[21] = yPoints[1];
/*  176: 321 */     xPoints[22] = ((int)(xc - rx * 0.021423D));
/*  177: 322 */     yPoints[22] = yPoints[0];
/*  178: 323 */     xPoints[23] = xPoints[22];
/*  179: 324 */     yPoints[23] = ((int)yc);
/*  180: 325 */     xPoints[24] = xPoints[0];
/*  181: 326 */     yPoints[24] = yPoints[23];
/*  182: 327 */     xPoints[25] = xPoints[0];
/*  183: 328 */     yPoints[25] = yPoints[0];
/*  184: 329 */     this.graphicsCanvas.drawPolygon(xPoints, yPoints, 26, this.graphicsColor);
/*  185:     */   }
/*  186:     */   
/*  187:     */   public void drawOval(int x, int y, int width, int height)
/*  188:     */   {
/*  189: 341 */     this.graphicsCanvas.drawOval(x, y, width, height, this.graphicsColor);
/*  190:     */   }
/*  191:     */   
/*  192:     */   public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints)
/*  193:     */   {
/*  194: 352 */     this.graphicsCanvas.drawPolygon(xPoints, yPoints, nPoints, this.graphicsColor);
/*  195:     */   }
/*  196:     */   
/*  197:     */   public void drawRect(int x, int y, int width, int height)
/*  198:     */   {
/*  199: 364 */     this.graphicsCanvas.drawRect(x, y, width, height, this.graphicsColor);
/*  200:     */   }
/*  201:     */   
/*  202:     */   public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight)
/*  203:     */   {
/*  204: 377 */     this.graphicsCanvas.drawRoundRect(x, y, width, height, 
/*  205: 378 */       arcWidth, arcHeight, this.graphicsColor);
/*  206:     */   }
/*  207:     */   
/*  208:     */   public void drawStar(int x, int y, int width, int height)
/*  209:     */   {
/*  210: 399 */     float rx = width;
/*  211: 400 */     float ry = height;
/*  212: 401 */     float xc = x + rx / 2.0F;
/*  213: 402 */     float yc = y + height;
/*  214:     */     
/*  215: 404 */     int[] xPoints = new int[11];
/*  216: 405 */     int[] yPoints = new int[11];
/*  217: 406 */     xPoints[0] = ((int)xc);
/*  218: 407 */     yPoints[0] = ((int)(yc - ry));
/*  219: 408 */     xPoints[1] = ((int)(xc + rx * 0.118034D));
/*  220: 409 */     yPoints[1] = ((int)(yc - ry * 0.61856D));
/*  221: 410 */     xPoints[2] = ((int)(xc + rx * 0.5D));
/*  222: 411 */     yPoints[2] = yPoints[1];
/*  223: 412 */     xPoints[3] = ((int)(xc + rx * 0.190983D));
/*  224: 413 */     yPoints[3] = ((int)(yc - ry * 0.381759D));
/*  225: 414 */     xPoints[4] = ((int)(xc + rx * 0.309017D));
/*  226: 415 */     yPoints[4] = ((int)yc);
/*  227: 416 */     xPoints[5] = ((int)xc);
/*  228: 417 */     yPoints[5] = ((int)(yc - ry * 0.236068D));
/*  229: 418 */     xPoints[6] = ((int)(xc - rx * 0.309017D));
/*  230: 419 */     yPoints[6] = yPoints[4];
/*  231: 420 */     xPoints[7] = ((int)(xc - rx * 0.190983D));
/*  232: 421 */     yPoints[7] = yPoints[3];
/*  233: 422 */     xPoints[8] = ((int)(xc - rx * 0.5D));
/*  234: 423 */     yPoints[8] = yPoints[2];
/*  235: 424 */     xPoints[9] = ((int)(xc - rx * 0.118034D));
/*  236: 425 */     yPoints[9] = yPoints[1];
/*  237: 426 */     xPoints[10] = xPoints[0];
/*  238: 427 */     yPoints[10] = yPoints[0];
/*  239: 428 */     this.graphicsCanvas.drawPolygon(xPoints, yPoints, 11, this.graphicsColor);
/*  240:     */   }
/*  241:     */   
/*  242:     */   public void drawString(String str, int x, int y)
/*  243:     */   {
/*  244: 439 */     this.graphicsCanvas.drawString(str, x, y, this.font, this.graphicsColor);
/*  245:     */   }
/*  246:     */   
/*  247:     */   protected void eraseLineOfInput()
/*  248:     */   {
/*  249: 451 */     int numChars = this.actualCol - this.startCol + this.maxCol * (this.actualRow - this.startRow);
/*  250: 452 */     this.currentRow = this.startRow;
/*  251: 453 */     this.currentCol = this.startCol;
/*  252: 454 */     this.actualRow = this.startRow;
/*  253: 455 */     this.actualCol = this.startCol;
/*  254: 456 */     for (int cnt = 0; cnt < numChars; cnt++) {
/*  255: 457 */       print(" ");
/*  256:     */     }
/*  257: 458 */     this.currentRow = this.startRow;
/*  258: 459 */     this.currentCol = this.startCol;
/*  259: 460 */     this.actualRow = this.startRow;
/*  260: 461 */     this.actualCol = this.startCol;
/*  261: 462 */     this.graphicsCanvas.setCursorPos(this.currentRow, this.currentCol);
/*  262:     */   }
/*  263:     */   
/*  264:     */   protected void erasePreviousChar()
/*  265:     */   {
/*  266: 472 */     if (this.currentCol > 1)
/*  267:     */     {
/*  268: 474 */       this.currentCol -= 1;
/*  269:     */     }
/*  270: 478 */     else if (this.currentRow > 1)
/*  271:     */     {
/*  272: 480 */       this.currentRow -= 1;
/*  273: 481 */       this.currentCol = this.maxCol;
/*  274:     */     }
/*  275: 484 */     this.actualRow = this.currentRow;
/*  276: 485 */     this.actualCol = this.currentCol;
/*  277:     */     
/*  278: 487 */     this.graphicsCanvas.drawText(this.currentRow, this.currentCol, " ", this.textColor, this.textBGColor);
/*  279: 488 */     this.graphicsCanvas.setCursorPos(this.currentRow, this.currentCol);
/*  280: 490 */     if ((this.currentCol == 1) && (this.currentRow != this.startRow))
/*  281:     */     {
/*  282: 492 */       this.currentCol = (this.maxCol + 1);
/*  283: 493 */       this.currentRow -= 1;
/*  284:     */     }
/*  285:     */   }
/*  286:     */   
/*  287:     */   public void fill3DRect(int x, int y, int width, int height, boolean raised)
/*  288:     */   {
/*  289: 507 */     this.graphicsCanvas.fill3DRect(x, y, width, height, raised, 
/*  290: 508 */       this.graphicsColor);
/*  291:     */   }
/*  292:     */   
/*  293:     */   public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle)
/*  294:     */   {
/*  295: 522 */     this.graphicsCanvas.fillArc(x, y, width, height, startAngle, 
/*  296: 523 */       arcAngle, this.graphicsColor);
/*  297:     */   }
/*  298:     */   
/*  299:     */   public void fillMapleLeaf(int x, int y, int width, int height)
/*  300:     */   {
/*  301: 545 */     float rx = width;
/*  302: 546 */     float ry = height;
/*  303: 547 */     float xc = x + rx / 2.0F;
/*  304: 548 */     float yc = y + height;
/*  305:     */     
/*  306: 550 */     int[] xPoints = new int[26];
/*  307: 551 */     int[] yPoints = new int[26];
/*  308: 552 */     xPoints[0] = ((int)(xc + rx * 0.021423D));
/*  309: 553 */     yPoints[0] = ((int)(yc - ry * 0.215686D));
/*  310: 554 */     xPoints[1] = ((int)(xc + rx * 0.27078D));
/*  311: 555 */     yPoints[1] = ((int)(yc - ry * 0.203804D));
/*  312: 556 */     xPoints[2] = ((int)(xc + rx * 0.27182D));
/*  313: 557 */     yPoints[2] = ((int)(yc - ry * 0.295752D));
/*  314: 558 */     xPoints[3] = ((int)(xc + rx * 0.482015D));
/*  315: 559 */     yPoints[3] = ((int)(yc - ry * 0.411765D));
/*  316: 560 */     xPoints[4] = ((int)(xc + rx * 0.443046D));
/*  317: 561 */     yPoints[4] = ((int)(yc - ry * 0.483267D));
/*  318: 562 */     xPoints[5] = ((int)(xc + rx * 0.5D));
/*  319: 563 */     yPoints[5] = ((int)(yc - ry * 0.587435D));
/*  320: 564 */     xPoints[6] = ((int)(xc + rx * 0.363353D));
/*  321: 565 */     yPoints[6] = ((int)(yc - ry * 0.619576D));
/*  322: 566 */     xPoints[7] = ((int)(xc + rx * 0.342287D));
/*  323: 567 */     yPoints[7] = ((int)(yc - ry * 0.6938490000000001D));
/*  324: 568 */     xPoints[8] = ((int)(xc + rx * 0.153596D));
/*  325: 569 */     yPoints[8] = ((int)(yc - ry * 0.612537D));
/*  326: 570 */     xPoints[9] = ((int)(xc + rx * 0.201601D));
/*  327: 571 */     yPoints[9] = ((int)(yc - ry * 0.918462D));
/*  328: 572 */     xPoints[10] = ((int)(xc + rx * 0.093001D));
/*  329: 573 */     yPoints[10] = ((int)(yc - ry * 0.894514D));
/*  330: 574 */     xPoints[11] = ((int)xc);
/*  331: 575 */     yPoints[11] = ((int)(yc - ry));
/*  332: 576 */     xPoints[12] = ((int)(xc - rx * 0.093001D));
/*  333: 577 */     yPoints[12] = yPoints[10];
/*  334: 578 */     xPoints[13] = ((int)(xc - rx * 0.201601D));
/*  335: 579 */     yPoints[13] = yPoints[9];
/*  336: 580 */     xPoints[14] = ((int)(xc - rx * 0.153596D));
/*  337: 581 */     yPoints[14] = yPoints[8];
/*  338: 582 */     xPoints[15] = ((int)(xc - rx * 0.342287D));
/*  339: 583 */     yPoints[15] = yPoints[7];
/*  340: 584 */     xPoints[16] = ((int)(xc - rx * 0.363353D));
/*  341: 585 */     yPoints[16] = yPoints[6];
/*  342: 586 */     xPoints[17] = ((int)(xc - rx * 0.5D));
/*  343: 587 */     yPoints[17] = yPoints[5];
/*  344: 588 */     xPoints[18] = ((int)(xc - rx * 0.443046D));
/*  345: 589 */     yPoints[18] = yPoints[4];
/*  346: 590 */     xPoints[19] = ((int)(xc - rx * 0.482015D));
/*  347: 591 */     yPoints[19] = yPoints[3];
/*  348: 592 */     xPoints[20] = ((int)(xc - rx * 0.27182D));
/*  349: 593 */     yPoints[20] = yPoints[2];
/*  350: 594 */     xPoints[21] = ((int)(xc - rx * 0.2707796D));
/*  351: 595 */     yPoints[21] = yPoints[1];
/*  352: 596 */     xPoints[22] = ((int)(xc - rx * 0.021423D));
/*  353: 597 */     yPoints[22] = yPoints[0];
/*  354: 598 */     xPoints[23] = xPoints[22];
/*  355: 599 */     yPoints[23] = ((int)yc);
/*  356: 600 */     xPoints[24] = xPoints[0];
/*  357: 601 */     yPoints[24] = yPoints[23];
/*  358: 602 */     xPoints[25] = xPoints[0];
/*  359: 603 */     yPoints[25] = yPoints[0];
/*  360: 604 */     this.graphicsCanvas.fillPolygon(xPoints, yPoints, 26, this.graphicsColor);
/*  361:     */   }
/*  362:     */   
/*  363:     */   public void fillOval(int x, int y, int width, int height)
/*  364:     */   {
/*  365: 616 */     this.graphicsCanvas.fillOval(x, y, width, height, this.graphicsColor);
/*  366:     */   }
/*  367:     */   
/*  368:     */   public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints)
/*  369:     */   {
/*  370: 627 */     this.graphicsCanvas.fillPolygon(xPoints, yPoints, nPoints, this.graphicsColor);
/*  371:     */   }
/*  372:     */   
/*  373:     */   public void fillRect(int x, int y, int width, int height)
/*  374:     */   {
/*  375: 639 */     this.graphicsCanvas.fillRect(x, y, width, height, this.graphicsColor);
/*  376:     */   }
/*  377:     */   
/*  378:     */   public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight)
/*  379:     */   {
/*  380: 652 */     this.graphicsCanvas.fillRoundRect(x, y, width, height, arcWidth, 
/*  381: 653 */       arcHeight, this.graphicsColor);
/*  382:     */   }
/*  383:     */   
/*  384:     */   public void fillStar(int x, int y, int width, int height)
/*  385:     */   {
/*  386: 675 */     float rx = width;
/*  387: 676 */     float ry = height;
/*  388: 677 */     float xc = x + rx / 2.0F;
/*  389: 678 */     float yc = y + height;
/*  390:     */     
/*  391: 680 */     int[] xPoints = new int[11];
/*  392: 681 */     int[] yPoints = new int[11];
/*  393: 682 */     xPoints[0] = ((int)xc);
/*  394: 683 */     yPoints[0] = ((int)(yc - ry));
/*  395: 684 */     xPoints[1] = ((int)(xc + rx * 0.118034D));
/*  396: 685 */     yPoints[1] = ((int)(yc - ry * 0.61856D));
/*  397: 686 */     xPoints[2] = ((int)(xc + rx * 0.5D));
/*  398: 687 */     yPoints[2] = yPoints[1];
/*  399: 688 */     xPoints[3] = ((int)(xc + rx * 0.190983D));
/*  400: 689 */     yPoints[3] = ((int)(yc - ry * 0.381759D));
/*  401: 690 */     xPoints[4] = ((int)(xc + rx * 0.309017D));
/*  402: 691 */     yPoints[4] = ((int)yc);
/*  403: 692 */     xPoints[5] = ((int)xc);
/*  404: 693 */     yPoints[5] = ((int)(yc - ry * 0.236068D));
/*  405: 694 */     xPoints[6] = ((int)(xc - rx * 0.309017D));
/*  406: 695 */     yPoints[6] = yPoints[4];
/*  407: 696 */     xPoints[7] = ((int)(xc - rx * 0.190983D));
/*  408: 697 */     yPoints[7] = yPoints[3];
/*  409: 698 */     xPoints[8] = ((int)(xc - rx * 0.5D));
/*  410: 699 */     yPoints[8] = yPoints[2];
/*  411: 700 */     xPoints[9] = ((int)(xc - rx * 0.118034D));
/*  412: 701 */     yPoints[9] = yPoints[1];
/*  413: 702 */     xPoints[10] = xPoints[0];
/*  414: 703 */     yPoints[10] = yPoints[0];
/*  415: 704 */     this.graphicsCanvas.fillPolygon(xPoints, yPoints, 11, this.graphicsColor);
/*  416:     */   }
/*  417:     */   
/*  418:     */   public synchronized boolean isCharAvail()
/*  419:     */   {
/*  420: 716 */     return this.kbdBufferHead != this.kbdBufferTail;
/*  421:     */   }
/*  422:     */   
/*  423:     */   public synchronized char getChar()
/*  424:     */   {
/*  425: 728 */     while (this.kbdBufferHead == this.kbdBufferTail) {
/*  426:     */       try
/*  427:     */       {
/*  428: 732 */         setWindowTitle("Waiting for input");
/*  429: 733 */         wait();
/*  430: 734 */         setWindowTitle("Running");
/*  431:     */       }
/*  432:     */       catch (InterruptedException localInterruptedException) {}
/*  433:     */     }
/*  434: 741 */     char ch = this.kbdBuffer[this.kbdBufferTail];
/*  435: 742 */     this.kbdBufferTail = ((this.kbdBufferTail + 1) % this.kbdBuffer.length);
/*  436:     */     
/*  437: 744 */     return ch;
/*  438:     */   }
/*  439:     */   
/*  440:     */   public int getColumn()
/*  441:     */   {
/*  442: 755 */     return this.consoleCanvas.getCurrentColumn();
/*  443:     */   }
/*  444:     */   
/*  445:     */   public int getHeight()
/*  446:     */   {
/*  447: 766 */     return this.graphicsCanvas.getDrawingAreaHeight();
/*  448:     */   }
/*  449:     */   
/*  450:     */   public int getMaxColumns()
/*  451:     */   {
/*  452: 777 */     return this.maxCol;
/*  453:     */   }
/*  454:     */   
/*  455:     */   public synchronized char getKey()
/*  456:     */   {
/*  457: 782 */     char key1 = this.key;
/*  458: 783 */     this.key = '\000';
/*  459: 784 */     return key1;
/*  460:     */   }
/*  461:     */   
/*  462:     */   public int getMaxRows()
/*  463:     */   {
/*  464: 794 */     return this.maxRow;
/*  465:     */   }
/*  466:     */   
/*  467:     */   public int getRow()
/*  468:     */   {
/*  469: 805 */     return this.consoleCanvas.getCurrentRow();
/*  470:     */   }
/*  471:     */   
/*  472:     */   public int getWidth()
/*  473:     */   {
/*  474: 816 */     return this.graphicsCanvas.getDrawingAreaWidth();
/*  475:     */   }
/*  476:     */   
/*  477:     */   public synchronized void keyPressed(KeyEvent e)
/*  478:     */   {
/*  479: 821 */     this.key = e.getKeyChar();
/*  480: 822 */     this.kbdBuffer[this.kbdBufferHead] = e.getKeyChar();
/*  481: 823 */     this.kbdBufferHead = ((this.kbdBufferHead + 1) % this.kbdBuffer.length);
/*  482: 824 */     notify();
/*  483:     */   }
/*  484:     */   
/*  485:     */   public int maxcol()
/*  486:     */   {
/*  487: 834 */     return getMaxColumns();
/*  488:     */   }
/*  489:     */   
/*  490:     */   public int maxrow()
/*  491:     */   {
/*  492: 845 */     return getMaxRows();
/*  493:     */   }
/*  494:     */   
/*  495:     */   public int maxx()
/*  496:     */   {
/*  497: 857 */     return this.graphicsCanvas.getDrawingAreaWidth() - 1;
/*  498:     */   }
/*  499:     */   
/*  500:     */   public int maxy()
/*  501:     */   {
/*  502: 869 */     return this.graphicsCanvas.getDrawingAreaHeight() - 1;
/*  503:     */   }
/*  504:     */   
/*  505:     */   public void print(String text)
/*  506:     */   {
/*  507: 881 */     if (text == null) {
/*  508: 883 */       text = "<null>";
/*  509:     */     }
/*  510: 886 */     int index = 0;
/*  511: 887 */     int len = text.length();
/*  512: 888 */     int start = 0;
/*  513:     */     for (;;)
/*  514:     */     {
/*  515: 893 */       index = start;
/*  516: 894 */       if (index == len)
/*  517:     */       {
/*  518: 896 */         this.graphicsCanvas.setCursorPos(this.actualRow, this.actualCol);
/*  519: 897 */         return;
/*  520:     */       }
/*  521: 899 */       char ch = text.charAt(index);
/*  522: 900 */       while ((index < len) && (text.charAt(index) != '\n') && 
/*  523: 901 */         (text.charAt(index) != '\t') && 
/*  524: 902 */         (index - start < this.maxCol - this.currentCol)) {
/*  525: 904 */         index++;
/*  526:     */       }
/*  527: 906 */       if (start != index)
/*  528:     */       {
/*  529: 909 */         this.graphicsCanvas.drawText(this.currentRow, this.currentCol, 
/*  530: 910 */           text.substring(start, index), this.textColor, this.textBGColor);
/*  531: 911 */         this.currentCol += index - start;
/*  532: 912 */         this.actualCol = this.currentCol;
/*  533:     */       }
/*  534: 914 */       if (index == len)
/*  535:     */       {
/*  536: 916 */         this.graphicsCanvas.setCursorPos(this.actualRow, this.actualCol);
/*  537: 917 */         return;
/*  538:     */       }
/*  539: 919 */       if (text.charAt(index) == '\n')
/*  540:     */       {
/*  541: 921 */         if ((this.currentRow <= this.maxRow) && (this.currentCol <= this.maxCol)) {
/*  542: 923 */           this.graphicsCanvas.clearToEOL(this.currentRow, this.currentCol, this.textBGColor);
/*  543:     */         }
/*  544: 925 */         if (this.currentRow < this.maxRow)
/*  545:     */         {
/*  546: 927 */           this.currentCol = 1;
/*  547: 928 */           this.currentRow += 1;
/*  548: 929 */           this.actualCol = this.currentCol;
/*  549: 930 */           this.actualRow = this.currentRow;
/*  550:     */         }
/*  551:     */         else
/*  552:     */         {
/*  553: 934 */           this.graphicsCanvas.scrollUpALine(this.textBGColor);
/*  554: 935 */           this.startRow -= 1;
/*  555: 936 */           this.currentCol = 1;
/*  556: 937 */           this.actualCol = this.currentCol;
/*  557:     */         }
/*  558:     */       }
/*  559: 940 */       else if (text.charAt(index) == '\t')
/*  560:     */       {
/*  561: 942 */         int numSpaces = 8 - (this.currentCol - 1) % 8;
/*  562: 946 */         if (this.currentCol + numSpaces > this.maxCol) {
/*  563: 948 */           print("\n");
/*  564:     */         } else {
/*  565: 952 */           print("        ".substring(0, numSpaces));
/*  566:     */         }
/*  567:     */       }
/*  568: 957 */       else if (this.currentCol <= this.maxCol)
/*  569:     */       {
/*  570: 959 */         this.graphicsCanvas.drawText(this.currentRow, this.currentCol, 
/*  571: 960 */           text.substring(index, index + 1), this.textColor, this.textBGColor);
/*  572: 961 */         if (this.currentCol < this.maxCol)
/*  573:     */         {
/*  574: 963 */           this.currentCol += 1;
/*  575: 964 */           this.actualCol = this.currentCol;
/*  576:     */         }
/*  577: 968 */         else if (this.currentRow < this.maxRow)
/*  578:     */         {
/*  579: 970 */           this.currentCol += 1;
/*  580: 971 */           this.actualCol = 1;
/*  581: 972 */           this.actualRow += 1;
/*  582:     */         }
/*  583:     */         else
/*  584:     */         {
/*  585: 976 */           this.currentCol += 1;
/*  586:     */         }
/*  587:     */       }
/*  588:     */       else
/*  589:     */       {
/*  590: 982 */         if (this.currentRow < this.maxRow)
/*  591:     */         {
/*  592: 984 */           this.currentRow += 1;
/*  593:     */         }
/*  594:     */         else
/*  595:     */         {
/*  596: 988 */           this.graphicsCanvas.scrollUpALine(this.textBGColor);
/*  597: 989 */           this.startRow -= 1;
/*  598:     */         }
/*  599: 991 */         this.graphicsCanvas.drawText(this.currentRow, 1, 
/*  600: 992 */           text.substring(index, index + 1), this.textColor, this.textBGColor);
/*  601: 993 */         this.currentCol = 2;
/*  602: 994 */         this.actualCol = this.currentCol;
/*  603: 995 */         this.actualRow = this.currentRow;
/*  604:     */       }
/*  605: 998 */       start = index + 1;
/*  606:     */     }
/*  607:     */   }
/*  608:     */   
/*  609:     */   public synchronized char readChar()
/*  610:     */   {
/*  611:1014 */     if (this.ungotChar != -1)
/*  612:     */     {
/*  613:1016 */       char result = (char)this.ungotChar;
/*  614:1017 */       this.ungotChar = -1;
/*  615:1018 */       return result;
/*  616:     */     }
/*  617:1021 */     if (this.lineBufferHead != this.lineBufferTail)
/*  618:     */     {
/*  619:1023 */       char result = this.lineBuffer[this.lineBufferTail];
/*  620:1024 */       this.lineBufferTail = ((this.lineBufferTail + 1) % this.lineBuffer.length);
/*  621:1025 */       return result;
/*  622:     */     }
/*  623:1028 */     this.startRow = this.currentRow;
/*  624:1029 */     this.startCol = this.currentCol;
/*  625:1030 */     if (this.currentRow > this.maxRow)
/*  626:     */     {
/*  627:1032 */       this.startRow += 1;
/*  628:1033 */       this.currentCol = 1;
/*  629:     */     }
/*  630:1037 */     this.consoleCanvas.setCursorVisible(true);
/*  631:     */     for (;;)
/*  632:     */     {
/*  633:1042 */       char ch = getChar();
/*  634:1044 */       if (ch == '\n')
/*  635:     */       {
/*  636:1046 */         this.clearToEOL = false;
/*  637:1047 */         if (this.echoOn) {
/*  638:1048 */           print("\n");
/*  639:     */         }
/*  640:1049 */         this.clearToEOL = true;
/*  641:1050 */         this.lineBuffer[this.lineBufferHead] = '\n';
/*  642:1051 */         this.lineBufferHead = ((this.lineBufferHead + 1) % this.lineBuffer.length);
/*  643:1052 */         break;
/*  644:     */       }
/*  645:1054 */       if (ch == '\b')
/*  646:     */       {
/*  647:1056 */         if (this.lineBufferHead == this.lineBufferTail)
/*  648:     */         {
/*  649:1058 */           this.consoleCanvas.invertScreen();
/*  650:     */         }
/*  651:     */         else
/*  652:     */         {
/*  653:1064 */           this.lineBufferHead = ((this.lineBufferHead + this.lineBuffer.length - 1) % this.lineBuffer.length);
/*  654:1065 */           int chToErase = this.lineBuffer[this.lineBufferHead];
/*  655:1066 */           if (this.echoOn) {
/*  656:1068 */             if (chToErase != 9)
/*  657:     */             {
/*  658:1070 */               erasePreviousChar();
/*  659:     */             }
/*  660:     */             else
/*  661:     */             {
/*  662:1075 */               eraseLineOfInput();
/*  663:1076 */               int cnt = this.lineBufferTail;
/*  664:1077 */               while (cnt != this.lineBufferHead)
/*  665:     */               {
/*  666:1079 */                 print(this.lineBuffer[cnt]);
/*  667:1080 */                 cnt = (cnt + 1) % this.lineBuffer.length;
/*  668:     */               }
/*  669:     */             }
/*  670:     */           }
/*  671:     */         }
/*  672:     */       }
/*  673:1086 */       else if (ch == '\025')
/*  674:     */       {
/*  675:1088 */         if (this.echoOn) {
/*  676:1090 */           eraseLineOfInput();
/*  677:     */         }
/*  678:1092 */         this.lineBufferHead = this.lineBufferTail;
/*  679:     */       }
/*  680:     */       else
/*  681:     */       {
/*  682:1096 */         if (this.echoOn) {
/*  683:1098 */           print(ch);
/*  684:     */         }
/*  685:1100 */         this.lineBuffer[this.lineBufferHead] = ch;
/*  686:1101 */         this.lineBufferHead = ((this.lineBufferHead + 1) % this.lineBuffer.length);
/*  687:     */       }
/*  688:     */     }
/*  689:     */     char ch;
/*  690:1105 */     char result = this.lineBuffer[this.lineBufferTail];
/*  691:1106 */     this.lineBufferTail = ((this.lineBufferTail + 1) % this.lineBuffer.length);
/*  692:     */     
/*  693:     */ 
/*  694:1109 */     this.consoleCanvas.setCursorVisible(false);
/*  695:     */     
/*  696:1111 */     return result;
/*  697:     */   }
/*  698:     */   
/*  699:     */   public void setColor(Color color)
/*  700:     */   {
/*  701:1122 */     this.graphicsColor = color;
/*  702:     */   }
/*  703:     */   
/*  704:     */   public void setColour(Color colour)
/*  705:     */   {
/*  706:1133 */     setColor(colour);
/*  707:     */   }
/*  708:     */   
/*  709:     */   public void setCursor(int row, int column)
/*  710:     */   {
/*  711:1145 */     this.currentRow = row;
/*  712:1146 */     this.currentCol = column;
/*  713:1147 */     this.actualRow = row;
/*  714:1148 */     this.actualCol = column;
/*  715:1149 */     this.graphicsCanvas.setCursorPos(this.currentRow, this.currentCol);
/*  716:     */   }
/*  717:     */   
/*  718:     */   public void setCursorVisible(boolean visible)
/*  719:     */   {
/*  720:1158 */     this.consoleCanvas.setCursorVisible(visible);
/*  721:     */   }
/*  722:     */   
/*  723:     */   public void setFont(Font font)
/*  724:     */   {
/*  725:1169 */     this.font = font;
/*  726:     */   }
/*  727:     */   
/*  728:     */   public void setPaintMode()
/*  729:     */   {
/*  730:1180 */     this.graphicsCanvas.setPaintMode();
/*  731:     */   }
/*  732:     */   
/*  733:     */   public void setTextBackgroundColor(Color color)
/*  734:     */   {
/*  735:1192 */     this.textBGColor = color;
/*  736:     */   }
/*  737:     */   
/*  738:     */   public void setTextBackgroundColour(Color colour)
/*  739:     */   {
/*  740:1204 */     setTextBackgroundColor(colour);
/*  741:     */   }
/*  742:     */   
/*  743:     */   public void setTextColor(Color color)
/*  744:     */   {
/*  745:1216 */     this.textColor = color;
/*  746:     */   }
/*  747:     */   
/*  748:     */   public void setTextColour(Color colour)
/*  749:     */   {
/*  750:1228 */     setTextColor(colour);
/*  751:     */   }
/*  752:     */   
/*  753:     */   public void setXORMode(Color xorColor)
/*  754:     */   {
/*  755:1239 */     this.graphicsCanvas.setXORMode(xorColor);
/*  756:     */   }
/*  757:     */   
/*  758:     */   public static void mainReturned()
/*  759:     */   {
/*  760:1245 */     mainReturned = true;
/*  761:     */   }
/*  762:     */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.Console
 * JD-Core Version:    0.7.0.1
 */