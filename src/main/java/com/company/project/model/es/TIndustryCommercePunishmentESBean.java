package com.company.project.model.es;

import com.company.project.model.TIndustryCommercePunishment;

import java.util.List;
import java.util.Set;

public class TIndustryCommercePunishmentESBean  extends TIndustryCommercePunishment{
    private List<LawBase> lawBaseList;

    private Set<String> sensitiveWords;

    private long timestamp;


    public List<LawBase> getLawBaseList() {
        return lawBaseList;
    }

    public void setLawBaseList(List<LawBase> lawBaseList) {
        this.lawBaseList = lawBaseList;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Set<String> getSensitiveWords() {
        return sensitiveWords;
    }

    public void setSensitiveWords(Set<String> sensitiveWords) {
        this.sensitiveWords = sensitiveWords;
    }
}