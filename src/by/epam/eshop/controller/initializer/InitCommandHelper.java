package by.epam.eshop.controller.initializer;

import by.epam.eshop.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.util.Map;

public class InitCommandHelper {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String URL = "http://127.0.0.1:8080/command.xml";


    public static Map<String, Command> init() {
        try {
            XMLReader reader = XMLReaderFactory.createXMLReader();
            SAXCommandParser parser = new SAXCommandParser();
            reader.setContentHandler(parser);
            reader.parse(new InputSource(URL));
            return parser.getCommandMap();
        } catch (IOException | SAXException e) {
            LOGGER.error(e);
            throw new CommandInitializationException(e);
        }
    }
}
