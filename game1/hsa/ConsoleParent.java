/*    1:     */ package game1.hsa;
/*    2:     */ 
/*    3:     */ import java.awt.Dimension;
/*    4:     */ import java.awt.Panel;
/*    5:     */ import java.awt.Point;
/*    6:     */ import java.awt.Toolkit;
/*    7:     */ import java.awt.datatransfer.Clipboard;
/*    8:     */ import java.awt.datatransfer.DataFlavor;
/*    9:     */ import java.awt.datatransfer.Transferable;
/*   10:     */ import java.awt.event.ActionEvent;
/*   11:     */ import java.awt.event.ActionListener;
/*   12:     */ import java.awt.event.FocusEvent;
/*   13:     */ import java.awt.event.FocusListener;
/*   14:     */ import java.awt.event.KeyEvent;
/*   15:     */ import java.awt.event.KeyListener;
/*   16:     */ import java.awt.event.WindowEvent;
/*   17:     */ import java.awt.event.WindowListener;
/*   18:     */ import java.text.DecimalFormat;
/*   19:     */ import java.text.NumberFormat;
/*   20:     */ 
/*   21:     */ abstract class ConsoleParent
/*   22:     */   implements ActionListener, FocusListener, KeyListener, WindowListener
/*   23:     */ {
/*   24:     */   protected static final String SAVE_COMMAND = "save";
/*   25:     */   protected static final String PRINT_COMMAND = "print";
/*   26:     */   protected static final String QUIT_COMMAND = "quit";
/*   27:     */   protected static final int DEFAULT_ROWS = 25;
/*   28:     */   protected static final int DEFAULT_COLUMNS = 80;
/*   29:     */   protected static final int DEFAULT_FONT_SIZE = 14;
/*   30:     */   protected static final String DEFAULT_TITLE = "Console";
/*   31:     */   protected static final int TAB_SIZE = 8;
/*   32:     */   protected ConsoleFrame window;
/*   33:  57 */   private static int numConsoles = 0;
/*   34:  59 */   protected int maxRow = 0;
/*   35:  60 */   protected int maxCol = 0;
/*   36:     */   protected static final int BUFFER_SIZE = 2048;
/*   37:     */   protected static final int EMPTY_BUFFER = -1;
/*   38:  64 */   protected char[] kbdBuffer = new char[2048];
/*   39:  65 */   protected int kbdBufferHead = 0;
/*   40:  65 */   protected int kbdBufferTail = 0;
/*   41:  66 */   protected char[] lineBuffer = new char[2048];
/*   42:  67 */   protected int lineBufferHead = 0;
/*   43:  67 */   protected int lineBufferTail = 0;
/*   44:  68 */   protected int ungotChar = -1;
/*   45:     */   private String baseWindowTitle;
/*   46:     */   protected Panel consoleCanvasPanelInner;
/*   47:     */   ConsoleCanvas consoleCanvas;
/*   48:  83 */   protected boolean eofReached = false;
/*   49:     */   
/*   50:     */   protected void initialize(int rows, int columns, int fontSize, String title)
/*   51:     */   {
/*   52:  94 */     numConsoles += 1;
/*   53:  97 */     if ((title.equals("Console")) && (numConsoles > 1)) {
/*   54:  99 */       title = title + " " + numConsoles;
/*   55:     */     }
/*   56: 101 */     this.baseWindowTitle = title;
/*   57:     */     
/*   58:     */ 
/*   59: 104 */     this.maxCol = columns;
/*   60: 105 */     this.maxRow = rows;
/*   61:     */     
/*   62:     */ 
/*   63: 108 */     this.window = new ConsoleFrame(this, this.consoleCanvasPanelInner);
/*   64:     */     
/*   65: 110 */     this.window.pack();
/*   66: 111 */     this.window.setResizable(false);
/*   67:     */     
/*   68: 113 */     setWindowTitle("Running");
/*   69:     */     
/*   70:     */ 
/*   71: 116 */     Dimension screenSize = 
/*   72: 117 */       Toolkit.getDefaultToolkit().getScreenSize();
/*   73:     */     
/*   74: 119 */     Point loc = new Point(screenSize.width - this.window.getWidth(), 0);
/*   75: 120 */     loc.x -= (numConsoles - 1) * 30;
/*   76: 121 */     loc.y += (numConsoles - 1) * 30;
/*   77: 122 */     this.window.setLocation(loc);
/*   78:     */     
/*   79:     */ 
/*   80: 125 */     this.window.toFront();
/*   81: 126 */     this.window.show();
/*   82:     */     try
/*   83:     */     {
/*   84: 133 */       Thread.sleep(100L);
/*   85:     */     }
/*   86:     */     catch (InterruptedException localInterruptedException) {}
/*   87: 142 */     this.consoleCanvas.requestFocus();
/*   88: 143 */     this.window.repaint();
/*   89:     */   }
/*   90:     */   
/*   91:     */   public void actionPerformed(ActionEvent e)
/*   92:     */   {
/*   93: 154 */     if (e.getActionCommand().equals("quit"))
/*   94:     */     {
/*   95: 156 */       quitProgram();
/*   96:     */     }
/*   97: 158 */     else if (e.getActionCommand().equals("save"))
/*   98:     */     {
/*   99: 160 */       this.consoleCanvas.saveContents();
/*  100: 161 */       this.consoleCanvas.requestFocus();
/*  101:     */     }
/*  102: 163 */     else if (e.getActionCommand().equals("print"))
/*  103:     */     {
/*  104: 165 */       this.consoleCanvas.printContents();
/*  105:     */     }
/*  106:     */   }
/*  107:     */   
/*  108:     */   public void close()
/*  109:     */   {
/*  110: 175 */     this.consoleCanvas.killCursorThread();
/*  111: 176 */     this.window.dispose();
/*  112:     */   }
/*  113:     */   
/*  114:     */   protected void enableButtons(boolean enable)
/*  115:     */   {
/*  116: 186 */     this.window.enableButtons(enable);
/*  117:     */   }
/*  118:     */   
/*  119:     */   public synchronized void focusGained(FocusEvent e)
/*  120:     */   {
/*  121: 199 */     this.consoleCanvas.requestFocus();
/*  122:     */   }
/*  123:     */   
/*  124:     */   public synchronized void focusLost(FocusEvent e) {}
/*  125:     */   
/*  126:     */   public synchronized void keyPressed(KeyEvent e)
/*  127:     */   {
/*  128: 220 */     if (!this.consoleCanvas.hasFocus) {
/*  129: 222 */       this.consoleCanvas.focusGained(null);
/*  130:     */     }
/*  131: 225 */     char ch = e.getKeyChar();
/*  132: 228 */     if (((' ' <= ch) && (ch <= '~')) || (ch == '\b') || 
/*  133: 229 */       (ch == '\t') || (ch == '\n') || (ch == '\025'))
/*  134:     */     {
/*  135: 232 */       this.kbdBuffer[this.kbdBufferHead] = e.getKeyChar();
/*  136: 233 */       this.kbdBufferHead = ((this.kbdBufferHead + 1) % this.kbdBuffer.length);
/*  137:     */       
/*  138:     */ 
/*  139:     */ 
/*  140: 237 */       notify();
/*  141:     */     }
/*  142: 241 */     else if (ch == '\023')
/*  143:     */     {
/*  144: 243 */       this.consoleCanvas.saveContents();
/*  145:     */     }
/*  146: 247 */     else if (ch == '\020')
/*  147:     */     {
/*  148: 249 */       this.consoleCanvas.printContents();
/*  149:     */     }
/*  150: 253 */     else if (ch == '\021')
/*  151:     */     {
/*  152: 255 */       quitProgram();
/*  153:     */     }
/*  154: 259 */     else if (ch == '\026')
/*  155:     */     {
/*  156: 261 */       Transferable clipData = 
/*  157: 262 */         Toolkit.getDefaultToolkit().getSystemClipboard().getContents(this);
/*  158:     */       try
/*  159:     */       {
/*  160: 266 */         String s = 
/*  161: 267 */           (String)clipData.getTransferData(DataFlavor.stringFlavor);
/*  162: 268 */         int bufferUsed = (this.kbdBufferHead - this.kbdBufferTail + this.kbdBuffer.length) % this.kbdBuffer.length;
/*  163: 269 */         if (s.length() > this.kbdBuffer.length - bufferUsed)
/*  164:     */         {
/*  165: 272 */           this.consoleCanvas.invertScreen();
/*  166:     */         }
/*  167:     */         else
/*  168:     */         {
/*  169: 276 */           for (int cnt = 0; cnt < s.length(); cnt++)
/*  170:     */           {
/*  171: 279 */             ch = s.charAt(cnt);
/*  172: 282 */             if (((' ' <= ch) && (ch <= '~')) || (ch == '\n'))
/*  173:     */             {
/*  174: 284 */               this.kbdBuffer[this.kbdBufferHead] = ch;
/*  175: 285 */               this.kbdBufferHead = ((this.kbdBufferHead + 1) % this.kbdBuffer.length);
/*  176:     */             }
/*  177:     */           }
/*  178: 288 */           notify();
/*  179:     */         }
/*  180:     */       }
/*  181:     */       catch (Exception exception)
/*  182:     */       {
/*  183: 293 */         this.consoleCanvas.invertScreen();
/*  184:     */       }
/*  185:     */     }
/*  186: 298 */     e.consume();
/*  187:     */   }
/*  188:     */   
/*  189:     */   public void keyReleased(KeyEvent e) {}
/*  190:     */   
/*  191:     */   public void keyTyped(KeyEvent e) {}
/*  192:     */   
/*  193:     */   protected void mainStopped()
/*  194:     */   {
/*  195: 327 */     this.window.mainStopped();
/*  196: 328 */     setWindowTitle("Finished Execution");
/*  197:     */   }
/*  198:     */   
/*  199:     */   public void print(byte number)
/*  200:     */   {
/*  201: 340 */     print(number);
/*  202:     */   }
/*  203:     */   
/*  204:     */   public void print(byte number, int fieldSize)
/*  205:     */   {
/*  206: 353 */     print(number, fieldSize);
/*  207:     */   }
/*  208:     */   
/*  209:     */   public void print(char ch)
/*  210:     */   {
/*  211: 364 */     print(String.valueOf(ch));
/*  212:     */   }
/*  213:     */   
/*  214:     */   public void print(char ch, int fieldSize)
/*  215:     */   {
/*  216: 376 */     String charStr = String.valueOf(ch);
/*  217: 377 */     StringBuffer padding = new StringBuffer();
/*  218: 379 */     for (int cnt = 0; cnt < fieldSize - charStr.length(); cnt++) {
/*  219: 381 */       padding.append(' ');
/*  220:     */     }
/*  221: 383 */     print(charStr + padding);
/*  222:     */   }
/*  223:     */   
/*  224:     */   public void print(double number)
/*  225:     */   {
/*  226: 395 */     print(String.valueOf(number));
/*  227:     */   }
/*  228:     */   
/*  229:     */   public void print(double number, int fieldSize)
/*  230:     */   {
/*  231: 408 */     double posValue = Math.abs(number);
/*  232: 409 */     int placesRemaining = fieldSize;
/*  233: 410 */     String format = null;
/*  234: 411 */     StringBuffer padding = new StringBuffer();
/*  235: 413 */     if (number < 0.0D) {
/*  236: 414 */       placesRemaining--;
/*  237:     */     }
/*  238: 415 */     if (posValue < 10.0D) {
/*  239: 416 */       format = "0";
/*  240: 417 */     } else if (posValue < 100.0D) {
/*  241: 418 */       format = "00";
/*  242: 419 */     } else if (posValue < 1000.0D) {
/*  243: 420 */       format = "000";
/*  244: 421 */     } else if (posValue < 10000.0D) {
/*  245: 422 */       format = "0000";
/*  246: 423 */     } else if (posValue < 100000.0D) {
/*  247: 424 */       format = "00000";
/*  248: 425 */     } else if (posValue < 1000000.0D) {
/*  249: 426 */       format = "000000";
/*  250: 427 */     } else if (posValue < 10000000.0D) {
/*  251: 428 */       format = "0000000";
/*  252: 429 */     } else if (posValue < 100000000.0D) {
/*  253: 430 */       format = "00000000";
/*  254:     */     }
/*  255:     */     String numStr;
/*  256:     */     String numStr;
/*  257: 432 */     if (format == null)
/*  258:     */     {
/*  259: 435 */       numStr = String.valueOf(number);
/*  260:     */     }
/*  261:     */     else
/*  262:     */     {
/*  263: 440 */       placesRemaining -= format.length();
/*  264: 441 */       if (placesRemaining > 0)
/*  265:     */       {
/*  266: 443 */         format = format + ".";
/*  267: 444 */         placesRemaining--;
/*  268:     */       }
/*  269: 448 */       for (int cnt = 0; cnt < placesRemaining; cnt++) {
/*  270: 450 */         format = format + "#";
/*  271:     */       }
/*  272: 454 */       NumberFormat form = new DecimalFormat(format);
/*  273: 455 */       numStr = form.format(number);
/*  274:     */     }
/*  275: 459 */     for (int cnt = 0; cnt < fieldSize - numStr.length(); cnt++) {
/*  276: 461 */       padding.append(' ');
/*  277:     */     }
/*  278: 463 */     print(padding + numStr);
/*  279:     */   }
/*  280:     */   
/*  281:     */   public void print(double number, int fieldSize, int decimalPlaces)
/*  282:     */   {
/*  283: 479 */     double posValue = Math.abs(number);
/*  284: 480 */     int placesRemaining = fieldSize;
/*  285: 481 */     String format = null;
/*  286: 482 */     StringBuffer padding = new StringBuffer();
/*  287: 484 */     if (number < 0.0D) {
/*  288: 485 */       placesRemaining--;
/*  289:     */     }
/*  290: 486 */     if (posValue < 10.0D) {
/*  291: 487 */       format = "0";
/*  292: 488 */     } else if (posValue < 100.0D) {
/*  293: 489 */       format = "00";
/*  294: 490 */     } else if (posValue < 1000.0D) {
/*  295: 491 */       format = "000";
/*  296: 492 */     } else if (posValue < 10000.0D) {
/*  297: 493 */       format = "0000";
/*  298: 494 */     } else if (posValue < 100000.0D) {
/*  299: 495 */       format = "00000";
/*  300: 496 */     } else if (posValue < 1000000.0D) {
/*  301: 497 */       format = "000000";
/*  302: 498 */     } else if (posValue < 10000000.0D) {
/*  303: 499 */       format = "0000000";
/*  304: 500 */     } else if (posValue < 100000000.0D) {
/*  305: 501 */       format = "00000000";
/*  306:     */     }
/*  307:     */     String numStr;
/*  308:     */     String numStr;
/*  309: 503 */     if (Math.abs(number) >= 100000000.0D)
/*  310:     */     {
/*  311: 506 */       numStr = String.valueOf(number);
/*  312:     */     }
/*  313:     */     else
/*  314:     */     {
/*  315: 510 */       format = "0.";
/*  316: 513 */       for (int cnt = 0; cnt < decimalPlaces; cnt++) {
/*  317: 515 */         format = format + "0";
/*  318:     */       }
/*  319: 519 */       NumberFormat form = new DecimalFormat(format);
/*  320: 520 */       form.setMinimumIntegerDigits(1);
/*  321: 521 */       numStr = form.format(number);
/*  322:     */     }
/*  323: 525 */     for (int cnt = 0; cnt < fieldSize - numStr.length(); cnt++) {
/*  324: 527 */       padding.append(' ');
/*  325:     */     }
/*  326: 529 */     print(padding + numStr);
/*  327:     */   }
/*  328:     */   
/*  329:     */   public void print(float number)
/*  330:     */   {
/*  331: 540 */     print(String.valueOf(number));
/*  332:     */   }
/*  333:     */   
/*  334:     */   public void print(float number, int fieldSize)
/*  335:     */   {
/*  336: 553 */     print(number, fieldSize);
/*  337:     */   }
/*  338:     */   
/*  339:     */   public void print(float number, int fieldSize, int decimalPlaces)
/*  340:     */   {
/*  341: 568 */     print(number, fieldSize, decimalPlaces);
/*  342:     */   }
/*  343:     */   
/*  344:     */   public void print(int number)
/*  345:     */   {
/*  346: 580 */     print(String.valueOf(number));
/*  347:     */   }
/*  348:     */   
/*  349:     */   public void print(int number, int fieldSize)
/*  350:     */   {
/*  351: 593 */     String numStr = String.valueOf(number);
/*  352: 594 */     StringBuffer padding = new StringBuffer();
/*  353: 596 */     for (int cnt = 0; cnt < fieldSize - numStr.length(); cnt++) {
/*  354: 598 */       padding.append(' ');
/*  355:     */     }
/*  356: 600 */     print(padding + numStr);
/*  357:     */   }
/*  358:     */   
/*  359:     */   public void print(long number)
/*  360:     */   {
/*  361: 612 */     print(String.valueOf(number));
/*  362:     */   }
/*  363:     */   
/*  364:     */   public void print(long number, int fieldSize)
/*  365:     */   {
/*  366: 625 */     String numStr = String.valueOf(number);
/*  367: 626 */     StringBuffer padding = new StringBuffer();
/*  368: 628 */     for (int cnt = 0; cnt < fieldSize - numStr.length(); cnt++) {
/*  369: 630 */       padding.append(' ');
/*  370:     */     }
/*  371: 632 */     print(padding + numStr);
/*  372:     */   }
/*  373:     */   
/*  374:     */   public abstract void print(String paramString);
/*  375:     */   
/*  376:     */   public void print(String text, int fieldSize)
/*  377:     */   {
/*  378: 654 */     StringBuffer padding = new StringBuffer();
/*  379: 656 */     for (int cnt = 0; cnt < fieldSize - text.length(); cnt++) {
/*  380: 658 */       padding.append(' ');
/*  381:     */     }
/*  382: 660 */     print(text + padding);
/*  383:     */   }
/*  384:     */   
/*  385:     */   public void print(short number)
/*  386:     */   {
/*  387: 672 */     print(number);
/*  388:     */   }
/*  389:     */   
/*  390:     */   public void print(short number, int fieldSize)
/*  391:     */   {
/*  392: 685 */     print(number, fieldSize);
/*  393:     */   }
/*  394:     */   
/*  395:     */   public void print(boolean value)
/*  396:     */   {
/*  397: 696 */     print(String.valueOf(value));
/*  398:     */   }
/*  399:     */   
/*  400:     */   public void print(boolean value, int fieldSize)
/*  401:     */   {
/*  402: 709 */     String boolStr = String.valueOf(value);
/*  403: 710 */     StringBuffer padding = new StringBuffer();
/*  404: 712 */     for (int cnt = 0; cnt < fieldSize - boolStr.length(); cnt++) {
/*  405: 714 */       padding.append(' ');
/*  406:     */     }
/*  407: 716 */     print(boolStr + padding);
/*  408:     */   }
/*  409:     */   
/*  410:     */   public void println()
/*  411:     */   {
/*  412: 725 */     print("\n");
/*  413:     */   }
/*  414:     */   
/*  415:     */   public void println(byte number)
/*  416:     */   {
/*  417: 737 */     print(number);
/*  418: 738 */     print("\n");
/*  419:     */   }
/*  420:     */   
/*  421:     */   public void println(byte number, int fieldSize)
/*  422:     */   {
/*  423: 751 */     print(number, fieldSize);
/*  424: 752 */     print("\n");
/*  425:     */   }
/*  426:     */   
/*  427:     */   public void println(char ch)
/*  428:     */   {
/*  429: 763 */     print(ch);
/*  430: 764 */     print("\n");
/*  431:     */   }
/*  432:     */   
/*  433:     */   public void println(char ch, int fieldSize)
/*  434:     */   {
/*  435: 776 */     print(ch, fieldSize);
/*  436: 777 */     print("\n");
/*  437:     */   }
/*  438:     */   
/*  439:     */   public void println(double number)
/*  440:     */   {
/*  441: 789 */     print(number);
/*  442: 790 */     print("\n");
/*  443:     */   }
/*  444:     */   
/*  445:     */   public void println(double number, int fieldSize)
/*  446:     */   {
/*  447: 803 */     print(number, fieldSize);
/*  448: 804 */     print("\n");
/*  449:     */   }
/*  450:     */   
/*  451:     */   public void println(double number, int fieldSize, int decimalPlaces)
/*  452:     */   {
/*  453: 820 */     print(number, fieldSize, decimalPlaces);
/*  454: 821 */     print("\n");
/*  455:     */   }
/*  456:     */   
/*  457:     */   public void println(float number)
/*  458:     */   {
/*  459: 833 */     print(number);
/*  460: 834 */     print("\n");
/*  461:     */   }
/*  462:     */   
/*  463:     */   public void println(float number, int fieldSize)
/*  464:     */   {
/*  465: 847 */     print(number, fieldSize);
/*  466: 848 */     print("\n");
/*  467:     */   }
/*  468:     */   
/*  469:     */   public void println(float number, int fieldSize, int decimalPlaces)
/*  470:     */   {
/*  471: 864 */     print(number, fieldSize, decimalPlaces);
/*  472: 865 */     print("\n");
/*  473:     */   }
/*  474:     */   
/*  475:     */   public void println(int number)
/*  476:     */   {
/*  477: 877 */     print(number);
/*  478: 878 */     print("\n");
/*  479:     */   }
/*  480:     */   
/*  481:     */   public void println(int number, int fieldSize)
/*  482:     */   {
/*  483: 891 */     print(number, fieldSize);
/*  484: 892 */     print("\n");
/*  485:     */   }
/*  486:     */   
/*  487:     */   public void println(long number)
/*  488:     */   {
/*  489: 904 */     print(number);
/*  490: 905 */     print("\n");
/*  491:     */   }
/*  492:     */   
/*  493:     */   public void println(long number, int fieldSize)
/*  494:     */   {
/*  495: 918 */     print(number, fieldSize);
/*  496: 919 */     print("\n");
/*  497:     */   }
/*  498:     */   
/*  499:     */   public void println(String text)
/*  500:     */   {
/*  501: 930 */     print(text);
/*  502: 931 */     print("\n");
/*  503:     */   }
/*  504:     */   
/*  505:     */   public void println(String text, int fieldSize)
/*  506:     */   {
/*  507: 944 */     print(text, fieldSize);
/*  508: 945 */     print("\n");
/*  509:     */   }
/*  510:     */   
/*  511:     */   public void println(short number)
/*  512:     */   {
/*  513: 957 */     print(number);
/*  514: 958 */     print("\n");
/*  515:     */   }
/*  516:     */   
/*  517:     */   public void println(short number, int fieldSize)
/*  518:     */   {
/*  519: 971 */     print(number, fieldSize);
/*  520: 972 */     print("\n");
/*  521:     */   }
/*  522:     */   
/*  523:     */   public void println(boolean value)
/*  524:     */   {
/*  525: 984 */     print(value);
/*  526: 985 */     print("\n");
/*  527:     */   }
/*  528:     */   
/*  529:     */   public void println(boolean value, int fieldSize)
/*  530:     */   {
/*  531: 998 */     print(value, fieldSize);
/*  532: 999 */     print("\n");
/*  533:     */   }
/*  534:     */   
/*  535:     */   protected void quitProgram()
/*  536:     */   {
/*  537:1008 */     this.window.setVisible(false);
/*  538:1009 */     System.exit(0);
/*  539:     */   }
/*  540:     */   
/*  541:     */   public boolean readBoolean()
/*  542:     */   {
/*  543:1024 */     String s = readToken().toLowerCase();
/*  544:1025 */     if (s.equals("true")) {
/*  545:1027 */       return true;
/*  546:     */     }
/*  547:1029 */     if (s.equals("false")) {
/*  548:1031 */       return false;
/*  549:     */     }
/*  550:1035 */     new FatalError("Unable to convert \"" + s + "\" to a boolean", this.window);
/*  551:     */     
/*  552:     */ 
/*  553:1038 */     return false;
/*  554:     */   }
/*  555:     */   
/*  556:     */   public byte readByte()
/*  557:     */   {
/*  558:1050 */     String s = readToken();
/*  559:     */     try
/*  560:     */     {
/*  561:1054 */       return Byte.parseByte(s);
/*  562:     */     }
/*  563:     */     catch (NumberFormatException e)
/*  564:     */     {
/*  565:1058 */       new FatalError("Unable to convert \"" + s + "\" to a byte", this.window);
/*  566:     */     }
/*  567:1061 */     return 0;
/*  568:     */   }
/*  569:     */   
/*  570:     */   public abstract char readChar();
/*  571:     */   
/*  572:     */   public double readDouble()
/*  573:     */   {
/*  574:1084 */     String s = readToken();
/*  575:     */     try
/*  576:     */     {
/*  577:1087 */       Double d = Double.valueOf(s);
/*  578:1088 */       return d.doubleValue();
/*  579:     */     }
/*  580:     */     catch (NumberFormatException e)
/*  581:     */     {
/*  582:1092 */       new FatalError("Unable to convert \"" + s + "\" to a double", this.window);
/*  583:     */     }
/*  584:1095 */     return 0.0D;
/*  585:     */   }
/*  586:     */   
/*  587:     */   public float readFloat()
/*  588:     */   {
/*  589:1109 */     String s = readToken();
/*  590:     */     try
/*  591:     */     {
/*  592:1112 */       Float f = Float.valueOf(s);
/*  593:1113 */       return f.floatValue();
/*  594:     */     }
/*  595:     */     catch (NumberFormatException e)
/*  596:     */     {
/*  597:1117 */       new FatalError("Unable to convert \"" + s + "\" to a float", this.window);
/*  598:     */     }
/*  599:1120 */     return 0.0F;
/*  600:     */   }
/*  601:     */   
/*  602:     */   public int readInt()
/*  603:     */   {
/*  604:1133 */     String s = readToken();
/*  605:     */     try
/*  606:     */     {
/*  607:1137 */       return Integer.parseInt(s);
/*  608:     */     }
/*  609:     */     catch (NumberFormatException e)
/*  610:     */     {
/*  611:1141 */       new FatalError("Unable to convert \"" + s + "\" to a int", this.window);
/*  612:     */     }
/*  613:1144 */     return 0;
/*  614:     */   }
/*  615:     */   
/*  616:     */   public String readLine()
/*  617:     */   {
/*  618:1156 */     String s = "";
/*  619:     */     char ch;
/*  620:     */     do
/*  621:     */     {
/*  622:1161 */       ch = readChar();
/*  623:1163 */     } while (ch == ' ');
/*  624:1165 */     if (ch == '\n') {
/*  625:1167 */       ch = readChar();
/*  626:     */     }
/*  627:1170 */     while (ch != '\n')
/*  628:     */     {
/*  629:1172 */       s = s + ch;
/*  630:1173 */       ch = readChar();
/*  631:     */     }
/*  632:1176 */     return s;
/*  633:     */   }
/*  634:     */   
/*  635:     */   public long readLong()
/*  636:     */   {
/*  637:1187 */     String s = readToken();
/*  638:     */     try
/*  639:     */     {
/*  640:1191 */       return Long.parseLong(s);
/*  641:     */     }
/*  642:     */     catch (NumberFormatException e)
/*  643:     */     {
/*  644:1195 */       new FatalError("Unable to convert \"" + s + "\" to a long", this.window);
/*  645:     */     }
/*  646:1198 */     return 0L;
/*  647:     */   }
/*  648:     */   
/*  649:     */   public short readShort()
/*  650:     */   {
/*  651:1210 */     String s = readToken();
/*  652:     */     try
/*  653:     */     {
/*  654:1214 */       return Short.parseShort(s);
/*  655:     */     }
/*  656:     */     catch (NumberFormatException e)
/*  657:     */     {
/*  658:1218 */       new FatalError("Unable to convert \"" + s + "\" to a short", this.window);
/*  659:     */     }
/*  660:1221 */     return 0;
/*  661:     */   }
/*  662:     */   
/*  663:     */   public String readString()
/*  664:     */   {
/*  665:1232 */     return readToken();
/*  666:     */   }
/*  667:     */   
/*  668:     */   protected String readToken()
/*  669:     */   {
/*  670:1244 */     StringBuffer sb = new StringBuffer();
/*  671:     */     char ch;
/*  672:     */     do
/*  673:     */     {
/*  674:1249 */       ch = readChar();
/*  675:1251 */     } while ((ch == ' ') || (ch == '\n') || (ch == '\t'));
/*  676:1253 */     if (ch == '"')
/*  677:     */     {
/*  678:1256 */       ch = readChar();
/*  679:1257 */       while (ch != '"')
/*  680:     */       {
/*  681:1259 */         sb.append(ch);
/*  682:     */         
/*  683:1261 */         ch = readChar();
/*  684:1262 */         if (ch == '\n') {
/*  685:1264 */           new FatalError("No terminating quote for quoted string");
/*  686:     */         }
/*  687:     */       }
/*  688:1270 */       ch = readChar();
/*  689:     */     }
/*  690:     */     else
/*  691:     */     {
/*  692:     */       do
/*  693:     */       {
/*  694:1276 */         sb.append(ch);
/*  695:     */         
/*  696:1278 */         ch = readChar();
/*  697:1280 */         if ((ch == ' ') || (ch == '\n')) {
/*  698:     */           break;
/*  699:     */         }
/*  700:1280 */       } while (ch != '\t');
/*  701:     */     }
/*  702:1284 */     while ((ch == ' ') || (ch == '\t')) {
/*  703:1286 */       ch = readChar();
/*  704:     */     }
/*  705:1289 */     if (ch != '\n') {
/*  706:1291 */       this.ungotChar = ch;
/*  707:     */     }
/*  708:1294 */     return new String(sb);
/*  709:     */   }
/*  710:     */   
/*  711:     */   protected void setWindowTitle(String s)
/*  712:     */   {
/*  713:1305 */     this.window.setTitle(this.baseWindowTitle + " - " + s);
/*  714:     */   }
/*  715:     */   
/*  716:     */   public void windowActivated(WindowEvent e) {}
/*  717:     */   
/*  718:     */   public void windowClosed(WindowEvent e) {}
/*  719:     */   
/*  720:     */   public void windowClosing(WindowEvent e)
/*  721:     */   {
/*  722:1340 */     quitProgram();
/*  723:     */   }
/*  724:     */   
/*  725:     */   public void windowDeactivated(WindowEvent e) {}
/*  726:     */   
/*  727:     */   public void windowDeiconified(WindowEvent e) {}
/*  728:     */   
/*  729:     */   public void windowIconified(WindowEvent e) {}
/*  730:     */   
/*  731:     */   public void windowOpened(WindowEvent e) {}
/*  732:     */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.ConsoleParent
 * JD-Core Version:    0.7.0.1
 */