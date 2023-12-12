package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoredCommands {
    private final List<String> invalidCommands = new ArrayList<>();
    private final Map<String, List<String>> transactionalCommands = new HashMap<>();
    private final Bank bank;

    public StoredCommands(Bank bank) {
        this.bank = bank;
    }

    private String formatDecimal(double value) {

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);

        return decimalFormat.format(value);
    }

    public void storeInvalidCommand(String command) {
        invalidCommands.add(command);
    }

    public void storeTransactionalCommand(String command) {

        String[] parsedCommand = command.split(" ");
        if (parsedCommand[0].equalsIgnoreCase("deposit") || parsedCommand[0].equalsIgnoreCase("withdraw")) {
            String id = parsedCommand[1];

            mapTransactionalCommandToId(id, command);
        } else if (parsedCommand[0].equalsIgnoreCase("transfer")) {
            String fromId = parsedCommand[1];
            String toId = parsedCommand[2];

            mapTransactionalCommandToId(fromId, command);

            mapTransactionalCommandToId(toId, command);
        }

    }

    private void mapTransactionalCommandToId(String id, String command) {
        if (!transactionalCommands.containsKey(id)) {
            transactionalCommands.put(id, new ArrayList<>());
            transactionalCommands.get(id).add(command);
        } else {
            transactionalCommands.get(id).add(command);
        }
    }

    public List<String> getOutputList() {

        List<String> outputList = new ArrayList<>();

        for (Map.Entry<String, GenericAccount> accounts : bank.getAccounts().entrySet()) {
            String id = accounts.getKey();
            String balance = formatDecimal(accounts.getValue().getBalance());
            String apr = formatDecimal(accounts.getValue().getApr());

            String accountType;

            if (bank.getAccountType(id).equals("banking.CheckingAccount")) {
                accountType = "Checking";
            } else if (bank.getAccountType(id).equals("banking.SavingsAccount")) {
                accountType = "Savings";
            } else {
                accountType = "Cd";
            }

            outputList.add(accountType + " " + id + " " + balance + " " + apr);

            if (!transactionalCommands.isEmpty() && transactionalCommands.containsKey(id)) {
                for (String command : transactionalCommands.get(id)) {
                    outputList.add(command);
                }
            }
        }

        for (String invalidCommand : invalidCommands) {
            outputList.add(invalidCommand);
        }

        return outputList;
    }

}
