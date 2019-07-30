import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-custom-video',
  templateUrl: './custom-video.component.html',
  styleUrls: ['./custom-video.component.css']
})
export class CustomVideoComponent implements OnInit {

  constructor() { }
  protected _mysSrc : String;

  public get mySrc(){
      return this._mysSrc;
  }

  @Input() public set mySrc(p_value){
    this._mysSrc = p_value;
  }

  ngOnInit() {

  }

}
