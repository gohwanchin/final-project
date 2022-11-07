import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { Tv } from 'src/app/models';
import { SearchService } from 'src/app/services/search.service';

@Component({
  selector: 'app-tv-details',
  templateUrl: './tv-details.component.html',
  styleUrls: ['./tv-details.component.css']
})
export class TvDetailsComponent implements OnInit {

  id!: number
  tv!: Tv
  added: boolean = false

  constructor(private route: ActivatedRoute, private searchSvc: SearchService, private title: Title, private router: Router) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id']
    this.searchSvc.getTV(this.id).then(result => {
      console.debug(result);
      if (result.code == 200) {
        const data = JSON.parse(result.data)
        this.tv = data.details
        this.added = data.added
        this.title.setTitle(this.tv.name)
      }
      else
        this.router.navigate(['/error', result.code])
    }).catch(err => {
      console.error(err)
    })
  }

  add() {
    this.searchSvc.addToWatchlist(this.id).then(result => {
      console.info(result.message)
      if (result.code == 200)
        this.added = true
    }).catch(err => {
      console.error(err);
    })
  }

  remove() {
    this.searchSvc.removeFromWatchlist(this.id).then(result => {
      console.info(result.message)
      if (result.code == 200)
        this.added = false
    }).catch(err => {
      console.error(err);
    })
  }
}
