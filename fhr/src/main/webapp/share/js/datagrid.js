		$(function(){
			//varBox();
		});
		var _dataGrid;//定义全局变量datagrid
		var _dataGrid_tt = "#_t_t_";
		var _dataGrid_pp = "#_p_p_";
		var _dataGrid_search = "#_search_form_";
		function _dataGridFn(obj,url,columns,toolbar){
			if(obj==null||url==null||columns==null||toolbar==null){
				return ;
			}
			if(obj.indexOf("#")==-1)obj="#"+obj;
			if($(obj+" "+_dataGrid_tt).length>0){
			}else{
				$("<table id=_t_t_></table>").appendTo($(obj));
				$("<div id='_search_form_'>"+
					"<input type=\"hidden\" name=\"totalCount\" value=\"\" id=\"requestParam_page_totalCount\"/>"+
					"<input type=\"hidden\" name=\"pageIndex\" value=\"\" id=\"requestParam_page_pageIndex\"/>"+
					"<input type=\"hidden\" name=\"pageSize\" value=\"\" id=\"requestParam_page_pageSize\"/>"+
					"<input type=\"hidden\" name=\"sidx\" value=\"\" id=\"requestParam_page_sidx\"/>"+
					"<input type=\"hidden\" name=\"sord\" value=\"\" id=\"requestParam_page_sord\"/>"+
					"</div>").appendTo($(obj));
				$("<div id='_p_p_' style='background:#efefef;border:1px solid #ccc;'></div>").appendTo($(obj));
			}
			
			url = _makeUrl(url);
			_dataGrid = $(obj+" "+_dataGrid_tt).datagrid({
				url: url+getParams((obj+" "+_dataGrid_search).substring(1, (obj+" "+_dataGrid_search).length)),
				title: '',
				//width: document.documentElement.clientWidth-39,
				height: 'auto',
				//fitColumns: true,
				singleSelect:false,//是否单选
				rownumbers:true,//行号
				//remoteSort:true,
				idField: 'id',
				nowrap: false,
				striped: true,
				//fit: true,
				loadMsg:'数据加载中请稍后……', 
				frozenColumns:[[ 
					{field:'ck',checkbox:true} 
				]],
				columns:columns,
				toolbar:toolbar,
    			rowStyler:function(rowIndex,rowData){  
                    //任务完成100%， 并且已审核通过，背景色置灰  
                    return '';  
                },
				onBeforeLoad:function(row, param){
					//$(this).datagrid('rejectChanges');
				},
				onLoadSuccess: function (data) {
					_dataGrid = $(obj+" "+_dataGrid_tt);
					$(obj+" "+_dataGrid_tt).datagrid('clearSelections');
					total = parseInt(data.totalCount);
					pageSize = parseInt(data.pageSize);
					$(obj+" "+_dataGrid_pp).pagination({ 
						total:total,
						pageSize: pageSize,//每页显示的记录条数，默认为10 
						pageList: [5,10,15],//可以设置每页记录条数的列表 
						beforePageText: '第',//页数文本框前显示的汉字 
						afterPageText: '页    共 {pages} 页', 
						displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
						onBeforeRefresh:function(){
						},
						onSelectPage:function(pageNumber, pageSize){
							$(this).pagination('loading');
							$(obj+" "+_dataGrid_search+" #requestParam_page_pageIndex").val(pageNumber);
							$(obj+" "+_dataGrid_search+" #requestParam_page_pageSize").val(pageSize);
							$().datagrid('clearSelections');
							_dataGridLoad(obj,_dataGrid_tt,_dataGrid_search,url);
							$(this).pagination('loaded');					
						}
				    });
					//closemark();
				},
				onHeaderContextMenu: function(e, field){
				},						
				queryParams:{
					"sidx":"",
					"sord":"",
					"queryWord":"",
					"queryType":""
				},
				onSortColumn:function(sort,order){
					var queryParams = $(obj+" "+_dataGrid_tt).datagrid('options').queryParams;
					queryParams.sidx = sort;
					queryParams.sord = order;
					$(obj+" "+_dataGrid_search+" #requestParam_page_sidx").val(sort);
					$(obj+" "+_dataGrid_search+" #requestParam_page_sord").val(order);
					_dataGridLoad(obj,_dataGrid_tt,_dataGrid_search,url);
		    	},
				onDblClickRow:function(rowIndex, rowData){//双击,
				}
			});
		}
		
		function _dataGridLoad(obj,_dataGrid_tt,_dataGrid_search,url){
			$(obj+" "+_dataGrid_tt).datagrid('clearSelections');
			$(obj+" "+_dataGrid_tt).datagrid({
				url:url+getParams((obj+" "+_dataGrid_search).substring(1, (obj+" "+_dataGrid_search).length))
		    });
		}
		
		function _makeUrl(url){
			if(url.indexOf('?')>-1){
				url +="&";
			}else{
				url +="?";
			}
			return url;
		}
