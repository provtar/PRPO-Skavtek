import { Component } from '@angular/core';
import { Clan, UserDataService } from '../../../services/data/user-data.service';
import { ActivatedRoute } from '@angular/router';
import { ClanDataService } from '../../../services/clan.service';
import { CommonModule } from '@angular/common';
import { SkupineComponent } from "../../add-in/skupine/skupine.component";
import { SrecanjaClanaComponent } from "../../add-in/srecanja-clana/srecanja-clana.component";
import { VarovanciComponent } from "../../add-in/varovanci/varovanci.component";

@Component({
  selector: 'app-clan',
  standalone: true,
  imports: [CommonModule, SkupineComponent],
  templateUrl: './clan.component.html',
  styleUrl: './clan.component.css'
})
export class ClanComponent {

    constructor(private route : ActivatedRoute, private claniService : ClanDataService) {}
    clan!: Clan;
    initialized: boolean = false;

    ngOnInit(){
      const clanId = parseInt(this.route.snapshot.paramMap.get('clanId')!, 10);
      this.claniService.getClan(clanId).subscribe(
        (response) =>{
          this.clan = response;
          this.initialized = true;
        }
      );
    }

    refresh(){
      const clanId = parseInt(this.route.snapshot.paramMap.get('clanId')!, 10);
      this.claniService.getClan(clanId).subscribe(
        (response) =>{
          this.clan = response;
          this.initialized = true;
        }
      );
    }

}
