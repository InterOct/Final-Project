package by.epam.eshop.controller.helper;

import by.epam.eshop.command.Command;
import by.epam.eshop.command.CommandName;
import by.epam.eshop.command.impl.ChangeLocal;
import by.epam.eshop.command.impl.LoginCommand;
import by.epam.eshop.command.impl.UnknownCommand;
import by.epam.eshop.command.impl.RegisterUserCommand;

import java.util.HashMap;
import java.util.Map;

public final class CommandHelper {
    private Map<CommandName, Command> commands = new HashMap<>();

    public CommandHelper() {

        commands.put(CommandName.LOGIN, new LoginCommand());
        commands.put(CommandName.REGISTER_USER, new RegisterUserCommand());
        commands.put(CommandName.CHANGE_LOCAL, new ChangeLocal());

    }


    public Command getCommand(String commandName) {
        Command command = null;
        CommandName key = null;

        commandName = commandName.replace('-', '_').toUpperCase();
        key = CommandName.valueOf(commandName);

        command = commands.get(key);

        if (command == null) {
            command = new UnknownCommand();
        }

        return command;
    }

}
