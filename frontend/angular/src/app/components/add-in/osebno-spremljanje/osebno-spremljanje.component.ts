import { CommonModule } from '@angular/common';
import { Component, Input, SimpleChange, SimpleChanges } from '@angular/core';
import { OsebnoSpremljanje } from '../../../services/data/user-data.service';
import { OsebnoSpremljanjeServiceService } from '../../../services/osebno-spremljanje-service.service';
import { OsebnoSpremljanjePostButtonComponent } from "../../modal-button/osebno-spremljanje-post-button/osebno-spremljanje-post-button.component";
import { OsebnoSpremljanjePutButtonComponent } from "../../modal-button/osebno-spremljanje-put-button/osebno-spremljanje-put-button.component";
import { OsebnoSpremljanjeDeleteButtonComponent } from "../../modal-button/osebno-spremljanje-delete-button/osebno-spremljanje-delete-button.component";

@Component({
  selector: 'app-osebno-spremljanje',
  standalone: true,
  imports: [CommonModule, OsebnoSpremljanjePostButtonComponent, OsebnoSpremljanjePutButtonComponent, OsebnoSpremljanjeDeleteButtonComponent],
  templateUrl: './osebno-spremljanje.component.html',
  styleUrl: './osebno-spremljanje.component.css'
})
export class OsebnoSpremljanjeComponent {
  @Input() clanId! : number;


  constructor(private osService : OsebnoSpremljanjeServiceService) {}
  osZapisi! : OsebnoSpremljanje[];
  initialized : boolean = false;

  ngOnChanges(changes : SimpleChanges){
    if(changes['clanId']){
      this.osService.getOsebnoSpremljanjePoClanu(this.clanId).subscribe(
        (response) => {
          this.osZapisi = response.map(
            (zapis) => {
              zapis.datum = new Date(zapis.datum);
              return zapis;
            }
          );
          this.initialized = true;
        }
      );
    }
  }

  refresh(){
    this.osService.getOsebnoSpremljanjePoClanu(this.clanId).subscribe(
      (response) => {
        this.osZapisi = response;
      }
    );
  }

  onOSPostSuccess(novOS : OsebnoSpremljanje){
    this.refresh();
  }

  onOSPutSuccess(posodobljenOS : OsebnoSpremljanje){
    this.refresh();
  }

  onOSDeleteSuccess(){
    this.refresh();
  }
}
