

// 边界
var lineArray=[
[
{x:180, y:20},
{x:300, y:20},
{x:300, y:30},
{x:330, y:12},
{x:325, y:20},
{x:450, y:20}
]
,
[
{x:445, y:20},
{x:445, y:112},
{x:468, y:207},
{x:445, y:266},
]
,
[
{x:445, y:265},
{x:359, y:265},
{x:355, y:273},
{x:165, y:273},
{x:148, y:347},
{x:114, y:347},
{x:99, y:293},
{x:90, y:293},
]
,
[
{x:100, y:300},
{x:112, y:300},
{x:112, y:170},
{x:170, y:170},
{x:187, y:132},
{x:187, y:22},
]

];

//
function lineToPathArray(line){
		if(!line){return null;}
		//var pArray = [];
		var prevNode = null;
			var pathString = "";
		for(var j=0; j<line.length;j++){
			var node = line[j];
			if(!node){continue;}
			//
			if(!prevNode){
				prevNode = node;
				continue;
			}
			//
			//var pathString = "";
			pathString += "M"+prevNode.x + " " + prevNode.y +" ";
			pathString += "L"+node.x + " " + node.y ;//+"z";
			//
			//pArray.push({path:pathString});
			//
			prevNode = node;
		}
		pathString += "z";
		return {path:pathString};
};
//
function toDrawSvgImage(){
	// 构造参数
	var rectValue = ["imageholder1",  600, 430];
	//
	var paper = Raphael.apply(window, rectValue);
	// 将坐标转为 path;
	var pArray = [];
	for(var i=0; i<lineArray.length;i++){
		var line = lineArray[i];
		if(!line){continue;}
		//
		var linePathObj =lineToPathArray(line);
		pArray.push(linePathObj);
	}
	//
	console.log(pArray);



	// 绘制Path
	for(var i=0; i<pArray.length;i++){
		var pathObj = pArray[i];
		//
		var p = paper.path(pathObj.path);
		p.attr({
			fill : pathObj.fill || "#fff",
			stroke : pathObj.stroke || "#000",
			"stroke-width" : pathObj.stroke || "0.6"
		});
	}

	// 输出文字-设置字号
	var image_title = "b)设计稳定温度场";
	//
	var txt = paper.text(300, 370, image_title);
	txt.attr({"font-size": 18});







	return paper;
};

window.onload = function () {
	toDrawSvgImage();
};
