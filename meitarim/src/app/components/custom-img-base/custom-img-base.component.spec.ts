import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomImgBaseComponent } from './custom-img-base.component';

describe('CustomImgBaseComponent', () => {
  let component: CustomImgBaseComponent;
  let fixture: ComponentFixture<CustomImgBaseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomImgBaseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomImgBaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
