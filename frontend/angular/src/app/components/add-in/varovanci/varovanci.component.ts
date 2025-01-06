import { Component, ViewChild } from '@angular/core';
import { ClanPostModalComponent } from "../../modal/clan-post-modal/clan-post-modal.component";
import { ClanPutModalComponent } from "../../modal/clan-put-modal/clan-put-modal.component";
import { Clan, UserDataService } from '../../../services/data/user-data.service';
import { ClanDataService } from '../../../services/clan.service';
import { CommonModule } from '@angular/common';
import { ClanPostButtonComponent } from "../../modal-button/clan-post-button/clan-post-button.component";
import { ClanPutButtonComponent } from "../../modal-button/clan-put-button/clan-put-button.component";
import { ClanDeleteButtonComponent } from "../../modal-button/clan-delete-button/clan-delete-button.component";

@Component({
  selector: 'app-varovanci',
  standalone: true,
  imports: [ClanPostModalComponent, ClanPutModalComponent, CommonModule, ClanPostButtonComponent, ClanPutButtonComponent, ClanDeleteButtonComponent],
  templateUrl: './varovanci.component.html',
  styleUrl: './varovanci.component.css'
})
export class VarovanciComponent {
  constructor(private userData: UserDataService, private clanData: ClanDataService) {}

  mojiVarovanci: Clan[] = [];
  user! : Clan;

  onVarovanecPostSuccess(){}

  onVarovanecPutSuccess(){}

  onVarovanecDeleteSuccess(){}

  ngOnInit(){
    if(localStorage.getItem('user') && this.userData.varovanciNotInit()){
      const user: Clan = JSON.parse(localStorage.getItem('user') as string);
      this.clanData.getVarovanci(user.id).subscribe((response) => {
        this.userData.initVarovanci(response);
      })
    }
    this.userData.varovanci$.subscribe((varovanci) => {
      this.mojiVarovanci = varovanci;
    });
    this.user = this.userData.user;
  }

  @ViewChild('clanPostModal') clanPostModal: ClanPostModalComponent | undefined;

  openClanPostModal() : void {
    if(this.clanPostModal) {
      this.clanPostModal.open();
    }
  }
  @ViewChild('clanPutModal') clanPutModal: ClanPutModalComponent | undefined;

  openClanPutModal(clan : Clan): void {
    if(this.clanPutModal) {
      this.clanPutModal.open();
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
