package com.cncounter.util.bytecode;


import java.io.Serializable;

/**
 * 字节码示例模型-仅作示例使用
 */
public final class ByteCodeModel extends Object implements Serializable {

    public static final String NAME_DEFAULT = "测试用户";
    //
    private int id = 0;
    private float hight = 0.0F;
    private double money = 0.0D;
    private long ts = 0L;
    public String name = NAME_DEFAULT;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getHight() {
        return this.hight;
    }

    public void setHight(float hight) {
        this.hight = hight;
    }

    public double getMoney() {
        return this.money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public long getTs() {
        return this.ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }
}
