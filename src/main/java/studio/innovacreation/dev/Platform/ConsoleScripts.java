package studio.innovacreation.dev.Platform;

import com.google.common.base.Joiner;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.script.ScriptEngineManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ConsoleScripts implements CommandExecutor {

    private HashMap<CommandSender, ArrayList<ConsoleSciptInstance>> script_insances = new HashMap();
    private HashMap<CommandSender, ConsoleSciptInstance> instance_selector = new HashMap<>();
    private final ScriptEngineManager manager = new ScriptEngineManager();
    private final Platform plugin;

    public ConsoleScripts(Platform plugin) {
        this.plugin = plugin;
    }

    public void checkCreateHashList(CommandSender sender) {
        if (!script_insances.containsKey(sender)) {
            script_insances.put(sender, new ArrayList<ConsoleSciptInstance>());
        }
    }

    public UUID createInstance(CommandSender sender) {
        checkCreateHashList(sender);
        ConsoleSciptInstance i = new ConsoleSciptInstance("js", manager);
        script_insances.get(sender).add(i);
        return i.getId();
    }

    public String listInstance(CommandSender sender) {
        String list = String.format("Command Sender %s ", sender.getName());
        if (script_insances.containsKey(sender)) {
            ArrayList<ConsoleSciptInstance> InstanceList = script_insances.get(sender);
            if (InstanceList instanceof ArrayList && !InstanceList.isEmpty()) {
                list += "has:";
                for (ConsoleSciptInstance i : InstanceList) {
                    list += String.format("\n%d : %s %c", InstanceList.indexOf(i), i.getUUID_String(), instance_selector.get(sender) == i ? '*' : ' ');
                }
            } else {
                list += "doesn't has script instances.";
            }
        } else {
            list += "doesn't has script instances.";
        }
        return list;
    }

    public boolean selectInstance(CommandSender sender, int x) {
        if (script_insances.containsKey(sender) && x >= 0) {
            ArrayList<ConsoleSciptInstance> InstanceList = script_insances.get(sender);
            if (InstanceList instanceof ArrayList && x < InstanceList.size()) {
                instance_selector.put(sender, InstanceList.get(x));
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean removeSelectedInstance(CommandSender sender) {
        if (script_insances.containsKey(sender) && instance_selector.get(sender) instanceof ConsoleSciptInstance) {
            script_insances.get(sender).remove(instance_selector.get(sender));
            instance_selector.remove(sender);
            return true;
        } else {
            return false;
        }
    }

    public String eval(CommandSender sender, String command) {
        if (instance_selector.get(sender) instanceof ConsoleSciptInstance) {
            return instance_selector.get(sender).eval(command);
        }
        return "Instance not selected";
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("listInstance")) {
            commandSender.sendMessage(listInstance(commandSender));
            return true;
        }
        if (command.getName().equalsIgnoreCase("createInstance")) {
            commandSender.sendMessage(createInstance(commandSender).toString());
            return true;
        }
        if (command.getName().equalsIgnoreCase("selectInstance")) {
            String[] processed_strings = (String[]) ArrayUtils.removeElement(strings, null);
            processed_strings = (String[]) ArrayUtils.removeElement(strings, "");
            if (processed_strings.length == 1 && StringUtils.isNumeric(processed_strings[0])) {
                if (selectInstance(commandSender, Integer.parseInt(processed_strings[0]))) {
                    commandSender.sendMessage("Instance selected");
                } else {
                    commandSender.sendMessage("Instance selection failed");
                }
            } else {
                commandSender.sendMessage("Usage: selectInstance [i]");
            }
            return true;
        }
        if (command.getName().equalsIgnoreCase("removeSelectedInstance")) {
            if (removeSelectedInstance(commandSender)) {
                commandSender.sendMessage("Remove succeed");
            } else {
                commandSender.sendMessage("Remove failed. Select a valid instance and then remove.");
            }
            return true;
        }
        if (command.getName().equalsIgnoreCase("eval")) {
            commandSender.sendMessage(eval(commandSender, Joiner.on(" ").join(strings)));
            return true;
        }
        return false;
    }
}
