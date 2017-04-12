package cn.qionouu.innova.Platform;

import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import java.util.UUID;

import java.net.*;
import java.io.*;

public final class Platform extends JavaPlugin {

  @Override
  public void onEnable() {
    getLogger().info("Innova Platform Init");
  }

  @Override
  public void onDisable() {
    getLogger().info("Innova Platform stop");
  }

  private void setWL(String name, boolean status) {
    Bukkit.getOfflinePlayer(name).setWhitelisted(status);
  }

  private boolean getWL(String name) {
	return Bukkit.getOfflinePlayer(name).isWhitelisted();
  }

  public void PullWL(String uri) {
    try {
  	  URL url = new URL(uri);
      BufferedReader in = new BufferedReader(
      new InputStreamReader(url.openStream()));

      getLogger().info("These users are added :");

      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        //if (inputLine.isEmpty()) {
          if (!getWL(inputLine)) {
      	    setWL(inputLine, true);
      	    getLogger().info(inputLine);
      	  }
        //}
      }
      in.close();
      getLogger().info("======");
    } catch (Exception e) {
      getLogger().info("Failed to pull");
      e.printStackTrace();
    }
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (cmd.getName().equalsIgnoreCase("PullWL")) {
      try {
        BufferedReader br = new BufferedReader(new FileReader("plugins/innova/WLsource.txt"));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        while (line != null) {
          PullWL(line);
          line = br.readLine();
        }

        br.close();
      } catch (Exception e) {
        getLogger().info("Failed to get WL source");
        e.printStackTrace();
      }
      return true;
    }
    return false;
  }
}
