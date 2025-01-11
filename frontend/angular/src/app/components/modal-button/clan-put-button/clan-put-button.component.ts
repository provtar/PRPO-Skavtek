import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { Clan } from '../../../services/data/user-data.service';
import { ClanPutModalComponent } from '../../modal/clan-put-modal/clan-put-modal.component';

@Component({
  selector: 'app-clan-put-button',
  standalone: true,
  imports: [ClanPutModalComponent],
  templateUrl: './clan-put-button.component.html',
  styleUrl: './clan-put-button.component.css'
})
export class ClanPutButtonComponent {
  @Input() clan! : Clan;
  @Output() putSuccess = new EventEmitter<Clan>();

  @ViewChild("clanPutModal") clanPutModal : ClanPutModalComponent | undefined;

  openModal(){
    if(this.clanPutModal)this.clanPutModal.open();
  }

  onPutSuccess(posodobljenClan : Clan){
    this.putSuccess.emit(posodobljenClan);
  }
}
