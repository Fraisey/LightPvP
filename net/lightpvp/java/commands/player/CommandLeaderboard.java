/*     */ package net.lightpvp.java.commands.player;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import net.lightpvp.java.Core;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ 
/*     */ 
/*     */ public class CommandLeaderboard
/*     */   implements CommandExecutor
/*     */ {
/*     */   private Core core;
/*     */   
/*     */   public CommandLeaderboard(Core core) {
/*  25 */     this.core = core;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/*  30 */     if (!(sender instanceof Player))
/*  31 */       return false; 
/*  32 */     Player player = (Player)sender;
/*     */     
/*  34 */     Inventory iv = Bukkit.createInventory(null, 9, "Leaderboards");
/*     */ 
/*     */ 
/*     */     
/*  38 */     LinkedHashMap<String, Integer> leaderboardCoins = this.core.getPlayerStatsManager().getLeaderboardInt("coins", 8);
/*     */     
/*  40 */     ItemStack isCoins = new ItemStack(Material.BLAZE_POWDER);
/*  41 */     ItemMeta isCoinsMeta = isCoins.getItemMeta();
/*  42 */     isCoinsMeta.setDisplayName(ChatColor.GOLD + "Coins");
/*  43 */     int indexCoins = 1;
/*  44 */     List<String> loreCoins = new ArrayList<>();
/*  45 */     for (String key : leaderboardCoins.keySet()) {
/*  46 */       loreCoins.add(ChatColor.GRAY + indexCoins + ". " + key + " - " + leaderboardCoins.get(key) + " coins");
/*  47 */       indexCoins++;
/*     */     } 
/*  49 */     isCoinsMeta.setLore(loreCoins);
/*  50 */     isCoins.setItemMeta(isCoinsMeta);
/*     */     
/*  52 */     iv.setItem(1, isCoins);
/*     */ 
/*     */ 
/*     */     
/*  56 */     LinkedHashMap<String, Integer> leaderboardKills = this.core.getPlayerStatsManager().getLeaderboardInt("kills", 8);
/*     */     
/*  58 */     ItemStack isKills = new ItemStack(Material.BLAZE_POWDER);
/*  59 */     ItemMeta isKillsMeta = isKills.getItemMeta();
/*  60 */     isKillsMeta.setDisplayName(ChatColor.GOLD + "Kills");
/*  61 */     int indexKills = 1;
/*  62 */     List<String> loreKills = new ArrayList<>();
/*  63 */     for (String key : leaderboardKills.keySet()) {
/*  64 */       loreKills.add(ChatColor.GRAY + indexKills + ". " + key + " - " + leaderboardKills.get(key) + " kills");
/*  65 */       indexKills++;
/*     */     } 
/*  67 */     isKillsMeta.setLore(loreKills);
/*  68 */     isKills.setItemMeta(isKillsMeta);
/*     */     
/*  70 */     iv.setItem(3, isKills);
/*     */ 
/*     */ 
/*     */     
/*  74 */     LinkedHashMap<String, Integer> leaderboardMaxKillstreak = this.core.getPlayerStatsManager().getLeaderboardInt("maxkillstreak", 8);
/*     */     
/*  76 */     ItemStack isMaxKillstreak = new ItemStack(Material.BLAZE_POWDER);
/*  77 */     ItemMeta isMaxKillstreakMeta = isMaxKillstreak.getItemMeta();
/*  78 */     isMaxKillstreakMeta.setDisplayName(ChatColor.GOLD + "Max Killstreak");
/*  79 */     int indexMaxKillstreak = 1;
/*  80 */     List<String> loreMaxKillstreak = new ArrayList<>();
/*  81 */     for (String key : leaderboardMaxKillstreak.keySet()) {
/*  82 */       loreMaxKillstreak.add(ChatColor.GRAY + indexMaxKillstreak + ". " + key + " - " + leaderboardMaxKillstreak.get(key) + " killstreak");
/*  83 */       indexMaxKillstreak++;
/*     */     } 
/*  85 */     isMaxKillstreakMeta.setLore(loreMaxKillstreak);
/*  86 */     isMaxKillstreak.setItemMeta(isMaxKillstreakMeta);
/*     */     
/*  88 */     iv.setItem(5, isMaxKillstreak);
/*     */     
/*  90 */     LinkedHashMap<String, Integer> leaderboard1v1Wins = this.core.getPlayerStatsManager().getLeaderboardInt("duelwins", 8);
/*     */     
/*  92 */     ItemStack is1v1Wins = new ItemStack(Material.BLAZE_POWDER);
/*  93 */     ItemMeta is1v1WinsMeta = is1v1Wins.getItemMeta();
/*  94 */     is1v1WinsMeta.setDisplayName(ChatColor.GOLD + "1v1 Wins");
/*  95 */     int index1v1Wins = 1;
/*  96 */     List<String> lore1v1Wins = new ArrayList<>();
/*  97 */     for (String key : leaderboard1v1Wins.keySet()) {
/*  98 */       lore1v1Wins.add(ChatColor.GRAY + index1v1Wins + ". " + key + " - " + leaderboard1v1Wins.get(key) + " 1v1 wins");
/*  99 */       index1v1Wins++;
/*     */     } 
/* 101 */     is1v1WinsMeta.setLore(lore1v1Wins);
/* 102 */     is1v1Wins.setItemMeta(is1v1WinsMeta);
/*     */     
/* 104 */     iv.setItem(7, is1v1Wins);
/*     */ 
/*     */     
/* 107 */     player.openInventory(iv);
/*     */     
/* 109 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\commands\player\CommandLeaderboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */