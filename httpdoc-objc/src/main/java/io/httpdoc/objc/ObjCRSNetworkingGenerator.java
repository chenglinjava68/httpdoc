package io.httpdoc.objc;

import io.httpdoc.core.*;
import io.httpdoc.core.generation.ControllerGenerateContext;
import io.httpdoc.core.generation.Generation;
import io.httpdoc.core.generation.Generator;
import io.httpdoc.core.generation.SchemaGenerateContext;
import io.httpdoc.core.modeler.Archetype;
import io.httpdoc.core.modeler.Modeler;
import io.httpdoc.core.strategy.Strategy;
import io.httpdoc.core.strategy.Task;
import io.httpdoc.core.supplier.Supplier;
import io.httpdoc.objc.core.ObjCDocument;
import io.httpdoc.objc.external.RSClient;
import io.httpdoc.objc.foundation.Cid;
import io.httpdoc.objc.fragment.ClassImplementationFragment;
import io.httpdoc.objc.fragment.ClassInterfaceFragment;
import io.httpdoc.objc.fragment.CommentFragment;
import io.httpdoc.objc.fragment.PropertyFragment;
import io.httpdoc.objc.type.ObjCProtocolType;
import io.httpdoc.objc.type.ObjCType;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * ObjC 生成器
 *
 * @author 杨昌沛 646742615@qq.com
 * @date 2018-07-25 16:26
 **/
public class ObjCRSNetworkingGenerator implements Generator {
    private final static String DEFAULT_PREFIX = "HD";
    private final String prefix;
    private final Modeler<ObjCFile> modeler;

    public ObjCRSNetworkingGenerator() {
        this(DEFAULT_PREFIX);
    }

    public ObjCRSNetworkingGenerator(String prefix) {
        this(prefix, new ObjCMJExtensionModeler(prefix));
    }

    public ObjCRSNetworkingGenerator(Modeler<ObjCFile> modeler) {
        this(DEFAULT_PREFIX, modeler);
    }

    public ObjCRSNetworkingGenerator(String prefix, Modeler<ObjCFile> modeler) {
        this.prefix = prefix;
        this.modeler = modeler;
    }

    @Override
    public void generate(Generation generation) throws IOException {
        Document document = generation.getDocument() != null ? new ObjCDocument(prefix, generation.getDocument()) : null;
        if (document == null) return;
        Map<String, Schema> schemas = document.getSchemas() != null ? document.getSchemas() : Collections.<String, Schema>emptyMap();
        Set<Controller> controllers = document.getControllers() != null ? document.getControllers() : Collections.<Controller>emptySet();
        String directory = generation.getDirectory();
        Strategy strategy = generation.getStrategy();
        Collection<ObjCFile> files = new LinkedHashSet<>();
        for (Schema schema : schemas.values()) files.addAll(generate(new SchemaGenerateContext(generation, schema)));
        for (Controller controller : controllers) files.addAll(generate(new ControllerGenerateContext(generation, controller)));
        Collection<Claxx> classes = new LinkedHashSet<>();
        for (ObjCFile file : files) {
            String pkg = file.getPkg();
            String name = file.getName();
            String extension = file.getType().extension;
            String className = pkg + "." + name;
            String classPath = File.separator + className.replace(".", File.separator) + extension;
            Claxx claxx = new Claxx(classPath, file, Preference.DEFAULT);
            classes.add(claxx);
        }
        Task task = new Task(directory, classes);
        strategy.execute(task);
    }

    protected Collection<ObjCFile> generate(SchemaGenerateContext context) {
        Document document = context.getDocument();
        String pkg = context.getPkg();
        boolean pkgForced = context.isPkgForced();
        Supplier supplier = context.getSupplier();
        Schema schema = context.getSchema();
        Archetype archetype = new Archetype(document, pkg, pkgForced, supplier, schema);
        return modeler.design(archetype);
    }

    protected Collection<ObjCFile> generate(ControllerGenerateContext context) {
        Generation generation = context.getGeneration();
        String pkgGenerated = context.getPkg();
        boolean pkgForced = context.isPkgForced();
        Controller controller = context.getController();
        String comment = "Generated By Httpdoc";
        String name = controller.getName();
        String pkgTranslated = controller.getPkg();
        String pkg = pkgForced || pkgTranslated == null ? pkgGenerated : pkgTranslated;

        ClassInterfaceFragment interfase = new ClassInterfaceFragment();
        interfase.setCommentFragment(new CommentFragment(controller.getDescription() != null ? controller.getDescription() + "\n" + comment : comment));
        interfase.setName(prefix + name);

        ClassImplementationFragment implementation = new ClassImplementationFragment();
        implementation.setCommentFragment(new CommentFragment(controller.getDescription() != null ? controller.getDescription() + "\n" + comment : comment));
        implementation.setName(prefix + name);
        {
            PropertyFragment client = new PropertyFragment();
            client.setName("client");
            client.setType(new ObjCProtocolType(ObjCType.valueOf(Cid.class), ObjCType.valueOf(RSClient.class)));
            implementation.addPropertyFragment(client);
        }

        return Arrays.asList(
                new ObjCFile(pkg, interfase.getName(), ObjCFile.Type.INTERFACE, interfase),
                new ObjCFile(pkg, implementation.getName(), ObjCFile.Type.IMPLEMENTATION, implementation)
        );
    }

}