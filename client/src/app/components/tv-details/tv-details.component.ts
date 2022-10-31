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

  constructor(private route: ActivatedRoute, private searchSvc: SearchService, private title: Title, private router: Router) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id']
    this.searchSvc.getTV(this.id).then(result => {
      console.log(result);
      if (result.code == 200) {
        this.tv = JSON.parse(result.data)
        this.title.setTitle(this.tv.name)
      }
      else
        this.router.navigate(['/error', result.code])
    }).catch(err => {
      console.error(err)
    })
  }

}
