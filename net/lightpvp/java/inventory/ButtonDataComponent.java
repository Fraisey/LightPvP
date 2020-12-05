/*    */ package net.lightpvp.java.inventory;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ 
/*    */ public class ButtonDataComponent
/*    */   extends DataComponent
/*    */ {
/*    */   public ButtonDataComponent(String name, List<String> description, String id, Material material) {
/* 12 */     super("button", name, id, material);
/*    */     
/* 14 */     this.description = description;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getInitial() {
/* 19 */     ItemStack item = new ItemStack(getMaterial());
/* 20 */     ItemMeta meta = item.getItemMeta();
/* 21 */     meta.setDisplayName(getName());
/* 22 */     meta.setLore(this.description);
/* 23 */     item.setItemMeta(meta);
/* 24 */     return item;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isOption() {
/* 29 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\inventory\ButtonDataComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */