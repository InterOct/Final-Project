package by.epam.eshop.controller.initializer;

import by.epam.eshop.command.Command;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;
import java.util.Map;

public class SAXCommandParser extends DefaultHandler {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private Map<String, Command> commandMap = new HashMap<>();
    private CommandHolder commandHolder;
    private StringBuilder text;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        text = new StringBuilder();
        if (CommandTag.getTag(qName) == CommandTag.COMMAND) {
            commandHolder = new CommandHolder();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        text.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        CommandTag tag = CommandTag.getTag(qName);
        switch (tag) {
            case COMMAND_NAME:
                commandHolder.setName(text.toString());
                break;
            case COMMAND_CLASS:
                commandHolder.setClassName(text.toString());
                break;
            case COMMAND:
                try {
                    commandMap.put(commandHolder.getName(),
                            (Command) Class.forName(commandHolder.getClassName()).newInstance());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    LOGGER.error("Error initialization commands", e);
                    throw new CommandInitializationException("Error initialization commands", e);
                }
        }
    }

    public Map<String, Command> getCommandMap() {
        return commandMap;
    }

    private enum CommandTag {
        COMMAND, COMMAND_NAME, COMMAND_CLASS, CONFIGURATION;

        public static CommandTag getTag(String localName) {
            return valueOf(localName.toUpperCase().replace('-', '_'));
        }
    }

    private class CommandHolder {
        private String name;
        private String className;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }
    }
}
