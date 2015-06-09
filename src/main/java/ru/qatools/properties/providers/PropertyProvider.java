package ru.qatools.properties.providers;

import java.util.Properties;

/**
 * User: lanwen
 * Date: 17.07.13
 * Time: 15:45
 */
public interface PropertyProvider {

    Properties provide(ClassLoader classLoader, Class<?> beanClass);
}
