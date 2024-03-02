import { FormControl } from '@angular/forms'

export enum EventType {
  LOG_IN_SUCCESS = 'LOG_IN_SUCCESS',
  AFFILIATION_CREATED = 'AFFILIATION_CREATED',
  AFFILIATION_UPDATED = 'AFFILIATION_UPDATED',
  USER_LIST_MODIFIED = 'USER_LIST_MODIFIED',
  AFFILIATION_LIST_MODIFICATION = 'AFFILIATION_LIST_MODIFICATION',
  IMPORT_AFFILIATIONS = 'IMPORT_AFFILIATIONS',
  SEND_NOTIFICATIONS = 'SEND_NOTIFICATIONS',
}

export enum AlertType {
  SEND_ACTIVATION_SUCCESS = 'Invite sent.',
  SEND_ACTIVATION_FAILURE = 'Invite email couldn`t be sent.',
  USER_CREATED = 'User created. Invite sent.',
  USER_UPDATED = 'User updated successfully',
  USER_DELETED = 'User deleted successfully',
}

export const EMAIL_NOT_FOUND_TYPE = 'https://www.jhipster.tech/problem/email-not-found'

export const DATE_FORMAT = 'YYYY-MM-DD'
export const DATE_TIME_FORMAT = 'YYYY-MM-DDTHH:mm'

export const ITEMS_PER_PAGE = 20

export function emailValidator(control: FormControl): { [key: string]: any } | null {
  const emailRegexp =
    // eslint-disable-next-line
    /^([^@\s/."'\(\)\[\]\{\}\\/,:;]+\.)*([^@\s\."\(\)\[\]\{\}\\/,:;]|(".+"))+@[^@\s\."'\(\)\[\]\{\}\\/,:;]+(\.[^@\s\."'\(\)\[\]\{\}\\/,:;]{2,})+$/
  if (control.value && !emailRegexp.test(control.value)) {
    return { invalidEmail: true }
  }
  return null
}

export const ORCID_BASE_URL = process.env['ORCID_BASE_URL']
