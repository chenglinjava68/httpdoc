package io.httpdoc.objc;

/**
 * @author 杨昌沛 646742615@qq.com
 * @date 2018-07-25 9:45
 **/
public interface ObjCConstant {
    String FOUNDATION = "#import <Foundation/ObjC.h>";
    String STRONG = "strong";
    String WEAK = "weak";
    String COPY = "copy";
    String ASSIGN = "assign";

    int FLAG_NONE = 0;
    int FLAG_CLASS = 1 << 1;
    int FLAG_PROTOCOL = 1 << 2;
    int FLAG_FOUNDATION = 1 << 3;
    int FLAG_ENUM = 1 << 4;
    int FLAG_PRIMITIVE = 1 << 5;
    int FLAG_TYPEDEF = 1 << 6;
    int FLAG_STRONG = 1 << 7;
    int FLAG_WEAK = 1 << 8;
    int FLAG_COPY = 1 << 9;
    int FLAG_ASSIGN = 1 << 10;

}
