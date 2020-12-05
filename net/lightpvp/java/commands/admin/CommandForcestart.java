/*    */ package net.lightpvp.java.commands.admin;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class CommandForcestart
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandForcestart(Core core) {
/* 15 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 20 */     if (this.core.getHostedEventManager().getCurrentEvent() != null) {
/* 21 */       if (!this.core.getHostedEventManager().getCurrentEvent().getStarted()) {
/* 22 */         this.core.getHostedEventManager().startCurrentEvent(true);
/*    */       } else {
/* 24 */         sender.sendMessage(ChatColor.RED + "The event you are trying to force start has already started");
/* 25 */         return true;
/*    */       } 
/*    */     } else {
/* 28 */       sender.sendMessage(ChatColor.RED + "There is no event being hosted at the moment!");
/* 29 */       return true;
/*    */     } 
/* 31 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\admin\CommandForcestart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */