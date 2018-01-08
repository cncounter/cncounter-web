package com.cncounter.test.string;

import java.util.*;

/**
 * 简单计算文本相似度
 */
public class CalcStringSimilar {
    // 单位
    public static class Unit{
        private Integer id;
        private String name;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Unit{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static class UnitSimilarWrap{
        //
        public Unit unit1;
        public Unit unit2;
        public Integer similarPercent;

        public Unit getUnit1() {
            return unit1;
        }

        public void setUnit1(Unit unit1) {
            this.unit1 = unit1;
        }

        public Unit getUnit2() {
            return unit2;
        }

        public void setUnit2(Unit unit2) {
            this.unit2 = unit2;
        }

        public Integer getSimilarPercent() {
            return similarPercent;
        }

        public void setSimilarPercent(Integer similarPercent) {
            this.similarPercent = similarPercent;
        }

        @Override
        public String toString() {
            return "相似度=" + similarPercent + "%;" +
                    "unit1=" + unit1 +
                    "; unit2=" + unit2 ;
        }
    }


    public static void main(String[] args) {
        //
        List<Unit> unitList =  getTestData();
        // 计算相似度
        List<UnitSimilarWrap> unitSimilarList = parseUnitSimilarList(unitList);
        for(UnitSimilarWrap wrap : unitSimilarList){
            System.out.println(wrap);
        }

    }

    private static List<UnitSimilarWrap> parseUnitSimilarList(List<Unit> unitList) {
        List<UnitSimilarWrap> unitSimilarList = new ArrayList<UnitSimilarWrap>();
        //
        Set<Unit> processedUnitSet = new HashSet<Unit>();
        //
        for(Unit unit1 : unitList){
            //
            processedUnitSet.add(unit1);
            //
            for(Unit unit2 : unitList){
                //已经比较过
                if(processedUnitSet.contains(unit2)){
                    continue;
                }
                //
                String name1 = unit1.getName();
                String name2 = unit2.getName();
                //
                int similarPercent = calStringSimilar(name1, name2);
                //
                UnitSimilarWrap wrap = new UnitSimilarWrap();
                wrap.setUnit1(unit1);
                wrap.setUnit2(unit2);
                wrap.setSimilarPercent(similarPercent);
                //
                unitSimilarList.add(wrap);
            }
        }
        // 排序
        Collections.sort(unitSimilarList, new Comparator<UnitSimilarWrap>() {
            @Override
            public int compare(UnitSimilarWrap w1, UnitSimilarWrap w2) {
                // 升序
                //return w1.getSimilarPercent() - w2.getSimilarPercent();
                // 降序
                return w2.getSimilarPercent() - w1.getSimilarPercent();
            }
        });

        //
        return unitSimilarList;
    }

    public static List<Unit> getTestData(){

        //
        Unit unit1 = new Unit();
        unit1.setId(1);
        unit1.setName("北京第1医院");
        //
        Unit unit2 = new Unit();
        unit2.setId(2);
        unit2.setName("北京第2医院2");
        //
        Unit unit3 = new Unit();
        unit3.setId(3);
        unit3.setName("北京第3医院");
        //
        Unit unit4 = new Unit();
        unit4.setId(4);
        unit4.setName("北京第4医院44");
        //
        Unit unit5 = new Unit();
        unit5.setId(5);
        unit5.setName("北京第5医院44");
        //
        List<Unit> unitList = new ArrayList<Unit>();
        //
        unitList.add(unit1);
        unitList.add(unit2);
        unitList.add(unit3);
        unitList.add(unit4);
        unitList.add(unit5);

        //
        return unitList;
    }




    public static int calStringSimilar(String name1, String name2) {
        int similarPercent = 0;
        //
        name1 = trimAllBlank(name1);
        name2 = trimAllBlank(name2);
        //
        int len1 = name1.length();
        int len2 = name2.length();
        // 空字符串
        if(len1 < 1 || len2 < 1){
            return similarPercent;
        }
        // 计算每个字出现的次数
        Map<String, Integer> wordNumMap1 = parseWordNumber(name1);
        Map<String, Integer> wordNumMap2 = parseWordNumber(name2);
        // 计算相似的字数
        int similarNum = calMapSimilarNum(wordNumMap1, wordNumMap2);
        int maxLen = Math.max(len1, len2);
        //
        if(similarNum < 1){
            return similarPercent;
        }
        //
        Long similarPercentD = Math.round(100.0 * similarNum / maxLen);
        similarPercent = similarPercentD.intValue();
        //
        return similarPercent;
    }

    // 计算相似的字数
    public static int calMapSimilarNum(Map<String, Integer> wordNumMap1, Map<String, Integer> wordNumMap2) {
        int similarNum = 0;
        if(null == wordNumMap1 || null==wordNumMap2
                || wordNumMap1.isEmpty() || wordNumMap2.isEmpty()){
            return similarNum;
        }
        //
        Set<String> keySet1 = wordNumMap1.keySet();
        Set<String> keySet2 = wordNumMap2.keySet();
        //
        for(String key : keySet1){
            //
            if(wordNumMap2.containsKey(key)){
                //
                Integer num1 = wordNumMap1.get(key);
                Integer num2 = wordNumMap2.get(key);
                //
                if(null == num1 || null == num2){
                   continue;
                }
                // 增加相同字数的数量
                similarNum += Math.min(num1, num2);
            }
        }

        //
        return similarNum;
    }

    // 计算每个字出现的次数
    public static Map<String, Integer> parseWordNumber(String name) {
        Map<String, Integer> wordNumMap = new HashMap<String, Integer>();
        //
        if(null == name){
            name = "";
        }
        // 去除中英文空格
        name = trimAllBlank(name);
        // 计算每个字出现的顺序
        int len = name.length();
        for(int i = 0; i< len; i++){
            //
            String word = name.substring(i, i+1);
            //
            Integer num = wordNumMap.get(word);
            if(null == num){
                num = Integer.valueOf(0);
            }
            // 数量 + 1
            num = num + 1;
            //
            wordNumMap.put(word, num);
        }
        //
        return wordNumMap;
    }

    // 去除中英文空格
    public static String trimAllBlank(String name) {
        if(null == name){
            name = "";
        }
        name = name.replace(" ", "").replace("　", "").trim();
        //
        return name;
    }
}
