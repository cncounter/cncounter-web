/**
 * 排序-查找-算法-DEMO
 */
;
// 选择排序; 返回新数组
// (数组, desc默认0;是否降序;)
function selectionSort(sourceArray, direction){
    if(!sourceArray || !sourceArray.length){
        return [];
    }
    direction = direction || 0;
    //
    var sourceLenth = sourceArray.length;
    for(var i=0; i < sourceLenth; i++){
        //
        var theIndex = -1;
        if(direction){ // 降序
            theIndex = findMaxValueIndex(sourceArray, i);
        } else { // 升序
            theIndex = findMinValueIndex(sourceArray, i);
        }
        if(theIndex>=0){
            // 交换数值
            var tempValue = sourceArray[i];
            sourceArray[i] = sourceArray[theIndex];
            sourceArray[theIndex] = tempValue;
        }
    }
    return sourceArray;

    // 查找最大值(数组, 起始idx )
    function findMaxValueIndex(theArray, startIndex){
        if(!theArray || !theArray.length){
            return -1;
        }
        startIndex = startIndex || 0;
        //
        var theLength = theArray.length;
        if(startIndex >= theLength){
            return -1; // 超过数组范围
        }
        var theIndex = startIndex;
        var theValue = theArray[theIndex];
        //
        for(var i=startIndex; i<theLength; i++){
            if(theArray[i] > theValue){
                theIndex = i;
                theValue = theArray[theIndex];
            }
        }
        return theIndex;
    };
    // 查找最小值(数组, 起始idx )
    function findMinValueIndex(theArray, startIndex){
        if(!theArray || !theArray.length){
            return -1;
        }
        startIndex = startIndex || 0;
        //
        var theLength = theArray.length;
        if(startIndex >= theLength){
            return -1; // 超过数组范围
        }
        var theIndex = startIndex;
        var theValue = theArray[theIndex];
        //
        for(var i=startIndex; i<theLength; i++){
            if(theArray[i] < theValue){
                theIndex = i;
                theValue = theArray[theIndex];
            }
        }
        return theIndex;
    };
};