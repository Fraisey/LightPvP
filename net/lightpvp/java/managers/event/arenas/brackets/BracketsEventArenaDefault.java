/*    */ package net.lightpvp.java.managers.event.arenas.brackets;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class BracketsEventArenaDefault
/*    */   extends BracketsEventArena
/*    */ {
/*    */   public BracketsEventArenaDefault(Core core) {
/* 13 */     this.spawn = new Location(Bukkit.getWorld("world"), -693.5D, 89.0D, 1159.5D, 90.0F, 0.0F);
/* 14 */     this.hologramLocation = new Location(Bukkit.getWorld("world"), -696.5D, 92.0D, 1159.5D, 0.0F, 0.0F);
/*    */     
/* 16 */     this.queueRoom = new Location(Bukkit.getWorld("world"), -693.5D, 89.0D, 1159.5D, 90.0F, 0.0F);
/* 17 */     this.queueXBorder = -699;
/*    */     
/* 19 */     this.arenaSpawn1 = new Location(Bukkit.getWorld("world"), -700.5D, 85.0D, 1159.5D, 90.0F, 0.0F);
/* 20 */     this.arenaSpawn2 = new Location(Bukkit.getWorld("world"), -720.5D, 85.0D, 1159.5D, -90.0F, 0.0F);
/*    */     
/* 22 */     this.winningRoom = new Location(Bukkit.getWorld("world"), -727.5D, 89.0D, 1159.5D, 90.0F, 0.0F);
/*    */     
/* 24 */     this.content = new ItemStack[] { core.diamondSwordSharpness, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
/* 25 */     this.armor = new ItemStack[] { new ItemStack(Material.IRON_BOOTS), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_HELMET) };
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 30 */     return "Default";
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\event\arenas\brackets\BracketsEventArenaDefault.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */