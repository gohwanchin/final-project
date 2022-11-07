import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { TVSearchPage } from 'src/app/models';
import { SearchService } from 'src/app/services/search.service';

@Component({
  selector: 'app-results',
  templateUrl: './results.component.html',
  styleUrls: ['./results.component.css']
})
export class ResultsComponent implements OnInit {

  searchPage!: TVSearchPage
  pages: number[] = []

  constructor(private searchSvc: SearchService, private route: ActivatedRoute, private router: Router, private title: Title) { }

  ngOnInit(): void {
    this.searchPage = this.searchSvc.searchPage
    this.title.setTitle("Search results")
    if(!!!this.searchPage) {
      const page = this.route.snapshot.queryParams['page']
      const query = this.route.snapshot.queryParams['query']
      const map = this.route.snapshot.queryParams
      console.info(page, query);
      console.log(map);
      this.searchSvc.searchTV(query, page)
          .then(result => {
            console.debug(result)
            if(result.code == 200){
              const data = JSON.parse(result.data)
              this.searchSvc.searchPage = data
              this.searchPage = data
              this.pages = new Array(this.searchPage.totalPages).fill(0).map((x,i)=>i+1)
            }
          }).catch(err => {
            console.error(err)
          })
    }
    else
      this.pages = new Array(this.searchPage.totalPages).fill(0).map((x,i)=>i+1)
  }

  page(i: number){
    this.searchSvc.searchTV(this.searchPage.query, i)
        .then(result => {
          console.log(result)
          if(result.code == 200){
            const data = JSON.parse(result.data)
            this.searchSvc.searchPage = data
            this.searchPage = data
            this.router.navigate(['/results'], {queryParams : { page: data.page, query: data.query }})
          }
        }).catch(err => {
          console.error(err)
        })
  }
}
