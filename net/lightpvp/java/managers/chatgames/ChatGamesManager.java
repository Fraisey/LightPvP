/*     */ package net.lightpvp.java.managers.chatgames;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.managers.data.stats.PlayerStat;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import net.lightpvp.java.utils.RankUtil;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.player.PlayerChatEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChatGamesManager
/*     */   implements Listener
/*     */ {
/*     */   private Core core;
/*  32 */   private Random random = new Random();
/*     */   
/*  34 */   private List<Question> questions = new ArrayList<>();
/*     */   
/*  36 */   private Question currentQuestion = null;
/*     */   
/*     */   public ChatGamesManager(final Core core) {
/*  39 */     this.core = core;
/*     */     
/*  41 */     this.questions.add(new Question("What is 3.5+33*3/2", (new Answer()
/*     */           {
/*     */             public boolean isRightAnswer(String guess)
/*     */             {
/*  45 */               return guess.equalsIgnoreCase("53");
/*     */             }
/*     */ 
/*     */             
/*     */             public Answer load() {
/*  50 */               return this;
/*     */             }
/*  52 */           }).load()));
/*     */     
/*  54 */     this.questions.add(new Question("Name one of the admins", (new Answer()
/*     */           {
/*     */             public boolean isRightAnswer(String guess)
/*     */             {
/*  58 */               return !(!guess.equalsIgnoreCase("Chemmic") && !guess.equalsIgnoreCase("MrFraise_04") && !guess.equalsIgnoreCase("Pepsi_Princess"));
/*     */             }
/*     */ 
/*     */             
/*     */             public Answer load() {
/*  63 */               return this;
/*     */             }
/*  65 */           }).load()));
/*     */     
/*  67 */     this.questions.add(new Question("How many normal kits are there", (new Answer()
/*     */           {
/*     */             public boolean isRightAnswer(String guess)
/*     */             {
/*  71 */               return guess.equalsIgnoreCase(String.valueOf((core.getKitManager().getKitArray()).length));
/*     */             }
/*     */ 
/*     */             
/*     */             public Answer load() {
/*  76 */               return this;
/*     */             }
/*  78 */           }).load()));
/*     */     
/*  80 */     this.questions.add(new Question("How many special kits are there", (new Answer()
/*     */           {
/*     */             public boolean isRightAnswer(String guess)
/*     */             {
/*  84 */               return guess.equalsIgnoreCase(String.valueOf((core.getKitManager().getSpecialKitArray()).length));
/*     */             }
/*     */ 
/*     */             
/*     */             public Answer load() {
/*  89 */               return this;
/*     */             }
/*  91 */           }).load()));
/*     */     
/*  93 */     this.questions.add(new Question("How many events are there", (new Answer()
/*     */           {
/*     */             public boolean isRightAnswer(String guess)
/*     */             {
/*  97 */               return guess.equalsIgnoreCase(String.valueOf((core.getHostedEventManager().getHostedEvents()).length));
/*     */             }
/*     */ 
/*     */             
/*     */             public Answer load() {
/* 102 */               return this;
/*     */             }
/* 104 */           }).load()));
/*     */     
/* 106 */     this.questions.add(new Question("What is Runelink2's skin based on", (new Answer()
/*     */           {
/*     */             public boolean isRightAnswer(String guess)
/*     */             {
/* 110 */               return !(!guess.equalsIgnoreCase("nyan cat") && !guess.equalsIgnoreCase("nyancat"));
/*     */             }
/*     */ 
/*     */             
/*     */             public Answer load() {
/* 115 */               return this;
/*     */             }
/* 117 */           }).load()));
/*     */     
/* 119 */     this.questions.add(new Question("What is the square root of 10+20-5*3 ", (new Answer()
/*     */           {
/*     */             public boolean isRightAnswer(String guess)
/*     */             {
/* 123 */               return guess.equalsIgnoreCase("3,87");
/*     */             }
/*     */ 
/*     */             
/*     */             public Answer load() {
/* 128 */               return this;
/*     */             }
/* 130 */           }).load()));
/*     */     
/* 132 */     this.questions.add(new Question("Name one of the donator ranks", (new Answer()
/*     */           {
/*     */             public boolean isRightAnswer(String guess)
/*     */             {
/* 136 */               return !(!guess.equalsIgnoreCase("Elite") && !guess.equalsIgnoreCase("Veteran") && !guess.equalsIgnoreCase("Veteran+"));
/*     */             }
/*     */ 
/*     */             
/*     */             public Answer load() {
/* 141 */               return this;
/*     */             }
/* 143 */           }).load()));
/*     */     
/* 145 */     this.questions.add(new Question("With what server did LightPvP merge", (new Answer()
/*     */           {
/*     */             public boolean isRightAnswer(String guess)
/*     */             {
/* 149 */               return guess.equalsIgnoreCase("lostkits");
/*     */             }
/*     */ 
/*     */             
/*     */             public Answer load() {
/* 154 */               return this;
/*     */             }
/* 156 */           }).load()));
/*     */     
/* 158 */     this.questions.add(new Question("Name of the girl in the admin team", (new Answer()
/*     */           {
/*     */             public boolean isRightAnswer(String guess)
/*     */             {
/* 162 */               return !(!guess.equalsIgnoreCase("Pepsi_Princess") && !guess.equalsIgnoreCase("MrFraise_04"));
/*     */             }
/*     */ 
/*     */             
/*     */             public Answer load() {
/* 167 */               return this;
/*     */             }
/* 169 */           }).load()));
/*     */     
/* 171 */     this.questions.add(new Question("Why are goomonster3's eyes red (1 word)", (new Answer()
/*     */           {
/*     */             public boolean isRightAnswer(String guess)
/*     */             {
/* 175 */               return !(!guess.equalsIgnoreCase("weed") && !guess.equalsIgnoreCase("cannabis") && !guess.equalsIgnoreCase("dope") && !guess.equalsIgnoreCase("marijuana") && !guess.equalsIgnoreCase("herb") && !guess.equalsIgnoreCase("hemp") && !guess.equalsIgnoreCase("tea") && !guess.equalsIgnoreCase("ganja") && !guess.equalsIgnoreCase("bhang"));
/*     */             }
/*     */ 
/*     */             
/*     */             public Answer load() {
/* 180 */               return this;
/*     */             }
/* 182 */           }).load()));
/*     */     
/* 184 */     this.questions.add(new Question("How many kills does Runelink2 have", (new Answer()
/*     */           {
/*     */             public boolean isRightAnswer(String guess)
/*     */             {
/* 188 */               return guess.equalsIgnoreCase(String.valueOf(core.getPlayerStatsManager().getInt(UUID.fromString(core.getPlayerStatsManager().getUUIDFromName("Runelink2")), "kills")));
/*     */             }
/*     */ 
/*     */             
/*     */             public Answer load() {
/* 193 */               return this;
/*     */             }
/* 195 */           }).load()));
/*     */     
/* 197 */     this.questions.add(new Question("How many coins does goomonster3 have", (new Answer()
/*     */           {
/*     */             public boolean isRightAnswer(String guess)
/*     */             {
/* 201 */               return guess.equalsIgnoreCase(String.valueOf(core.getPlayerStatsManager().getInt(UUID.fromString(core.getPlayerStatsManager().getUUIDFromName("goomonster3")), "coins")));
/*     */             }
/*     */ 
/*     */             
/*     */             public Answer load() {
/* 206 */               return this;
/*     */             }
/* 208 */           }).load()));
/*     */     
/* 210 */     this.questions.add(new Question("What is the rarest special kit", (new Answer()
/*     */           {
/*     */             public boolean isRightAnswer(String guess)
/*     */             {
/* 214 */               return guess.equalsIgnoreCase("nosouper");
/*     */             }
/*     */ 
/*     */             
/*     */             public Answer load() {
/* 219 */               return this;
/*     */             }
/* 221 */           }).load()));
/*     */     
/* 223 */     this.questions.add(new Question("How many deaths does Chemmic have", (new Answer()
/*     */           {
/*     */             public boolean isRightAnswer(String guess)
/*     */             {
/* 227 */               return guess.equalsIgnoreCase(String.valueOf(core.getPlayerStatsManager().getInt(UUID.fromString(core.getPlayerStatsManager().getUUIDFromName("Chemmic")), "deaths")));
/*     */             }
/*     */ 
/*     */             
/*     */             public Answer load() {
/* 232 */               return this;
/*     */             }
/* 234 */           }).load()));
/*     */     
/* 236 */     this.questions.add(new Question("How many hot air balloons are on the main map", (new Answer()
/*     */           {
/*     */             public boolean isRightAnswer(String guess)
/*     */             {
/* 240 */               return !(!guess.equalsIgnoreCase("8") && !guess.equalsIgnoreCase("eight"));
/*     */             }
/*     */ 
/*     */             
/*     */             public Answer load() {
/* 245 */               return this;
/*     */             }
/* 247 */           }).load()));
/*     */     
/* 249 */     this.questions.add(new Question("Who is the latest donator", (new Answer()
/*     */           {
/* 251 */             long lastCache = System.currentTimeMillis();
/*     */             
/* 253 */             String cachedName = "";
/*     */ 
/*     */             
/*     */             public boolean isRightAnswer(String guess) {
/* 257 */               if (System.currentTimeMillis() - this.lastCache >= 120000L) {
/* 258 */                 this.lastCache = System.currentTimeMillis();
/*     */               }
/*     */ 
/*     */ 
/*     */               
/* 263 */               return guess.equalsIgnoreCase(this.cachedName);
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             public Answer load() {
/* 271 */               return this;
/*     */             }
/* 273 */           }).load()));
/*     */     
/* 275 */     this.questions.add(new Question("How many different types of brackets are there", (new Answer()
/*     */           {
/*     */             public boolean isRightAnswer(String guess)
/*     */             {
/* 279 */               return !(!guess.equalsIgnoreCase("5") && !guess.equalsIgnoreCase("five"));
/*     */             }
/*     */ 
/*     */             
/*     */             public Answer load() {
/* 284 */               return this;
/*     */             }
/* 286 */           }).load()));
/*     */     
/* 288 */     this.questions.add(new Question("What pattern gives you a special kit in the casino", (new Answer()
/*     */           {
/*     */             public boolean isRightAnswer(String guess)
/*     */             {
/* 292 */               return !(!guess.equalsIgnoreCase("+") && !guess.equalsIgnoreCase("plus") && !guess.equalsIgnoreCase("plus sign"));
/*     */             }
/*     */ 
/*     */             
/*     */             public Answer load() {
/* 297 */               return this;
/*     */             }
/* 299 */           }).load()));
/*     */     
/* 301 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */   }
/*     */   
/*     */   public void askRandomQuestion() {
/* 305 */     this.currentQuestion = this.questions.get(this.random.nextInt(this.questions.size()));
/*     */     
/* 307 */     ChatUtil.broadcastMessage(ChatColor.GOLD + "=+=+=+=+=+=+=+=+=+=+= \n" + ChatUtil.Gi + ChatColor.BOLD + "Chat Game: " + ChatColor.WHITE + this.currentQuestion.getQuestion() + "..." + ChatUtil.Gi + "\nType" + ChatColor.GRAY + " the answer in chat if you know the answer!" + ChatColor.GOLD + "\n=+=+=+=+=+=+=+=+=+=+=");
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onPlayerChatEvent(PlayerChatEvent evt) {
/* 313 */     if (this.currentQuestion != null) {
/* 314 */       String guess = evt.getMessage();
/* 315 */       if (this.currentQuestion.isRightAnswer(guess)) {
/* 316 */         Player winner = evt.getPlayer();
/*     */         
/* 318 */         ChatUtil.broadcastMessage(ChatColor.GOLD + "=+=+=+=+=+=+=+=+=+=+= \n" + RankUtil.getFullTag(winner) + ChatUtil.Gi + " won the chat game and earned 800 coins! The guess was: \"" + guess + "\"." + ChatColor.GOLD + "\n=+=+=+=+=+=+=+=+=+=+=");
/* 319 */         winner.playSound(winner.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
/*     */         
/* 321 */         this.core.getPlayerStatsManager().incrementInt(winner.getUniqueId(), 800, PlayerStat.COINS);
/*     */         
/* 323 */         this.currentQuestion = null;
/*     */         
/* 325 */         evt.setCancelled(true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\chatgames\ChatGamesManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */