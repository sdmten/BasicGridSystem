/*   1:    */ package game1.hsa;
/*   2:    */ 
/*   3:    */ import java.awt.Dimension;
/*   4:    */ import java.awt.Frame;
/*   5:    */ import java.awt.ScrollPane;
/*   6:    */ import java.awt.Toolkit;
/*   7:    */ import java.awt.event.WindowAdapter;
/*   8:    */ import java.awt.event.WindowEvent;
/*   9:    */ 
/*  10:    */ class TreeWindow
/*  11:    */   extends Frame
/*  12:    */ {
/*  13:760 */   private static int numWindows = 0;
/*  14:    */   
/*  15:    */   public TreeWindow(Object root, String title, int fontSize)
/*  16:    */   {
/*  17:764 */     super(title);
/*  18:    */     
/*  19:    */ 
/*  20:    */ 
/*  21:    */ 
/*  22:    */ 
/*  23:770 */     addWindowListener(new WindowAdapter()
/*  24:    */     {
/*  25:    */       public void windowClosing(WindowEvent e)
/*  26:    */       {
/*  27:774 */         TreeWindow.this.hide();
/*  28:775 */         TreeWindow.this.dispose();
/*  29:    */       }
/*  30:780 */     });
/*  31:781 */     Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
/*  32:    */     
/*  33:    */ 
/*  34:784 */     ScrollPane scrollPane = new ScrollPane();
/*  35:785 */     add(scrollPane);
/*  36:    */     
/*  37:787 */     TreeCanvas treeCanvas = new TreeCanvas(root, fontSize);
/*  38:788 */     scrollPane.add(treeCanvas);
/*  39:    */     
/*  40:    */ 
/*  41:791 */     scrollPane.setSize(treeCanvas.getPreferredSize().width + 4, 
/*  42:792 */       treeCanvas.getPreferredSize().height + 4);
/*  43:    */     
/*  44:    */ 
/*  45:795 */     pack();
/*  46:796 */     if (getWidth() > screen.width * 5 / 6) {
/*  47:798 */       setSize(screen.width * 5 / 6, 
/*  48:799 */         getHeight() + scrollPane.getVScrollbarWidth());
/*  49:    */     }
/*  50:801 */     if (getHeight() > screen.height * 5 / 6) {
/*  51:803 */       setSize(getWidth() + scrollPane.getHScrollbarHeight(), 
/*  52:804 */         screen.height * 5 / 6);
/*  53:    */     }
/*  54:808 */     setLocation(screen.width - getWidth() - numWindows * 30, 
/*  55:809 */       numWindows * 30);
/*  56:810 */     numWindows += 1;
/*  57:    */     
/*  58:    */ 
/*  59:813 */     show();
/*  60:    */   }
/*  61:    */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.hsa.TreeWindow
 * JD-Core Version:    0.7.0.1
 */