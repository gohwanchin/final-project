import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom } from "rxjs";
import { Response, TVSearchPage } from "../models";

const URL = "http://localhost:8080/api"

@Injectable()
export class SearchService {

    searchPage!: TVSearchPage

    constructor(private http: HttpClient) { }

    searchTV(query: string, page: number = 1) {
        let params = new HttpParams().set("query", query).set("page", page)
        console.debug(params);
        return firstValueFrom(
            this.http.get<Response>(URL + '/search', { params })
        )
    }

    getTV(id: number) {
        const username = this.getUsername()
        return firstValueFrom(
            this.http.post<Response>(URL + `/tv/${id}`, { username })
        )        
    }

    addToWatchlist(id: number) {
        const username = this.getUsername()
        return firstValueFrom(
            this.http.post<Response>(URL + `/tv/${id}/add`, { username })
        )    
    }

    removeFromWatchlist(id: number) {
        const username = this.getUsername()
        return firstValueFrom(
            this.http.post<Response>(URL + `/tv/${id}/remove`, { username })
        )    
    }

    getWatchlist() {
        const username = this.getUsername()
        return firstValueFrom(
            this.http.get<Response>(URL + `/${username}/watchlist`)
        )
    }

    rate(id: number, rate: number) {
        const username = this.getUsername()
        return firstValueFrom(
            this.http.post<Response>(URL + `/tv/${id}/rate`, { username, rate })
        )
    }

    clearRating(id: number) {
        const username = this.getUsername()
        return firstValueFrom(
            this.http.post<Response>(URL + `/tv/${id}/clearRating`, { username })
        )
    }

    getGenres(){
        return firstValueFrom(
            this.http.get<Response>(URL + '/genres')
        )
    }

    private getUsername() {
        return localStorage.getItem("username")
    }
}