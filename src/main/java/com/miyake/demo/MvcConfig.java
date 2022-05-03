package com.miyake.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	public void addViewControllers(ViewControllerRegistry registry) {

		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/header").setViewName("header");
		registry.addViewController("/headerout").setViewName("headerout");
		registry.addViewController("/testerdef").setViewName("tester");
		registry.addViewController("/testerscreen").setViewName("testerscreen");
		registry.addViewController("/projectsummary").setViewName("projectsummary");
		registry.addViewController("/equipmentdef").setViewName("equipmentdef");
/*		
		registry.addViewController("/diagram.js").setViewName("diagram.js");
		registry.addViewController("/portdetail.js").setViewName("portdetail.js");
		registry.addViewController("/testitemeditor.js").setViewName("testitemeditor.js");
		registry.addViewController("/porttopology.js").setViewName("porttopology.js");
		registry.addViewController("/mytesterstatus.js").setViewName("mytesterstatus.js");
		registry.addViewController("/mywebsocket.js").setViewName("mywebsocket.js");
*/
//		registry.addViewController("/user.js").setViewName("user.js");
//		registry.addViewController("/nav.css").setViewName("nav.css");
	}

}