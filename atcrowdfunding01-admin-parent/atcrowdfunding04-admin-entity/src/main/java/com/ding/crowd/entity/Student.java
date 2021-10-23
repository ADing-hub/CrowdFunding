package com.ding.crowd.entity;

import java.util.List;
import java.util.Map;

/**
 * @author Qidong Ding
 * @description TODO：
 * @date 2021-10-22-21:39
 * @since JDK 1.8
 */

public class Student {

    private Integer stuId;
    private String stuName;
    private Address address;
    private List<keCheng> keChengList;
    private Map<String, String> map;

    public Student() {
    }

    public Student(Integer stuId, String stuName, Address address, List<keCheng> keChengList, Map<String, String> map) {
        this.stuId = stuId;
        this.stuName = stuName;
        this.address = address;
        this.keChengList = keChengList;
        this.map = map;
    }

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<keCheng> getKeChengList() {
        return keChengList;
    }

    public void setKeChengList(List<keCheng> keChengList) {
        this.keChengList = keChengList;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "Student{" +
                "stuId=" + stuId +
                ", stuName='" + stuName + '\'' +
                ", address=" + address +
                ", keChengList=" + keChengList +
                ", map=" + map +
                '}';
    }
}


