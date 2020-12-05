/*    */ package net.lightpvp.java.commands.admin;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.lightpvp.java.Core;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ 
/*    */ public class CommandRecords
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/*    */   
/*    */   public CommandRecords(Core core) {
/* 17 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 22 */     if (args.length >= 1) {
/* 23 */       List<String> records = null;
/*    */       
/* 25 */       int page = 0;
/*    */       
/*    */       try {
/* 28 */         page = Integer.parseInt(args[args.length - 1]) - 1;
/* 29 */       } catch (NumberFormatException numberFormatException) {}
/*    */       
/*    */       String str;
/*    */       
/* 33 */       switch ((str = args[0].toLowerCase()).hashCode()) { case -1367775349: if (str.equals("caller")) {
/*    */             
/* 35 */             if (args.length >= 2) {
/* 36 */               String caller = args[1];
/*    */               
/* 38 */               records = this.core.getServerRecordsManager().getRecords("SELECT id,caller,considered,type,reason,timestamp FROM records WHERE caller = '" + caller + "' ORDER BY id DESC" + " LIMIT 10 OFFSET " + (page * 10)); break;
/*    */             } 
/* 40 */             sender.sendMessage(ChatColor.RED + "Invalid Arguments!");
/* 41 */             return true;
/*    */           } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/*    */         case -1109880953:
/*    */           if (str.equals("latest"))
/* 65 */           { records = this.core.getServerRecordsManager().getRecords("SELECT id,caller,considered,type,reason,timestamp FROM records ORDER BY id DESC LIMIT 10 OFFSET " + (page * 10)); break; } 
/*    */         case -518098920: if (str.equals("considered")) { if (args.length >= 2) { String considered = args[1]; records = this.core.getServerRecordsManager().getRecords("SELECT id,caller,considered,type,reason,timestamp FROM records WHERE considered = '" + considered + "' ORDER BY id DESC" + " LIMIT 10 OFFSET " + (page * 10)); break; }  sender.sendMessage(ChatColor.RED + "Invalid Arguments!"); return true; } 
/*    */         case 3575610: if (str.equals("type")) { if (args.length >= 2) { String type = args[1]; records = this.core.getServerRecordsManager().getRecords("SELECT id,caller,considered,type,reason,timestamp FROM records WHERE type = '" + type.toUpperCase() + "' ORDER BY id DESC" + " LIMIT 10 OFFSET " + (page * 10)); break; }  sender.sendMessage(ChatColor.RED + "Invalid Arguments!"); return true; } 
/* 68 */         case 94851343: if (str.equals("count")) { sender.sendMessage(ChatColor.RED + "Total Record Count: " + this.core.getServerRecordsManager().getRecordCount());
/* 69 */             return true; } 
/*    */         default:
/* 71 */           sender.sendMessage(ChatColor.RED + "Usage: /records {caller/considered/type/latest/count} {player/type} [page]");
/*    */           break; }
/*    */ 
/*    */       
/* 75 */       if (records != null) {
/* 76 */         printRecords(sender, records, page + 1);
/*    */       } else {
/* 78 */         sender.sendMessage(ChatColor.RED + "No records found!");
/*    */       } 
/*    */     } else {
/* 81 */       sender.sendMessage(ChatColor.RED + "Usage: /records {caller/considered/type/latest/count} {player/type} [page]");
/*    */     } 
/* 83 */     return false;
/*    */   }
/*    */   
/*    */   private void printRecords(CommandSender sender, List<String> records, int page) {
/* 87 */     StringBuilder message = new StringBuilder();
/*    */     
/* 89 */     message.append(ChatColor.RED + "Records(Page: " + page + "):\n");
/* 90 */     for (String record : records) {
/* 91 */       message.append(String.valueOf(record) + "\n");
/*    */     }
/*    */     
/* 94 */     sender.sendMessage(message.toString());
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\admin\CommandRecords.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */