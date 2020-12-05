/*     */ package net.lightpvp.java.utils;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.Random;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.regex.PatternSyntaxException;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.FireworkEffect;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Firework;
/*     */ import org.bukkit.entity.Item;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.FireworkMeta;
/*     */ import org.bukkit.potion.Potion;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionType;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Util
/*     */ {
/*     */   public static boolean validIP(String ip) {
/*  34 */     if (ip == null || ip.isEmpty())
/*  35 */       return false; 
/*  36 */     ip = ip.trim();
/*  37 */     if ((((ip.length() < 6) ? 1 : 0) & ((ip.length() > 15) ? 1 : 0)) != 0) {
/*  38 */       return false;
/*     */     }
/*     */     try {
/*  41 */       Pattern pattern = Pattern.compile("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
/*  42 */       Matcher matcher = pattern.matcher(ip);
/*  43 */       return matcher.matches();
/*  44 */     } catch (PatternSyntaxException ex) {
/*  45 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void clearPlayer(Player player) {
/*  50 */     player.getInventory().clear();
/*  51 */     player.getInventory().setArmorContents(null);
/*  52 */     for (PotionEffect pe : player.getActivePotionEffects()) {
/*  53 */       player.removePotionEffect(pe.getType());
/*     */     }
/*  55 */     ((CraftPlayer)player).setHealth(20.0D);
/*  56 */     player.setAllowFlight(false);
/*  57 */     player.setFireTicks(0);
/*  58 */     player.setExp(0.0F);
/*  59 */     player.setLevel(0);
/*  60 */     player.setWalkSpeed(0.2F);
/*     */   }
/*     */   
/*     */   public static void broadcastSound(Sound sound) {
/*  64 */     for (Player p : Bukkit.getOnlinePlayers()) {
/*  65 */       p.playSound(p.getLocation(), sound, 1.0F, 1.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void setOldItem(Item item, int secondsAlive) {
/*  70 */     int ticks = 6000 - secondsAlive * 20;
/*     */     
/*  72 */     Field itemField = null;
/*  73 */     Field ageField = null;
/*  74 */     Object NMSItem = null;
/*     */     
/*     */     try {
/*  77 */       itemField = item.getClass().getDeclaredField("item");
/*  78 */       itemField.setAccessible(true);
/*  79 */       NMSItem = itemField.get(item);
/*  80 */       ageField = NMSItem.getClass().getDeclaredField("age");
/*  81 */       ageField.setAccessible(true);
/*  82 */       ageField.set(NMSItem, Integer.valueOf(ticks));
/*  83 */     } catch (NoSuchFieldException|SecurityException|IllegalArgumentException|IllegalAccessException e) {
/*  84 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void pullEntityToLocation(Entity e, Location loc) {
/*  89 */     Location entityLoc = e.getLocation();
/*     */     
/*  91 */     entityLoc.setY(entityLoc.getY() + 0.5D);
/*  92 */     e.teleport(entityLoc);
/*     */     
/*  94 */     double g = -0.08D;
/*  95 */     double d = loc.distance(entityLoc);
/*  96 */     double t = d;
/*  97 */     double v_x = (1.0D + 0.07D * t) * (loc.getX() - entityLoc.getX()) / t;
/*  98 */     double v_y = (1.0D + 0.03D * t) * (loc.getY() - entityLoc.getY()) / t - 0.5D * g * t;
/*  99 */     double v_z = (1.0D + 0.07D * t) * (loc.getZ() - entityLoc.getZ()) / t;
/*     */     
/* 101 */     Vector v = e.getVelocity();
/* 102 */     v.setX(v_x);
/* 103 */     v.setY(v_y);
/* 104 */     v.setZ(v_z);
/* 105 */     e.setVelocity(v);
/*     */   }
/*     */   
/*     */   public static void spawnFireworks(Location location) {
/* 109 */     Firework fw = (Firework)location.getWorld().spawnEntity(location, EntityType.FIREWORK);
/* 110 */     FireworkMeta fwm = fw.getFireworkMeta();
/*     */     
/* 112 */     Random r = new Random();
/*     */     
/* 114 */     int rt = r.nextInt(4) + 1;
/* 115 */     FireworkEffect.Type type = FireworkEffect.Type.BALL;
/* 116 */     if (rt == 1)
/* 117 */       type = FireworkEffect.Type.BALL; 
/* 118 */     if (rt == 2)
/* 119 */       type = FireworkEffect.Type.BALL_LARGE; 
/* 120 */     if (rt == 3)
/* 121 */       type = FireworkEffect.Type.BURST; 
/* 122 */     if (rt == 4)
/* 123 */       type = FireworkEffect.Type.CREEPER; 
/* 124 */     if (rt == 5) {
/* 125 */       type = FireworkEffect.Type.STAR;
/*     */     }
/* 127 */     int r1i = r.nextInt(17) + 1;
/* 128 */     int r2i = r.nextInt(17) + 1;
/* 129 */     Color c1 = getColor(r1i);
/* 130 */     Color c2 = getColor(r2i);
/*     */     
/* 132 */     FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();
/*     */     
/* 134 */     fwm.addEffect(effect);
/*     */     
/* 136 */     int rp = r.nextInt(2) + 1;
/* 137 */     fwm.setPower(rp);
/*     */     
/* 139 */     fw.setFireworkMeta(fwm);
/*     */   }
/*     */   
/*     */   private static Color getColor(int i) {
/* 143 */     Color c = null;
/* 144 */     if (i == 1) {
/* 145 */       c = Color.AQUA;
/*     */     }
/* 147 */     if (i == 2) {
/* 148 */       c = Color.BLACK;
/*     */     }
/* 150 */     if (i == 3) {
/* 151 */       c = Color.BLUE;
/*     */     }
/* 153 */     if (i == 4) {
/* 154 */       c = Color.FUCHSIA;
/*     */     }
/* 156 */     if (i == 5) {
/* 157 */       c = Color.GRAY;
/*     */     }
/* 159 */     if (i == 6) {
/* 160 */       c = Color.GREEN;
/*     */     }
/* 162 */     if (i == 7) {
/* 163 */       c = Color.LIME;
/*     */     }
/* 165 */     if (i == 8) {
/* 166 */       c = Color.MAROON;
/*     */     }
/* 168 */     if (i == 9) {
/* 169 */       c = Color.NAVY;
/*     */     }
/* 171 */     if (i == 10) {
/* 172 */       c = Color.OLIVE;
/*     */     }
/* 174 */     if (i == 11) {
/* 175 */       c = Color.ORANGE;
/*     */     }
/* 177 */     if (i == 12) {
/* 178 */       c = Color.PURPLE;
/*     */     }
/* 180 */     if (i == 13) {
/* 181 */       c = Color.RED;
/*     */     }
/* 183 */     if (i == 14) {
/* 184 */       c = Color.SILVER;
/*     */     }
/* 186 */     if (i == 15) {
/* 187 */       c = Color.TEAL;
/*     */     }
/* 189 */     if (i == 16) {
/* 190 */       c = Color.WHITE;
/*     */     }
/* 192 */     if (i == 17) {
/* 193 */       c = Color.YELLOW;
/*     */     }
/*     */     
/* 196 */     return c;
/*     */   }
/*     */   
/*     */   public static void addPotion(Player player, PotionType potionEffectType, int level) {
/* 200 */     ItemStack item = (new Potion(potionEffectType, level)).toItemStack(1);
/*     */     
/* 202 */     int firstAvailable = 27;
/* 203 */     for (; firstAvailable < 36; firstAvailable++) {
/* 204 */       ItemStack cItem = player.getInventory().getItem(firstAvailable);
/* 205 */       if (cItem != null && (cItem.getType().equals(Material.MUSHROOM_SOUP) || cItem.getType().equals(Material.BOWL)))
/*     */         break; 
/*     */     } 
/* 208 */     player.getInventory().setItem(firstAvailable, item);
/*     */     
/* 210 */     Potion potion = Potion.fromItemStack(item);
/* 211 */     player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "received a " + ChatUtil.Gi + potion.getType().toString().toLowerCase() + " " + potion.getLevel() + " potion" + ChatColor.GRAY + " for your killstreak!");
/*     */   }
/*     */   
/*     */   public static void addItemStack(Player player, Material material, Enchantment enchantment, int level) {
/* 215 */     ItemStack item = new ItemStack(material);
/*     */     
/* 217 */     if (level != 0) {
/* 218 */       item.addEnchantment(enchantment, level);
/*     */     }
/*     */     
/* 221 */     int firstAvailable = 27;
/* 222 */     for (; firstAvailable < 36; firstAvailable++) {
/* 223 */       ItemStack cItem = player.getInventory().getItem(firstAvailable);
/* 224 */       if (cItem != null && (cItem.getType().equals(Material.MUSHROOM_SOUP) || cItem.getType().equals(Material.BOWL))) {
/*     */         break;
/*     */       }
/*     */     } 
/* 228 */     player.getInventory().setItem(firstAvailable, item);
/*     */     
/* 230 */     player.sendMessage(String.valueOf(ChatUtil.Gi) + "You " + ChatColor.GRAY + "received a " + ChatUtil.Gi + item.getType().toString().replace("_", " ").toLowerCase() + ChatColor.GRAY + " for your killstreak!");
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\jav\\utils\Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */