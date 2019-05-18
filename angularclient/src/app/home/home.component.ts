import { Component, OnInit } from '@angular/core';
import { Kweet } from "../_models/kweet";

import { User } from '../_models/user';
import { AuthenticationService } from '../_services/authentication.service';
import { KweetService } from '../_services/kweet.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  private currentUser: User;
  private kweets: Kweet[];

  constructor(
    private authenticationService: AuthenticationService,
    private userService: UserService,
    private kweetService: KweetService
  ) {
    this.authenticationService.currentUser.subscribe(user => {
      this.currentUser = user;
    });

    kweetService.getTimeline(this.currentUser.id).subscribe((kweets: Kweet[]) => {
      this.kweets = kweets;
      console.log(kweets);
    });
  }

  ngOnInit() {

  }

  onCreateKweet() {
    this.kweetService.create(this.currentUser.id, 'test');
  }
}
