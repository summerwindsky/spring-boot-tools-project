package com.company.project.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_dangjifagui_topic")
public class TDangjifaguiTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer siteid;

    private Integer boardid;

    private String title;

    private String url;

    /**
     * 法规类别
     */
    @Column(name = "Category")
    private String category;

    /**
     * 发文字号
     */
    @Column(name = "DocumentNO")
    private String documentno;

    /**
     * 发布部门
     */
    @Column(name = "IssueDepartment")
    private String issuedepartment;

    /**
     * 时效性
     */
    @Column(name = "TimelinessDic")
    private String timelinessdic;

    /**
     * 效力级别
     */
    @Column(name = "EffectivenessDic")
    private String effectivenessdic;

    private Date posttime;

    private Integer status;

    @Column(name = "CTIME")
    private Date ctime;

    @Column(name = "UTIME")
    private Date utime;

    private String description;

    private String content;

    private String htmlcontent;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return siteid
     */
    public Integer getSiteid() {
        return siteid;
    }

    /**
     * @param siteid
     */
    public void setSiteid(Integer siteid) {
        this.siteid = siteid;
    }

    /**
     * @return boardid
     */
    public Integer getBoardid() {
        return boardid;
    }

    /**
     * @param boardid
     */
    public void setBoardid(Integer boardid) {
        this.boardid = boardid;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取法规类别
     *
     * @return Category - 法规类别
     */
    public String getCategory() {
        return category;
    }

    /**
     * 设置法规类别
     *
     * @param category 法规类别
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 获取发文字号
     *
     * @return DocumentNO - 发文字号
     */
    public String getDocumentno() {
        return documentno;
    }

    /**
     * 设置发文字号
     *
     * @param documentno 发文字号
     */
    public void setDocumentno(String documentno) {
        this.documentno = documentno;
    }

    /**
     * 获取发布部门
     *
     * @return IssueDepartment - 发布部门
     */
    public String getIssuedepartment() {
        return issuedepartment;
    }

    /**
     * 设置发布部门
     *
     * @param issuedepartment 发布部门
     */
    public void setIssuedepartment(String issuedepartment) {
        this.issuedepartment = issuedepartment;
    }

    /**
     * 获取时效性
     *
     * @return TimelinessDic - 时效性
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
     * 获取效力级别
     *
     * @return EffectivenessDic - 效力级别
     */
    public String getEffectivenessdic() {
        return effectivenessdic;
    }

    /**
     * 设置效力级别
     *
     * @param effectivenessdic 效力级别
     */
    public void setEffectivenessdic(String effectivenessdic) {
        this.effectivenessdic = effectivenessdic;
    }

    /**
     * @return posttime
     */
    public Date getPosttime() {
        return posttime;
    }

    /**
     * @param posttime
     */
    public void setPosttime(Date posttime) {
        this.posttime = posttime;
    }

    /**
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return CTIME
     */
    public Date getCtime() {
        return ctime;
    }

    /**
     * @param ctime
     */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /**
     * @return UTIME
     */
    public Date getUtime() {
        return utime;
    }

    /**
     * @param utime
     */
    public void setUtime(Date utime) {
        this.utime = utime;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return htmlcontent
     */
    public String getHtmlcontent() {
        return htmlcontent;
    }

    /**
     * @param htmlcontent
     */
    public void setHtmlcontent(String htmlcontent) {
        this.htmlcontent = htmlcontent;
    }
}