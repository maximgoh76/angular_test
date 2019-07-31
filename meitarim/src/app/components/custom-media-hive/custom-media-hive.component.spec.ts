import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomMediaHiveComponent } from './custom-media-hive.component';

describe('CustomMediaHiveComponent', () => {
  let component: CustomMediaHiveComponent;
  let fixture: ComponentFixture<CustomMediaHiveComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomMediaHiveComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomMediaHiveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
