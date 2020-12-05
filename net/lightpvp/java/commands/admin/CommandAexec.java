/*    */ package net.lightpvp.java.commands.admin;
/*    */ 
/*    */ import net.lightpvp.java.Core;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ public class CommandAexec
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandAexec(Core core) {
/* 17 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 22 */     if (args.length >= 2) {
/* 23 */       Player target = Bukkit.getPlayer(args[0]);
/* 24 */       if (target != null) {
/* 25 */         String message = "";
/* 26 */         for (int i = 1; i < args.length; i++) {
/* 27 */           message = message.concat(String.valueOf(args[i]) + " ");
/*    */         }
/* 29 */         target.chat(message);
/* 30 */         return true;
/*    */       } 
/*    */     } 
/* 33 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\admin\CommandAexec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */