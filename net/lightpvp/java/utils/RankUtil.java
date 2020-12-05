/*    */ package net.lightpvp.java.utils;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.entity.Player;
/*    */ import ru.tehkode.permissions.PermissionGroup;
/*    */ import ru.tehkode.permissions.bukkit.PermissionsEx;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RankUtil
/*    */ {
/*    */   static PermissionGroup getGroup(Player player) {
/* 15 */     return PermissionsEx.getUser(player).getGroups()[0];
/*    */   }
/*    */   
/*    */   public static String getFullTag(Player player) {
/* 19 */     Bukkit.getLogger().info("getGroup: " + getGroup(player));
/* 20 */     Bukkit.getLogger().info("GetOpt: " + getGroup(player).getOption("color"));
/* 21 */     return String.valueOf(ChatColor.translateAlternateColorCodes('&', getGroup(player).getOption("color").toString())) + player.getName();
/*    */   }
/*    */   
/*    */   public static String getPrefix(UUID uuid) {
/* 25 */     return ChatColor.translateAlternateColorCodes('&', PermissionsEx.getUser(uuid.toString()).getGroups()[0].getPrefix());
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\jav\\utils\RankUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */