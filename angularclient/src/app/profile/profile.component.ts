import { Component, OnInit } from '@angular/core';

import { User } from "../_models/user";
import { AuthenticationService } from "../_services/authentication.service";
import { UserService } from "../_services/user.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  currentUser: User;

  constructor(
    private authenticationService: AuthenticationService,
    private userService: UserService,
  ) {
    this.authenticationService.currentUser.subscribe(user => {
      this.currentUser = user;
    });
  }


  ngOnInit() {
  }

}
