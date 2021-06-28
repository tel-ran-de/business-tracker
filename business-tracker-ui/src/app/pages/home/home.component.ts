import {Component} from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  cards: { img: string, title: string }[] = [
    {img: 'assets/img/card-icon.png', title: 'Founder'},
    {img: 'assets/img/card-icon.png/', title: 'Mentor'},
    {img: 'assets/img/card-icon.png', title: 'Investor'},
    {img: 'assets/img/card-icon.png', title: 'Manger'},
  ];
}
