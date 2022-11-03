import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, firstValueFrom, tap } from "rxjs";

const URL = "http://localhost:8080"

@Injectable()
export class AuthService {

    private _isLoggedIn$ = new BehaviorSubject<boolean>(false)
    isLoggedIn$ = this._isLoggedIn$.asObservable()
    
    constructor(private http: HttpClient) {}

    login(username: string, password: string) {
        return this.http.post<any>(URL + '/authenticate', { username, password }).pipe(
            tap(res => {
                console.log(res.token)
                localStorage.setItem('token', res.token)
                localStorage.setItem('username', username)
                this._isLoggedIn$.next(true)
            })
        )
    }

    logout() {
        localStorage.removeItem('token')
        localStorage.removeItem('username')
    }

    isLoggedIn() {
        return !!localStorage.getItem('token')
    }

    register(username: string, password: string) {
        return firstValueFrom(this.http.post(URL + '/register', {username, password }))
    }
}