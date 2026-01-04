import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Booksdetail } from './booksdetail';

describe('Booksdetail', () => {
  let component: Booksdetail;
  let fixture: ComponentFixture<Booksdetail>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Booksdetail]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Booksdetail);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
