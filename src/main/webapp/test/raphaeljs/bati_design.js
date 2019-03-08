//
var colorArray = ["red", "green", "blue", "pink"];

// 边界
var boundaryLineArray=[
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
,
[
{x:155, y:237},
{x:155, y:252},
{x:141, y:252},
{x:141, y:237},
{x:142, y:230},
{x:148, y:229},
{x:152, y:232},
{x:155, y:237},
],
[
{x: 258, y:272},
{x: 258, y:201},
{x: 276, y:179},
{x: 293, y:188},
{x: 329, y:244},
{x: 329, y:272},
],
[
{x:411, y:209},
{x:406, y:211},
{x:403, y:215},
{x:404, y:231},
{x:419, y:231},
{x:419, y:215},
{x:417, y:211},
{x:411, y:209},
],
[
{x:185, y:135},
{x:213, y:75},
{x:213, y:56},
{x:227, y:48},
{x:231, y:46},
{x:247, y:48},
{x:271, y:61},
{x:325, y:110},
{x:355, y:122},
{x:396, y:124},
{x:446, y:111},
],

];

// 温度坐标对象数组
var temperatureLineArray=[
    {
      text : '13',
      addr : [
          {x: 114, y:295},
          {x: 120, y:302},
          {x: 122, y:309},
          {x: 115, y:312},
          {x: 104, y:311},
      ]
    },
    {
      text : '14',
      addr : [
          {x: 114, y:278},
          {x: 120, y:287},
          {x: 122, y:294},
          {x: 126, y:306},
          {x: 125, y:315},
          {x: 117, y:321},
          {x: 106, y:319},
      ]
    },
    {
      text : '15',
      addr : [
          {x: 114, y:241},
          {x: 118, y:257},
          {x: 120, y:271},
          {x: 122, y:277},
          {x: 126, y:293},
          {x: 130, y:306},
          {x: 129, y:317},
          {x: 121, y:328},
          {x: 111, y:336},
      ]
    },
    {
      text : '16',
      addr : [
          {x: 114, y:186},
          {x: 118, y:202},
          {x: 120, y:226},
          {x: 122, y:253},
          {x: 126, y:270},
          {x: 130, y:283},
          {x: 136, y:300},
          {x: 138, y:309},
          {x: 135, y:327},
          {x: 128, y:343},
          {x: 125, y:346},
      ]
    },
    {
      text : '17',
      addr : [
          {x: 174, y:159},
          {x: 175, y:168},
          {x: 170, y:175},
          {x: 152, y:187},
          {x: 140, y:197},
          {x: 133, y:212},
          {x: 130, y:219},
          {x: 128, y:233},
          {x: 128, y:252},
          {x: 132, y:268},
          {x: 137, y:277},
          {x: 146, y:287},
          {x: 157, y:307},
      ]
    },
    {
      text : '18',
      addr : [
          {x: 194, y:120},
          {x: 194, y:147},
          {x: 195, y:160},
          {x: 196, y:168},
          {x: 194, y:175},
          {x: 192, y:187},
          {x: 188, y:197},
          {x: 156, y:212},
          {x: 139, y:219},
          {x: 133, y:233},
          {x: 133, y:252},
          {x: 141, y:268},
          {x: 149, y:277},
          {x: 163, y:287},
      ]
    },
    {
      text : '19',
      addr : [
          {x: 208, y:87},
          {x: 213, y:104},
          {x: 212, y:120},
          {x: 215, y:137},
          {x: 216, y:147},
          {x: 220, y:160},
          {x: 223, y:168},
          {x: 224, y:175},
          {x: 225, y:187},
          {x: 222, y:197},
          {x: 211, y:212},
          {x: 204, y:219},
          {x: 177, y:227},
          {x: 149, y:227},
          {x: 140, y:228},
          {x: 136, y:238},
          {x: 137, y:256},
          {x: 144, y:261},
          {x: 163, y:260},
          {x: 179, y:259},
          {x: 194, y:267},
          {x: 202, y:274},
      ]
    },
    {
      text : '20',
      addr : [
          {x: 220, y:54},
          {x: 225, y:74},
          {x: 229, y:117},
          {x: 231, y:152},
          {x: 241, y:174},
          {x: 252, y:197},
          {x: 258, y:217},
          {x: 258, y:272},
      ]
    },
    {
      text : '21',
      addr : [
          {x: 246, y:48},
          {x: 243, y:56},
          {x: 236, y:71},
          {x: 237, y:88},
          {x: 243, y:119},
          {x: 250, y:136},
          {x: 266, y:154},
          {x: 328, y:198},
          {x: 370, y:204},
          {x: 411, y:205},
          {x: 424, y:211},
          {x: 430, y:228},
          {x: 429, y:251},
          {x: 429, y:266},
      ]
    },
    {
      text : '22',
      addr : [
          {x: 248, y:49},
          {x: 246, y:61},
          {x: 249, y:95},
          {x: 316, y:164},
          {x: 375, y:176},
          {x: 389, y:180},
          {x: 420, y:200},
          {x: 447, y:230},
          {x: 456, y:238},
      ]
    },
];


// 初始化回调;
window.onload = function () {
    drawDesignBati()
};


// 绘制坝体
function drawDesignBati(){
    // 初始化画板
    var paper = initPaper();
    // 绘制标题
    toDrawTitle(paper);
    // 绘制边界
    toDrawDoundary(paper, boundaryLineArray);
    // 绘制等温线
    toDrawTemperatureLine(paper, temperatureLineArray);
};
// 初始化画板
function initPaper(){

    // 构造参数
    var rectValue = ["imageholder1",  600, 430];
    //
    var paper = Raphael.apply(window, rectValue);

    return paper;
};
// 绘制标题
function toDrawTitle(paper){

    // 输出文字-设置字号
    var image_title = "b)设计稳定温度场";
    //
    var txt = paper.text(300, 370, image_title);
    txt.attr({"font-size": 18});
};
// 绘制边界
function toDrawDoundary(paper, bLineArray){
    //
    // 将坐标转为 path;
    var pArray = [];
    for(var i=0; i<bLineArray.length;i++){
        var line = bLineArray[i];
        if(!line){continue;}
        //
        var linePathObj =lineToPathArray(line);
        pArray.push(linePathObj);
    }
    //
    // console.log(pArray);

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

    // 线条转换为Path数组
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
};

// 绘制等温线
function toDrawTemperatureLine(paper, tempLineArray){
    //
    console.dir(tempLineArray);
    //
    // 将坐标转为 path;
    var cArray = [];
    for(var i=0; i<tempLineArray.length;i++){
        var t = tempLineArray[i];
        if(!t){continue;}
        //
        var curvePathObj =tempToCurvePath(t);//, paper);
        //cArray.push(curvePathObj);
		// 绘制Path
		//
		var color = colorArray[(i%colorArray.length)];
        //
        var p = paper.path(curvePathObj.path);
        p.attr({
            fill : curvePathObj.fill || "#fff",
            stroke : curvePathObj.stroke || color|| "#000",
            "stroke-width" : curvePathObj.stroke || "0.6"
        });
		//
        var addr = t.addr || [];
        var text = t.text;
		//
		if(text){
			var centerIndex = Math.ceil(addr.length /2)
			var centerPoint = addr[centerIndex];
			// 绘制白心圆点
			var dot = paper.circle(centerPoint.x, centerPoint.y, 6);
			dot.attr("fill", "white");
			dot.attr("stroke", "white");
			//dot.attr("stroke", "blue");
			//dot.attr("stroke-width", 3);
			// 温度
			var txt = paper.text(centerPoint.x, centerPoint.y, text);
			txt.attr({"stroke": color, "stroke-width": 0.3, "font-size": 12});
		}
		
    }
	// 

    // 温度线转换为曲线Path
    function tempToCurvePath(tempLine, paper){
            if(!tempLine){return null;}
            //var pArray = [];
            var prevNode = null;
            var pathString = "";
            var addr = tempLine.addr || [];
            for(var j=0; j < addr.length;j++){
                var node = addr[j];
                if(!node){continue;}
                //
                if(!prevNode){
                    prevNode = node;
                    continue;
                }
				var controllPairObj = calcControllPoint(prevNode, node, itemAt(addr, j-2), itemAt(addr, j+1));
				//
				pathString += "M"+prevNode.x + "," + prevNode.y +" ";
				if(!controllPairObj){
					// 直线
					pathString += "L"+node.x + " " + node.y ;
				} else {
					// 贝塞尔
					pathString += "C";
					// 增加控制点
					pathString += " "+controllPairObj.x1 + "," + controllPairObj.y1;
					pathString += " "+controllPairObj.x2 + "," + controllPairObj.y2;
					// 避免中途拐弯。
					/*
					pathString += " "+Math.round((controllPairObj.x1+controllPairObj.x2)/2) 
						+ "," + Math.round((controllPairObj.y1+controllPairObj.y2)/2);
					pathString += " "+Math.round((controllPairObj.x1+controllPairObj.x2)/2) 
						+ "," + Math.round((controllPairObj.y1+controllPairObj.y2)/2);*/

					// 终点
					pathString += " "+node.x + "," + node.y ;
				}
				if(paper){
					var p = paper.path(pathString);
					p.attr({
						fill :  "#fff",
						stroke :  "#000",
						"stroke-width" :  "0.6"
					});
					pathString = "";
				}
                //
                //pArray.push({path:pathString});
                //
                prevNode = node;
            }
            // pathString += "z";
            return {path:pathString};
    };
	//
	function itemAt(arr, idx){
		if(idx < 0 || idx >= arr.length){return null;}
		return arr[idx];
	};
	//
	function to1min(dx, dx2){
		var symbol = (dx2 < 0) ? -1 : 1;
		var dxa = Math.abs(dx);
		var dx2a = Math.abs(dx2);
		return symbol * Math.min(dxa, dx2a);
		
	};

	// 计算控制点
	// beforeNode影响x1y1; afterNode 影响x2y2
	// 下一步尝试返回 {x1, y1, x2, y2, mx, my}
	function calcControllPoint(startNode, endNode, beforeNode, afterNode){
		if(!startNode || !endNode){ return null; }
		if(!beforeNode && !afterNode){ return null; }
		// 均力点
		var avgForcePoint = {
			x1 : null,
			y1 : null,
			x1 : null,
			y1 : null
		};
		// 系数
		var scale = 0.7;
		// 两个坐标点
		var startX = startNode.x;
		var startY = startNode.y;
		var endX = endNode.x;
		var endY = endNode.y;
		// 中间点
		var middleNode = {x: Math.round((startX+endX)/2), y: Math.round((startY+endY)/2) };
		// 类似直线; 特殊处理;
		if(startX == endX || startY == endY){
			avgForcePoint.x1 = middleNode.x;
			avgForcePoint.x2 = middleNode.x;
			avgForcePoint.y1 = middleNode.y;
			avgForcePoint.y2 = middleNode.y;
			return avgForcePoint;
		}
		// 算偏移量;
		// 最大偏移量
		var dx = endX - startX;
		var dy = endY - startY;
		//
		//
		if(beforeNode){
			//
			var pdx = beforeNode.x - startX;
			var pdy = beforeNode.y - startY;
			pdx=to1min(dx, pdx);
			pdy=to1min(dy, pdy);
			//
			avgForcePoint.x1 = middleNode.x - pdx * scale;
			avgForcePoint.y1 = middleNode.y - pdy * scale;
		}
		if(afterNode){
			//
			var ndx = afterNode.x - endX;
			var ndy = afterNode.y - endY;
			ndx=to1min(dx, ndx);
			ndy=to1min(dy, ndy);
			//
			avgForcePoint.x2 = middleNode.x - ndx * scale;
			avgForcePoint.y2 = middleNode.y - ndy * scale;
		}
		//
		if(!beforeNode){
			avgForcePoint.x1 = avgForcePoint.x2;
			avgForcePoint.y1 = avgForcePoint.y2;
		}
		//
		if(!afterNode){
			avgForcePoint.x2 = avgForcePoint.x1;
			avgForcePoint.y2 = avgForcePoint.y1;
		}
		// 算出均力点; 避免中途拐弯。
		var mx = Math.round((avgForcePoint.x1+avgForcePoint.x2)/2);
		var my = Math.round((avgForcePoint.y1+avgForcePoint.y2)/2);
		// 然后依次计算
		var controllPairObj = {
			mx : mx,
			my : my,
			x1 : mx,//Math.round((mx + startX)/2),
			y1 : my,//Math.round((my + startY)/2),
			x2 : mx,//Math.round((mx + endX)/2),
			y2 : my,//Math.round((my + endX)/2)
		};
		
		//
		console.log("arguments:", arguments, "; controllPairObj:" , controllPairObj);
		//
		return controllPairObj;
	};
};