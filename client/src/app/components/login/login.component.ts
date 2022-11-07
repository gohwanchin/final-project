import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form!:FormGroup

  constructor(private fb: FormBuilder, private authSvc: AuthService, private router: Router, private title: Title) { }

  ngOnInit(): void {
    this.form = this.createForm()
    this.title.setTitle("Login")
  }

  submit(){
    this.authSvc.login(this.form.value.username, this.form.value.password).then(res =>{
      console.log(res);
      this.router.navigate(['/search'])
    }).catch(err =>{
      console.error();
      alert("Wrong username or password \nPlease try again")
      this.form = this.createForm()
    })
  }

  private createForm(){
    return this.fb.group({
      username: this.fb.control('', Validators.required),
      password: this.fb.control('', Validators.required)
    })
  }

}
