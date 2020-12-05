/*    */ package net.lightpvp.java.managers.feast;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Random;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.Chest;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ public class FeastManager
/*    */ {
/* 16 */   Random random = new Random();
/*    */   
/* 18 */   Map<Double, FeastItem> items = new HashMap<>();
/*    */   
/*    */   public void registerItems(double chance, Material material, int minAmount, int maxAmount) {
/* 21 */     this.items.put(Double.valueOf(chance), new FeastItem(material, minAmount, maxAmount));
/*    */   }
/*    */   
/*    */   public ItemStack getFeastItem() {
/* 25 */     FeastItem feastItem = null;
/*    */     
/* 27 */     double chance = this.random.nextDouble();
/*    */     
/*    */     do {
/*    */     
/* 31 */     } while (feastItem == null);
/*    */     
/* 33 */     return new ItemStack(feastItem.getMaterial(), feastItem.getRandomAmount(this.random));
/*    */   }
/*    */   
/*    */   public void fillChest(Location loc) {
/* 37 */     Block block = loc.getBlock();
/*    */     
/* 39 */     if (block.getState() instanceof Chest) {
/* 40 */       Chest chest = (Chest)block.getState();
/* 41 */       Inventory chestInventory = chest.getBlockInventory();
/*    */ 
/*    */       
/* 44 */       for (int slot = 0; slot < chestInventory.getSize(); slot++) {
/* 45 */         double skipChance = this.random.nextDouble();
/*    */         
/* 47 */         if (skipChance < 0.35D)
/*    */         {
/*    */           
/* 50 */           chestInventory.setItem(slot, getFeastItem());
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\feast\FeastManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */