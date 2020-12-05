/*    */ package net.lightpvp.java.managers.ctf;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.event.Listener;
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
/*    */ public class CaptureTheFlagManager
/*    */   implements Listener
/*    */ {
/*    */   private Core core;
/*    */   private Location captureFlagLocation;
/*    */   
/*    */   public CaptureTheFlagManager(Core core) {
/* 33 */     this.core = core;
/*    */     
/* 35 */     this.captureFlagLocation = new Location(core.getWorld(), -645.5D, 104.0D, 719.5D);
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\ctf\CaptureTheFlagManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */