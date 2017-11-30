document.write("<script src='"+ctx+"/js/echarts/doc/slide/js/dist/echarts.js"+"'><\/script>");
function pie_chart(obj_div,title,legend_data,data,color,evnFn){
	// 路径配置
        require.config({
            paths: {
                echarts: ctx+'/js/echarts/doc/slide/js/dist'
            }
        });
        
        // 使用
        require(
            [
                'echarts',
                'echarts/chart/pie' // 使用饼图就加载模块，按需加载
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(obj_div); 
                var ecConfig = require('echarts/config'); 
                myChart.on(ecConfig.EVENT.CLICK, eval(evnFn));                
				option = {
				    title : {
				        text: title,
				        padding:5,
				        subtext: '',
				        x:'center'
				    },
				    tooltip : {
				        trigger: 'item',
				        formatter: "{a} <br/>{b} : {c} ({d}%)"
				    },
				    legend: {
				        orient : 'horizontal',
				        x : 'center',
				        y : 'bottom',
				        padding:5,
				        borderWidth : 1,
				        textStyle:{
				        	fontSize:12
				        },
				        formatter:"{name}",
				        data:legend_data
				    },
				    toolbox: {
				        show : false,
				        feature : {
				            mark : {show: true},
				            dataView : {show: true, readOnly: false},
				            magicType : {
				                show: true, 
				                type: ['pie', 'funnel'],
				                option: {
				                    funnel: {
				                        x: '25%',
				                        width: '50%',
				                        funnelAlign: 'left',
				                        max: 1548
				                    }
				                }
				            },
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    },
				    calculable : true,
				    series : [
				        {
				            name:title,
				            type:'pie',
				            radius : '55%',
				            center: ['50%', '50%'],
				            data:data,
				            itemStyle:{
				            	normal:{
				                  label:{
				                    show: true,
				                    formatter: "{b}\n{c}({d}%)"
				                  },
				                  labelLine :{show:true}
				                }
				            }
				        }
				    ],
				    color:color  
				};
                // 为echarts对象加载数据 
                myChart.setOption(option); 
            }
        );
}