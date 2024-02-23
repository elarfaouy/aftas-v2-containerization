export interface User {
  num: number | null;
  name: string | null;
  familyName: string | null;
  username: string;
  password: string;
  role: object | string | null;
  permissions: string[] | null;
}
