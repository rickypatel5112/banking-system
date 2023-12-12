package banking;

import java.util.List;

public class MasterControl {

    private final CommandValidator commandValidator;
    private final CommandProcessor commandProcessor;
    private final StoredCommands storedCommands;

    public MasterControl(CommandValidator commandValidator, CommandProcessor commandProcessor, StoredCommands storedCommands) {
        this.commandValidator = commandValidator;
        this.commandProcessor = commandProcessor;
        this.storedCommands = storedCommands;
    }

    public List<String> start(List<String> input) {
        for (String command : input) {
            if (commandValidator.validate(command)) {
                commandProcessor.runCommand(command);
                storedCommands.storeTransactionalCommand(command);
            } else {
                storedCommands.storeInvalidCommand(command);
            }
        }
        return storedCommands.getOutputList();
    }
}
