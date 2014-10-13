package com.nuvola.myproject.server;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

import com.nuvola.myproject.server.spring.WebConfig;
import com.nuvola.myproject.shared.ResourcePaths;

public class WebAppInitializer implements WebApplicationInitializer {
    private static final String DISPATCHER_SERVLET_NAME = AbstractDispatcherServletInitializer.DEFAULT_SERVLET_NAME;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        WebApplicationContext rootContext = createRootContext(servletContext);
        configureSpringMvc(servletContext, rootContext);
    }

    private WebApplicationContext createRootContext(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebConfig.class);

        servletContext.addListener(new ContextLoaderListener(context));

        return context;
    }

    private void configureSpringMvc(ServletContext servletContext, WebApplicationContext rootContext) {
        DispatcherServlet dispatcher = new DispatcherServlet(rootContext);

        ServletRegistration.Dynamic servlet = servletContext.addServlet(DISPATCHER_SERVLET_NAME, dispatcher);
        servlet.setLoadOnStartup(1);
        servlet.addMapping(ResourcePaths.ROOT + "/*");
    }
}
