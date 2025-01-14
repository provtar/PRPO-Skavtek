import { Component, ViewChild } from '@angular/core';
import { ClanPostModalComponent } from '../../modal/clan-post-modal/clan-post-modal.component';
import { Clan, Skupina, UserDataService } from '../../../services/data/user-data.service';
import { CommonModule } from '@angular/common';
import { ClanPutModalComponent } from '../../modal/clan-put-modal/clan-put-modal.component';
import { ClanDataService } from '../../../services/clan.service';
import { SkupinaPostModalButtonComponent } from "../../modal-button/skupina-post-modal-button/skupina-post-modal-button.component";
import { VarovanciComponent } from "../../add-in/varovanci/varovanci.component";
import { SkupineComponent } from "../../add-in/skupine/skupine.component";
import { SrecanjaComponent } from "../srecanja/srecanja.component";
import { SrecanjaClanaComponent } from "../../add-in/srecanja-clana/srecanja-clana.component";
import { MenuComponent } from "../../common/menu/menu.component";
import { TerminiComponent } from "../../termini/termini.component";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, VarovanciComponent, SkupineComponent, SrecanjaClanaComponent, SkupinaPostModalButtonComponent, MenuComponent, TerminiComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

  @ViewChild("mojeSkupine") mojeSkupine : SkupineComponent | undefined;



  constructor(private userData: UserDataService) {}
  jaz! : Clan;

  ngOnInit(): void {
    this.userData.initUser();
    this.jaz = this.userData.user;
  }

  onSkupinePostSuccess(novaSkupina: Skupina){
    if(this.mojeSkupine)this.mojeSkupine.refresh();
  }
}
