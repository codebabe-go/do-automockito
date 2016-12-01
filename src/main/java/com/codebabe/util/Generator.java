package com.codebabe.util;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;

/**
 * 2016 4/16/16 09:41
 *
 * @author bill
 */
public class Generator {

    public static String generate(Map<String, Object> model, String template) {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("velocimacro.library", "template/macro.vm");
        ve.setProperty("input.encoding", "UTF-8");
        ve.setProperty("output.encoding", "UTF-8");
        ve.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogChute");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

        ve.init();

        Template t = ve.getTemplate("template" + File.separator + template);
        VelocityContext ctx = new VelocityContext();

        if (model != null && !model.isEmpty()) {
            Iterator<Map.Entry<String, Object>> it = model.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Object> entry = it.next();
                ctx.put(entry.getKey(), entry.getValue());
            }
        }

        StringWriter sw = new StringWriter();

        t.merge(ctx, sw);

        return sw.toString();
    }
}
