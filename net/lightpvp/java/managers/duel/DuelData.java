/*     */ package net.lightpvp.java.managers.duel;
/*     */ 
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DuelData
/*     */ {
/*     */   private Player player;
/*     */   private boolean quickMatchup = false;
/*  11 */   private String invited = "";
/*     */ 
/*     */   
/*     */   private boolean active = false;
/*     */ 
/*     */   
/*  17 */   private DuelGame duel = null;
/*     */   
/*  19 */   private DuelArena arena = null;
/*  20 */   private DuelLoadout loadout = null;
/*     */   private boolean refills = false;
/*  22 */   private DuelPotion potion = null;
/*     */   
/*     */   public DuelData(Player player) {
/*  25 */     this.player = player;
/*     */   }
/*     */   
/*     */   public void reset() {
/*  29 */     this.quickMatchup = false;
/*     */     
/*  31 */     this.active = false;
/*  32 */     this.invited = "";
/*  33 */     this.duel = null;
/*     */   }
/*     */   
/*     */   public Player getPlayer() {
/*  37 */     return this.player;
/*     */   }
/*     */   
/*     */   public boolean isQuickMatchup() {
/*  41 */     return this.quickMatchup;
/*     */   }
/*     */   
/*     */   public void setQuickMatchup(boolean quickMatchup) {
/*  45 */     this.quickMatchup = quickMatchup;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInvited() {
/*  50 */     return this.invited;
/*     */   }
/*     */   
/*     */   public void setInvited(String invited) {
/*  54 */     this.invited = invited;
/*     */   }
/*     */   
/*     */   public boolean isActive() {
/*  58 */     return this.active;
/*     */   }
/*     */   
/*     */   public void setActive(boolean isActive) {
/*  62 */     this.active = isActive;
/*     */   }
/*     */ 
/*     */   
/*     */   public DuelGame getDuel() {
/*  67 */     return this.duel;
/*     */   }
/*     */   
/*     */   public void setDuel(DuelGame game) {
/*  71 */     this.duel = game;
/*     */   }
/*     */   
/*     */   public DuelArena getArena() {
/*  75 */     return this.arena;
/*     */   }
/*     */   
/*     */   public void setArena(DuelArena arena) {
/*  79 */     this.arena = arena;
/*     */   }
/*     */   
/*     */   public DuelLoadout getLoadout() {
/*  83 */     return this.loadout;
/*     */   }
/*     */   
/*     */   public void setLoadout(DuelLoadout loadout) {
/*  87 */     this.loadout = loadout;
/*     */   }
/*     */   
/*     */   public boolean isRefills() {
/*  91 */     return this.refills;
/*     */   }
/*     */   
/*     */   public void setRefills(boolean refills) {
/*  95 */     this.refills = refills;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DuelPotion getPotion() {
/* 101 */     return this.potion;
/*     */   }
/*     */   
/*     */   public void setPotion(DuelPotion potion) {
/* 105 */     this.potion = potion;
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\duel\DuelData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */