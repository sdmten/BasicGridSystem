/*   1:    */ package game1.hsa;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Component;
/*   5:    */ import java.awt.Font;
/*   6:    */ import java.awt.FontMetrics;
/*   7:    */ import java.awt.Graphics;
/*   8:    */ import java.awt.Image;
/*   9:    */ import java.awt.image.PixelGrabber;
/*  10:    */ import java.awt.print.PageFormat;
/*  11:    */ import java.awt.print.Printable;
/*  12:    */ import java.io.DataOutputStream;
/*  13:    */ import java.io.FileOutputStream;
/*  14:    */ import java.io.IOException;
/*  15:    */ import java.text.DateFormat;
/*  16:    */ import java.text.SimpleDateFormat;
/*  17:    */ import java.util.Date;
/*  18:    */ import java.util.TimeZone;
/*  19:    */ 
/*  20:    */ public class SavePrint
/*  21:    */   implements Printable
/*  22:    */ {
/*  23:    */   private static final int ROWS_GRABBED_AT_A_TIME = 75;
/*  24:    */   private static final int BI_RGB = 0;
/*  25:    */   private static final int BI_COMPRESSED_RLE8 = 1;
/*  26:    */   private static final int BI_COMPRESSED_RLE4 = 2;
/*  27:    */   private DrawGraphics savePrintObj;
/*  28:    */   private Component componentObj;
/*  29:    */   private int xSize;
/*  30:    */   private int ySize;
/*  31:    */   private Status status;
/*  32:    */   private DataOutputStream out;
/*  33:    */   
/*  34:    */   public SavePrint(DrawGraphics savePrintObj, Component componentObj, int xSize, int ySize)
/*  35:    */   {
/*  36: 34 */     this.savePrintObj = savePrintObj;
/*  37: 35 */     this.componentObj = componentObj;
/*  38: 36 */     this.xSize = xSize;
/*  39: 37 */     this.ySize = ySize;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void saveToFile(String fileName)
/*  43:    */   {
/*  44: 43 */     Image saveBuffer = this.componentObj.createImage(this.xSize, this.ySize);
/*  45: 44 */     this.savePrintObj.drawWindowToGraphics(saveBuffer.getGraphics());
/*  46:    */     
/*  47:    */ 
/*  48: 47 */     long lastStatusTime = 0L;
/*  49:    */     
/*  50:    */ 
/*  51: 50 */     this.status = new Status("Calculating Size", "Converting to BMP");
/*  52:    */     
/*  53:    */ 
/*  54: 53 */     int[] pixels = new int[this.ySize * this.xSize];
/*  55: 54 */     byte[] indexedPixels = new byte[this.ySize * this.xSize];
/*  56: 55 */     int[] colourMap = new int[256];
/*  57: 56 */     int numColours = 0;
/*  58: 60 */     for (int y = 0; y < this.ySize; y += 75)
/*  59:    */     {
/*  60: 63 */       if (System.currentTimeMillis() - lastStatusTime > 1000L)
/*  61:    */       {
/*  62: 65 */         lastStatusTime = System.currentTimeMillis();
/*  63: 66 */         this.status.setMessage("Grabbing pixels: " + 
/*  64: 67 */           y * 100 / this.ySize + "% Done");
/*  65:    */       }
/*  66: 70 */       PixelGrabber grabber = new PixelGrabber(saveBuffer, 0, y, this.xSize, 
/*  67: 71 */         Math.min(75, this.xSize - y), 
/*  68: 72 */         pixels, y * this.xSize, this.xSize);
/*  69:    */       try
/*  70:    */       {
/*  71: 76 */         if (!grabber.grabPixels())
/*  72:    */         {
/*  73: 78 */           this.status.dispose();
/*  74: 79 */           new Message("Unable to get picture image");
/*  75: 80 */           return;
/*  76:    */         }
/*  77:    */       }
/*  78:    */       catch (InterruptedException e)
/*  79:    */       {
/*  80: 85 */         this.status.dispose();
/*  81: 86 */         new Message("Unable to get picture image");
/*  82: 87 */         return;
/*  83:    */       }
/*  84:    */     }
/*  85: 92 */     for (int y = 0; y < this.ySize; y++)
/*  86:    */     {
/*  87: 95 */       if (System.currentTimeMillis() - lastStatusTime > 1000L)
/*  88:    */       {
/*  89: 97 */         lastStatusTime = System.currentTimeMillis();
/*  90: 98 */         this.status.setMessage("Making Colormap: " + 
/*  91: 99 */           y * 100 / this.ySize + "% Done");
/*  92:    */       }
/*  93:102 */       for (int x = 0; x < this.xSize; x++)
/*  94:    */       {
/*  95:104 */         int pixel = pixels[(y * this.xSize + x)] & 0xFFFFFF;
/*  96:105 */         for (int match = 0; match < numColours; match++) {
/*  97:106 */           if (pixel == colourMap[match]) {
/*  98:    */             break;
/*  99:    */           }
/* 100:    */         }
/* 101:108 */         if (match > 255)
/* 102:    */         {
/* 103:110 */           indexedPixels = (byte[])null;
/* 104:    */           
/* 105:112 */           write24BitBMP(pixels, this.xSize, this.ySize, fileName);
/* 106:113 */           if (this.status != null) {
/* 107:115 */             this.status.dispose();
/* 108:    */           }
/* 109:117 */           return;
/* 110:    */         }
/* 111:119 */         indexedPixels[(y * this.xSize + x)] = ((byte)match);
/* 112:120 */         if (match == numColours) {
/* 113:122 */           colourMap[(numColours++)] = pixel;
/* 114:    */         }
/* 115:    */       }
/* 116:    */     }
/* 117:128 */     pixels = (int[])null;
/* 118:129 */     if (numColours <= 2) {
/* 119:131 */       write1BitBMP(indexedPixels, colourMap, this.xSize, this.ySize, fileName);
/* 120:133 */     } else if (numColours <= 16) {
/* 121:135 */       write4BitBMP(indexedPixels, colourMap, this.xSize, this.ySize, fileName);
/* 122:    */     } else {
/* 123:139 */       write8BitBMP(indexedPixels, colourMap, this.xSize, this.ySize, fileName);
/* 124:    */     }
/* 125:142 */     if (this.status != null) {
/* 126:144 */       this.status.dispose();
/* 127:    */     }
/* 128:    */   }
/* 129:    */   
/* 130:    */   public int print(Graphics g, PageFormat format, int pageIndex)
/* 131:    */   {
/* 132:151 */     if (pageIndex > 0) {
/* 133:153 */       return 1;
/* 134:    */     }
/* 135:156 */     int left = (int)format.getImageableX();
/* 136:157 */     int top = (int)format.getImageableY();
/* 137:158 */     int right = left + (int)format.getImageableWidth();
/* 138:    */     
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:    */ 
/* 143:    */ 
/* 144:165 */     DateFormat formatter = new SimpleDateFormat("d MMM yyyy HH:mm:ss");
/* 145:166 */     formatter.setTimeZone(TimeZone.getDefault());
/* 146:167 */     String dateString = formatter.format(new Date());
/* 147:168 */     String userName = System.getProperty("user.name");
/* 148:    */     
/* 149:170 */     Font headerFont = new Font("SansSerif", 1, 12);
/* 150:171 */     FontMetrics fm = this.componentObj.getFontMetrics(headerFont);
/* 151:172 */     g.setColor(Color.black);
/* 152:173 */     g.setFont(headerFont);
/* 153:174 */     if (userName.length() != 0) {
/* 154:176 */       g.drawString(userName, left + 2, top + fm.getHeight() + 2);
/* 155:    */     }
/* 156:178 */     g.drawString(dateString, right - 2 - fm.stringWidth(dateString), 
/* 157:179 */       top + fm.getHeight() + 2);
/* 158:    */     
/* 159:    */ 
/* 160:182 */     int ty = 3 * fm.getHeight() + top;
/* 161:184 */     if (((int)format.getImageableWidth() > this.xSize + 2 * this.savePrintObj.getMargin()) || 
/* 162:    */     
/* 163:186 */       ((int)format.getImageableHeight() > this.ySize + 3 * fm.getHeight() + 2 * this.savePrintObj.getMargin()))
/* 164:    */     {
/* 165:188 */       g.translate(left, ty);
/* 166:189 */       this.savePrintObj.drawWindowToGraphics(g, (int)format.getImageableWidth(), 
/* 167:190 */         (int)format.getImageableHeight() - 3 * fm.getHeight());
/* 168:191 */       g.translate(left, -ty);
/* 169:    */     }
/* 170:    */     else
/* 171:    */     {
/* 172:195 */       int tx = ((int)format.getImageableWidth() - this.xSize) / 2 + left;
/* 173:196 */       g.translate(tx, ty);
/* 174:197 */       this.savePrintObj.drawWindowToGraphics(g);
/* 175:198 */       g.translate(-tx, -ty);
/* 176:    */     }
/* 177:201 */     return 0;
/* 178:    */   }
/* 179:    */   
/* 180:    */   private void write1BitBMP(byte[] pixels, int[] colourMap, int width, int height, String fileName)
/* 181:    */   {
/* 182:211 */     long lastStatusTime = 0L;
/* 183:    */     
/* 184:213 */     this.status.setMessage("Writing 1 bit BMP");
/* 185:    */     
/* 186:    */ 
/* 187:216 */     int padding = (4 - (width + 7) / 8 % 4) % 4;
/* 188:217 */     int imageSize = ((width + 7) / 8 + padding) * height;
/* 189:218 */     byte[] picture = new byte[imageSize];
/* 190:    */     
/* 191:220 */     int ptr = 0;
/* 192:221 */     for (int y = 0; y < height; y++)
/* 193:    */     {
/* 194:224 */       if (System.currentTimeMillis() - lastStatusTime > 1000L)
/* 195:    */       {
/* 196:226 */         lastStatusTime = System.currentTimeMillis();
/* 197:227 */         this.status.setMessage("Converting to BMP: " + y * 100 / height + 
/* 198:228 */           "% Done");
/* 199:    */       }
/* 200:231 */       for (int x = 0; x < width; x++)
/* 201:    */       {
/* 202:233 */         int pixel = pixels[((height - 1 - y) * width + x)]; int 
/* 203:234 */           tmp138_136 = ptr; byte[] tmp138_134 = picture;tmp138_134[tmp138_136] = ((byte)(tmp138_134[tmp138_136] | pixel << 7 - x % 8));
/* 204:235 */         if ((x % 8 == 7) || (x == width - 1)) {
/* 205:236 */           ptr++;
/* 206:    */         }
/* 207:    */       }
/* 208:238 */       for (int cnt = 0; cnt < padding; cnt++) {
/* 209:240 */         picture[(ptr++)] = 0;
/* 210:    */       }
/* 211:    */     }
/* 212:246 */     writeBMPFile(fileName, imageSize, 2, width, height, 
/* 213:247 */       1, 0, colourMap, picture);
/* 214:    */   }
/* 215:    */   
/* 216:    */   private void write24BitBMP(int[] pixels, int width, int height, String fileName)
/* 217:    */   {
/* 218:257 */     long lastStatusTime = 0L;
/* 219:    */     
/* 220:259 */     this.status.setMessage("Writing 24 bit BMP");
/* 221:    */     
/* 222:    */ 
/* 223:262 */     int padding = (4 - width * 3 % 4) % 4;
/* 224:263 */     int imageSize = (width * 3 + padding) * height;
/* 225:264 */     byte[] picture = new byte[imageSize];
/* 226:    */     
/* 227:266 */     int ptr = 0;
/* 228:267 */     for (int y = 0; y < height; y++)
/* 229:    */     {
/* 230:270 */       if (System.currentTimeMillis() - lastStatusTime > 1000L)
/* 231:    */       {
/* 232:272 */         lastStatusTime = System.currentTimeMillis();
/* 233:273 */         this.status.setMessage("Converting to BMP: " + y * 100 / height + 
/* 234:274 */           "% Done");
/* 235:    */       }
/* 236:277 */       for (int x = 0; x < width; x++)
/* 237:    */       {
/* 238:279 */         int pixel = pixels[((height - 1 - y) * width + x)];
/* 239:280 */         picture[(ptr++)] = ((byte)(pixel & 0xFF));
/* 240:281 */         picture[(ptr++)] = ((byte)(pixel >> 8 & 0xFF));
/* 241:282 */         picture[(ptr++)] = ((byte)(pixel >> 16 & 0xFF));
/* 242:    */       }
/* 243:284 */       for (int cnt = 0; cnt < padding; cnt++) {
/* 244:286 */         picture[(ptr++)] = 0;
/* 245:    */       }
/* 246:    */     }
/* 247:292 */     writeBMPFile(fileName, imageSize, 0, width, height, 
/* 248:293 */       24, 0, null, picture);
/* 249:    */   }
/* 250:    */   
/* 251:    */   private void write4BitBMP(byte[] pixels, int[] colourMap, int width, int height, String fileName)
/* 252:    */   {
/* 253:303 */     int[] bitShift = { 4 };
/* 254:304 */     long lastStatusTime = 0L;
/* 255:    */     
/* 256:306 */     this.status.setMessage("Writing 4 bit BMP");
/* 257:    */     
/* 258:    */ 
/* 259:309 */     int padding = (4 - (width + 1) / 2 % 4) % 4;
/* 260:310 */     int imageSize = ((width + 1) / 2 + padding) * height;
/* 261:311 */     byte[] picture = new byte[imageSize];
/* 262:    */     
/* 263:313 */     int ptr = 0;
/* 264:314 */     for (int y = 0; y < height; y++)
/* 265:    */     {
/* 266:317 */       if (System.currentTimeMillis() - lastStatusTime > 1000L)
/* 267:    */       {
/* 268:319 */         lastStatusTime = System.currentTimeMillis();
/* 269:320 */         this.status.setMessage("Converting to BMP: " + y * 100 / height + 
/* 270:321 */           "% Done");
/* 271:    */       }
/* 272:324 */       for (int x = 0; x < width; x++)
/* 273:    */       {
/* 274:326 */         int pixel = pixels[((height - 1 - y) * width + x)]; int 
/* 275:327 */           tmp143_141 = ptr; byte[] tmp143_139 = picture;tmp143_139[tmp143_141] = ((byte)(tmp143_139[tmp143_141] | pixel << bitShift[(x % 2)]));
/* 276:328 */         if ((x % 2 == 1) || (x == width - 1)) {
/* 277:329 */           ptr++;
/* 278:    */         }
/* 279:    */       }
/* 280:331 */       for (int cnt = 0; cnt < padding; cnt++) {
/* 281:333 */         picture[(ptr++)] = 0;
/* 282:    */       }
/* 283:    */     }
/* 284:339 */     this.status.setMessage("Compressing the bitmap");
/* 285:340 */     byte[] compressed = new byte[imageSize];
/* 286:341 */     int byteWidth = width / 2 + padding;
/* 287:342 */     int compressedPtr = 0;
/* 288:343 */     for (int y = 0; y < height; y++)
/* 289:    */     {
/* 290:345 */       int startY = y;
/* 291:    */       
/* 292:    */ 
/* 293:348 */       int x = 0;
/* 294:    */       int startX;
/* 295:349 */       for (; x < (width + 1) / 2; x != startX)
/* 296:    */       {
/* 297:351 */         startX = x;
/* 298:    */         
/* 299:    */ 
/* 300:354 */         boolean foundIdenticals = false;
/* 301:355 */         while ((!foundIdenticals) && (x < width - 4))
/* 302:    */         {
/* 303:357 */           int loc = y * byteWidth + x;
/* 304:358 */           if ((picture[loc] == picture[(loc + 1)]) && 
/* 305:359 */             (picture[(loc + 1)] == picture[(loc + 2)]))
/* 306:    */           {
/* 307:361 */             foundIdenticals = true;
/* 308:362 */             break;
/* 309:    */           }
/* 310:364 */           x++;
/* 311:    */         }
/* 312:368 */         if (!foundIdenticals) {
/* 313:369 */           x = (width + 1) / 2;
/* 314:    */         }
/* 315:371 */         while (x - startX > 2)
/* 316:    */         {
/* 317:376 */           if (compressedPtr + 300 > imageSize)
/* 318:    */           {
/* 319:379 */             writeBMPFile(fileName, imageSize, 16, width, height, 
/* 320:380 */               4, 0, colourMap, picture);
/* 321:381 */             return;
/* 322:    */           }
/* 323:384 */           int size = Math.min(x - startX, 127);
/* 324:385 */           compressed[(compressedPtr++)] = 0;
/* 325:386 */           compressed[(compressedPtr++)] = ((byte)(size * 2));
/* 326:387 */           int picPtr = y * byteWidth + startX;
/* 327:388 */           for (int cnt = 0; cnt < size; cnt++) {
/* 328:390 */             compressed[(compressedPtr++)] = picture[(picPtr + cnt)];
/* 329:    */           }
/* 330:392 */           if (size % 2 == 1) {
/* 331:393 */             compressed[(compressedPtr++)] = 0;
/* 332:    */           }
/* 333:394 */           startX += size;
/* 334:    */         }
/* 335:396 */         x = startX;
/* 336:398 */         if (x == width) {
/* 337:    */           break;
/* 338:    */         }
/* 339:402 */         byte pixel = picture[(y * byteWidth + x)];
/* 340:403 */         boolean identical = true;
/* 341:404 */         x++;
/* 342:    */         do
/* 343:    */         {
/* 344:407 */           if (picture[(y * byteWidth + x)] != pixel)
/* 345:    */           {
/* 346:409 */             identical = false;
/* 347:410 */             break;
/* 348:    */           }
/* 349:412 */           x++;
/* 350:405 */           if (!identical) {
/* 351:    */             break;
/* 352:    */           }
/* 353:405 */         } while (x < (width + 1) / 2);
/* 354:415 */         continue;
/* 355:420 */         if (compressedPtr + 300 > imageSize)
/* 356:    */         {
/* 357:423 */           writeBMPFile(fileName, imageSize, 16, width, height, 
/* 358:424 */             4, 0, colourMap, picture);
/* 359:425 */           return;
/* 360:    */         }
/* 361:428 */         int size = Math.min(x - startX, 127);
/* 362:429 */         compressed[(compressedPtr++)] = ((byte)(size * 2));
/* 363:430 */         compressed[(compressedPtr++)] = pixel;
/* 364:431 */         startX += size;
/* 365:    */       }
/* 366:436 */       compressed[(compressedPtr++)] = 0;
/* 367:437 */       compressed[(compressedPtr++)] = 0;
/* 368:    */     }
/* 369:442 */     compressed[(compressedPtr++)] = 0;
/* 370:443 */     compressed[(compressedPtr++)] = 1;
/* 371:    */     
/* 372:445 */     writeBMPFile(fileName, compressedPtr, 16, width, height, 
/* 373:446 */       4, 2, colourMap, compressed);
/* 374:    */   }
/* 375:    */   
/* 376:    */   private void write8BitBMP(byte[] pixels, int[] colourMap, int width, int height, String fileName)
/* 377:    */   {
/* 378:456 */     long lastStatusTime = 0L;
/* 379:    */     
/* 380:458 */     this.status.setMessage("Writing 8 bit BMP");
/* 381:    */     
/* 382:    */ 
/* 383:461 */     int padding = (4 - width % 4) % 4;
/* 384:462 */     int imageSize = (width + padding) * height;
/* 385:463 */     byte[] picture = new byte[imageSize];
/* 386:    */     
/* 387:465 */     int ptr = 0;
/* 388:466 */     for (int y = 0; y < height; y++)
/* 389:    */     {
/* 390:469 */       if (System.currentTimeMillis() - lastStatusTime > 1000L)
/* 391:    */       {
/* 392:471 */         lastStatusTime = System.currentTimeMillis();
/* 393:472 */         this.status.setMessage("Converting to BMP: " + y * 100 / height + 
/* 394:473 */           "% Done");
/* 395:    */       }
/* 396:476 */       for (int x = 0; x < width; x++)
/* 397:    */       {
/* 398:478 */         int tmp113_110 = (ptr++); byte[] tmp113_106 = picture;tmp113_106[tmp113_110] = ((byte)(tmp113_106[tmp113_110] | pixels[((height - 1 - y) * width + x)]));
/* 399:    */       }
/* 400:480 */       for (int cnt = 0; cnt < padding; cnt++) {
/* 401:482 */         picture[(ptr++)] = 0;
/* 402:    */       }
/* 403:    */     }
/* 404:488 */     this.status.setMessage("Compressing the bitmap");
/* 405:489 */     byte[] compressed = new byte[imageSize];
/* 406:490 */     int byteWidth = width + padding;
/* 407:491 */     int compressedPtr = 0;
/* 408:492 */     for (int y = 0; y < height; y++)
/* 409:    */     {
/* 410:494 */       int startY = y;
/* 411:    */       
/* 412:    */ 
/* 413:497 */       int x = 0;
/* 414:    */       int startX;
/* 415:498 */       for (; x < width; x != startX)
/* 416:    */       {
/* 417:500 */         startX = x;
/* 418:    */         
/* 419:    */ 
/* 420:503 */         boolean foundIdenticals = false;
/* 421:504 */         while ((!foundIdenticals) && (x < width - 4))
/* 422:    */         {
/* 423:506 */           int loc = y * byteWidth + x;
/* 424:507 */           if ((picture[loc] == picture[(loc + 1)]) && 
/* 425:508 */             (picture[(loc + 1)] == picture[(loc + 2)]))
/* 426:    */           {
/* 427:510 */             foundIdenticals = true;
/* 428:511 */             break;
/* 429:    */           }
/* 430:513 */           x++;
/* 431:    */         }
/* 432:517 */         if (!foundIdenticals) {
/* 433:518 */           x = width;
/* 434:    */         }
/* 435:520 */         while (x - startX > 2)
/* 436:    */         {
/* 437:525 */           if (compressedPtr + 300 > imageSize)
/* 438:    */           {
/* 439:528 */             writeBMPFile(fileName, imageSize, 256, width, height, 
/* 440:529 */               8, 0, colourMap, picture);
/* 441:530 */             return;
/* 442:    */           }
/* 443:533 */           int size = Math.min(x - startX, 255);
/* 444:534 */           compressed[(compressedPtr++)] = 0;
/* 445:535 */           compressed[(compressedPtr++)] = ((byte)size);
/* 446:536 */           int picPtr = y * byteWidth + startX;
/* 447:537 */           for (int cnt = 0; cnt < size; cnt++) {
/* 448:539 */             compressed[(compressedPtr++)] = picture[(picPtr + cnt)];
/* 449:    */           }
/* 450:541 */           if (size % 2 == 1) {
/* 451:542 */             compressed[(compressedPtr++)] = 0;
/* 452:    */           }
/* 453:543 */           startX += size;
/* 454:    */         }
/* 455:545 */         x = startX;
/* 456:547 */         if (x == width) {
/* 457:    */           break;
/* 458:    */         }
/* 459:551 */         byte pixel = picture[(y * byteWidth + x)];
/* 460:552 */         boolean identical = true;
/* 461:553 */         x++;
/* 462:    */         do
/* 463:    */         {
/* 464:556 */           if (picture[(y * byteWidth + x)] != pixel)
/* 465:    */           {
/* 466:558 */             identical = false;
/* 467:559 */             break;
/* 468:    */           }
/* 469:561 */           x++;
/* 470:554 */           if (!identical) {
/* 471:    */             break;
/* 472:    */           }
/* 473:554 */         } while (x < width);
/* 474:564 */         continue;
/* 475:569 */         if (compressedPtr + 300 > imageSize)
/* 476:    */         {
/* 477:572 */           writeBMPFile(fileName, imageSize, 256, width, height, 
/* 478:573 */             8, 0, colourMap, picture);
/* 479:574 */           return;
/* 480:    */         }
/* 481:577 */         int size = Math.min(x - startX, 255);
/* 482:578 */         compressed[(compressedPtr++)] = ((byte)size);
/* 483:579 */         compressed[(compressedPtr++)] = pixel;
/* 484:580 */         startX += size;
/* 485:    */       }
/* 486:585 */       compressed[(compressedPtr++)] = 0;
/* 487:586 */       compressed[(compressedPtr++)] = 0;
/* 488:    */     }
/* 489:591 */     compressed[(compressedPtr++)] = 0;
/* 490:592 */     compressed[(compressedPtr++)] = 1;
/* 491:    */     
/* 492:594 */     writeBMPFile(fileName, compressedPtr, 256, width, height, 
/* 493:595 */       8, 1, colourMap, compressed);
/* 494:    */   }
/* 495:    */   
/* 496:    */   private void writeBMPFile(String fileName, int imageSize, int paletteEntries, int width, int height, int bitCount, int compressionType, int[] colourMap, byte[] buffer)
/* 497:    */   {
/* 498:606 */     this.status.setMessage("Writing image");
/* 499:    */     try
/* 500:    */     {
/* 501:610 */       this.out = new DataOutputStream(new FileOutputStream(fileName));
/* 502:    */       
/* 503:    */ 
/* 504:613 */       writeShort(19778);
/* 505:614 */       writeInt(imageSize + 54 + paletteEntries * 4);
/* 506:615 */       writeShort(0);
/* 507:616 */       writeShort(0);
/* 508:617 */       writeInt(54 + paletteEntries * 4);
/* 509:    */       
/* 510:619 */       writeInt(40);
/* 511:620 */       writeInt(width);
/* 512:621 */       writeInt(height);
/* 513:622 */       writeShort(1);
/* 514:623 */       writeShort(bitCount);
/* 515:624 */       writeInt(compressionType);
/* 516:625 */       writeInt(imageSize);
/* 517:626 */       writeInt(2835);
/* 518:627 */       writeInt(2835);
/* 519:628 */       writeInt(paletteEntries);
/* 520:629 */       writeInt(paletteEntries);
/* 521:632 */       for (int colours = 0; colours < paletteEntries; colours++)
/* 522:    */       {
/* 523:634 */         int colour = colourMap[colours];
/* 524:    */         
/* 525:636 */         this.out.writeByte(colour & 0xFF);
/* 526:637 */         this.out.writeByte(colour >> 8 & 0xFF);
/* 527:638 */         this.out.writeByte(colour >> 16 & 0xFF);
/* 528:639 */         this.out.writeByte(0);
/* 529:    */       }
/* 530:643 */       this.out.write(buffer, 0, imageSize);
/* 531:    */       
/* 532:645 */       this.out.close();
/* 533:    */       
/* 534:647 */       this.out = null;
/* 535:    */     }
/* 536:    */     catch (IOException localIOException)
/* 537:    */     {
/* 538:654 */       if (this.out != null) {
/* 539:    */         try
/* 540:    */         {
/* 541:658 */           this.out.close();
/* 542:659 */           this.out = null;
/* 543:    */         }
/* 544:    */         catch (IOException localIOException1) {}
/* 545:    */       }
/* 546:    */     }
/* 547:    */     finally
/* 548:    */     {
/* 549:654 */       if (this.out != null) {
/* 550:    */         try
/* 551:    */         {
/* 552:658 */           this.out.close();
/* 553:659 */           this.out = null;
/* 554:    */         }
/* 555:    */         catch (IOException localIOException2) {}
/* 556:    */       }
/* 557:    */     }
/* 558:    */   }
/* 559:    */   
/* 560:    */   private void writeInt(int number)
/* 561:    */   {
/* 562:    */     try
/* 563:    */     {
/* 564:676 */       this.out.writeByte(number & 0xFF);
/* 565:677 */       this.out.writeByte(number >> 8 & 0xFF);
/* 566:678 */       this.out.writeByte(number >> 16 & 0xFF);
/* 567:679 */       this.out.writeByte(number >> 24 & 0xFF);
/* 568:    */     }
/* 569:    */     catch (IOException localIOException) {}
/* 570:    */   }
/* 571:    */   
/* 572:    */   private void writeShort(int number)
/* 573:    */   {
/* 574:    */     try
/* 575:    */     {
/* 576:695 */       this.out.writeByte(number & 0xFF);
/* 577:696 */       this.out.writeByte(number >> 8 & 0xFF);
/* 578:    */     }
/* 579:    */     catch (IOException localIOException) {}
/* 580:    */   }
/* 581:    */   
/* 582:    */   class Message
/* 583:    */   {
/* 584:    */     public Message(String message) {}
/* 585:    */   }
/* 586:    */   
/* 587:    */   class Status
/* 588:    */   {
/* 589:    */     public Status(String windowTitle, String message) {}
/* 590:    */     
/* 591:    */     public void setMessage(String windowTitle) {}
/* 592:    */     
/* 593:    */     public void dispose() {}
/* 594:    */   }
/* 595:    */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.SavePrint
 * JD-Core Version:    0.7.0.1
 */