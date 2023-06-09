import { Component, OnInit } from '@angular/core';
import { ReportService } from './report.service';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'jhi-report',
  templateUrl: './report.component.html',
  styleUrls: ['report.scss']
})
export class ReportComponent implements OnInit {
  reportSrc: any;
  reportType: string;

  constructor(private reportService: ReportService, private sanitizer: DomSanitizer, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(data => {
      this.reportType = data.reportType;

      this.reportService.getDashboardInfo(this.reportType).subscribe(res => {
        const url = res.body.url;
        const token = res.body.jwt;
        this.reportSrc = this.safeUrl(url + '?_token=' + token);
        console.log('reportSrc set to ' + this.reportSrc);
      });
    });
  }

  safeUrl(url: string) {
    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }
}
