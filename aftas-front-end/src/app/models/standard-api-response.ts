export interface StandardApiResponse {
  status: string;
  message: string;
  errors?: Record<string, string>;
}
