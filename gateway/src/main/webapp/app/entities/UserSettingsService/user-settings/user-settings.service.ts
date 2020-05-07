import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUserSettings } from 'app/shared/model/UserSettingsService/user-settings.model';

type EntityResponseType = HttpResponse<IUserSettings>;
type EntityArrayResponseType = HttpResponse<IUserSettings[]>;

@Injectable({ providedIn: 'root' })
export class UserSettingsService {
  public resourceUrl = SERVER_API_URL + 'services/oauth2service/api/users';

  constructor(protected http: HttpClient) {}

  create(userSettings: IUserSettings): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userSettings);
    return this.http
      .post<IUserSettings>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  upload(userSettings: IUserSettings): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userSettings);
    return this.http
      .post<IUserSettings>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userSettings: IUserSettings): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userSettings);
    return this.http
      .put<IUserSettings>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(login: string): Observable<EntityResponseType> {
    console.log('login:' + login);
    return this.http
      .get<IUserSettings>(`${this.resourceUrl}/${login}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserSettings[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(userSettings: IUserSettings): IUserSettings {
    const copy: IUserSettings = Object.assign({}, userSettings, {
      createdDate: userSettings.createdDate != null && userSettings.createdDate.isValid() ? userSettings.createdDate.toJSON() : null,
      lastModifiedDate:
        userSettings.lastModifiedDate != null && userSettings.lastModifiedDate.isValid() ? userSettings.lastModifiedDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
      res.body.lastModifiedDate = res.body.lastModifiedDate != null ? moment(res.body.lastModifiedDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((userSettings: IUserSettings) => {
        userSettings.createdDate = userSettings.createdDate != null ? moment(userSettings.createdDate) : null;
        userSettings.lastModifiedDate = userSettings.lastModifiedDate != null ? moment(userSettings.lastModifiedDate) : null;
      });
    }
    return res;
  }
}
