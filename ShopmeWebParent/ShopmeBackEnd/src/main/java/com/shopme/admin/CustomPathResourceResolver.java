package com.shopme.admin;

import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class CustomPathResourceResolver extends PathResourceResolver implements ResourceResolver{
	
	// https://stackoverflow.com/questions/42201431/spring-mvc-error-with-white-space-in-file-name-for-download
	
	@Override
    protected Resource getResource(String resourcePath, Resource location) throws IOException {
        //fixes problems with whitespaces in url
		//==========
		//   104/Gigabyte-B450M-main.png
		//   72/Acer%20Chromebook%20Spin%20main.png
		System.out.println(resourcePath);
        resourcePath = resourcePath.replace("%2520"," ");
        System.out.println("======replate space ====");
        System.out.println(resourcePath);
        System.out.println(location);
        return super.getResource(resourcePath, location);
    }
}
