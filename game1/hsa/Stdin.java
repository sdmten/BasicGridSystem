/*   1:    */ package game1.hsa;
/*   2:    */ 
/*   3:    */ public class Stdin
/*   4:    */ {
/*   5: 21 */   protected static TextInputFile in = null;
/*   6:    */   
/*   7:    */   public static void close()
/*   8:    */   {
/*   9: 28 */     if (in != null)
/*  10:    */     {
/*  11: 30 */       in.close();
/*  12: 31 */       in = null;
/*  13:    */     }
/*  14:    */   }
/*  15:    */   
/*  16:    */   public static boolean eof()
/*  17:    */   {
/*  18: 43 */     if (in == null) {
/*  19: 45 */       in = new TextInputFile();
/*  20:    */     }
/*  21: 48 */     return in.eof();
/*  22:    */   }
/*  23:    */   
/*  24:    */   public static boolean readBoolean()
/*  25:    */   {
/*  26: 61 */     if (in == null) {
/*  27: 63 */       in = new TextInputFile();
/*  28:    */     }
/*  29: 66 */     return in.readBoolean();
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static byte readByte()
/*  33:    */   {
/*  34: 78 */     if (in == null) {
/*  35: 80 */       in = new TextInputFile();
/*  36:    */     }
/*  37: 83 */     return in.readByte();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static char readChar()
/*  41:    */   {
/*  42: 94 */     if (in == null) {
/*  43: 96 */       in = new TextInputFile();
/*  44:    */     }
/*  45: 99 */     return in.readChar();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static double readDouble()
/*  49:    */   {
/*  50:111 */     if (in == null) {
/*  51:113 */       in = new TextInputFile();
/*  52:    */     }
/*  53:116 */     return in.readDouble();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static float readFloat()
/*  57:    */   {
/*  58:127 */     if (in == null) {
/*  59:129 */       in = new TextInputFile();
/*  60:    */     }
/*  61:132 */     return in.readFloat();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static int readInt()
/*  65:    */   {
/*  66:145 */     if (in == null) {
/*  67:147 */       in = new TextInputFile();
/*  68:    */     }
/*  69:150 */     return in.readInt();
/*  70:    */   }
/*  71:    */   
/*  72:    */   public static String readLine()
/*  73:    */   {
/*  74:161 */     if (in == null) {
/*  75:163 */       in = new TextInputFile();
/*  76:    */     }
/*  77:166 */     return in.readLine();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static long readLong()
/*  81:    */   {
/*  82:177 */     if (in == null) {
/*  83:179 */       in = new TextInputFile();
/*  84:    */     }
/*  85:182 */     return in.readLong();
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static short readShort()
/*  89:    */   {
/*  90:194 */     if (in == null) {
/*  91:196 */       in = new TextInputFile();
/*  92:    */     }
/*  93:199 */     return in.readShort();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static String readString()
/*  97:    */   {
/*  98:210 */     if (in == null) {
/*  99:212 */       in = new TextInputFile();
/* 100:    */     }
/* 101:215 */     return in.readString();
/* 102:    */   }
/* 103:    */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.Stdin
 * JD-Core Version:    0.7.0.1
 */