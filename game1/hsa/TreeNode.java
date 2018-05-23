/*  1:   */ package game1.hsa;
/*  2:   */ 
/*  3:   */ public class TreeNode
/*  4:   */ {
/*  5:   */   private Object value;
/*  6:   */   private TreeNode left;
/*  7:   */   private TreeNode right;
/*  8:   */   
/*  9:   */   public TreeNode(Object initValue, TreeNode initLeft, TreeNode initRight)
/* 10:   */   {
/* 11:13 */     this.value = initValue;
/* 12:14 */     this.left = initLeft;
/* 13:15 */     this.right = initRight;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public Object getValue()
/* 17:   */   {
/* 18:21 */     return this.value;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public TreeNode getLeft()
/* 22:   */   {
/* 23:27 */     return this.left;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public TreeNode getRight()
/* 27:   */   {
/* 28:33 */     return this.right;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void setValue(Object theNewValue)
/* 32:   */   {
/* 33:39 */     this.value = theNewValue;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void setLeft(TreeNode theNewLeft)
/* 37:   */   {
/* 38:45 */     this.left = theNewLeft;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void setRight(TreeNode theNewRight)
/* 42:   */   {
/* 43:51 */     this.right = theNewRight;
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.TreeNode
 * JD-Core Version:    0.7.0.1
 */