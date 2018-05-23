/*  1:   */ package game1.hsa;
/*  2:   */ 
/*  3:   */ public class PhoneBook
/*  4:   */ {
/*  5:   */   int numEntries;
/*  6:   */   String[] names;
/*  7:   */   String[] phoneNumbers;
/*  8:   */   
/*  9:   */   public PhoneBook()
/* 10:   */   {
/* 11:11 */     this.numEntries = 0;
/* 12:12 */     this.names = new String[100];
/* 13:13 */     this.phoneNumbers = new String[100];
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void add(String name, String phoneNumber)
/* 17:   */   {
/* 18:19 */     this.names[this.numEntries] = name;
/* 19:20 */     this.phoneNumbers[this.numEntries] = phoneNumber;
/* 20:21 */     this.numEntries += 1;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String lookUp(String name)
/* 24:   */   {
/* 25:27 */     for (int cnt = 0; cnt < this.numEntries; cnt++) {
/* 26:29 */       if (name.equals(this.names[cnt])) {
/* 27:31 */         return this.phoneNumbers[cnt];
/* 28:   */       }
/* 29:   */     }
/* 30:35 */     return "";
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void remove(String name)
/* 34:   */   {
/* 35:41 */     for (int cnt = 0; cnt < this.numEntries; cnt++) {
/* 36:43 */       if (name.equals(this.names[cnt]))
/* 37:   */       {
/* 38:46 */         for (int cnt1 = cnt + 1; cnt++ < this.numEntries; cnt1++)
/* 39:   */         {
/* 40:48 */           this.names[(cnt1 - 1)] = this.names[cnt1];
/* 41:49 */           this.phoneNumbers[(cnt1 - 1)] = this.phoneNumbers[cnt1];
/* 42:   */         }
/* 43:51 */         return;
/* 44:   */       }
/* 45:   */     }
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.PhoneBook
 * JD-Core Version:    0.7.0.1
 */