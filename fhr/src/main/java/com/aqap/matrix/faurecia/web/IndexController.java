package com.aqap.matrix.faurecia.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/index")
public class IndexController {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(IndexController.class);
	  
	@RequestMapping
	public String index()
	{
		return "index";    
	}
}
