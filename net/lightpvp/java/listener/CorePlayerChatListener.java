/*     */ package net.lightpvp.java.listener;
/*     */ 
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import net.lightpvp.java.utils.RankUtil;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.player.AsyncPlayerChatEvent;
/*     */ import org.bukkit.event.player.PlayerChatEvent;
/*     */ 
/*     */ public class CorePlayerChatListener
/*     */   implements Listener
/*     */ {
/*     */   private Core core;
/*     */   
/*     */   public CorePlayerChatListener(Core core) {
/*  23 */     this.core = core;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onChat(AsyncPlayerChatEvent e) {
/*  28 */     for (Player on : Bukkit.getServer().getOnlinePlayers()) {
/*  29 */       if (!on.equals(e.getPlayer()) && 
/*  30 */         e.getMessage().contains(on.getName()))
/*     */       {
/*  32 */         on.playSound(on.getLocation(), Sound.NOTE_PLING, 10.0F, 1.0F);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.LOW)
/*     */   public void onPlayerChatEvent(PlayerChatEvent evt) {
/*  39 */     Player player = evt.getPlayer();
/*     */     
/*  41 */     User user = this.core.getUser(player.getUniqueId());
/*     */     
/*  43 */     if (user.isMuted()) {
/*  44 */       player.sendMessage(ChatColor.GRAY + "You are muted for " + ChatColor.GOLD + (int)((user.getMutedExpiry() - System.currentTimeMillis()) / 1000L / 60L + 1L) + ChatColor.GRAY + " more minutes!");
/*  45 */       evt.setCancelled(true);
/*     */       
/*     */       return;
/*     */     } 
/*  49 */     if (user.getLastChatTimeStamp() + 2000L >= System.currentTimeMillis() && !player.hasPermission("core.bypass")) {
/*  50 */       player.sendMessage(ChatColor.GRAY + "Don't spam the " + ChatUtil.Gi + "chat!");
/*  51 */       evt.setCancelled(true);
/*     */       
/*     */       return;
/*     */     } 
/*  55 */     String message = evt.getMessage();
/*     */     
/*  57 */     if (player.hasPermission("core.yellow")) {
/*  58 */       message = ChatColor.YELLOW + message;
/*     */     }
/*     */     
/*  61 */     int capital = 0;
/*  62 */     for (int i = 0; i < message.length(); i++) {
/*  63 */       if (Character.isUpperCase(message.charAt(i))) {
/*  64 */         capital++;
/*     */       }
/*     */     } 
/*  67 */     if (message.length() > 6 && message.length() - capital < message.length() - message.length() / 2 && !player.hasPermission("core.bypass")) {
/*  68 */       message = message.toLowerCase();
/*     */     }
/*     */     
/*  71 */     if (message.contains("%")) {
/*  72 */       message = message.replaceFirst("%", " percent");
/*  73 */       message = message.replaceAll("%", "");
/*     */     } 
/*     */     
/*  76 */     if (message.contains("<3")) {
/*  77 */       message = message.replaceFirst("<3", "❤");
/*  78 */       message = message.replaceAll("<3", "");
/*     */     } 
/*     */     
/*  81 */     if (message.equalsIgnoreCase("Who is Chemmic?")) {
/*  82 */       player.sendMessage(ChatColor.RED + "Chemmic is the developer!");
/*  83 */       message = message.replaceAll("Who is Chemmic?", "");
/*     */     } 
/*     */     
/*  86 */     if (message.equalsIgnoreCase("Whos Chemmic?")) {
/*  87 */       player.sendMessage(ChatColor.RED + "Chemmic is the developer!");
/*  88 */       message = message.replaceAll("Whos Chemmic?", "");
/*     */     } 
/*     */     
/*  91 */     if (message.equalsIgnoreCase("Who's Chemmic?")) {
/*  92 */       player.sendMessage(ChatColor.RED + "Chemmic is the developer!");
/*  93 */       message = message.replaceAll("Who's Chemmic?", "");
/*     */     } 
/*     */     
/*  96 */     if (message.equalsIgnoreCase("Who is Chemmic")) {
/*  97 */       player.sendMessage(ChatColor.RED + "Chemmic is the developer!");
/*  98 */       message = message.replaceAll("Who is Chemmic", "");
/*     */     } 
/*     */     
/* 101 */     if (message.equalsIgnoreCase("Who dafuq is Chemmic?")) {
/* 102 */       player.sendMessage(ChatColor.RED + "Chemmic is the developer!");
/* 103 */       message = message.replaceAll("Who dafuq is Chemmic?", "");
/*     */     } 
/*     */     
/* 106 */     if (message.equalsIgnoreCase("ez")) {
/* 107 */       message = message.replaceFirst("eZ", "Good Game!");
/* 108 */       message = message.replaceAll("eZ", "");
/*     */     } 
/*     */     
/* 111 */     if (message.contains("gay")) {
/* 112 */       message = message.replaceFirst("gay", "♂");
/* 113 */       message = message.replaceAll("gay", "");
/*     */     } 
/* 115 */     if (message.contains(";3")) {
/* 116 */       message = message.replaceFirst(";3", "❤");
/* 117 */       message = message.replaceAll(";3", "");
/*     */     } 
/* 119 */     if (message.contains(":3")) {
/* 120 */       message = message.replaceFirst(":3", "❤");
/* 121 */       message = message.replaceAll(":3", "");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     if (message.contains("#") && 
/* 128 */       player.hasPermission("core.hashtag")) {
/* 129 */       String[] words = message.split(" ");
/* 130 */       String newMessage = "";
/* 131 */       for (int j = 0; j < words.length; j++) {
/* 132 */         if (words[j].startsWith("#")) {
/* 133 */           newMessage = String.valueOf(newMessage) + ChatColor.GOLD + words[j] + ChatColor.WHITE + " ";
/*     */         } else {
/* 135 */           newMessage = String.valueOf(newMessage) + words[j] + " ";
/*     */         } 
/*     */       } 
/* 138 */       message = newMessage;
/*     */     } 
/*     */ 
/*     */     
/* 142 */     evt.setFormat(String.valueOf(RankUtil.getFullTag(player)) + ChatColor.GREEN + " ▶ " + ChatColor.WHITE + message);
/* 143 */     user.setLastChatTimeStamp(System.currentTimeMillis());
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\listener\CorePlayerChatListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */