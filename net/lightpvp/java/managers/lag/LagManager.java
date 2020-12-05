/*    */ package net.lightpvp.java.managers.lag;
/*    */ 
/*    */ public class LagManager
/*    */   implements Runnable {
/*  5 */   public static int TickCount = 0;
/*  6 */   public static long[] Ticks = new long[600];
/*  7 */   public static long LastTick = 0L;
/*    */   
/*    */   public static double getTPS() {
/* 10 */     return getTPS(100);
/*    */   }
/*    */   
/*    */   public static double getTPS(int ticks) {
/* 14 */     if (TickCount < ticks) {
/* 15 */       return 20.0D;
/*    */     }
/*    */ 
/*    */     
/* 19 */     int target = (TickCount - 1 - ticks) % Ticks.length;
/* 20 */     long elapsed = System.currentTimeMillis() - Ticks[target];
/*    */     
/* 22 */     return ticks / elapsed / 1000.0D;
/*    */   }
/*    */   
/*    */   public static long getElapsed(int tickID) {
/* 26 */     Ticks.length;
/*    */ 
/*    */ 
/*    */     
/* 30 */     long time = Ticks[tickID % Ticks.length];
/* 31 */     return System.currentTimeMillis() - time;
/*    */   }
/*    */   
/*    */   public void run() {
/* 35 */     Ticks[TickCount % Ticks.length] = System.currentTimeMillis();
/*    */     
/* 37 */     TickCount++;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\lag\LagManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */