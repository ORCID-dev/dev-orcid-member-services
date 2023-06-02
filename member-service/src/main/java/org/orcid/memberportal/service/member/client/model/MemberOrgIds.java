package org.orcid.memberportal.service.member.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class MemberOrgIds {

    @JsonProperty("totalSize")
    private int totalSize;

    @JsonProperty("records")
    private List<MemberOrgId> records;

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<MemberOrgId> getRecords() {
        return records;
    }

    public void setRecords(List<MemberOrgId> records) {
        this.records = records;
    }
}
