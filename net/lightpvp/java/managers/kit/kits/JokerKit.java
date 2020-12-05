/*     */ package net.lightpvp.java.managers.kit.kits;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.lightpvp.java.Core;
/*     */ import net.lightpvp.java.User;
/*     */ import net.lightpvp.java.managers.kit.Kit;
/*     */ import net.lightpvp.java.utils.ChatUtil;
/*     */ import net.lightpvp.java.utils.RankUtil;
/*     */ import net.minecraft.server.v1_8_R3.Entity;
/*     */ import net.minecraft.server.v1_8_R3.EntityHuman;
/*     */ import net.minecraft.server.v1_8_R3.EntityPlayer;
/*     */ import net.minecraft.server.v1_8_R3.Packet;
/*     */ import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
/*     */ import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
/*     */ import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
/*     */ import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
/*     */ import net.minecraft.server.v1_8_R3.PlayerInteractManager;
/*     */ import net.minecraft.server.v1_8_R3.World;
/*     */ import net.minecraft.server.v1_8_R3.WorldServer;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.LeatherArmorMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ 
/*     */ public class JokerKit
/*     */   implements Listener, Kit
/*     */ {
/*     */   private Core core;
/*  55 */   ItemStack[] content = new ItemStack[36];
/*  56 */   ItemStack[] armor = new ItemStack[4];
/*     */   
/*  58 */   private HashMap<String, Long> cooldowns = new HashMap<>();
/*     */   
/*     */   public JokerKit(Core core) {
/*  61 */     this.core = core;
/*     */     
/*  63 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */     
/*  65 */     ItemStack jukeTool = new ItemStack(Material.SULPHUR);
/*  66 */     ItemMeta jukeToolMeta = jukeTool.getItemMeta();
/*  67 */     jukeToolMeta.setDisplayName(ChatColor.GREEN + "Juke Tool");
/*  68 */     jukeToolMeta.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Right Click" }));
/*  69 */     jukeTool.setItemMeta(jukeToolMeta);
/*     */     
/*  71 */     this.content = new ItemStack[] { core.diamondSwordSharpness, jukeTool, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
/*     */     
/*  73 */     ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
/*  74 */     LeatherArmorMeta chestplateMeta = (LeatherArmorMeta)chestplate.getItemMeta();
/*  75 */     chestplateMeta.setColor(Color.GREEN);
/*  76 */     chestplate.setItemMeta((ItemMeta)chestplateMeta);
/*     */     
/*  78 */     this.armor = new ItemStack[] { new ItemStack(Material.DIAMOND_BOOTS), new ItemStack(Material.IRON_LEGGINGS), chestplate, new ItemStack(Material.DIAMOND_HELMET) };
/*     */     
/*  80 */     for (int i = 0; i < this.armor.length; i++) {
/*  81 */       if (this.armor[i] != null) {
/*  82 */         this.armor[i].addEnchantment(Enchantment.DURABILITY, 2);
/*     */         
/*  84 */         ItemMeta meta = this.armor[i].getItemMeta();
/*  85 */         meta.setLore(Arrays.asList(new String[] { getName() }));
/*  86 */         this.armor[i].setItemMeta(meta);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  93 */     return "Joker";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getListedName() {
/*  98 */     return "kit.joker";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPrice() {
/* 103 */     return 75000;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKit(Player p) {
/* 108 */     User user = this.core.getUser(p.getUniqueId());
/*     */     
/* 110 */     p.getInventory().setContents(this.content);
/* 111 */     p.getInventory().setArmorContents(this.armor);
/* 112 */     user.setKit(getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/* 117 */     return Material.SULPHUR;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getAbilities() {
/* 122 */     return Arrays.asList(new String[] { "", ChatColor.GOLD + "Abilities:", ChatColor.GRAY + "Use your powder to mark a spot", ChatColor.GRAY + "and 3 seconds after", ChatColor.GRAY + "juke your chaser!", "", ChatColor.GOLD + "Price: " + ChatColor.GRAY + getPrice() });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getKitRelatedItem() {
/* 127 */     return this.core.soup;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onPlayerInteractEvent(PlayerInteractEvent evt) {
/* 135 */     if ((evt.getAction().equals(Action.RIGHT_CLICK_AIR) || evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && 
/* 136 */       evt.getItem() != null && evt.getItem().getType().equals(Material.SULPHUR)) {
/* 137 */       final Player player = evt.getPlayer();
/* 138 */       final User user = this.core.getUser(player.getUniqueId());
/*     */       
/* 140 */       if (this.core.getKitManager().isKit(player, this))
/* 141 */         if (this.cooldowns.containsKey(player.getName())) {
/* 142 */           long cooldown = System.currentTimeMillis() - ((Long)this.cooldowns.get(player.getName())).longValue();
/* 143 */           player.sendMessage(ChatColor.RED + "You are still on a cooldown for " + (new DecimalFormat("#.#", this.core.decimalSymbol)).format((30000.0D - cooldown) / 1000.0D) + " more seconds!");
/* 144 */           evt.setCancelled(true);
/*     */         } else {
/* 146 */           player.sendMessage(ChatColor.GRAY + "Spot " + ChatUtil.Gi + "marked," + ChatColor.GRAY + " juking in " + ChatColor.GOLD + "3" + ChatColor.GRAY + " seconds!");
/*     */           
/* 148 */           final Location location = player.getLocation().clone();
/*     */           
/* 150 */           (new BukkitRunnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 154 */                 if (player.getLocation().distance(location) > 30.0D || JokerKit.this.core.hasSpawnFlag(user)) {
/* 155 */                   player.sendMessage(String.valueOf(ChatUtil.Gi) + "Desynchronized," + ChatColor.GRAY + " something interupted your " + ChatUtil.Gi + "juke!");
/* 156 */                   cancel();
/*     */                   
/*     */                   return;
/*     */                 } 
/* 160 */                 WorldServer worldServer = ((CraftWorld)JokerKit.this.core.getWorld()).getHandle();
/*     */                 
/* 162 */                 String tagName = RankUtil.getFullTag(player);
/* 163 */                 if (tagName.length() > 16) {
/* 164 */                   tagName = tagName.substring(0, 16);
/*     */                 }
/*     */                 
/* 167 */                 GameProfile gameProfile = new GameProfile(UUID.randomUUID(), tagName);
/*     */                 
/* 169 */                 EntityPlayer entityPlayer = new EntityPlayer(worldServer.getMinecraftServer(), worldServer, gameProfile, new PlayerInteractManager((World)worldServer));
/*     */                 
/* 171 */                 final int id = entityPlayer.getId();
/*     */                 
/* 173 */                 entityPlayer.setPositionRotation(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
/*     */                 
/* 175 */                 PacketPlayOutNamedEntitySpawn packetSpawnClone = new PacketPlayOutNamedEntitySpawn((EntityHuman)entityPlayer);
/* 176 */                 PacketPlayOutEntityEquipment packet1 = new PacketPlayOutEntityEquipment(id, 4, CraftItemStack.asNMSCopy(player.getInventory().getArmorContents()[3]));
/* 177 */                 PacketPlayOutEntityEquipment packet2 = new PacketPlayOutEntityEquipment(id, 3, CraftItemStack.asNMSCopy(player.getInventory().getArmorContents()[2]));
/* 178 */                 PacketPlayOutEntityEquipment packet3 = new PacketPlayOutEntityEquipment(id, 2, CraftItemStack.asNMSCopy(player.getInventory().getArmorContents()[1]));
/* 179 */                 PacketPlayOutEntityEquipment packet4 = new PacketPlayOutEntityEquipment(id, 1, CraftItemStack.asNMSCopy(player.getInventory().getArmorContents()[0]));
/* 180 */                 PacketPlayOutEntityEquipment packet5 = new PacketPlayOutEntityEquipment(id, 0, CraftItemStack.asNMSCopy(player.getInventory().getItemInHand()));
/* 181 */                 PacketPlayOutEntityHeadRotation packet6 = new PacketPlayOutEntityHeadRotation((Entity)entityPlayer, (byte)(int)(player.getLocation().getYaw() * 256.0D / 360.0D));
/*     */                 
/* 183 */                 (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)packetSpawnClone);
/* 184 */                 (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)packet1);
/* 185 */                 (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)packet2);
/* 186 */                 (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)packet3);
/* 187 */                 (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)packet4);
/* 188 */                 (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)packet5);
/* 189 */                 (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)packet6);
/*     */                 
/* 191 */                 player.sendMessage(String.valueOf(ChatUtil.Gi) + "Juked!");
/* 192 */                 List<Entity> nearbyEntities = player.getNearbyEntities(48.0D, 48.0D, 48.0D);
/* 193 */                 for (Entity e : nearbyEntities) {
/* 194 */                   if (e instanceof Player) {
/* 195 */                     Player p = (Player)e;
/* 196 */                     p.hidePlayer(player);
/* 197 */                     (((CraftPlayer)p).getHandle()).playerConnection.sendPacket((Packet)packetSpawnClone);
/* 198 */                     (((CraftPlayer)p).getHandle()).playerConnection.sendPacket((Packet)packet1);
/* 199 */                     (((CraftPlayer)p).getHandle()).playerConnection.sendPacket((Packet)packet1);
/* 200 */                     (((CraftPlayer)p).getHandle()).playerConnection.sendPacket((Packet)packet2);
/* 201 */                     (((CraftPlayer)p).getHandle()).playerConnection.sendPacket((Packet)packet3);
/* 202 */                     (((CraftPlayer)p).getHandle()).playerConnection.sendPacket((Packet)packet4);
/* 203 */                     (((CraftPlayer)p).getHandle()).playerConnection.sendPacket((Packet)packet5);
/* 204 */                     (((CraftPlayer)p).getHandle()).playerConnection.sendPacket((Packet)packet6);
/*     */                   } 
/*     */                 } 
/*     */ 
/*     */                 
/* 209 */                 final Location playerLocation = player.getLocation().clone();
/*     */                 
/* 211 */                 player.teleport(location);
/* 212 */                 player.playSound(player.getLocation(), Sound.FIZZ, 1.0F, 1.0F);
/*     */                 
/* 214 */                 for (Entity e : nearbyEntities) {
/* 215 */                   if (e instanceof Player) {
/* 216 */                     Player p = (Player)e;
/* 217 */                     p.showPlayer(player);
/*     */                   } 
/*     */                 } 
/*     */                 
/* 221 */                 (new BukkitRunnable()
/*     */                   {
/*     */                     public void run()
/*     */                     {
/* 225 */                       PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] { this.val$id });
/*     */                       
/* 227 */                       StringBuilder caught = new StringBuilder();
/* 228 */                       caught.append(String.valueOf(ChatUtil.Gi) + "Caught: " + ChatColor.GRAY);
/* 229 */                       boolean first = true;
/*     */                       
/* 231 */                       int dist = 4;
/* 232 */                       for (Player p : Bukkit.getOnlinePlayers()) {
/* 233 */                         (((CraftPlayer)p).getHandle()).playerConnection.sendPacket((Packet)packet);
/*     */                         
/* 235 */                         if (!p.getName().equals(player.getName()) && 
/* 236 */                           p.getLocation().distance(playerLocation) < dist) {
/* 237 */                           p.sendMessage(String.valueOf(RankUtil.getFullTag(player)) + ChatColor.GRAY + " fooled you with a " + ChatUtil.Gi + "clone!");
/* 238 */                           p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 1));
/*     */                           
/* 240 */                           caught.append(String.valueOf(first ? "" : ", ") + p.getName());
/* 241 */                           first = false;
/*     */                         } 
/*     */                       } 
/*     */                       
/* 245 */                       player.sendMessage(ChatColor.GRAY + "Clone " + ChatUtil.Gi + "despawned!");
/*     */                       
/* 247 */                       if (!first) {
/* 248 */                         player.sendMessage(caught.toString());
/*     */                       }
/*     */                     }
/* 251 */                   }).runTaskLater((Plugin)JokerKit.this.core, 60L);
/*     */               }
/* 253 */             }).runTaskLater((Plugin)this.core, 60L);
/*     */           
/* 255 */           final String name = player.getName();
/* 256 */           this.cooldowns.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
/* 257 */           Bukkit.getScheduler().runTaskLater((Plugin)this.core, new BukkitRunnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 261 */                   JokerKit.this.cooldowns.remove(name);
/* 262 */                   player.sendMessage(ChatColor.GRAY + "Cooldown ended!");
/*     */                 }
/*     */               }, 
/* 265 */               600L);
/*     */         }  
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\kits\JokerKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */