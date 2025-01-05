import { Component, ViewChild } from '@angular/core';
import { SkupinaPostModalButtonComponent } from "../../modal-button/skupina-post-modal-button/skupina-post-modal-button.component";
import { Clan, Skupina, UserDataService } from '../../../services/data/user-data.service';
import { SkupinaService } from '../../../services/skupina.service';
import { CommonModule } from '@angular/common';
import { SkupinaPutModalButtonComponent } from "../../modal-button/skupina-put-modal-button/skupina-put-modal-button.component";
import { SkupinaDeleteButtonComponent } from "../../modal-button/skupina-delete-button/skupina-delete-button.component";

@Component({
  selector: 'app-moje-skupine',
  standalone: true,
  imports: [SkupinaPostModalButtonComponent, CommonModule, SkupinaPutModalButtonComponent, SkupinaDeleteButtonComponent],
  templateUrl: './moje-skupine.component.html',
  styleUrl: './moje-skupine.component.css'
})
export class MojeSkupineComponent {

  constructor(private userData: UserDataService, private skupinaService: SkupinaService) {}

  mojeSkupine: Skupina[] = [];

  ngOnInit(){
    if(localStorage.getItem('user') && this.userData.mojeSkupineNotInit()){
      const user: Clan = JSON.parse(localStorage.getItem('user') as string);
      this.skupinaService.getSkupinePoClanu(user.id).subscribe(
      (response) => {
        this.userData.initMojeSkupine(response);
      } );
    }
    this.userData.mojeSkupine$.subscribe((skupine) => {
      this.mojeSkupine = skupine;
    });
  }
}
