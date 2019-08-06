import { Component, OnInit, Input, Output, EventEmitter, ViewChild, ElementRef } from '@angular/core';
import { IPlayableMedia, IPlayableMediaOptions } from 'src/app/interfaces/mediainterfaces';


@Component({
  selector: 'app-custom-img',
  templateUrl: './custom-img.component.html',
  styleUrls: ['./custom-img.component.css']
})

export class CustomImgComponent implements OnInit,IPlayableMedia {


  private _myOptions:IPlayableMediaOptions;

  private _myStyle:any;
  private _myHeight:string;
  private _myTransformOrigin:string;
  private _myTop : number;
  private _myTopOfSeconds: number;
  
  private _myWidth: number;
  private _myVisibility: string;

  private _myMin: number;
  private _myMax: number;
  private _myValue: number;
  private _myStep: number;
  private _myOffset: number;
  
  _mySlider: HTMLInputElement;

  @Output()
  myOncanplaythrough:EventEmitter<string> = new EventEmitter<string>();

  constructor() { }

  ngOnInit() {
    this._myOffset= 6;
  }



  @ViewChild("slider",{static: false})  
  set mainVideoEl(el: ElementRef) {
        this._mySlider = el.nativeElement;
        //this._mySlider.onmousedown = this.setMyNewValue.bind(this);
        //this._mySlider.onmouseup = this.setMyNewValue.bind(this);
  }

  private _isSliderDisabled = false;
  disableSlider (event:any){
    this._isSliderDisabled = true;
    
  }

  setMyNewValue(newValue:number){
    alert('hi');
    this.myValue = newValue;
    this.setMyTopValue();
  }

  setMyTopValue(){
    //var slider = this._mySlider;
    var sliderPos =this.myValue / (this.myMax - this.myMin);
    var myCursorStep = this._myOptions.height * sliderPos;
    // var myCursorStep  = ((this._myOptions.height) / (this.myMax - this.myMin +1));
    //this.myTop = (-1) * Math.round(myCursorStep * this.myValue + this._myOffset);
    this.myTop = (-1) * myCursorStep  - this._myOffset;
  }

  sync(currentTime:number): void {
    if (this.myMax>=this.myValue){
      this.myValue = currentTime;
      this.setMyTopValue();
    }
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
    this.myTransformOrigin =  this._myOptions.height/2 + "px  " + this._myOptions.height/2 + "px";
    this.myTop = (-1) * this._myOffset;

    this.myMin = this._myOptions.start;
    this.myMax = this._myOptions.end;
    this.myValue = this._myOptions.start;
    this.myStep = 1;
    this.myVisibility = "visible";
  }
  
  getOptions():IPlayableMediaOptions {
    return this._myOptions;
  }
  setCurrentPosition(position: number): void {
    throw new Error("Method not implemented.");
  }
  


  public get myMin(): number {
    return this._myMin;
  }
  @Input() public set myMin(value: number) {
    this._myMin = value;
  }

  public get myMax(): number {
    return this._myMax;
  }
  @Input() public set myMax(value: number) {
    this._myMax = value;
  }
 
  public get myValue(): number {
    return (Math.round (this._myValue*10)/10);
  }
  @Input() public set myValue(value: number) {
    this._myValue = value;
  }

  public get myStep(): number {
    return this._myStep;
  }

  @Input() public set myStep(value: number) {
    this._myStep = value;
  }

  public get myStyle(){
    return this._myStyle;
  }

  @Input() public set myStyle(p_value){
    this._myStyle = p_value;
  }
  public get myTop(){
    return this._myTop;
  }

  @Input() public set myTop(p_value){
    this._myTop = p_value;
  }

  public get myTopOfSeconds(): number {
    return  this._myTop - 11 // add offset to myTop ;
  }

 
  
  public get myHeight(){
    return this._myHeight;
  }

  @Input() public set myTransformOrigin(p_value){
    this._myTransformOrigin = p_value;
  }

 public get myTransformOrigin(){
    return this._myTransformOrigin;
  }

  @Input() public set myHeight(p_value){
    this._myHeight = p_value;
  }

  public get myWidth(): number {
    return this._myWidth;
  }
  
  @Input() public set myWidth(value: number) {
    this._myWidth = value;
  }

  public get myVisibility(): string {
    return this._myVisibility;
  }
  @Input()  public set myVisibility(value: string) {
    this._myVisibility = value;
  }


}
