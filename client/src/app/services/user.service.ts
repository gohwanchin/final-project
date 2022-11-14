import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom } from "rxjs";
import { Response } from "../models";

const URL = "https://tv-trakr.herokuapp.com/api/user"

@Injectable()
export class UserService {

    constructor(private http: HttpClient) {}

    uploadProfile(file: File | Blob) {
        const data = new FormData()
        data.set('file', file)
        data.set('username', this.getUsername()!)
        return firstValueFrom(this.http.post<Response>(URL + '/upload', data))
    }

    getUserDetails(){
        const username = this.getUsername()
        return firstValueFrom(this.http.get<Response>(URL + `/${username}`))
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