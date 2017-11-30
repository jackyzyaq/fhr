function circleProgress(objId){
	//获取Canvas对象(画布)
	var canvas = document.getElementById(objId);
	//简单地检测当前浏览器是否支持Canvas对象，以免在一些不支持html5的浏览器中提示语法错误
	if(canvas.getContext){
		//获取对应的CanvasRenderingContext2D对象(画笔)
		var ctx = canvas.getContext("2d");
		//开始一个新的绘制路径
		ctx.beginPath();
		//设置弧线的颜色为蓝色
		ctx.strokeStyle = "blue";
		var circle = {
			x : 30,    //圆心的x轴坐标值
			y : 30,    //圆心的y轴坐标值
			r : 30      //圆的半径
		};
		//沿着坐标点(100,100)为圆心、半径为50px的圆的顺时针方向绘制弧线
		ctx.arc(circle.x, circle.y, circle.r, 0, Math.PI*2 , false);
		//按照指定的路径绘制弧线
		ctx.stroke();
	}
}; 