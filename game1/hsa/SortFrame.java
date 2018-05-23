/*   1:    */ package game1.hsa;
/*   2:    */ 
/*   3:    */ import java.awt.Canvas;
/*   4:    */ import java.awt.Color;
/*   5:    */ import java.awt.Dimension;
/*   6:    */ import java.awt.Frame;
/*   7:    */ import java.awt.Graphics;
/*   8:    */ import java.awt.Label;
/*   9:    */ import java.awt.Scrollbar;
/*  10:    */ import java.awt.Toolkit;
/*  11:    */ import java.awt.Window;
/*  12:    */ import java.awt.event.AdjustmentEvent;
/*  13:    */ import java.awt.event.AdjustmentListener;
/*  14:    */ import java.awt.event.WindowAdapter;
/*  15:    */ import java.awt.event.WindowEvent;
/*  16:    */ import java.io.PrintStream;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ 
/*  19:    */ class SortFrame
/*  20:    */   extends Frame
/*  21:    */   implements AdjustmentListener
/*  22:    */ {
/*  23:    */   private SortCanvas canvas;
/*  24:    */   private Scrollbar scrollBar;
/*  25:    */   private int delay;
/*  26:    */   private Integer[] list;
/*  27:    */   
/*  28:    */   public SortFrame(String sortName, int width, int height)
/*  29:    */   {
/*  30: 77 */     super(sortName);
/*  31:    */     
/*  32:    */ 
/*  33: 80 */     this.canvas = new SortCanvas(width, height);
/*  34: 81 */     add("North", this.canvas);
/*  35: 82 */     this.scrollBar = new Scrollbar(0, 5000, 0, 1, 10000);
/*  36: 83 */     this.delay = ((int)(1000.0D * (1.0D - Math.log(5000.0D) / Math.log(10000.0D))));
/*  37:    */     
/*  38: 85 */     add("West", new Label("Slow  "));
/*  39: 86 */     add("Center", this.scrollBar);
/*  40: 87 */     add("East", new Label("  Fast"));
/*  41:    */     
/*  42:    */ 
/*  43: 90 */     addWindowListener(new WindowCloser());
/*  44:    */     
/*  45:    */ 
/*  46: 93 */     this.scrollBar.addAdjustmentListener(this);
/*  47:    */     
/*  48:    */ 
/*  49: 96 */     this.list = new Integer[width];
/*  50:    */     
/*  51:    */ 
/*  52: 99 */     fakeData(width, height);
/*  53:    */     
/*  54:    */ 
/*  55:102 */     pack();
/*  56:    */     
/*  57:    */ 
/*  58:105 */     Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
/*  59:106 */     setLocation(screen.width - getWidth(), 0);
/*  60:    */     
/*  61:108 */     show();
/*  62:    */   }
/*  63:    */   
/*  64:    */   private void fakeData(int xSize, int maxHeight)
/*  65:    */   {
/*  66:115 */     ArrayList numbers = new ArrayList();
/*  67:117 */     for (int i = 0; i < xSize; i++) {
/*  68:119 */       numbers.add(new Integer(i * maxHeight / xSize + 1));
/*  69:    */     }
/*  70:121 */     for (int i = 0; i < xSize; i++)
/*  71:    */     {
/*  72:123 */       int where = (int)(Math.random() * numbers.size());
/*  73:124 */       this.list[i] = ((Integer)numbers.get(where));
/*  74:125 */       numbers.remove(where);
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Comparable[] getData()
/*  79:    */   {
/*  80:132 */     return this.list;
/*  81:    */   }
/*  82:    */   
/*  83:    */   protected void swap(int i, int j)
/*  84:    */   {
/*  85:141 */     Comparable temp = this.list[i];
/*  86:142 */     this.list[i] = this.list[j];
/*  87:143 */     this.list[j] = ((Integer)temp);
/*  88:    */     
/*  89:145 */     this.canvas.drawSwap(i, j);
/*  90:    */     
/*  91:147 */     doDelay();
/*  92:    */   }
/*  93:    */   
/*  94:    */   protected void shift(int i, int j)
/*  95:    */   {
/*  96:154 */     Comparable temp = this.list[j];
/*  97:155 */     for (int k = j; k >= i + 1; k--) {
/*  98:157 */       this.list[k] = this.list[(k - 1)];
/*  99:    */     }
/* 100:159 */     this.list[i] = ((Integer)temp);
/* 101:    */     
/* 102:161 */     this.canvas.drawShift(i, j);
/* 103:    */     
/* 104:163 */     doDelay();
/* 105:    */   }
/* 106:    */   
/* 107:    */   protected void showMerge(int i, int j)
/* 108:    */   {
/* 109:170 */     this.canvas.drawMerge(i, j);
/* 110:    */     
/* 111:172 */     doDelay();
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void adjustmentValueChanged(AdjustmentEvent evt)
/* 115:    */   {
/* 116:182 */     int value = evt.getValue();
/* 117:183 */     this.delay = ((int)(1000.0D * (1.0D - Math.log(value) / Math.log(10000.0D))));
/* 118:    */   }
/* 119:    */   
/* 120:    */   private void doDelay()
/* 121:    */   {
/* 122:    */     try
/* 123:    */     {
/* 124:193 */       Thread.sleep(this.delay);
/* 125:    */     }
/* 126:    */     catch (InterruptedException e)
/* 127:    */     {
/* 128:199 */       System.out.println("Sleep interrupted.");
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   class WindowCloser
/* 133:    */     extends WindowAdapter
/* 134:    */   {
/* 135:    */     WindowCloser() {}
/* 136:    */     
/* 137:    */     public void windowClosing(WindowEvent e)
/* 138:    */     {
/* 139:209 */       e.getWindow().dispose();
/* 140:210 */       System.exit(0);
/* 141:    */     }
/* 142:    */   }
/* 143:    */   
/* 144:    */   class SortCanvas
/* 145:    */     extends Canvas
/* 146:    */   {
/* 147:    */     protected static final int MARGIN = 5;
/* 148:    */     private int width;
/* 149:    */     private int height;
/* 150:    */     private int xOffset;
/* 151:    */     private int yOffset;
/* 152:    */     private int xSize;
/* 153:    */     private int ySize;
/* 154:    */     private Color[] colors;
/* 155:    */     private Graphics graphics;
/* 156:    */     
/* 157:    */     public SortCanvas(int width, int height)
/* 158:    */     {
/* 159:230 */       this.width = (width + 10);
/* 160:231 */       this.height = (height + 40 + 10);
/* 161:    */       
/* 162:233 */       this.xOffset = 5;
/* 163:234 */       this.yOffset = 45;
/* 164:235 */       this.xSize = width;
/* 165:236 */       this.ySize = height;
/* 166:    */       
/* 167:238 */       createColors(height);
/* 168:    */     }
/* 169:    */     
/* 170:    */     public void addNotify()
/* 171:    */     {
/* 172:244 */       super.addNotify();
/* 173:    */       
/* 174:246 */       this.graphics = getGraphics();
/* 175:    */     }
/* 176:    */     
/* 177:    */     private void createColors(int maxHeight)
/* 178:    */     {
/* 179:257 */       int orangeBreak = maxHeight / 5;
/* 180:258 */       int yellowBreak = maxHeight * 2 / 5;
/* 181:259 */       int greenBreak = maxHeight * 3 / 5;
/* 182:260 */       int blueBreak = maxHeight * 4 / 5;
/* 183:    */       
/* 184:262 */       this.colors = new Color[maxHeight];
/* 185:264 */       for (int i = 0; i < maxHeight; i++) {
/* 186:270 */         if (i <= orangeBreak)
/* 187:    */         {
/* 188:272 */           float location = i / orangeBreak;
/* 189:273 */           this.colors[i] = new Color(1.0F, (float)(0.6D * location), 0.0F);
/* 190:    */         }
/* 191:275 */         else if (i <= yellowBreak)
/* 192:    */         {
/* 193:277 */           float location = (i - orangeBreak) / (
/* 194:278 */             yellowBreak - orangeBreak);
/* 195:279 */           this.colors[i] = 
/* 196:280 */             new Color(1.0F, (float)(0.6D + 0.4D * location), 0.0F);
/* 197:    */         }
/* 198:282 */         else if (i <= greenBreak)
/* 199:    */         {
/* 200:284 */           float location = (i - yellowBreak) / (
/* 201:285 */             greenBreak - yellowBreak);
/* 202:286 */           this.colors[i] = new Color((float)(1.0D - location), 1.0F, 0.0F);
/* 203:    */         }
/* 204:288 */         else if (i <= blueBreak)
/* 205:    */         {
/* 206:290 */           float location = (i - greenBreak) / (blueBreak - greenBreak);
/* 207:291 */           this.colors[i] = new Color(0.0F, (float)(1.0D - location), location);
/* 208:    */         }
/* 209:    */         else
/* 210:    */         {
/* 211:295 */           float location = (i - blueBreak) / (
/* 212:296 */             maxHeight - 1 - blueBreak);
/* 213:297 */           this.colors[i] = new Color((float)(0.6D * location), 0.0F, 1.0F);
/* 214:    */         }
/* 215:    */       }
/* 216:    */     }
/* 217:    */     
/* 218:    */     protected void drawSwap(int i, int j)
/* 219:    */     {
/* 220:305 */       drawElement(i);
/* 221:306 */       drawElement(j);
/* 222:    */       
/* 223:    */ 
/* 224:309 */       this.graphics.setColor(Color.white);
/* 225:310 */       this.graphics.fillRect(this.xOffset - 4, 0, this.xSize + 8, this.yOffset - 3);
/* 226:    */       
/* 227:    */ 
/* 228:313 */       this.graphics.setColor(Color.red);
/* 229:    */       
/* 230:315 */       this.graphics.drawLine(this.xOffset + i, this.yOffset - 8, this.xOffset + i, 10);
/* 231:316 */       this.graphics.drawLine(this.xOffset + i, this.yOffset - 8, this.xOffset + i + 3, 
/* 232:317 */         this.yOffset - 14);
/* 233:318 */       this.graphics.drawLine(this.xOffset + i, this.yOffset - 8, this.xOffset + i - 3, 
/* 234:319 */         this.yOffset - 14);
/* 235:    */       
/* 236:321 */       this.graphics.drawLine(this.xOffset + i, 10, this.xOffset + j, 10);
/* 237:    */       
/* 238:323 */       this.graphics.drawLine(this.xOffset + j, this.yOffset - 8, this.xOffset + j, 10);
/* 239:324 */       this.graphics.drawLine(this.xOffset + j, this.yOffset - 8, this.xOffset + j + 3, 
/* 240:325 */         this.yOffset - 14);
/* 241:326 */       this.graphics.drawLine(this.xOffset + j, this.yOffset - 8, this.xOffset + j - 3, 
/* 242:327 */         this.yOffset - 14);
/* 243:    */     }
/* 244:    */     
/* 245:    */     protected void drawShift(int i, int j)
/* 246:    */     {
/* 247:334 */       this.graphics.setColor(Color.white);
/* 248:335 */       this.graphics.fillRect(this.xOffset - 4, 0, this.xSize + 8, this.yOffset - 3);
/* 249:    */       
/* 250:    */ 
/* 251:338 */       this.graphics.setColor(Color.red);
/* 252:    */       
/* 253:340 */       this.graphics.drawLine(this.xOffset + i, this.yOffset - 8, this.xOffset + i, 10);
/* 254:341 */       this.graphics.drawLine(this.xOffset + i, this.yOffset - 8, this.xOffset + i + 3, 
/* 255:342 */         this.yOffset - 14);
/* 256:343 */       this.graphics.drawLine(this.xOffset + i, this.yOffset - 8, this.xOffset + i - 3, 
/* 257:344 */         this.yOffset - 14);
/* 258:    */       
/* 259:346 */       this.graphics.drawLine(this.xOffset + i, 10, this.xOffset + j, 10);
/* 260:    */       
/* 261:348 */       this.graphics.drawLine(this.xOffset + j, this.yOffset - 8, this.xOffset + j, 10);
/* 262:350 */       for (int k = j; k >= i; k--) {
/* 263:352 */         drawElement(k);
/* 264:    */       }
/* 265:    */     }
/* 266:    */     
/* 267:    */     protected void drawMerge(int i, int j)
/* 268:    */     {
/* 269:360 */       this.graphics.setColor(Color.white);
/* 270:361 */       this.graphics.fillRect(this.xOffset - 4, 0, this.xSize + 8, this.yOffset - 3);
/* 271:    */       
/* 272:    */ 
/* 273:364 */       this.graphics.setColor(Color.red);
/* 274:    */       
/* 275:366 */       this.graphics.drawLine(this.xOffset + i, 30, this.xOffset + j, 30);
/* 276:367 */       this.graphics.drawLine(this.xOffset + i, 35, this.xOffset + j, 35);
/* 277:369 */       for (int k = i; k <= j; k++) {
/* 278:371 */         drawElement(k);
/* 279:    */       }
/* 280:    */     }
/* 281:    */     
/* 282:    */     private void drawElement(int i)
/* 283:    */     {
/* 284:379 */       int h = SortFrame.this.list[i].intValue();
/* 285:380 */       this.graphics.setColor(this.colors[(h - 1)]);
/* 286:381 */       this.graphics.drawLine(this.xOffset + i, this.yOffset + this.ySize, this.xOffset + i, 
/* 287:382 */         this.yOffset + this.ySize - h);
/* 288:383 */       this.graphics.setColor(Color.white);
/* 289:384 */       this.graphics.drawLine(this.xOffset + i, this.yOffset + this.ySize - h - 1, 
/* 290:385 */         this.xOffset + i, this.yOffset);
/* 291:    */     }
/* 292:    */     
/* 293:    */     public void paint(Graphics g)
/* 294:    */     {
/* 295:391 */       this.graphics.clearRect(0, 0, this.width, this.height);
/* 296:392 */       this.graphics.setColor(Color.black);
/* 297:393 */       this.graphics.drawRect(this.xOffset - 2, this.yOffset - 2, 
/* 298:394 */         this.xSize + 4, this.ySize + 4);
/* 299:395 */       for (int i = 0; i < this.xSize; i++)
/* 300:    */       {
/* 301:397 */         int h = SortFrame.this.list[i].intValue();
/* 302:398 */         this.graphics.setColor(this.colors[(h - 1)]);
/* 303:399 */         this.graphics.drawLine(this.xOffset + i, this.yOffset + this.ySize, 
/* 304:400 */           this.xOffset + i, this.yOffset + this.ySize - h);
/* 305:    */       }
/* 306:    */     }
/* 307:    */     
/* 308:    */     public Dimension getMinimumSize()
/* 309:    */     {
/* 310:407 */       return new Dimension(this.width, this.height);
/* 311:    */     }
/* 312:    */     
/* 313:    */     public Dimension getPreferredSize()
/* 314:    */     {
/* 315:413 */       return getMinimumSize();
/* 316:    */     }
/* 317:    */   }
/* 318:    */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.SortFrame
 * JD-Core Version:    0.7.0.1
 */