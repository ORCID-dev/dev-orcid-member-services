import { HttpErrorResponse } from '@angular/common/http'
import { ErrorHandler, Injectable } from '@angular/core'
import { Observable, Subject } from 'rxjs'
import { AppError } from '../model/error.model'

// To inject this service, you have to include '@Inject(ErrorHandler)' to be able to subscribe to observables, e.g.:
// @Inject(ErrorHandler) private errorService: ErrorService

@Injectable({ providedIn: 'root' })
export class ErrorService implements ErrorHandler {
  private errors: Subject<any> = new Subject<any>()

  handleError(error: any) {
    console.log(error)
    if (error instanceof HttpErrorResponse) {
      this.errors.next(new AppError(error.status, error.error.title || error.message))
    } else {
      console.error('Unknown error occurred', error)
    }
  }

  on(): Observable<any> {
    return this.errors.asObservable()
  }
}
