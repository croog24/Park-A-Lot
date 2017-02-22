package com.github.parkalot;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.test.util.ReflectionTestUtils;

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
	public static <T> T createJSONObjectFromResource(String fileName, Class<T> clazz) {
		Object jsonObj = null;
		ClassLoader classLoader = TestHelper.class.getClassLoader();
		try {
			File file = new File(classLoader.getResource(fileName).getFile());
			jsonObj = new ObjectMapper().readValue(file, clazz);
		} catch (Exception e) {
			LOGGER.error(String.format("Failed to find test resource while creating JSONObject: %s", fileName), e);
		}
		return (T) jsonObj;
	}

	/**
	 * Leverages the ReflectionTestUtils framework to conveniently inject mock
	 * objects.
	 * @param <T>
	 * 
	 * @param obj the object to inject into.
	 * @param mockObject the mock object to inject.
	 * @param fieldName the name of the field on the object to inject into.
	 */
	@SuppressWarnings("unchecked")
	public static <T> void injectMock(Object obj, Object mockObject, String fieldName) {
		try {
			// Unwrap the object if it is a proxy object
			Object target = obj;
			if (AopUtils.isAopProxy(obj) || AopUtils.isCglibProxy(obj) || AopUtils.isJdkDynamicProxy(obj)) {
				target = ((Advised) obj).getTargetSource().getTarget();
			}
			target = (T) target;

			// Inject
			ReflectionTestUtils.setField(target, fieldName, mockObject);
		} catch (Exception e) {
			LOGGER.error(String.format("Failed to inject %s into %s using fieldname %s", mockObject, obj, fieldName), e);
		}
	}
}
