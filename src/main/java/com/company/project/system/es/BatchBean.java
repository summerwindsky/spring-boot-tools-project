package com.company.project.system.es;

/**
 * Title: BatchBean
 * Description: 
 * Company: 北京华宇元典信息服务有限公司
 *
 * @author lyf
 * @version 1.0
 * @date 2017年3月31日 上午9:48:39
 *
 */
public class BatchBean {
    private String index;

    private String type;

    private String fid;

    private String id;

    private Object json;

    private TypeEnum flag;

    /**
     * @param index
     * @param type
     * @param fid
     * @param id
     * @param json
     * @param flag
     */
    private BatchBean(String index, String type, String fid, String id, Object json, TypeEnum flag) {
        super();
        this.index = index;
        this.type = type;
        this.fid = fid;
        this.id = id;
        this.json = json;
        this.flag = flag;
    }

    /**
     * UPDATE
     * @param index
     * @param type
     * @param id
     * @param json
     */
    public BatchBean(String index, String type, String id, Object json) {
        this(index, type, null, id, json, TypeEnum.UPDATE);
    }

    /**
     * INSERT
     * @param index
     * @param type
     * @param fid
     * @param id
     * @param json
     */
    public BatchBean(String index, String type, String fid, String id, Object json) {
        this(index, type, fid, id, json, TypeEnum.INSERT);
    }

    /**
     * DELETE
     * @param index
     * @param type
     * @param id
     */
    public BatchBean(String index, String type, String id) {
        this(index, type, null, id, null, TypeEnum.DELETE);
    }

    public BatchBean() {
        super();
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getJson() {
        return json;
    }

    public void setJson(Object json) {
        this.json = json;
    }

    public TypeEnum getFlag() {
        return flag;
    }

    public void setFlag(TypeEnum flag) {
        this.flag = flag;
    }

    enum TypeEnum {
        DELETE, INSERT, UPDATE;
    }
}
