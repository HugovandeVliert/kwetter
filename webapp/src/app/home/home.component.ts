import { Component, OnInit } from '@angular/core';
import { StompConfig, StompService } from '@stomp/ng2-stompjs';
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
  private trendingTopics: string[];
  private newKweetCounter: string;

  private serverUrl = 'ws://localhost:8080/socket';
  private stompService: StompService;

  constructor(
    private authenticationService: AuthenticationService,
    private userService: UserService,
    private kweetService: KweetService
  ) {
    this.authenticationService.currentUser.subscribe((user: User) => {
      this.currentUser = user;
    });

    this.updateTimeline();

    this.newKweetCounter = '0/140';

    this.initializeWebSocketConnection();
  }

  ngOnInit() {

  }

  initializeWebSocketConnection(): void {
    this.stompService = new StompService({
      url: this.serverUrl,
      headers: {
        login: '',
        passcode: ''
      },
      heartbeat_in: 0,
      heartbeat_out: 20000,
      reconnect_delay: 5000,
      debug: false,
    });

    this.stompService.subscribe('/timeline/' + this.currentUser.id).subscribe(() => {
      this.updateTimeline();
    });
  }

  updateTimeline(): void {
    this.kweetService.getTimeline(this.currentUser.id).subscribe((kweets: Kweet[]) => {
      this.kweets = kweets;
    });
  }

  onCreateKweet(kweet: string): void {
    this.kweetService.create(this.currentUser.id, kweet).subscribe(() => {
      this.updateTimeline();
    });
  }
}
