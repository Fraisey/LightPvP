/*    */ package net.lightpvp.java.managers.event.arenas.brackets;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class BracketsEventArenaMossy
/*    */   extends BracketsEventArena
/*    */ {
/*    */   public BracketsEventArenaMossy(Core core) {
/* 13 */     this.spawn = new Location(Bukkit.getWorld("world"), -524.5D, 99.0D, 1248.5D, 90.0F, 0.0F);
/* 14 */     this.hologramLocation = new Location(Bukkit.getWorld("world"), -524.5D, 101.0D, 1248.5D, 90.0F, 0.0F);
/*    */     
/* 16 */     this.queueRoom = new Location(Bukkit.getWorld("world"), -524.5D, 99.0D, 1248.5D, 90.0F, 0.0F);
/* 17 */     this.queueXBorder = -529;
/*    */     
/* 19 */     this.arenaSpawn1 = new Location(Bukkit.getWorld("world"), -536.5D, 101.0D, 1239.5D, 50.0F, 0.0F);
/* 20 */     this.arenaSpawn2 = new Location(Bukkit.getWorld("world"), -557.5D, 99.0D, 1256.5D, 230.0F, 0.0F);
/*    */     
/* 22 */     this.winningRoom = new Location(Bukkit.getWorld("world"), -571.5D, 99.0D, 1249.5D, 90.0F, 0.0F);
/*    */     
/* 24 */     this.content = new ItemStack[] { core.diamondSwordSharpness, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, new ItemStack(Material.ARROW, 64) };
/* 25 */     this.armor = new ItemStack[] { new ItemStack(Material.IRON_BOOTS), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.LEATHER_HELMET) };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 31 */     return "Mossy";
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\event\arenas\brackets\BracketsEventArenaMossy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */