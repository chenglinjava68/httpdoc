package io.httpdoc.jestful.client;

import io.httpdoc.core.Controller;
import io.httpdoc.core.Operation;
import io.httpdoc.core.Parameter;
import io.httpdoc.core.Result;
import io.httpdoc.core.fragment.ClassFragment;
import io.httpdoc.core.fragment.MethodFragment;
import io.httpdoc.core.fragment.ParameterFragment;
import io.httpdoc.core.fragment.ResultFragment;
import io.httpdoc.core.fragment.annotation.HDAnnotation;
import io.httpdoc.core.generation.Generation;
import io.httpdoc.core.generation.OperationGenerateContext;
import io.httpdoc.core.generation.ParameterGenerateContext;
import io.httpdoc.core.modeler.Modeler;
import io.httpdoc.core.supplier.Supplier;
import io.httpdoc.core.type.HDType;
import org.qfox.jestful.client.Message;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Jestful Client 标准生成器
 *
 * @author 杨昌沛 646742615@qq.com
 * @date 2018-05-14 13:29
 **/
public class JestfulStandardGenerator extends JestfulAbstractGenerator {

    public JestfulStandardGenerator() {
        super("", "");
    }

    public JestfulStandardGenerator(Modeler<ClassFragment> modeler) {
        super(modeler);
    }

    public JestfulStandardGenerator(String prefix, String suffix) {
        super(prefix, suffix);
    }

    public JestfulStandardGenerator(Modeler<ClassFragment> modeler, String prefix, String suffix) {
        super(modeler, prefix, suffix);
    }

    @Override
    protected Collection<MethodFragment> generate(OperationGenerateContext context) {
        String pkg = context.getPkg();
        boolean pkgForced = context.isPkgForced();
        Supplier supplier = context.getSupplier();
        Operation operation = context.getOperation();
        MethodFragment method = new MethodFragment(0);
        method.setComment(operation.getDescription());
        Collection<HDAnnotation> annotations = annotate(operation);
        method.getAnnotations().addAll(annotations);
        Result result = operation.getResult();
        HDType type = result != null && result.getType() != null ? result.getType().isVoid() ? null : result.getType().toType(pkg, pkgForced, supplier) : null;
        HDType returnType = type != null ? type : HDType.valueOf(Message.class);
        String comment = result != null ? result.getDescription() : null;
        method.setResultFragment(new ResultFragment(returnType, comment));
        method.setName(name(operation.getName()));
        Generation generation = context.getGeneration();
        Controller controller = context.getController();
        List<Parameter> parameters = operation.getParameters() != null ? operation.getParameters() : Collections.<Parameter>emptyList();
        Collection<ParameterFragment> fragments = generate(new ParameterGenerateContext(generation, controller, operation, parameters));
        method.getParameterFragments().addAll(fragments);
        return Collections.singleton(method);
    }

}
