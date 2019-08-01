import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { IPlayableMedia, IPlayableMediaOptions } from 'src/app/interfaces/mediainterfaces';
import { NgStyle } from '@angular/common';

@Component({
  selector: 'app-custom-img',
  templateUrl: './custom-img.component.html',
  styleUrls: ['./custom-img.component.css']
})

export class CustomImgComponent implements OnInit,IPlayableMedia {

  private _myOptions:IPlayableMediaOptions;
  @Output()
  myOncanplaythrough:EventEmitter<string> = new EventEmitter<string>();
  
  play(): void {
    //TODO
  }
  stop(): void {
    //TODO
  }

  setOptions(option: IPlayableMediaOptions): void {
    this._myOptions = option;
    var myStyle:any = 
    {
      display:"inline-block",
      background:"url('"+ this._myOptions.src  + "') no-repeat center center",
      width:this._myOptions.width + "px",
      height:this._myOptions.height + "px"
    }
    this.myHeight = this._myOptions.height + "";
    // " display: inline-block;background:url('"+ this._myOptions.src
    //   + "') no-repeat center center;width: "+this._myOptions.width+ "px;height: "+this._myOptions.height+ "px;"
    this.myStyle = myStyle;
    this.myOncanplaythrough.emit (this._myOptions.src);
   
  }
  
  getOptions():IPlayableMediaOptions {
    return this._myOptions;
  }
  setCurrentPosition(position: number): void {
    throw new Error("Method not implemented.");
  }
  
  constructor() { }

  private _myStyle:any;
  private _myHeight:string;
  
  public get myStyle(){
    return this._myStyle;
  }

  @Input() public set myStyle(p_value){
    this._myStyle = p_value;
  }


  
  public get myHeight(){
    return this._myHeight;
  }

  @Input() public set myHeight(p_value){
    this._myHeight = p_value;
  }


  ngOnInit() {
    
  }

}
