import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form!: FormGroup

  constructor(private fb: FormBuilder, private authSvc: AuthService, private router: Router, private title: Title) { }

  ngOnInit(): void {
    this.title.setTitle("Register")
    this.form = this.createForm()
  }

  submit() {
    this.authSvc.register(this.form.value.username, this.form.value.password)
        .then(result => {
          console.log(result);
          this.authSvc.login(this.form.value.username, this.form.value.password)
              .subscribe(res => {
                console.log(res);
                this.router.navigate(['/search'])
              })
        }).catch(err => {
          console.error(err);
          alert("There was an error registering the user \n Please try again")
        })
  }

  private createForm(){
    return this.fb.group({
      username: this.fb.control('', Validators.required),
      password: this.fb.control('', Validators.required)
    })
  }
}
