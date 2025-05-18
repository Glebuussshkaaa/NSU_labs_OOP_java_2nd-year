package com.glebestraikh.carfactory.util;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FactoryConfig {
    private static final Logger logger = Logger.getLogger(FactoryConfig.class.getName());
    private static final String FACTORY_PROPERTIES_FILE = "config.properties";
    private static final Properties properties;

    private FactoryConfig() {
        throw new IllegalStateException("Utility class");
    }

    static {
        logger.info("Read configuration file with factory settings");
        try (InputStream configStream = FactoryConfig.class.getClassLoader()
                .getResourceAsStream(FACTORY_PROPERTIES_FILE)) {
            if (configStream == null) {
                throw new NullPointerException("Resource not found: " + FACTORY_PROPERTIES_FILE);
            }
            properties = new Properties();
            properties.load(configStream);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to load factory configuration", e);
            throw new RuntimeException(e);
        }
    }

    private static int parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, "Failed to parse integer from string: " + s, e);
            throw new RuntimeException(e);
        }
    }

    public static int getEngineStorageCapacity() {
        return parseInt(properties.getProperty("EngineStorageCapacity"));
    }

    public static int getBodyStorageCapacity() {
        return parseInt(properties.getProperty("BodyStorageCapacity"));
    }

    public static int getAccessoryStorageCapacity() {
        return parseInt(properties.getProperty("AccessoryStorageCapacity"));
    }

    public static int getCarStorageCapacity() {
        return parseInt(properties.getProperty("CarStorageCapacity"));
    }

    public static int getAccessorySupplierCount() {
        return parseInt(properties.getProperty("NumberOfAccessorySuppliers"));
    }

    public static int getWorkerCount() {
        return parseInt(properties.getProperty("NumberOfWorkers"));
    }

    public static int getDealerCount() {
        return parseInt(properties.getProperty("NumberOfDealers"));
    }
}