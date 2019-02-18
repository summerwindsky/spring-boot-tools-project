package com.company.project.model;

import javax.persistence.*;

@Table(name = "t_xs_xsgd")
public class TXsXsgd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "xsfbName")
    private String xsfbname;

    @Column(name = "xsfbZuiming")
    private String xsfbzuiming;

    @Column(name = "xsfbZhiyin")
    private String xsfbzhiyin;

    private String xsfbfbyzm;

    @Column(name = "xsfbId")
    private String xsfbid;

    @Column(name = "xsfbContent")
    private String xsfbcontent;

    @Column(name = "xsfbBody")
    private String xsfbbody;

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
     * @return xsfbName
     */
    public String getXsfbname() {
        return xsfbname;
    }

    /**
     * @param xsfbname
     */
    public void setXsfbname(String xsfbname) {
        this.xsfbname = xsfbname;
    }

    /**
     * @return xsfbZuiming
     */
    public String getXsfbzuiming() {
        return xsfbzuiming;
    }

    /**
     * @param xsfbzuiming
     */
    public void setXsfbzuiming(String xsfbzuiming) {
        this.xsfbzuiming = xsfbzuiming;
    }

    /**
     * @return xsfbZhiyin
     */
    public String getXsfbzhiyin() {
        return xsfbzhiyin;
    }

    /**
     * @param xsfbzhiyin
     */
    public void setXsfbzhiyin(String xsfbzhiyin) {
        this.xsfbzhiyin = xsfbzhiyin;
    }

    /**
     * @return xsfbfbyzm
     */
    public String getXsfbfbyzm() {
        return xsfbfbyzm;
    }

    /**
     * @param xsfbfbyzm
     */
    public void setXsfbfbyzm(String xsfbfbyzm) {
        this.xsfbfbyzm = xsfbfbyzm;
    }

    /**
     * @return xsfbId
     */
    public String getXsfbid() {
        return xsfbid;
    }

    /**
     * @param xsfbid
     */
    public void setXsfbid(String xsfbid) {
        this.xsfbid = xsfbid;
    }

    /**
     * @return xsfbContent
     */
    public String getXsfbcontent() {
        return xsfbcontent;
    }

    /**
     * @param xsfbcontent
     */
    public void setXsfbcontent(String xsfbcontent) {
        this.xsfbcontent = xsfbcontent;
    }

    /**
     * @return xsfbBody
     */
    public String getXsfbbody() {
        return xsfbbody;
    }

    /**
     * @param xsfbbody
     */
    public void setXsfbbody(String xsfbbody) {
        this.xsfbbody = xsfbbody;
    }
}