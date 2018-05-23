/*   1:    */ package game1.hsa;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.EOFException;
/*   5:    */ import java.io.File;
/*   6:    */ import java.io.FileNotFoundException;
/*   7:    */ import java.io.FileReader;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.io.InputStreamReader;
/*  10:    */ 
/*  11:    */ public class TextInputFile
/*  12:    */ {
/*  13:    */   protected String fileName;
/*  14: 29 */   protected BufferedReader f = null;
/*  15: 34 */   protected boolean closed = false;
/*  16: 39 */   protected boolean useStandardIO = false;
/*  17: 44 */   protected boolean eofFlag = false;
/*  18:    */   protected static final int EMPTY = -1;
/*  19: 50 */   protected String lineBuffer = "";
/*  20: 51 */   int lineBufferPtr = 0;
/*  21: 52 */   int ungotChar = -1;
/*  22:    */   
/*  23:    */   public TextInputFile()
/*  24:    */   {
/*  25: 60 */     this("Standard input");
/*  26:    */   }
/*  27:    */   
/*  28:    */   public TextInputFile(File file)
/*  29:    */   {
/*  30:    */     try
/*  31:    */     {
/*  32: 73 */       FileReader fileReader = new FileReader(file);
/*  33: 74 */       this.f = new BufferedReader(fileReader);
/*  34:    */     }
/*  35:    */     catch (FileNotFoundException e)
/*  36:    */     {
/*  37: 78 */       new FatalError("Unable to open file \"" + this.fileName + "\"");
/*  38:    */     }
/*  39: 81 */     this.fileName = file.getName();
/*  40:    */   }
/*  41:    */   
/*  42:    */   public TextInputFile(String fileName)
/*  43:    */   {
/*  44: 92 */     if ((fileName.equalsIgnoreCase("standard input")) || 
/*  45: 93 */       (fileName.equalsIgnoreCase("keyboard")) || 
/*  46: 94 */       (fileName.equalsIgnoreCase("stdin")))
/*  47:    */     {
/*  48: 96 */       this.f = new BufferedReader(new InputStreamReader(System.in));
/*  49: 97 */       this.fileName = "Standard input";
/*  50: 98 */       this.useStandardIO = true;
/*  51:    */     }
/*  52:    */     else
/*  53:    */     {
/*  54:    */       try
/*  55:    */       {
/*  56:104 */         this.f = new BufferedReader(new FileReader(new File(fileName)));
/*  57:    */       }
/*  58:    */       catch (FileNotFoundException e)
/*  59:    */       {
/*  60:108 */         new FatalError("Unable to open file \"" + fileName + "\"");
/*  61:    */       }
/*  62:111 */       this.fileName = fileName;
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void close()
/*  67:    */   {
/*  68:121 */     if (this.closed) {
/*  69:123 */       new FatalError("\"" + this.fileName + "\" is already closed.");
/*  70:    */     }
/*  71:128 */     if (this.useStandardIO)
/*  72:    */     {
/*  73:130 */       this.useStandardIO = false;
/*  74:    */     }
/*  75:    */     else
/*  76:    */     {
/*  77:    */       try
/*  78:    */       {
/*  79:136 */         this.f.close();
/*  80:    */       }
/*  81:    */       catch (IOException e)
/*  82:    */       {
/*  83:140 */         new FatalError("Close failed: Unable to close \"" + this.fileName + "\"");
/*  84:    */       }
/*  85:143 */       this.f = null;
/*  86:    */     }
/*  87:146 */     this.closed = true;
/*  88:147 */     this.lineBuffer = "";
/*  89:    */   }
/*  90:    */   
/*  91:    */   public boolean eof()
/*  92:    */   {
/*  93:    */     try
/*  94:    */     {
/*  95:160 */       char ch = readACharacterThrowsEOF();
/*  96:161 */       pushACharacter(ch);
/*  97:    */     }
/*  98:    */     catch (EOFException e)
/*  99:    */     {
/* 100:165 */       return true;
/* 101:    */     }
/* 102:167 */     return false;
/* 103:    */   }
/* 104:    */   
/* 105:    */   protected void pushACharacter(char ch)
/* 106:    */   {
/* 107:179 */     this.ungotChar = ch;
/* 108:    */   }
/* 109:    */   
/* 110:    */   protected char readACharacter()
/* 111:    */   {
/* 112:    */     try
/* 113:    */     {
/* 114:193 */       return readACharacterThrowsEOF();
/* 115:    */     }
/* 116:    */     catch (EOFException e)
/* 117:    */     {
/* 118:197 */       new FatalError(
/* 119:198 */         "Attempt to read past end of file on " + this.fileName);
/* 120:    */     }
/* 121:202 */     return '\000';
/* 122:    */   }
/* 123:    */   
/* 124:    */   protected char readACharacterThrowsEOF()
/* 125:    */     throws EOFException
/* 126:    */   {
/* 127:215 */     if (this.closed) {
/* 128:217 */       new FatalError("Read failed: \"" + this.fileName + "\" is already closed.");
/* 129:    */     }
/* 130:221 */     if (this.ungotChar != -1)
/* 131:    */     {
/* 132:223 */       char ch = (char)this.ungotChar;
/* 133:224 */       this.ungotChar = -1;
/* 134:225 */       return ch;
/* 135:    */     }
/* 136:227 */     if (this.lineBufferPtr < this.lineBuffer.length()) {
/* 137:229 */       return this.lineBuffer.charAt(this.lineBufferPtr++);
/* 138:    */     }
/* 139:232 */     readALineFromFile();
/* 140:233 */     return this.lineBuffer.charAt(this.lineBufferPtr++);
/* 141:    */   }
/* 142:    */   
/* 143:    */   protected void readALineFromFile()
/* 144:    */     throws EOFException
/* 145:    */   {
/* 146:    */     try
/* 147:    */     {
/* 148:246 */       if (this.useStandardIO)
/* 149:    */       {
/* 150:248 */         if (this.eofFlag) {
/* 151:250 */           throw new EOFException();
/* 152:    */         }
/* 153:253 */         this.lineBuffer = this.f.readLine();
/* 154:254 */         if (this.lineBuffer != null) {
/* 155:256 */           if (this.lineBuffer.indexOf('\032') != -1)
/* 156:    */           {
/* 157:258 */             this.lineBuffer = this.lineBuffer.substring(0, 
/* 158:259 */               this.lineBuffer.indexOf('\032'));
/* 159:260 */             if (this.lineBuffer.length() == 0) {
/* 160:262 */               this.lineBuffer = null;
/* 161:    */             }
/* 162:264 */             this.eofFlag = true;
/* 163:    */           }
/* 164:266 */           else if (this.lineBuffer.indexOf(4) != -1)
/* 165:    */           {
/* 166:268 */             this.lineBuffer = this.lineBuffer.substring(0, 
/* 167:269 */               this.lineBuffer.indexOf(4));
/* 168:270 */             if (this.lineBuffer.length() == 0) {
/* 169:272 */               this.lineBuffer = null;
/* 170:    */             }
/* 171:274 */             this.eofFlag = true;
/* 172:    */           }
/* 173:    */         }
/* 174:    */       }
/* 175:    */       else
/* 176:    */       {
/* 177:280 */         this.lineBuffer = this.f.readLine();
/* 178:    */       }
/* 179:282 */       if (this.lineBuffer == null) {
/* 180:284 */         throw new EOFException();
/* 181:    */       }
/* 182:286 */       this.lineBuffer += "\n";
/* 183:287 */       this.lineBufferPtr = 0;
/* 184:    */     }
/* 185:    */     catch (IOException e)
/* 186:    */     {
/* 187:291 */       if ((e instanceof EOFException)) {
/* 188:293 */         throw new EOFException();
/* 189:    */       }
/* 190:297 */       new FatalError("Read on \"" + this.fileName + "\" failed: " + e);
/* 191:    */     }
/* 192:    */   }
/* 193:    */   
/* 194:    */   protected String readAToken()
/* 195:    */   {
/* 196:311 */     StringBuffer sb = new StringBuffer();
/* 197:    */     char ch;
/* 198:    */     do
/* 199:    */     {
/* 200:316 */       ch = readACharacter();
/* 201:318 */     } while ((ch == ' ') || (ch == '\n') || (ch == '\t'));
/* 202:320 */     if (ch == '"')
/* 203:    */     {
/* 204:323 */       ch = readACharacter();
/* 205:324 */       while (ch != '"')
/* 206:    */       {
/* 207:326 */         sb.append(ch);
/* 208:    */         
/* 209:328 */         ch = readACharacter();
/* 210:329 */         if (ch == '\n') {
/* 211:331 */           new FatalError(
/* 212:    */           
/* 213:333 */             "No terminating quote for quoted string in \"" + this.fileName + "\"");
/* 214:    */         }
/* 215:    */       }
/* 216:339 */       ch = readACharacter();
/* 217:    */     }
/* 218:    */     else
/* 219:    */     {
/* 220:    */       do
/* 221:    */       {
/* 222:345 */         sb.append(ch);
/* 223:    */         
/* 224:347 */         ch = readACharacter();
/* 225:349 */         if ((ch == ' ') || (ch == '\n')) {
/* 226:    */           break;
/* 227:    */         }
/* 228:349 */       } while (ch != '\t');
/* 229:    */     }
/* 230:353 */     while ((ch == ' ') || (ch == '\t')) {
/* 231:355 */       ch = readACharacter();
/* 232:    */     }
/* 233:358 */     if (ch != '\n') {
/* 234:360 */       pushACharacter(ch);
/* 235:    */     }
/* 236:363 */     return new String(sb);
/* 237:    */   }
/* 238:    */   
/* 239:    */   public boolean readBoolean()
/* 240:    */   {
/* 241:376 */     String s = readAToken().toLowerCase();
/* 242:378 */     if (s.equals("true")) {
/* 243:380 */       return true;
/* 244:    */     }
/* 245:382 */     if (s.equals("false")) {
/* 246:384 */       return false;
/* 247:    */     }
/* 248:388 */     new FatalError("Unable to convert \"" + s + "\" to a boolean");
/* 249:    */     
/* 250:    */ 
/* 251:391 */     return false;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public byte readByte()
/* 255:    */   {
/* 256:403 */     String s = readAToken();
/* 257:    */     try
/* 258:    */     {
/* 259:407 */       return Byte.parseByte(s);
/* 260:    */     }
/* 261:    */     catch (NumberFormatException e)
/* 262:    */     {
/* 263:411 */       new FatalError("Unable to convert \"" + s + "\" to a byte");
/* 264:    */     }
/* 265:414 */     return 0;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public char readChar()
/* 269:    */   {
/* 270:    */     char ch;
/* 271:    */     do
/* 272:    */     {
/* 273:432 */       ch = readACharacter();
/* 274:434 */     } while ((ch == ' ') || (ch == '\n') || (ch == '\t'));
/* 275:437 */     char result = ch;
/* 276:    */     do
/* 277:    */     {
/* 278:442 */       ch = readACharacter();
/* 279:444 */     } while ((ch == ' ') || (ch == '\t'));
/* 280:446 */     if (ch != '\n') {
/* 281:448 */       pushACharacter(ch);
/* 282:    */     }
/* 283:452 */     return result;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public double readDouble()
/* 287:    */   {
/* 288:465 */     String s = readAToken();
/* 289:    */     try
/* 290:    */     {
/* 291:469 */       Double d = Double.valueOf(s);
/* 292:470 */       return d.doubleValue();
/* 293:    */     }
/* 294:    */     catch (NumberFormatException e)
/* 295:    */     {
/* 296:474 */       new FatalError("Unable to convert \"" + s + "\" to a double");
/* 297:    */     }
/* 298:477 */     return 0.0D;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public float readFloat()
/* 302:    */   {
/* 303:489 */     String s = readAToken();
/* 304:    */     try
/* 305:    */     {
/* 306:493 */       Float f = Float.valueOf(s);
/* 307:494 */       return f.floatValue();
/* 308:    */     }
/* 309:    */     catch (NumberFormatException e)
/* 310:    */     {
/* 311:498 */       new FatalError("Unable to convert \"" + s + "\" to a float");
/* 312:    */     }
/* 313:501 */     return 0.0F;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public int readInt()
/* 317:    */   {
/* 318:514 */     String s = readAToken();
/* 319:    */     try
/* 320:    */     {
/* 321:518 */       return Integer.parseInt(s);
/* 322:    */     }
/* 323:    */     catch (NumberFormatException e)
/* 324:    */     {
/* 325:522 */       new FatalError("Unable to convert \"" + s + "\" to a int");
/* 326:    */     }
/* 327:525 */     return 0;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public String readLine()
/* 331:    */   {
/* 332:537 */     StringBuffer s = new StringBuffer();
/* 333:    */     
/* 334:    */ 
/* 335:540 */     char ch = readACharacter();
/* 336:541 */     while (ch != '\n')
/* 337:    */     {
/* 338:543 */       s.append(ch);
/* 339:544 */       ch = readACharacter();
/* 340:    */     }
/* 341:547 */     return s.toString();
/* 342:    */   }
/* 343:    */   
/* 344:    */   public long readLong()
/* 345:    */   {
/* 346:558 */     String s = readAToken();
/* 347:    */     try
/* 348:    */     {
/* 349:562 */       return Long.parseLong(s);
/* 350:    */     }
/* 351:    */     catch (NumberFormatException e)
/* 352:    */     {
/* 353:566 */       new FatalError("Unable to convert \"" + s + "\" to a long");
/* 354:    */     }
/* 355:569 */     return 0L;
/* 356:    */   }
/* 357:    */   
/* 358:    */   public short readShort()
/* 359:    */   {
/* 360:581 */     String s = readAToken();
/* 361:    */     try
/* 362:    */     {
/* 363:585 */       return Short.parseShort(s);
/* 364:    */     }
/* 365:    */     catch (NumberFormatException e)
/* 366:    */     {
/* 367:589 */       new FatalError("Unable to convert \"" + s + "\" to a short");
/* 368:    */     }
/* 369:592 */     return 0;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public String readString()
/* 373:    */   {
/* 374:603 */     return readAToken();
/* 375:    */   }
/* 376:    */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.TextInputFile
 * JD-Core Version:    0.7.0.1
 */