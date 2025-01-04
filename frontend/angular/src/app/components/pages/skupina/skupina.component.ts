import { Component } from '@angular/core';
import { UserDataService } from '../../../services/data/user-data.service';

@Component({
  selector: 'app-skupina',
  standalone: true,
  imports: [],
  templateUrl: './skupina.component.html',
  styleUrl: './skupina.component.css'
})
export class SkupinaComponent {

  constructor(private userData: UserDataService) {}

  ngOnInit(){
    this.userData.initUser();
  }

  // ngOnInit(){
  //   if(localStorage.getItem('user') && this.userData.varovanciNotInit()){
  //         const user: Clan = JSON.parse(localStorage.getItem('user') as string);
  //         this.clanData.getVarovanci(user.id).subscribe((response) => {
  //           this.userData.initVarovanci(response);
  //         })
  //       }
  // }

}
