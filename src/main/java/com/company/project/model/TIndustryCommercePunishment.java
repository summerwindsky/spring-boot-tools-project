package com.company.project.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_industry_commerce_punishment")
public class TIndustryCommercePunishment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String number;

    private String title;

    @Column(name = "wolters_link")
    private String woltersLink;

    private String institution;

    private String area;

    private String caseflag;

    @Column(name = "punish_time")
    private Date punishTime;

    private String link;

    private String seachinfo;

    @Column(name = "pdf_link")
    private String pdfLink;

    @Column(name = "pdf_path")
    private String pdfPath;

    private Integer status;

    @Column(name = "CTIME")
    private Date ctime;

    @Column(name = "UTIME")
    private Date utime;

    @Column(name = "law_base")
    private String lawBase;

    @Column(name = "pdf_format_content")
    private String pdfFormatContent;

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
     * @return number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number
     */
    public void setNumber(String number) {
        this.number = number;
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
     * @return wolters_link
     */
    public String getWoltersLink() {
        return woltersLink;
    }

    /**
     * @param woltersLink
     */
    public void setWoltersLink(String woltersLink) {
        this.woltersLink = woltersLink;
    }

    /**
     * @return institution
     */
    public String getInstitution() {
        return institution;
    }

    /**
     * @param institution
     */
    public void setInstitution(String institution) {
        this.institution = institution;
    }

    /**
     * @return area
     */
    public String getArea() {
        return area;
    }

    /**
     * @param area
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * @return caseflag
     */
    public String getCaseflag() {
        return caseflag;
    }

    /**
     * @param caseflag
     */
    public void setCaseflag(String caseflag) {
        this.caseflag = caseflag;
    }

    /**
     * @return punish_time
     */
    public Date getPunishTime() {
        return punishTime;
    }

    /**
     * @param punishTime
     */
    public void setPunishTime(Date punishTime) {
        this.punishTime = punishTime;
    }

    /**
     * @return link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return seachinfo
     */
    public String getSeachinfo() {
        return seachinfo;
    }

    /**
     * @param seachinfo
     */
    public void setSeachinfo(String seachinfo) {
        this.seachinfo = seachinfo;
    }

    /**
     * @return pdf_link
     */
    public String getPdfLink() {
        return pdfLink;
    }

    /**
     * @param pdfLink
     */
    public void setPdfLink(String pdfLink) {
        this.pdfLink = pdfLink;
    }

    /**
     * @return pdf_path
     */
    public String getPdfPath() {
        return pdfPath;
    }

    /**
     * @param pdfPath
     */
    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
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
     * @return law_base
     */
    public String getLawBase() {
        return lawBase;
    }

    /**
     * @param lawBase
     */
    public void setLawBase(String lawBase) {
        this.lawBase = lawBase;
    }

    /**
     * @return pdf_format_content
     */
    public String getPdfFormatContent() {
        return pdfFormatContent;
    }

    /**
     * @param pdfFormatContent
     */
    public void setPdfFormatContent(String pdfFormatContent) {
        this.pdfFormatContent = pdfFormatContent;
    }
}