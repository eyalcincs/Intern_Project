import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Bookregister } from './bookregister';

describe('Bookregister', () => {
  let component: Bookregister;
  let fixture: ComponentFixture<Bookregister>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Bookregister]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Bookregister);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
