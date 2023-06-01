import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared';

import {
  PasswordStrengthBarComponent,
  ActivateComponent,
  PasswordComponent,
  PasswordResetInitComponent,
  PasswordResetFinishComponent,
  SettingsComponent,
  accountState,
} from './';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(accountState)],
  declarations: [
    ActivateComponent,
    PasswordComponent,
    PasswordStrengthBarComponent,
    PasswordResetInitComponent,
    PasswordResetFinishComponent,
    SettingsComponent,
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class GatewayAccountModule {}
