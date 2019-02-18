package com.company.project.other.ft.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Flfg {
    private String Gid;//唯一标识id
    private String ImplementDate;//实施日期
    private String IssueDate;//发布日期
    private String Title_ImplementDate;//存title+实施日期
    private String Title;//只存title
    private String Category;//法规类别(子类别)
    private String DocumentNO;//发文字号
    private String IssueDepartment;//发布部门
    private String TimelinessDic;//时效性
    private String Library;//效力级别主类别库名
    private String Effectiveness;//效力级别主类别(中文）
    private String EffectiveShow;//效力级别子类别(中文）
}