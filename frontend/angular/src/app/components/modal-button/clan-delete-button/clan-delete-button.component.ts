import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ClanDataService } from '../../../services/clan.service';

@Component({
  selector: 'app-clan-delete-button',
  standalone: true,
  imports: [],
  templateUrl: './clan-delete-button.component.html',
  styleUrl: './clan-delete-button.component.css'
})
export class ClanDeleteButtonComponent {
  @Input() clanId! : number;
  @Output() deleteSuccess = new EventEmitter<void>();

  constructor(private claniService : ClanDataService) {}

  deleteClan(){
    this.claniService.deleteClan(this.clanId).subscribe((res) => {
      this.deleteSuccess.emit();
    })
  }
}
