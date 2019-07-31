import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { CustomImgComponent } from 'src/app/components/custom-img/custom-img.component';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit,AfterViewInit  {

  
  constructor() { }
  
  ngAfterViewInit() {
  }  
  
  ngOnInit() {
  }

}
