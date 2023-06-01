package org.orcid.memberportal.service.member.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ConsortiumLeadDetails extends MemberDetails {

  @JsonProperty("consortiumOpportunities")
  private List<ConsortiumMember> consortiumMembers;

  public List<ConsortiumMember> getConsortiumMembers() {
    return consortiumMembers;
  }

  public void setConsortiumMembers(List<ConsortiumMember> consortiumMembers) {
    this.consortiumMembers = consortiumMembers;
  }
}
