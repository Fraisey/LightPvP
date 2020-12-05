/*    */ package net.lightpvp.java.inventory;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.DyeColor;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ import org.bukkit.material.Wool;
/*    */ import org.bukkit.metadata.MetadataValue;
/*    */ 
/*    */ public class BooleanDataComponent
/*    */   extends DataComponent {
/*    */   public BooleanDataComponent(String name, List<String> description, String id, Material material) {
/* 17 */     super("boolean", name, id, material);
/* 18 */     this.description = description;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getInitial() {
/* 23 */     return reconstruct(0);
/*    */   }
/*    */   
/*    */   public ItemStack reconstruct(int value) {
/* 27 */     ItemStack item = (new Wool((value == 1) ? DyeColor.LIME : DyeColor.RED)).toItemStack();
/* 28 */     ItemMeta meta = item.getItemMeta();
/* 29 */     meta.setDisplayName(getName());
/* 30 */     List<String> lore = new ArrayList<>();
/* 31 */     lore.addAll(this.description);
/* 32 */     lore.add("");
/* 33 */     lore.add(ChatColor.GOLD + ChatColor.BOLD + "Middle Click:");
/* 34 */     lore.add((value == 1) ? (ChatColor.GREEN + "On") : (ChatColor.RED + "Off"));
/* 35 */     meta.setLore(lore);
/* 36 */     item.setItemMeta(meta);
/* 37 */     return item;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isOption() {
/* 42 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public Integer getValue(Player player) {
/* 47 */     return Integer.valueOf(((MetadataValue)player.getMetadata(getId()).get(0)).asInt());
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\inventory\BooleanDataComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */