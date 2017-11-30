/*
 * 	Additional function for this template
 *	Written by ThemePixels	
 *	http://themepixels.com/
 *
 *	Copyright (c) 2012 ThemePixels (http://themepixels.com)
 *	
 *	Built for Amanda Premium Responsive Admin Template
 *  http://themeforest.net/category/site-templates/admin-templates
 */

var $ = jQuery.noConflict();

$(document).ready(function(){
	$('#overviewselect, input:checkbox').uniform();
	$('#searchUser select, input:checkbox').uniform();
	
	///// NOTIFICATION CONTENT /////
	
	$('.notitab a').live('click', function(){
		var id = $(this).attr('href');
		$('.notitab li').removeClass('current'); //reset current 
		$(this).parent().addClass('current');
		if(id == '#messages')
			$('#activities').hide();
		else
			$('#messages').hide();
			
		$(id).show();
		return false;
	});
	
	
	
	///// SHOW/HIDE VERTICAL SUB MENU /////	
	
	$('.vernav > ul li a, .vernav2 > ul li a').each(function(){
		var url = $(this).attr('href');
		$(this).click(function(){
			if($(url).length > 0) {
				if($(url).is(':visible')) {
					if(!$(this).parents('div').hasClass('menucoll') &&
					   !$(this).parents('div').hasClass('menucoll2'))
							$(url).slideUp();
				} else {
					$('.vernav ul ul, .vernav2 ul ul').each(function(){
							$(this).slideUp();
					});
					if(!$(this).parents('div').hasClass('menucoll') &&
					   !$(this).parents('div').hasClass('menucoll2'))
							$(url).slideDown();
				}
				return false;	
			}
		});
	});
	
	
	///// SHOW/HIDE SUB MENU WHEN MENU COLLAPSED /////
	$('.menucoll > ul > li, .menucoll2 > ul > li').live('mouseenter mouseleave',function(e){
		if(e.type == 'mouseenter') {
			$(this).addClass('hover');
			$(this).find('ul').show();	
		} else {
			$(this).removeClass('hover').find('ul').hide();	
		}
	});
	
	
	///// HORIZONTAL NAVIGATION (AJAX/INLINE DATA) /////	
	
	$('.hornav a').click(function(){
		//this is only applicable when window size below 450px
		if($(this).parents('.more').length == 0)
			$('.hornav li.more ul').hide();
		
		//remove current menu
		$('.hornav li').each(function(){
			$(this).removeClass('current');
		});
		
		$(this).parent().addClass('current');	// set as current menu
		return false;
	});
	
	///// RESPONSIVE /////
	if($(document).width() < 640) {
		$('.togglemenu').addClass('togglemenu_collapsed');
		if($('.vernav').length > 0) {
			
			$('.iconmenu').addClass('menucoll');
			$('body').addClass('withmenucoll');
			$('.centercontent').css({marginLeft: '56px'});
			if($('.iconmenu').length == 0) {
				$('.togglemenu').removeClass('togglemenu_collapsed');
			} else {
				$('.iconmenu > ul > li > a').each(function(){
					var label = $(this).text();
					$('<li><span>'+label+'</span></li>')
						.insertBefore($(this).parent().find('ul li:first-child'));
				});		
			}

		} else {
			
			$('.iconmenu').addClass('menucoll2');
			$('body').addClass('withmenucoll2');
			$('.centercontent').css({marginLeft: '36px'});
			
			$('.iconmenu > ul > li > a').each(function(){
				var label = $(this).text();
				$('<li><span>'+label+'</span></li>')
					.insertBefore($(this).parent().find('ul li:first-child'));
			});		
		}
	}
	
	///// ON RESIZE WINDOW /////
	$(window).resize(function(){
		if($(window).width() > 640)
			$('.centercontent').removeAttr('style');
	});
	
	$('#tabs').tabs();
});