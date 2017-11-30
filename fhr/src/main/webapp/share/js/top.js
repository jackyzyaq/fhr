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
								
								
	///// SHOW/HIDE USERDATA WHEN USERINFO IS CLICKED ///// 
	
	$('.userinfo').click(function(){
		if(!$(this).hasClass('active')) {
			$('.userinfodrop').show();
			$(this).addClass('active');
		} else {
			$('.userinfodrop').hide();
			$(this).removeClass('active');
		}
		//remove notification box if visible
		$('.notification').removeClass('active');
		$('.noticontent').remove();
		return false;
	});
	
	
	///// SHOW/HIDE NOTIFICATION /////
	
	$('.notification a').click(function(){
		var t = $(this);
		var url = t.attr('href');
		if(!$('.noticontent').is(':visible')) {
			$.post(url,function(data){
				t.parent().append('<div class="noticontent">'+data+'</div>');
			});
			//this will hide user info drop down when visible
			$('.userinfo').removeClass('active');
			$('.userinfodrop').hide();
		} else {
			t.parent().removeClass('active');
			$('.noticontent').hide();
		}
		return false;
	});
	
	///// SHOW/HIDE BOTH NOTIFICATION & USERINFO WHEN CLICKED OUTSIDE OF THIS ELEMENT /////

	$(document).click(function(event) {
		var ud = $('.userinfodrop');
		var nb = $('.noticontent');
		//hide user drop menu when clicked outside of this element
		if(!$(event.target).is('.userinfodrop') 
			&& !$(event.target).is('.userdata') 
			&& ud.is(':visible')) {
				ud.hide();
				$('.userinfo').removeClass('active');
		}
		//hide notification box when clicked outside of this element
		if(!$(event.target).is('.noticontent') && nb.is(':visible')) {
			nb.remove();
			$('.notification').removeClass('active');
		}
	});
	
});