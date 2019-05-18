import { User } from './user';

export class Kweet {
  id: number;
  text: string;
  time: Date;
  author: User;
}
