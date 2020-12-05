package net.lightpvp.java.managers.kit;

import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Kit {
  String getName();
  
  String getListedName();
  
  int getPrice();
  
  void setKit(Player paramPlayer);
  
  Material getMaterial();
  
  List<String> getAbilities();
  
  ItemStack getKitRelatedItem();
}


/* Location:              C:\Users\Shelby\Documents\jd-gui-windows-1.6.6\LightPvP.jar!\net\lightpvp\java\managers\kit\Kit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */