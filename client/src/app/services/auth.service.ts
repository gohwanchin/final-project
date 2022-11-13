import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom, tap } from "rxjs";
import { User } from "../models";

const URL = "http://localhost:8080"

@Injectable()
export class AuthService {

    constructor(private http: HttpClient) { }

    login(username: string, password: string) {
        return firstValueFrom(this.http.post<any>(URL + '/authenticate', { username, password }).pipe(
            tap(res => {
                console.log(res.token)
                localStorage.setItem('token', res.token)
                localStorage.setItem('username', username)
            })
        ))
    }

    logout() {
        localStorage.removeItem('token')
        localStorage.removeItem('username')
    }

    isLoggedIn() {
        return !!localStorage.getItem('token')
    }

    register(user: User) {
        return firstValueFrom(this.http.post(URL + '/register', user))
    }
}