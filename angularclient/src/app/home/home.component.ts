import { Component, OnInit } from '@angular/core';
import { Kweet } from '../_models/kweet';

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
  private newKweetCounter: string;

  constructor(
    private authenticationService: AuthenticationService,
    private userService: UserService,
    private kweetService: KweetService
  ) {
    this.authenticationService.currentUser.subscribe(user => {
      this.currentUser = user;
    });

    this.kweetService.getTimeline(this.currentUser.id).subscribe((kweets: Kweet[]) => {
      this.kweets = kweets;
    });

    this.newKweetCounter = '0/140';
  }

  ngOnInit() {

  }

  onCreateKweet(kweet: string): void {
    this.kweetService.create(this.currentUser.id, kweet).subscribe(() => {
      this.kweetService.getTimeline(this.currentUser.id).subscribe((kweets: Kweet[]) => {
        this.kweets = kweets;
      });
    });
  }
}
