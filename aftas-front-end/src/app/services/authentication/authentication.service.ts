import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {catchError, map, Observable, of, tap, throwError} from "rxjs";
import {User} from "../../models/user";
import {Response} from "../../models/response";
import {ToastrService} from "ngx-toastr";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private url: string = "http://localhost:8888/api/auth/";
  private _user: User | null = null;

  constructor(private http: HttpClient,
              private toast: ToastrService) {
  }

  set user(user: User) {
    this._user = user;
  }

  get user(): Observable<User> {
    if (!this._user) {
      let userObservable = this.profile();
      userObservable.subscribe(
        (user) => this.user = user
      );
      return userObservable;
    }

    return of(this._user);
  }

  login(user: User): Observable<Response> {
    return this.http.post<Response>(this.url + 'login', user);
  }

  register(user: User): Observable<Response> {
    return this.http.post<Response>(this.url + 'register', user);
  }

  profile(): Observable<User> {
    return this.http.get<User>(this.url + 'info');
  }

  isAuthenticate(): Observable<boolean> {
    let token = localStorage.getItem("access-token");
    let expiration = localStorage.getItem("token-expiration");

    if (token && expiration) {
      const expirationDate = new Date(expiration);
      const now = new Date();

      if (expirationDate >= now) {
        return of(true);
      }

      return this.profile()
        .pipe(
          map(() => true),
          catchError((error: HttpErrorResponse) => {
            if (error.status == 401 || error.status == 403) {
              this.refreshToken().subscribe();
            }
            return throwError(() => new Error("No or Invalid token"));
          })
        );
    }
    this.toast.error("You are not authenticated", "Error")

    return of(false);
  }

  hasRightAuthority(authority: string): Observable<boolean> {
    return this.user.pipe(
      map(user => user.permissions != null && user.permissions.includes(authority)),
      tap((value) => {
          if (!value) {
            this.toast.error("You don't have the right authority", "Error");
          }
        }
      )
    );
  }

  logout() {
    this.http.post(this.url + 'logout', null).subscribe(
      () => {
        this.clearToken();
      }
    );
  }

  refreshToken(): Observable<Response> {
    let refreshToken = localStorage.getItem("refresh-token");
    return this.http.post<Response>(this.url + 'refresh-token', {refreshToken}).pipe(
      tap((response) => {
        localStorage.setItem("access-token", response["access-token"]);
        localStorage.setItem("token-expiration", response["token-expiration"]);
      }),
      catchError((error: HttpErrorResponse) => {
        this.clearToken();
        return throwError(() => new Error("No or Invalid refresh token"));
      })
    );
  }

  clearToken() {
    localStorage.removeItem("access-token");
    localStorage.removeItem("token-expiration");
    localStorage.removeItem("refresh-token");

    this._user = null;
  }
}
