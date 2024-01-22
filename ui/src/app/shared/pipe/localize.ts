import { Pipe, PipeTransform } from '@angular/core'
import { AlertType } from 'src/app/app.constants'

@Pipe({
  name: 'localize',
})
export class LocalizePipe implements PipeTransform {
  constructor() {}

  transform(value: string, ...args: any[]): any {
    switch (value) {
      case AlertType.SEND_ACTIVATION_SUCCESS:
        return $localize`:@@gatewayApp.msUserServiceMSUser.sendActivate.success.string:${AlertType.SEND_ACTIVATION_SUCCESS}`
      case AlertType.SEND_ACTIVATION_FAILURE:
        return $localize`:@@gatewayApp.msUserServiceMSUser.sendActivate.error.string:${AlertType.SEND_ACTIVATION_FAILURE}`
    }
  }
}
