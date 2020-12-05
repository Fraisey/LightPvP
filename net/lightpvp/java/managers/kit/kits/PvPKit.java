/*    */ package net.lightpvp.java.managers.kit.kits;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import net.lightpvp.java.Core;
/*    */ import net.lightpvp.java.User;
/*    */ import net.lightpvp.java.managers.kit.Kit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.enchantments.Enchantment;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PvPKit
/*    */   implements Listener, Kit
/*    */ {
/*    */   private Core core;
/* 23 */   ItemStack[] content = new ItemStack[36];
/* 24 */   ItemStack[] armor = new ItemStack[4];
/*    */   
/*    */   public PvPKit(Core core) {
/* 27 */     this.core = core;
/*    */     
/* 29 */     this.content = new ItemStack[] { core.diamondSwordSharpness, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup, core.soup };
/* 30 */     this.armor = new ItemStack[] { new ItemStack(Material.IRON_BOOTS), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_HELMET) };
/*    */     
/* 32 */     for (int i = 0; i < this.armor.length; i++) {
/* 33 */       if (this.armor[i] != null) {
/* 34 */         this.armor[i].addEnchantment(Enchantment.DURABILITY, 2);
/*    */         
/* 36 */         ItemMeta meta = this.armor[i].getItemMeta();
/* 37 */         meta.setLore(Arrays.asList(new String[] { getName() }));
/* 38 */         this.armor[i].setItemMeta(meta);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 46 */     return "PvP";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getListedName() {
/* 51 */     return "kit.pvp";
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPrice() {
/* 56 */     return 100;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setKit(Player p) {
/* 61 */     User user = this.core.getUser(p.getUniqueId());
/*    */     
/* 63 */     p.getInventory().setContents(this.content);
/* 64 */     p.getInventory().setArmorContents(this.armor);
/* 65 */     user.setKit(getName());
/*    */   }
/*    */ 
/*    */   
/*    */   public Material getMaterial() {
/* 70 */     return Material.DIAMOND_SWORD;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getAbilities() {
/* 75 */     return Arrays.asList(new String[] { "", ChatColor.GOLD + "Abilities:", ChatColor.GRAY + "Standard PvP Kit", ChatColor.GRAY + "Full Iron and sharp 1 diamond sword", "", ChatColor.GOLD + "Price: " + ChatColor.GRAY + getPrice() });
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getKitRelatedItem() {
/* 80 */     return this.core.soup;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\kits\PvPKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */