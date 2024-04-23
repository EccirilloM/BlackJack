import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile-container',
  templateUrl: './profile-container.component.html',
  styleUrls: ['./profile-container.component.css']
})
export class ProfileContainerComponent implements OnInit {

  // COSTRUTTORE ----------------------------------------------------------------------------
  constructor() {

  }

  // NGONINIT ----------------------------------------------------------------------------
  ngOnInit(): void {
    console.log('ProfileContainerComponent');
  }
}
