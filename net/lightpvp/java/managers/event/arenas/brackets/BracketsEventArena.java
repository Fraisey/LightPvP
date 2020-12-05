/*    */ package net.lightpvp.java.managers.event.arenas.brackets;
/*    */ 
/*    */ import net.lightpvp.java.managers.event.arenas.EventArena;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BracketsEventArena
/*    */   extends EventArena
/*    */ {
/*    */   protected Location queueRoom;
/*    */   protected int queueXBorder;
/*    */   protected Location arenaSpawn1;
/*    */   protected Location arenaSpawn2;
/*    */   protected Location winningRoom;
/*    */   protected ItemStack[] content;
/*    */   protected ItemStack[] armor;
/*    */   
/*    */   public Location getQueueRoom() {
/* 22 */     return this.queueRoom;
/*    */   }
/*    */   
/*    */   public int getQueueXBorder() {
/* 26 */     return this.queueXBorder;
/*    */   }
/*    */   
/*    */   public Location getArenaSpawn1() {
/* 30 */     return this.arenaSpawn1;
/*    */   }
/*    */   
/*    */   public Location getArenaSpawn2() {
/* 34 */     return this.arenaSpawn2;
/*    */   }
/*    */   
/*    */   public Location getWinningRoom() {
/* 38 */     return this.winningRoom;
/*    */   }
/*    */   
/*    */   public ItemStack[] getContent() {
/* 42 */     return this.content;
/*    */   }
/*    */   
/*    */   public ItemStack[] getArmor() {
/* 46 */     return this.armor;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\event\arenas\brackets\BracketsEventArena.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */