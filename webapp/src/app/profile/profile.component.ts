import { Component, OnInit } from '@angular/core';
import { Kweet } from '../_models/kweet';

import { User } from '../_models/user';
import { AuthenticationService } from '../_services/authentication.service';
import { KweetService } from '../_services/kweet.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  user: User;
  kweets: Kweet[];

  constructor(private authenticationService: AuthenticationService, private userService: UserService, private kweetService: KweetService) {
    this.kweets = [];

    this.authenticationService.currentUser.subscribe(user => {
      this.user = user;
    });

    kweetService.getAllFromUser(this.user.id).subscribe((kweets: Kweet[]) => {
      this.kweets = kweets;
    });
  }

  ngOnInit() {
  }
}
