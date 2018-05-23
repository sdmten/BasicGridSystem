/*   1:    */ package game1.hsa;
/*   2:    */ 
/*   3:    */ public class Stdout
/*   4:    */ {
/*   5: 20 */   private static TextOutputFile out = new TextOutputFile();
/*   6:    */   
/*   7:    */   public static void close() {}
/*   8:    */   
/*   9:    */   public static void print(byte number)
/*  10:    */   {
/*  11: 39 */     out.print(number);
/*  12:    */   }
/*  13:    */   
/*  14:    */   public static void print(byte number, int fieldSize)
/*  15:    */   {
/*  16: 52 */     out.print(number, fieldSize);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public static void print(char ch)
/*  20:    */   {
/*  21: 63 */     out.print(ch);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public static void print(char ch, int fieldSize)
/*  25:    */   {
/*  26: 75 */     out.print(ch, fieldSize);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static void print(double number)
/*  30:    */   {
/*  31: 87 */     out.print(number);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static void print(double number, int fieldSize)
/*  35:    */   {
/*  36:100 */     out.print(number, fieldSize);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static void print(double number, int fieldSize, int decimalPlaces)
/*  40:    */   {
/*  41:116 */     out.print(number, fieldSize, decimalPlaces);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static void print(float number)
/*  45:    */   {
/*  46:127 */     out.print(number);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static void print(float number, int fieldSize)
/*  50:    */   {
/*  51:140 */     out.print(number, fieldSize);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static void print(float number, int fieldSize, int decimalPlaces)
/*  55:    */   {
/*  56:155 */     out.print(number, fieldSize, decimalPlaces);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static void print(int number)
/*  60:    */   {
/*  61:167 */     out.print(number);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static void print(int number, int fieldSize)
/*  65:    */   {
/*  66:180 */     out.print(number, fieldSize);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public static void print(long number)
/*  70:    */   {
/*  71:192 */     out.print(number);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static void print(long number, int fieldSize)
/*  75:    */   {
/*  76:205 */     out.print(number, fieldSize);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static void print(String text)
/*  80:    */   {
/*  81:216 */     out.print(text);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static void print(String text, int fieldSize)
/*  85:    */   {
/*  86:228 */     out.print(text, fieldSize);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static void print(short number)
/*  90:    */   {
/*  91:240 */     out.print(number);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public static void print(short number, int fieldSize)
/*  95:    */   {
/*  96:253 */     out.print(number, fieldSize);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static void print(boolean value)
/* 100:    */   {
/* 101:264 */     out.print(value);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static void print(boolean value, int fieldSize)
/* 105:    */   {
/* 106:277 */     out.print(value, fieldSize);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public static void println()
/* 110:    */   {
/* 111:286 */     out.println();
/* 112:    */   }
/* 113:    */   
/* 114:    */   public static void println(byte number)
/* 115:    */   {
/* 116:298 */     out.println(number);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public static void println(byte number, int fieldSize)
/* 120:    */   {
/* 121:311 */     out.println(number, fieldSize);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public static void println(char ch)
/* 125:    */   {
/* 126:322 */     out.println(ch);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public static void println(char ch, int fieldSize)
/* 130:    */   {
/* 131:334 */     out.println(ch, fieldSize);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public static void println(double number)
/* 135:    */   {
/* 136:346 */     out.println(number);
/* 137:    */   }
/* 138:    */   
/* 139:    */   public static void println(double number, int fieldSize)
/* 140:    */   {
/* 141:359 */     out.println(number, fieldSize);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public static void println(double number, int fieldSize, int decimalPlaces)
/* 145:    */   {
/* 146:375 */     out.println(number, fieldSize, decimalPlaces);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public static void println(float number)
/* 150:    */   {
/* 151:387 */     out.println(number);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public static void println(float number, int fieldSize)
/* 155:    */   {
/* 156:400 */     out.println(number, fieldSize);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public static void println(float number, int fieldSize, int decimalPlaces)
/* 160:    */   {
/* 161:416 */     out.println(number, fieldSize, decimalPlaces);
/* 162:    */   }
/* 163:    */   
/* 164:    */   public static void println(int number)
/* 165:    */   {
/* 166:428 */     out.println(number);
/* 167:    */   }
/* 168:    */   
/* 169:    */   public static void println(int number, int fieldSize)
/* 170:    */   {
/* 171:442 */     out.println(number, fieldSize);
/* 172:    */   }
/* 173:    */   
/* 174:    */   public static void println(long number)
/* 175:    */   {
/* 176:454 */     out.println(number);
/* 177:    */   }
/* 178:    */   
/* 179:    */   public static void println(long number, int fieldSize)
/* 180:    */   {
/* 181:468 */     out.println(number, fieldSize);
/* 182:    */   }
/* 183:    */   
/* 184:    */   public static void println(String text)
/* 185:    */   {
/* 186:479 */     out.println(text);
/* 187:    */   }
/* 188:    */   
/* 189:    */   public static void println(String text, int fieldSize)
/* 190:    */   {
/* 191:492 */     out.println(text, fieldSize);
/* 192:    */   }
/* 193:    */   
/* 194:    */   public static void println(short number)
/* 195:    */   {
/* 196:504 */     out.println(number);
/* 197:    */   }
/* 198:    */   
/* 199:    */   public static void println(short number, int fieldSize)
/* 200:    */   {
/* 201:518 */     out.println(number, fieldSize);
/* 202:    */   }
/* 203:    */   
/* 204:    */   public static void println(boolean value)
/* 205:    */   {
/* 206:530 */     out.println(value);
/* 207:    */   }
/* 208:    */   
/* 209:    */   public static void println(boolean value, int fieldSize)
/* 210:    */   {
/* 211:543 */     out.println(value, fieldSize);
/* 212:    */   }
/* 213:    */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.Stdout
 * JD-Core Version:    0.7.0.1
 */