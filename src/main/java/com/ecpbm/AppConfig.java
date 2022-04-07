package com.ecpbm;


import java.io.File;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.ServletLoader;
import com.mitchellbosecke.pebble.spring.extension.SpringExtension;
import com.mitchellbosecke.pebble.spring.servlet.PebbleViewResolver;

import org.apache.catalina.*;


@Configuration
@ComponentScan
@MapperScan("com.ecpbm.mapper")
@EnableWebMvc
@EnableTransactionManagement
@PropertySource("classpath:/jdbc.properties")
public class AppConfig {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		// Æô¶¯Tomcat:
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.getInteger("port", 8080));
        tomcat.getConnector();
        // ´´½¨webapp:
        Context ctx = tomcat.addWebapp("", new File("src/main/webapp").getAbsolutePath());
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(
                new DirResourceSet(resources, "/WEB-INF/classes", new File("target/classes").getAbsolutePath(), "/"));
        ctx.setResources(resources);
        tomcat.start();
        tomcat.getServer().await();
	}
	
	@Bean("multipartResolver")
	CommonsMultipartResolver createCommonsMultipartResolver(@Autowired ServletContext servletContext)
	{
		CommonsMultipartResolver cmr=new CommonsMultipartResolver(servletContext);
		cmr.setDefaultEncoding("utf-8");
		cmr.setMaxUploadSize(1048576);
//		System.out.println("1111111111111");
		return cmr;
	}
	
	@Bean
	WebMvcConfigurer createWebMvcConfigurer() {
	    return new WebMvcConfigurer() {
	        @Override
	        public void addResourceHandlers(ResourceHandlerRegistry registry) {
	            registry.addResourceHandler("EasyUI/**").addResourceLocations("/EasyUI/");
	        }
	    };
	}
	
	@Bean
	ViewResolver createViewResolver(@Autowired ServletContext servletContext) {
	    PebbleEngine engine = new PebbleEngine.Builder().autoEscaping(true)
	            .cacheActive(false)
	            .loader(new ServletLoader(servletContext))
	            .extension(new SpringExtension())
	            .build();
	    PebbleViewResolver viewResolver = new PebbleViewResolver();
	    viewResolver.setPrefix("");
	    viewResolver.setSuffix("");
	    viewResolver.setPebbleEngine(engine);
//	    System.out.println("22222222222");
	    return viewResolver;
	}
	
	
	// -- jdbc configuration --------------------------------------------------

	@Value("${jdbc.url}")
	String jdbcUrl;

	@Value("${jdbc.username}")
	String jdbcUsername;

	@Value("${jdbc.password}")
	String jdbcPassword;

	@Bean
	DataSource createDataSource() {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(jdbcUrl);
		config.setUsername(jdbcUsername);
		config.setPassword(jdbcPassword);
		config.addDataSourceProperty("autoCommit", "false");
		config.addDataSourceProperty("connectionTimeout", "5");
		config.addDataSourceProperty("idleTimeout", "60");
		return new HikariDataSource(config);
	}
	
	@Bean
	SqlSessionFactoryBean createSqlSessionFactoryBean(@Autowired DataSource dataSource) {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
	    sqlSessionFactoryBean.setDataSource(dataSource);
	    return sqlSessionFactoryBean;
	}
	
	@Bean
	PlatformTransactionManager createTxManager(@Autowired DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

}