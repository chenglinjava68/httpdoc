package io.httpdoc.retrofit;

import io.httpdoc.core.*;
import io.httpdoc.core.fragment.ClassFragment;
import io.httpdoc.core.fragment.MethodFragment;
import io.httpdoc.core.provider.Provider;
import io.httpdoc.core.type.HDParameterizedType;
import io.httpdoc.core.type.HDType;
import okhttp3.ResponseBody;
import rx.Observable;

import java.util.List;

/**
 * Jestful Client Observable 生成器
 *
 * @author 杨昌沛 646742615@qq.com
 * @date 2018-05-14 13:39
 **/
public class RetrofitObservableGenerator extends RetrofitAbstractGenerator {

    public RetrofitObservableGenerator() {
        this("", "ForObservable");
    }

    public RetrofitObservableGenerator(String prefix, String suffix) {
        super(prefix, suffix);
    }

    @Override
    protected void generate(String pkg, Provider provider, ClassFragment interfase, Document document, Controller controller, Operation operation) {
        MethodFragment method = new MethodFragment(0);
        annotate(document, controller, operation, method);
        Result result = operation.getResult();
        HDType type = result != null && result.getType() != null ? result.getType().toType(pkg, provider) : null;
        method.setType(new HDParameterizedType(HDType.valueOf(Observable.class), null, type != null ? type : HDType.valueOf(ResponseBody.class)));
        method.setName(name(operation.getName()));
        List<Parameter> parameters = operation.getParameters();
        if (parameters != null) generate(pkg, provider, method, parameters);

        describe(operation, method, parameters);

        interfase.getMethodFragments().add(method);
    }


}