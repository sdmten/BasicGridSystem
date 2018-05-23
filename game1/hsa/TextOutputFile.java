/*   1:    */ package game1.hsa;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileWriter;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import java.io.PrintWriter;
/*   8:    */ import java.text.DecimalFormat;
/*   9:    */ import java.text.NumberFormat;
/*  10:    */ 
/*  11:    */ public class TextOutputFile
/*  12:    */ {
/*  13:    */   protected String fileName;
/*  14: 28 */   protected PrintWriter f = null;
/*  15: 33 */   protected boolean closed = false;
/*  16: 38 */   protected boolean useStandardIO = false;
/*  17: 43 */   protected String lineSeparator = System.getProperty("line.separator");
/*  18:    */   
/*  19:    */   public TextOutputFile()
/*  20:    */   {
/*  21: 51 */     this("Standard output");
/*  22:    */   }
/*  23:    */   
/*  24:    */   public TextOutputFile(File file)
/*  25:    */   {
/*  26: 62 */     this(file, false);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public TextOutputFile(File file, boolean append)
/*  30:    */   {
/*  31:    */     try
/*  32:    */     {
/*  33: 76 */       if (append) {
/*  34: 78 */         this.f = new PrintWriter(new FileWriter(file.getName(), append));
/*  35:    */       } else {
/*  36: 82 */         this.f = new PrintWriter(new FileWriter(file));
/*  37:    */       }
/*  38:    */     }
/*  39:    */     catch (IOException e)
/*  40:    */     {
/*  41: 87 */       new FatalError("Unable to open file \"" + this.fileName + "\"");
/*  42:    */     }
/*  43: 90 */     this.fileName = file.getName();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public TextOutputFile(String fileName)
/*  47:    */   {
/*  48:101 */     this(fileName, false);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public TextOutputFile(String fileName, boolean append)
/*  52:    */   {
/*  53:113 */     if ((fileName.equalsIgnoreCase("standard output")) || 
/*  54:114 */       (fileName.equalsIgnoreCase("screen")) || 
/*  55:115 */       (fileName.equalsIgnoreCase("stdout")))
/*  56:    */     {
/*  57:117 */       this.fileName = "Standard output";
/*  58:118 */       this.useStandardIO = true;
/*  59:    */     }
/*  60:    */     else
/*  61:    */     {
/*  62:    */       try
/*  63:    */       {
/*  64:124 */         this.f = new PrintWriter(new FileWriter(fileName, append));
/*  65:    */       }
/*  66:    */       catch (IOException e)
/*  67:    */       {
/*  68:128 */         new FatalError("Unable to open file \"" + fileName + "\"");
/*  69:    */       }
/*  70:131 */       this.fileName = fileName;
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void close()
/*  75:    */   {
/*  76:141 */     if (this.closed) {
/*  77:143 */       new FatalError("\"" + this.fileName + "\" is already closed.");
/*  78:    */     }
/*  79:148 */     if (this.useStandardIO)
/*  80:    */     {
/*  81:150 */       this.useStandardIO = false;
/*  82:    */     }
/*  83:    */     else
/*  84:    */     {
/*  85:154 */       this.f.close();
/*  86:155 */       if (this.f.checkError()) {
/*  87:157 */         new FatalError("Close failed: Unable to close \"" + this.fileName + "\"");
/*  88:    */       }
/*  89:160 */       this.f = null;
/*  90:    */     }
/*  91:163 */     this.closed = true;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean isStandardOut()
/*  95:    */   {
/*  96:169 */     return this.useStandardIO;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void print(byte number)
/* 100:    */   {
/* 101:181 */     print(number);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void print(byte number, int fieldSize)
/* 105:    */   {
/* 106:194 */     print(number, fieldSize);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void print(char ch)
/* 110:    */   {
/* 111:205 */     print(String.valueOf(ch));
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void print(char ch, int fieldSize)
/* 115:    */   {
/* 116:217 */     String charStr = String.valueOf(ch);
/* 117:218 */     StringBuffer padding = new StringBuffer();
/* 118:220 */     for (int cnt = 0; cnt < fieldSize - charStr.length(); cnt++) {
/* 119:222 */       padding.append(' ');
/* 120:    */     }
/* 121:224 */     print(charStr + padding);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void print(double number)
/* 125:    */   {
/* 126:236 */     print(String.valueOf(number));
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void print(double number, int fieldSize)
/* 130:    */   {
/* 131:249 */     double posValue = Math.abs(number);
/* 132:250 */     int placesRemaining = fieldSize;
/* 133:251 */     String format = null;
/* 134:252 */     StringBuffer padding = new StringBuffer();
/* 135:254 */     if (number < 0.0D) {
/* 136:255 */       placesRemaining--;
/* 137:    */     }
/* 138:256 */     if (posValue < 10.0D) {
/* 139:257 */       format = "0";
/* 140:258 */     } else if (posValue < 100.0D) {
/* 141:259 */       format = "00";
/* 142:260 */     } else if (posValue < 1000.0D) {
/* 143:261 */       format = "000";
/* 144:262 */     } else if (posValue < 10000.0D) {
/* 145:263 */       format = "0000";
/* 146:264 */     } else if (posValue < 100000.0D) {
/* 147:265 */       format = "00000";
/* 148:266 */     } else if (posValue < 1000000.0D) {
/* 149:267 */       format = "000000";
/* 150:268 */     } else if (posValue < 10000000.0D) {
/* 151:269 */       format = "0000000";
/* 152:270 */     } else if (posValue < 100000000.0D) {
/* 153:271 */       format = "00000000";
/* 154:    */     }
/* 155:    */     String numStr;
/* 156:    */     String numStr;
/* 157:273 */     if (format == null)
/* 158:    */     {
/* 159:276 */       numStr = String.valueOf(number);
/* 160:    */     }
/* 161:    */     else
/* 162:    */     {
/* 163:281 */       placesRemaining -= format.length();
/* 164:282 */       if (placesRemaining > 0)
/* 165:    */       {
/* 166:284 */         format = format + ".";
/* 167:285 */         placesRemaining--;
/* 168:    */       }
/* 169:289 */       for (int cnt = 0; cnt < placesRemaining; cnt++) {
/* 170:291 */         format = format + "#";
/* 171:    */       }
/* 172:295 */       NumberFormat form = new DecimalFormat(format);
/* 173:296 */       numStr = form.format(number);
/* 174:    */     }
/* 175:300 */     for (int cnt = 0; cnt < fieldSize - numStr.length(); cnt++) {
/* 176:302 */       padding.append(' ');
/* 177:    */     }
/* 178:304 */     print(padding + numStr);
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void print(double number, int fieldSize, int decimalPlaces)
/* 182:    */   {
/* 183:320 */     double posValue = Math.abs(number);
/* 184:321 */     int placesRemaining = fieldSize;
/* 185:322 */     String format = null;
/* 186:323 */     StringBuffer padding = new StringBuffer();
/* 187:325 */     if (number < 0.0D) {
/* 188:326 */       placesRemaining--;
/* 189:    */     }
/* 190:327 */     if (posValue < 10.0D) {
/* 191:328 */       format = "0";
/* 192:329 */     } else if (posValue < 100.0D) {
/* 193:330 */       format = "00";
/* 194:331 */     } else if (posValue < 1000.0D) {
/* 195:332 */       format = "000";
/* 196:333 */     } else if (posValue < 10000.0D) {
/* 197:334 */       format = "0000";
/* 198:335 */     } else if (posValue < 100000.0D) {
/* 199:336 */       format = "00000";
/* 200:337 */     } else if (posValue < 1000000.0D) {
/* 201:338 */       format = "000000";
/* 202:339 */     } else if (posValue < 10000000.0D) {
/* 203:340 */       format = "0000000";
/* 204:341 */     } else if (posValue < 100000000.0D) {
/* 205:342 */       format = "00000000";
/* 206:    */     }
/* 207:    */     String numStr;
/* 208:    */     String numStr;
/* 209:344 */     if (Math.abs(number) >= 100000000.0D)
/* 210:    */     {
/* 211:347 */       numStr = String.valueOf(number);
/* 212:    */     }
/* 213:    */     else
/* 214:    */     {
/* 215:351 */       format = "0.";
/* 216:354 */       for (int cnt = 0; cnt < decimalPlaces; cnt++) {
/* 217:356 */         format = format + "0";
/* 218:    */       }
/* 219:360 */       NumberFormat form = new DecimalFormat(format);
/* 220:361 */       form.setMinimumIntegerDigits(1);
/* 221:362 */       numStr = form.format(number);
/* 222:    */     }
/* 223:366 */     for (int cnt = 0; cnt < fieldSize - numStr.length(); cnt++) {
/* 224:368 */       padding.append(' ');
/* 225:    */     }
/* 226:370 */     print(padding + numStr);
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void print(float number)
/* 230:    */   {
/* 231:381 */     print(String.valueOf(number));
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void print(float number, int fieldSize)
/* 235:    */   {
/* 236:394 */     print(number, fieldSize);
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void print(float number, int fieldSize, int decimalPlaces)
/* 240:    */   {
/* 241:409 */     print(number, fieldSize, decimalPlaces);
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void print(int number)
/* 245:    */   {
/* 246:421 */     print(String.valueOf(number));
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void print(int number, int fieldSize)
/* 250:    */   {
/* 251:434 */     String numStr = String.valueOf(number);
/* 252:435 */     StringBuffer padding = new StringBuffer();
/* 253:437 */     for (int cnt = 0; cnt < fieldSize - numStr.length(); cnt++) {
/* 254:439 */       padding.append(' ');
/* 255:    */     }
/* 256:441 */     print(padding + numStr);
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void print(long number)
/* 260:    */   {
/* 261:453 */     print(String.valueOf(number));
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void print(long number, int fieldSize)
/* 265:    */   {
/* 266:466 */     String numStr = String.valueOf(number);
/* 267:467 */     StringBuffer padding = new StringBuffer();
/* 268:469 */     for (int cnt = 0; cnt < fieldSize - numStr.length(); cnt++) {
/* 269:471 */       padding.append(' ');
/* 270:    */     }
/* 271:473 */     print(padding + numStr);
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void print(String text)
/* 275:    */   {
/* 276:485 */     if (!this.useStandardIO)
/* 277:    */     {
/* 278:487 */       int newLinePosition = 0;
/* 279:488 */       while (text.indexOf('\n', newLinePosition) != -1)
/* 280:    */       {
/* 281:490 */         newLinePosition = text.indexOf('\n', newLinePosition);
/* 282:491 */         text = text.substring(0, newLinePosition) + this.lineSeparator + 
/* 283:492 */           text.substring(newLinePosition + 1);
/* 284:493 */         newLinePosition += this.lineSeparator.length();
/* 285:    */       }
/* 286:    */     }
/* 287:498 */     if (this.closed) {
/* 288:500 */       new FatalError("Write failed: \"" + this.fileName + "\" is already closed.");
/* 289:    */     }
/* 290:504 */     if (this.useStandardIO)
/* 291:    */     {
/* 292:507 */       System.out.print(text);
/* 293:508 */       System.out.flush();
/* 294:510 */       if (System.out.checkError()) {
/* 295:512 */         new FatalError("Write failed: Unable to write to \"" + this.fileName + "\"");
/* 296:    */       }
/* 297:    */     }
/* 298:    */     else
/* 299:    */     {
/* 300:519 */       this.f.print(text);
/* 301:521 */       if (this.f.checkError()) {
/* 302:523 */         new FatalError("Write failed: Unable to write to \"" + this.fileName + "\"");
/* 303:    */       }
/* 304:    */     }
/* 305:    */   }
/* 306:    */   
/* 307:    */   public void print(String text, int fieldSize)
/* 308:    */   {
/* 309:538 */     StringBuffer padding = new StringBuffer();
/* 310:539 */     for (int cnt = 0; cnt < Math.abs(fieldSize) - text.length(); cnt++) {
/* 311:541 */       padding.append(' ');
/* 312:    */     }
/* 313:543 */     if (fieldSize >= 0) {
/* 314:545 */       print(text + padding);
/* 315:    */     } else {
/* 316:549 */       print(padding + text);
/* 317:    */     }
/* 318:    */   }
/* 319:    */   
/* 320:    */   public void print(short number)
/* 321:    */   {
/* 322:562 */     print(number);
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void print(short number, int fieldSize)
/* 326:    */   {
/* 327:575 */     print(number, fieldSize);
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void print(boolean value)
/* 331:    */   {
/* 332:586 */     print(String.valueOf(value));
/* 333:    */   }
/* 334:    */   
/* 335:    */   public void print(boolean value, int fieldSize)
/* 336:    */   {
/* 337:599 */     String boolStr = String.valueOf(value);
/* 338:600 */     StringBuffer padding = new StringBuffer();
/* 339:602 */     for (int cnt = 0; cnt < fieldSize - boolStr.length(); cnt++) {
/* 340:604 */       padding.append(' ');
/* 341:    */     }
/* 342:606 */     print(boolStr + padding);
/* 343:    */   }
/* 344:    */   
/* 345:    */   public void println()
/* 346:    */   {
/* 347:615 */     print("\n");
/* 348:    */   }
/* 349:    */   
/* 350:    */   public void println(byte number)
/* 351:    */   {
/* 352:627 */     print(number);
/* 353:628 */     print("\n");
/* 354:    */   }
/* 355:    */   
/* 356:    */   public void println(byte number, int fieldSize)
/* 357:    */   {
/* 358:641 */     print(number, fieldSize);
/* 359:642 */     print("\n");
/* 360:    */   }
/* 361:    */   
/* 362:    */   public void println(char ch)
/* 363:    */   {
/* 364:653 */     print(ch);
/* 365:654 */     print("\n");
/* 366:    */   }
/* 367:    */   
/* 368:    */   public void println(char ch, int fieldSize)
/* 369:    */   {
/* 370:666 */     print(ch, fieldSize);
/* 371:667 */     print("\n");
/* 372:    */   }
/* 373:    */   
/* 374:    */   public void println(double number)
/* 375:    */   {
/* 376:679 */     print(number);
/* 377:680 */     print("\n");
/* 378:    */   }
/* 379:    */   
/* 380:    */   public void println(double number, int fieldSize)
/* 381:    */   {
/* 382:693 */     print(number, fieldSize);
/* 383:694 */     print("\n");
/* 384:    */   }
/* 385:    */   
/* 386:    */   public void println(double number, int fieldSize, int decimalPlaces)
/* 387:    */   {
/* 388:710 */     print(number, fieldSize, decimalPlaces);
/* 389:711 */     print("\n");
/* 390:    */   }
/* 391:    */   
/* 392:    */   public void println(float number)
/* 393:    */   {
/* 394:723 */     print(number);
/* 395:724 */     print("\n");
/* 396:    */   }
/* 397:    */   
/* 398:    */   public void println(float number, int fieldSize)
/* 399:    */   {
/* 400:737 */     print(number, fieldSize);
/* 401:738 */     print("\n");
/* 402:    */   }
/* 403:    */   
/* 404:    */   public void println(float number, int fieldSize, int decimalPlaces)
/* 405:    */   {
/* 406:754 */     print(number, fieldSize, decimalPlaces);
/* 407:755 */     print("\n");
/* 408:    */   }
/* 409:    */   
/* 410:    */   public void println(int number)
/* 411:    */   {
/* 412:767 */     print(number);
/* 413:768 */     print("\n");
/* 414:    */   }
/* 415:    */   
/* 416:    */   public void println(int number, int fieldSize)
/* 417:    */   {
/* 418:781 */     print(number, fieldSize);
/* 419:782 */     print("\n");
/* 420:    */   }
/* 421:    */   
/* 422:    */   public void println(long number)
/* 423:    */   {
/* 424:794 */     print(number);
/* 425:795 */     print("\n");
/* 426:    */   }
/* 427:    */   
/* 428:    */   public void println(long number, int fieldSize)
/* 429:    */   {
/* 430:808 */     print(number, fieldSize);
/* 431:809 */     print("\n");
/* 432:    */   }
/* 433:    */   
/* 434:    */   public void println(String text)
/* 435:    */   {
/* 436:820 */     print(text);
/* 437:821 */     print("\n");
/* 438:    */   }
/* 439:    */   
/* 440:    */   public void println(String text, int fieldSize)
/* 441:    */   {
/* 442:834 */     print(text, fieldSize);
/* 443:835 */     print("\n");
/* 444:    */   }
/* 445:    */   
/* 446:    */   public void println(short number)
/* 447:    */   {
/* 448:847 */     print(number);
/* 449:848 */     print("\n");
/* 450:    */   }
/* 451:    */   
/* 452:    */   public void println(short number, int fieldSize)
/* 453:    */   {
/* 454:861 */     print(number, fieldSize);
/* 455:862 */     print("\n");
/* 456:    */   }
/* 457:    */   
/* 458:    */   public void println(boolean value)
/* 459:    */   {
/* 460:874 */     print(value);
/* 461:875 */     print("\n");
/* 462:    */   }
/* 463:    */   
/* 464:    */   public void println(boolean value, int fieldSize)
/* 465:    */   {
/* 466:888 */     print(value, fieldSize);
/* 467:889 */     print("\n");
/* 468:    */   }
/* 469:    */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.TextOutputFile
 * JD-Core Version:    0.7.0.1
 */