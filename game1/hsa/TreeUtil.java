/*   1:    */ package game1.hsa;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.lang.reflect.Constructor;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ 
/*   8:    */ public class TreeUtil
/*   9:    */ {
/*  10:    */   private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
/*  11:    */   private static final int NODE_IS_TALLER = 1;
/*  12:    */   private static final int DEPTH_UNCHANGED = 0;
/*  13:    */   private static final int NODE_IS_SHORTER = -1;
/*  14:    */   private static final String DEFAULT_TITLE = "Tree";
/*  15:    */   private static final int DEFAULT_FONT_SIZE = 12;
/*  16:    */   private static Method getLeft;
/*  17:    */   private static Method getRight;
/*  18:    */   private static Method getValue;
/*  19:    */   private static Method setValue;
/*  20: 39 */   private static Object[] setArgument = new Object[1];
/*  21:    */   
/*  22:    */   public static Object createNumberTree(int paramInt)
/*  23:    */   {
/*  24: 59 */     throw new Error("Unresolved compilation problem: \n\tThe method convertToUserTreeNode(TreeNode) from the type TreeUtil refers to the missing type TreeNode\n");
/*  25:    */   }
/*  26:    */   
/*  27:    */   public static Object createLetterTree(int paramInt)
/*  28:    */   {
/*  29: 91 */     throw new Error("Unresolved compilation problem: \n\tThe method convertToUserTreeNode(TreeNode) from the type TreeUtil refers to the missing type TreeNode\n");
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static Object createIntegerTree(int paramInt)
/*  33:    */   {
/*  34:106 */     throw new Error("Unresolved compilation problem: \n\tThe method convertToUserTreeNode(TreeNode) from the type TreeUtil refers to the missing type TreeNode\n");
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static Object createBalancedNumberTree(int numNodes)
/*  38:    */   {
/*  39:112 */     AVLTreeNode avlRoot = null;
/*  40:113 */     ArrayList a = new ArrayList(numNodes);
/*  41:115 */     for (int i = 0; i < numNodes; i++) {
/*  42:117 */       a.add(new Integer(i + 1));
/*  43:    */     }
/*  44:121 */     for (int i = 0; i < numNodes; i++)
/*  45:    */     {
/*  46:123 */       int r = (int)(Math.random() * a.size());
/*  47:124 */       avlRoot = avlInsert(avlRoot, (Comparable)a.get(r)).node;
/*  48:125 */       a.remove(r);
/*  49:    */     }
/*  50:129 */     return convertAVLToUserTreeNode(avlRoot);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static Object createBalancedLetterTree(int numNodes)
/*  54:    */   {
/*  55:135 */     AVLTreeNode avlRoot = null;
/*  56:136 */     ArrayList a = new ArrayList(numNodes);
/*  57:137 */     StringBuffer letters = new StringBuffer("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
/*  58:140 */     if (numNodes > 26)
/*  59:    */     {
/*  60:142 */       System.out.println("You cannot have more than 26 letter nodes");
/*  61:143 */       return null;
/*  62:    */     }
/*  63:146 */     for (int i = 0; i < numNodes; i++)
/*  64:    */     {
/*  65:148 */       int pos = (int)(Math.random() * letters.length());
/*  66:149 */       a.add(letters.substring(pos, pos + 1));
/*  67:150 */       letters.deleteCharAt(pos);
/*  68:    */     }
/*  69:154 */     for (int i = 0; i < numNodes; i++)
/*  70:    */     {
/*  71:156 */       int r = (int)(Math.random() * a.size());
/*  72:157 */       avlRoot = avlInsert(avlRoot, (Comparable)a.get(r)).node;
/*  73:158 */       a.remove(r);
/*  74:    */     }
/*  75:162 */     return convertAVLToUserTreeNode(avlRoot);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static void displayTreeInWindow(Object root)
/*  79:    */   {
/*  80:168 */     new TreeWindow(root, "Tree", 12);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static void displayTreeInWindow(Object root, String title)
/*  84:    */   {
/*  85:174 */     new TreeWindow(root, title, 12);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static void displayTreeInWindow(Object root, String title, int fontSize)
/*  89:    */   {
/*  90:181 */     if ((fontSize < 6) || (fontSize > 72))
/*  91:    */     {
/*  92:183 */       System.out.println("The font size between 6 and 72");
/*  93:184 */       return;
/*  94:    */     }
/*  95:187 */     new TreeWindow(root, title, fontSize);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public static Object createExpressionTree(String paramString)
/*  99:    */   {
/* 100:195 */     throw new Error("Unresolved compilation problem: \n\tThe method convertToUserTreeNode(TreeNode) from the type TreeUtil refers to the missing type TreeNode\n");
/* 101:    */   }
/* 102:    */   
/* 103:    */   private static TreeNode createExpr(String expr)
/* 104:    */   {
/* 105:201 */     int pos = 0;
/* 106:202 */     int parenDepth = 0;
/* 107:    */     
/* 108:    */ 
/* 109:205 */     expr = expr.trim();
/* 110:    */     
/* 111:207 */     pos = 0;
/* 112:208 */     while (pos < expr.length())
/* 113:    */     {
/* 114:210 */       char ch = expr.charAt(pos);
/* 115:212 */       if (ch == '(') {
/* 116:213 */         parenDepth++;
/* 117:214 */       } else if (ch == ')') {
/* 118:215 */         parenDepth--;
/* 119:216 */       } else if (parenDepth == 0) {
/* 120:218 */         if ((ch == '+') || (ch == '-'))
/* 121:    */         {
/* 122:220 */           TreeNode left = createExpr(expr.substring(0, pos - 1));
/* 123:221 */           TreeNode right = createExpr(expr.substring(pos + 1));
/* 124:222 */           return new TreeNode(ch, left, right);
/* 125:    */         }
/* 126:    */       }
/* 127:225 */       pos++;
/* 128:    */     }
/* 129:228 */     pos = 0;
/* 130:229 */     while (pos < expr.length())
/* 131:    */     {
/* 132:231 */       char ch = expr.charAt(pos);
/* 133:233 */       if (ch == '(') {
/* 134:234 */         parenDepth++;
/* 135:235 */       } else if (ch == ')') {
/* 136:236 */         parenDepth--;
/* 137:237 */       } else if (parenDepth == 0) {
/* 138:239 */         if ((ch == '*') || (ch == '/') || (ch == '%'))
/* 139:    */         {
/* 140:241 */           TreeNode left = createExpr(expr.substring(0, pos - 1));
/* 141:242 */           TreeNode right = createExpr(expr.substring(pos + 1));
/* 142:243 */           return new TreeNode(ch, left, right);
/* 143:    */         }
/* 144:    */       }
/* 145:246 */       pos++;
/* 146:    */     }
/* 147:249 */     pos = 0;
/* 148:250 */     while (pos < expr.length())
/* 149:    */     {
/* 150:252 */       char ch = expr.charAt(pos);
/* 151:254 */       if (ch == '(') {
/* 152:255 */         parenDepth++;
/* 153:256 */       } else if (ch == ')') {
/* 154:257 */         parenDepth--;
/* 155:258 */       } else if (parenDepth == 0) {
/* 156:260 */         if (ch == '^')
/* 157:    */         {
/* 158:262 */           TreeNode left = createExpr(expr.substring(0, pos));
/* 159:263 */           TreeNode right = createExpr(expr.substring(pos + 1));
/* 160:264 */           return new TreeNode(ch, left, right);
/* 161:    */         }
/* 162:    */       }
/* 163:267 */       pos++;
/* 164:    */     }
/* 165:270 */     if ((expr.charAt(0) == '(') && 
/* 166:271 */       (expr.charAt(expr.length() - 1) == ')')) {
/* 167:273 */       return createExpr(expr.substring(1, expr.length() - 1));
/* 168:    */     }
/* 169:276 */     return new TreeNode(expr, null, null);
/* 170:    */   }
/* 171:    */   
/* 172:    */   private static TreeNode insert(TreeNode node, Comparable value)
/* 173:    */   {
/* 174:282 */     if (node == null) {
/* 175:284 */       node = new TreeNode(value, null, null);
/* 176:286 */     } else if (value.compareTo(node.getValue()) < 0) {
/* 177:288 */       node.setLeft(insert(node.getLeft(), value));
/* 178:    */     } else {
/* 179:292 */       node.setRight(insert(node.getRight(), value));
/* 180:    */     }
/* 181:295 */     return node;
/* 182:    */   }
/* 183:    */   
/* 184:    */   private static AVLNodeInfo avlInsert(AVLTreeNode node, Comparable value)
/* 185:    */   {
/* 186:    */     int newState;
/* 187:    */     int newState;
/* 188:303 */     if (node == null)
/* 189:    */     {
/* 190:308 */       node = new AVLTreeNode(value, null, null, 2);
/* 191:309 */       newState = 1;
/* 192:    */     }
/* 193:    */     else
/* 194:    */     {
/* 195:    */       int newState;
/* 196:311 */       if (value.compareTo(node.getValue()) == 0)
/* 197:    */       {
/* 198:315 */         newState = 0;
/* 199:    */       }
/* 200:    */       else
/* 201:    */       {
/* 202:    */         int newState;
/* 203:317 */         if (value.compareTo(node.getValue()) < 0)
/* 204:    */         {
/* 205:322 */           AVLNodeInfo info = avlInsert(node.getLeft(), value);
/* 206:323 */           node.setLeft(info.node);
/* 207:    */           int newState;
/* 208:325 */           if (info.stateChange == 1)
/* 209:    */           {
/* 210:    */             int newState;
/* 211:327 */             if (node.getBalance() == 1)
/* 212:    */             {
/* 213:333 */               info = delLeftBalance(node);
/* 214:334 */               node = info.node;
/* 215:    */               int newState;
/* 216:339 */               if (info.stateChange == -1) {
/* 217:340 */                 newState = 0;
/* 218:    */               } else {
/* 219:342 */                 newState = 1;
/* 220:    */               }
/* 221:    */             }
/* 222:    */             else
/* 223:    */             {
/* 224:    */               int newState;
/* 225:344 */               if (node.getBalance() == 2)
/* 226:    */               {
/* 227:348 */                 node.setBalance(1);
/* 228:349 */                 newState = 1;
/* 229:    */               }
/* 230:    */               else
/* 231:    */               {
/* 232:355 */                 node.setBalance(2);
/* 233:356 */                 newState = 0;
/* 234:    */               }
/* 235:    */             }
/* 236:    */           }
/* 237:    */           else
/* 238:    */           {
/* 239:361 */             newState = 0;
/* 240:    */           }
/* 241:    */         }
/* 242:    */         else
/* 243:    */         {
/* 244:369 */           AVLNodeInfo info = avlInsert(node.getRight(), value);
/* 245:370 */           node.setRight(info.node);
/* 246:    */           int newState;
/* 247:372 */           if (info.stateChange == 1)
/* 248:    */           {
/* 249:    */             int newState;
/* 250:374 */             if (node.getBalance() == 1)
/* 251:    */             {
/* 252:378 */               node.setBalance(2);
/* 253:379 */               newState = 0;
/* 254:    */             }
/* 255:    */             else
/* 256:    */             {
/* 257:    */               int newState;
/* 258:381 */               if (node.getBalance() == 2)
/* 259:    */               {
/* 260:385 */                 node.setBalance(3);
/* 261:386 */                 newState = 1;
/* 262:    */               }
/* 263:    */               else
/* 264:    */               {
/* 265:394 */                 info = delRightBalance(node);
/* 266:395 */                 node = info.node;
/* 267:    */                 int newState;
/* 268:400 */                 if (info.stateChange == -1) {
/* 269:401 */                   newState = 0;
/* 270:    */                 } else {
/* 271:403 */                   newState = 1;
/* 272:    */                 }
/* 273:    */               }
/* 274:    */             }
/* 275:    */           }
/* 276:    */           else
/* 277:    */           {
/* 278:408 */             newState = 0;
/* 279:    */           }
/* 280:    */         }
/* 281:    */       }
/* 282:    */     }
/* 283:412 */     return new AVLNodeInfo(node, newState);
/* 284:    */   }
/* 285:    */   
/* 286:    */   private static AVLNodeInfo delRightBalance(AVLTreeNode node)
/* 287:    */   {
/* 288:419 */     AVLTreeNode right = node.getRight();
/* 289:421 */     if (right.getBalance() == 2)
/* 290:    */     {
/* 291:423 */       node.setBalance(3);
/* 292:424 */       right.setBalance(1);
/* 293:425 */       node = rotateLeft(node);
/* 294:426 */       return new AVLNodeInfo(node, 0);
/* 295:    */     }
/* 296:428 */     if (right.getBalance() == 3)
/* 297:    */     {
/* 298:430 */       node.setBalance(2);
/* 299:431 */       right.setBalance(2);
/* 300:432 */       node = rotateLeft(node);
/* 301:433 */       return new AVLNodeInfo(node, -1);
/* 302:    */     }
/* 303:437 */     AVLTreeNode left = right.getLeft();
/* 304:438 */     if (left.getBalance() == 1)
/* 305:    */     {
/* 306:440 */       node.setBalance(2);
/* 307:441 */       right.setBalance(3);
/* 308:    */     }
/* 309:443 */     else if (left.getBalance() == 2)
/* 310:    */     {
/* 311:445 */       node.setBalance(2);
/* 312:446 */       right.setBalance(2);
/* 313:    */     }
/* 314:    */     else
/* 315:    */     {
/* 316:450 */       node.setBalance(1);
/* 317:451 */       right.setBalance(2);
/* 318:    */     }
/* 319:453 */     left.setBalance(2);
/* 320:454 */     right = rotateRight(right);
/* 321:455 */     node.setRight(right);
/* 322:456 */     node = rotateLeft(node);
/* 323:457 */     return new AVLNodeInfo(node, -1);
/* 324:    */   }
/* 325:    */   
/* 326:    */   private static AVLNodeInfo delLeftBalance(AVLTreeNode node)
/* 327:    */   {
/* 328:465 */     AVLTreeNode left = node.getLeft();
/* 329:467 */     if (left.getBalance() == 2)
/* 330:    */     {
/* 331:469 */       node.setBalance(1);
/* 332:470 */       left.setBalance(3);
/* 333:471 */       node = rotateRight(node);
/* 334:472 */       return new AVLNodeInfo(node, 0);
/* 335:    */     }
/* 336:476 */     if (left.getBalance() == 1)
/* 337:    */     {
/* 338:478 */       node.setBalance(2);
/* 339:479 */       left.setBalance(2);
/* 340:480 */       node = rotateRight(node);
/* 341:481 */       return new AVLNodeInfo(node, -1);
/* 342:    */     }
/* 343:485 */     AVLTreeNode right = left.getRight();
/* 344:486 */     if (right.getBalance() == 3)
/* 345:    */     {
/* 346:488 */       node.setBalance(2);
/* 347:489 */       left.setBalance(1);
/* 348:    */     }
/* 349:491 */     else if (right.getBalance() == 2)
/* 350:    */     {
/* 351:493 */       node.setBalance(2);
/* 352:494 */       left.setBalance(2);
/* 353:    */     }
/* 354:    */     else
/* 355:    */     {
/* 356:498 */       node.setBalance(3);
/* 357:499 */       left.setBalance(2);
/* 358:    */     }
/* 359:501 */     right.setBalance(2);
/* 360:502 */     left = rotateLeft(left);
/* 361:503 */     node.setLeft(left);
/* 362:504 */     node = rotateRight(node);
/* 363:505 */     return new AVLNodeInfo(node, -1);
/* 364:    */   }
/* 365:    */   
/* 366:    */   private static AVLTreeNode rotateRight(AVLTreeNode node)
/* 367:    */   {
/* 368:513 */     AVLTreeNode t = node.getLeft();
/* 369:514 */     node.setLeft(t.getRight());
/* 370:515 */     t.setRight(node);
/* 371:516 */     return t;
/* 372:    */   }
/* 373:    */   
/* 374:    */   private static AVLTreeNode rotateLeft(AVLTreeNode node)
/* 375:    */   {
/* 376:523 */     AVLTreeNode t = node.getRight();
/* 377:524 */     node.setRight(t.getLeft());
/* 378:525 */     t.setLeft(node);
/* 379:526 */     return t;
/* 380:    */   }
/* 381:    */   
/* 382:    */   static Object convertToUserTreeNode(hsa.TreeNode paramTreeNode)
/* 383:    */   {
/* 384:530 */     throw new Error("Unresolved compilation problem: \n\thsa cannot be resolved to a type\n");
/* 385:    */   }
/* 386:    */   
/* 387:    */   static Object convertNodeToUserTreeNode(hsa.TreeNode paramTreeNode, Constructor paramConstructor)
/* 388:    */   {
/* 389:575 */     throw new Error("Unresolved compilation problem: \n\thsa cannot be resolved to a type\n");
/* 390:    */   }
/* 391:    */   
/* 392:    */   private static Object convertAVLToUserTreeNode(AVLTreeNode root)
/* 393:    */   {
/* 394:    */     try
/* 395:    */     {
/* 396:608 */       userTreeNodeClass = Class.forName("TreeNode");
/* 397:    */     }
/* 398:    */     catch (ClassNotFoundException e)
/* 399:    */     {
/* 400:    */       Class userTreeNodeClass;
/* 401:612 */       System.out.println("TreeNode class not found");
/* 402:613 */       return root;
/* 403:    */     }
/* 404:    */     Class userTreeNodeClass;
/* 405:616 */     Class[] conParams = new Class[3];
/* 406:    */     try
/* 407:    */     {
/* 408:619 */       conParams[0] = Class.forName("java.lang.Object");
/* 409:    */     }
/* 410:    */     catch (ClassNotFoundException e)
/* 411:    */     {
/* 412:623 */       System.out.println("Object class not found");
/* 413:624 */       return root;
/* 414:    */     }
/* 415:627 */     conParams[1] = userTreeNodeClass;
/* 416:628 */     conParams[2] = userTreeNodeClass;
/* 417:    */     try
/* 418:    */     {
/* 419:632 */       con = userTreeNodeClass.getConstructor(conParams);
/* 420:    */     }
/* 421:    */     catch (NoSuchMethodException e)
/* 422:    */     {
/* 423:    */       Constructor con;
/* 424:638 */       System.out.println("No constructor found");
/* 425:639 */       return root;
/* 426:    */     }
/* 427:    */     Constructor con;
/* 428:643 */     return convertAVLNodeToUserTreeNode(root, con);
/* 429:    */   }
/* 430:    */   
/* 431:    */   private static Object convertAVLNodeToUserTreeNode(AVLTreeNode node, Constructor con)
/* 432:    */   {
/* 433:650 */     Object[] args = new Object[3];
/* 434:652 */     if (node == null) {
/* 435:654 */       return null;
/* 436:    */     }
/* 437:657 */     args[0] = node.getValue();
/* 438:658 */     args[1] = convertAVLNodeToUserTreeNode(node.getLeft(), con);
/* 439:659 */     args[2] = convertAVLNodeToUserTreeNode(node.getRight(), con);
/* 440:    */     try
/* 441:    */     {
/* 442:663 */       return con.newInstance(args);
/* 443:    */     }
/* 444:    */     catch (Exception e)
/* 445:    */     {
/* 446:667 */       System.out.println("Unable to to call TreeNode constructor");
/* 447:    */     }
/* 448:668 */     return null;
/* 449:    */   }
/* 450:    */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.TreeUtil
 * JD-Core Version:    0.7.0.1
 */