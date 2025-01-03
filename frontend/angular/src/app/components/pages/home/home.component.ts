import { Component, ViewChild } from '@angular/core';
import { ClanPostModalComponent } from '../../modal/clan-post-modal/clan-post-modal.component';
import { Clan, UserDataService } from '../../../services/user-data.service';
import { CommonModule } from '@angular/common';
import { ClanPutModalComponent } from '../../modal/clan-put-modal/clan-put-modal.component';
import { ClanDataService } from '../../../services/clan-data.service';
import { SkupinaPostModalButtonComponent } from "../../modal-button/skupina-post-modal-button/skupina-post-modal-button.component";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [ClanPostModalComponent, CommonModule, ClanPutModalComponent, SkupinaPostModalButtonComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

  constructor(private userData: UserDataService, private clanData: ClanDataService) {}

  mojiVarovanci: Clan[] = [];

  ngOnInit(): void {
    if(localStorage.getItem('user') && this.userData.varovanciNotInit()){
      const user: Clan = JSON.parse(localStorage.getItem('user') as string);
      this.clanData.getVarovanci(user.id).subscribe((response) => {
        this.userData.initVarovanci(response);
      })
    }
    this.userData.varovanci$.subscribe((varovanci) => {
      this.mojiVarovanci = varovanci;
    });
  }

  // Modalno okno za dodajanje clana
  @ViewChild('clanPostModal') clanPostModal: ClanPostModalComponent | undefined;

  openClanPostModal() : void {
    if(this.clanPostModal) {
      this.clanPostModal.open();
    }
  }
  @ViewChild('clanPutModal') clanPutModal: ClanPutModalComponent | undefined;

  openClanPutModal(clan : Clan): void {
    if(this.clanPutModal) {
      this.clanPutModal.open(clan);
    }
  }

  odstraniVarovanca(id: number){
    this.clanData.deleteClan(id).subscribe(
      (response) => {
        this.userData.odstraniVarovanca(id);
      }
    )
  }
}
