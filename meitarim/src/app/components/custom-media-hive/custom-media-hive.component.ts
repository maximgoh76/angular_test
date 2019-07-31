import { Component, OnInit, ViewChild } from '@angular/core';
import { IPlayableMedia, IPlayableMediaOptions } from 'src/app/interfaces/mediainterfaces';
import { CustomImgComponent } from '../custom-img/custom-img.component';
import { CustomVideoComponent } from '../custom-video/custom-video.component';

@Component({
  selector: 'app-custom-media-hive',
  templateUrl: './custom-media-hive.component.html',
  styleUrls: ['./custom-media-hive.component.css']
})
export class CustomMediaHiveComponent implements OnInit {
  
  @ViewChild("img1",{static: false}) img1Component: CustomImgComponent;
  @ViewChild("video1",{static: false}) video1Component: CustomVideoComponent; 
  @ViewChild("video2",{static: false}) video2Component: CustomVideoComponent;
  
  private _itemsLoaded:number = 0;
  private _itemsCount:number = 0;
  constructor() { }

  ngOnInit() {
  
  }

  public setVideo(){
    try{
      this._itemsLoaded = 0;
      this._itemsCount = 3; 
      this.img1Component.setOptions(
        {
          end:100,
          start:0,
          height:951,
          width:257,
          src:"/assets/pictures/specto.png"
        }
      );
        
      this.video1Component.setOptions(
        {
          end:100,
          start:0,
          height:300,
          width:400,
          src:"/assets/moovies/big_buck_bunny.mp4"
        }
      );
      this.video2Component.setOptions(
          {
            end:100,
            start:0,
            height:300,
            width:400,
            src:"/assets/moovies/big_buck_bunny2.mp4"
          }
        );
  
   }catch(e){
      alert (e.message)
    }
    
  }

  
  public startPlay(){
    
    this.img1Component.play();
    this.video1Component.play();
    this.video2Component.play();
}
  public stopPlay(){
    
      this.img1Component.stop();
      this.video1Component.stop();
      this.video2Component.stop();
  }



public mediaLoaded(){
  this._itemsLoaded ++;
  if (this._itemsLoaded >= this._itemsCount)
    this.startPlay();
}
  
}
