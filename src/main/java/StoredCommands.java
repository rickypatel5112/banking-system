import java.util.ArrayList;
import java.util.List;

public class StoredCommands {
    private List<String> storedCommands = new ArrayList<>();


    public void store(String command) {
        storedCommands.add(command);
    }

    public boolean containsCommand(String command) {
        return storedCommands.contains(command);
    }

    public List<String> getStoredCommands() {
        return storedCommands;
    }
}