/*    */ package net.lightpvp.java.utils;
/*    */ 
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChatUtil
/*    */ {
/* 17 */   public static String Gi = ChatColor.GREEN + ChatColor.ITALIC;
/* 18 */   public static String LPp = ChatColor.GRAY + "[" + ChatColor.BLUE + ChatColor.BOLD + "LightPvP" + ChatColor.RESET + ChatColor.GRAY + "] ";
/* 19 */   public static String LPip = "eu.lightpvp.net";
/*    */   
/*    */   public static void broadcastMessage(String message) {
/* 22 */     for (Player p : Bukkit.getOnlinePlayers())
/* 23 */       p.sendMessage(message); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\jav\\utils\ChatUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */