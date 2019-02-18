package com.company.project.other.fgmc;

import java.util.List;

/**
 * Title:
 * Description:
 * Company: 北京华宇元典信息服务有限公司
 *
 * @author zhangjing
 * @version 1.0
 * @date 2018-11-05 14:16
 */
public class JsonBean {

    /**
     * Code : OK
     * Message : 成功
     * Data : [{"Title":"最高人民法院执行工作办公室主任俞灵雨在全国高级人民法院执行局(庭)长座谈会上的总结讲话"}]
     */

    private String Code;
    private String Message;
    private List<DataBean> Data;

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * Title : 最高人民法院执行工作办公室主任俞灵雨在全国高级人民法院执行局(庭)长座谈会上的总结讲话
         */

        private String Title;

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }
    }
}
