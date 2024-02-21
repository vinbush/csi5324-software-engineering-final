import { User } from './user';

export class Realtor extends User {
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
    public agency: string,
    public yearsExperience: number,
    public brokerage: string,
    public website: string
  ) {
    super(username, email, password, name, phone, street, unitNum, city, state, zipCode);
  }
}
