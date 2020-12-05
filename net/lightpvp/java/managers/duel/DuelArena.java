/*    */ package net.lightpvp.java.managers.duel;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ 
/*    */ public class DuelArena
/*    */   implements Cloneable
/*    */ {
/*    */   private String name;
/*    */   private Location spawn1;
/*    */   private Location spawn2;
/*    */   
/*    */   public DuelArena(String name, Location spawn1, Location spawn2) {
/* 13 */     this.name = name;
/*    */     
/* 15 */     this.spawn1 = spawn1;
/* 16 */     this.spawn2 = spawn2;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 20 */     return this.name;
/*    */   }
/*    */   
/*    */   public Location getSpawn1() {
/* 24 */     return this.spawn1;
/*    */   }
/*    */   
/*    */   public Location getSpawn2() {
/* 28 */     return this.spawn2;
/*    */   }
/*    */ 
/*    */   
/*    */   protected DuelArena clone() {
/*    */     try {
/* 34 */       return (DuelArena)super.clone();
/* 35 */     } catch (CloneNotSupportedException e) {
/* 36 */       e.printStackTrace();
/*    */       
/* 38 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\duel\DuelArena.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */