import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core'
import { RouterModule } from '@angular/router'
import { JhiLanguageService } from 'ng-jhipster'
import { ClipboardModule } from 'ngx-clipboard'
import { JhiLanguageHelper } from 'app/core'
import { GatewaySharedModule } from 'app/shared'
import {
  AssertionComponent,
  AssertionDetailComponent,
  AssertionUpdateComponent,
  AssertionDeletePopupComponent,
  AssertionDeleteDialogComponent,
  AssertionImportPopupComponent,
  AssertionImportDialogComponent,
  SendNotificationsPopupComponent,
  SendNotificationsDialogComponent,
  assertionRoute,
  assertionPopupRoute,
} from './'

const ENTITY_STATES = [...assertionRoute, ...assertionPopupRoute]

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(ENTITY_STATES), ClipboardModule],
  declarations: [
    AssertionComponent,
    AssertionDetailComponent,
    AssertionUpdateComponent,
    AssertionDeleteDialogComponent,
    AssertionDeletePopupComponent,
    AssertionImportPopupComponent,
    AssertionImportDialogComponent,
    SendNotificationsPopupComponent,
    SendNotificationsDialogComponent,
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AffiliationModule {
  constructor(
    private languageService: JhiLanguageService,
    private languageHelper: JhiLanguageHelper
  ) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey)
      }
    })
  }
}
