import { Component, OnInit } from '@angular/core';
import { Subscription } from "rxjs";

import { User } from "../_models/user";
import { AuthenticationService } from "../_services/authentication.service";
import { KweetService } from "../_services/kweet.service";
import { UserService } from "../_services/user.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  currentUser: User;
  currentUserSubscription: Subscription;

  constructor(
    private authenticationService: AuthenticationService,
    private userService: UserService,
    private kweetService: KweetService
  ) {
    this.currentUserSubscription = this.authenticationService.currentUser.subscribe(user => {
      this.currentUser = user;
    });
  }

  ngOnInit() {

  }

  ngOnDestroy() {
    // unsubscribe to ensure no memory leaks
    this.currentUserSubscription.unsubscribe();
  }

  onCreateKweet() {
    this.kweetService.create(this.currentUser.id, 'test');
  }
}
