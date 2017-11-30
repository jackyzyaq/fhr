		/**********************
		*  upload 
		*********************/
		var swfu;
		$(function(){
		/** function **/
			swfu = new SWFUpload({
				//upload_url: _url,
				//post_params: {"name" : ""},
				
				// File Upload Settings
				file_size_limit : "5 MB",	// 1000MB
				file_types : "*.bmp;*.jpg;*.png;",
				file_types_description : "BMP FILE;JPG FILE;PNG FILE",
				file_upload_limit : "5",
				file_post_name:"Filedata",
								
				file_queue_error_handler : fileQueueError,
				file_dialog_complete_handler : fileDialogComplete,//选择好文件后提交
				file_queued_handler : addFileQueued,
				upload_progress_handler : uploadProgress,
				upload_error_handler : uploadError,
				upload_success_handler : uploadSuccess,
				upload_complete_handler : uploadComplete,
				upload_start_handler : uploadStartHandler,
	
				// Button Settings
				button_image_url : ctx+"/js/swfupload/SmallSpyGlassWithTransperancy_17x18.png",
				button_placeholder_id : "spanButtonPlaceholder",
				button_width: 180,
				button_height: 18,
				button_text : '<span class="button">添加文件</span>',
				button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }',
				button_text_top_padding: 0,
				button_text_left_padding: 18,
				button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
				button_cursor: SWFUpload.CURSOR.HAND,
				
				// Flash Settings
				flash_url : ctx+"/js/swfupload/swfupload.swf",
	
				custom_settings : {
					upload_target : "divFileProgressContainer"
				},
				// Debug Settings
				debug: false  //是否显示调试窗口
			});	
			
			$('#fileUpload').window({
				onBeforeClose:function(){ //当面板关闭之前触发的事件
					$('#fileUpload').window('close', true); //这里调用close 方法，true 表示面板被关闭的时候忽略onBeforeClose 回调函数。
				} 
			});			
		});
		
		function startUploadFile(){
			if(!check()){
				return;
			}
			setParameters();
			swfu.setUploadURL(_url);
			swfu.startUpload();
		}
		
		function check(){
			var swfuStats = swfu.getStats();
			if(swfuStats.files_queued>5){
				showInfo('请上传一个文件！');
				cancelUpload();
				return false;
			}
			$("#file_count").val(swfuStats.files_queued);
			return true;
		}
		
		function setParameters(){
			var sequence = new Date().getTime();
			var guidValue = GenerateGuid();
			var valueObject = {
					"guid":guidValue,
					"sequence":sequence
					};
			swfu.setPostParams(valueObject);
		}
		
		var FILE_COUNT_NAME = "vo.thisFileCount";
	
		function addFileQueued(file){
			fileQueued(file);
			return true;
		}
		function uploadStartHandler(file){
			var swfuStats = swfu.getStats();
			swfu.addFileParam(file.id,FILE_COUNT_NAME,swfuStats.files_queued);
			//alert(swfuStats.successful_uploads + "  "+ swfuStats.files_queued);
			return true;
		}
		/**********************
		*  upload function
		*********************/	