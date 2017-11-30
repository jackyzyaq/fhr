package com.aqap.matrix.faurecia.web.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/manager/index")
public class IndexManagerController {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(IndexManagerController.class);
	  
	@RequestMapping
	public String index()
	{
		return "/manager/index";    
	}
}
