/*    */ package net.lightpvp.java.managers.kit;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.ChatColor;
/*    */ 
/*    */ 
/*    */ public enum Rareness
/*    */ {
/*  9 */   COMMON(ChatColor.AQUA + "Common"), RARE(ChatColor.BLUE + "Rare"), VERY_RARE(ChatColor.RED + "Very Rare"), EXTREMLY_RARE(ChatColor.DARK_RED + "Extremly Rare"), IMPOSSIBLY_RARE(ChatColor.DARK_RED + ChatColor.BOLD + "Impossibly Rare"); private static Random random;
/*    */   static {
/* 11 */     random = new Random();
/*    */   }
/*    */   private String name;
/*    */   
/*    */   Rareness(String name) {
/* 16 */     this.name = name;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 20 */     return this.name;
/*    */   }
/*    */   
/*    */   public static Rareness getNaturalRareness() {
/* 24 */     int chance = random.nextInt(1000);
/*    */     
/* 26 */     if (chance >= 0 && chance < 600)
/* 27 */       return COMMON; 
/* 28 */     if (chance >= 600 && chance < 850)
/* 29 */       return RARE; 
/* 30 */     if (chance >= 850 && chance < 950)
/* 31 */       return VERY_RARE; 
/* 32 */     if (chance >= 950 && chance < 995)
/* 33 */       return EXTREMLY_RARE; 
/* 34 */     if (chance >= 995 && chance < 1000) {
/* 35 */       return IMPOSSIBLY_RARE;
/*    */     }
/*    */     
/* 38 */     return COMMON;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\Rareness.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */