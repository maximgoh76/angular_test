import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainComponent } from './views/main/main.component';
import { LoginComponent } from './views/login/login.component';
import { CustomVideoComponent } from './components/custom-video/custom-video.component';
import { CustomImgComponent } from './components/custom-img/custom-img.component';
import { CustomMediaHiveComponent } from './components/custom-media-hive/custom-media-hive.component';
import { CustomImgBaseComponent } from './components/custom-img-base/custom-img-base.component';

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    LoginComponent,
    CustomVideoComponent,
    CustomImgComponent,
    CustomMediaHiveComponent,
    CustomImgBaseComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
