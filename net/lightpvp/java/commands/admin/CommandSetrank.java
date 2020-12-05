/*    */ package net.lightpvp.java.commands.admin;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import net.lightpvp.java.Core;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ 
/*    */ public class CommandSetrank
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandSetrank(Core core) {
/* 17 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 22 */     if (args.length == 2) {
/* 23 */       String uuidString = this.core.getPlayerStatsManager().getUUIDFromName(args[0]);
/*    */       
/* 25 */       if (uuidString != null) {
/* 26 */         UUID uuid = UUID.fromString(uuidString);
/*    */         
/* 28 */         this.core.setRank(uuid, args[1]);
/*    */       } else {
/* 30 */         sender.sendMessage(ChatColor.RED + "UUID not found");
/*    */       } 
/*    */       
/* 33 */       return true;
/*    */     } 
/* 35 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\admin\CommandSetrank.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */