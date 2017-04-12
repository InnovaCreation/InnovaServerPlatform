package cn.qionouu.innova.Platform;

import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.*;

public final class Platform extends JavaPlugin {

  @Override
  public void onEnable() {
    getLogger().info("Innova Platform Init");
    WL.father = this;
  }

  @Override
  public void onDisable() {
    getLogger().info("Innova Platform disabled");
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (cmd.getName().equalsIgnoreCase("PullWL")) {
      WL.commandPull();
      return true;
    }
    return false;
  }
}
