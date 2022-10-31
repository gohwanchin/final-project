import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {

  code!: number
  
  constructor(private route: ActivatedRoute, private title: Title) { }

  ngOnInit(): void {
    this.code = this.route.snapshot.params['code']
    this.title.setTitle(`Error ${this.code}`)
  }
}
