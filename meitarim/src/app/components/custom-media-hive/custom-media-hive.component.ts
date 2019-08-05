import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { IPlayableMedia, IPlayableMediaOptions } from 'src/app/interfaces/mediainterfaces';
import { CustomImgComponent } from '../custom-img/custom-img.component';
import { CustomVideoComponent } from '../custom-video/custom-video.component';
import { CdkDragDrop } from '@angular/cdk/drag-drop';

@Component({
  selector: 'app-custom-media-hive',
  templateUrl: './custom-media-hive.component.html',
  styleUrls: ['./custom-media-hive.component.css']
})
export class CustomMediaHiveComponent implements OnInit {
  
  @ViewChild("img1",{static: false}) img1Component: CustomImgComponent;
  @ViewChild("img2",{static: false}) img2Component: CustomImgComponent;
  @ViewChild("img3",{static: false}) img3Component: CustomImgComponent;

  @ViewChild("video1",{static: false}) video1Component: CustomVideoComponent; 
  @ViewChild("video2",{static: false}) video2Component: CustomVideoComponent;
  
  private _itemsLoaded:number = 0;
  private _itemsCount:number = 0;
  private _currentTime: number = 0;
  private _currentDruation = 60;
 
  public get currentTime(): number {
    return this._currentTime;
  }

  

  @Input() public set currentTime(value: number) {
    this._currentTime = value;
  }


  constructor() { }

  ngOnInit() {
    this._mySpeed = 100;
    this._currentTime = 1000;
  }

  public setVideo(){
    try{
      this._itemsLoaded = 0;
      this._itemsCount = 5; 
      this.img1Component.setOptions(
        {
          end:this._currentDruation,
          start:0,
          height:750, //951
          width:140,  //257
          src:"/assets/pictures/specto.png",
          step:1
        }
      );

      this.img2Component.setOptions(
        {
          end:this._currentDruation,
          start:0,
          height:276, //951
          width:168,  //257
          src:"/assets/pictures/graph1.jpg",
          step:1
        }
      );

      this.img3Component.setOptions(
        {
          end:this._currentDruation,
          start:0,
          height:300, //951
          width:168,  //257
          src:"/assets/pictures/graph2.jpg",
          step:1
        }
      );
        
      this.video1Component.setOptions(
        {
          end:this._currentDruation,
          start:0,
          height:300,
          width:400,
          src:"/assets/moovies/big_buck_bunny.mp4",
          step:1
        }
      );
      this.video2Component.setOptions(
          {
            end:this._currentDruation,
            start:0,
            height:300,
            width:400,
            src:"/assets/moovies/big_buck_bunny2.mp4",
            step:1
          }
        );
  
   }catch(e){
      alert (e.message)
    }
    
  }

  
public startPlay(){
    this.video1Component.play();
    this.video2Component.play();
    this.startUpdateTimer()
}
  public stopUpdateTimer(){
      this.video1Component.stop();
      this.video2Component.stop();
      this.stopTimer();
  }

  private _myTimer: any;
  private _mySpeed: number;
  
  startUpdateTimer(): void {
    
    if (this._myTimer == undefined){
      this._myTimer = setInterval(() => {

        //get video position
        var currentTime = this.video1Component.myCuurentTime();
        //console.log("currentTime" + currentTime);
        if (currentTime<=this._currentDruation){
          this.img1Component.sync(currentTime);
          this.img2Component.sync(currentTime);
          this.img3Component.sync(currentTime);
        }else{
          this.stopTimer();
        }
      },  this._mySpeed);
    }
  }

  stopTimer(): void {
    if (this._myTimer) {
      clearInterval(this._myTimer);
      this._myTimer = undefined;
    }
  }

  ngOnDestroy() {
    this.stopTimer();
  }

  public mediaLoaded(){
    this._itemsLoaded ++;
    if (this._itemsLoaded >= this._itemsCount)
      this.startPlay();
  }
  
}
