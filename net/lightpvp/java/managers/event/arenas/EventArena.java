/*    */ package net.lightpvp.java.managers.event.arenas;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ 
/*    */ public abstract class EventArena
/*    */ {
/*    */   protected Location spawn;
/*    */   protected Location hologramLocation;
/*    */   protected Location ctspawn;
/*    */   protected Location tspawn;
/*    */   
/*    */   public abstract String getName();
/*    */   
/*    */   public Location getSpawn() {
/* 15 */     return this.spawn;
/*    */   }
/*    */   
/*    */   public Location getHologramLocation() {
/* 19 */     return this.hologramLocation;
/*    */   }
/*    */   
/*    */   public Location getCTSpawn() {
/* 23 */     return this.ctspawn;
/*    */   }
/*    */   
/*    */   public Location getTSpawn() {
/* 27 */     return this.tspawn;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\event\arenas\EventArena.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */