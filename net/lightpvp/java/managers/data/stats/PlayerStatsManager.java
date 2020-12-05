/*     */ package net.lightpvp.java.managers.data.stats;
/*     */ 
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.lightpvp.java.Core;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerStatsManager
/*     */ {
/*     */   private Core core;
/*     */   
/*     */   public PlayerStatsManager(Core core) {
/*  25 */     this.core = core;
/*     */   }
/*     */   
/*     */   public boolean handlePlayerJoin(Player player) {
/*  29 */     if (!containsPlayer(player.getUniqueId())) {
/*  30 */       addPlayer(player);
/*  31 */       return true;
/*     */     } 
/*  33 */     updatePlayer(player);
/*  34 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsPlayer(UUID uuid) {
/*  39 */     ResultSet rs = this.core.getLocalDatabase().executeQuery("SELECT name FROM players WHERE uuid = '" + uuid + "'");
/*     */     try {
/*  41 */       return rs.next();
/*  42 */     } catch (SQLException e) {
/*  43 */       e.printStackTrace();
/*     */     } finally {
/*     */       try {
/*  46 */         rs.close();
/*  47 */       } catch (SQLException e) {
/*  48 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/*  52 */     return false;
/*     */   }
/*     */   
/*     */   public void addPlayer(Player player) {
/*  56 */     this.core.getLocalDatabase().updateQuery("INSERT INTO players(uuid, name, ipaddress) VALUES('" + player.getUniqueId() + "','" + player.getName() + "','" + player.getAddress().getHostString() + "')");
/*     */   }
/*     */   
/*     */   public void updatePlayer(Player player) {
/*  60 */     this.core.getLocalDatabase().updateQuery("UPDATE players SET name = '" + player.getName() + "', ipaddress = '" + player.getAddress().getHostString() + "' WHERE uuid = '" + player.getUniqueId().toString() + "'");
/*  61 */     setString(player.getUniqueId(), "ipaddress", player.getAddress().getHostString());
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInt(UUID uuid, String value) {
/*  66 */     ResultSet rs = this.core.getLocalDatabase().executeQuery("SELECT " + value + " FROM players WHERE uuid = '" + uuid.toString() + "'");
/*     */     try {
/*  68 */       if (rs.next()) {
/*  69 */         return rs.getInt(1);
/*     */       }
/*  71 */     } catch (SQLException e) {
/*  72 */       e.printStackTrace();
/*     */     } finally {
/*     */       try {
/*  75 */         rs.close();
/*  76 */       } catch (SQLException e) {
/*  77 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/*  81 */     return 0;
/*     */   }
/*     */   
/*     */   public void setInt(UUID uuid, PlayerStat stat, int newValue) {
/*  85 */     Player updatedPlayer = null;
/*  86 */     switch (stat) {
/*     */       case MAXKILLSTREAK:
/*  88 */         this.core.getLocalDatabase().updateQuery("UPDATE players SET maxkillstreak = " + newValue + " WHERE uuid = '" + uuid.toString() + "'");
/*  89 */         if ((updatedPlayer = Core.getNameFromUUID(uuid)) != null) {
/*  90 */           updatedPlayer.getScoreboard().getObjective("stats").getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "Max KS:")).setScore(newValue);
/*     */         }
/*     */         break;
/*     */       case null:
/*  94 */         this.core.getLocalDatabase().updateQuery("UPDATE players SET coins = " + newValue + " WHERE uuid = '" + uuid.toString() + "'");
/*  95 */         if ((updatedPlayer = Core.getNameFromUUID(uuid)) != null) {
/*  96 */           updatedPlayer.getScoreboard().getObjective("stats").getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "Coins:")).setScore(newValue);
/*     */         }
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void incrementInt(UUID uuid, int amount, PlayerStat stat) {
/* 105 */     Player updatedPlayer = null;
/* 106 */     switch (stat) {
/*     */       case null:
/* 108 */         this.core.getLocalDatabase().updateQuery("UPDATE players SET coins = coins + " + amount + " WHERE uuid = '" + uuid.toString() + "'");
/* 109 */         if ((updatedPlayer = Core.getNameFromUUID(uuid)) != null) {
/* 110 */           int coins = updatedPlayer.getScoreboard().getObjective("stats").getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "Coins:")).getScore();
/* 111 */           updatedPlayer.getScoreboard().getObjective("stats").getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "Coins:")).setScore(coins + amount);
/*     */         } 
/*     */         break;
/*     */       case KILLS:
/* 115 */         this.core.getLocalDatabase().updateQuery("UPDATE players SET kills = kills + " + amount + " WHERE uuid = '" + uuid.toString() + "'");
/* 116 */         if ((updatedPlayer = Core.getNameFromUUID(uuid)) != null) {
/* 117 */           int kills = updatedPlayer.getScoreboard().getObjective("stats").getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "Kills:")).getScore();
/* 118 */           updatedPlayer.getScoreboard().getObjective("stats").getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "Kills:")).setScore(kills + amount);
/*     */         } 
/*     */         break;
/*     */       case DEATHS:
/* 122 */         this.core.getLocalDatabase().updateQuery("UPDATE players SET deaths = deaths + " + amount + " WHERE uuid = '" + uuid.toString() + "'");
/* 123 */         if ((updatedPlayer = Core.getNameFromUUID(uuid)) != null) {
/* 124 */           int deaths = updatedPlayer.getScoreboard().getObjective("stats").getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "Deaths:")).getScore();
/* 125 */           updatedPlayer.getScoreboard().getObjective("stats").getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "Deaths:")).setScore(deaths + amount);
/*     */         } 
/*     */         break;
/*     */       case DUELWINS:
/* 129 */         this.core.getLocalDatabase().updateQuery("UPDATE players SET duelwins = duelwins + " + amount + " WHERE uuid = '" + uuid.toString() + "'");
/* 130 */         if ((updatedPlayer = Core.getNameFromUUID(uuid)) != null) {
/* 131 */           int duelwins = updatedPlayer.getScoreboard().getObjective("stats").getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "1v1 Wins:")).getScore();
/* 132 */           updatedPlayer.getScoreboard().getObjective("stats").getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "1v1 Wins:")).setScore(duelwins + amount);
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void decrementInt(UUID uuid, int amount, PlayerStat stat) {
/* 141 */     Player updatedPlayer = null;
/* 142 */     switch (stat) {
/*     */       case null:
/* 144 */         this.core.getLocalDatabase().updateQuery("UPDATE players SET coins = coins - " + amount + " WHERE uuid = '" + uuid.toString() + "'");
/* 145 */         if ((updatedPlayer = Core.getNameFromUUID(uuid)) != null) {
/* 146 */           int coins = updatedPlayer.getScoreboard().getObjective("stats").getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "Coins:")).getScore();
/* 147 */           updatedPlayer.getScoreboard().getObjective("stats").getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "Coins:")).setScore(coins - amount);
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getString(UUID uuid, String value) {
/* 157 */     ResultSet rs = this.core.getLocalDatabase().executeQuery("SELECT " + value + " FROM players WHERE uuid = '" + uuid.toString() + "'");
/*     */     try {
/* 159 */       if (rs.next()) {
/* 160 */         return rs.getString(1);
/*     */       }
/* 162 */     } catch (SQLException e) {
/* 163 */       e.printStackTrace();
/*     */     } finally {
/*     */       try {
/* 166 */         rs.close();
/* 167 */       } catch (SQLException e) {
/* 168 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/* 171 */     return null;
/*     */   }
/*     */   
/*     */   public void setString(UUID uuid, String value, String newValue) {
/* 175 */     this.core.getLocalDatabase().updateQuery("UPDATE players SET " + value + " = '" + newValue + "' WHERE uuid = '" + uuid.toString() + "'");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUUIDFromName(String name) {
/* 183 */     ResultSet rs = this.core.getLocalDatabase().executeQuery("SELECT uuid FROM players WHERE name = '" + name + "'");
/*     */     try {
/* 185 */       if (rs.next()) {
/* 186 */         String output = rs.getString(1);
/* 187 */         rs.close();
/* 188 */         return output;
/*     */       } 
/* 190 */     } catch (SQLException e) {
/* 191 */       e.printStackTrace();
/*     */     } finally {
/*     */       try {
/* 194 */         rs.close();
/* 195 */       } catch (SQLException e) {
/* 196 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/* 199 */     return null;
/*     */   }
/*     */   
/*     */   public List<String> getNamesFromIP(String ipaddress) {
/* 203 */     List<String> output = new ArrayList<>();
/* 204 */     ResultSet rs = this.core.getLocalDatabase().executeQuery("SELECT name FROM players WHERE ipaddress = '" + ipaddress + "'");
/*     */     try {
/* 206 */       while (rs.next()) {
/* 207 */         output.add(rs.getString(1));
/*     */       }
/* 209 */     } catch (SQLException e) {
/* 210 */       e.printStackTrace();
/*     */     } finally {
/*     */       try {
/* 213 */         rs.close();
/* 214 */       } catch (SQLException e) {
/* 215 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/* 218 */     return output.isEmpty() ? null : output;
/*     */   }
/*     */   
/*     */   public String getIPFromName(String name) {
/* 222 */     ResultSet rs = this.core.getLocalDatabase().executeQuery("SELECT ipaddress FROM players WHERE name = '" + name + "'");
/*     */     try {
/* 224 */       if (rs.next()) {
/* 225 */         String output = rs.getString(1);
/* 226 */         rs.close();
/* 227 */         return output;
/*     */       } 
/* 229 */     } catch (SQLException e) {
/* 230 */       e.printStackTrace();
/*     */     } finally {
/*     */       try {
/* 233 */         rs.close();
/* 234 */       } catch (SQLException e) {
/* 235 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/* 238 */     return null;
/*     */   }
/*     */   
/*     */   public LinkedHashMap<String, Integer> getLeaderboardInt(String value, int limit) {
/* 242 */     LinkedHashMap<String, Integer> output = new LinkedHashMap<>(limit);
/*     */     
/* 244 */     ResultSet rs = this.core.getLocalDatabase().executeQuery("SELECT name," + value + " FROM players ORDER BY " + value + " DESC LIMIT 0," + limit);
/*     */     try {
/* 246 */       for (int i = 0; i < limit; i++) {
/* 247 */         if (rs.next()) {
/* 248 */           output.put(rs.getString(1), Integer.valueOf(rs.getInt(2)));
/*     */         }
/*     */       } 
/* 251 */       return output;
/* 252 */     } catch (SQLException e) {
/* 253 */       e.printStackTrace();
/*     */     } finally {
/*     */       try {
/* 256 */         rs.close();
/* 257 */       } catch (SQLException e) {
/* 258 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 262 */     return null;
/*     */   }
/*     */   
/*     */   public ItemStack[] getAllPlayersInventory(int currentPage) {
/* 266 */     ItemStack[] content = new ItemStack[36];
/*     */     
/* 268 */     int playerCount = getPlayerCount();
/* 269 */     int maximumPage = (int)Math.ceil((playerCount / 27.0F));
/*     */     
/* 271 */     if (currentPage > maximumPage) {
/* 272 */       currentPage = 1;
/* 273 */     } else if (currentPage < 1) {
/* 274 */       currentPage = maximumPage;
/*     */     } 
/*     */     
/* 277 */     ItemStack back = new ItemStack(Material.CARPET);
/* 278 */     ItemMeta backMeta = back.getItemMeta();
/* 279 */     backMeta.setDisplayName(ChatColor.GREEN + "Back");
/* 280 */     backMeta.setLore(Arrays.asList(new String[] { "Players: " + playerCount, "Page: " + currentPage + " / " + maximumPage }));
/* 281 */     back.setItemMeta(backMeta);
/*     */     
/* 283 */     ItemStack next = new ItemStack(Material.CARPET);
/* 284 */     ItemMeta nextMeta = next.getItemMeta();
/* 285 */     nextMeta.setDisplayName(ChatColor.GREEN + "Next");
/* 286 */     nextMeta.setLore(Arrays.asList(new String[] { "Players: " + playerCount, "Page: " + currentPage + " / " + maximumPage }));
/* 287 */     next.setItemMeta(nextMeta);
/* 288 */     content[0] = back;
/* 289 */     content[8] = next;
/*     */     
/* 291 */     List<String> players = new ArrayList<>();
/* 292 */     ResultSet rs = this.core.getLocalDatabase().executeQuery("SELECT name FROM players LIMIT 27 OFFSET " + (27 * (currentPage - 1)));
/*     */     try {
/* 294 */       while (rs.next()) {
/* 295 */         players.add(rs.getString(1));
/*     */       }
/* 297 */     } catch (SQLException e) {
/* 298 */       e.printStackTrace();
/*     */     } finally {
/*     */       try {
/* 301 */         rs.close();
/* 302 */       } catch (SQLException e) {
/* 303 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 307 */     for (int i = 0; i < players.size(); i++) {
/* 308 */       ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
/* 309 */       ItemMeta headMeta = head.getItemMeta();
/* 310 */       headMeta.setDisplayName(players.get(i));
/* 311 */       headMeta.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Click for more information..." }));
/* 312 */       head.setItemMeta(headMeta);
/* 313 */       content[i + 9] = head;
/*     */     } 
/* 315 */     return content;
/*     */   }
/*     */   
/*     */   public int getPlayerCount() {
/* 319 */     ResultSet rs = this.core.getLocalDatabase().executeQuery("SELECT COUNT(*) FROM players");
/*     */     try {
/* 321 */       if (rs.next()) {
/* 322 */         return rs.getInt(1);
/*     */       }
/* 324 */     } catch (SQLException e) {
/* 325 */       e.printStackTrace();
/*     */     } finally {
/*     */       try {
/* 328 */         rs.close();
/* 329 */       } catch (SQLException e) {
/* 330 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/* 333 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\data\stats\PlayerStatsManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */