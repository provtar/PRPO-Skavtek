import { Component } from '@angular/core';
import { UserDataService } from '../../../services/data/user-data.service';

@Component({
  selector: 'app-srecanja',
  standalone: true,
  imports: [],
  templateUrl: './srecanja.component.html',
  styleUrl: './srecanja.component.css'
})
export class SrecanjaComponent {

  constructor(private userData: UserDataService) {}

  ngOnInit(){
    this.userData.initUser();
  }

}
