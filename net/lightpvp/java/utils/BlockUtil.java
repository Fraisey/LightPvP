/*    */ package net.lightpvp.java.utils;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockUtil
/*    */ {
/*    */   public static void setBlockType(Block block, Material material) {
/* 15 */     block.setType(material);
/* 16 */     block.getChunk().unload();
/* 17 */     block.getChunk().load();
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\jav\\utils\BlockUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */