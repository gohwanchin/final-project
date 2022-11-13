import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import emailjs from '@emailjs/browser';
import { Episode, Tv, User } from 'src/app/models';
import { SearchService } from 'src/app/services/search.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-watchlist',
  templateUrl: './watchlist.component.html',
  styleUrls: ['./watchlist.component.css']
})
export class WatchlistComponent implements OnInit {

  watchlist!: Array<Tv>
  nextWeekFrom!: Date
  nextWeekTo!: Date
  user!: User

  constructor(private searchSvc: SearchService, private userSvc: UserService, private title: Title) { }

  ngOnInit(): void {
    this.title.setTitle("Watchlist")
    emailjs.init("gEiJpoC49ABmdgc69")
    this.searchSvc.getWatchlist().then(result => {
      console.debug(result)
      if (result.code == 200)
        this.watchlist = JSON.parse(result.data)
    }).catch(err => {
      console.error(err);
    })
    this.userSvc.getUserDetails().then(result => {
      console.debug(result)
      this.user = JSON.parse(result.data)
    }).catch(err => {
      console.error(err);
    })
  }

  generateSchedule() {
    this.getDates()
    const list = this.watchlist.map(tv => tv.nextEp)
        .filter(ep => ep != null)
        .filter(ep => this.checkAirDate(ep.airDate))
        .sort((a, b) => (a.airDate < b.airDate ? -1 : 1))
    console.log(list);
    const html = this.generateHTML(list)
    const templateParams = {
      username: this.user.username,
      html: html,
      email: this.user.email
    }
    console.info(templateParams)
    emailjs.send("service_dm8zf4h","template_bpvpd49", templateParams).then(result => {
      console.debug(result);
    }).catch(err => {
      console.error();
    })
  }

  close(id: number) {
    console.info(id);
    this.searchSvc.removeFromWatchlist(id).then(result => {
      console.debug(result)
      this.searchSvc.getWatchlist().then(result => {
        console.debug(result)
        if (result.code == 200)
          this.watchlist = JSON.parse(result.data)
      })
    }).catch(err => {
      console.error(err);
    })
  }

  private generateHTML(list: Array<Episode>){
    var currDate = list[0].airDate
    var html = `<h3>${list[0].airDate}</h3><table>`
    list.forEach(ep => {
      console.log(currDate);
      if(ep.airDate != currDate){
        currDate = ep.airDate
        html += `</table><h3>${currDate}</h3><table>`
      }
      html += "<tr><td>"
      html += `<p><img src="${ep.poster}" alt="${ep.showName} poster"></p>`
      html += `<p><strong>${ep.showName}</strong></p>`
      html += "</td><td>"
      html += `<p>Season ${ep.seasonNum} Episode ${ep.episodeNum} - ${ep.name}</p>`
      html += `<p>Overview: ${ep.overview}</p>`
      html += "</td></tr>"
    })
    html += "</table>"
    return html
  }

  private getDates() {
    var today = new Date;
    var nextWeekStart = today.getDate() - today.getDay() + 8;
    this.nextWeekFrom = new Date(today.setDate(nextWeekStart));
    this.nextWeekFrom.setHours(0, 0, 0)
    var nextWeekEnd = today.getDate() - today.getDay() + 7;
    this.nextWeekTo = new Date(today.setDate(nextWeekEnd));
    this.nextWeekTo.setHours(11, 59, 59)

    console.info('nextWeekFrom: ' + this.nextWeekFrom.toString())
    console.info('nextWeekTo  : ' + this.nextWeekTo.toString())
  }

  private checkAirDate(airDate: string) {
    const date = new Date(airDate)
    return date > this.nextWeekFrom && date < this.nextWeekTo
  }
}
