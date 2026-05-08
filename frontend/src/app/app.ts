import { Component } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { NavbarComponent } from './components/navbar/navbar';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink, RouterLinkActive, NavbarComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  title = 'DigitalBank';
}
