package cn.qionouu.innova.Platform;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import java.util.UUID;

import java.net.*;
import java.io.*;

public final class WL {

  public static JavaPlugin father;

  public static void setWL(String name, boolean status) {
    Bukkit.getOfflinePlayer(name).setWhitelisted(status);
  }

  public static boolean getWL(String name) {
	return Bukkit.getOfflinePlayer(name).isWhitelisted();
  }

  public static void PullWL(String uri) {
    try {
  	  URL url = new URL(uri);
      BufferedReader in = new BufferedReader(
      new InputStreamReader(url.openStream()));

      father.getLogger().info("These users are added :");

      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        //if (inputLine.isEmpty()) {
          if (!getWL(inputLine)) {
      	    setWL(inputLine, true);
      	    father.getLogger().info(inputLine);
      	  }
        //}
      }
      in.close();
      father.getLogger().info("======");
    } catch (Exception e) {
      father.getLogger().info("Failed to pull");
      e.printStackTrace();
    }
  }

  public static void commandPull() {
    try {
      BufferedReader br = new BufferedReader(new FileReader("plugins/innova/WLsource.txt"));
      String line = br.readLine();

      while (line != null) {
        PullWL(line);
        line = br.readLine();
      }

      br.close();
    } catch (Exception e) {
      father.getLogger().info("Failed to get WL source");
      e.printStackTrace();
    }
  }
}
