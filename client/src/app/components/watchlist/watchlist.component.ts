import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Tv } from 'src/app/models';
import { SearchService } from 'src/app/services/search.service';

@Component({
  selector: 'app-watchlist',
  templateUrl: './watchlist.component.html',
  styleUrls: ['./watchlist.component.css']
})
export class WatchlistComponent implements OnInit {

  watchlist!: Array<Tv>

  constructor(private searchSvc: SearchService, private title: Title) { }

  ngOnInit(): void {
    this.title.setTitle("Watchlist")
    this.searchSvc.getWatchlist().then(result => {
      console.debug(result)
      if (result.code == 200)
        this.watchlist = JSON.parse(result.data)
    }).catch(err => {
      console.error(err);
    })
  }

}
