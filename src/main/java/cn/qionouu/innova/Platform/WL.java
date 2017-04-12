package cn.qionouu.innova.Platform;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import java.util.UUID;

import java.net.*;
import java.io.*;

class updateThread extends Thread {
  long t;

  updateThread(long t0) {
    this.t = t0;
  }

  public void run() {
    try {
      for (;;) {
        WL.commandPull();
        sleep(t * 1000);
      }
    } catch (Exception e) { }
  }

}

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

      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        //if (inputLine.isEmpty()) {
          if (!getWL(inputLine)) {
      	    setWL(inputLine, true);
      	    father.getLogger().info(inputLine + " is added to whitelist [PullWL]");
      	  }
        //}
      }
      in.close();
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
  
  private static updateThread thr = new updateThread(5);
  
  public static void startUpdateThread() {
    thr.start();
  }
  
  public static void endUpdateThread() {
    try {
      thr.join(300);
    } catch (Exception e) { }
  }
}
