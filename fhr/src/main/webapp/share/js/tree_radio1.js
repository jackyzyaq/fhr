		var type_setting = {
			check: {
				enable: true,
				chkStyle: "radio",
				radioType: "all"
			},
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeClick: type_beforeClick,
				onCheck: type_onCheck
			}
		};

		var type,type_id,typeTreeDemo,typeMenuContent;

		function type_beforeClick(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj(typeTreeDemo);
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}
		
		function type_onCheck(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj(typeTreeDemo),
			nodes = zTree.getCheckedNodes(true);
			if(nodes.length>0){
				v = "",
				ids = "",
				values = "";
				//for (var i=0, l=nodes.length; i<l; i++) {
					v += nodes[0].name + ",";
					ids +=nodes[0].id + ",";
					values +=nodes[0].value + ",";
				//}
				if (v.length > 0 ) v = v.substring(0, v.length-1);
				if (ids.length > 0 ) ids = ids.substring(0, ids.length-1);
				if (values.length > 0 ) values = values.substring(0, values.length-1);
				var cityObj = $("#"+type);
				var cityObjId = $("#"+type_id);
				cityObj.attr("value", values);
				cityObjId.attr("value", ids);
				type_hideMenu();
				$("#"+type+"").focus();
			}
			
		}

		function type_show(obj,obj_id,objTreeDemo,objMenuContent,tree_zNodes) {
			type = obj;
			type_id = obj_id;
			typeTreeDemo = objTreeDemo;
			typeMenuContent = objMenuContent;
			$.fn.zTree.init($("#"+typeTreeDemo), type_setting, tree_zNodes);
			var cityObj = $("#"+type);
			var cityOffset = $("#"+type).offset();
			$("#"+typeMenuContent).css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
			checkNode($("#"+type_id).val());
			$("body").bind("mousedown", type_onBodyDown);
		}
		function type_hideMenu() {
			$("#"+typeMenuContent).fadeOut("fast");
			$("body").unbind("mousedown", type_onBodyDown);
		}
		function type_onBodyDown(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == type || event.target.id == typeMenuContent || $(event.target).parents("#"+typeMenuContent).length>0)) {
				type_hideMenu();
			}
		}
		
		function checkNode(id) {
			var zTree = $.fn.zTree.getZTreeObj(typeTreeDemo);
			var nodes = zTree.transformToArray(zTree.getNodes());
			for(var i=0;i<nodes.length;i++){
				if(id==nodes[i].id){
					nodes[i].checked = true;
					zTree.updateNode(nodes[i]);
				}
			}
		}		
