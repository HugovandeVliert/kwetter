import { Component, OnDestroy, OnInit } from '@angular/core';
import { IMessage } from '@stomp/stompjs';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';

import { Kweet } from '../_models/kweet';
import { User } from '../_models/user';
import { AuthenticationService } from '../_services/authentication.service';
import { KweetService } from '../_services/kweet.service';
import { UserService } from '../_services/user.service';
import { WebsocketService } from '../_services/websocket.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {
  private subscriptions: Array<Subscription>;
  private newKweetCounter: string;

  private currentUser: User;
  private timeline: Kweet[];
  private trendingTopics: string[];

  constructor(
    private authenticationService: AuthenticationService,
    private userService: UserService,
    private kweetService: KweetService,
    private websocketService: WebsocketService,
    private toastrService: ToastrService
  ) {
    this.subscriptions = [];
    this.newKweetCounter = '0/140';
  }

  ngOnInit(): void {
    this.subscriptions.push(this.authenticationService.currentUser.subscribe((user: User) => {
      this.currentUser = user;
    }));
    this.subscriptions.push(this.websocketService.getNewFollowerNotifications(this.currentUser.id).subscribe((message: IMessage) => {
      this.toastrService.info(message.body);
    }));
    this.subscriptions.push(this.websocketService.getTimelineUpdateNotifications(this.currentUser.id).subscribe(() => {
      this.updateTimeline();
      this.updateTrendingTopics()
    }));

    this.updateTimeline();
    this.updateTrendingTopics()
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription: Subscription) => {
      subscription.unsubscribe();
    });
  }

  updateTimeline(): void {
    this.kweetService.getTimeline(this.currentUser.id).subscribe((kweets: Kweet[]) => {
      this.timeline = kweets;
    });
  }

  updateTrendingTopics(): void {
    this.kweetService.getTrendingTopics().subscribe((topics: string[]) => {
      this.trendingTopics = topics;
    });
  }

  onCreateKweet(kweet: string): void {
    this.kweetService.create(this.currentUser.id, kweet).subscribe(() => {
      this.updateTimeline();
    });
  }
}
