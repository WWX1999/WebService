package com.example.emailservice.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

//@EnableWs
@Configuration
public class WebServiceConfiguration extends WsConfigurerAdapter {

//    @Bean
//    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext)
//    {
//        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
//        servlet.setApplicationContext(applicationContext);
//        servlet.setTransformWsdlLocations(true);
//        return new ServletRegistrationBean<>(servlet, "/schemas/*");
//    }

    @Bean(name="sendEmail")
    @Autowired
    public Wsdl11Definition userWsdl11Definition(XsdSchema emailXsdSchema ){
        DefaultWsdl11Definition defaultWsdl11Definition = new DefaultWsdl11Definition();

        defaultWsdl11Definition.setPortTypeName("ServicePort");
        //设置访问路径：@Bean("my")，案例：http://localhost:8080/test/ws/my.wsdl
        defaultWsdl11Definition.setLocationUri("/soap");
        //user.xsd中的targetNamespace属性
        defaultWsdl11Definition.setTargetNamespace("http://segmentfault.com/schemas");
        defaultWsdl11Definition.setSchema(emailXsdSchema);

        return defaultWsdl11Definition;
    }

    @Bean
    public XsdSchema emailXsdSchema(){
        return new SimpleXsdSchema(
                new ClassPathResource("META-INF/schemas/email.xsd"));
    }

}
