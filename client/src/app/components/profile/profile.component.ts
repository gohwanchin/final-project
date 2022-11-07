import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  url!: string
  show = true

  form!: FormGroup

  @ViewChild('file')
  file!: ElementRef

  constructor(private fb: FormBuilder, private userSvc: UserService, private title: Title) { }

  ngOnInit(): void {
    this.url = this.userSvc.getProfileURL()
    this.title.setTitle(this.userSvc.getUsername()!)
    this.form = this.createForm()
  }

  private createForm(){
    return this.fb.group({
      file: this.fb.control<any>('', Validators.required)
    })
  }

  uploadFile() {
    const file = this.file.nativeElement.files[0]
    console.debug(file);
    this.userSvc.uploadProfile(file).then(result => {
      console.debug(result)
      this.url = this.userSvc.getProfileURL()
      this.show = true
    }).catch(err => {
      console.error(err);
    })
  }

  delete(){
    console.log("delete");
    
    this.userSvc.deleteProfile().then(result => {
      console.debug(result)
      if (result.code == 200)
        this.imgError()
      else
        alert('There was an error deleting the profile picture\nPlease try again')
    }).catch(err => {
      console.error(err);
    })
  }

  imgError() {
    this.url = 'https://www.w3schools.com/howto/img_avatar.png'
    this.show = false
  }

}
