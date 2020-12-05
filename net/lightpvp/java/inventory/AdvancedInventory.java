/*     */ package net.lightpvp.java.inventory;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.lightpvp.java.Core;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.inventory.InventoryCloseEvent;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.metadata.FixedMetadataValue;
/*     */ import org.bukkit.metadata.MetadataValue;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ 
/*     */ public class AdvancedInventory
/*     */   implements Listener
/*     */ {
/*     */   private Core core;
/*     */   private AdvancedInventoryTrigger trigger;
/*     */   private int size;
/*     */   private String title;
/*  29 */   private HashMap<Integer, DataComponent> data = new HashMap<>();
/*     */   
/*     */   public AdvancedInventory(Core core, AdvancedInventoryTrigger trigger, int size, String title) {
/*  32 */     this.core = core;
/*     */     
/*  34 */     this.trigger = trigger;
/*     */     
/*  36 */     this.size = size;
/*  37 */     this.title = title;
/*     */     
/*  39 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)core);
/*     */   }
/*     */   
/*     */   public void addSelector(int slot, String name, List<String> description, String id, Material material, List<String> values) {
/*  43 */     this.data.put(Integer.valueOf(slot), new ScrollerDataComponent(name, description, id, material, values));
/*     */   }
/*     */   
/*     */   public void addBoolean(int slot, String name, List<String> description, String id) {
/*  47 */     this.data.put(Integer.valueOf(slot), new BooleanDataComponent(name, description, id, Material.WOOL));
/*     */   }
/*     */   
/*     */   public void addButton(int slot, String name, List<String> description, String id, Material material) {
/*  51 */     this.data.put(Integer.valueOf(slot), new ButtonDataComponent(name, description, id, material));
/*     */   }
/*     */   
/*     */   public Inventory construct() {
/*  55 */     Inventory inventory = Bukkit.createInventory(null, this.size, this.title);
/*     */     
/*  57 */     Set<Integer> keys = this.data.keySet();
/*  58 */     for (Iterator<Integer> iterator = keys.iterator(); iterator.hasNext(); ) { int key = ((Integer)iterator.next()).intValue();
/*  59 */       DataComponent dataComponent = this.data.get(Integer.valueOf(key));
/*  60 */       ItemStack item = dataComponent.getInitial();
/*  61 */       inventory.setItem(key, item); }
/*     */ 
/*     */     
/*  64 */     return inventory;
/*     */   }
/*     */   
/*     */   private HashMap<String, Integer> collectData(Player player) {
/*  68 */     HashMap<String, Integer> data = new HashMap<>();
/*     */     
/*  70 */     Set<Integer> keys = this.data.keySet();
/*  71 */     for (Iterator<Integer> iterator = keys.iterator(); iterator.hasNext(); ) { int key = ((Integer)iterator.next()).intValue();
/*  72 */       DataComponent dataComponent = this.data.get(Integer.valueOf(key));
/*     */       
/*  74 */       if (dataComponent.isOption()) {
/*  75 */         if (player.hasMetadata(dataComponent.getId())) {
/*  76 */           data.put(dataComponent.getId(), dataComponent.getValue(player)); continue;
/*     */         } 
/*  78 */         data.put(dataComponent.getId(), Integer.valueOf(0));
/*     */       }  }
/*     */ 
/*     */ 
/*     */     
/*  83 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onInventoryClickEvent(InventoryClickEvent evt) {
/*  90 */     if (evt.getWhoClicked() instanceof Player && 
/*  91 */       evt.getInventory().getTitle().equals(this.title) && 
/*  92 */       evt.getRawSlot() < this.size && 
/*  93 */       evt.getCurrentItem() != null && !evt.getCurrentItem().getType().equals(Material.AIR)) {
/*  94 */       ScrollerDataComponent scrollerDataComponent; int newIndex; BooleanDataComponent booleanDataComponent; int newValue; HashMap<String, Integer> data; Player player = (Player)evt.getWhoClicked();
/*     */       
/*  96 */       ItemStack newItem = evt.getCurrentItem();
/*     */       
/*  98 */       DataComponent dataComponent = this.data.get(Integer.valueOf(evt.getRawSlot()));
/*     */       String str;
/* 100 */       switch ((str = dataComponent.getType()).hashCode()) { case -1377687758: if (!str.equals("button")) {
/*     */             break;
/*     */           }
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
/* 134 */           data = collectData(player);
/* 135 */           this.trigger.trigger(player, this.title, ((DataComponent)this.data.get(Integer.valueOf(evt.getRawSlot()))).getId(), data);
/* 136 */           player.closeInventory(); break;case -402164678: if (!str.equals("scroller"))
/*     */             break;  scrollerDataComponent = (ScrollerDataComponent)dataComponent; newIndex = 0; if (!player.hasMetadata(scrollerDataComponent.getId())) { newIndex = 1; } else { newIndex = ((MetadataValue)player.getMetadata(scrollerDataComponent.getId()).get(0)).asInt() + 1; }  if (newIndex + 1 > scrollerDataComponent.getValues().size())
/*     */             newIndex = 0;  player.setMetadata(scrollerDataComponent.getId(), (MetadataValue)new FixedMetadataValue((Plugin)this.core, Integer.valueOf(newIndex))); newItem = scrollerDataComponent.reconstruct(newIndex); break;
/*     */         case 64711720: if (!str.equals("boolean"))
/* 140 */             break;  booleanDataComponent = (BooleanDataComponent)dataComponent; newValue = 0; if (!player.hasMetadata(booleanDataComponent.getId())) { newValue = 1; } else { newValue = (((MetadataValue)player.getMetadata(booleanDataComponent.getId()).get(0)).asInt() == 0) ? 1 : 0; }  player.setMetadata(booleanDataComponent.getId(), (MetadataValue)new FixedMetadataValue((Plugin)this.core, Integer.valueOf(newValue))); newItem = booleanDataComponent.reconstruct(newValue); break; }  evt.setCurrentItem(newItem);
/* 141 */       evt.setCancelled(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onInventoryCloseEvent(InventoryCloseEvent evt) {
/* 150 */     if (evt.getInventory().getTitle().equals(this.title) && 
/* 151 */       evt.getPlayer() instanceof Player) {
/* 152 */       Player player = (Player)evt.getPlayer();
/*     */       
/* 154 */       Set<Integer> keys = this.data.keySet();
/* 155 */       for (Iterator<Integer> iterator = keys.iterator(); iterator.hasNext(); ) { int key = ((Integer)iterator.next()).intValue();
/* 156 */         player.removeMetadata(((DataComponent)this.data.get(Integer.valueOf(key))).getId(), (Plugin)this.core); }
/*     */     
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\inventory\AdvancedInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */