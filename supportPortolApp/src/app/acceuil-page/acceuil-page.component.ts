import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import * as bootstrap from 'bootstrap';
import SimpleLightbox from "simplelightbox";
import { User } from '../model/user';
import { NotificationType } from '../enum/notification-type.enum';
import { Subscription } from 'rxjs';
import { NotificationService } from '../service/notification.service';
import { Router } from '@angular/router';
import { AuthenticationService } from '../service/authentication.service';
import { HeaderType } from '../enum/header-type.enum';

@Component({
  selector: 'app-acceuil-page',
  templateUrl: './acceuil-page.component.html',
  styleUrls: ['./acceuil-page.component.css']
})
export class AcceuilPageComponent implements OnInit{

  formOpenBtn: HTMLElement | null = null;
  home: HTMLElement | null = null;
  formContainer: HTMLElement | null = null;
  formCloseBtn: HTMLElement | null = null;
  signupBtn: HTMLElement | null = null;
  loginBtn: HTMLElement | null = null;
  pwShowHide: NodeListOf<HTMLElement> | null = null;

  private subscriptions : Subscription [] =[]; 

  constructor(  private notificationService :NotificationService , private router : Router ,
    private authenticationService :AuthenticationService) {}

  ngOnInit(): void {
    if(this.authenticationService.isUserLoggedIn()){
      this.router.navigateByUrl('/dashbord-chart') ;
    }

    window.addEventListener('DOMContentLoaded', (event) => {
      // Navbar shrink function
      const navbarShrink = () => {
        const navbarCollapsible = document.body.querySelector('#mainNav') as HTMLElement;
        if (!navbarCollapsible) {
          return;
        }
        if (window.scrollY === 0) {
          navbarCollapsible.classList.remove('navbar-shrink');
        } else {
          navbarCollapsible.classList.add('navbar-shrink');
        }
      };

      // Shrink the navbar
      navbarShrink();

      // Shrink the navbar when page is scrolled
      document.addEventListener('scroll', navbarShrink);

      // Activate Bootstrap scrollspy on the main nav element
      const mainNav = document.body.querySelector('#mainNav');
      if (mainNav) {
        new bootstrap.ScrollSpy(document.body, {
          target: '#mainNav',
          rootMargin: '0px 0px -40%'
        });
      }

      // Collapse responsive navbar when toggler is visible
      const navbarToggler = document.body.querySelector('.navbar-toggler') as HTMLElement;
      const responsiveNavItems = Array.from(document.querySelectorAll('#navbarResponsive .nav-link'));
      responsiveNavItems.map((responsiveNavItem: HTMLElement) => {
        responsiveNavItem.addEventListener('click', () => {
          if (window.getComputedStyle(navbarToggler).display !== 'none') {
            navbarToggler.click();
          }
        });
      });
    });
    if(this.authenticationService.isUserLoggedIn()){
      this.router.navigateByUrl('dashbord-chart') ;
    }else{
      this.router.navigateByUrl('/acceuil') ; 
    }

    // cliquer sur un bouton à travers un autre bouton en utilisant son ID,
    
    const targetButton = document.getElementById('form-open') as HTMLButtonElement;
    // Obtenez la référence du bouton déclencheur à partir de son ID
    const triggerButton = document.getElementById('click-form-open') as HTMLButtonElement;

    // Ajoutez un gestionnaire d'événement pour le clic sur le bouton déclencheur
    triggerButton.addEventListener('click', () => {
      // Déclenchez le clic sur le bouton cible
      targetButton.click();
    });
    

    this.formOpenBtn = document.querySelector("#form-open");
    this.home = document.querySelector(".home");
    this.formContainer = document.querySelector(".form_container");
    this.formCloseBtn = document.querySelector(".form_close");
    this.signupBtn = document.querySelector("#signup");
    this.loginBtn = document.querySelector("#login");
    this.pwShowHide = document.querySelectorAll(".pw_hide");

    if (this.formOpenBtn) {
      this.formOpenBtn.addEventListener("click", () => this.home?.classList.add("show"));
    }

    if (this.formCloseBtn) {
      this.formCloseBtn.addEventListener("click", () => this.home?.classList.remove("show"));
    }

    if (this.pwShowHide) {
      this.pwShowHide.forEach((icon) => {
        icon.addEventListener("click", () => {
          let getPwInput = icon.parentElement?.querySelector("input");
          if (getPwInput?.type === "password") {
            getPwInput.type = "text";
            icon.classList.replace("uil-eye-slash", "uil-eye");
          } else {
            getPwInput.type = "password";
            icon.classList.replace("uil-eye", "uil-eye-slash");
          }
        });
      });
    }

    if (this.signupBtn) {
      this.signupBtn.addEventListener("click", (e) => {
        e.preventDefault();
        this.formContainer?.classList.add("active");
      });
    }

    if (this.loginBtn) {
      this.loginBtn.addEventListener("click", (e) => {
        e.preventDefault();
        this.formContainer?.classList.remove("active");
      });
    }
  }

  public onLogin (user : User){
    this.subscriptions.push (
      this.authenticationService.login(user).subscribe(
        (response:HttpResponse<User>) => {
          const token = response.headers.get(HeaderType.JWT_TOKEN) ;
          this.authenticationService.saveToken(token);
          this.authenticationService.addUserToLocalCache(response.body);
          this.router.navigateByUrl('/dashbord-chart') ;
        },
        (errorResponse: HttpErrorResponse) => {
          this.sendNotification(NotificationType.ERROR, errorResponse.error.message);
        }
      )
    );     
  }

  public onRegister (user : User){
    this.subscriptions.push(
      this.authenticationService.register(user).subscribe(
        (response:User) => {
          this.sendNotification(NotificationType.SUCCESS, `A new account was created for ${response.firstName}.
          please check your email for password to log in .`);
        },
        (errorResponse: HttpErrorResponse) => {
          this.sendNotification(NotificationType.ERROR, errorResponse.error.message);
        }
      )
      ); 
  }

  private sendNotification(notificationType: NotificationType, message: string) :void{
    if(message){
      this.notificationService.notify(notificationType,message) ;
    } else {
      this.notificationService.notify(notificationType, 'An error occure . please try again ');
    }
  }

  


  ngOnDestroy(): void {
    this.subscriptions.forEach(sub=>sub.unsubscribe());
  }
}