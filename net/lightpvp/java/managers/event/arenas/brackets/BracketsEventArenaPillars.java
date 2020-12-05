/*    */ package net.lightpvp.java.managers.event.arenas.brackets;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class BracketsEventArenaPillars
/*    */   extends BracketsEventArena
/*    */ {
/*    */   public BracketsEventArenaPillars(Core core) {
/* 13 */     this.spawn = new Location(Bukkit.getWorld("world"), -401.5D, 63.0D, 1447.5D, 90.0F, 0.0F);
/* 14 */     this.hologramLocation = new Location(Bukkit.getWorld("world"), -404.5D, 65.0D, 1447.5D, 0.0F, 0.0F);
/*    */     
/* 16 */     this.queueRoom = new Location(Bukkit.getWorld("world"), -401.5D, 63.0D, 1447.5D, 90.0F, 0.0F);
/* 17 */     this.queueXBorder = -408;
/*    */     
/* 19 */     this.arenaSpawn1 = new Location(Bukkit.getWorld("world"), -427.5D, 68.0D, 1460.5D, 180.0F, 0.0F);
/* 20 */     this.arenaSpawn2 = new Location(Bukkit.getWorld("world"), -427.5D, 68.0D, 1434.5D, 0.0F, 0.0F);
/*    */     
/* 22 */     this.winningRoom = new Location(Bukkit.getWorld("world"), -453.5D, 63.0D, 1447.5D, -90.0F, 0.0F);
/*    */     
/* 24 */     this.content = new ItemStack[] { core.diamondSwordSharpness, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, new ItemStack(Material.ARROW, 64) };
/* 25 */     this.armor = new ItemStack[] { new ItemStack(Material.IRON_BOOTS), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.LEATHER_HELMET) };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 31 */     return "Pillars";
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\event\arenas\brackets\BracketsEventArenaPillars.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */