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
/*    */ public class CommandMsg
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandMsg(Core core) {
/* 20 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 25 */     if (!(sender instanceof Player))
/* 26 */       return false; 
/* 27 */     Player player = (Player)sender;
/*    */     
/* 29 */     User user = this.core.getUser(player.getUniqueId());
/*    */     
/* 31 */     if (user.isMuted()) {
/* 32 */       player.sendMessage(ChatColor.RED + "You can not send private messages while muted!");
/* 33 */       return true;
/*    */     } 
/*    */     
/* 36 */     if (args.length >= 2) {
/* 37 */       Player target = Bukkit.getPlayer(args[0]);
/* 38 */       if (target != null) {
/*    */         
/* 40 */         User targetUser = this.core.getUser(target.getUniqueId());
/*    */         
/* 42 */         if (!targetUser.ignoresPlayer(player)) {
/*    */           
/* 44 */           if (!user.ignoresPlayer(target)) {
/*    */             
/* 46 */             if (!player.getName().equals(target.getName())) {
/* 47 */               StringBuilder builder = new StringBuilder();
/* 48 */               for (int i = 1; i < args.length; i++) {
/* 49 */                 builder.append(String.valueOf(args[i]) + " ");
/*    */               }
/*    */               
/* 52 */               String message = builder.toString();
/* 53 */               if (!message.isEmpty()) {
/* 54 */                 player.sendMessage(String.valueOf(ChatUtil.Gi) + "[Me -> " + RankUtil.getFullTag(target) + ChatUtil.Gi + "] " + ChatColor.WHITE + message);
/* 55 */                 target.sendMessage(String.valueOf(ChatUtil.Gi) + "[" + RankUtil.getFullTag(player) + ChatUtil.Gi + " -> Me] " + ChatColor.WHITE + message);
/* 56 */                 user.setReplyTo(target.getUniqueId());
/* 57 */                 targetUser.setReplyTo(player.getUniqueId());
/*    */                 
/* 59 */                 for (Player p : Bukkit.getOnlinePlayers()) {
/* 60 */                   if (!p.getName().equals(player.getName()) && !p.getName().equals(target.getName()) && p.hasPermission("core.spy")) {
/* 61 */                     User pUser = this.core.getUser(p.getUniqueId());
/* 62 */                     pUser.isSpyMode();
/*    */                   } 
/*    */                 } 
/*    */ 
/*    */                 
/* 67 */                 return true;
/*    */               } 
/*    */             } else {
/* 70 */               player.sendMessage(ChatColor.RED + "You can't private message yourself!");
/* 71 */               return true;
/*    */             } 
/*    */           } else {
/* 74 */             player.sendMessage(ChatColor.RED + "You can't message players who you are ignoring!");
/*    */           } 
/*    */         } else {
/* 77 */           player.sendMessage(String.valueOf(RankUtil.getFullTag(target)) + ChatColor.GRAY + " is " + ChatUtil.Gi + "ignoring" + ChatColor.GRAY + " you!");
/*    */         } 
/*    */       } else {
/* 80 */         player.sendMessage(ChatColor.RED + "That player is not online!");
/* 81 */         return true;
/*    */       } 
/*    */     } else {
/* 84 */       player.sendMessage(ChatColor.RED + "Usage: /" + label + " <player> <message>");
/* 85 */       return false;
/*    */     } 
/* 87 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\player\CommandMsg.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */