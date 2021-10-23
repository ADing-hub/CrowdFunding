package com.ding.crowd.entity;

/**
 * @author Qidong Ding
 * @description TODO：
 * @date 2021-10-22-21:43
 * @since JDK 1.8
 */

public class keCheng {

    private String keChengName;
    private Integer keChengScore;

    public keCheng() {
    }

    public keCheng(String keChengName, Integer keChengScore) {
        this.keChengName = keChengName;
        this.keChengScore = keChengScore;
    }

    public String getKeChengName() {
        return keChengName;
    }

    public void setKeChengName(String keChengName) {
        this.keChengName = keChengName;
    }

    public Integer getKeChengScore() {
        return keChengScore;
    }

    public void setKeChengScore(Integer keChengScore) {
        this.keChengScore = keChengScore;
    }

    @Override
    public String toString() {
        return "keCheng{" +
                "keChengName='" + keChengName + '\'' +
                ", keChengScore=" + keChengScore +
                '}';
    }
}
