/*    */ package net.lightpvp.java.managers.feast;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class FeastItem
/*    */ {
/*    */   private Material material;
/*    */   private int minAmount;
/*    */   private int maxAmount;
/*    */   
/*    */   public FeastItem(Material material, int minAmount, int maxAmount) {
/* 63 */     this.material = material;
/* 64 */     this.minAmount = minAmount;
/* 65 */     this.maxAmount = maxAmount;
/*    */   }
/*    */   
/*    */   public Material getMaterial() {
/* 69 */     return this.material;
/*    */   }
/*    */   
/*    */   public int getRandomAmount(Random random) {
/* 73 */     return random.nextInt(this.maxAmount - this.minAmount + 1) + this.minAmount;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\feast\FeastItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */