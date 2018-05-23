/*  1:   */ package game1.hsa;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ 
/*  5:   */ public abstract class VisualSortUtil
/*  6:   */ {
/*  7:20 */   private static ArrayList windows = new ArrayList();
/*  8:21 */   private static ArrayList dataSets = new ArrayList();
/*  9:   */   
/* 10:   */   public static Comparable[] createVisualArray(String sortName, int width, int height)
/* 11:   */   {
/* 12:26 */     SortFrame window = new SortFrame(sortName, width, height);
/* 13:27 */     Comparable[] data = window.getData();
/* 14:28 */     windows.add(window);
/* 15:29 */     dataSets.add(data);
/* 16:   */     
/* 17:31 */     return data;
/* 18:   */   }
/* 19:   */   
/* 20:   */   private static SortFrame getWindow(Comparable[] list)
/* 21:   */   {
/* 22:37 */     int index = dataSets.indexOf(list);
/* 23:38 */     return (SortFrame)windows.get(index);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public static void swap(int i, int j, Comparable[] list)
/* 27:   */   {
/* 28:46 */     getWindow(list).swap(i, j);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public static void shift(int i, int j, Comparable[] list)
/* 32:   */   {
/* 33:54 */     getWindow(list).shift(i, j);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public static void showMerge(int i, int j, Comparable[] list)
/* 37:   */   {
/* 38:62 */     getWindow(list).showMerge(i, j);
/* 39:   */   }
/* 40:   */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.VisualSortUtil
 * JD-Core Version:    0.7.0.1
 */