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
        console.log(params);
        return firstValueFrom(
            this.http.get<Response>(URL + '/search', { params })
        )
    }

    getTV(id: number) {
        return firstValueFrom(
            this.http.get<Response>(URL + `/tv/${id}`)
        )        
    }
}