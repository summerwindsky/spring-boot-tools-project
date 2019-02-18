package com.company.project.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "flfg_sorted")
public class FlfgSorted {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 分类id
     */
    private Integer boardid;

    /**
     * 法规分类，中央，北京，安徽，其他
     */
    @Column(name = "fgType")
    private String fgtype;

    /**
     * 效力级别分类
     */
    @Column(name = "effectSort")
    private String effectsort;

    /**
     * 地区划分
     */
    private String district;

    /**
     * 时效性
     */
    @Column(name = "timelinessDic")
    private String timelinessdic;

    /**
     * 发布年份
     */
    private String postyear;

    /**
     * 发布时间
     */
    private Date posttime;

    @Column(name = "documentNO")
    private String documentno;

    /**
     * 发布部门，带层级划分
     */
    @Column(name = "issueDeps")
    private String issuedeps;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取分类id
     *
     * @return boardid - 分类id
     */
    public Integer getBoardid() {
        return boardid;
    }

    /**
     * 设置分类id
     *
     * @param boardid 分类id
     */
    public void setBoardid(Integer boardid) {
        this.boardid = boardid;
    }

    /**
     * 获取法规分类，中央，北京，安徽，其他
     *
     * @return fgType - 法规分类，中央，北京，安徽，其他
     */
    public String getFgtype() {
        return fgtype;
    }

    /**
     * 设置法规分类，中央，北京，安徽，其他
     *
     * @param fgtype 法规分类，中央，北京，安徽，其他
     */
    public void setFgtype(String fgtype) {
        this.fgtype = fgtype;
    }

    /**
     * 获取效力级别分类
     *
     * @return effectSort - 效力级别分类
     */
    public String getEffectsort() {
        return effectsort;
    }

    /**
     * 设置效力级别分类
     *
     * @param effectsort 效力级别分类
     */
    public void setEffectsort(String effectsort) {
        this.effectsort = effectsort;
    }

    /**
     * 获取地区划分
     *
     * @return district - 地区划分
     */
    public String getDistrict() {
        return district;
    }

    /**
     * 设置地区划分
     *
     * @param district 地区划分
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     * 获取时效性
     *
     * @return timelinessDic - 时效性
     */
    public String getTimelinessdic() {
        return timelinessdic;
    }

    /**
     * 设置时效性
     *
     * @param timelinessdic 时效性
     */
    public void setTimelinessdic(String timelinessdic) {
        this.timelinessdic = timelinessdic;
    }

    /**
     * 获取发布年份
     *
     * @return postyear - 发布年份
     */
    public String getPostyear() {
        return postyear;
    }

    /**
     * 设置发布年份
     *
     * @param postyear 发布年份
     */
    public void setPostyear(String postyear) {
        this.postyear = postyear;
    }

    /**
     * 获取发布时间
     *
     * @return posttime - 发布时间
     */
    public Date getPosttime() {
        return posttime;
    }

    /**
     * 设置发布时间
     *
     * @param posttime 发布时间
     */
    public void setPosttime(Date posttime) {
        this.posttime = posttime;
    }

    /**
     * @return documentNO
     */
    public String getDocumentno() {
        return documentno;
    }

    /**
     * @param documentno
     */
    public void setDocumentno(String documentno) {
        this.documentno = documentno;
    }

    /**
     * 获取发布部门，带层级划分
     *
     * @return issueDeps - 发布部门，带层级划分
     */
    public String getIssuedeps() {
        return issuedeps;
    }

    /**
     * 设置发布部门，带层级划分
     *
     * @param issuedeps 发布部门，带层级划分
     */
    public void setIssuedeps(String issuedeps) {
        this.issuedeps = issuedeps;
    }
}