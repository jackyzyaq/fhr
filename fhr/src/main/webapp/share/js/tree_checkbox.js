		var type_setting = {
			check: {
				enable: true,
				chkStyle: "checkbox",
				radioType: "all",
				chkboxType: { "Y" : "s", "N" : "s" }
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

		var type,type_id,typeTreeDemo,typeContent,isHiden=true;

		function type_beforeClick(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj(typeTreeDemo);
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}
		
		function type_onCheck(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj(typeTreeDemo),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			ids = "";
			values = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				if(nodes[i].id<0)continue;
				v += nodes[i].name + ",";
				ids +=nodes[i].id + ",";
				values +=nodes[i].value + ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			if (ids.length > 0 ) ids = ids.substring(0, ids.length-1);
			if (values.length > 0 ) values = values.substring(0, values.length-1);
			var cityObj = $("#"+type);
			var cityObjId = $("#"+type_id);
			cityObj.attr("value", v);
			cityObjId.attr("value", ids);
		}

		function type_show(obj,obj_id,objTreeDemo,objContent,tree_zNodes,is_hiden) {
			type = obj;
			type_id = obj_id;
			typeTreeDemo = objTreeDemo;
			typeContent = objContent;
			if(typeof(is_hiden) != "undefined"){
				isHiden=is_hiden;
			}
			$.fn.zTree.init($("#"+typeTreeDemo), type_setting, tree_zNodes);
			var cityObj = $("#"+type);
			var cityOffset = $("#"+type).offset();
			$("#"+typeContent).css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
			checkNode($("#"+type_id).val().split(','));
			$("body").bind("mousedown", type_onBodyDown);
		}
		function type_hide() {
			$("#"+typeContent).fadeOut("fast");
			$("body").unbind("mousedown", type_onBodyDown);
		}
		function type_onBodyDown(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == type || event.target.id == typeContent || $(event.target).parents("#"+typeContent).length>0)) {
				if(isHiden){
					type_hide();
				}
			}
		}
		function checkNode(ids) {
			var zTree = $.fn.zTree.getZTreeObj(typeTreeDemo);
			var nodes = zTree.transformToArray(zTree.getNodes());
			if(ids.length>0){
				for(var _id=0;_id<ids.length;_id++){
					for(var i=0;i<nodes.length;i++){
						if(ids[_id]==nodes[i].id){
							nodes[i].checked = true;
							zTree.updateNode(nodes[i]);
						}
					}
				}
			}
		}
