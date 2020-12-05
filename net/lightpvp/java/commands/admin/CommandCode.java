/*    */ package net.lightpvp.java.commands.admin;
/*    */ 
/*    */ import java.util.Random;
/*    */ import java.util.Set;
/*    */ import net.lightpvp.java.Core;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.configuration.ConfigurationSection;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandCode
/*    */   implements CommandExecutor
/*    */ {
/*    */   private Core core;
/* 18 */   private final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
/* 19 */   private Random random = new Random();
/*    */   
/*    */   public CommandCode(Core core) {
/* 22 */     this.core = core;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 27 */     if (args.length > 0) {
/* 28 */       if (args[0].equalsIgnoreCase("create")) {
/* 29 */         if (args.length > 2) {
/* 30 */           String code = getRandomCode();
/* 31 */           String value = "";
/* 32 */           if (args[1].equalsIgnoreCase("kit")) {
/* 33 */             if (this.core.getKitManager().getKit(args[2]) != null) {
/* 34 */               value = "kit-" + args[2];
/*    */             } else {
/* 36 */               sender.sendMessage(ChatColor.RED + "Invalid Value!");
/* 37 */               return true;
/*    */             } 
/* 39 */           } else if (args[1].equalsIgnoreCase("coins")) {
/*    */             try {
/* 41 */               if (Integer.parseInt(args[2]) > 0) {
/* 42 */                 value = "coins-" + args[2];
/*    */               }
/* 44 */             } catch (NumberFormatException e) {
/* 45 */               sender.sendMessage(ChatColor.RED + "Invalid Value!");
/* 46 */               return true;
/*    */             } 
/* 48 */           } else if (args[1].equalsIgnoreCase("casinotries")) {
/*    */             try {
/* 50 */               if (Integer.parseInt(args[2]) > 0) {
/* 51 */                 value = "casinotries-" + args[2];
/*    */               }
/* 53 */             } catch (NumberFormatException e) {
/* 54 */               sender.sendMessage(ChatColor.RED + "Invalid Value!");
/* 55 */               return true;
/*    */             } 
/*    */           } else {
/* 58 */             sender.sendMessage(ChatColor.RED + "Invalid Type!");
/* 59 */             return true;
/*    */           } 
/* 61 */           this.core.getConfig().set("codes." + code, value);
/* 62 */           sender.sendMessage(ChatColor.YELLOW + "Redeem code: " + code + " succesfully created!");
/*    */         } else {
/* 64 */           sender.sendMessage(ChatColor.RED + "Usage: /code create <type> <value>");
/*    */         } 
/* 66 */       } else if (args[0].equalsIgnoreCase("remove")) {
/* 67 */         if (args.length > 1) {
/* 68 */           if (this.core.getConfig().getString("codes." + args[1]) != null) {
/* 69 */             this.core.getConfig().set("codes." + args[1], null);
/* 70 */             sender.sendMessage(ChatColor.YELLOW + "Succesfully removed the redeem code: " + args[1]);
/*    */           } else {
/* 72 */             sender.sendMessage(ChatColor.RED + "Not Found!");
/*    */           } 
/*    */         } else {
/* 75 */           sender.sendMessage(ChatColor.RED + "Usage: /code remove <code>");
/*    */         } 
/* 77 */       } else if (args[0].equalsIgnoreCase("list")) {
/* 78 */         sender.sendMessage(ChatColor.YELLOW + "Redeem Codes:");
/* 79 */         ConfigurationSection cs = this.core.getConfig().getConfigurationSection("codes");
/* 80 */         if (cs != null) {
/* 81 */           Set<String> codes = cs.getKeys(false);
/* 82 */           if (codes != null) {
/* 83 */             for (String code : codes) {
/* 84 */               sender.sendMessage(ChatColor.YELLOW + code + " : " + this.core.getConfig().getString("codes." + code));
/*    */             }
/*    */           }
/*    */         } 
/*    */       } 
/*    */     }
/* 90 */     return false;
/*    */   }
/*    */   
/*    */   private String getRandomCode() {
/* 94 */     StringBuilder sb = new StringBuilder();
/* 95 */     for (int i = 0; i < 6; i++)
/* 96 */       sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(this.random.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZ".length()))); 
/* 97 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\admin\CommandCode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */