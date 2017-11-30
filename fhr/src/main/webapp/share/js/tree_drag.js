		var setting = {
			edit: {
				drag: {
					autoExpandTrigger: true,
					prev: dropPrev,
					inner: dropInner,
					next: dropNext
				},
				enable: true,
				showRemoveBtn: false,
				showRenameBtn: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeDrag: beforeDrag,
				beforeDrop: beforeDrop,
				beforeDragOpen: beforeDragOpen,
				onDrag: onDrag,
				onDrop: onDrop,
				onExpand: onExpand
			}
		};
		
		var dragNodes,dragTreeDemo,curDragNodes, autoExpandNode;		

		function dropPrev(treeId, nodes, targetNode) {
			var pNode = targetNode.getParentNode();
			if (pNode && pNode.dropInner === false) {
				return false;
			} else {
				for (var i=0,l=curDragNodes.length; i<l; i++) {
					var curPNode = curDragNodes[i].getParentNode();
					if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
						return false;
					}
				}
			}
			return true;
		}
		function dropInner(treeId, nodes, targetNode) {
			if (targetNode && targetNode.dropInner === false) {
				return false;
			} else {
				for (var i=0,l=curDragNodes.length; i<l; i++) {
					if (!targetNode && curDragNodes[i].dropRoot === false) {
						return false;
					} else if (curDragNodes[i].parentTId && curDragNodes[i].getParentNode() !== targetNode && curDragNodes[i].getParentNode().childOuter === false) {
						return false;
					}
				}
			}
			return true;
		}
		function dropNext(treeId, nodes, targetNode) {
			var pNode = targetNode.getParentNode();
			if (pNode && pNode.dropInner === false) {
				return false;
			} else {
				for (var i=0,l=curDragNodes.length; i<l; i++) {
					var curPNode = curDragNodes[i].getParentNode();
					if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
						return false;
					}
				}
			}
			return true;
		}

		function beforeDrag(treeId, treeNodes) {
			//点击后，移动之前
			for (var i=0,l=treeNodes.length; i<l; i++) {
				if (treeNodes[i].drag === false) {
					curDragNodes = null;
					return false;
				} else if (treeNodes[i].parentTId && treeNodes[i].getParentNode().childDrag === false) {
					curDragNodes = null;
					return false;
				}
			}
			curDragNodes = treeNodes;
			return true;
		}
		function onDrag(event, treeId, treeNodes) {
			//移动中
		}
		function beforeDrop(treeId, treeNodes, targetNode, moveType, isCopy) {
			//移动后放下之前
			return true;
		}
		function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
			//移动后放下中
			reSetOrderNumNode();
		}
		
		
		
		function beforeDragOpen(treeId, treeNode) {
			autoExpandNode = treeNode;
			return true;
		}
		function onExpand(event, treeId, treeNode) {
			if (treeNode === autoExpandNode) {
			}
		}

		function setTrigger() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.setting.edit.drag.autoExpandTrigger = $("#callbackTrigger").attr("checked");
		}
		
		/**
		 * 重置排序orderNum
		 */
		function reSetOrderNumNode() {
			var zTree = $.fn.zTree.getZTreeObj(dragTreeDemo);
			var nodes = zTree.transformToArray(zTree.getNodes());
			for(var i=0;i<nodes.length;i++){
				nodes[i].orderNum=(i+1);
				//nodes[i].checked = true;
				zTree.updateNode(nodes[i]);
			}
		}
		
		function type_show(objTreeDemo,tree_zNodes) {
			dragTreeDemo = objTreeDemo;
			dragNodes = tree_zNodes;
			$.fn.zTree.init($("#"+dragTreeDemo), setting, dragNodes);
			$("#callbackTrigger").bind("change", {}, setTrigger);
		}
		
		/**
		 * 获取id:orderNum排列的json
		 * @param objTreeDemo
		 * @param tree_zNodes
		 */
		function getDragNodesJson() {
			var urlparam = {},_str_='';
			var zTree = $.fn.zTree.getZTreeObj(dragTreeDemo);
			var nodes = zTree.transformToArray(zTree.getNodes());
			for(var i=0;i<nodes.length;i++){
				_str_ += nodes[i].id+'<->'+nodes[i].orderNum+',';
			}
			_str_=_str_.substring(0,_str_.length-1);
			urlparam["ids"]=_str_;
			return urlparam;
		}		
		
		$(document).ready(function(){
		});