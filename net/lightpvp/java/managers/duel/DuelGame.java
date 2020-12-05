/*     */ package net.lightpvp.java.managers.duel;
/*     */ 
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DuelGame
/*     */   extends BukkitRunnable
/*     */ {
/*     */   private DuelManager duelManager;
/*     */   private Player player1;
/*     */   private Player player2;
/*     */   private DuelArena arena;
/*     */   private DuelLoadout loadout;
/*     */   private DuelPotion potion;
/*  24 */   private int countdown = 3;
/*     */   private boolean move = false;
/*     */   
/*     */   public DuelGame(DuelManager duelManager, Player player1, Player player2, DuelArena arena, DuelLoadout loadout, DuelPotion potion) {
/*  28 */     this.duelManager = duelManager;
/*     */     
/*  30 */     this.player1 = player1;
/*  31 */     this.player2 = player2;
/*     */     
/*  33 */     this.arena = arena;
/*  34 */     this.loadout = loadout;
/*  35 */     this.potion = potion;
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/*  40 */     this.player1.teleport(this.arena.getSpawn1());
/*  41 */     this.player2.teleport(this.arena.getSpawn2());
/*     */     
/*  43 */     this.player1.getInventory().setContents(this.loadout.getContent());
/*  44 */     this.player2.getInventory().setContents(this.loadout.getContent());
/*     */     
/*  46 */     this.player1.getInventory().setArmorContents(this.loadout.getArmor());
/*  47 */     this.player2.getInventory().setArmorContents(this.loadout.getArmor());
/*     */     
/*  49 */     this.player1.updateInventory();
/*  50 */     this.player2.updateInventory();
/*     */     
/*  52 */     if (this.potion.getPotionEffect() != null) {
/*  53 */       this.player1.addPotionEffect(this.potion.getPotionEffect());
/*  54 */       this.player2.addPotionEffect(this.potion.getPotionEffect());
/*     */     } 
/*     */     
/*  57 */     (new BukkitRunnable()
/*     */       {
/*     */         public void run()
/*     */         {
/*  61 */           DuelGame.this.player1.showPlayer(DuelGame.this.player2);
/*  62 */           DuelGame.this.player2.showPlayer(DuelGame.this.player1);
/*     */         }
/*  64 */       }).runTaskLater((Plugin)this.duelManager.getCore(), 10L);
/*     */     
/*  66 */     runTaskTimer((Plugin)this.duelManager.getCore(), 20L, 20L);
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*  71 */     if (this.countdown <= 0) {
/*  72 */       sendMessage(String.valueOf(ChatUtil.Gi) + "Fight!");
/*  73 */       playSound(Sound.ANVIL_LAND);
/*  74 */       this.move = true;
/*  75 */       cancel();
/*     */     } else {
/*  77 */       sendMessage(ChatColor.GRAY + "Duel is starting in " + ChatUtil.Gi + this.countdown + ChatColor.GRAY + " seconds!");
/*  78 */       playSound(Sound.NOTE_PLING);
/*  79 */       this.countdown--;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendMessage(String message) {
/*  85 */     this.player1.sendMessage(message);
/*  86 */     this.player2.sendMessage(message);
/*     */   }
/*     */   
/*     */   private void playSound(Sound sound) {
/*  90 */     this.player1.playSound(this.player1.getLocation(), sound, 1.0F, 1.0F);
/*  91 */     this.player2.playSound(this.player2.getLocation(), sound, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public Player getPlayer1() {
/*  95 */     return this.player1;
/*     */   }
/*     */   
/*     */   public Player getPlayer2() {
/*  99 */     return this.player2;
/*     */   }
/*     */   
/*     */   public boolean canMove() {
/* 103 */     return this.move;
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\duel\DuelGame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */