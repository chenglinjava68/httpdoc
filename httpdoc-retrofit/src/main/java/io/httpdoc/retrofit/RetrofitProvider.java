package io.httpdoc.retrofit;

import io.httpdoc.core.Schema;
import io.httpdoc.core.provider.Provider;
import io.httpdoc.core.provider.SystemProvider;
import okhttp3.MultipartBody;

import java.lang.reflect.Type;

/**
 * Retrofit 类型提供器
 *
 * @author 杨昌沛 646742615@qq.com
 * @date 2018-05-16 13:39
 **/
public class RetrofitProvider implements Provider {
    private final Provider provider;

    public RetrofitProvider() {
        this(new SystemProvider());
    }

    public RetrofitProvider(Provider provider) {
        this.provider = provider;
    }

    @Override
    public boolean contains(Type type) {
        return provider.contains(type);
    }

    @Override
    public Schema acquire(Type type) {
        return provider.acquire(type);
    }

    @Override
    public boolean contains(Schema schema) {
        return provider.contains(schema);
    }

    @Override
    public Type acquire(Schema schema) {
        if ("File".equals(schema.getName())) return MultipartBody.Part.class;
        return provider.acquire(schema);
    }
}