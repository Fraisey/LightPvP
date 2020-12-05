/*    */ package net.lightpvp.java.utils;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class InventoryUtil
/*    */ {
/*    */   public static boolean isSword(ItemStack is) {
/*  9 */     Material m = is.getType();
/* 10 */     return !(!m.equals(Material.WOOD_SWORD) && !m.equals(Material.STONE_SWORD) && !m.equals(Material.GOLD_SWORD) && !m.equals(Material.IRON_SWORD) && !m.equals(Material.DIAMOND_SWORD));
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\jav\\utils\InventoryUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */