import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Admind } from './admind';

describe('Admind', () => {
  let component: Admind;
  let fixture: ComponentFixture<Admind>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Admind],
    }).compileComponents();

    fixture = TestBed.createComponent(Admind);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
