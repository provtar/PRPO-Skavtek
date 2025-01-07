import { Component } from '@angular/core';
import { LoginService } from '../../../../services/login.service';
import { GlobalVarService } from '../../../../services/data/global-var.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm!: FormGroup;

  constructor(private global: GlobalVarService,
    private router: Router,
    private loginService: LoginService,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required]],
      password: ['', [Validators.required]]
    })
  }

  public login() : void {
    if(this.loginForm.valid) {
      //TODO prikazi napako ko napacno username ali geslo
      this.loginService.login(this.loginForm.value).subscribe(
        (response) => {
          localStorage.setItem('user', JSON.stringify(response));
          var user = localStorage.getItem('user');
          if(user){
            this.router.navigate(['/home']);
          }
        })
    } else {
      console.log('Form not valid');
    }
  }
}
