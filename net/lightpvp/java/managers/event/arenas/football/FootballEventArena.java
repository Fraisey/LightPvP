/*    */ package net.lightpvp.java.managers.event.arenas.football;
/*    */ 
/*    */ import net.lightpvp.java.managers.event.arenas.EventArena;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class FootballEventArena
/*    */   extends EventArena
/*    */ {
/*    */   protected Location blueGoal;
/*    */   protected Location redGoal;
/*    */   protected Vector blueGoalRegionMax;
/*    */   protected Vector blueGoalRegionMin;
/*    */   protected Vector redGoalRegionMax;
/*    */   protected Vector redGoalRegionMin;
/*    */   protected Location ballSpawn;
/*    */   
/*    */   public Location getBlueGoal() {
/* 22 */     return this.blueGoal;
/*    */   }
/*    */   
/*    */   public Location getRedGoal() {
/* 26 */     return this.redGoal;
/*    */   }
/*    */   
/*    */   public Vector getBlueGoalRegionMax() {
/* 30 */     return this.blueGoalRegionMax;
/*    */   }
/*    */   
/*    */   public Vector getBlueGoalRegionMin() {
/* 34 */     return this.blueGoalRegionMin;
/*    */   }
/*    */   
/*    */   public Vector getRedGoalRegionMax() {
/* 38 */     return this.redGoalRegionMax;
/*    */   }
/*    */   
/*    */   public Vector getRedGoalRegionMin() {
/* 42 */     return this.redGoalRegionMin;
/*    */   }
/*    */   
/*    */   public Location getBallSpawn() {
/* 46 */     return this.ballSpawn;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\event\arenas\football\FootballEventArena.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */