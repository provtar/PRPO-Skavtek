import { Component } from '@angular/core';
import { Prisotnost, Srecanje, UserDataService } from '../../../services/data/user-data.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SrecanjaService } from '../../../services/srecanja.service';
import { CommonModule } from '@angular/common';
import { PrisotnostiPutFormComponent } from "../../form/prisotnosti-put-form/prisotnosti-put-form.component";
import { PrisotnostiPostButtonComponent } from "../../modal-button/prisotnosti-post-button/prisotnosti-post-button.component";
import { PrisotnostiComponent } from "../../add-in/prisotnosti/prisotnosti.component";
import { SrecanjePutButtonComponent } from "../../modal-button/srecanje-put-button/srecanje-put-button.component";
import { SrecanjeDeleteButtonComponent } from "../../modal-button/srecanje-delete-button/srecanje-delete-button.component";
import { PrisotnostiDeleteVseButtonComponent } from "../../modal-button/prisotnosti-delete-vse-button/prisotnosti-delete-vse-button.component";

@Component({
  selector: 'app-srecanja',
  standalone: true,
  imports: [CommonModule, PrisotnostiPostButtonComponent, PrisotnostiComponent, SrecanjePutButtonComponent, SrecanjeDeleteButtonComponent, PrisotnostiPutFormComponent, PrisotnostiDeleteVseButtonComponent],
  templateUrl: './srecanja.component.html',
  styleUrl: './srecanja.component.css'
})
export class SrecanjaComponent {


  constructor(private userData: UserDataService, private srecanjaService : SrecanjaService,  private route : ActivatedRoute, private router : Router) {}
  srecanje!: Srecanje;
  prisotnosti!: Prisotnost[] | undefined;
  initialized : boolean = false;
  urejanjePrisotnosti: boolean = false;

  ngOnInit(){
    this.userData.initUser();
    const srecanjeId = parseInt(this.route.snapshot.queryParamMap.get('srecanjeId')!);
    this.srecanjaService.getSrecanje(srecanjeId).subscribe({
      next: (srecanje) => {
        srecanje.datumOd = new Date(srecanje.datumOd);
        srecanje.datumDo = new Date(srecanje.datumDo);
        this.srecanje = srecanje;
        this.initialized = true;
      },
      error: (error) => {
        this.router.navigate(['/not-found']);
      }
  })
  }

  refresh(){
    const srecanjeId = parseInt(this.route.snapshot.queryParamMap.get('srecanjeId')!);
    this.srecanjaService.getSrecanje(srecanjeId).subscribe(
      (srecanje) => {
        srecanje.datumOd = new Date(srecanje.datumOd);
        srecanje.datumDo = new Date(srecanje.datumDo);
        this.srecanje = srecanje;
      }
    )
  }

  uredi(){
    this.urejanjePrisotnosti = true;
  }

  naloziPrisotnosti(prisotnosti : Prisotnost[]){
    this.prisotnosti = prisotnosti;
  }

  onPrisotnostiPostSuccess(novePrisotnosti : Prisotnost[]){
    this.refresh();
  }

  onPrisotnostiPutSuccess(posodobljenePrisotnosti : Prisotnost[]){
    this.urejanjePrisotnosti = false;
    this.refresh();
  }

  onPrisotnostiDeleteSuccess(){
    this.refresh();
  }

  onSrecanjeDeleteSuccess(){
    this.router.navigate(['/skupina'], {
      queryParams: {
        skupinaId: this.srecanje.idSkupine,
      }
    })
  }

  onSrecanjePutSuccess(posodobljenoSrecanje : Srecanje){
    this.refresh();
  }

}
