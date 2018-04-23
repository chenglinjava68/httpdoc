package io.httpdoc.core;

import java.util.Enumeration;

/**
 * 应用容器
 *
 * @author 杨昌沛 646742615@qq.com
 * @date 2018-04-23 15:56
 **/
public interface Context {

    Object get(String name);

    Enumeration<Attribute> enumeration();

    void remove(String name);

    void set(String name, Object value);

}