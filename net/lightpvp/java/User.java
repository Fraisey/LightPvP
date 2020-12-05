/*     */ package net.lightpvp.java;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.lightpvp.java.particles.ParticleEffect;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class User
/*     */ {
/*  18 */   private Player player = null;
/*     */   
/*  20 */   private String uuid = "";
/*     */   
/*     */   private boolean joinMessage = true;
/*     */   
/*  24 */   private String replyTo = "";
/*  25 */   private List<String> ignorePlayers = new ArrayList<>();
/*     */   
/*  27 */   private long muteExpiry = 0L;
/*  28 */   private long banExpiry = 0L;
/*     */   
/*  30 */   private int slotmachineCount = 0;
/*  31 */   private long resetSlotmachineCount = 0L;
/*     */   
/*  33 */   private List<String> specialKits = new ArrayList<>();
/*     */   
/*     */   private boolean spawn = false;
/*  36 */   private String kit = "";
/*  37 */   private int killstreak = 0;
/*  38 */   private String lastKit = "PvP";
/*  39 */   private String warp = "";
/*     */   
/*     */   private boolean spyMode = false;
/*     */   
/*  43 */   private long totalSecondsPlayed = 0L;
/*  44 */   private long lastSeen = 0L;
/*     */   
/*  46 */   private ParticleEffect trail = null;
/*  47 */   private int trailAmount = 1;
/*  48 */   private List<String> trails = new ArrayList<>();
/*     */   
/*  50 */   private long startTime = 0L;
/*     */   
/*  52 */   private long lastChatTimeStamp = 0L;
/*  53 */   private String cl_damager = "";
/*  54 */   private long cl_timestamp = 0L;
/*     */   private String resetKit;
/*     */   private ItemStack[] resetContent;
/*     */   private ItemStack[] resetArmour;
/*  58 */   private String tradeInvite = "";
/*     */   
/*  60 */   private int clicks = 0;
/*  61 */   private long resetClicksCount = 0L;
/*     */   
/*  63 */   private int forcefieldWarnings = 0;
/*  64 */   private long resetForcefieldWarningsCount = 0L;
/*     */   
/*  66 */   private double prevYVelocity = 0.0D;
/*  67 */   private double prevYChange = 0.0D;
/*     */   
/*  69 */   private int flyWarnings = 0;
/*  70 */   private long resetFlyWarningsCount = 0L;
/*     */   
/*     */   public User(String uuid) {
/*  73 */     this.uuid = uuid;
/*  74 */     load(uuid);
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(String uuid) {
/*  79 */     this.startTime = System.currentTimeMillis();
/*     */     
/*  81 */     File userConfigFile = new File("plugins/Core/users/" + File.separator + uuid + ".yml");
/*     */     
/*  83 */     if (!userConfigFile.exists()) {
/*     */       try {
/*  85 */         userConfigFile.createNewFile();
/*  86 */       } catch (IOException e) {
/*  87 */         e.printStackTrace();
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*  92 */     YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(userConfigFile);
/*     */     
/*  94 */     if (!yamlConfiguration.contains("data.joinmessage")) {
/*  95 */       yamlConfiguration.set("data.joinmessage", Boolean.valueOf(true));
/*     */     }
/*  97 */     this.joinMessage = yamlConfiguration.getBoolean("data.joinmessage");
/*     */     
/*  99 */     if (!yamlConfiguration.contains("data.replyto")) {
/* 100 */       yamlConfiguration.set("data.replyto", Boolean.valueOf(true));
/*     */     }
/* 102 */     this.replyTo = yamlConfiguration.getString("data.replyto");
/*     */     
/* 104 */     if (!yamlConfiguration.contains("data.ignoreplayers")) {
/* 105 */       yamlConfiguration.set("data.ignoreplayers", new ArrayList());
/*     */     }
/* 107 */     this.ignorePlayers = yamlConfiguration.getStringList("data.ignoreplayers");
/*     */     
/* 109 */     if (!yamlConfiguration.contains("data.punishment.muteexpiry")) {
/* 110 */       yamlConfiguration.set("data.punishment.muteexpiry", Integer.valueOf(0));
/*     */     }
/* 112 */     this.muteExpiry = yamlConfiguration.getLong("data.punishment.muteexpiry");
/*     */     
/* 114 */     if (!yamlConfiguration.contains("data.punishment.banexpiry")) {
/* 115 */       yamlConfiguration.set("data.punishment.banexpiry", Integer.valueOf(0));
/*     */     }
/* 117 */     this.banExpiry = yamlConfiguration.getLong("data.punishment.banexpiry");
/*     */     
/* 119 */     if (!yamlConfiguration.contains("data.slotmachinecount")) {
/* 120 */       yamlConfiguration.set("data.slotmachinecount", Integer.valueOf(0));
/*     */     }
/* 122 */     this.slotmachineCount = yamlConfiguration.getInt("data.slotmachinecount");
/*     */     
/* 124 */     if (!yamlConfiguration.contains("data.resetslotmachinecount")) {
/* 125 */       yamlConfiguration.set("data.resetslotmachinecount", Integer.valueOf(0));
/*     */     }
/* 127 */     this.resetSlotmachineCount = yamlConfiguration.getLong("data.resetslotmachinecount");
/*     */     
/* 129 */     if (!yamlConfiguration.contains("data.specialkits")) {
/* 130 */       yamlConfiguration.set("data.specialkits", new ArrayList());
/*     */     }
/* 132 */     this.specialKits = yamlConfiguration.getStringList("data.specialkits");
/*     */     
/* 134 */     if (!yamlConfiguration.contains("data.spawn")) {
/* 135 */       yamlConfiguration.set("data.spawn", Boolean.valueOf(true));
/*     */     }
/* 137 */     this.spawn = yamlConfiguration.getBoolean("data.spawn");
/*     */     
/* 139 */     if (!yamlConfiguration.contains("data.kit")) {
/* 140 */       yamlConfiguration.set("data.kit", "");
/*     */     }
/* 142 */     this.kit = yamlConfiguration.getString("data.kit");
/*     */     
/* 144 */     if (!yamlConfiguration.contains("data.lastkit")) {
/* 145 */       yamlConfiguration.set("data.lastkit", "");
/*     */     }
/* 147 */     this.lastKit = yamlConfiguration.getString("data.lastkit");
/*     */     
/* 149 */     if (!yamlConfiguration.contains("data.warp")) {
/* 150 */       yamlConfiguration.set("data.warp", "");
/*     */     }
/* 152 */     this.warp = yamlConfiguration.getString("data.warp");
/*     */     
/* 154 */     if (!yamlConfiguration.contains("data.admin.spymode")) {
/* 155 */       yamlConfiguration.set("data.admin.spymode", Boolean.valueOf(false));
/*     */     }
/* 157 */     this.spyMode = yamlConfiguration.getBoolean("data.admin.spymode");
/*     */     
/* 159 */     if (!yamlConfiguration.contains("data.totalsecondsplayed")) {
/* 160 */       yamlConfiguration.set("data.totalsecondsplayed", Integer.valueOf(0));
/*     */     }
/* 162 */     this.totalSecondsPlayed = yamlConfiguration.getLong("data.totalsecondsplayed");
/*     */     
/* 164 */     if (!yamlConfiguration.contains("data.lastseen")) {
/* 165 */       yamlConfiguration.set("data.lastseen", Integer.valueOf(0));
/*     */     }
/* 167 */     this.lastSeen = yamlConfiguration.getLong("data.lastseen");
/*     */     
/* 169 */     if (!yamlConfiguration.contains("data.trail")) {
/* 170 */       yamlConfiguration.set("data.trail", "");
/*     */     }
/* 172 */     this.trail = !yamlConfiguration.getString("data.trail").isEmpty() ? ParticleEffect.valueOf(yamlConfiguration.getString("data.trail")) : null;
/*     */     
/* 174 */     if (!yamlConfiguration.contains("data.trailstrength")) {
/* 175 */       yamlConfiguration.set("data.trailstrength", Integer.valueOf(1));
/*     */     }
/* 177 */     this.trailAmount = yamlConfiguration.getInt("data.trailstrength");
/*     */     
/* 179 */     if (!yamlConfiguration.contains("data.trails")) {
/* 180 */       yamlConfiguration.set("data.trails", new ArrayList());
/*     */     }
/* 182 */     this.trails = yamlConfiguration.getStringList("data.trails");
/*     */     
/* 184 */     if (!yamlConfiguration.contains("data.killstreak")) {
/* 185 */       yamlConfiguration.set("data.killstreak", Integer.valueOf(0));
/*     */     }
/* 187 */     this.killstreak = yamlConfiguration.getInt("data.killstreak");
/*     */   }
/*     */   
/*     */   public void save() {
/* 191 */     File userConfigFile = new File("plugins/Core/users/" + File.separator + this.uuid + ".yml");
/* 192 */     YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(userConfigFile);
/*     */     
/* 194 */     yamlConfiguration.set("data.joinmessage", Boolean.valueOf(this.joinMessage));
/*     */     
/* 196 */     yamlConfiguration.set("data.replyto", this.replyTo);
/*     */     
/* 198 */     yamlConfiguration.set("data.ignoreplayers", this.ignorePlayers);
/*     */     
/* 200 */     yamlConfiguration.set("data.punishment.muteexpiry", Long.valueOf(this.muteExpiry));
/*     */     
/* 202 */     yamlConfiguration.set("data.punishment.banexpiry", Long.valueOf(this.banExpiry));
/*     */     
/* 204 */     yamlConfiguration.set("data.slotmachinecount", Integer.valueOf(this.slotmachineCount));
/*     */     
/* 206 */     yamlConfiguration.set("data.resetslotmachinecount", Long.valueOf(this.resetSlotmachineCount));
/*     */     
/* 208 */     yamlConfiguration.set("data.specialkits", this.specialKits);
/*     */     
/* 210 */     yamlConfiguration.set("data.spawn", Boolean.valueOf(this.spawn));
/*     */     
/* 212 */     yamlConfiguration.set("data.kit", this.kit);
/*     */     
/* 214 */     yamlConfiguration.set("data.lastkit", this.lastKit);
/*     */     
/* 216 */     yamlConfiguration.set("data.warp", this.warp);
/*     */     
/* 218 */     yamlConfiguration.set("data.admin.spymode", Boolean.valueOf(this.spyMode));
/*     */     
/* 220 */     yamlConfiguration.set("data.totalsecondsplayed", Long.valueOf(this.totalSecondsPlayed));
/*     */     
/* 222 */     yamlConfiguration.set("data.lastseen", Long.valueOf(this.lastSeen));
/*     */     
/* 224 */     yamlConfiguration.set("data.trail", (this.trail == null) ? "" : this.trail.name());
/*     */     
/* 226 */     yamlConfiguration.set("data.trailstrength", Integer.valueOf(this.trailAmount));
/*     */     
/* 228 */     yamlConfiguration.set("data.trails", this.trails);
/*     */     
/* 230 */     yamlConfiguration.set("data.killstreak", Integer.valueOf(this.killstreak));
/*     */     
/*     */     try {
/* 233 */       yamlConfiguration.save(userConfigFile);
/* 234 */     } catch (IOException e) {
/* 235 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void unload() {
/* 240 */     updateTotalSecondsPlayed();
/* 241 */     this.lastSeen = System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   public Player getPlayer() {
/* 245 */     return this.player;
/*     */   }
/*     */   
/*     */   public void setPlayer(Player player) {
/* 249 */     this.player = player;
/*     */   }
/*     */   
/*     */   public String getUniqueID() {
/* 253 */     return this.uuid;
/*     */   }
/*     */   
/*     */   public boolean isJoinMessage() {
/* 257 */     return this.joinMessage;
/*     */   }
/*     */   
/*     */   public void setJoinMessage(boolean joinMessage) {
/* 261 */     this.joinMessage = joinMessage;
/*     */   }
/*     */   
/*     */   public String getReplyTo() {
/* 265 */     return this.replyTo;
/*     */   }
/*     */   
/*     */   public void setReplyTo(UUID uuid) {
/* 269 */     this.replyTo = uuid.toString();
/*     */   }
/*     */   
/*     */   public List<String> getIgnorePlayers() {
/* 273 */     return this.ignorePlayers;
/*     */   }
/*     */   
/*     */   public boolean ignoresPlayer(Player player) {
/* 277 */     return this.ignorePlayers.contains(player.getUniqueId().toString());
/*     */   }
/*     */   
/*     */   public void ignorePlayer(Player player) {
/* 281 */     if (!this.ignorePlayers.contains(player.getUniqueId().toString())) {
/* 282 */       this.ignorePlayers.add(player.getUniqueId().toString());
/*     */     } else {
/* 284 */       this.ignorePlayers.remove(player.getUniqueId().toString());
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isMuted() {
/* 289 */     if (System.currentTimeMillis() >= this.muteExpiry) {
/* 290 */       return false;
/*     */     }
/* 292 */     return true;
/*     */   }
/*     */   
/*     */   public long getMutedExpiry() {
/* 296 */     return this.muteExpiry;
/*     */   }
/*     */   
/*     */   public void setMuted(long muteExpiry) {
/* 300 */     this.muteExpiry = muteExpiry;
/*     */   }
/*     */   
/*     */   public boolean isBanned() {
/* 304 */     if (this.banExpiry == -1L) {
/* 305 */       return true;
/*     */     }
/*     */     
/* 308 */     if (System.currentTimeMillis() >= this.banExpiry) {
/* 309 */       return false;
/*     */     }
/* 311 */     return true;
/*     */   }
/*     */   
/*     */   public long getBanExpiry() {
/* 315 */     return this.banExpiry;
/*     */   }
/*     */   
/*     */   public void setBanExpiry(long banExpiry) {
/* 319 */     this.banExpiry = banExpiry;
/*     */   }
/*     */   
/*     */   public int getSlotmachineCount() {
/* 323 */     return this.slotmachineCount;
/*     */   }
/*     */   
/*     */   public void setSlotmachineCount(int slotmachineCount) {
/* 327 */     this.slotmachineCount = slotmachineCount;
/*     */   }
/*     */   
/*     */   public void incrementSlotmachineCount() {
/* 331 */     this.slotmachineCount++;
/*     */   }
/*     */   
/*     */   public long getResetSlotmachineCount() {
/* 335 */     return this.resetSlotmachineCount;
/*     */   }
/*     */   
/*     */   public void setResetSlotmachineCount(long resetSlotmachineCount) {
/* 339 */     this.resetSlotmachineCount = resetSlotmachineCount;
/*     */   }
/*     */   
/*     */   public List<String> getSpecialKits() {
/* 343 */     return this.specialKits;
/*     */   }
/*     */   
/*     */   public boolean hasSpecialKit(String kit) {
/* 347 */     return this.specialKits.contains(kit);
/*     */   }
/*     */   
/*     */   public void addSpecialKit(String kit) {
/* 351 */     this.specialKits.add(kit);
/*     */   }
/*     */   
/*     */   public boolean getSpawn() {
/* 355 */     return this.spawn;
/*     */   }
/*     */   
/*     */   public void setSpawn(boolean spawn) {
/* 359 */     this.spawn = spawn;
/*     */   }
/*     */   
/*     */   public String getKit() {
/* 363 */     return this.kit;
/*     */   }
/*     */   
/*     */   public void setKit(String kit) {
/* 367 */     this.kit = kit;
/*     */   }
/*     */   
/*     */   public String getLastKit() {
/* 371 */     return this.lastKit;
/*     */   }
/*     */   
/*     */   public void setLastKit(String lastKit) {
/* 375 */     this.lastKit = lastKit;
/*     */   }
/*     */   
/*     */   public String getWarp() {
/* 379 */     return this.warp;
/*     */   }
/*     */   
/*     */   public void setWarp(String warp) {
/* 383 */     this.warp = warp;
/*     */   }
/*     */   
/*     */   public boolean isSpyMode() {
/* 387 */     return this.spyMode;
/*     */   }
/*     */   
/*     */   public void setSpyMode(boolean spyMode) {
/* 391 */     this.spyMode = spyMode;
/*     */   }
/*     */   
/*     */   public int getKillstreak() {
/* 395 */     return this.killstreak;
/*     */   }
/*     */   
/*     */   public void setKillstreak(int killstreak) {
/* 399 */     this.killstreak = killstreak;
/*     */   }
/*     */   
/*     */   public int incrementKillstreakAndGet() {
/* 403 */     this.killstreak++;
/* 404 */     return this.killstreak;
/*     */   }
/*     */   
/*     */   public long getTotalSecondsPlayed() {
/* 408 */     return this.totalSecondsPlayed + ((this.startTime != 0L) ? ((System.currentTimeMillis() - this.startTime) / 1000L) : 0L);
/*     */   }
/*     */   
/*     */   public void setTotalSecondsPlayed(long totalSecondsPlayed) {
/* 412 */     this.totalSecondsPlayed = totalSecondsPlayed;
/*     */   }
/*     */   
/*     */   private void updateTotalSecondsPlayed() {
/* 416 */     this.totalSecondsPlayed = getTotalSecondsPlayed();
/*     */   }
/*     */   
/*     */   public long getLastSeen() {
/* 420 */     return this.lastSeen;
/*     */   }
/*     */   
/*     */   public ParticleEffect getTrail() {
/* 424 */     return this.trail;
/*     */   }
/*     */   
/*     */   public void setTrail(ParticleEffect trail) {
/* 428 */     this.trail = trail;
/*     */   }
/*     */   
/*     */   public int getTrailAmount() {
/* 432 */     return this.trailAmount;
/*     */   }
/*     */   
/*     */   public void setTrailAmount(int trailAmount) {
/* 436 */     this.trailAmount = trailAmount;
/*     */   }
/*     */   
/*     */   public List<String> getTrails() {
/* 440 */     return this.trails;
/*     */   }
/*     */   
/*     */   public long getStartTime() {
/* 444 */     return this.startTime;
/*     */   }
/*     */   
/*     */   public long getLastChatTimeStamp() {
/* 448 */     return this.lastChatTimeStamp;
/*     */   }
/*     */   
/*     */   public void setLastChatTimeStamp(long lastChatTimeStamp) {
/* 452 */     this.lastChatTimeStamp = lastChatTimeStamp;
/*     */   }
/*     */   
/*     */   public void tag(Player damager) {
/* 456 */     this.cl_damager = damager.getName();
/* 457 */     this.cl_timestamp = System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   public void untag() {
/* 461 */     this.cl_damager = "";
/* 462 */     this.cl_timestamp = 0L;
/*     */   }
/*     */   
/*     */   public String isCombatLog() {
/* 466 */     int dif = (int)(System.currentTimeMillis() - this.cl_timestamp);
/* 467 */     if (dif < 12000) {
/* 468 */       return (this.cl_damager == null || this.cl_damager.isEmpty()) ? null : this.cl_damager;
/*     */     }
/* 470 */     return null;
/*     */   }
/*     */   
/*     */   public String getResetKit() {
/* 474 */     return this.resetKit;
/*     */   }
/*     */   
/*     */   public void setResetKit(String resetKit) {
/* 478 */     this.resetKit = resetKit;
/*     */   }
/*     */   
/*     */   public void resetResetKit() {
/* 482 */     this.resetKit = null;
/*     */   }
/*     */   
/*     */   public ItemStack[] getResetContent() {
/* 486 */     return this.resetContent;
/*     */   }
/*     */   
/*     */   public void setResetContent(ItemStack[] resetContent) {
/* 490 */     this.resetContent = resetContent;
/*     */   }
/*     */   
/*     */   public void resetResetContent() {
/* 494 */     this.resetContent = null;
/*     */   }
/*     */   
/*     */   public ItemStack[] getResetArmour() {
/* 498 */     return this.resetArmour;
/*     */   }
/*     */   
/*     */   public void setResetArmour(ItemStack[] resetArmour) {
/* 502 */     this.resetArmour = resetArmour;
/*     */   }
/*     */   
/*     */   public void resetResetArmour() {
/* 506 */     this.resetArmour = null;
/*     */   }
/*     */   
/*     */   public String getTradeInvite() {
/* 510 */     return this.tradeInvite;
/*     */   }
/*     */   
/*     */   public void setTradeInvite(String tradeInvite) {
/* 514 */     this.tradeInvite = tradeInvite;
/*     */   }
/*     */   
/*     */   public int getClicks() {
/* 518 */     return this.clicks;
/*     */   }
/*     */   
/*     */   public void incrementClicks() {
/* 522 */     if (System.currentTimeMillis() - this.resetClicksCount > 2000L || this.resetClicksCount == 0L) {
/* 523 */       this.resetClicksCount = System.currentTimeMillis();
/* 524 */       this.clicks = 0;
/*     */     } 
/*     */     
/* 527 */     this.clicks++;
/*     */   }
/*     */   
/*     */   public int getForcefieldWarnings() {
/* 531 */     return this.forcefieldWarnings;
/*     */   }
/*     */   
/*     */   public void incrementForcefieldWarnings() {
/* 535 */     if (System.currentTimeMillis() - this.resetForcefieldWarningsCount > 5000L || this.resetForcefieldWarningsCount == 0L) {
/* 536 */       this.resetForcefieldWarningsCount = System.currentTimeMillis();
/* 537 */       this.forcefieldWarnings = 0;
/*     */     } 
/*     */     
/* 540 */     this.forcefieldWarnings++;
/*     */   }
/*     */   
/*     */   public double getPrevYVelocity() {
/* 544 */     return this.prevYVelocity;
/*     */   }
/*     */   
/*     */   public void setPrevYVelocity(double prevYVelocity) {
/* 548 */     this.prevYVelocity = prevYVelocity;
/*     */   }
/*     */   
/*     */   public double getPrevYChange() {
/* 552 */     return this.prevYChange;
/*     */   }
/*     */   
/*     */   public void setPrevYChange(double prevYChange) {
/* 556 */     this.prevYChange = prevYChange;
/*     */   }
/*     */   
/*     */   public int getFlyWarnings() {
/* 560 */     return this.flyWarnings;
/*     */   }
/*     */   
/*     */   public void incrementFlyWarnings() {
/* 564 */     if (System.currentTimeMillis() - this.resetFlyWarningsCount > 3000L || this.resetFlyWarningsCount == 0L) {
/* 565 */       this.resetFlyWarningsCount = System.currentTimeMillis();
/* 566 */       this.flyWarnings = 0;
/*     */     } 
/*     */     
/* 569 */     this.flyWarnings++;
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\User.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */