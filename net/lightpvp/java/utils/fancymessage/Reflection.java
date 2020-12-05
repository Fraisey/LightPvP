/*    */ package net.lightpvp.java.utils.fancymessage;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Method;
/*    */ import org.bukkit.Bukkit;
/*    */ 
/*    */ 
/*    */ public class Reflection
/*    */ {
/*    */   public static String getVersion() {
/* 11 */     String name = Bukkit.getServer().getClass().getPackage().getName();
/* 12 */     String version = String.valueOf(name.substring(name.lastIndexOf('.') + 1)) + ".";
/* 13 */     return version;
/*    */   }
/*    */   
/*    */   public static Class<?> getNMSClass(String className) {
/* 17 */     String fullName = "net.minecraft.server." + getVersion() + className;
/* 18 */     Class<?> clazz = null;
/*    */     try {
/* 20 */       clazz = Class.forName(fullName);
/* 21 */     } catch (Exception e) {
/* 22 */       e.printStackTrace();
/*    */     } 
/* 24 */     return clazz;
/*    */   }
/*    */   
/*    */   public static Class<?> getOBCClass(String className) {
/* 28 */     String fullName = "org.bukkit.craftbukkit." + getVersion() + className;
/* 29 */     Class<?> clazz = null;
/*    */     try {
/* 31 */       clazz = Class.forName(fullName);
/* 32 */     } catch (Exception e) {
/* 33 */       e.printStackTrace();
/*    */     } 
/* 35 */     return clazz;
/*    */   }
/*    */   
/*    */   public static Object getHandle(Object obj) {
/*    */     try {
/* 40 */       return getMethod(obj.getClass(), "getHandle", new Class[0]).invoke(obj, new Object[0]);
/* 41 */     } catch (Exception e) {
/* 42 */       e.printStackTrace();
/* 43 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static Field getField(Class<?> clazz, String name) {
/*    */     try {
/* 49 */       Field field = clazz.getDeclaredField(name);
/* 50 */       field.setAccessible(true);
/* 51 */       return field;
/* 52 */     } catch (Exception e) {
/* 53 */       e.printStackTrace();
/* 54 */       return null;
/*    */     }  } public static Method getMethod(Class<?> clazz, String name, Class... args) {
/*    */     byte b;
/*    */     int i;
/*    */     Method[] arrayOfMethod;
/* 59 */     for (i = (arrayOfMethod = clazz.getMethods()).length, b = 0; b < i; ) { Method m = arrayOfMethod[b];
/* 60 */       if (m.getName().equals(name) && (args.length == 0 || ClassListEqual(args, m.getParameterTypes()))) {
/* 61 */         m.setAccessible(true);
/* 62 */         return m;
/*    */       }  b++; }
/* 64 */      return null;
/*    */   }
/*    */   
/*    */   public static boolean ClassListEqual(Class[] l1, Class[] l2) {
/* 68 */     boolean equal = true;
/* 69 */     if (l1.length != l2.length)
/* 70 */       return false; 
/* 71 */     for (int i = 0; i < l1.length; i++) {
/* 72 */       if (l1[i] != l2[i]) {
/* 73 */         equal = false; break;
/*    */       } 
/*    */     } 
/* 76 */     return equal;
/*    */   }
/*    */ }


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\jav\\utils\fancymessage\Reflection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */