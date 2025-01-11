import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { Clan } from '../../../services/data/user-data.service';
import { ClanPostModalComponent } from '../../modal/clan-post-modal/clan-post-modal.component';

@Component({
  selector: 'app-clan-post-button',
  standalone: true,
  imports: [ClanPostModalComponent],
  templateUrl: './clan-post-button.component.html',
  styleUrl: './clan-post-button.component.css'
})
export class ClanPostButtonComponent {

  @Input() masterId!: number;
  @Output() postSuccess = new EventEmitter<Clan>();

  @ViewChild("clanPostModal") clanPostModal : ClanPostModalComponent | undefined;

  openModal(){
    if(this.clanPostModal)this.clanPostModal.open();
  }

  onPostSuccess(novClan : Clan){
    this.postSuccess.emit(novClan);
  }
}
