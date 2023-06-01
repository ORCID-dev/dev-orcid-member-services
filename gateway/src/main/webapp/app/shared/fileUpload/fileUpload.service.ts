import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';

@Injectable()
export class FileUploadService {
  constructor(private http: HttpClient) {}

  uploadFile(resourceUrl, file: File, expectedResponseType): Observable<HttpEvent<Boolean>> {
    const formdata: FormData = new FormData();
    formdata.append('file', file);
    const req = new HttpRequest('POST', resourceUrl, formdata, {
      reportProgress: true,
      responseType: expectedResponseType,
    });
    return this.http.request(req);
  }
}
