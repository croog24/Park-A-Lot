package com.github.parkalot;

import java.io.File;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A utility class with common useful test methods.
 * 
 * @author Craig
 *
 */
public final class TestHelper {

    private static final Logger LOGGER = Logger.getLogger(TestHelper.class);

    /**
     * Creates a JSON object from a JSON file resource.
     * 
     * @param fileName the JSON file name.
     * @param clazz the class of the JSON object.
     * @return a new <code>Object</code> of input class type <code>T</code>.
     */
    @SuppressWarnings("unchecked")
    public static <T> T createJSONObjectFromResource(final String fileName, final Class<T> clazz) {
        Object jsonObj = null;
        final ClassLoader classLoader = TestHelper.class.getClassLoader();
        try {
            final File file = new File(classLoader.getResource(fileName).getFile());
            jsonObj = new ObjectMapper().readValue(file, clazz);
        } catch (Exception e) {
            LOGGER.error(String.format("Failed to find test resource while creating JSONObject: %s",
                    fileName), e);
        }
        return (T) jsonObj;
    }

}
