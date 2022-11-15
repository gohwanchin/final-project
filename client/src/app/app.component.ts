import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from './services/auth.service';
import { SearchService } from './services/search.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  form!: FormGroup

  constructor(private fb: FormBuilder, public authSvc: AuthService, 
    private searchSvc: SearchService, private router: Router) {}

  ngOnInit(): void {
    this.form = this.createForm()    
  }

  logout() {
    this.authSvc.logout()
    this.router.navigate(['/'])  
  }

  search() {
    const query = this.form.value.query
    console.debug(query);
    this.searchSvc.searchTV(query)
        .then(result => {
          console.debug(result);
          if(result.code == 200){
            const data = JSON.parse(result.data)
            this.searchSvc.searchPage = data
            if (this.searchSvc.searchPage.totalResults == 0)
              alert("There are no results, please try again")
            else
              this.router.navigate(['/results'], {queryParams : { page: data.page, query: data.query }})
          }
        }).catch(err => {
          console.error(err)
        })
    this.form = this.createForm()
  }

  searchNav() {
    this.router.navigate(['/search'])
  }

  private createForm() {
    return this.fb.group({
      query: this.fb.control('', Validators.required)
    })
  }
}
