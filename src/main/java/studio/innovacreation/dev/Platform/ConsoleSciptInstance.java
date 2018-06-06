package studio.innovacreation.dev.Platform;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.UUID;

public class ConsoleSciptInstance {
    public ScriptEngine engine;
    private UUID id;

    public ConsoleSciptInstance(String lang, ScriptEngineManager manager) {
        engine = manager.getEngineByName("js");
        id = UUID.randomUUID();
    }

    public String getUUID_String() {
        return id.toString();
    }

    public UUID getId() {
        return id;
    }

    public String eval(String s) {
        try {
            Object result = engine.eval(s);
            return result != null ? result.toString() : "null";
        } catch (ScriptException e) {
            return e.getLocalizedMessage();
        }
    }
}
