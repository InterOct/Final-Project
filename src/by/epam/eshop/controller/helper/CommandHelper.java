package by.epam.eshop.controller.helper;

import by.epam.eshop.command.Command;
import by.epam.eshop.command.impl.UnknownCommand;
import by.epam.eshop.controller.initializer.InitCommandHelper;

import java.util.Map;

public final class CommandHelper {
    private final static Map<String, Command> commands = InitCommandHelper.init();

      public Command getCommand(String commandName) {
        Command command = null;

        command = commands.get(commandName);

        if (command == null) {
            command = new UnknownCommand();
        }

        return command;
    }

}
