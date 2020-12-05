/*    */ package net.lightpvp.java.commands.player;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import net.lightpvp.java.User;
/*    */ import net.lightpvp.java.utils.ChatUtil;
/*    */ import net.lightpvp.java.utils.RankUtil;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class CommandReport
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandReport(Core core) {
/* 20 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 25 */     if (!(sender instanceof Player))
/* 26 */       return false; 
/* 27 */     Player player = (Player)sender;
/* 28 */     User user = this.core.getUser(player.getUniqueId());
/*    */     
/* 30 */     if (!user.isMuted()) {
/* 31 */       if (args.length > 1) {
/* 32 */         Player target = Bukkit.getPlayer(args[0]);
/* 33 */         if (target != null) {
/* 34 */           if (!player.getName().equals(target.getName())) {
/* 35 */             StringBuilder builder = new StringBuilder();
/* 36 */             for (int i = 1; i < args.length; i++) {
/* 37 */               builder.append(String.valueOf(args[i]) + " ");
/*    */             }
/*    */             
/* 40 */             String reason = builder.toString();
/* 41 */             String report = ChatColor.DARK_RED + "███" + ChatColor.BOLD + " " + ChatUtil.Gi + "Report from " + RankUtil.getFullTag(player) + ChatColor.GRAY + " - " + RankUtil.getFullTag(target) + ChatColor.GRAY + " for " + ChatUtil.Gi + reason;
/*    */             
/* 43 */             int informed = 0;
/* 44 */             for (Player p : Bukkit.getOnlinePlayers()) {
/* 45 */               if (p.hasPermission("core.report") && !player.getName().equals(p.getName())) {
/* 46 */                 p.sendMessage(report);
/* 47 */                 informed++;
/*    */               } 
/*    */             } 
/* 50 */             player.sendMessage(ChatColor.GRAY + "Thank you for " + ChatUtil.Gi + "reporting " + RankUtil.getFullTag(target) + ChatColor.GRAY + ", " + informed + ChatUtil.Gi + " admin" + ((informed == 1) ? "" : "s") + ChatColor.GRAY + " have been informed!");
/* 51 */             player.sendMessage(ChatColor.RED + ChatColor.BOLD + "Warning: fake reports or spamming reports will get you " + ChatColor.DARK_RED + ChatColor.BOLD + "banned!");
/*    */           } else {
/* 53 */             player.sendMessage(ChatColor.RED + "You can't report yourself!");
/*    */           } 
/*    */         } else {
/* 56 */           player.sendMessage(ChatColor.RED + "The reported player must be online!");
/*    */         } 
/*    */       } else {
/* 59 */         player.sendMessage(ChatColor.RED + "Usage: /report <player> <reason>");
/*    */       } 
/*    */     } else {
/* 62 */       player.sendMessage(ChatColor.RED + "You can't report players while muted!");
/*    */     } 
/* 64 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\player\CommandReport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */