/*     */ package net.lightpvp.java.commands.premium;
/*     */ 
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.particles.ParticleEffect;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandTrail
/*     */   implements CommandExecutor
/*     */ {
/*     */   private Core core;
/*     */   private String trails;
/*     */   
/*     */   public CommandTrail(Core core) {
/*  22 */     this.trails = "Dust, Aura, Magic, Enchant, Crit. Type /trail <trailname> to use. For example: /trail dust";
/*     */     this.core = core;
/*     */   }
/*     */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
/*  26 */     if (!(sender instanceof Player))
/*  27 */       return false; 
/*  28 */     Player player = (Player)sender;
/*  29 */     if (args.length == 1) {
/*  30 */       if (args[0].equalsIgnoreCase("dust")) {
/*  31 */         User user = this.core.getUser(player.getUniqueId());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  40 */         player.sendMessage(String.valueOf(ChatUtil.Gi) + "Dust trail applied");
/*     */       }
/*  42 */       else if (args[0].equalsIgnoreCase("aura")) {
/*  43 */         User user = this.core.getUser(player.getUniqueId());
/*     */ 
/*     */         
/*  46 */         int strength = 5;
/*     */ 
/*     */ 
/*     */         
/*  50 */         user.setTrailAmount(strength);
/*     */         
/*  52 */         player.sendMessage(String.valueOf(ChatUtil.Gi) + "Aura trail applied");
/*     */       }
/*  54 */       else if (args[0].equalsIgnoreCase("magic")) {
/*  55 */         User user = this.core.getUser(player.getUniqueId());
/*     */ 
/*     */         
/*  58 */         int strength = 5;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  64 */         player.sendMessage(String.valueOf(ChatUtil.Gi) + "Magic trail applied");
/*     */       }
/*  66 */       else if (args[0].equalsIgnoreCase("enchant")) {
/*  67 */         User user = this.core.getUser(player.getUniqueId());
/*     */ 
/*     */         
/*  70 */         int strength = 5;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  76 */         player.sendMessage(String.valueOf(ChatUtil.Gi) + "Enchant trail applied");
/*     */       }
/*  78 */       else if (args[0].equalsIgnoreCase("crit")) {
/*  79 */         User user = this.core.getUser(player.getUniqueId());
/*     */ 
/*     */         
/*  82 */         int strength = 5;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  88 */         player.sendMessage(String.valueOf(ChatUtil.Gi) + "Crit trail applied");
/*     */       }
/*  90 */       else if (args[0].equalsIgnoreCase("multidust")) {
/*  91 */         User user = this.core.getUser(player.getUniqueId());
/*     */ 
/*     */         
/*  94 */         int strength = 5;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 100 */         player.sendMessage(String.valueOf(ChatUtil.Gi) + "Multi Coloured Dust trail applied");
/*     */       }
/* 102 */       else if (args[0].equalsIgnoreCase("off")) {
/* 103 */         User user = this.core.getUser(player.getUniqueId());
/*     */         
/* 105 */         user.setTrail(null);
/* 106 */         user.setTrailAmount(0);
/* 107 */         player.sendMessage(String.valueOf(ChatUtil.Gi) + "Trails off");
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 116 */       if (args.length == 3 && args[0].equals("secretcode123")) {
/* 117 */         User user = this.core.getUser(player.getUniqueId());
/*     */         
/* 119 */         ParticleEffect effect = null;
/* 120 */         int strength = 0;
/*     */         
/*     */         try {
/* 123 */           effect = ParticleEffect.valueOf(args[1]);
/* 124 */         } catch (IllegalArgumentException e) {
/* 125 */           player.sendMessage(ChatColor.RED + "Not a valid effect!");
/* 126 */           return true;
/*     */         } 
/*     */         
/*     */         try {
/* 130 */           strength = Integer.parseInt(args[2]);
/* 131 */         } catch (NumberFormatException e) {
/* 132 */           player.sendMessage(ChatColor.RED + "Not a number!");
/* 133 */           return true;
/*     */         } 
/*     */         
/* 136 */         if (effect != null) {
/* 137 */           user.setTrail(effect);
/* 138 */           user.setTrailAmount(strength);
/*     */         } 
/*     */         
/* 141 */         return true;
/*     */       } 
/* 143 */       player.sendMessage(ChatColor.RED + "Invalid Usage: /trail <trailname>\nTrail Types:\n" + this.trails);
/* 144 */       return true;
/*     */     } 
/*     */     
/* 147 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\premium\CommandTrail.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */