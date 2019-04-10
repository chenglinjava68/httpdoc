package io.httpdoc.core.fragment;

import io.httpdoc.core.type.HDType;

/**
 * Getter方法碎片
 *
 * @author 杨昌沛 646742615@qq.com
 * @date 2018-04-27 16:34
 **/
public class GetterMethodFragment extends MethodFragment {
    private static final HDType BOOLEAN_TYPE = HDType.valueOf(boolean.class);

    public GetterMethodFragment(HDType type, String name, String alias) {
        this.resultFragment = new ResultFragment(type);
        this.name = (BOOLEAN_TYPE.equals(type) ? "is" : "get") + name.substring(0, 1).toUpperCase() + name.substring(1);
        this.blockFragment = new BlockFragment("return " + alias + ";");
    }

}
