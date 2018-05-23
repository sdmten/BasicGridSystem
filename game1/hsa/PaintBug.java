/*   1:    */ package game1.hsa;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ 
/*   6:    */ public class PaintBug
/*   7:    */ {
/*   8:    */   static final int DEFAULT_X = -99;
/*   9:    */   static final double DEFAULT_DIR = 0.0D;
/*  10:    */   static final int DEFAULT_TRAIL_WIDTH = 5;
/*  11: 13 */   static boolean gridInitialized = false;
/*  12: 14 */   static boolean drawGrid = false;
/*  13: 15 */   static int xSize = 0;
/*  14: 15 */   static int ySize = 0;
/*  15:    */   static PaintBugWindow window;
/*  16: 17 */   static Color[] colors = { Color.red, Color.green, Color.blue, Color.cyan, 
/*  17: 18 */     Color.orange, Color.magenta, Color.yellow };
/*  18: 19 */   static int colorNumber = 0;
/*  19:    */   double xPos;
/*  20:    */   double yPos;
/*  21:    */   double direction;
/*  22:    */   Color color;
/*  23:    */   int trailWidth;
/*  24:    */   boolean trailVisible;
/*  25:    */   String label;
/*  26:    */   Thread movementThread;
/*  27:    */   
/*  28:    */   public PaintBug(double startX, double startY, double startDirection, Color startColor)
/*  29:    */   {
/*  30: 34 */     initializeGridIfNecessary();
/*  31: 36 */     if (startX == -99.0D)
/*  32:    */     {
/*  33: 38 */       this.xPos = (window.getXSize() / 2);
/*  34: 39 */       this.yPos = (window.getYSize() / 2);
/*  35:    */     }
/*  36:    */     else
/*  37:    */     {
/*  38: 43 */       this.xPos = startX;
/*  39: 44 */       this.yPos = startY;
/*  40:    */     }
/*  41: 46 */     this.direction = startDirection;
/*  42: 47 */     this.color = startColor;
/*  43: 48 */     this.trailWidth = 5;
/*  44: 49 */     this.trailVisible = true;
/*  45: 50 */     this.label = "";
/*  46:    */     
/*  47: 52 */     window.showBug(this, this.xPos, this.yPos, this.direction, this.color, this.label);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public PaintBug()
/*  51:    */   {
/*  52: 58 */     this(-99.0D, -99.0D, 0.0D, getNextColor());
/*  53:    */   }
/*  54:    */   
/*  55:    */   public PaintBug(int startX, int startY)
/*  56:    */   {
/*  57: 64 */     this(startX, startY, 0.0D, getNextColor());
/*  58:    */   }
/*  59:    */   
/*  60:    */   public PaintBug(double startX, double startY)
/*  61:    */   {
/*  62: 70 */     this((int)startX, (int)startY, 0.0D, getNextColor());
/*  63:    */   }
/*  64:    */   
/*  65:    */   public PaintBug(Color color)
/*  66:    */   {
/*  67: 76 */     this(-99.0D, -99.0D, 0.0D, color);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public PaintBug(int startX, int startY, Color color)
/*  71:    */   {
/*  72: 82 */     this(startX, startY, 0.0D, color);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public PaintBug(double startX, double startY, Color color)
/*  76:    */   {
/*  77: 88 */     this((int)startX, (int)startY, 0.0D, color);
/*  78:    */   }
/*  79:    */   
/*  80:    */   private static void initializeGridIfNecessary()
/*  81:    */   {
/*  82: 94 */     if (!gridInitialized)
/*  83:    */     {
/*  84: 96 */       if (xSize != 0) {
/*  85: 98 */         window = new PaintBugWindow(xSize, ySize, drawGrid);
/*  86:    */       } else {
/*  87:102 */         window = new PaintBugWindow(drawGrid);
/*  88:    */       }
/*  89:104 */       gridInitialized = true;
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void move(double distance)
/*  94:    */   {
/*  95:111 */     waitUntilPreviousMoveCompleted();
/*  96:    */     
/*  97:    */ 
/*  98:114 */     this.movementThread = window.moveBug(this, this.xPos, this.yPos, this.direction, 
/*  99:115 */       distance, this.color, this.trailWidth, this.trailVisible, this.label);
/* 100:    */     
/* 101:    */ 
/* 102:118 */     this.xPos += distance * Math.cos(Math.toRadians(this.direction));
/* 103:119 */     this.yPos -= distance * Math.sin(Math.toRadians(this.direction));
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void turnRight(double degreesRight)
/* 107:    */   {
/* 108:125 */     waitUntilPreviousMoveCompleted();
/* 109:    */     
/* 110:    */ 
/* 111:128 */     this.movementThread = window.rotateBug(this, this.xPos, this.yPos, 
/* 112:129 */       this.direction, -degreesRight, this.color);
/* 113:    */     
/* 114:    */ 
/* 115:    */ 
/* 116:133 */     this.direction = ((this.direction - degreesRight) % 360.0D);
/* 117:136 */     if (this.direction < 0.0D) {
/* 118:138 */       this.direction += 360.0D;
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void turnLeft(double degreesLeft)
/* 123:    */   {
/* 124:145 */     waitUntilPreviousMoveCompleted();
/* 125:    */     
/* 126:    */ 
/* 127:148 */     this.movementThread = window.rotateBug(this, this.xPos, this.yPos, 
/* 128:149 */       this.direction, degreesLeft, this.color);
/* 129:    */     
/* 130:    */ 
/* 131:    */ 
/* 132:153 */     this.direction = ((this.direction + degreesLeft) % 360.0D);
/* 133:    */   }
/* 134:    */   
/* 135:    */   public double getXPos()
/* 136:    */   {
/* 137:159 */     waitUntilPreviousMoveCompleted();
/* 138:    */     
/* 139:161 */     return this.xPos;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public double getYPos()
/* 143:    */   {
/* 144:167 */     waitUntilPreviousMoveCompleted();
/* 145:    */     
/* 146:169 */     return this.yPos;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public double getDirection()
/* 150:    */   {
/* 151:175 */     waitUntilPreviousMoveCompleted();
/* 152:    */     
/* 153:177 */     return this.direction;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setPosition(double newX, double newY)
/* 157:    */   {
/* 158:183 */     waitUntilPreviousMoveCompleted();
/* 159:    */     
/* 160:185 */     window.hideBug(this, this.xPos, this.yPos, this.direction, this.color, this.label);
/* 161:186 */     this.xPos = newX;
/* 162:187 */     this.yPos = newY;
/* 163:188 */     window.showBug(this, this.xPos, this.yPos, this.direction, this.color, this.label);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setDirection(double newDirection)
/* 167:    */   {
/* 168:196 */     waitUntilPreviousMoveCompleted();
/* 169:    */     
/* 170:    */ 
/* 171:199 */     double angleRotated = (newDirection - this.direction) % 360.0D;
/* 172:200 */     if (angleRotated < 0.0D) {
/* 173:202 */       angleRotated += 360.0D;
/* 174:    */     }
/* 175:206 */     if (angleRotated <= 180.0D) {
/* 176:208 */       this.movementThread = window.rotateBug(this, this.xPos, this.yPos, 
/* 177:209 */         this.direction, angleRotated, this.color);
/* 178:    */     } else {
/* 179:213 */       this.movementThread = window.rotateBug(this, this.xPos, this.yPos, 
/* 180:214 */         this.direction, angleRotated - 360.0D, this.color);
/* 181:    */     }
/* 182:216 */     this.direction = newDirection;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setColor(Color newColor)
/* 186:    */   {
/* 187:222 */     waitUntilPreviousMoveCompleted();
/* 188:    */     
/* 189:224 */     window.hideBug(this, this.xPos, this.yPos, this.direction, this.color, this.label);
/* 190:225 */     this.color = newColor;
/* 191:226 */     window.showBug(this, this.xPos, this.yPos, this.direction, this.color, this.label);
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setTrailWidth(int newTrailWidth)
/* 195:    */   {
/* 196:232 */     this.trailWidth = newTrailWidth;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setLabel(String newLabel)
/* 200:    */   {
/* 201:238 */     waitUntilPreviousMoveCompleted();
/* 202:    */     
/* 203:240 */     window.hideBug(this, this.xPos, this.yPos, this.direction, this.color, this.label);
/* 204:241 */     this.label = newLabel;
/* 205:242 */     window.showBug(this, this.xPos, this.yPos, this.direction, this.color, this.label);
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void show()
/* 209:    */   {
/* 210:248 */     window.showBug(this, this.xPos, this.yPos, this.direction, this.color, this.label);
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void hide()
/* 214:    */   {
/* 215:254 */     window.hideBug(this, this.xPos, this.yPos, this.direction, this.color, this.label);
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void trailOn()
/* 219:    */   {
/* 220:260 */     this.trailVisible = true;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void trailOff()
/* 224:    */   {
/* 225:266 */     this.trailVisible = false;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public static void setWindowSize(int newXSize, int newYSize)
/* 229:    */   {
/* 230:272 */     if (gridInitialized)
/* 231:    */     {
/* 232:274 */       System.out.println("The PaintBug.setWindowSize method must be called before the first PaintBug is created");
/* 233:    */     }
/* 234:    */     else
/* 235:    */     {
/* 236:279 */       xSize = newXSize;
/* 237:280 */       ySize = newYSize;
/* 238:    */     }
/* 239:    */   }
/* 240:    */   
/* 241:    */   private synchronized void waitUntilPreviousMoveCompleted()
/* 242:    */   {
/* 243:288 */     if (this.movementThread == null) {
/* 244:290 */       return;
/* 245:    */     }
/* 246:    */     try
/* 247:    */     {
/* 248:294 */       this.movementThread.join();
/* 249:    */     }
/* 250:    */     catch (InterruptedException localInterruptedException) {}
/* 251:    */   }
/* 252:    */   
/* 253:    */   public static int getXSize()
/* 254:    */   {
/* 255:305 */     initializeGridIfNecessary();
/* 256:306 */     return window.getXSize();
/* 257:    */   }
/* 258:    */   
/* 259:    */   public static int getYSize()
/* 260:    */   {
/* 261:312 */     initializeGridIfNecessary();
/* 262:313 */     return window.getYSize();
/* 263:    */   }
/* 264:    */   
/* 265:    */   public static void drawGrid()
/* 266:    */   {
/* 267:319 */     if (gridInitialized) {
/* 268:321 */       System.out.println("The PaintBug.setGridSize method must be called before the first PaintBug is created");
/* 269:    */     } else {
/* 270:326 */       drawGrid = true;
/* 271:    */     }
/* 272:    */   }
/* 273:    */   
/* 274:    */   public static void setSpeed(int speed)
/* 275:    */   {
/* 276:333 */     initializeGridIfNecessary();
/* 277:    */     
/* 278:335 */     window.setSpeed(speed);
/* 279:    */   }
/* 280:    */   
/* 281:    */   private static Color getNextColor()
/* 282:    */   {
/* 283:341 */     if (colorNumber < colors.length)
/* 284:    */     {
/* 285:343 */       colorNumber += 1;
/* 286:344 */       return colors[(colorNumber - 1)];
/* 287:    */     }
/* 288:350 */     Color newColor = new Color((int)(Math.random() * 256.0D), 
/* 289:351 */       (int)(Math.random() * 256.0D), (int)(Math.random() * 256.0D));
/* 290:352 */     return newColor;
/* 291:    */   }
/* 292:    */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.PaintBug
 * JD-Core Version:    0.7.0.1
 */