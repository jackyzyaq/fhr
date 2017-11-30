
	function mask(){
	var popup = 	
	' 	<div id="blk6" class="blk" style="display:none;"> '+
	'         <div class="main"> '+
	'             <a href="javascript:void(0)" id="close6" class="closeBtn">关闭</a> '+
	'             <ul> '+
	'                 <li><a href="#">项目1</a></li> '+
	'             </ul> '+
	'         </div> '+
	'     </div> ';
		
		
	    
		$(popup).css( {
			width : $(document).width(),
			height : $(document).height()
		}).appendTo("body");
		new PopupLayer({trigger:"#login",popupBlk:"#blk6",closeBtn:"#close6",useOverlay:true});
	}
	function closemark(){
		$(".mask").remove();
		$(".mask-msg").remove();
	}