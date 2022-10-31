import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TVSearchPage } from 'src/app/models';
import { SearchService } from 'src/app/services/search.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  form!: FormGroup

  constructor(private fb: FormBuilder, private searchSvc: SearchService, private router: Router) { }

  ngOnInit(): void {
    this.form = this.createForm()
  } 

  submit(){
    const query = this.form.value.query
    console.log(query);
    this.searchSvc.searchTV(query)
        .then(result => {
          console.log(result);
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

  private createForm(){
    return this.fb.group({
      query: this.fb.control('', Validators.required)
    })
  }
}
