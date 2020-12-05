/*    */ package net.lightpvp.java.managers.event.arenas.brackets;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.enchantments.Enchantment;
/*    */ import org.bukkit.inventory.ItemStack;
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
/*    */ public class BracketsEventArenaBridges
/*    */   extends BracketsEventArena
/*    */ {
/*    */   public BracketsEventArenaBridges(Core core) {
/* 25 */     ItemStack bow = new ItemStack(Material.BOW);
/* 26 */     bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
/* 27 */     bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 2);
/* 28 */     bow.addEnchantment(Enchantment.ARROW_DAMAGE, 4);
/* 29 */     this.content = new ItemStack[] { bow, new ItemStack(Material.ARROW, 1) };
/* 30 */     this.armor = new ItemStack[] { new ItemStack(Material.IRON_BOOTS), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.IRON_HELMET) };
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 35 */     return "Bridges";
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\event\arenas\brackets\BracketsEventArenaBridges.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */