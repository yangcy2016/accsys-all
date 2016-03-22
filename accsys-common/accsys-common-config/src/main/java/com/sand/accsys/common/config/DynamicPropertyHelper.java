package com.sand.accsys.common.config;

import com.netflix.config.*;

/**
 * @author : huanghy
 * @create : 2016/3/16 0016 下午 1:22
 * @since : ${VERSION}
 */
public class DynamicPropertyHelper {

    private static DynamicPropertyFactory factory = DynamicPropertyFactory.getInstance();

    public static DynamicStringProperty getStringProperty(String propName ,String defaultValue){
        return factory.getStringProperty(propName,defaultValue);
    }

    public static DynamicIntProperty getIntProperty(String propName,int defaultValue){
        return factory.getIntProperty(propName,defaultValue);
    }

    public static DynamicBooleanProperty getBooleanProperty(String propName,boolean defaultValue){
        return factory.getBooleanProperty(propName,defaultValue);
    }

    public static DynamicLongProperty getLongProperty(String propName,long defaultValue){
        return factory.getLongProperty(propName,defaultValue);
    }
}
