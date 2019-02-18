package com.company.project.model;

import javax.persistence.*;

@Table(name = "t_qkwz")
public class TQkwz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Journal_Id")
    private Integer journalId;

    @Column(name = "Info_Id")
    private Integer infoId;

    @Column(name = "Info_Ctitle")
    private String infoCtitle;

    @Column(name = "Info_Author")
    private String infoAuthor;

    @Column(name = "Info_AuthorUnit")
    private String infoAuthorunit;

    @Column(name = "Info_Kind")
    private String infoKind;

    @Column(name = "Info_Ckeyword")
    private String infoCkeyword;

    @Column(name = "Info_Csummary")
    private String infoCsummary;

    @Column(name = "Info_Reference")
    private String infoReference;

    @Column(name = "Content_Id")
    private Integer contentId;

    @Column(name = "Info_Remark")
    private String infoRemark;

    @Column(name = "Content_Txt")
    private String contentTxt;

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
     * @return Journal_Id
     */
    public Integer getJournalId() {
        return journalId;
    }

    /**
     * @param journalId
     */
    public void setJournalId(Integer journalId) {
        this.journalId = journalId;
    }

    /**
     * @return Info_Id
     */
    public Integer getInfoId() {
        return infoId;
    }

    /**
     * @param infoId
     */
    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    /**
     * @return Info_Ctitle
     */
    public String getInfoCtitle() {
        return infoCtitle;
    }

    /**
     * @param infoCtitle
     */
    public void setInfoCtitle(String infoCtitle) {
        this.infoCtitle = infoCtitle;
    }

    /**
     * @return Info_Author
     */
    public String getInfoAuthor() {
        return infoAuthor;
    }

    /**
     * @param infoAuthor
     */
    public void setInfoAuthor(String infoAuthor) {
        this.infoAuthor = infoAuthor;
    }

    /**
     * @return Info_AuthorUnit
     */
    public String getInfoAuthorunit() {
        return infoAuthorunit;
    }

    /**
     * @param infoAuthorunit
     */
    public void setInfoAuthorunit(String infoAuthorunit) {
        this.infoAuthorunit = infoAuthorunit;
    }

    /**
     * @return Info_Kind
     */
    public String getInfoKind() {
        return infoKind;
    }

    /**
     * @param infoKind
     */
    public void setInfoKind(String infoKind) {
        this.infoKind = infoKind;
    }

    /**
     * @return Info_Ckeyword
     */
    public String getInfoCkeyword() {
        return infoCkeyword;
    }

    /**
     * @param infoCkeyword
     */
    public void setInfoCkeyword(String infoCkeyword) {
        this.infoCkeyword = infoCkeyword;
    }

    /**
     * @return Info_Csummary
     */
    public String getInfoCsummary() {
        return infoCsummary;
    }

    /**
     * @param infoCsummary
     */
    public void setInfoCsummary(String infoCsummary) {
        this.infoCsummary = infoCsummary;
    }

    /**
     * @return Info_Reference
     */
    public String getInfoReference() {
        return infoReference;
    }

    /**
     * @param infoReference
     */
    public void setInfoReference(String infoReference) {
        this.infoReference = infoReference;
    }

    /**
     * @return Content_Id
     */
    public Integer getContentId() {
        return contentId;
    }

    /**
     * @param contentId
     */
    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    /**
     * @return Info_Remark
     */
    public String getInfoRemark() {
        return infoRemark;
    }

    /**
     * @param infoRemark
     */
    public void setInfoRemark(String infoRemark) {
        this.infoRemark = infoRemark;
    }

    /**
     * @return Content_Txt
     */
    public String getContentTxt() {
        return contentTxt;
    }

    /**
     * @param contentTxt
     */
    public void setContentTxt(String contentTxt) {
        this.contentTxt = contentTxt;
    }
}