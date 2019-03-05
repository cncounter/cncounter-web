

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
        var curvePathObj =tempToCurvePath(t);
        cArray.push(curvePathObj);
    }
    //
    // 绘制Path
    for(var i=0; i<cArray.length;i++){
        var curvePathObj = cArray[i];
        //
        var p = paper.path(curvePathObj.path);
        p.attr({
            fill : curvePathObj.fill || "#fff",
            stroke : curvePathObj.stroke || "#000",
            "stroke-width" : curvePathObj.stroke || "0.6"
        });
    }

    // 温度线转换为曲线Path
    function tempToCurvePath(tempLine){
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