import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-custom-img',
  templateUrl: './custom-img.component.html',
  styleUrls: ['./custom-img.component.css']
})
export class CustomImgComponent implements OnInit {

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
