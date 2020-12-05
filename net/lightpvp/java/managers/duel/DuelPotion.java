/*    */ package net.lightpvp.java.managers.duel;
/*    */ 
/*    */ import org.bukkit.potion.PotionEffect;
/*    */ 
/*    */ public class DuelPotion
/*    */   implements Cloneable
/*    */ {
/*    */   private String name;
/*    */   private PotionEffect potionEffect;
/*    */   
/*    */   public DuelPotion(String name, PotionEffect potionEffect) {
/* 12 */     this.name = name;
/*    */     
/* 14 */     this.potionEffect = potionEffect;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 18 */     return this.name;
/*    */   }
/*    */   
/*    */   public PotionEffect getPotionEffect() {
/* 22 */     return this.potionEffect;
/*    */   }
/*    */ 
/*    */   
/*    */   protected DuelPotion clone() {
/*    */     try {
/* 28 */       return (DuelPotion)super.clone();
/* 29 */     } catch (CloneNotSupportedException e) {
/* 30 */       e.printStackTrace();
/*    */       
/* 32 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\duel\DuelPotion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */