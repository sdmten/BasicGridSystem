/*  1:   */ package game1.lib;
/*  2:   */ 
/*  3:   */ import java.awt.AlphaComposite;
/*  4:   */ import java.awt.Color;
/*  5:   */ import java.awt.Graphics2D;
/*  6:   */ import java.awt.image.BufferedImage;
/*  7:   */ import java.io.File;
/*  8:   */ import java.io.IOException;
/*  9:   */ import java.io.InputStream;
/* 10:   */ import java.net.URL;
/* 11:   */ import javax.imageio.ImageIO;
/* 12:   */ 
/* 13:   */ public class SpriteImport
/* 14:   */ {
/* 15:   */   public static BufferedImage makeColorTransparent(String name, Color color)
/* 16:   */     throws IOException
/* 17:   */   {
/* 18:18 */     BufferedImage image = ImageIO.read(new File(name));
/* 19:19 */     BufferedImage dimg = new BufferedImage(image.getWidth(), image.getHeight(), 2);
/* 20:20 */     Graphics2D g = dimg.createGraphics();
/* 21:21 */     g.setComposite(AlphaComposite.Src);
/* 22:22 */     g.drawImage(image, null, 0, 0);
/* 23:23 */     g.dispose();
/* 24:24 */     for (int i = 0; i < dimg.getHeight(); i++) {
/* 25:26 */       for (int j = 0; j < dimg.getWidth(); j++) {
/* 26:28 */         if (dimg.getRGB(j, i) == color.getRGB()) {
/* 27:30 */           dimg.setRGB(j, i, 9378844);
/* 28:   */         }
/* 29:   */       }
/* 30:   */     }
/* 31:34 */     return dimg;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public static BufferedImage makeColorTransparent(InputStream inputstream, Color color)
/* 35:   */     throws IOException
/* 36:   */   {
/* 37:38 */     BufferedImage image = ImageIO.read(inputstream);
/* 38:39 */     BufferedImage dimg = new BufferedImage(image.getWidth(), image.getHeight(), 2);
/* 39:40 */     Graphics2D g = dimg.createGraphics();
/* 40:41 */     g.setComposite(AlphaComposite.Src);
/* 41:42 */     g.drawImage(image, null, 0, 0);
/* 42:43 */     g.dispose();
/* 43:44 */     for (int i = 0; i < dimg.getHeight(); i++) {
/* 44:46 */       for (int j = 0; j < dimg.getWidth(); j++) {
/* 45:48 */         if (dimg.getRGB(j, i) == color.getRGB()) {
/* 46:50 */           dimg.setRGB(j, i, 9378844);
/* 47:   */         }
/* 48:   */       }
/* 49:   */     }
/* 50:54 */     inputstream.close();
/* 51:55 */     return dimg;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public static BufferedImage makeColorTransparent(URL name, Color color)
/* 55:   */     throws IOException
/* 56:   */   {
/* 57:59 */     BufferedImage image = ImageIO.read(new File(name.toString()));
/* 58:60 */     BufferedImage dimg = new BufferedImage(image.getWidth(), image.getHeight(), 2);
/* 59:61 */     Graphics2D g = dimg.createGraphics();
/* 60:62 */     g.setComposite(AlphaComposite.Src);
/* 61:63 */     g.drawImage(image, null, 0, 0);
/* 62:64 */     g.dispose();
/* 63:65 */     for (int i = 0; i < dimg.getHeight(); i++) {
/* 64:67 */       for (int j = 0; j < dimg.getWidth(); j++) {
/* 65:69 */         if (dimg.getRGB(j, i) == color.getRGB()) {
/* 66:71 */           dimg.setRGB(j, i, 9378844);
/* 67:   */         }
/* 68:   */       }
/* 69:   */     }
/* 70:75 */     return dimg;
/* 71:   */   }
/* 72:   */ }


/* Location:           C:\Users\Sergei Ten\workspace\game1\src\
 * Qualified Name:     game1.lib.SpriteImport
 * JD-Core Version:    0.7.0.1
 */