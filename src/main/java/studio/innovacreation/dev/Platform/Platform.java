package studio.innovacreation.dev.Platform;

import com.pablo67340.SQLiteLib.Main.SQLiteLib;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.*;

import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public final class Platform extends JavaPlugin implements Listener {

    public SQLiteLib sqlLib;

    private VirtualToolbox vToolBox;
    private ConsoleScripts cScripts;

    @Override
    public void onEnable() {
        sqlLib = SQLiteLib.hookSQLiteLib(null);
        sqlLib.initializeDatabase("InnovaPlatform", "CREATE TABLE IF NOT EXISTS table_name");

        getLogger().info("Innova Platform Init");
        WL.father = this;
        WL.startUpdateThread();

        getServer().getPluginManager().registerEvents(this, this);

        // Register Virtual Tool Box
        vToolBox = new VirtualToolbox(this);
        this.getCommand("wb").setExecutor(vToolBox);
        this.getCommand("enchantment").setExecutor(vToolBox);

        // Register Console Scripts
        cScripts = new ConsoleScripts(this);
        this.getCommand("listInstance").setExecutor(cScripts);
        this.getCommand("createInstance").setExecutor(cScripts);
        this.getCommand("selectInstance").setExecutor(cScripts);
        this.getCommand("removeSelectedInstance").setExecutor(cScripts);
        this.getCommand("eval").setExecutor(cScripts);
    }

    @Override
    public void onDisable() {
        getLogger().info("Innova Platform disabled");
        WL.endUpdateThread();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("PullWL")) {
            WL.commandPull();
            return true;
        }
        return false;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        getLogger().info( event.getPlayer().getName());
    }

    private final Inventory settings_view = Bukkit.createInventory(null, 36, "商店设置");
    {
        ItemStack seperator = new ItemStack(Material.BOAT_JUNGLE, 1, (short) 1);
        settings_view.setItem(4     , seperator);
        settings_view.setItem(4 + 9 , seperator);
        settings_view.setItem(4 + 18, seperator);
        settings_view.setItem(4 + 27, seperator);

    }

    @EventHandler
    public void onOpenInventory(InventoryOpenEvent event) {
        Inventory target_inventory = event.getInventory();
        Player player = (Player) event.getPlayer();
        if (target_inventory.getHolder() instanceof ShulkerBox) {
            ShulkerBox target = (ShulkerBox) target_inventory.getHolder();
            if (    target.getType() == Material.SILVER_SHULKER_BOX ||
                    target.getType() == Material.BLACK_SHULKER_BOX ||
                    target.getType() == Material.BLUE_SHULKER_BOX ||
                    target.getType() == Material.BROWN_SHULKER_BOX ||
                    target.getType() == Material.CYAN_SHULKER_BOX ||
                    target.getType() == Material.GRAY_SHULKER_BOX ||
                    target.getType() == Material.GREEN_SHULKER_BOX ||
                    target.getType() == Material.LIGHT_BLUE_SHULKER_BOX ||
                    target.getType() == Material.LIME_SHULKER_BOX ||
                    target.getType() == Material.MAGENTA_SHULKER_BOX ||
                    target.getType() == Material.ORANGE_SHULKER_BOX ||
                    target.getType() == Material.PINK_SHULKER_BOX ||
                    target.getType() == Material.PURPLE_SHULKER_BOX ||
                    target.getType() == Material.RED_SHULKER_BOX ||
                    target.getType() == Material.WHITE_SHULKER_BOX ||
                    target.getType() == Material.YELLOW_SHULKER_BOX
            ) {
                // 用命名牌管理交易
                // 其他情况为交易
                if (player.getInventory().getItemInMainHand().getType() == Material.NAME_TAG) {
                    event.setCancelled(true);
                    player.openInventory(settings_view);
                    player.sendTitle("", "已将潜影箱设置为商店", 5, 15, 5);
                } else {
                    event.setCancelled(true);
                    Merchant m = Bukkit.createMerchant("潜影商店");
                    player.openMerchant(m, true);
                    m = null;
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteractBlock(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block target = player.getTargetBlock((Set<Material>) null, 4);
    }
}
