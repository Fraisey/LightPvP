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
/*    */ 
/*    */ 
/*    */ public class CommandReply
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandReply(Core core) {
/* 22 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 27 */     if (!(sender instanceof Player))
/* 28 */       return false; 
/* 29 */     Player player = (Player)sender;
/*    */     
/* 31 */     User user = this.core.getUser(player.getUniqueId());
/*    */     
/* 33 */     if (user.isMuted()) {
/* 34 */       player.sendMessage(ChatColor.RED + "You can not send private messages while muted!");
/* 35 */       return true;
/*    */     } 
/*    */     
/* 38 */     if (user.getReplyTo() != null) {
/* 39 */       Player target = Bukkit.getPlayerExact(user.getReplyTo());
/* 40 */       if (target != null) {
/*    */         
/* 42 */         User targetUser = this.core.getUser(target.getUniqueId());
/*    */         
/* 44 */         if (!targetUser.ignoresPlayer(player)) {
/*    */           
/* 46 */           if (!user.ignoresPlayer(target)) {
/*    */             
/* 48 */             if (args.length >= 1) {
/* 49 */               StringBuilder builder = new StringBuilder();
/* 50 */               for (int i = 0; i < args.length; i++) {
/* 51 */                 builder.append(String.valueOf(args[i]) + " ");
/*    */               }
/*    */               
/* 54 */               String message = builder.toString();
/* 55 */               if (!message.isEmpty()) {
/* 56 */                 player.sendMessage(String.valueOf(ChatUtil.Gi) + "[Me -> " + RankUtil.getFullTag(target) + ChatUtil.Gi + "] " + ChatColor.WHITE + message);
/* 57 */                 target.sendMessage(String.valueOf(ChatUtil.Gi) + "[" + RankUtil.getFullTag(player) + ChatUtil.Gi + " -> Me] " + ChatColor.WHITE + message);
/* 58 */                 targetUser.setReplyTo(player.getUniqueId());
/*    */                 
/* 60 */                 for (Player p : Bukkit.getOnlinePlayers()) {
/* 61 */                   if (!p.getName().equals(player.getName()) && !p.getName().equals(target.getName()) && p.hasPermission("core.spy")) {
/* 62 */                     User pUser = this.core.getUser(p.getUniqueId());
/* 63 */                     if (pUser.isSpyMode()) {
/* 64 */                       p.sendMessage(ChatColor.GRAY + "(" + ChatUtil.Gi + "Spy" + ChatColor.GRAY + ") [" + RankUtil.getFullTag(player) + ChatUtil.Gi + " -> " + RankUtil.getFullTag(target) + ChatUtil.Gi + "] " + ChatColor.WHITE + message);
/*    */                     }
/*    */                   } 
/*    */                 } 
/* 68 */                 return true;
/*    */               } 
/*    */             } else {
/* 71 */               player.sendMessage(ChatColor.RED + "Usage: /" + label + " <message>");
/* 72 */               return true;
/*    */             } 
/*    */           } else {
/* 75 */             player.sendMessage(ChatColor.RED + "You can't message players who you are ignoring!");
/*    */           } 
/*    */         } else {
/* 78 */           player.sendMessage(String.valueOf(RankUtil.getFullTag(target)) + ChatColor.GRAY + " is " + ChatUtil.Gi + "ignoring" + ChatColor.GRAY + " you!");
/*    */         } 
/*    */       } else {
/* 81 */         player.sendMessage(ChatColor.RED + "The player you are trying to reply to is not online!");
/* 82 */         return true;
/*    */       } 
/*    */     } else {
/* 85 */       player.sendMessage(ChatColor.RED + "No one to reply to!");
/* 86 */       return true;
/*    */     } 
/* 88 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\player\CommandReply.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */