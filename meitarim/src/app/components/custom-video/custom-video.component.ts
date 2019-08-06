import { Component, OnInit, Input, ElementRef, ViewChild, Output, EventEmitter } from '@angular/core';
import { IPlayableMedia, IPlayableMediaOptions } from 'src/app/interfaces/mediainterfaces';

@Component({
  selector: 'app-custom-video',
  templateUrl: './custom-video.component.html',
  styleUrls: ['./custom-video.component.css']
})
export class CustomVideoComponent implements OnInit,IPlayableMedia {
 


  private _myOptions:IPlayableMediaOptions;
  protected _mysSrc : any;
  protected _myWidth: string;
  protected _myHeight: string;
  
  @Output()
  myOncanplaythrough:EventEmitter<string> = new EventEmitter();
  
  public play(): void {
    this.videoPlayer.play();
  }
  public stop(): void {
    this.videoPlayer.pause();
  }
  public setOptions(option: IPlayableMediaOptions): void {
    this._myOptions = option;
    this.myWidth =  this._myOptions.width + "px";
    this.myHeight =  this._myOptions.height + "px";

    var vid =this._myOptions.src// URL.createObjectURL(this._myOptions.src); // IE10+
    // Video is now downloaded
    // and we can set it as source on the video element
    this.mySrc = vid;// this._myOptions.src;
    this.videoPlayer.load();
    //this.videoPlayer.addEventListener ("oncanplaythrough",this.oncanplaythroughLocal)
    //this.videoPlayer.addEventListener("oncanplaythrough",this.oncanplaythroughLocal)
    this.videoPlayer.oncanplaythrough=this.oncanplaythroughLocal.bind(this);
  }

  public oncanplaythroughLocal(event){
    this.myOncanplaythrough.emit(this.mySrc);
  }



  public getOptions():IPlayableMediaOptions {
    return this._myOptions;
  }
  public setCurrentPosition(position: number): void {
    throw new Error("Method not implemented.");
  }

  constructor() { 
    //this.mySrc = "/assets/moovies/popcorntest.mp4";
  }
  
  
  public get mySrc(){
      return this._mysSrc;
  }

  @Input() public set mySrc(p_value){
    this._mysSrc = p_value;
  }

  @Input() public set myWidth(p_value){
    this._myWidth = p_value;
  }

  public get myWidth(){
    return this._myWidth;
  }

  @Input() public set myHeight(p_value){
    this._myHeight = p_value;
  }
  public get myHeight(){
    return this._myHeight;
  }


  videoPlayer: HTMLVideoElement;

  @ViewChild("videoPlayer",{static: false})  
  set mainVideoEl(el: ElementRef) {
        this.videoPlayer = el.nativeElement;
  }

  toggleVideo(event: any) {
    this.videoPlayer.play();
  }

  public myCuurentTime():number{
    return this.videoPlayer.currentTime;
  }

  ngOnInit() {

  }

}
