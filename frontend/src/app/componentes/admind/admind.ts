import { Component } from '@angular/core';
import { RouterOutlet } from "@angular/router";

@Component({
  imports: [RouterOutlet],
  selector: 'app-admind',
  styleUrl: './admind.css',
  templateUrl: './admind.html',
})
export class Admind {
goToReject() {
throw new Error('Method not implemented.');
}
goToAprove() {
throw new Error('Method not implemented.');
}

}
