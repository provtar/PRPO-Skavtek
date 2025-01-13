import { Component, ViewChild } from '@angular/core';
import { ClanSkupine, Skupina, Srecanje, UserDataService } from '../../../services/data/user-data.service';
import { ActivatedRoute } from '@angular/router';
import { SkupinaService } from '../../../services/skupina.service';
import { CommonModule } from '@angular/common';
import { ClaniSkupineComponent } from "../../add-in/clani-skupine/clani-skupine.component";
import { ClaniSkupinePutFormComponent } from "../../form/clani-skupine-put-form/clani-skupine-put-form.component";
import { SrecanjePostButtonComponent } from "../../modal-button/srecanje-post-button/srecanje-post-button.component";
import { SrecanjaSkupineComponent } from "../../add-in/srecanja-skupine/srecanja-skupine.component";
import { MenuComponent } from "../../common/menu/menu.component";

@Component({
  selector: 'app-skupina',
  standalone: true,
  imports: [CommonModule, ClaniSkupineComponent, ClaniSkupinePutFormComponent, SrecanjePostButtonComponent, SrecanjaSkupineComponent, MenuComponent],
  templateUrl: './skupina.component.html',
  styleUrl: './skupina.component.css'
})
export class SkupinaComponent {

  @ViewChild('claniSkupine') claniSkupine : ClaniSkupineComponent | undefined;
  @ViewChild('srecanja') srecanja : SrecanjaSkupineComponent | undefined;

  constructor(private userData: UserDataService, private skupinaService : SkupinaService,  private route : ActivatedRoute) {}
  skupina!: Skupina;
  clanPutFormOpen:boolean = false;
  initialized : boolean = false;

  ngOnInit(){
    this.userData.initUser();
    const skupinaId = parseInt(this.route.snapshot.queryParamMap.get('skupinaId')!);
    this.skupinaService.getSkupina(skupinaId).subscribe(
      (skupina) => {
        this.skupina = skupina;
        this.initialized = true;
      }
    )
  }

  dodajClane(){
    this.clanPutFormOpen = true;
  }

  closeClaniPut(){
    this.clanPutFormOpen = false;
  }

  onSrecanjePostSuccess(novoSrecanje : Srecanje){
    this.srecanja?.refresh();
  }

  onClaniSkupinePutSuccess(dodaniClani: ClanSkupine[]){
    this.closeClaniPut();
    this.claniSkupine?.refresh();
  }

  // ngOnInit(){
  //   if(localStorage.getItem('user') && this.userData.varovanciNotInit()){
  //         const user: Clan = JSON.parse(localStorage.getItem('user') as string);
  //         this.clanData.getVarovanci(user.id).subscribe((response) => {
  //           this.userData.initVarovanci(response);
  //         })
  //       }
  // }

}
