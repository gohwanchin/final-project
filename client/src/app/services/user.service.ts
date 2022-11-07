import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom } from "rxjs";
import { Response } from "../models";

const URL = "http://localhost:8080/api/user"

@Injectable()
export class UserService {

    constructor(private http: HttpClient) {}

    uploadProfile(file: File | Blob) {
        const data = new FormData()
        data.set('file', file)
        data.set('username', this.getUsername()!)
        return firstValueFrom(this.http.post<Response>(URL + '/upload', data))
    }

    deleteProfile() {
        const username = this.getUsername()
        return firstValueFrom(this.http.delete<Response>(URL + `/${username}/delete`))
    }

    getProfileURL() {
        return URL + '/p/' + this.getUsername()
    }

    getUsername() {
        return localStorage.getItem("username")
    }
}