import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { CdkDragDrop } from '@angular/cdk/drag-drop';


@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit,AfterViewInit  {

  
  constructor() { }
  
  ngAfterViewInit() {
  }  
  onDrop(event: CdkDragDrop<string[]>) {
   alert ('dropped')
  }
  entered(event: any) {
    alert ('entered')
   }
  
  ngOnInit() {
  }

}
