import { Component, ViewChild } from '@angular/core';
import { ClanPostModalComponent } from '../modal/clan-post-modal/clan-post-modal.component';
import { Clan, UserDataService } from '../../services/user-data.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [ClanPostModalComponent, CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

  constructor(private userData: UserDataService) {}

  mojiVarovanci: Clan[] = [];

  ngOnInit(): void {
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
}
