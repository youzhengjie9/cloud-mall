package com.boot.config;
import javax.websocket.server.ServerEndpointConfig.Configurator;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

//在开发过程中遇到了使用注解@ServerEndpoint时，会发生无法自动注入其他实例的情况
@Configuration
public class MyEndpointConfigure extends Configurator implements
        ApplicationContextAware {

    private static volatile BeanFactory context;

    @Override
    public <T> T getEndpointInstance(Class<T> clazz)
            throws InstantiationException {
        return context.getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
//        System.out.println("auto load" + this.hashCode());
        MyEndpointConfigure.context = applicationContext;
    }

}

