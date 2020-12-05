/*     */ package net.lightpvp.java;
/*     */ 
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Set;
/*     */ import me.confuser.barapi.BarAPI;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import org.bukkit.ChatColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InfoBar
/*     */ {
/*     */   private Core core;
/*  16 */   private String stage = "WELCOME";
/*     */ 
/*     */   
/*  19 */   private String scrollColor = "";
/*  20 */   private String scrollMessage = ChatColor.RED + "~ Hello! " + ChatColor.RED + "Thank you for checking out " + ChatColor.DARK_RED + "Light" + ChatColor.GOLD + "PvP" + ChatColor.RED + " we offer alot of " + ChatColor.GOLD + "cool features" + ChatColor.RED + " such as a good" + ChatColor.GOLD + " Event System." + ChatColor.RED + " We hope to see you more on this server! ~";
/*  21 */   private int scrollStartpoint = 0;
/*  22 */   private int scrollEndpoint = 0;
/*  23 */   private final int scrollCap = 60;
/*     */ 
/*     */   
/*  26 */   private String blinkMessage1 = ChatColor.GREEN + ChatColor.BOLD + "~ " + ChatColor.RED + "LightPvP - " + ChatUtil.LPip + ChatColor.GREEN + ChatColor.BOLD + " ~";
/*  27 */   private String blinkMessage2 = ChatColor.GREEN + ChatColor.BOLD + "~" + ChatColor.GOLD + "LightPvP - " + ChatUtil.LPip + ChatColor.GREEN + ChatColor.BOLD + "~";
/*  28 */   private int blinkFrame = 0;
/*  29 */   private final int blinkMaxFrames = 60;
/*     */ 
/*     */   
/*  32 */   private String[] leaderboardkillsMessage = new String[4];
/*  33 */   private int leaderboardkillsFrame = 0;
/*  34 */   private final int leaderboardkillsMaxFrame = 50;
/*     */   
/*     */   public InfoBar(Core core) {
/*  37 */     this.core = core;
/*     */     
/*  39 */     this.leaderboardkillsMessage[0] = String.valueOf(ChatUtil.Gi) + "Empty!";
/*  40 */     this.leaderboardkillsMessage[1] = String.valueOf(ChatUtil.Gi) + "Empty!";
/*  41 */     this.leaderboardkillsMessage[2] = String.valueOf(ChatUtil.Gi) + "Empty!";
/*  42 */     this.leaderboardkillsMessage[3] = ChatColor.GOLD + ChatColor.BOLD + "Leaderboard (" + ChatColor.GREEN + ChatColor.BOLD + "Kills" + ChatColor.GOLD + ChatColor.BOLD + "):";
/*  43 */     refreshMessages();
/*     */   }
/*     */   
/*     */   public void update() { String str;
/*  47 */     switch ((str = this.stage).hashCode()) { case 2251950: if (!str.equals("INFO")) {
/*     */           break;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  81 */         if (this.blinkFrame < 60) {
/*     */           
/*  83 */           String submessage = (this.blinkFrame % 2 == 0) ? this.blinkMessage1 : this.blinkMessage2;
/*  84 */           float percent = 100.0F - this.blinkFrame / 60.0F * 100.0F;
/*     */           
/*  86 */           BarAPI.setMessage(submessage, percent);
/*     */           
/*  88 */           this.blinkFrame++; break;
/*     */         } 
/*  90 */         this.blinkFrame = 0;
/*  91 */         this.stage = "LEADERBOARDKILLS"; break;
/*     */       case 771215224:
/*     */         if (!str.equals("LEADERBOARDKILLS"))
/*     */           break; 
/*  95 */         if (this.leaderboardkillsFrame < 50) {
/*     */           
/*  97 */           float percent = 100.0F - this.leaderboardkillsFrame / 50.0F * 100.0F;
/*  98 */           int index = (int)(4.0F - this.leaderboardkillsFrame / 50.0F * 4.0F);
/*  99 */           if (index > 3) {
/* 100 */             index = 3;
/* 101 */           } else if (index < 0) {
/* 102 */             index = 0;
/*     */           } 
/* 104 */           BarAPI.setMessage(this.leaderboardkillsMessage[index], percent);
/*     */           
/* 106 */           this.leaderboardkillsFrame++; break;
/*     */         } 
/* 108 */         this.leaderboardkillsFrame = 0;
/* 109 */         this.stage = "WELCOME";
/* 110 */         refreshMessages(); break;
/*     */       case 1951082306:
/*     */         if (!str.equals("WELCOME"))
/*     */           break;  if (this.scrollMessage.length() - this.scrollStartpoint >= 60) { this.scrollEndpoint = this.scrollStartpoint + 60; if (this.scrollEndpoint > this.scrollMessage.length())
/*     */             this.scrollEndpoint = this.scrollMessage.length();  String subMessage = ""; if (this.scrollStartpoint >= 1 && this.scrollMessage.charAt(this.scrollStartpoint - 1) == '§') { this.scrollColor = ChatColor.translateAlternateColorCodes('&', "&" + String.valueOf(this.scrollMessage.charAt(this.scrollStartpoint))); this.scrollStartpoint++; }  subMessage = String.valueOf(subMessage) + this.scrollColor; if (this.scrollMessage.charAt(this.scrollEndpoint - 1) == '§') { subMessage = String.valueOf(subMessage) + this.scrollMessage.substring(this.scrollStartpoint, this.scrollEndpoint - 1); } else { subMessage = String.valueOf(subMessage) + this.scrollMessage.substring(this.scrollStartpoint, this.scrollEndpoint); }
/*     */            BarAPI.setMessage(subMessage); this.scrollStartpoint++; break; }
/*     */          this.scrollStartpoint = 0; this.stage = "INFO"; break; }
/* 117 */      } private void refreshMessages() { LinkedHashMap<String, Integer> topKills = this.core.getPlayerStatsManager().getLeaderboardInt("kills", 3);
/* 118 */     Set<String> keys = topKills.keySet();
/* 119 */     int index = 0;
/* 120 */     for (String key : keys) {
/* 121 */       int value = ((Integer)topKills.get(key)).intValue();
/* 122 */       this.leaderboardkillsMessage[index] = ChatColor.DARK_RED + ChatColor.BOLD + String.valueOf(index + 1) + ". " + ChatColor.GOLD + ChatColor.BOLD + key + " - " + value + ChatColor.BLUE + ChatColor.BOLD + " Kills";
/* 123 */       index++;
/*     */     }  }
/*     */ 
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\InfoBar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */