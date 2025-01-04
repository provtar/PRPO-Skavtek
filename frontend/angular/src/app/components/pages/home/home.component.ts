import { Component, ViewChild } from '@angular/core';
import { ClanPostModalComponent } from '../../modal/clan-post-modal/clan-post-modal.component';
import { Clan, UserDataService } from '../../../services/data/user-data.service';
import { CommonModule } from '@angular/common';
import { ClanPutModalComponent } from '../../modal/clan-put-modal/clan-put-modal.component';
import { ClanDataService } from '../../../services/clan.service';
import { SkupinaPostModalButtonComponent } from "../../modal-button/skupina-post-modal-button/skupina-post-modal-button.component";
import { VarovanciComponent } from "../../add-in/varovanci/varovanci.component";
import { MojeSkupineComponent } from "../../add-in/moje-skupine/moje-skupine.component";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, VarovanciComponent, MojeSkupineComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

  constructor(private userData: UserDataService) {}

  ngOnInit(): void {
    this.userData.initUser();
  }
}
