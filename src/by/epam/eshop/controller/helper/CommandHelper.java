package by.epam.eshop.controller.helper;

import by.epam.eshop.command.Command;
import by.epam.eshop.command.impl.UnknownCommand;
import by.epam.eshop.controller.initializer.InitCommandHelper;

import java.util.Map;

public final class CommandHelper {

    private static final Map<String, Command> commands = new InitCommandHelper().init();
    private static CommandHelper instance = null;

    private CommandHelper() {
    }

    public static CommandHelper getInstance() {
        if (instance == null) {
            instance = new CommandHelper();
        }
        return instance;
    }

    public Command getCommand(String commandName) {
        Command command = commands.get(commandName);
        if (command == null) {
            command = new UnknownCommand();
        }
        return command;
    }
}
