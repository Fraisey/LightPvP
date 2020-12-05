/*    */ package net.lightpvp.java.inventory;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ import org.bukkit.metadata.MetadataValue;
/*    */ 
/*    */ public class ScrollerDataComponent
/*    */   extends DataComponent {
/*    */   private List<String> values;
/*    */   
/*    */   public ScrollerDataComponent(String name, List<String> description, String id, Material material, List<String> values) {
/* 17 */     super("scroller", name, id, material);
/*    */     
/* 19 */     this.description = description;
/* 20 */     this.values = values;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getInitial() {
/* 25 */     return reconstruct(0);
/*    */   }
/*    */   
/*    */   public ItemStack reconstruct(int index) {
/* 29 */     ItemStack item = new ItemStack(getMaterial());
/* 30 */     ItemMeta meta = item.getItemMeta();
/* 31 */     meta.setDisplayName(getName());
/* 32 */     List<String> lore = new ArrayList<>();
/* 33 */     lore.addAll(this.description);
/* 34 */     for (int i = 0; i < this.values.size(); i++) {
/* 35 */       lore.add(((i == index) ? (String)ChatColor.GREEN : (String)ChatColor.RED) + (String)this.values.get(i));
/*    */     }
/* 37 */     meta.setLore(lore);
/* 38 */     item.setItemMeta(meta);
/* 39 */     return item;
/*    */   }
/*    */   
/*    */   public List<String> getValues() {
/* 43 */     return this.values;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isOption() {
/* 48 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public Integer getValue(Player player) {
/* 53 */     return Integer.valueOf(((MetadataValue)player.getMetadata(getId()).get(0)).asInt());
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\inventory\ScrollerDataComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */