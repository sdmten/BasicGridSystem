/*   1:    */ package game1.hsa;
/*   2:    */ 
/*   3:    */ class AVLTreeNode
/*   4:    */ {
/*   5:    */   public static final int MORE_LEFT = 1;
/*   6:    */   public static final int EVEN = 2;
/*   7:    */   public static final int MORE_RIGHT = 3;
/*   8:    */   private Object value;
/*   9:    */   private AVLTreeNode left;
/*  10:    */   private AVLTreeNode right;
/*  11:    */   private int balance;
/*  12:    */   
/*  13:    */   public AVLTreeNode(Object value, AVLTreeNode left, AVLTreeNode right, int balance)
/*  14:    */   {
/*  15:689 */     this.value = value;
/*  16:690 */     this.left = left;
/*  17:691 */     this.right = right;
/*  18:692 */     this.balance = balance;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public Object getValue()
/*  22:    */   {
/*  23:698 */     return this.value;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public AVLTreeNode getLeft()
/*  27:    */   {
/*  28:704 */     return this.left;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public AVLTreeNode getRight()
/*  32:    */   {
/*  33:710 */     return this.right;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public int getBalance()
/*  37:    */   {
/*  38:716 */     return this.balance;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setValue(Object value)
/*  42:    */   {
/*  43:722 */     this.value = value;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setLeft(AVLTreeNode node)
/*  47:    */   {
/*  48:728 */     this.left = node;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setRight(AVLTreeNode node)
/*  52:    */   {
/*  53:734 */     this.right = node;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setBalance(int balance)
/*  57:    */   {
/*  58:740 */     this.balance = balance;
/*  59:    */   }
/*  60:    */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.AVLTreeNode
 * JD-Core Version:    0.7.0.1
 */