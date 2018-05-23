/*    1:     */ package game1.hsa;
/*    2:     */ 
/*    3:     */ import java.awt.Canvas;
/*    4:     */ import java.awt.Dimension;
/*    5:     */ import java.awt.Font;
/*    6:     */ import java.awt.FontMetrics;
/*    7:     */ import java.awt.Graphics;
/*    8:     */ import java.awt.Image;
/*    9:     */ import java.awt.Point;
/*   10:     */ import java.awt.Toolkit;
/*   11:     */ import java.io.PrintStream;
/*   12:     */ import java.lang.reflect.Method;
/*   13:     */ 
/*   14:     */ class TreeCanvas
/*   15:     */   extends Canvas
/*   16:     */ {
/*   17: 820 */   private static int VERT_MARGIN = 4;
/*   18: 821 */   private static int HORZ_MARGIN = 4;
/*   19: 822 */   private static int SPACE_BETWEEN = 6;
/*   20: 823 */   private static int MAIN_VERT_MARGIN = 10;
/*   21: 824 */   private static int MAIN_HORZ_MARGIN = 10;
/*   22:     */   private int xSize;
/*   23:     */   private int ySize;
/*   24:     */   private Image offscreen;
/*   25:     */   private Font font;
/*   26:     */   private FontMetrics fm;
/*   27:     */   private int treeDepth;
/*   28:     */   private int numNodes;
/*   29:     */   private int boxWidth;
/*   30:     */   private int boxHeight;
/*   31:     */   private String[] nodes;
/*   32:     */   private int[] nodeDepth;
/*   33:     */   private Point[] nodePos;
/*   34:     */   private static Method getLeft;
/*   35:     */   private static Method getRight;
/*   36:     */   private static Method getValue;
/*   37:     */   
/*   38:     */   public TreeCanvas(Object root, int fontSize)
/*   39:     */   {
/*   40:     */     try
/*   41:     */     {
/*   42: 848 */       userTreeNodeClass = Class.forName("TreeNode");
/*   43:     */     }
/*   44:     */     catch (ClassNotFoundException e)
/*   45:     */     {
/*   46:     */       Class userTreeNodeClass;
/*   47: 852 */       System.out.println("TreeNode class not found"); return;
/*   48:     */     }
/*   49:     */     Class userTreeNodeClass;
/*   50: 856 */     Class[] noParams = new Class[0];
/*   51:     */     try
/*   52:     */     {
/*   53: 860 */       getLeft = userTreeNodeClass.getMethod("getLeft", noParams);
/*   54: 861 */       getRight = userTreeNodeClass.getMethod("getRight", noParams);
/*   55: 862 */       getValue = userTreeNodeClass.getMethod("getValue", noParams);
/*   56:     */     }
/*   57:     */     catch (NoSuchMethodException e)
/*   58:     */     {
/*   59: 866 */       System.out.println("No method found");
/*   60: 867 */       return;
/*   61:     */     }
/*   62: 871 */     this.font = new Font("SansSerif", 0, fontSize);
/*   63: 872 */     this.fm = getFontMetrics(this.font);
/*   64:     */     
/*   65:     */ 
/*   66: 875 */     this.treeDepth = getDepth(root);
/*   67:     */     
/*   68:     */ 
/*   69: 878 */     this.boxWidth = (getWidestNode(root) + 2 * HORZ_MARGIN);
/*   70: 879 */     if ((this.boxWidth + SPACE_BETWEEN) % 2 == 1) {
/*   71: 881 */       this.boxWidth += 1;
/*   72:     */     }
/*   73: 884 */     this.boxHeight = (this.fm.getAscent() + 2 * VERT_MARGIN);
/*   74:     */     
/*   75: 886 */     this.numNodes = ((int)Math.round(Math.pow(2.0D, this.treeDepth)) - 1);
/*   76:     */     
/*   77:     */ 
/*   78: 889 */     this.nodes = new String[this.numNodes];
/*   79: 890 */     this.nodeDepth = new int[this.numNodes];
/*   80: 891 */     this.nodePos = new Point[this.numNodes];
/*   81:     */     
/*   82:     */ 
/*   83: 894 */     place(root, 0, 0);
/*   84:     */     
/*   85:     */ 
/*   86: 897 */     positionUsingConstantDistance();
/*   87:     */     
/*   88:     */ 
/*   89:     */ 
/*   90: 901 */     Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
/*   91: 902 */     if ((this.xSize > screen.width * 2 / 3) || 
/*   92: 903 */       (this.ySize > screen.height * 3 / 4))
/*   93:     */     {
/*   94: 906 */       this.nodePos[0].x = 0;
/*   95: 907 */       positionUsingVariableDistance();
/*   96:     */     }
/*   97: 910 */     setSize(this.xSize, this.ySize);
/*   98:     */   }
/*   99:     */   
/*  100:     */   private void place(Object node, int index, int depth)
/*  101:     */   {
/*  102: 916 */     if (node == null) {
/*  103: 918 */       return;
/*  104:     */     }
/*  105:     */     try
/*  106:     */     {
/*  107: 922 */       this.nodes[index] = getValue.invoke(node, null).toString();
/*  108: 923 */       this.nodeDepth[index] = depth;
/*  109: 924 */       this.nodePos[index] = new Point(0, 
/*  110: 925 */         MAIN_VERT_MARGIN + depth * 3 * this.boxHeight);
/*  111: 926 */       place(getLeft.invoke(node, null), 2 * index + 1, depth + 1);
/*  112: 927 */       place(getRight.invoke(node, null), 2 * index + 2, depth + 1);
/*  113:     */     }
/*  114:     */     catch (Exception e)
/*  115:     */     {
/*  116: 931 */       System.out.println("Unable to invoke TreeNode method");
/*  117: 932 */       return;
/*  118:     */     }
/*  119:     */   }
/*  120:     */   
/*  121:     */   private void positionUsingConstantDistance()
/*  122:     */   {
/*  123: 945 */     int maxWidth = (int)Math.round(Math.pow(2.0D, this.treeDepth - 1));
/*  124: 946 */     int[] xMargin = new int[this.treeDepth];
/*  125: 947 */     int[] spaceBetween = new int[this.treeDepth];
/*  126: 948 */     xMargin[(this.treeDepth - 1)] = MAIN_HORZ_MARGIN;
/*  127: 949 */     spaceBetween[(this.treeDepth - 1)] = SPACE_BETWEEN;
/*  128: 951 */     for (int depth = this.treeDepth - 2; depth >= 0; depth--)
/*  129:     */     {
/*  130: 953 */       xMargin[depth] = 
/*  131: 954 */         (xMargin[(depth + 1)] + this.boxWidth + (spaceBetween[(depth + 1)] - this.boxWidth) / 2);
/*  132: 955 */       spaceBetween[depth] = (spaceBetween[(depth + 1)] * 2 + this.boxWidth);
/*  133:     */     }
/*  134: 958 */     for (int cnt = 0; cnt < this.numNodes; cnt++) {
/*  135: 960 */       if (this.nodes[cnt] != null)
/*  136:     */       {
/*  137: 964 */         int depth = this.nodeDepth[cnt];
/*  138: 965 */         int beginLine = (int)Math.round(Math.pow(2.0D, depth)) - 1;
/*  139: 966 */         this.nodePos[cnt].x = 
/*  140: 967 */           (xMargin[depth] + (cnt - beginLine) * (spaceBetween[depth] + this.boxWidth));
/*  141:     */       }
/*  142:     */     }
/*  143: 971 */     this.xSize = 
/*  144: 972 */       (MAIN_HORZ_MARGIN * 2 + (maxWidth - 1) * (this.boxWidth + SPACE_BETWEEN) + this.boxWidth);
/*  145: 973 */     this.ySize = 
/*  146: 974 */       (MAIN_VERT_MARGIN * 2 + (this.treeDepth - 1) * 3 * this.boxHeight + this.boxHeight);
/*  147:     */   }
/*  148:     */   
/*  149:     */   private void positionUsingVariableDistance()
/*  150:     */   {
/*  151: 980 */     for (int cnt = 1; cnt < this.numNodes; cnt++) {
/*  152: 982 */       if (this.nodes[cnt] != null) {
/*  153: 984 */         position(cnt);
/*  154:     */       }
/*  155:     */     }
/*  156: 990 */     int min = this.nodePos[0].x;
/*  157: 991 */     int max = min;
/*  158: 992 */     for (int cnt = 0; cnt < this.numNodes; cnt++) {
/*  159: 994 */       if (this.nodes[cnt] != null)
/*  160:     */       {
/*  161: 996 */         min = Math.min(min, this.nodePos[cnt].x);
/*  162: 997 */         max = Math.max(max, this.nodePos[cnt].x);
/*  163:     */       }
/*  164:     */     }
/*  165:1000 */     for (int cnt = 0; cnt < this.numNodes; cnt++) {
/*  166:1002 */       if (this.nodes[cnt] != null) {
/*  167:1004 */         this.nodePos[cnt].x += MAIN_HORZ_MARGIN - min;
/*  168:     */       }
/*  169:     */     }
/*  170:1009 */     this.xSize = (2 * MAIN_HORZ_MARGIN + max - min + this.boxWidth);
/*  171:1010 */     this.ySize = 
/*  172:1011 */       (MAIN_VERT_MARGIN * 2 + (this.treeDepth - 1) * 3 * this.boxHeight + this.boxHeight);
/*  173:     */   }
/*  174:     */   
/*  175:     */   private void position(int index)
/*  176:     */   {
/*  177:1027 */     int depth = this.nodeDepth[index];
/*  178:1028 */     int endLine = (int)Math.round(Math.pow(2.0D, depth + 1));
/*  179:1029 */     int parent = (index - 1) / 2;
/*  180:     */     
/*  181:1031 */     boolean left = index % 2 == 1;
/*  182:     */     int x;
/*  183:     */     int x;
/*  184:1034 */     if (left) {
/*  185:1036 */       x = this.nodePos[parent].x - (this.boxWidth + SPACE_BETWEEN) / 2;
/*  186:     */     } else {
/*  187:1040 */       x = this.nodePos[parent].x + (this.boxWidth + SPACE_BETWEEN) / 2;
/*  188:     */     }
/*  189:1042 */     this.nodePos[index].x = x;
/*  190:     */     
/*  191:     */ 
/*  192:1045 */     int prevIndex = getPrevious(index);
/*  193:1047 */     if (prevIndex == -1) {
/*  194:1050 */       return;
/*  195:     */     }
/*  196:1054 */     int difference = x - this.nodePos[prevIndex].x;
/*  197:1055 */     if (difference >= this.boxWidth + SPACE_BETWEEN) {
/*  198:1058 */       return;
/*  199:     */     }
/*  200:1062 */     int extraSpaceNeeded = this.boxWidth + SPACE_BETWEEN - difference;
/*  201:     */     
/*  202:     */ 
/*  203:1065 */     shiftLeft(prevIndex, extraSpaceNeeded, index);
/*  204:     */   }
/*  205:     */   
/*  206:     */   private int getParent(int index)
/*  207:     */   {
/*  208:1071 */     if (index == 0) {
/*  209:1073 */       return -1;
/*  210:     */     }
/*  211:1075 */     return (index - 1) / 2;
/*  212:     */   }
/*  213:     */   
/*  214:     */   private int getPrevious(int index)
/*  215:     */   {
/*  216:1081 */     int depth = this.nodeDepth[index];
/*  217:1082 */     int beginLine = (int)Math.round(Math.pow(2.0D, depth)) - 1;
/*  218:1083 */     int prevIndex = index - 1;
/*  219:1085 */     while (prevIndex >= beginLine)
/*  220:     */     {
/*  221:1087 */       if (this.nodes[prevIndex] != null) {
/*  222:     */         break;
/*  223:     */       }
/*  224:1089 */       prevIndex--;
/*  225:     */     }
/*  226:1091 */     if (prevIndex < beginLine) {
/*  227:1093 */       return -1;
/*  228:     */     }
/*  229:1097 */     return prevIndex;
/*  230:     */   }
/*  231:     */   
/*  232:     */   private void printPos(int index)
/*  233:     */   {
/*  234:1104 */     System.out.println("Inserted node " + index + 
/*  235:1105 */       " (" + this.nodes[index] + ")");
/*  236:1106 */     for (int cnt = 0; cnt <= index; cnt++) {
/*  237:1108 */       if (this.nodes[cnt] != null) {
/*  238:1110 */         System.out.println("N: " + cnt + " (" + this.nodes[cnt] + 
/*  239:1111 */           ")  d: " + this.nodeDepth[cnt] + "  x: " + 
/*  240:1112 */           this.nodePos[cnt].x + "  y: " + this.nodePos[cnt].y);
/*  241:     */       }
/*  242:     */     }
/*  243:     */   }
/*  244:     */   
/*  245:     */   private void checkPos(int currentNode)
/*  246:     */   {
/*  247:1120 */     for (int cnt = 1; cnt <= currentNode; cnt++) {
/*  248:1122 */       if (this.nodes[cnt] != null)
/*  249:     */       {
/*  250:1125 */         int prevIndex = getPrevious(cnt);
/*  251:1126 */         if (prevIndex != -1)
/*  252:     */         {
/*  253:1128 */           int difference = this.nodePos[cnt].x - this.nodePos[prevIndex].x;
/*  254:1129 */           if (difference < this.boxWidth + SPACE_BETWEEN)
/*  255:     */           {
/*  256:1131 */             System.out.println("PROBLEM (1) " + cnt + " " + difference);
/*  257:1132 */             this.numNodes = (currentNode + 1);
/*  258:1133 */             return;
/*  259:     */           }
/*  260:     */         }
/*  261:1136 */         int parIndex = getParent(cnt);
/*  262:1137 */         if (prevIndex != -1)
/*  263:     */         {
/*  264:1139 */           boolean left = cnt % 2 == 1;
/*  265:1140 */           int difference = this.nodePos[cnt].x - this.nodePos[parIndex].x;
/*  266:1141 */           if (left)
/*  267:     */           {
/*  268:1143 */             if (difference > (this.boxWidth + SPACE_BETWEEN) / 2)
/*  269:     */             {
/*  270:1145 */               System.out.println("PROBLEM (2) " + cnt + " " + difference);
/*  271:1146 */               this.numNodes = (currentNode + 1);
/*  272:     */             }
/*  273:     */           }
/*  274:1152 */           else if (difference < (this.boxWidth + SPACE_BETWEEN) / 2)
/*  275:     */           {
/*  276:1154 */             System.out.println("PROBLEM (3) " + cnt + " " + difference);
/*  277:1155 */             this.numNodes = (currentNode + 1);
/*  278:1156 */             return;
/*  279:     */           }
/*  280:     */         }
/*  281:     */       }
/*  282:     */     }
/*  283:     */   }
/*  284:     */   
/*  285:     */   private void calcParentX(int nextIndex, int lastPositionedNode)
/*  286:     */   {
/*  287:1167 */     while (nextIndex >= 0)
/*  288:     */     {
/*  289:1169 */       int index = nextIndex;
/*  290:1170 */       int left = 2 * index + 1;
/*  291:1171 */       int right = 2 * index + 2;
/*  292:1172 */       int prevX = this.nodePos[index].x;
/*  293:     */       
/*  294:     */ 
/*  295:1175 */       nextIndex = getParent(index);
/*  296:     */       int x;
/*  297:     */       int x;
/*  298:1177 */       if (this.nodes[left] == null)
/*  299:     */       {
/*  300:1179 */         if (this.nodes[right] == null) {
/*  301:1181 */           System.out.println("Internal error in TreeUtil - calcParent called on node with no children");
/*  302:     */         }
/*  303:1184 */         x = this.nodePos[right].x - (this.boxWidth + SPACE_BETWEEN) / 2;
/*  304:     */       }
/*  305:     */       else
/*  306:     */       {
/*  307:     */         int x;
/*  308:1188 */         if (this.nodes[right] == null) {
/*  309:1190 */           x = this.nodePos[left].x + (this.boxWidth + SPACE_BETWEEN) / 2;
/*  310:     */         } else {
/*  311:1194 */           x = (this.nodePos[left].x + this.nodePos[right].x) / 2;
/*  312:     */         }
/*  313:     */       }
/*  314:1200 */       if (x != prevX) {
/*  315:1203 */         shiftLeft(index, prevX - x, lastPositionedNode);
/*  316:     */       }
/*  317:1207 */       int prevIndex = getPrevious(index);
/*  318:1209 */       if (prevIndex != -1)
/*  319:     */       {
/*  320:1216 */         int difference = this.nodePos[index].x - this.nodePos[prevIndex].x;
/*  321:1217 */         if (difference < this.boxWidth + SPACE_BETWEEN)
/*  322:     */         {
/*  323:1224 */           int extraSpaceNeeded = this.boxWidth + SPACE_BETWEEN - difference;
/*  324:     */           
/*  325:     */ 
/*  326:1227 */           shiftLeft(prevIndex, extraSpaceNeeded, lastPositionedNode);
/*  327:     */         }
/*  328:     */       }
/*  329:     */     }
/*  330:     */   }
/*  331:     */   
/*  332:     */   private void shiftLeft(int index, int amount, int lastPositionedNode)
/*  333:     */   {
/*  334:1234 */     int depth = this.nodeDepth[index];
/*  335:1235 */     int beginLine = (int)Math.round(Math.pow(2.0D, depth)) - 1;
/*  336:1238 */     for (int cnt = index; cnt >= beginLine; cnt--) {
/*  337:1240 */       if (this.nodes[cnt] != null) {
/*  338:1242 */         this.nodePos[cnt].x -= amount;
/*  339:     */       }
/*  340:     */     }
/*  341:1247 */     for (int cnt = index; cnt >= beginLine; cnt--) {
/*  342:1249 */       if (this.nodes[cnt] != null) {
/*  343:1251 */         calcParentX(getParent(cnt), lastPositionedNode);
/*  344:     */       }
/*  345:     */     }
/*  346:     */   }
/*  347:     */   
/*  348:     */   private void shiftChildren(int parent, int amount, int lastPositionedNode)
/*  349:     */   {
/*  350:1259 */     int left = 2 * parent + 1;
/*  351:1260 */     int right = 2 * parent + 2;
/*  352:1261 */     if ((left <= lastPositionedNode) && (this.nodes[left] != null))
/*  353:     */     {
/*  354:1263 */       this.nodePos[left].x -= amount;
/*  355:1264 */       shiftChildren(left, amount, lastPositionedNode);
/*  356:     */     }
/*  357:1266 */     if ((right <= lastPositionedNode) && (this.nodes[right] != null))
/*  358:     */     {
/*  359:1268 */       this.nodePos[right].x -= amount;
/*  360:1269 */       shiftChildren(right, amount, lastPositionedNode);
/*  361:     */     }
/*  362:     */   }
/*  363:     */   
/*  364:     */   private void drawToOffscreen()
/*  365:     */   {
/*  366:1276 */     Graphics g = this.offscreen.getGraphics();
/*  367:     */     
/*  368:1278 */     g.setFont(this.font);
/*  369:1280 */     for (int cnt = 0; cnt < this.numNodes; cnt++) {
/*  370:1282 */       if (this.nodes[cnt] != null)
/*  371:     */       {
/*  372:1286 */         int x = this.nodePos[cnt].x;
/*  373:1287 */         int y = this.nodePos[cnt].y;
/*  374:1288 */         String s = this.nodes[cnt];
/*  375:1289 */         g.drawRect(x, y, this.boxWidth, this.boxHeight);
/*  376:1290 */         y += VERT_MARGIN + this.fm.getAscent();
/*  377:1291 */         x = x + (this.boxWidth - this.fm.stringWidth(s)) / 2 + 1;
/*  378:1292 */         g.drawString(s, x, y);
/*  379:1295 */         if (cnt != 0)
/*  380:     */         {
/*  381:1299 */           int x1 = this.nodePos[cnt].x + this.boxWidth / 2;
/*  382:1300 */           int y1 = this.nodePos[cnt].y;
/*  383:1301 */           int parent = (cnt - 1) / 2;
/*  384:1302 */           int leftRight = (cnt + 1) % 2 + 1;
/*  385:1303 */           int x2 = this.nodePos[parent].x + this.boxWidth * leftRight / 3;
/*  386:1304 */           int y2 = this.nodePos[parent].y + this.boxHeight;
/*  387:1305 */           g.drawLine(x1, y1, x2, y2);
/*  388:     */         }
/*  389:     */       }
/*  390:     */     }
/*  391:     */   }
/*  392:     */   
/*  393:     */   public void addNotify()
/*  394:     */   {
/*  395:1312 */     super.addNotify();
/*  396:     */     
/*  397:1314 */     this.offscreen = createImage(this.xSize, this.ySize);
/*  398:1315 */     drawToOffscreen();
/*  399:     */   }
/*  400:     */   
/*  401:     */   public void paint(Graphics g)
/*  402:     */   {
/*  403:1321 */     update(g);
/*  404:     */   }
/*  405:     */   
/*  406:     */   public void update(Graphics g)
/*  407:     */   {
/*  408:1328 */     g.drawImage(this.offscreen, 0, 0, null, null);
/*  409:     */   }
/*  410:     */   
/*  411:     */   public Dimension getMinimumSize()
/*  412:     */   {
/*  413:1334 */     return new Dimension(this.xSize, this.ySize);
/*  414:     */   }
/*  415:     */   
/*  416:     */   public Dimension getPreferredSize()
/*  417:     */   {
/*  418:1343 */     return getMinimumSize();
/*  419:     */   }
/*  420:     */   
/*  421:     */   public Dimension getMaximumSize()
/*  422:     */   {
/*  423:1349 */     return getMinimumSize();
/*  424:     */   }
/*  425:     */   
/*  426:     */   public int getDepth(Object node)
/*  427:     */   {
/*  428:1361 */     if (node == null) {
/*  429:1363 */       return 0;
/*  430:     */     }
/*  431:     */     try
/*  432:     */     {
/*  433:1368 */       Object left = getLeft.invoke(node, null);
/*  434:1369 */       right = getRight.invoke(node, null);
/*  435:     */     }
/*  436:     */     catch (Exception e)
/*  437:     */     {
/*  438:     */       Object right;
/*  439:1373 */       System.out.println("Unable to invoke TreeNode method");
/*  440:1374 */       return 0;
/*  441:     */     }
/*  442:     */     Object right;
/*  443:     */     Object left;
/*  444:1377 */     int leftDepth = getDepth(left);
/*  445:1378 */     int rightDepth = getDepth(right);
/*  446:     */     
/*  447:1380 */     return 1 + Math.max(leftDepth, rightDepth);
/*  448:     */   }
/*  449:     */   
/*  450:     */   public int getWidestNode(Object node)
/*  451:     */   {
/*  452:1389 */     if (node == null) {
/*  453:1391 */       return 0;
/*  454:     */     }
/*  455:     */     try
/*  456:     */     {
/*  457:1396 */       int width = this.fm.stringWidth(getValue.invoke(node, null).toString());
/*  458:1397 */       Object left = getLeft.invoke(node, null);
/*  459:1398 */       right = getRight.invoke(node, null);
/*  460:     */     }
/*  461:     */     catch (Exception e)
/*  462:     */     {
/*  463:     */       Object right;
/*  464:1402 */       System.out.println("Unable to invoke TreeNode method");
/*  465:1403 */       return 0;
/*  466:     */     }
/*  467:     */     int width;
/*  468:     */     Object right;
/*  469:     */     Object left;
/*  470:1405 */     int leftWidth = getWidestNode(left);
/*  471:1406 */     int rightWidth = getWidestNode(right);
/*  472:     */     
/*  473:1408 */     return Math.max(width, Math.max(leftWidth, rightWidth));
/*  474:     */   }
/*  475:     */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.TreeCanvas
 * JD-Core Version:    0.7.0.1
 */