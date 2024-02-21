import { User } from './user';

export class Buyer extends User {
  constructor(
    username: string,
    email: string,
    password: string,
    name: string,
    phone: string,
    street: string,
    unitNum: string,
    city: string,
    state: string,
    zipCode: string,
    public maritalStatus: string,
    public dependents: number
  ) {
    super(username, email, password, name, phone, street, unitNum, city, state, zipCode);
  }
}
