package org.orcid.memberportal.service.member.web.rest;

import org.orcid.memberportal.service.member.services.ReportService;
import org.orcid.memberportal.service.member.services.UserService;
import org.orcid.memberportal.service.member.services.pojo.MemberServiceUser;
import org.orcid.memberportal.service.member.services.pojo.ReportInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for requesting JWTs for chartio reports.
 */
@RestController
@RequestMapping("/api")
public class ReportResource {

  private final Logger LOG = LoggerFactory.getLogger(ReportResource.class);

  @Autowired
  private ReportService reportService;

  @Autowired
  private UserService userService;

  @GetMapping("/reports/member")
  public ResponseEntity<ReportInfo> getMemberReport() {
    MemberServiceUser user = userService.getLoggedInUser();
    LOG.info("Generating member report for user {} of {}", user.getEmail(), user.getMemberName());
    ReportInfo memberReportInfo = reportService.getMemberReportInfo();
    return ResponseEntity.ok(memberReportInfo);
  }

  @GetMapping("/reports/integration")
  public ResponseEntity<ReportInfo> getIntegrationReport() {
    MemberServiceUser user = userService.getLoggedInUser();
    LOG.info("Generating integration report for user {} of {}", user.getEmail(), user.getMemberName());
    ReportInfo memberReportInfo = reportService.getIntegrationReportInfo();
    return ResponseEntity.ok(memberReportInfo);
  }

  @GetMapping("/reports/consortia")
  public ResponseEntity<ReportInfo> getConsortiaReport() {
    MemberServiceUser user = userService.getLoggedInUser();
    LOG.info("Generating consortium report for user {} of {}", user.getEmail(), user.getMemberName());
    ReportInfo consortiumReportInfo = reportService.getConsortiaReportInfo();
    return ResponseEntity.ok(consortiumReportInfo);
  }

  @GetMapping("/reports/affiliation")
  public ResponseEntity<ReportInfo> getAffiliationReport() {
    MemberServiceUser user = userService.getLoggedInUser();
    LOG.info("Generating affiliation report for user {} of {}", user.getEmail(), user.getMemberName());
    ReportInfo affiliationReportInfo = reportService.getAffiliationReportInfo();
    return ResponseEntity.ok(affiliationReportInfo);
  }

  @GetMapping("/reports/consortia-member-affiliations")
  public ResponseEntity<ReportInfo> getConsortiaMemberAffiliationsReport() {
    MemberServiceUser user = userService.getLoggedInUser();
    LOG.info("Generating consortia member affiliations report for user {} of {}", user.getEmail(), user.getMemberName());
    ReportInfo reportInfo = reportService.getConsortiaMemberAffiliationsReportInfo();
    return ResponseEntity.ok(reportInfo);
  }
}
