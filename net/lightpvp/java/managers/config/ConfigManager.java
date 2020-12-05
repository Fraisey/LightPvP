/*     */ package net.lightpvp.java.managers.config;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConfigManager
/*     */ {
/*  14 */   public static Map<String, FileConfiguration> configs = new HashMap<>();
/*     */   
/*     */   public static boolean isFileLoaded(String fileName) {
/*  17 */     return configs.containsKey(fileName);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void load(Plugin plugin, String fileName) {
/*  22 */     File file = new File(plugin.getDataFolder(), fileName);
/*  23 */     if (!file.exists()) {
/*     */       try {
/*  25 */         plugin.saveResource(fileName, false);
/*  26 */       } catch (Exception e) {
/*  27 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*  30 */     if (!isFileLoaded(fileName)) {
/*  31 */       configs.put(fileName, YamlConfiguration.loadConfiguration(file));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static FileConfiguration get(String fileName) {
/*  37 */     if (isFileLoaded(fileName)) {
/*  38 */       return configs.get(fileName);
/*     */     }
/*  40 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean update(String fileName, String path, Object value) {
/*  45 */     if (isFileLoaded(fileName) && 
/*  46 */       !((FileConfiguration)configs.get(fileName)).contains(path)) {
/*  47 */       ((FileConfiguration)configs.get(fileName)).set(path, value);
/*  48 */       return true;
/*     */     } 
/*     */     
/*  51 */     return false;
/*     */   }
/*     */   
/*     */   public static void set(String fileName, String path, Object value) {
/*  55 */     if (isFileLoaded(fileName)) {
/*  56 */       ((FileConfiguration)configs.get(fileName)).set(path, value);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addComment(String fileName, String path, String... comments) {
/*  62 */     if (isFileLoaded(fileName)) {
/*  63 */       byte b; int i; String[] arrayOfString; for (i = (arrayOfString = comments).length, b = 0; b < i; ) { String comment = arrayOfString[b];
/*  64 */         if (!((FileConfiguration)configs.get(fileName)).contains(path)) {
/*  65 */           ((FileConfiguration)configs.get(fileName)).set("_COMMENT_" + comments.length, " " + comment);
/*     */         }
/*     */         b++; }
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void remove(String fileName, String path) {
/*  73 */     if (isFileLoaded(fileName)) {
/*  74 */       ((FileConfiguration)configs.get(fileName)).set(path, null);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean contains(String fileName, String path) {
/*  80 */     if (isFileLoaded(fileName)) {
/*  81 */       return ((FileConfiguration)configs.get(fileName)).contains(path);
/*     */     }
/*  83 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void reload(Plugin plugin, String fileName) {
/*  88 */     File file = new File(plugin.getDataFolder(), fileName);
/*  89 */     if (isFileLoaded(fileName)) {
/*     */       try {
/*  91 */         ((FileConfiguration)configs.get(fileName)).load(file);
/*  92 */       } catch (Exception e) {
/*  93 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static void save(Plugin plugin, String fileName) {
/*  99 */     File file = new File(plugin.getDataFolder(), fileName);
/* 100 */     if (isFileLoaded(fileName))
/*     */       try {
/* 102 */         ((FileConfiguration)configs.get(fileName)).save(file);
/* 103 */       } catch (Exception e) {
/* 104 */         e.printStackTrace();
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\config\ConfigManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */