import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { User } from 'src/app/models';
import { AuthService } from 'src/app/services/auth.service';
import { SearchService } from 'src/app/services/search.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form!: FormGroup
  genres!: Array<String>

  constructor(private fb: FormBuilder, private authSvc: AuthService, private searchSvc:SearchService, private router: Router, private title: Title) { }

  ngOnInit(): void {
    this.title.setTitle("Register")
    this.form = this.createForm()
    this.searchSvc.getGenres().then(result => {
      console.debug(result);
      const list = JSON.parse(result.data).genres
      this.genres = list.map((i:any) => i.name)
    }).catch(err => {
      console.error(err)
    })
  }

  submit() {
    const user = this.form.value as User
    this.authSvc.register(user)
        .then(result => {
          console.log(result);
          this.authSvc.login(user.username, user.password)
              .then(res => {
                console.log(res);
                this.router.navigate(['/search'])
              })
        }).catch(err => {
          console.error(err);
          alert("There was an error registering the user \nPlease try again")
        })
  }

  private createForm(){
    return this.fb.group({
      username: this.fb.control('', Validators.required),
      password: this.fb.control('', Validators.required),
      email: this.fb.control('', [Validators.email, Validators.required]),
      genre: this.fb.control('', Validators.required),
      phone: this.fb.control('', [Validators.required, Validators.pattern('^[0-9]{8}$')])
    })
  }
}
