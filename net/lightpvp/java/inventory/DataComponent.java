/*    */ package net.lightpvp.java.inventory;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class DataComponent
/*    */ {
/*    */   private String type;
/*    */   private String id;
/*    */   private String name;
/*    */   protected List<String> description;
/*    */   private Material material;
/*    */   
/*    */   public DataComponent(String type, String name, String id, Material material) {
/* 19 */     this.type = type;
/* 20 */     this.name = name;
/* 21 */     this.id = id;
/*    */     
/* 23 */     this.material = material;
/*    */   }
/*    */   
/*    */   public abstract boolean isOption();
/*    */   
/*    */   public Integer getValue(Player player) {
/* 29 */     return Integer.valueOf(0);
/*    */   }
/*    */   
/*    */   public String getType() {
/* 33 */     return this.type;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 37 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getId() {
/* 41 */     return this.id;
/*    */   }
/*    */   
/*    */   public Material getMaterial() {
/* 45 */     return this.material;
/*    */   }
/*    */   
/*    */   public abstract ItemStack getInitial();
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\inventory\DataComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */