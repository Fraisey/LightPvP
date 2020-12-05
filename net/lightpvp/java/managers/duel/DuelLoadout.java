/*    */ package net.lightpvp.java.managers.duel;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ public class DuelLoadout
/*    */   implements Cloneable
/*    */ {
/*    */   private String name;
/*    */   private ItemStack[] content;
/*    */   private ItemStack[] armor;
/*    */   
/*    */   public DuelLoadout(String name, ItemStack[] content, ItemStack[] armor) {
/* 16 */     this.name = name;
/*    */     
/* 18 */     this.content = content;
/* 19 */     this.armor = armor;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 23 */     return this.name;
/*    */   }
/*    */   
/*    */   public ItemStack[] getContent() {
/* 27 */     return this.content;
/*    */   }
/*    */   
/*    */   public void addRefills() {
/* 31 */     this.content = Arrays.<ItemStack>copyOf(this.content, 36);
/* 32 */     for (int i = 9; i < 36; i++) {
/* 33 */       this.content[i] = new ItemStack(Material.MUSHROOM_SOUP);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack[] getArmor() {
/* 40 */     return this.armor;
/*    */   }
/*    */ 
/*    */   
/*    */   protected DuelLoadout clone() {
/*    */     try {
/* 46 */       return (DuelLoadout)super.clone();
/* 47 */     } catch (CloneNotSupportedException e) {
/* 48 */       e.printStackTrace();
/*    */       
/* 50 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\duel\DuelLoadout.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */