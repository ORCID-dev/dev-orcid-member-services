export class Account {
  constructor(
    public activated: boolean,
    public authorities: string[],
    public email: string,
    public firstName: string,
    public langKey: string,
    public lastName: string,
    public imageUrl: string,
    public salesforceId: string,
    public loggedAs: boolean,
    public mfaEnabled: boolean
  ) {}
}
