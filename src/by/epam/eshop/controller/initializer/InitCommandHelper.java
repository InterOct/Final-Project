package by.epam.eshop.controller.initializer;

import by.epam.eshop.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class InitCommandHelper {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String URL = "D:\\web_epam_2015\\Task_Final_eshop\\project\\web\\WEB-INF\\command.xml";

    public static Map<String, Command> init() {
        XMLStreamReader reader = null;
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(URL);
            reader = inputFactory.createXMLStreamReader(inputStream);
            return getCommands(reader);
        } catch (FileNotFoundException e) {
           LOGGER.error("command.xml not found",e);
            throw new CommandInitializationException("command.xml not found", e);
        } catch (XMLStreamException e) {
            LOGGER.error("Initialization error", e);
            throw new CommandInitializationException("command.xml not found", e);
        }
    }

    private static Map<String, Command> getCommands(XMLStreamReader reader) throws XMLStreamException {
        Map<String,Command> commandMap = new HashMap<>();
        CommandTag tagName = null;
        CommandHolder command = null;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    tagName = CommandTag.getElementTagName(reader.getLocalName());
                    if (tagName == CommandTag.COMMAND) {
                        command = new CommandHolder();
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    String text = reader.getText().trim();
                    if (text.isEmpty()) {
                        break;
                    }
                    setChildInfo(command, tagName, text);
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    tagName = CommandTag.getElementTagName(reader.getLocalName());
                    if (tagName == CommandTag.COMMAND) {
                        try {
                            commandMap.put(command.getName(),
                                    (Command) Class.forName(command.getClassName()).newInstance());
                        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                            LOGGER.error("Error initialization commands",e);
                            throw new CommandInitializationException("command.xml not found", e);
                        }
                    }
                    break;
            }
        }

        return commandMap;
    }

    private static void setChildInfo(CommandHolder command, CommandTag tagName, String text) {
        switch (tagName) {
            case COMMAND_NAME:
                command.setName(text);
                break;
            case COMMAND_CLASS:
                command.setClassName(text);
                break;
        }
    }

    private enum CommandTag {
        COMMAND, COMMAND_NAME, COMMAND_CLASS, CONFIGURATION;

        public static CommandTag getElementTagName(String localName) {
            return valueOf(localName.toUpperCase().replace('-', '_'));
        }
    }

    private static class CommandHolder {
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
