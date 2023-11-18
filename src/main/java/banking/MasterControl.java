package banking;

import java.util.List;

public class MasterControl {

    private CommandChecker commandChecker;
    private CommandRunner commandRunner;
    private StoredCommands storedCommands;

    public MasterControl(CommandChecker commandChecker, CommandRunner commandRunner, StoredCommands storedCommands) {
        this.commandChecker = commandChecker;
        this.commandRunner = commandRunner;
        this.storedCommands = storedCommands;
    }

    public List<String> start(List<String> input) {
        for (String command : input) {
            if (commandChecker.validate(command)) {
                commandRunner.runCommand(command);
            } else {
                storedCommands.store(command);
            }
        }
        return storedCommands.getStoredCommands();
    }
}
