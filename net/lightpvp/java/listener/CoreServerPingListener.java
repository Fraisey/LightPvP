/*    */ package net.lightpvp.java.listener;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import java.util.UUID;
/*    */ import net.lightpvp.java.Core;
/*    */ import net.lightpvp.java.utils.ChatUtil;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.server.ServerListPingEvent;
/*    */ import ru.tehkode.permissions.bukkit.PermissionsEx;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CoreServerPingListener
/*    */   implements Listener
/*    */ {
/*    */   private Core core;
/* 22 */   private List<String> news = new ArrayList<>();
/* 23 */   private Random random = new Random();
/*    */   
/*    */   public CoreServerPingListener(Core core) {
/* 26 */     this.core = core;
/*    */     
/* 28 */     this.news.add("/trail added for veteran+!");
/* 29 */     this.news.add("Achievements for special kits coming soon!");
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onServerListPingEvent(ServerListPingEvent evt) {
/* 34 */     evt.setMaxPlayers(115);
/* 35 */     List<String> names = this.core.getPlayerStatsManager().getNamesFromIP(evt.getAddress().getHostAddress());
/* 36 */     String name = (names == null) ? null : names.get(0);
/*    */     
/* 38 */     String uuidString = this.core.getPlayerStatsManager().getUUIDFromName(name);
/*    */     
/* 40 */     if (PermissionsEx.getPermissionManager().getGroup("Guest").has("kit.*")) {
/* 41 */       evt.setMotd(String.valueOf(ChatUtil.LPp) + ChatColor.DARK_RED + ChatColor.BOLD + "Big update on monday because of heavy bugs!" + "\n" + ChatColor.BLUE + ChatColor.BOLD + "Stats" + "> " + ChatColor.GRAY + ChatColor.BOLD + "Kills: " + ChatColor.BLUE + ChatColor.BOLD + "0" + ChatColor.GRAY + ChatColor.BOLD + ", Coins: " + ChatColor.BLUE + ChatColor.BOLD + "500");
/*    */       
/*    */       return;
/*    */     } 
/* 45 */     if (name == null && uuidString == null) {
/* 46 */       evt.setMotd(String.valueOf(ChatUtil.LPp) + ChatColor.DARK_RED + ChatColor.BOLD + "Big update on monday because of heavy bugs!" + "\n" + ChatColor.BLUE + ChatColor.BOLD + "Stats" + "> " + ChatColor.GRAY + ChatColor.BOLD + "Kills: " + ChatColor.BLUE + ChatColor.BOLD + "0" + ChatColor.GRAY + ChatColor.BOLD + ", Coins: " + ChatColor.BLUE + ChatColor.BOLD + "500");
/*    */       return;
/*    */     } 
/* 49 */     UUID uuid = UUID.fromString(uuidString);
/* 50 */     evt.setMotd(String.valueOf(ChatUtil.LPp) + ChatColor.DARK_RED + ChatColor.BOLD + "Big update on monday because of heavy bugs!" + "\n" + ChatColor.BLUE + ChatColor.BOLD + name + "> " + ChatColor.GRAY + ChatColor.BOLD + "Kills: " + ChatColor.BLUE + ChatColor.BOLD + this.core.getPlayerStatsManager().getInt(uuid, "kills") + ChatColor.GRAY + ChatColor.BOLD + ", Coins: " + ChatColor.BLUE + ChatColor.BOLD + this.core.getPlayerStatsManager().getInt(uuid, "coins"));
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\listener\CoreServerPingListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */