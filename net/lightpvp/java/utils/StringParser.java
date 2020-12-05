/*    */ package net.lightpvp.java.utils;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Font;
/*    */ import java.awt.FontMetrics;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.font.FontRenderContext;
/*    */ import java.awt.geom.Rectangle2D;
/*    */ import java.awt.image.BufferedImage;
/*    */ 
/*    */ public class StringParser
/*    */ {
/*    */   public static BufferedImage stringToBufferedImage(Font font, String s) {
/* 14 */     BufferedImage img = new BufferedImage(1, 1, 6);
/* 15 */     Graphics g = img.getGraphics();
/* 16 */     g.setFont(font);
/*    */     
/* 18 */     FontRenderContext frc = g.getFontMetrics().getFontRenderContext();
/* 19 */     Rectangle2D rect = font.getStringBounds(s, frc);
/* 20 */     g.dispose();
/*    */     
/* 22 */     img = new BufferedImage((int)Math.ceil(rect.getWidth()), (int)Math.ceil(rect.getHeight()), 6);
/* 23 */     g = img.getGraphics();
/* 24 */     g.setColor(Color.black);
/* 25 */     g.setFont(font);
/*    */     
/* 27 */     FontMetrics fm = g.getFontMetrics();
/* 28 */     int x = 0;
/* 29 */     int y = fm.getAscent();
/*    */     
/* 31 */     g.drawString(s, x, y);
/* 32 */     g.dispose();
/*    */     
/* 34 */     return img;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\jav\\utils\StringParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */