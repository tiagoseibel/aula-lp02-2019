import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InputAulaComponent } from './input-aula.component';

describe('InputAulaComponent', () => {
  let component: InputAulaComponent;
  let fixture: ComponentFixture<InputAulaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InputAulaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InputAulaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
