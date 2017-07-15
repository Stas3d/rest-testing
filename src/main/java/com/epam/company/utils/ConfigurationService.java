package com.epam.company.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;

@UtilityClass
public class ConfigurationService {
    private static final String FILE_NAME = "configuration/tests.properties";
    private static final Object SYN_OBJ = new Object();
    private static Configuration CONFIGURATION = null;

    public static Configuration loadConfiguration() {
        if (CONFIGURATION == null)
            synchronized (SYN_OBJ) {
                if (CONFIGURATION == null)
                    try {
                        CompositeConfiguration compositeConfiguration = new CompositeConfiguration();
                        compositeConfiguration.addConfiguration(new SystemConfiguration());
                        compositeConfiguration.addConfiguration(new PropertiesConfiguration(FILE_NAME));
                        CONFIGURATION = compositeConfiguration;
                    } catch (ConfigurationException e) {
                        throw new IllegalStateException("Unable to load configuration", e);
                    }
            }
        return CONFIGURATION;
    }
}
