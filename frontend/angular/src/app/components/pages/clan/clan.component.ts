import { Component } from '@angular/core';
import { UserDataService } from '../../../services/data/user-data.service';

@Component({
  selector: 'app-clan',
  standalone: true,
  imports: [],
  templateUrl: './clan.component.html',
  styleUrl: './clan.component.css'
})
export class ClanComponent {

    constructor(private userData: UserDataService) {}

    ngOnInit(){
      this.userData.initUser();
    }

}
