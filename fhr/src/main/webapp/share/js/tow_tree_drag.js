					var setting = {
						edit: {
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
							beforeDrop: beforeDrop
						}
					};
					var zNodes,zNodes2,treeDemo,treeDemo2;
					function beforeDrag(treeId, treeNodes) {
						for (var i=0,l=treeNodes.length; i<l; i++) {
							if(treeNodes[i].node_count==1){
								return false;
							}
							if (treeNodes[i].drag === false) {
								return false;
							}
						}
						return true;
					}
					function beforeDrop(treeId, treeNodes, targetNode, moveType) {
						if(treeNodes[0].node_count==1){
							return false;
						}
						return targetNode ? targetNode.drop !== false : true;
					}
					
					function type_show(zNodes,zNodes2,treeDemo,treeDemo2) {
						zNodes = zNodes;
						zNodes2 = zNodes2;
						treeDemo = treeDemo;
						treeDemo2 = treeDemo2;
						$.fn.zTree.init($("#"+treeDemo), setting, zNodes);
						$.fn.zTree.init($("#"+treeDemo2), setting,zNodes2);
					}